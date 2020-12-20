package org.example;

import java.io.IOException;
import java.sql.SQLException;

public class AddPost {

    public void addPost() throws IOException, SQLException {

        Menu menu = new Menu("Главное меню");

        Menu menu1 = new Menu("Добавить пост в базу данных", context -> {
            Methods methods = new Methods();
            methods.createPost();
        });
        Menu menu2 = new Menu("Распечатать список постов", context -> {
            Methods methods = new Methods();
            methods.selectAll();
        });
        Menu menu3 = new Menu("Удалить пост из базы данных", context -> {
            Methods methods = new Methods();
            methods.deletePost();
        });
        Menu menu4 = new Menu("Редактировать пост", context -> {
            Methods methods = new Methods();
            methods.setPost();
        });

        Menu menu5 = new Menu("Распечатать пост", context -> {
            Methods methods = new Methods();
            methods.findPostById();
        });

        menu.addSubMenu(menu1);
        menu.addSubMenu(menu2);
        menu.addSubMenu(menu3);
        menu.addSubMenu(menu4);
        menu.addSubMenu(menu5);

        do {
            menu.print();
        } while (!menu.action());
    }
}
