package org.example;

import java.sql.SQLException;
import java.util.List;

public interface PostDao extends GenericDao<Post, Integer>{
    List<Post> findPostDatabase(Integer start, Integer end) throws SQLException;
    Integer countLines()throws SQLException;
}
