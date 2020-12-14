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

@WebServlet(urlPatterns = "/post")
public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            addFullPost(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addFullPost(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String id = "";
        int idInt = 0;
        ;
        if (req.getParameter("fullPost") != null) {
            id = req.getParameter("fullPost");
        }
        try {
            idInt = Integer.parseInt(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        PostService bean = context.getBean(PostService.class);
        Post post = bean.findById(idInt);
        req.setAttribute("postAuthor", post.getPostAuthor());
        req.setAttribute("publicationDate", post.getPublicationDate());
        req.setAttribute("postName", post.getPostName());
        req.setAttribute("postTheme", post.getPostTheme());
        req.setAttribute("post", post.getPostBody());
        req.setAttribute("idImg", idInt);
        req.setAttribute("extension", post.getExtension());
        req.getRequestDispatcher("/WEB-INF/pages/post.jsp").forward(req, resp);
    }
}

