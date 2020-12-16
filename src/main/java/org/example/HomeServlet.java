package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet(urlPatterns = "/")
public class HomeServlet extends HttpServlet {
    private String URL;
    private String UserSQL;
    private String PasswordSQL;
    private int postId = 0;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        URL = servletContext.getInitParameter("URL");
        UserSQL = servletContext.getInitParameter("UserSQL");
        PasswordSQL = servletContext.getInitParameter("PasswordSQL");
        SecurityContext securityContext = SecurityContext.getInstance();
        securityContext.initSQl(URL, UserSQL, PasswordSQL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            selectPostsFromDataBase(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectPostsFromDataBase(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        PostService bean = (PostService) req.getServletContext().getAttribute("bean");
        int startPosition = 0;
        int postPosition = 0;
        int countLines = bean.countLines();
        if (countLines > 0) {
            postPosition = 3;
        }
        int position = 0;
        if (req.getParameter("postNewer") != null) {
            String postNever = req.getParameter("postNewer");
            try {
                position = Integer.parseInt(postNever);
            } catch (Exception e) {
                e.printStackTrace();
            }
            startPosition += position;
            postPosition += position;
            if (postPosition > countLines) {
                postPosition = countLines;
            }

        }

        if (req.getParameter("postOlder") != null) {
            String postNever = req.getParameter("postOlder");
            try {
                position = Integer.parseInt(postNever);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int temp = position;
            if (temp % 3 == 1) {
                postPosition = position - 1;
                startPosition = position - 4;
            }
            if (temp % 3 == 2) {
                postPosition = position - 2;
                startPosition = position - 5;
            }
            if (temp % 3 == 0) {
                postPosition = position - 3;
                startPosition = position - 6;
            }
        }

        List<Post> postList = bean.findPostDatabase(startPosition, postPosition);
        for (int i = 0; i < postList.size(); i++) {
            Post post = postList.get(i);
            req.setAttribute("id" + (i + 1) + "", post.getId());
            req.setAttribute("postAuthor" + (i + 1) + "", post.getPostAuthor());
            req.setAttribute("publicationDate" + (i + 1) + "", post.getPublicationDate());
            req.setAttribute("postName" + (i + 1) + "", post.getPostName());
            req.setAttribute("postTheme" + (i + 1) + "", post.getPostTheme());
            req.setAttribute("extension" + (i + 1) + "", post.getExtension());

        }

        req.setAttribute("startPosition", startPosition);
        req.setAttribute("countLines", countLines);
        req.setAttribute("postPosition", postPosition);
        req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
    }
}















