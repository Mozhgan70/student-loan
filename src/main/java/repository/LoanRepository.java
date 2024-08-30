package repository;

import entity.Loan;
import entity.Student;
import entity.enumration.LoanType;

public interface LoanRepository {

    Loan registerLoan(Loan loan);
    Loan findStudentLoan(Student student, LoanType loanType);
    Loan findLoanByNationalCode(String nationalCode);
}
