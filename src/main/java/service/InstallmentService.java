package service;

import entity.Installment;
import entity.Loan;

import java.util.Set;

public interface InstallmentService {
    void setInstallment(Set<Installment> installments);

}
