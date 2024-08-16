package entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "Installment")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="installment_number")
    private Integer installmentNumber;

    @Column(name="installment_date")
    private Date installmentDate;

    @Column(name="installment_amount")
    private BigDecimal installmentAmount;

    @Column(name="payment_date")
    private Date paymentDate;

    @Column(name="payment_amount")
    private BigDecimal paymentAmount;

    @Column(name="unpayment_amount")
    private BigDecimal unPaymentAmount;

    @Column(name="isPayment")
    private Boolean isPayment;

   @ManyToOne
   @JoinColumn(name="loan_id")
   private Loan loan;


}
