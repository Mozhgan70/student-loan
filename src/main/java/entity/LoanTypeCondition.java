package entity;


import entity.enumration.EducationGrade;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@Entity
@Table(
        name = "LoanType_Condition"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class LoanTypeCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value =EnumType.STRING)
    @Column(name="loan_type")
    private entity.enumration.LoanType loanType;

    @Enumerated(value =EnumType.STRING)
    @Column(name="education_grade")
    private EducationGrade educationGrade;

    @Column(name="amount")
    private Double amount;

    @Column(name="profit")
    private Double profit;

    @Column(name="city")
    private String city;

    @Column(name="startDate")
    private Date startDate;

    @Column(name="endDate")
    private Date endDate;

    @OneToMany(mappedBy = "loanType",cascade = CascadeType.ALL)
    private Set<Loan> loan;


}
