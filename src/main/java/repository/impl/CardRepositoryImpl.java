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
}
