package repository.impl;

import entity.Installment;
import entity.Loan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repository.InstallmentRepository;

import java.util.Set;

public class InstallmentRepositoryImpl implements InstallmentRepository {
    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    public InstallmentRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;

    }
    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    @Override
    public void setInstallment(Set<Installment> installments) {
        try {
            getEntityManager().getTransaction().begin();
            for (Installment installment : installments) {
                getEntityManager().persist(installment);

            }
            getEntityManager().getTransaction().commit();
        }
        catch (Exception e) {
            if (getEntityManager().getTransaction() != null) {
                getEntityManager().getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {

            getEntityManager().close();
        }
    }



}
