package pl.polsl.entities;

import javafx.scene.control.CheckBox;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "nieobecnosci")
@NamedQueries({
        @NamedQuery(name = "nieobecnosci.findAll", query = "SELECT n FROM Nieobecnosci n")
})
public class Nieobecnosci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "idPrzedmiotu", nullable = false)
    private Integer idPrzedmiotu;

    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "godzina", nullable = false)
    private Integer godzina;

    @Column(name = "czyUsprawiedliwiona", nullable = false)
    private Integer czyUsprawiedliwiona;


    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setUczniowieId(Integer uczniowieId) {
        this.idUcznia = uczniowieId;
    }

    public Integer getUczniowieId() {
        return idUcznia;
    }

    public void setPrzedmiotyId(Integer przedmiotyId) {
        this.idPrzedmiotu = przedmiotyId;
    }

    public Integer getPrzedmiotyId() {
        return idPrzedmiotu;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setGodzina(Integer data) {
        this.godzina = data;
    }

    public Integer getGodzina(){ return godzina; }

    public void setCzyUsprawiedliwiona(CheckBox czyUsprawiedliwiona) {//trzeba coś takiego zrobić żeby był checkbox w tablicy
        if (czyUsprawiedliwiona.isSelected()) {
            this.czyUsprawiedliwiona = 1;
        } else {
            this.czyUsprawiedliwiona = 0;
        }
    }

    public CheckBox getCzyUsprawiedliwiona() {
        CheckBox a = new CheckBox();
        if (czyUsprawiedliwiona == 0) {
            a.setSelected(false);
        } else {
            a.setSelected(true);
        }
        return a;
    }

    @Override
    public String toString() {
        return "Nieobecnosci{" +
                "ID=" + ID + '\'' +
                "uczniowieId=" + idUcznia + '\'' +
                "przedmiotyId=" + idPrzedmiotu + '\'' +
                "data=" + data + '\'' +
                "czyUsprawiedliwiona=" + czyUsprawiedliwiona + '\'' +
                '}';
    }
}
