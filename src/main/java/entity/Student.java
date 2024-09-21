package entity;

import entity.enumration.City;
import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Table(
        name = "Student"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="name")
    private String name;


    @Column(name="last_name")
    private String lastName;


    @Column(name="father_name")
    private String fatherName;



    @Column(name="mother_name")
    private String motherName;

    @Column(name="id_number")
    private String idNumber;


    @Column(name="national_code")
    private String nationalCode;


    @Column(name="birthDate")
    private Date birthDate;


    @Column(name="student_number")
    private String studentNumber;



    @Column(name="university_name")
    private String universityName;


    @Enumerated(value =EnumType.STRING)
    @Column(name="university_type")
    private UniversityType universityType;



    @Column(name="entry_year")
    private Date entryYear;



    @Enumerated(value =EnumType.STRING)
    @Column(name="education_grade")
    private EducationGrade educationGrade;

    @Column(name="user_name")
    private String userName;

    @Column(name="password")
    private String password;

    @Enumerated(value =EnumType.STRING)
    @Column(name="residence_city")
    private City residenceCity;

    @Column(name="address")
    private String address;

    @Column(name="contractNumber")
    private String contractNumber;



    @Enumerated(value =EnumType.STRING)
    @Column(name="marital_status")
    private MaritalStatus maritalStatus;



    @Column(name = "is_dormitory_resident", nullable = false)
    private boolean isDormitoryResident;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private Set<Loan> loan= new HashSet<>();

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "spouse_id", referencedColumnName = "id")
    private Spouse spouse;

   @Transient
    private boolean canReceiveHousingLoan;

//    @PostLoad
//   // @PostPersist
//    @PostUpdate
//    private void calculateCanReceiveHousingLoan() {
//        this.canReceiveHousingLoan =
//                this.maritalStatus == MaritalStatus.MARRIED &&
//                        !this.isDormitoryResident &&
//                        (this.spouse != null || this.id < this.spouse.getId());
//    }

    @PostPersist
    protected void generateUsernameAndPassword() {

        this.userName = this.nationalCode;
        this.password = generatePassword();


    }

    public String generatePassword()
    {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String digitChars = "0123456789";
        String specialChars = "@#$%&";
        String allTypeOfChars = upperCaseChars + lowerCaseChars + digitChars + specialChars;
        StringBuilder complexPass = new StringBuilder();
        Random random = new Random();
        complexPass.append(upperCaseChars.charAt(random.nextInt(upperCaseChars.length())));
        complexPass.append(lowerCaseChars.charAt(random.nextInt(lowerCaseChars.length())));
        complexPass.append(digitChars.charAt(random.nextInt(digitChars.length())));
        complexPass.append(specialChars.charAt(random.nextInt(specialChars.length())));
        for (int i = 0; i < 4; i++) {
            complexPass.append(allTypeOfChars.charAt(random.nextInt(allTypeOfChars.length())));
        }

        return complexPass.toString();
    }

}
