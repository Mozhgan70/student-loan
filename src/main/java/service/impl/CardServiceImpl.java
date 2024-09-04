package service.impl;

import dto.CardDto;
import dto.mapStruct.CardMapper;
import entity.Card;
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
    public Card findCard(CardDto card) {
        Card card1=cardMapper.toEntity(card);
        return cardRepository.findCard(card1);
    }

    @Override
    public void saveOrUpdateCard(Card card) {
        cardRepository.saveOrUpdateCard(card);
    }
}
