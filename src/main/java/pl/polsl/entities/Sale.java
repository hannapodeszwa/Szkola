package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sale")
@NamedQueries({
        @NamedQuery(name = "sale.getNameById", query = "SELECT s.nazwa FROM Sale s WHERE s.ID = :id")
})
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "liczbaMiejsc")
    private Integer liczbaMiejsc;

    @Column(name = "czyJestRzutnik", nullable = false)
    private Integer czyJestRzutnik;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setLiczbaMiejsc(Integer liczbaMiejsc) {
        this.liczbaMiejsc = liczbaMiejsc;
    }

    public Integer getLiczbaMiejsc() {
        return liczbaMiejsc;
    }

    public void setCzyJestRzutnik(Integer czyJestRzutnik) {
        this.czyJestRzutnik = czyJestRzutnik;
    }

    public Integer getCzyJestRzutnik() {
        return czyJestRzutnik;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "ID=" + ID + '\'' +
                "liczbaMiejsc=" + liczbaMiejsc + '\'' +
                "czyJestRzutnik=" + czyJestRzutnik + '\'' +
                '}';
    }
}
