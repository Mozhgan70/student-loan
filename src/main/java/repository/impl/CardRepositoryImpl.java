package repository.impl;

import entity.Card;
import entity.Installment;
import entity.Loan;
import entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import repository.CardRepository;

import java.util.List;

public class CardRepositoryImpl implements CardRepository {
    private final EntityManager entityManager;
    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;


    }



    @Override
    public List<Card> selectAllStudentCard(Long stdId) {
        TypedQuery<Card> query = entityManager.createQuery(
                "select c from Loan l " +
                        "join l.card c " +
                        "where l.student.id = :studentId",
                Card.class
        );
        query.setParameter("studentId", stdId);
        return query.getResultList();
    }

    @Override
    public Card findCardRelatedLoan(Installment installment) {
//        TypedQuery<Card> query = getEntityManager().createQuery("select c from Card c where c.cardNumber=:cardNumber" +
//                " and c.cvv2=:cvv2 and c.expireDate=:expireDate", Card.class);
//        query.setParameter("cardNumber",card.getCardNumber());
//        query.setParameter("cvv2",card.getCvv2());
//        query.setParameter("expireDate",card.getExpireDate());
//        List<Card> resultList = query.getResultList();
//        if (resultList!=null && !resultList.isEmpty()) return resultList.get(0);
//        return null;

            String jpql = "SELECT c FROM Card c " +
                    "JOIN c.loan l " +
                    "JOIN l.installments i " +
                    "WHERE l.id = :loanId";

            return entityManager.createQuery(jpql, Card.class)
                    .setParameter("loanId", installment.getLoan().getId())
                    .getSingleResult();

    }


    public void saveOrUpdateCard(Card card) {


        try {
            entityManager.getTransaction().begin();
            entityManager.merge(card);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

}
