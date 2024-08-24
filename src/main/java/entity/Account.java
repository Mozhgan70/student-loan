//package entity;
//
//
//import entity.enumration.Bank;
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//
//@Entity
//@Table(name = "Account")
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@SuperBuilder
//public class Account  {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name="sheba")
//    private String sheba;
//
//    @Column(name="deposit_number")
//    private String deposit_number;
//
//    @Column(name="balance")
//    private Double balance;
//
//    @Enumerated(value=EnumType.STRING)
//    @Column(name="bank")
//    private Bank bank;
//
//    @OneToOne(mappedBy = "account")
//    private Card card;
//
//}
