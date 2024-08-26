package repository;

import entity.Installment;
import entity.Loan;

import java.util.Set;

public interface InstallmentRepository {
    void setInstallment(Set<Installment> installments, Loan loan);
}
