package service.impl;

import dto.CardDto;
import dto.CardDtoBalance;
import dto.mapStruct.CardMapper;
import dto.mapStruct.LoanMapper;
import entity.Card;
import entity.Installment;
import repository.InstallmentRepository;
import service.CardService;
import service.InstallmentService;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class InstallmentServiceImpl implements InstallmentService {
    private final InstallmentRepository installmentRepository;
    private final CardMapper cardMapper;
    private final CardService CARD_SERVICE;

    public InstallmentServiceImpl(InstallmentRepository installmentRepository, CardMapper cardMapper, CardService cardService) {
        this.installmentRepository = installmentRepository;
        this.cardMapper = cardMapper;
        CARD_SERVICE = cardService;
    }

    @Override
    public void setInstallment(Set<Installment> installments) {
        installmentRepository.setInstallment(installments);
    }

    @Override
    public List<Installment> getInstallmentsByLoanIdAndPaidStatus(Long loanId,Boolean paidStatus) {
        return installmentRepository.getInstallmentsByLoanIdAndPaidStatus(loanId,paidStatus);
    }

    @Override
    public void installmentPayment(Card card, Installment installment,Double amount) {
           card.setBalance(card.getBalance()-amount);
            installment.setPaymentDate(new Date());
            installment.setIsPaid(true);
            installment.setPaymentAmount(installment.getInstallmentAmount());
            installment.setUnPaymentAmount(0.0);
            installmentRepository.installmentPayment(installment,card);

    }
}
