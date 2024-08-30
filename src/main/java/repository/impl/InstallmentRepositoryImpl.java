package repository.impl;

import entity.Installment;
import entity.Loan;
import jakarta.persistence.EntityManager;
import repository.InstallmentRepository;

import java.util.Set;

public class InstallmentRepositoryImpl implements InstallmentRepository {
    private final EntityManager entityManager;

    public InstallmentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void setInstallment(Set<Installment> installments) {
        try {
            entityManager.getTransaction().begin();
            for (Installment installment : installments) {
                entityManager.persist(installment);

            }
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            if (entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {

           // entityManager.close();
        }
    }



}
