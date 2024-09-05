package service.impl;

import dto.CardDto;
import dto.mapStruct.CardMapper;
import entity.Card;
import entity.Installment;
import entity.Student;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import repository.CardRepository;
import service.CardService;

import java.util.List;
import java.util.Set;

public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private Validator validator;

    public CardServiceImpl(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    public List<Card> selectAllStudentCard(Long stdId) {
        return cardRepository.selectAllStudentCard(stdId);
    }

    @Override
    public Card findCardRelatedLoan(CardDto card, Installment installment) {
    try{
        Set<ConstraintViolation<CardDto>> violations = validator.validate(card);


        if (!violations.isEmpty()) {
            System.out.println("Payment process failed please fix this errors and try again:");
            for (ConstraintViolation<CardDto> violation : violations) {
                System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
            }
            throw new IllegalArgumentException("Payment process failed due to validation errors.");

        }
        Card cardFromUser=cardMapper.toEntity(card);
        Card cardRelatedLoan = cardRepository.findCardRelatedLoan(installment);
        if(cardFromUser.getCardNumber().equals(cardRelatedLoan.getCardNumber())
        && cardFromUser.getCvv2().equals(cardRelatedLoan.getCvv2()) && cardFromUser.getExpireDate().equals(cardRelatedLoan.getExpireDate())){
            return cardRelatedLoan;
        }
        else{
            return null;
        }}catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void saveOrUpdateCard(Card card) {
        cardRepository.saveOrUpdateCard(card);
    }
}
