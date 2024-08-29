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

            int batchSize = 20;
            int count = 0;
            for (Installment installment : installments) {
//                Loan mergeLoan = entityManager.merge(installment.getLoan());
//                installment.setLoan(mergeLoan);
                entityManager.persist(installment);

//                if (++count % batchSize == 0) {
//                    entityManager.flush();
//                    entityManager.clear();
//                }

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
