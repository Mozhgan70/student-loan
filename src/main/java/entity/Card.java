package entity;

import entity.enumration.Bank;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Set;

@Entity
@Table(name = "Card")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="expire_date")
    private String expireDate;

    @Column(name="card_number")
    private String cardNumber;

    @Column(name="cvv2")
    private Integer cvv2;

    @Column(name="balance")
    private Double balance;

    @Enumerated(value=EnumType.STRING)
    @Column(name="bank")
    private Bank bank;

    @OneToMany(mappedBy = "card")
    private Set<Loan> loan;

    @PrePersist
    protected void onCreate() {
        if (balance == null) {
            balance = 100000000.0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (balance == null) {
            balance = 100000000.0;
        }
    }

}
