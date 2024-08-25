package repository;

import entity.LoanTypeCondition;
import entity.enumration.EducationGrade;
import entity.enumration.LoanType;

public interface LoanTypeConditionRepository {

    LoanTypeCondition findByEducationandLoanType(EducationGrade education, LoanType loanType);

}
