package service.impl;

import entity.Installment;
import entity.Loan;
import repository.InstallmentRepository;
import service.InstallmentService;

import java.util.Set;

public class InstallmentServiceImpl implements InstallmentService {
    private final InstallmentRepository installmentRepository;

    public InstallmentServiceImpl(InstallmentRepository installmentRepository) {
        this.installmentRepository = installmentRepository;
    }

    @Override
    public void setInstallment(Set<Installment> installments) {
        installmentRepository.setInstallment(installments);
    }
}
