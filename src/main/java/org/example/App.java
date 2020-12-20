package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class App {
    public static void main(String[] args) throws IOException, SQLException {

        AddPost addPost = new AddPost();
        addPost.addPost();
    }
}


