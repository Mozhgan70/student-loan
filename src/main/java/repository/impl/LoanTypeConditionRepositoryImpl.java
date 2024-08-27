package repository.impl;

import entity.LoanTypeCondition;
import entity.Student;
import entity.enumration.EducationGrade;
import entity.enumration.LoanType;
import jakarta.persistence.EntityManager;
import repository.LoanTypeConditionRepository;

public class LoanTypeConditionRepositoryImpl implements LoanTypeConditionRepository {
    private final EntityManager entityManager;

    public LoanTypeConditionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public LoanTypeCondition findByEducationandLoanType(EducationGrade education, LoanType loanType) {
        String query = "SELECT s FROM LoanTypeCondition s WHERE s.educationGrade = :education and s.loanType = :loanType"  ;
        return entityManager.createQuery(query, LoanTypeCondition.class)
                .setParameter("education", education)
                .setParameter("loanType", loanType)
                .getSingleResult();
    }
}
