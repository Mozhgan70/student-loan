package entity;

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
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name="expire_date")
    private String expireDate;

    @Column(name="card_number")
    private String cardNumber;

    @Column(name="cvv2")
    private Integer cvv2;

    @OneToOne(mappedBy = "account_id")
    @Column(name="account")
    private Account account;

    @OneToMany(mappedBy = "loan_id")
    private Set<Loan> loan;


}
