package repository;

import entity.Card;
import entity.Installment;
import entity.Student;

import java.util.List;

public interface CardRepository {

    List<Card> selectAllStudentCard(Long stdId);
    Card findCardRelatedLoan(Installment installment);
    void saveOrUpdateCard(Card card);

}
