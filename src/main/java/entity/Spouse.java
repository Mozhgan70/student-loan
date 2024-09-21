package entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "Spouse"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Spouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="last_name")
    private String lastName;

    @Column(name="nationalCode",unique = true)
    private String nationalCode;

    @Column(name="is_student")
    private boolean isStudent;

    @OneToOne(mappedBy = "spouse")
    private Student student;
}
