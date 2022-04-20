package com.hatstone.bobertapi.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.hatstone.bobertapi.dto.Problem;
import com.hatstone.bobertapi.dto.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import javax.mail.internet.InternetAddress;

@RestController
@RequestMapping(path = "bobert-api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ProblemController {
    @Value("${database.url}")
    private String dbUrl;
    @Value("${database.username}")
    private String dbUser;
    @Value("${database.password}")
    private String dbPassword;

    @PostMapping("/create-problem")
    public ResponseEntity<Long> CreateProblem(@RequestBody Problem problem){
        String insertQuery = "INSERT INTO problems (contestid, title, description, timelimit, memlimit) VALUES(?,?,?,?,?)";
        long id = 0;

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setLong(1, problem.getContestId());
                pstmt.setString(2, problem.getTitle());
                pstmt.setString(3, problem.getDescription());


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

    @GetMapping("/get-problem")
    public ResponseEntity<Problem> GetProblem(@RequestParam(value = "id") Long id){
        String selectQuery = "SELECT * FROM problems WHERE id=?";
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                 PreparedStatement pstmt = conn.prepareStatement(selectQuery,
                         Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setLong(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    return new ResponseEntity<Problem>(HttpStatus.EXPECTATION_FAILED);
                }
                else {
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    Long cid = rs.getLong("contestId");
                    Problem foundProblem = new Problem(title, description, cid);
                    return new ResponseEntity<Problem>(foundProblem, HttpStatus.OK);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Problem>(HttpStatus.BAD_REQUEST);
    }

    public Connection dbConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
