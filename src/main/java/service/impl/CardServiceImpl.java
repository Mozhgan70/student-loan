package service.impl;

import entity.Card;
import entity.Student;
import repository.CardRepository;
import service.CardService;

import java.util.List;

public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> selectAllStudentCard(Long stdId) {
        return cardRepository.selectAllStudentCard(stdId);
    }

    @Override
    public Card findCard(Card card) {
        return cardRepository.findCard(card);
    }
}
