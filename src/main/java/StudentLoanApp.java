import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.ApplicationContext;

public class StudentLoanApp {


    public static void main(String[] args) {
//        EntityManagerFactory emf= Persistence.createEntityManagerFactory("default");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.getTransaction().commit();
        ApplicationContext.getInstance().getMenu().show();

    }
}
