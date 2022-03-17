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
    public ResponseEntity<Long> CreateProblem(@RequestParam(value="title") String title){
        String insertQuery = "INSERT INTO contests(title) VALUES(?)";
        long id = 0;

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, title);

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

    @GetMapping("/get-contest")
    public ResponseEntity<Contest> GetProblem(@RequestParam(value = "id") Long id){
        String contestQuery = "SELECT * FROM contests WHERE id=?";
        String problemQuery = "SELECT * FROM problems WHERE contestid=?";
        String userQuery = "SELECT * FROM usercontestinteractions where contestid=?";

        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                    PreparedStatement c = conn.prepareStatement(contestQuery, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement p = conn.prepareStatement(problemQuery, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement u = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS)) {
                c.setLong(1, id);
                ResultSet c_rs = c.executeQuery();
                p.setLong(1, id);
                ResultSet p_rs = p.executeQuery();
                u.setLong(1, id);
                ResultSet u_rs = u.executeQuery();
                if (!c_rs.next() || !p_rs.next() || !u_rs.next()) {
                    return new ResponseEntity<Contest>(HttpStatus.EXPECTATION_FAILED);
                }
                else {
                    String title = c_rs.getString("title");

                    List<Long> problems = new ArrayList<Long>();
                    do{
                        problems.add(p_rs.getLong("contestId"));
                    } while(p_rs.next());

                    List<Long> users = new ArrayList<Long>();
                    do{
                        users.add(u_rs.getLong("userId"));
                    } while(u_rs.next());

                    Contest foundContest = new Contest(title, problems, users);
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
