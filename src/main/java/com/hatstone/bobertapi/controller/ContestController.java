package com.hatstone.bobertapi.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.hatstone.bobertapi.dto.Contest;
import com.hatstone.bobertapi.dto.Problem;
import com.hatstone.bobertapi.dto.User;
import com.hatstone.bobertapi.dto.ContestWithAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "bobert-api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ContestController {
    @Value("${database.url}")
    private String dbUrl;
    @Value("${database.username}")
    private String dbUser;
    @Value("${database.password}")
    private String dbPassword;

    @PostMapping("/create-contest")
    public ResponseEntity<Long> CreateContest(@RequestParam(value="title") String title, @RequestParam(value="uid") Long uid){
        String insertQuery = "INSERT INTO contests(title) VALUES(?)";
        String insertQuery2 = "INSERT INTO usercontestinteractions(userid, contestid, teamid) VALUES(?,?,?)";
        long id = 0;

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmt2 = conn.prepareStatement(insertQuery2, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, title);

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getLong(1);
                        }
                        pstmt2.setLong(1, uid);
                        pstmt2.setLong(2, id);
                        pstmt2.setLong(3, 2);
                        pstmt2.executeUpdate();
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

    @GetMapping("/get-usercontests")
    public ResponseEntity<List<ContestWithAdmin>> GetUserContests(@RequestParam(value = "id") Long id) {
    String contestQuery = "SELECT * FROM contests LEFT OUTER JOIN usercontestinteractions ON contests.id = usercontestinteractions.contestid WHERE id in (SELECT contestid FROM usercontestinteractions WHERE userid = ?)";

        try {
        Class.forName("org.postgresql.Driver");
        try (
            Connection conn = dbConnect();
            PreparedStatement c = conn.prepareStatement(contestQuery, Statement.RETURN_GENERATED_KEYS)

        ) {
            c.setLong(1, id);
            ResultSet c_rs = c.executeQuery();
            if (!c_rs.next()) {
                return new ResponseEntity<List<ContestWithAdmin>>(HttpStatus.EXPECTATION_FAILED);
            }
            ArrayList<ContestWithAdmin> contests = new ArrayList<ContestWithAdmin>();
            do {
                System.out.println(c_rs.toString());
                Long cid = c_rs.getLong(1);
                String title = c_rs.getString("title");

                Contest foundContest = new Contest(cid, title);
                ContestWithAdmin cadmin = new ContestWithAdmin(foundContest, c_rs.getLong("teamid") == 2);
                contests.add(cadmin);
            } while (c_rs.next());
            return new ResponseEntity<List<ContestWithAdmin>>(contests, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
        return new ResponseEntity<List<ContestWithAdmin>>(HttpStatus.BAD_REQUEST);
}


// TODO MAKE THING CORRECT WHEN PROBLEM TABLE IS FINALIZED //

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
                    System.out.println(p_rs.toString());
                    String description = p_rs.getString("description");
                    String title = p_rs.getString("title");
                    Long contestId  = p_rs.getLong("contestId");

                    Problem foundProblem = new Problem(title, description, contestId);
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

    @GetMapping("/get-contest")
    public ResponseEntity<Contest> GetContest(@RequestParam(value = "id") Long id){
        String contestQuery = "SELECT * FROM contests WHERE id=?";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect(); PreparedStatement c = conn.prepareStatement(contestQuery, Statement.RETURN_GENERATED_KEYS)) {
                c.setLong(1, id);
                ResultSet c_rs = c.executeQuery();
                if (!c_rs.next()) {
                    return new ResponseEntity<Contest>(HttpStatus.EXPECTATION_FAILED);
                }
                else {
                    Long cid = c_rs.getLong("id");
                    String title = c_rs.getString("title");

                    Contest foundContest = new Contest(cid, title);
                    return new ResponseEntity<Contest>(foundContest, HttpStatus.OK);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Contest>(HttpStatus.BAD_REQUEST);
    }

    public Connection dbConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
