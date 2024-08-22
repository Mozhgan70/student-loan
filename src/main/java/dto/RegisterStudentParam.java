package dto;

import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import java.util.Date;

public record RegisterStudentParam (

            String name,
            String lastName,
            String fatherName,
            String motherName,
            String idNumber,
            String nationalCode,
            Date birthDate,
            String studentNumber,
            String universityName,
            UniversityType universityType,
            Date entryYear,
            EducationGrade educationGrade,
            MaritalStatus maritalStatus,
            City city,
            boolean isDormitoryResident
    ) {}


