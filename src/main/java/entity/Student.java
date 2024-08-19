package entity;

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
@ToString
@SuperBuilder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters")
    @Column(name="name")
    private String name;


    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters")
    @Column(name="last_name")
    private String lastName;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters")
    @Column(name="father_name")
    private String fatherName;


    @Size(max = 50, message = "Name must be less than 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters")
    @Column(name="mother_name")
    private String motherName;

    @NotBlank(message = "ID number cannot be blank")
    @Pattern(regexp = "^\\d{4,10}$", message = "ID number must be between 6 and 10 digits")
    @Column(name="id_number")
    private String idNumber;


    @NotBlank(message = "National ID cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "National ID must be exactly 10 digits")
    @Column(name="national_code")
    private String nationalCode;

    @NotNull(message = "Birthdate cannot be null")
    @Past(message = "Birthdate must be in the past")
    @Column(name="birthDate")
    private Date birthDate;


    @NotBlank(message = "Student number cannot be blank")
    @Pattern(regexp = "^\\d{8,10}$", message = "Student number must be between 8 and 10 digits")
    @Column(name="student_number")
    private String studentNumber;


    @NotBlank(message = "University name cannot be blank")
    @Size(max = 100, message = "University name must be less than 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "University name can only contain letters and spaces")
    @Column(name="university_name")
    private String universityName;

    @NotNull(message = "University type cannot be null")
    @Enumerated(value =EnumType.STRING)
    @Column(name="university_type")
    private UniversityType universityType;


    @NotNull(message = "Entry year cannot be null")
   // @Min(value = 1900, message = "Entry year must be after 1900")
    //@Max(value = Year.now().getValue(), message = "Entry year cannot be in the future")
    @Column(name="entry_year")
    private Date entryYear;


    @NotNull(message = "Education grade cannot be null")
    @Enumerated(value =EnumType.STRING)
    @Column(name="education_grade")
    private EducationGrade educationGrade;

    @Column(name="user_name")
    private String userName;

    @Column(name="password")
    private String password;


    @NotNull(message = "University type cannot be null")
    @Enumerated(value =EnumType.STRING)
    @Column(name="marital_status")
    private MaritalStatus maritalStatus;


    @NotNull(message = "University type cannot be null")
    @Column(name = "is_dormitory_resident", nullable = false)
    private boolean isDormitoryResident;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private Set<Loan> loan= new HashSet<>();

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "spouse_id", referencedColumnName = "id")
    private Spouse spouse;

   @Transient
    private boolean canReceiveHousingLoan;

    @PostLoad
   // @PostPersist
    @PostUpdate
    private void calculateCanReceiveHousingLoan() {
        this.canReceiveHousingLoan =
                this.maritalStatus == MaritalStatus.MARRIED &&
                        !this.isDormitoryResident &&
                        (this.spouse != null || this.id < this.spouse.getId());
    }

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
