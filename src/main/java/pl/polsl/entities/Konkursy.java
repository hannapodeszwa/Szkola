package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "konkursy")
public class Konkursy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "opis")
    private String opis;

    @Column(name = "dataOdbyciaSie")
    private Date dataOdbyciaSie;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    public void setDataOdbyciaSie(Date dataOdbyciaSie) {
        this.dataOdbyciaSie = dataOdbyciaSie;
    }

    public Date getDataOdbyciaSie() {
        return dataOdbyciaSie;
    }

    @Override
    public String toString() {
        return "Konkursy{" +
                "ID=" + ID + '\'' +
                "nazwa=" + nazwa + '\'' +
                "opis=" + opis + '\'' +
                "dataOdbyciaSie=" + dataOdbyciaSie + '\'' +
                '}';
    }
}
