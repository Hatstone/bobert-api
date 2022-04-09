package com.hatstone.bobertapi.controller;

import com.hatstone.bobertapi.dto.Submission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.*;

@RestController
@RequestMapping(path = "bobert-api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class SubmissionController {
    @Value("${database.url}")
    private String dbUrl;
    @Value("${database.username}")
    private String dbUser;
    @Value("${database.password}")
    private String dbPassword;

    @PostMapping("/create-submission")
    public ResponseEntity<Long> CreateSubmission(@RequestBody Submission submission){
        String insertQuery = "INSERT INTO submissions (userid, problemid, data, language) VALUES(?,?,?,?)";
        long id = 0;

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setLong(1, submission.getUserId());
                pstmt.setLong(2, submission.getProblemId());
                pstmt.setBytes(3, submission.getData());
                pstmt.setString(4, submission.getLanguage());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getLong(1);
                        }
                        return new ResponseEntity<Long>(id, HttpStatus.OK);
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/get-submission")
    public ResponseEntity<Submission> GetSubmission(@RequestParam(value = "id") Long id){
        String selectQuery = "SELECT * FROM submissions WHERE id=?";
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                 PreparedStatement pstmt = conn.prepareStatement(selectQuery,
                         Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setLong(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    return new ResponseEntity<Submission>(HttpStatus.EXPECTATION_FAILED);
                }
                else {
                    Long uid = rs.getLong("userid");
                    Long pid = rs.getLong ("problemid");
                    byte[] data = rs.getBytes ("data");
                    String lang = rs.getString ("language");
                    Submission foundSubmission = new Submission(uid, pid, data, lang);
                    return new ResponseEntity<Submission>(foundSubmission, HttpStatus.OK);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Submission>(HttpStatus.BAD_REQUEST);
    }

    public Connection dbConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
