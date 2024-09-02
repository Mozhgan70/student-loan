package service;

import entity.Card;
import entity.Student;

import java.util.List;

public interface CardService {
    public List<Card> selectAllStudentCard(Long stdId);
}
