package lab.web.lab_05.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "partners_banks")
public class PartnersBank {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "pb_id_seq")
    @SequenceGenerator(name = "pb_id_seq", sequenceName = "banks_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Column(name = "iban", nullable = false, length = Integer.MAX_VALUE)
    private String iban;

//    @Column(name = "actual")
//    private Boolean actual;

}