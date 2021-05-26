package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "nieobecnosci")
public class Nieobecnosci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "Uczniowie_ID", nullable = false)
    private Integer uczniowieId;

    @Column(name = "Przedmioty_ID", nullable = false)
    private Integer przedmiotyId;

    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "czyUsprawiedliwiona", nullable = false)
    private Integer czyUsprawiedliwiona;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setUczniowieId(Integer uczniowieId) {
        this.uczniowieId = uczniowieId;
    }

    public Integer getUczniowieId() {
        return uczniowieId;
    }

    public void setPrzedmiotyId(Integer przedmiotyId) {
        this.przedmiotyId = przedmiotyId;
    }

    public Integer getPrzedmiotyId() {
        return przedmiotyId;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setCzyUsprawiedliwiona(Integer czyUsprawiedliwiona) {
        this.czyUsprawiedliwiona = czyUsprawiedliwiona;
    }

    public Integer getCzyUsprawiedliwiona() {
        return czyUsprawiedliwiona;
    }

    @Override
    public String toString() {
        return "Nieobecnosci{" +
                "ID=" + ID + '\'' +
                "uczniowieId=" + uczniowieId + '\'' +
                "przedmiotyId=" + przedmiotyId + '\'' +
                "data=" + data + '\'' +
                "czyUsprawiedliwiona=" + czyUsprawiedliwiona + '\'' +
                '}';
    }
}
