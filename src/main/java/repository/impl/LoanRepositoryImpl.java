package repository.impl;

import entity.Loan;
import entity.Student;
import entity.enumration.LoanType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.LoanRepository;

import java.util.List;

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

    @Override
    public Loan findStudentLoan(Student student, LoanType loanType) {
        TypedQuery<Loan> query = entityManager.createQuery(
                "select l from Loan l where l.student = :student and l.loanType.loanType = :loanType and l.registerLoanDate = " +
                        "(select max(l2.registerLoanDate) from Loan l2 where l2.student = :student and l2.loanType.loanType = :loanType)",
                Loan.class
        );
        query.setParameter("student", student);
        query.setParameter("loanType", loanType);
        List<Loan> resultLoanList = query.getResultList();
        if (resultLoanList.isEmpty()) {
           return null;
        }
            return resultLoanList.get(0);
    }

    @Override
    public Loan findLoanByNationalCode(String nationalCode) {
        String query = "SELECT l FROM Loan l " +
                "LEFT JOIN FETCH l.student s " +
                "WHERE s.nationalCode = :nationalCode AND l.loanType.loanType=:loanType";

        TypedQuery<Loan> typedQuery = entityManager.createQuery(query, Loan.class);
        typedQuery.setParameter("nationalCode", nationalCode);
        typedQuery.setParameter("loanType", LoanType.HOUSING_LOAN);

        List<Loan> loans = typedQuery.getResultList();
        if (loans.isEmpty()) {
            return null;
        }
        return loans.get(0);

    }
}
