package repository.impl;

import entity.Loan;
import jakarta.persistence.EntityManager;
import repository.LoanRepository;

public class LoanRepositoryImpl implements LoanRepository {
    private final EntityManager entityManager;

    public LoanRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Loan registerLoan(Loan loan) {
       entityManager.getTransaction().begin();
       entityManager.persist(loan);
       entityManager.getTransaction().commit();
       return loan;
    }
}
