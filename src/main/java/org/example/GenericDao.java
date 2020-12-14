package org.example;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao <T, ID, Integer> {
    ID save(T data) throws SQLException;
    List<T> findAll() throws SQLException;
    T findById(ID id) throws SQLException;
    void delete(ID id) throws SQLException;
    T update(T data) throws SQLException;
    List<T> findPostDatabase(Integer start, Integer end) throws SQLException;
    Integer countLines()throws SQLException;
}
