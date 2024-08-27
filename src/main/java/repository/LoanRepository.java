package repository;

import entity.Loan;
import entity.Student;
import entity.enumration.LoanType;

public interface LoanRepository {

    Loan registerLoan(Loan loan);
    Loan FindStudentLoan(Student student, LoanType loanType);
}
