package dto.mapStruct;

import dto.CardDto;
import dto.LoanRegistrationDto;
import dto.SpouseDto;
import entity.Card;
import entity.Loan;
import entity.Spouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LoanMapper {

    @Mapping(source = "loanRegistrationDto.card", target = "card")
    @Mapping(source = "loanRegistrationDto.loanType", target = "loanType")
    @Mapping(target = "student", ignore = true) // Set student separately
    @Mapping(target = "installments", ignore = true) // Set installments separately
    Loan toLoan(LoanRegistrationDto loanRegistrationDto);

    Card toCard(CardDto cardDto);


    Spouse toSpouse(SpouseDto spouseDto);
}
