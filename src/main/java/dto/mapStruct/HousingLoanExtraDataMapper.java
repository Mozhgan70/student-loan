package dto.mapStruct;

import dto.HousingLoanExtraDataDto;
import entity.Spouse;
import entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface HousingLoanExtraDataMapper {

    @Mapping(target = "id", source = "stdId")
    @Mapping(target = "address", source = "stdAddress")
    @Mapping(target = "contractNumber", source = "stdContractNumber")
    Student toStudent(HousingLoanExtraDataDto dto);

    @Mapping(target = "nationalCode", source = "spouseNationalCode")
    @Mapping(target = "name", source = "spouseName")
    @Mapping(target = "lastName", source = "spouseLastName")
    Spouse toSpouse(HousingLoanExtraDataDto dto);



}
