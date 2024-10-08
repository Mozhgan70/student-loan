package entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@Entity
@Table(
        name = "Loan"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="loanType_id")
    private LoanTypeCondition loanType;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @OneToMany(mappedBy = "loan",cascade = CascadeType.ALL)
    private Set<Installment> installments;


    @Column(name="payment_date")
    private Date paymentDate;

    @Column(name="registerLoan_date")
    private Date registerLoanDate;

    @Column(name="start_installments")
    private Date startInstallments;

    @Column(name="installments_count")
    private Integer installmentsCount;

    @Column(name="remain_loan_amount")
    private Double remainLoanAmount;

    @Column(name="number_of_paid")
    private Integer numberOfPaid;

    @Column(name="number_of_unPaid")
    private Integer numberOfUnPaid;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="card_id")
    private Card card;

    @PrePersist
    protected void onCreate() {
        if (installmentsCount == null) {
            installmentsCount = 60;
        }
        if (numberOfPaid == null) {
            numberOfPaid = 0;
        }

        if (numberOfUnPaid == null) {
            numberOfUnPaid = 60;
        }
    }

}
