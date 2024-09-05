package dto;

import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import jakarta.validation.constraints.*;

import java.util.Date;

public record RegisterStudentDto(


        @NotBlank(message = "Name cannot be blank")
        @Size(max = 50, message = "Name must be less than 50 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters")
        String name,

        @NotBlank(message = "Last Name cannot be blank")
        @Size(max = 50, message = "Last Name must be less than 50 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last Name can only contain letters")
        String lastName,

        @NotBlank(message = "father Name cannot be blank")
        @Size(max = 50, message = "father Name must be less than 50 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "father Name can only contain letters")
        String fatherName,

        @Size(max = 50, message = "mother Name must be less than 50 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "mother Name can only contain letters")
        String motherName,

        @NotBlank(message = "ID number cannot be blank")
        @Pattern(regexp = "^\\d{4,10}$", message = "ID number must be between 6 and 10 digits")
        String idNumber,

        @NotBlank(message = "National ID cannot be blank")
        @Pattern(regexp = "^\\d{10}$", message = "National ID must be exactly 10 digits")
        String nationalCode,

        @NotNull(message = "Birthdate cannot be null")
        @Past(message = "Birthdate must be in the past")
        Date birthDate,

        @NotBlank(message = "Student number cannot be blank")
        @Pattern(regexp = "^\\d{8,10}$", message = "Student number must be between 8 and 10 digits")
        String studentNumber,

        @NotBlank(message = "University name cannot be blank")
        @Size(max = 100, message = "University name must be less than 100 characters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "University name can only contain letters and spaces")
        String universityName,

        @NotNull(message = "University type cannot be null")
        UniversityType universityType,

        @NotNull(message = "Entry year cannot be null")
        Date entryYear,

        @NotNull(message = "Education grade cannot be null")
        EducationGrade educationGrade,

        @NotNull(message = "Marital Status cannot be null")
        MaritalStatus maritalStatus,

        @NotNull(message = "residency city cannot be null")
        City residenceCity,

        @NotNull(message = "is Dormitory Resident cannot be null")
        boolean isDormitoryResident
    ) {}


