package pl.polsl.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Data
@Entity
@Table(name = "sql11418837.godzinylekcji")
public class Godzinylekcji implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "poczatek", nullable = false)
    private Time poczatek;

    @Column(name = "koniec", nullable = false)
    private Time koniec;

}
