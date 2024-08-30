package repository;

import entity.LoanTypeCondition;
import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.LoanType;

public interface LoanTypeConditionRepository {

    LoanTypeCondition findByEducationandLoanType(EducationGrade education, LoanType loanType, City city);

}
