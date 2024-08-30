package service;

import entity.LoanTypeCondition;
import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.LoanType;

public interface LoanTypeConditionService {
    LoanTypeCondition findByEducationandLoanType(EducationGrade education, LoanType loanType, City city);
}
