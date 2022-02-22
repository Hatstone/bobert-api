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
    private final String dbUrl = "https://extendsclass.com/postgresql/07398d2";
    private final String dbProblem = "";
    private final String dbPassword = "pls";

    @PostMapping("/create-problem")
    public ResponseEntity<Long> CreateProblem(@RequestBody Problem problem){
        String insertQuery = "INSERT INTO problems (language, sourceCode, inputCode, timeLimit, memoryLimit, status) VALUES(?,?,?,?,?,?)";
        long id = 0;

//        try {
//            InternetAddress emailAddress = new InternetAddress(problem.getEmail());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
//        }
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, problem.getLanguage());
                pstmt.setString(2, problem.getSourceCode());
                pstmt.setString(3, problem.getInputCode());
                pstmt.setInt(4, problem.getTimeLimit());
                pstmt.setInt(5, problem.getMemoryLimit());
                pstmt.setString(6, problem.getStatus());

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
                    String lang = rs.getString("language");
                    String sc = rs.getString ("sourceCode");
                    String ic = rs.getString ("inputCode");
                    int tl = rs.getInt ("timeLimit");
                    int ml = rs.getInt ("memoryLimit");
                    String status = rs.getString ("status");
                    Problem foundProblem = new Problem(lang, sc, ic, tl, ml, status);
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
        return DriverManager.getConnection(dbUrl, dbProblem, dbPassword);
    }
}