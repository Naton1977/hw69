package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Methods {

    public void createPost() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String postAuthor;
        String publicationDate;
        String postName;
        String postTheme;
        String postBody;
        String draft;


        System.out.println("Введите фамилию и имя автора поста");
        postAuthor = scanner.nextLine();
        System.out.println("Введите дату публикации поста (По умолчанию будет установленна текущая дата)");
        publicationDate = scanner.nextLine();
        System.out.println("Введите название поста");
        postName = scanner.nextLine();
        System.out.println("Введите тему поста");
        postTheme = scanner.nextLine();
        System.out.println("Введите содержание поста");
        postBody = scanner.nextLine();
        System.out.println("Укажите это черновик или нет (да, нет)");
        draft = scanner.nextLine();

        Post post = new Post(postAuthor, publicationDate, postName, postTheme, postBody, draft);
        SecurityContext securityContext = SecurityContext.getInstance();
        PostService bean = securityContext.addBean();
        bean.save(post);

    }

    public void selectAll() throws SQLException {
        SecurityContext securityContext = SecurityContext.getInstance();
        PostService bean = securityContext.addBean();
        List<Post> postList = bean.findAll();
        System.out.printf("|%-10s|%-30s|%-30s|%-30s|%-40s|%-70s|%-10s", "Id поста", "Автор поста", "Дата публикации", "Название поста", "Тема поста", "Пост", "Черновик");
        System.out.println();
        for (int i = 0; i < postList.size(); i++) {
            Post post = postList.get(i);
            System.out.printf("|%-10s|%-30s|%-30s|%-30s|%-40s|%-70s|%-10s", post.getId(), post.getPostAuthor(), post.getPublicationDate(), post.getPostName(), post.getPostTheme(), post.getPostBody(), post.getDraft());
            System.out.println();
        }
    }

    public void deletePost() throws SQLException {
        SecurityContext securityContext = SecurityContext.getInstance();
        PostService bean = securityContext.addBean();
        Scanner scanner = new Scanner(System.in);
        selectAll();

        int idInt = 0;
        while (true) {
            System.out.println("Введите id поста который нужно удалить");
            String id = scanner.nextLine();
            try {
                idInt = Integer.parseInt(id);
                bean.delete(idInt);
                break;
            } catch (Exception e) {
                System.out.println("Введите правильно id");
            }
        }
    }

    public void setPost() throws SQLException {
        String postAuthor;
        String publicationDate;
        String postName;
        String postTheme;
        String postBody;
        String draft;
        String idSt;
        int id;
        Scanner scanner = new Scanner(System.in);
        selectAll();
        while (true) {
            System.out.println("Введите id поста который нужно обновить");
            idSt = scanner.nextLine();
            try {
                id = Integer.parseInt(idSt);
                break;
            } catch (Exception e) {
                System.out.println("Введите id поста правильно");
            }
        }
        System.out.println("Введите имя автора поста");
        postAuthor = scanner.nextLine();
        System.out.println("Введите дату публикации поста");
        publicationDate = scanner.nextLine();
        System.out.println("Введите название поста");
        postName = scanner.nextLine();
        System.out.println("Введите тему поста");
        postTheme = scanner.nextLine();
        System.out.println("Введите пост");
        postBody = scanner.nextLine();
        System.out.println("Введите это черновик или нет");
        draft = scanner.nextLine();
        if (publicationDate.equals("")) {
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            publicationDate = formatForDateNow.format(dateNow);
        }
        Post post = new Post(postAuthor, publicationDate, postName, postTheme, postBody, draft);
        post.setId(id);
        SecurityContext securityContext = SecurityContext.getInstance();
        PostService bean = securityContext.addBean();
        bean.update(post);
    }

    public void findPostById() throws SQLException {
        SecurityContext securityContext = SecurityContext.getInstance();
        PostService bean = securityContext.addBean();
        Post post;
        Scanner scanner = new Scanner(System.in);
        int idInt = 0;
        String id;
        selectAll();
        while (true) {
            System.out.println("Введите id поста который нужно распечатать");
            id = scanner.nextLine();
            try {
                idInt = Integer.parseInt(id);
                post = bean.findById(idInt);
                break;
            } catch (Exception e) {
                System.out.println("Введите id правильно ");
            }
        }
        System.out.printf("|%-10s|%-30s|%-30s|%-30s|%-40s|%-70s|%-10s", "Id поста", "Автор поста", "Дата публикации", "Название поста", "Тема поста", "Пост", "Черновик");
        System.out.println();
        System.out.printf("|%-10s|%-30s|%-30s|%-30s|%-40s|%-70s|%-10s", post.getId(), post.getPostAuthor(), post.getPublicationDate(), post.getPostName(), post.getPostTheme(), post.getPostBody(), post.getDraft());
        System.out.println();
    }

}
