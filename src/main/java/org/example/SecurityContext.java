package org.example;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SecurityContext {
    private static SecurityContext instance;
    private String URL = "jdbc:mysql://localhost/postlibrary";
    private String UserSQL = "root";
    private String PasswordSQL = "";


    private SecurityContext() {

    }

    public static SecurityContext getInstance() {
        if (instance == null) {
            instance = new SecurityContext();
        }
        return instance;
    }


    public Connection connection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, UserSQL, PasswordSQL);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }
    public PostService addBean(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");
        PostService bean = context.getBean(PostService.class);
        return bean;
    }

}
