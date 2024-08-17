package util;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import menu.*;
import menu.util.Input;
import menu.util.Message;

import java.sql.Connection;

public class ApplicationContext {

    AuthHolder authHolder = new AuthHolder();
    private static ApplicationContext applicationContext ;
    private static Menu menu;
    private EntityManagerFactory emf;
    private EntityManager em;
    public static ApplicationContext getInstance() {
        if(applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }


    private EntityManagerFactory getEntityManagerFactory(){
        if(emf==null){
            emf= Persistence.createEntityManagerFactory("default");
        }
        return emf;
    }

    public EntityManager getEntityManager() {
        if (em == null) {
            em = getEntityManagerFactory().createEntityManager();
        }
        return em;
    }

    public ApplicationContext() {
        AuthHolder authHolder = new AuthHolder();
        Input input = new Input();
        Message message = new Message();
        SignupMenu signupMenu=new SignupMenu(input,message);
        RegisterLoanMenu registerLoanMenu=new RegisterLoanMenu(input,message);
        PaymentMenu paymentMenu=new PaymentMenu(input,message);
        LoginSubmenu loginSubmenu=new LoginSubmenu(input,message,registerLoanMenu,paymentMenu);
        LoginMenu loginMenu=new LoginMenu(input,message,loginSubmenu,authHolder);
        menu = new Menu(input,message,signupMenu,loginMenu);
    }


    public Menu getMenu() {
        return menu;
    }



}
