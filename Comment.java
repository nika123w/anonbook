package com.anonbook.model;

import java.sql.Timestamp;

public class Comment {
    public int id;
    public int postId;
    public String content;
    public Timestamp createdAt;
}
