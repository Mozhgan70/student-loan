package repository;

import entity.Card;
import entity.Installment;
import entity.Loan;

import java.util.List;
import java.util.Set;

public interface InstallmentRepository {
    void setInstallment(Set<Installment> installments);
   List<Installment> getInstallmentsByLoanIdAndPaidStatus(Long loanId,Boolean paidStatus);
    void installmentPayment(Installment installment, Card card);
}
