package lab.web.lab_05.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "partners")
@NoArgsConstructor
public class Partner {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "p_id_seq")
    @SequenceGenerator(name = "p_id_seq", sequenceName = "partners_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "address", nullable = false, length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "tel_number", length = Integer.MAX_VALUE)
    private String telNumber;

    @Column(name = "itn", length = Integer.MAX_VALUE)
    private String itn;

    @Column(name = "reg", length = Integer.MAX_VALUE)
    private String reg;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;

    @OneToMany(mappedBy = "partner")
    private Set<PartnersBank> banks = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "actual_bank_account", nullable = true)
    private PartnersBank actualBankAccount;

    public Partner(String name, String address, String telNumber, String itn, String reg, String notes) {
//        this.id = id;
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.itn = itn;
        this.reg = reg;
        this.notes = notes;
    }
}