package service.impl;

import dto.CardDto;
import dto.mapStruct.CardMapper;
import entity.Card;
import entity.Installment;
import entity.Student;
import repository.CardRepository;
import service.CardService;

import java.util.List;

public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardServiceImpl(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public List<Card> selectAllStudentCard(Long stdId) {
        return cardRepository.selectAllStudentCard(stdId);
    }

    @Override
    public Card findCardRelatedLoan(CardDto card, Installment installment) {
        Card cardFromUser=cardMapper.toEntity(card);
        Card cardRelatedLoan = cardRepository.findCardRelatedLoan(installment);
        if(cardFromUser.getCardNumber().equals(cardRelatedLoan.getCardNumber())
        && cardFromUser.getCvv2().equals(cardRelatedLoan.getCvv2()) && cardFromUser.getExpireDate().equals(cardRelatedLoan.getExpireDate())){
            return cardRelatedLoan;
        }
        else{
            return null;
        }
    }

    @Override
    public void saveOrUpdateCard(Card card) {
        cardRepository.saveOrUpdateCard(card);
    }
}
