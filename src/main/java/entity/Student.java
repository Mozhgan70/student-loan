package entity;

import entity.enumration.EducationGrade;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.HashSet;
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
    private String birthDate;

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
    @Column(name="marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "is_dormitory_resident", nullable = false)
    private boolean isDormitoryResident;

    @OneToMany(mappedBy = "student")
    private Set<Loan> loan= new HashSet<>();

   @OneToOne
   @JoinColumn(name = "spouse_id", referencedColumnName = "id")
    private Student spouse;

   @Transient
    private boolean canReceiveHousingLoan;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateCanReceiveHousingLoan() {
        this.canReceiveHousingLoan =
                this.maritalStatus == MaritalStatus.MARRIED &&
                        !this.isDormitoryResident &&
                        (this.spouse != null || this.id < this.spouse.getId());
    }


}
