package util;

import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {

    private Long tokenId;
    private String tokenName;
    private EducationGrade educationGrade;
    private MaritalStatus maritalStatus;
    private City city;


    public void reset() {
        tokenId = null;
        tokenName = null;
        educationGrade = null;
        maritalStatus = null;
        city = null;
    }
}
