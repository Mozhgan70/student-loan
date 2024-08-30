package repository.impl;

import entity.LoanTypeCondition;
import entity.Student;
import entity.enumration.City;
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
    public LoanTypeCondition findByEducationandLoanType(EducationGrade education, LoanType loanType,City city) {
        String query = "SELECT s FROM LoanTypeCondition s WHERE s.educationGrade = :education " +
                "And s.loanType = :loanType " +
                "And (:city IS NULL OR s.city = :city)"  ;

        return entityManager.createQuery(query, LoanTypeCondition.class)
                .setParameter("education", education)
                .setParameter("loanType", loanType)
                .setParameter("city",city.getCityName())
                .getSingleResult();
    }
}
