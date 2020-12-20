package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDaoMySqlImpl implements PostDao {

    @Override
    public Integer save(Post data) throws SQLException {
        Post post = data;
        if (post.getPublicationDate().equals("")) {
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            data.setPublicationDate(formatForDateNow.format(dateNow));
        }
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        if (post.getPublicationDate() == null) {
            try {
                statement.executeUpdate("insert into posts (postAuthor, postName, postTheme, post, draft) value ('" + post.getPostAuthor() + "', '" + post.getPostName() + "', '" + post.getPostTheme() + "', '" + post.getPostBody() + "', '" + post.getDraft() + "');");
                connection.setAutoCommit(true);
                System.out.println("Пост успешно добавлен !!!");
            } catch (Exception exception) {
                connection.rollback();
                exception.printStackTrace();
            }
        } else {
            try {
                statement.executeUpdate("insert into posts (postAuthor, publicationDate ,postName, postTheme, post, draft) value ('" + post.getPostAuthor() + "', '" + post.getPublicationDate() + "','" + post.getPostName() + "', '" + post.getPostTheme() + "', '" + post.getPostBody() + "', '" + post.getDraft() + "');");
                connection.setAutoCommit(true);
            } catch (Exception exception) {
                connection.rollback();
                exception.printStackTrace();
            }
        }
        ResultSet resultSet = statement.executeQuery("select id from posts order by id desc limit 1;");
        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    @Override
    public List<Post> findAll() throws SQLException {
        List<Post> list = new ArrayList<>();
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from posts;");
        while (resultSet.next()) {
            String postAuthor = resultSet.getString("postAuthor");
            String publicationDate = resultSet.getString("publicationDate");
            String postName = resultSet.getString("postName");
            String postTheme = resultSet.getString("postTheme");
            String postBody = resultSet.getString("post");
            String draft = resultSet.getString("draft");
            int id = resultSet.getInt("id");
            Post post1 = new Post(postAuthor, publicationDate, postName, postTheme, postBody, draft);
            post1.setId(id);
            list.add(post1);
        }
        return list;
    }

    @Override
    public Post findById(Integer integer) throws SQLException {
        Post post = null;
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from posts where  id = '" + integer + "';");
        while (resultSet.next()) {
            String postAuthor = resultSet.getString("postAuthor");
            String publicationDate = resultSet.getString("publicationDate");
            String postName = resultSet.getString("postName");
            String postTheme = resultSet.getString("postTheme");
            String postBody = resultSet.getString("post");
            String draft = resultSet.getString("draft");
            int id = resultSet.getInt("id");
            post = new Post(postAuthor, publicationDate, postName, postTheme, postBody, draft);
            post.setId(id);
        }
        return post;
    }

    @Override
    public void delete(Integer integer) throws SQLException {
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        try {
            statement.executeUpdate("delete from posts where id = " + integer + ";");
            System.out.println("Пост успешно удален !!!");
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Post update(Post data) throws SQLException {
        Post post = data;
        int id = post.getId();
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        try {
            statement.executeUpdate("update posts set postAuthor = '" + post.getPostAuthor() + "', publicationDate = '" + post.getPublicationDate() + "', postName = '" + post.getPostName() + "', postTheme = '" + post.getPostTheme() + "', post = '" + post.getPostBody() + "', draft = '" + post.getDraft() + "' where id = " + id + ";");
            System.out.println("Данные поста успешно обновленны !!!");
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        return post;
    }

}
