package repository.impl;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repository.InstallmentRepository;

import java.util.List;
import java.util.Set;

public class InstallmentRepositoryImpl implements InstallmentRepository {

    private final EntityManager entityManager;
    public InstallmentRepositoryImpl(EntityManager entityManager) {


        this.entityManager = entityManager;
    }


//    @Override
//    public void setInstallment(Set<Installment> installments,Card card,Loan loan) {
//        Spouse spouse=installments.stream().findFirst().get().getLoan().getStudent().getSpouse();
//        Student student=installments.stream().findFirst().get().getLoan().getStudent();
//        try {
//            getEntityManager().getTransaction().begin();
//            if (card.getId() != null) {
//
//                Card cardmerge = getEntityManager().merge(card);
//                loan.setCard(cardmerge);
//            } else {
//
//                getEntityManager().persist(card);
//                loan.setCard(card);
//            }
//            getEntityManager().persist(spouse);
//            student.setSpouse(spouse);
//            getEntityManager().persist(student);
//            getEntityManager().persist(loan);
//
//
//            for (Installment installment : installments) {
//                installment.setLoan(loan);
//                getEntityManager().persist(installment);
//
//
//            }
//            getEntityManager().getTransaction().commit();
//        }
//        catch (Exception e) {
//            if (getEntityManager().getTransaction() != null) {
//                getEntityManager().getTransaction().rollback();
//            }
//            e.printStackTrace();
//        }
//    }

    @Override
    public void setInstallment(Set<Installment> installments) {
        try {
            entityManager.getTransaction().begin();
          //  entityManager.merge(installments.stream().findFirst().get().getLoan().getStudent());
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
        }
//        finally {
//
//            getEntityManager().close();
//        }

    }

    @Override
    public List<Installment> getInstallmentsByLoanIdAndPaidStatus(Long loanId,Boolean paidStatus) {
        try{
        String hql = "SELECT i FROM Installment i WHERE i.loan.id = :loanId and i.isPaid=:paidStatus order by i.installmentNumber";
        List<Installment> resultList = entityManager.createQuery(hql, Installment.class)
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
