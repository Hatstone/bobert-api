package com.hatstone.bobertapi.controller;

import com.hatstone.bobertapi.dto.RunObject;
import com.hatstone.bobertapi.dto.SubmissionResults;
import com.hatstone.bobertapi.dto.Submission;
import com.hatstone.bobertapi.services.RestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<SubmissionResults> CreateSubmission(@RequestBody Submission submission){
        String insertQuery = "INSERT INTO submissions (userid, problemid, data, language) VALUES(?,?,?,?)";
        String fetchArgsQuery = "SELECT inputargs FROM testcases WHERE problemid = ?";
        
        long id = 0;
        RestService restService = null;

        try {
            Class.forName("org.postgresql.Driver");
            try (
                Connection conn = dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmtArgs = conn.prepareStatement(fetchArgsQuery, Statement.RETURN_GENERATED_KEYS);
            ) {
                pstmt.setLong(1, submission.getUserId());
                pstmt.setLong(2, submission.getProblemId());
                pstmt.setString(3, submission.getData());
                pstmt.setString(4, submission.getLanguage());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getLong(1);
                        }

                        //////////////////////////////////////////////////////////////////
                        //  Deal with RestService Stuff
                        //////////////////////////////////////////////////////////////////
                        pstmtArgs.setLong(1, submission.getProblemId());
                        ResultSet rsArgs = pstmtArgs.executeQuery();

                        double count = 0;
                        double correct = 0;
                        if (!rsArgs.next()) {
                            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                        }
                        else {
                            do {
                                String args = rsArgs.getString("inputargs");
                                String result = restService.createRunObject(submission.getLanguage(), submission.getData(), args);
                                count += 1.0;
                                if(result.equals(rsArgs.getString("expectedoutput"))) correct += 1.0;
                            } while (rsArgs.next());
                        }
                        //////////////////////////////////////////////////////////////////

                        SubmissionResults res = new SubmissionResults(id, correct / count);
                        return new ResponseEntity<SubmissionResults>(res, HttpStatus.OK);
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
        return new ResponseEntity<SubmissionResults>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/get-submission")
    public ResponseEntity<Submission> GetSubmission(@RequestParam(value = "id") Long id){
        String selectQuery = "SELECT * FROM submissions WHERE id=?";
        try {
            Class.forName("org.postgresql.Driver");
            try (
                Connection conn = dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);
            ) {
                pstmt.setLong(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    return new ResponseEntity<Submission>(HttpStatus.EXPECTATION_FAILED);
                }
                else {
                    Long uid = rs.getLong("userid");
                    Long pid = rs.getLong ("problemid");
                    String data = rs.getString ("data");
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
        //hi mom
    }

    @GetMapping("/get-userproblemsubmissions")
    public ResponseEntity<List<Submission>> GetUserProblemSubmissions(@RequestParam(value = "uid") Long uid, @RequestParam(value = "pid") Long pid){
        String selectQuery = "SELECT * FROM submissions WHERE userid = ? AND problemid = ?";
        try {
            Class.forName("org.postgresql.Driver");
            try (
                Connection conn = dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);
            ) {
                pstmt.setLong(1, uid);
                pstmt.setLong(2, pid);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    return new ResponseEntity<List<Submission>>(HttpStatus.EXPECTATION_FAILED);
                }
                ArrayList<Submission> submissions = new ArrayList<Submission>();
                do {
                    Long userId = rs.getLong("userid");
                    Long problemId = rs.getLong ("problemid");
                    String data = rs.getString ("data");
                    String lang = rs.getString ("language");
                    Submission foundSubmission = new Submission(userId, problemId, data, lang);
                    submissions.add(foundSubmission);
                } while (rs.next());
                return new ResponseEntity<List<Submission>>(submissions, HttpStatus.OK);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<List<Submission>>(HttpStatus.BAD_REQUEST);
    }

    public Connection dbConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
