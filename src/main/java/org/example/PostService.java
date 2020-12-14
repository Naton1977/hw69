package org.example;

import java.sql.SQLException;
import java.util.List;

public class PostService implements GenericDao<Post, Integer, Integer> {
    GenericDao service;

    public GenericDao getService() {
        return service;
    }

    public void setService(GenericDao service) {
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

    @Override
    public List<Post> findPostDatabase(Integer start, Integer end) throws SQLException {
        return service.findPostDatabase(start, end);
    }

    @Override
    public Integer countLines() throws SQLException {
        return (Integer) service.countLines();
    }
}
