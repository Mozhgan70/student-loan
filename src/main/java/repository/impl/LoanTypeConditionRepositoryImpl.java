package repository.impl;

import entity.LoanTypeCondition;
import entity.Student;
import jakarta.persistence.EntityManager;
import repository.LoanTypeConditionRepository;

public class LoanTypeConditionRepositoryImpl implements LoanTypeConditionRepository {
    private final EntityManager entityManager;

    public LoanTypeConditionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public LoanTypeCondition findByEducationandLoanType(String education, String loanType) {
        String hql = "SELECT s FROM LoanTypeCondition s WHERE s.educationGrade = :education and s.loanType = :loanType"  ;
        return entityManager.createQuery(hql, LoanTypeCondition.class)
                .setParameter("education", education)
                .setParameter("loanType", loanType)
                .getSingleResult();
    }
}
