package dto.mapStruct;

import dto.CardDto;
import dto.CardDtoBalance;
import dto.RegisterStudentDto;
import entity.Card;
import entity.Student;
import org.mapstruct.Mapper;

@Mapper
public interface CardMapper {
    CardDto toDTO(Card card);
    Card toEntity(CardDto cardDto);

    CardDtoBalance toDTOBalance(Card card);
    Card toEntityBalance(CardDtoBalance cardDtoCreate);
}
