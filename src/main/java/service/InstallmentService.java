package service;

import dto.CardDto;
import entity.Installment;
import entity.Loan;

import java.util.List;
import java.util.Set;

public interface InstallmentService {
    void setInstallment(Set<Installment> installments);
    List<Installment> getInstallmentsByLoanIdAndPaidStatus(Long loanId,Boolean paidStatus);
    void installmentPayment(CardDto cardDto,Installment installment);
}
