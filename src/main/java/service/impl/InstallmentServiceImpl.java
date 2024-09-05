package service.impl;


import dto.mapStruct.CardMapper;
import entity.Card;
import entity.Installment;
import entity.Loan;
import repository.InstallmentRepository;
import service.CardService;
import service.InstallmentService;

import java.util.Date;
import java.util.List;
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

    @Override
    public List<Installment> getInstallmentsByLoanIdAndPaidStatus(Long loanId, Boolean paidStatus) {
        return installmentRepository.getInstallmentsByLoanIdAndPaidStatus(loanId, paidStatus);
    }

    @Override
    public void installmentPayment(Card card, Installment installment, Double amount) {

        try {
            card.setBalance(card.getBalance() - amount);
            installment.setPaymentDate(new Date());
            installment.setIsPaid(true);
            installment.setPaymentAmount(installment.getInstallmentAmount());
            installment.setUnPaymentAmount(0.0);
            installmentRepository.installmentPayment(installment, card);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }


}
