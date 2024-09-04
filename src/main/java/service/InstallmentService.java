package service;

import dto.CardDto;
import dto.CardDtoBalance;
import entity.Card;
import entity.Installment;
import entity.Loan;

import java.util.List;
import java.util.Set;

public interface InstallmentService {
    void setInstallment(Set<Installment> installments);
    List<Installment> getInstallmentsByLoanIdAndPaidStatus(Long loanId,Boolean paidStatus);
    void installmentPayment(Card card, Installment installment,Double amount);
}
