package service.impl;

import entity.LoanTypeCondition;
import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.LoanType;
import repository.LoanTypeConditionRepository;
import service.LoanTypeConditionService;

public class LoanTypeConditionServiceImpl implements LoanTypeConditionService {

    private final LoanTypeConditionRepository loanTypeConditionRepository;

    public LoanTypeConditionServiceImpl(LoanTypeConditionRepository loanTypeConditionRepository) {
        this.loanTypeConditionRepository = loanTypeConditionRepository;
    }


    @Override
    public LoanTypeCondition findByEducationandLoanType(EducationGrade education, LoanType loanType, City city) {
        return loanTypeConditionRepository.findByEducationandLoanType(education,loanType,city);
    }
}
