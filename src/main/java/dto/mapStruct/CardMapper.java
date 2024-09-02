package dto.mapStruct;

import dto.CardDto;
import dto.RegisterStudentDto;
import entity.Card;
import entity.Student;
import org.mapstruct.Mapper;

@Mapper
public interface CardMapper {
    CardDto toDTO(Card card);

    Card toEntity(CardDto cardDto);
}
