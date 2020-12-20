package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostDaoMySqlImpl implements PostDao {

    @Override
    public Integer save(Post data) throws SQLException {
        Post post = data;
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        if (post.getPublicationDate() == null) {
            try {
                statement.executeUpdate("insert into posts (postAuthor, postName, postTheme, post, draft, extension) value ('" + post.getPostAuthor() + "', '" + post.getPostName() + "', '" + post.getPostTheme() + "', '" + post.getPostBody() + "', '" + post.getDraft() + "', '" + post.getExtension() + "');");
                connection.setAutoCommit(true);
            } catch (Exception exception) {
                connection.rollback();
                exception.printStackTrace();
            }
        } else {
            try {
                statement.executeUpdate("insert into posts (postAuthor, publicationDate ,postName, postTheme, post, draft, extension) value ('" + post.getPostAuthor() + "', '" + post.getPublicationDate() + "','" + post.getPostName() + "', '" + post.getPostTheme() + "', '" + post.getPostBody() + "', '" + post.getDraft() + "', '" + post.getExtension() + "');");
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
            String extension = resultSet.getString("extension");
            int id = resultSet.getInt("id");
            Post post1 = new Post(postAuthor, publicationDate, postName, postTheme, postBody, draft, extension);
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
            String extension = resultSet.getString("extension");
            int id = resultSet.getInt("id");
            post = new Post(postAuthor, publicationDate, postName, postTheme, postBody, draft, extension);
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
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Post update(Post data) throws SQLException {
        Post post = data;
        int id = 0;
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from posts where postAuthor = '" + post.getPostAuthor() + "' and publicationDate = '" + post.getPublicationDate() + "' and postName = '" + post.getPostName() + "' and postTheme ='" + post.getPostTheme() + "' and post = '" + post.getPostBody() + "' and draft = '" + post.getDraft() + "' and extension = '" + post.getExtension() + "' ;");
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return post;
    }

    public List<Post> findPostDatabase(Integer startIndex, Integer endIndex) throws SQLException {
        List<Post> postList = new ArrayList<>();
        int startPosition = startIndex;
        int postPosition = endIndex;
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from posts where draft = 'no' limit " + startPosition + ", " + postPosition + ";");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String postAuthor = resultSet.getString("postAuthor");
            String publicationDate = resultSet.getString("publicationDate");
            String postName = resultSet.getString("postName");
            String postTheme = resultSet.getString("postTheme");
            String extension = resultSet.getString("extension");
            String postBody = null;
            String draft = null;
            Post post = new Post(postAuthor, publicationDate, postName, postTheme, postBody, draft, extension);
            post.setId(id);
            postList.add(post);
        }
        return postList;
    }

    @Override
    public Integer countLines() throws SQLException {
        int countLines = 0;
        SecurityContext securityContext = SecurityContext.getInstance();
        Connection connection = securityContext.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from posts where draft = 'no';");
        if (resultSet.next()) {
            countLines = resultSet.getInt(1);
        }
        return countLines;
    }

}
