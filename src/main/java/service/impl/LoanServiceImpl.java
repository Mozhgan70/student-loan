package service.impl;

import entity.Loan;
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
}
