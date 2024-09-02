package service;

import dto.LoanRegistrationDto;
import entity.Installment;
import entity.Loan;
import entity.LoanTypeCondition;
import entity.Student;
import entity.enumration.LoanType;

import java.util.Date;
import java.util.Set;

public interface LoanService {
    Loan registerLoan(Loan loan);

    Loan FindStudentLoan(Student student, LoanType loanType);

    Loan findLoanByNationalCode(String nationalCode);

    int getCheckLoanCondition(LoanTypeCondition loanTypeCondition);

    boolean checkRequestDateIsValid(LoanType loanType, Student student);

    Date calcInstallmentStartDate(Student student);
    Set<Installment> calculateInstallments(double loanAmount, double annualIncreasePercentage, Loan loan, Date startDate);
    void registerLoan(LoanRegistrationDto loanRegistrationDTO);
//    void finalRegisterLoan(LoanTypeCondition LoanTypeCondition, String cardNumber, String expireDate, int cvv2, Bank bank);
//    Student registerHousingLoan(HousingLoanExtraDataDto housingLoanExtraDataDto,Student student);
}


