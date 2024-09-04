package service;

import dto.CardDto;
import dto.CardDtoBalance;
import entity.Card;
import entity.Student;

import java.util.List;

public interface CardService {
    public List<Card> selectAllStudentCard(Long stdId);
    Card findCard(CardDto card);
    void saveOrUpdateCard(Card card);
}
