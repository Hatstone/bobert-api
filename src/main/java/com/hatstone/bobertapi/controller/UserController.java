package com.hatstone.bobertapi.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
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
public class UserController {
    @Value("${database.url}")
    private String dbUrl;
    @Value("${database.username}")
    private String dbUser;
    @Value("${database.password}")
    private String dbPassword;

    @PostMapping("/create-user")
    public ResponseEntity<Long> CreateUser(@RequestParam(value="email") String email){
        String insertQuery = "INSERT INTO users (email, admin) VALUES(?,?)";
        long id = 0;

        try {
            InternetAddress emailAddress = new InternetAddress(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
        }
        try {
            Class.forName("org.postgresql.Driver");
            try (
                Connection conn = dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ) {
                pstmt.setString(1, email);
                pstmt.setBoolean(2, false);

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


    @GetMapping("/get-user")
    public ResponseEntity<Long> GetUser(@RequestParam(value = "email") String email){
        String selectQuery = "SELECT * FROM users WHERE email=?";
        try {
            Class.forName("org.postgresql.Driver");
            try (
                Connection conn = dbConnect();
                PreparedStatement pstmt = conn.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);
            ) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    return new ResponseEntity<Long>(HttpStatus.EXPECTATION_FAILED);
                }
                else {
                    Long id = rs.getLong("id");
                    return new ResponseEntity<Long>(id, HttpStatus.OK);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
    }

    public Connection dbConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
