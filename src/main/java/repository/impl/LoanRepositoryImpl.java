package repository.impl;

import entity.Loan;
import entity.Student;
import entity.enumration.LoanType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    @Override
    public Loan FindStudentLoan(Student student, LoanType loanType) {
        TypedQuery<Loan> query = entityManager.createQuery(
                "select l from Loan l where l.student = :student and l.loanType.loanType = :loanType and l.registerLoanDate = " +
                        "(select max(l2.registerLoanDate) from Loan l2 where l2.student = :student and l2.loanType.loanType = :loanType)",
                Loan.class
        );
        query.setParameter("student", student);
        query.setParameter("loanType", loanType);
        return query.getSingleResult();


//        TypedQuery<Staff> query = entityManager.createQuery("SELECT s FROM Staff s WHERE s.staffNumber = :staffNumber", Staff.class);
//        query.setParameter("staffNumber", staffNumber);
//        return query.getSingleResult();


    }
}
