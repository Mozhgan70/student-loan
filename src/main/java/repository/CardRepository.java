package repository;

import entity.Card;
import entity.Student;

import java.util.List;

public interface CardRepository {

    List<Card> selectAllStudentCard(Long stdId);
    Card findCard(Card card);
    void saveOrUpdateCard(Card card);

}
