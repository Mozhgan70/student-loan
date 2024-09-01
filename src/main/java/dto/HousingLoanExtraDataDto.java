package dto;

public record HousingLoanExtraDataDto(
        Long stdId,
        String spouseName,
        String spouseLastName,
        String spouseNationalCode,
        String stdAddress,
        String stdContractNumber


) {}
//(nationalCode, name, lastName, address, contractNumber)