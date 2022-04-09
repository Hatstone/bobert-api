package com.hatstone.bobertapi.dto;

import java.util.ArrayList;
import java.util.List;

public class Contest {
    private Long id;
    private String title;

    // Constructors //
    public Contest(Long id, String title){
        this.id = id;
        this.title = title;
    }

    // Getters //

    public Long getId() { return id; }

    public String getTitle() {return title;}

    // Setters //

    public void setId(Long id) { this.id = id; }

    public void setTitle(String title) {this.title = title;}
}
