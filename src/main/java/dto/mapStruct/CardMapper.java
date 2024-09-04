package dto.mapStruct;

import dto.CardDto;
import dto.CardDtoWithId;
import entity.Card;
import org.mapstruct.Mapper;

@Mapper
public interface CardMapper {
    CardDto toDTO(Card card);
    Card toEntity(CardDto cardDto);

    CardDtoWithId toDTOId(Card card);
    Card toEntityId(CardDtoWithId cardDto);



}
