package service;

import entity.LoanTypeCondition;
import entity.enumration.EducationGrade;
import entity.enumration.LoanType;

public interface LoanTypeConditionService {
    LoanTypeCondition findByEducationandLoanType(EducationGrade education, LoanType loanType);
}
