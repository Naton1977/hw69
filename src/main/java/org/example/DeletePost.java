package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/delete")
public class DeletePost extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id;
        if (req.getParameter("delete") != null) {
            id = req.getParameter("delete");
            System.out.println(id);
            try {
                int idInt = Integer.parseInt(id);
                deletePost(idInt, req);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin");
        }


        if (req.getParameter("deletePost") != null) {
            try {
                selectPostsFromDataBase(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public void selectPostsFromDataBase(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Post> postsList;
        PostService bean = (PostService) req.getServletContext().getAttribute("bean");
        postsList = bean.findAll();
        req.setAttribute("Post", postsList);
        req.getRequestDispatcher("WEB-INF/pages/deletepost.jsp").forward(req, resp);
    }

    public void deletePost(int id, HttpServletRequest req) throws SQLException {
        PostService bean = (PostService) req.getServletContext().getAttribute("bean");
        bean.delete(id);
    }
}
