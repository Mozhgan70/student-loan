package repository;

import entity.LoanTypeCondition;

public interface LoanTypeConditionRepository {

    LoanTypeCondition findByEducationandLoanType(String education, String loanType);

}
