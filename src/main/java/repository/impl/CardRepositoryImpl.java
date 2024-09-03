package repository.impl;

import entity.Card;
import entity.Loan;
import entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import repository.CardRepository;

import java.util.List;

public class CardRepositoryImpl implements CardRepository {
    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    public CardRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;

    }

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    @Override
    public List<Card> selectAllStudentCard(Long stdId) {
        TypedQuery<Card> query = getEntityManager().createQuery(
                "select c from Loan l " +
                        "join l.card c " +
                        "where l.student.id = :studentId",
                Card.class
        );
        query.setParameter("studentId", stdId);
        return query.getResultList();
    }

    @Override
    public Card findCard(Card card) {
        TypedQuery<Card> query = entityManager.createQuery("select c from Card c where c.cardNumber=:cardNumber" +
                " and c.cvv2=:cvv2 and c.expireDate=:expireDate", Card.class);
        query.setParameter("cardNumber",card.getCardNumber());
        query.setParameter("cvv2",card.getCvv2());
        query.setParameter("expireDate",card.getExpireDate());
        List<Card> resultList = query.getResultList();
        if (resultList!=null) return resultList.get(0);
        return null;
    }
}
