package service;

import dto.CardDto;
import entity.Card;
import entity.Installment;

import java.util.List;

public interface CardService {
    public List<Card> selectAllStudentCard(Long stdId);
    Card findCardRelatedLoan(CardDto card, Installment installment);
    void saveOrUpdateCard(Card card);
}
