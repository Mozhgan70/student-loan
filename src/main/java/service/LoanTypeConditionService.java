package service;

import entity.LoanTypeCondition;

public interface LoanTypeConditionService {
    LoanTypeCondition findByEducationandLoanType(String education, String loanType);
}
