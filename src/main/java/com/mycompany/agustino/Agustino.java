package com.mycompany.agustino;

import com.mycompany.agustino.config.AppConfig;
import com.mycompany.agustino.dao.UserDAO;
import com.mycompany.agustino.model.User;
import javax.swing.SwingUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Agustino {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserDAO userDAO = context.getBean(UserDAO.class);

        if (userDAO.countUsers() == 0) {
            userDAO.save(new User("owner", "admin123", User.Role.OWNER));
            userDAO.save(new User("worker", "worker123", User.Role.WORKER));
        }

        SwingUtilities.invokeLater(() -> {
            new LoginForm(context).setVisible(true);
        });
    }
}