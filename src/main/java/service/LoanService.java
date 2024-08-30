package service;

import entity.Loan;
import entity.Student;
import entity.enumration.LoanType;

public interface LoanService {
    Loan registerLoan(Loan loan);
    Loan FindStudentLoan(Student student, LoanType loanType);
    Loan findLoanByNationalCode(String nationalCode);

}
