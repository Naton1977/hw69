package org.example;

import java.sql.SQLException;
import java.util.List;

public class PostService implements PostDao {
    PostDao service;

    public PostDao getService() {
        return service;
    }

    public void setService(PostDao service) {
        this.service = service;
    }

    @Override
    public Integer save(Post data) throws SQLException {
        return (Integer) service.save(data);
    }

    @Override
    public List<Post> findAll() throws SQLException {
        return service.findAll();
    }

    @Override
    public Post findById(Integer integer) throws SQLException {
        return (Post) service.findById(integer);
    }

    @Override
    public void delete(Integer integer) throws SQLException {
        service.delete(integer);
    }

    @Override
    public Post update(Post data) throws SQLException {
        return (Post) service.update(data);
    }

}
