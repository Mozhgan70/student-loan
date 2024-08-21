package service.impl;

import entity.LoanTypeCondition;
import repository.LoanTypeConditionRepository;
import service.LoanTypeConditionService;

public class LoanTypeConditionServiceImpl implements LoanTypeConditionService {

    private final LoanTypeConditionRepository loanTypeConditionRepository;

    public LoanTypeConditionServiceImpl(LoanTypeConditionRepository loanTypeConditionRepository) {
        this.loanTypeConditionRepository = loanTypeConditionRepository;
    }


    @Override
    public LoanTypeCondition findByEducationandLoanType(String education, String loanType) {
        return loanTypeConditionRepository.findByEducationandLoanType(education,loanType);
    }
}
