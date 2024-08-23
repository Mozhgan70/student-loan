package util;

import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {

    private Long tokenId;
    private String tokenName;
    private EducationGrade educationGrade;
    private UniversityType universityType;
    private MaritalStatus maritalStatus;
    private City city;


    public void reset() {
        tokenId = null;
        tokenName = null;
        educationGrade = null;
        maritalStatus = null;
        universityType=null;
        city = null;
    }
}
