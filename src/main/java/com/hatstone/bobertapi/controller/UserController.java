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
    private final String dbUrl = "https://extendsclass.com/postgresql/07398d2";
    private final String dbUser = "";
    private final String dbPassword = "pls";

    @PostMapping("/create-user")
    public ResponseEntity<Long> CreateUser(@RequestBody User user){
        String insertQuery = "INSERT INTO users (displayName, firstName, lastName, email, password, admin) VALUES(?,?,?,?,?,?)";
        long id = 0;

        try {
            InternetAddress emailAddress = new InternetAddress(user.getEmail());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
        }
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getDisplayName());
                pstmt.setString(2, user.getFirstName());
                pstmt.setString(3, user.getLastName());
                pstmt.setString(4, user.getEmail());
                pstmt.setString(5, user.getPassword());
                pstmt.setBoolean(6, user.getAdmin());

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
    public ResponseEntity<User> GetUser(@RequestParam(value = "id") Long id){
        String selectQuery = "SELECT * FROM users WHERE id=?";
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = dbConnect();
                 PreparedStatement pstmt = conn.prepareStatement(selectQuery,
                         Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setLong(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    return new ResponseEntity<User>(HttpStatus.EXPECTATION_FAILED);
                }
                else {
                    String dn = rs.getString("displayName");
                    String fn = rs.getString ("firstName");
                    String ln = rs.getString ("lastName");
                    String email = rs.getString ("email");
                    String pw = rs.getString ("password");
                    Boolean admin = rs.getBoolean ("admin");
                    User foundUser = new User(dn, fn, ln, email, pw, admin);
                    return new ResponseEntity<User>(foundUser, HttpStatus.OK);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    public Connection dbConnect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}