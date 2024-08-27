package service.impl;

import entity.Loan;
import entity.Student;
import entity.enumration.LoanType;
import repository.LoanRepository;
import service.LoanService;

public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;

    }

    @Override
    public Loan registerLoan(Loan loan) {
        return loanRepository.registerLoan(loan);
    }

    @Override
    public Loan FindStudentLoan(Student student, LoanType loanType) {
        return loanRepository.FindStudentLoan(student,loanType);
    }
}
