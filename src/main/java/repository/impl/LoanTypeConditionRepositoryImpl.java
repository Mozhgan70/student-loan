package repository.impl;

import entity.Loan;
import entity.LoanTypeCondition;
import entity.Student;
import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.LoanType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import repository.LoanTypeConditionRepository;

import java.util.List;

public class LoanTypeConditionRepositoryImpl implements LoanTypeConditionRepository {
    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public LoanTypeConditionRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;

    }

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }


    @Override
    public LoanTypeCondition findByEducationandLoanType(EducationGrade education, LoanType loanType,City city) {
        TypedQuery<LoanTypeCondition> query = getEntityManager().createQuery("SELECT s FROM LoanTypeCondition s WHERE" +
                "(:education IS NULL OR s.educationGrade = :education)" +
                "And s.loanType = :loanType " +
                "And (:city IS NULL OR s.city = :city)" ,LoanTypeCondition.class) ;


                query.setParameter("education", education);
                query.setParameter("loanType", loanType);
                query.setParameter("city", city != null ? city.getCategory() : null);

        List<LoanTypeCondition> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }


    }
