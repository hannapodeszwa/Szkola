package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "klasy")
public class Klasy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "numer", nullable = false)
    private String numer;

    @Column(name = "idWychowawcy", nullable = false)
    private Integer idWychowawcy;

    @Column(name = "idPrzewodniczacego", nullable = false)
    private Integer idPrzewodniczacego;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public String getNumer() {
        return numer;
    }

    public void setIdWychowawcy(Integer idWychowawcy) {
        this.idWychowawcy = idWychowawcy;
    }

    public Integer getIdWychowawcy() {
        return idWychowawcy;
    }

    public void setIdPrzewodniczacego(Integer idPrzewodniczacego) {
        this.idPrzewodniczacego = idPrzewodniczacego;
    }

    public Integer getIdPrzewodniczacego() {
        return idPrzewodniczacego;
    }

    @Override
    public String toString() {
        return "Klasy{" +
                "ID=" + ID + '\'' +
                "numer=" + numer + '\'' +
                "idWychowawcy=" + idWychowawcy + '\'' +
                "idPrzewodniczacego=" + idPrzewodniczacego + '\'' +
                '}';
    }
}
