package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PostListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");
        PostService bean = context.getBean(PostService.class);
        servletContextEvent.getServletContext().setAttribute("bean", bean);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
