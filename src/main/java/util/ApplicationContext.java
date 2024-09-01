package util;


import dto.mapStruct.StudentMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import menu.*;
import menu.util.Input;
import menu.util.Message;
import repository.*;
import repository.impl.*;
import service.*;
import service.impl.*;

import org.mapstruct.factory.Mappers;

public class ApplicationContext {



    private static ApplicationContext applicationContext ;
    private static Menu menu;
    private EntityManagerFactory emf;
    private EntityManager em;
    private StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

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
        UserSession userSession = new UserSession();
        Input input = new Input();
        Message message = new Message();
        Common common=new Common(input,message);
        StudentRepository studentRepository=new StudentRepositoryImpl(getEntityManager());
        LoanTypeConditionRepository loanTypeConditionRepository=new LoanTypeConditionRepositoryImpl(getEntityManager());
        LoanRepository loanRepository=new LoanRepositoryImpl(getEntityManager());
        InstallmentRepository installmentRepository=new InstallmentRepositoryImpl(getEntityManager());
        SpouseRepository spouseRepository=new SpouseRepositoryImpl(getEntityManager());

        InstallmentService installmentService=new InstallmentServiceImpl(installmentRepository);
        StudentService studentService=new  StudentServiceImpl(studentRepository,studentMapper, userSession);
        LoanTypeConditionService loanTypeConditionService=new LoanTypeConditionServiceImpl(loanTypeConditionRepository);
        LoanService loanService=new LoanServiceImpl(loanRepository,userSession,input,installmentService);

        SpouseService spouseService=new SpouseSeviceImpl(spouseRepository);



        SignupMenu signupMenu=new SignupMenu(input,message,studentService,common);
        RegisterLoanMenu registerLoanMenu=new RegisterLoanMenu(input,message,userSession,loanTypeConditionService
                ,studentService,common,loanService,installmentService,spouseService);
        PaymentMenu paymentMenu=new PaymentMenu(input,message);
        LoginSubmenu loginSubmenu=new LoginSubmenu(input,message,registerLoanMenu,paymentMenu);
        LoginMenu loginMenu=new LoginMenu(input,message,loginSubmenu, userSession,studentService);
        menu = new Menu(input,message,signupMenu,loginMenu);
    }


    public Menu getMenu() {
        return menu;
    }



}
