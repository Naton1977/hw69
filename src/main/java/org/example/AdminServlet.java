package org.example;


import com.mysql.cj.xdevapi.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@MultipartConfig
@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    public static final String UPLOADS = "resources/uploads";
    private String postAuthor;
    private String publicationDate;
    private String postName;
    private String postTheme;
    private String postBody;
    private String draft;
    private String extension;
    int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/adminpage.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("image");

        if (req.getParameter("postAuthor") != null) {
            postAuthor = req.getParameter("postAuthor");
        }
        if (req.getParameter("postName") != null) {
            postName = req.getParameter("postName");
        }
        if (req.getParameter("postTheme") != null) {
            postTheme = req.getParameter("postTheme");
        }
        if (req.getParameter("post") != null) {
            postBody = req.getParameter("post");
        }

        if (req.getParameter("draft") != null) {
            draft = req.getParameter("draft");
        }
        if (req.getParameter("publicationDate") != null) {
            publicationDate = req.getParameter("publicationDate");
            if (publicationDate.length() < 3) {
                publicationDate = null;
            }
        }
        if (contentType(part)) {
            extension = addFileExtension(part);

            Post post = new Post(postAuthor, publicationDate, postName, postTheme, postBody, draft, extension);
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("application.xml");
            PostService bean = context.getBean(PostService.class);
            try {
                id = bean.save(post);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        try {
            saveFile(req, resp, id, extension, part);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void saveFile(HttpServletRequest req, HttpServletResponse resp, int id, String extension, Part part) throws SQLException, IOException, ServletException {
        String uploadsDirUrl = getServletContext().getRealPath(UPLOADS);
        String absolutePathToFile = uploadsDirUrl + "/" + id + "." + extension;
        part.write(absolutePathToFile);
    }

    public boolean contentType(Part part) {
        if (part == null) return false;
        String contentDisposition = part.getContentType();
        int end = contentDisposition.lastIndexOf("/");
        String img = contentDisposition.substring(0, end);
        if (img.equals("image")) {
            return true;
        }
        return false;
    }

    public String addFileExtension(Part part) throws IOException, ServletException {
        String contentDisposition = part.getHeader("Content-Disposition");
        int start = contentDisposition.indexOf("filename=");
        start += "filename=".length();
        int end = contentDisposition.lastIndexOf("\"");
        String filename = contentDisposition.substring(start + 1, end);
        int indexExpansion = filename.indexOf(".");
        return filename.substring(indexExpansion + 1);
    }

}
