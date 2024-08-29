package util;

import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserSession {

    private Long tokenId;
    private String tokenName;
    private EducationGrade educationGrade;
    private Date entryYear;
    private Boolean isDormitoryResident;
    private UniversityType universityType;
    private MaritalStatus maritalStatus;
    private City city;


    public void reset() {
        tokenId = null;
        tokenName = null;
        educationGrade = null;
        entryYear=null;
        isDormitoryResident=null;
        maritalStatus = null;
        universityType=null;
        city = null;
    }
}
