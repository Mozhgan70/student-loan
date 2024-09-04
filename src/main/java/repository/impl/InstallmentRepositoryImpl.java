package repository.impl;

import entity.Card;
import entity.Installment;
import entity.Loan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repository.InstallmentRepository;

import java.util.List;
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

    @Override
    public List<Installment> getInstallmentsByLoanIdAndPaidStatus(Long loanId,Boolean paidStatus) {
        try{
        String hql = "SELECT i FROM Installment i WHERE i.loan.id = :loanId and i.isPaid=:paidStatus order by i.installmentNumber";
        List<Installment> resultList = getEntityManager().createQuery(hql, Installment.class)
                .setParameter("loanId", loanId)
                .setParameter("paidStatus", paidStatus)
                .getResultList();
        if(resultList!=null && resultList.size()>0) {
            return resultList;
        }
        return null;}
        catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return null;
    }
    }

    @Override
    public void installmentPayment(Installment installment,Card card) {
        try{
        entityManager.getTransaction().begin();
        entityManager.merge(installment);
        entityManager.merge(card);
        entityManager.getTransaction().commit();
    } catch (Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

    }
}
}
