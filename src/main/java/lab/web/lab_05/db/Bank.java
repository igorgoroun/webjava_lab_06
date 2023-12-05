package lab.web.lab_05.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "b_id_seq")
    @SequenceGenerator(name = "b_id_seq", sequenceName = "bank_name_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "tel_number", length = Integer.MAX_VALUE)
    private String telNumber;

}