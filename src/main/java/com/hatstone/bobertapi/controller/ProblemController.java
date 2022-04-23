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
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.hatstone.bobertapi.dto.Problem;
import com.hatstone.bobertapi.dto.ProblemWithTestCases;
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
    public ResponseEntity<Long> CreateProblem(@RequestBody ProblemWithTestCases problem){
        String insertQuery = "INSERT INTO problems (contestid, title, description) VALUES(?,?,?)";
        // question marks are missing from this next query because the number of them
        // is dynamic and they're added below
        String insertTestCaseQuery = "INSERT INTO testcases (inputargs, expectedoutput, problemid) VALUES";
        long id = 0;

        try {
            Class.forName("org.postgresql.Driver");
            try (
                Connection conn = dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ) {
                pstmt.setLong(1, problem.getProblem().getContestId());
                pstmt.setString(2, problem.getProblem().getTitle());
                pstmt.setString(3, problem.getProblem().getDescription());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getLong(1);
                            if(problem.getTestCases().size() != problem.getTestCaseOutcomes().size()){
                                return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
                            }
                            for(int i = 0; i < problem.getTestCases().size(); i++){
                                insertTestCaseQuery += "(?,?,?),";
                            }
                            insertTestCaseQuery = insertTestCaseQuery.substring(0, insertTestCaseQuery.length() - 1);
                            PreparedStatement pstmt2 = conn.prepareStatement(insertTestCaseQuery, Statement.RETURN_GENERATED_KEYS);
                            int indexCounter = 1;
                            List<String> testCases = problem.getTestCases();
                            List<String> testCaseOutcomes = problem.getTestCaseOutcomes();
                            for(int i = 0; i < testCases.size(); i++){
                                pstmt2.setString(indexCounter, testCases.get(i));
                                pstmt2.setString(indexCounter + 1, testCaseOutcomes.get(i));
                                pstmt2.setLong(indexCounter + 2, id);
                                indexCounter += 3;
                            }
                            pstmt2.executeUpdate();
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
                    Long pid = rs.getLong("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    Long cid = rs.getLong("contestId");
                    Problem foundProblem = new Problem(pid, title, description, cid);
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

    @GetMapping("/get-contestproblems")
    public ResponseEntity<List<Problem>> GetContestProblems(@RequestParam(value = "id") Long id) {
        String contestQuery = "SELECT * FROM problems WHERE contestid = ?";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect(); PreparedStatement c = conn.prepareStatement(contestQuery, Statement.RETURN_GENERATED_KEYS)) {
                c.setLong(1, id);
                ResultSet p_rs = c.executeQuery();
                if (!p_rs.next()) {
                    return new ResponseEntity<List<Problem>>(HttpStatus.EXPECTATION_FAILED);
                }
                ArrayList<Problem> problems = new ArrayList<Problem>();
                do {
                    Long pid = p_rs.getLong("id");
                    String description = p_rs.getString("description");
                    String title = p_rs.getString("title");
                    Long contestId  = p_rs.getLong("contestId");

                    Problem foundProblem = new Problem(pid, title, description, contestId);
                    problems.add(foundProblem);
                } while (p_rs.next());
                return new ResponseEntity<List<Problem>>(problems, HttpStatus.OK);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<List<Problem>>(HttpStatus.BAD_REQUEST);
    }

    public Connection dbConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
