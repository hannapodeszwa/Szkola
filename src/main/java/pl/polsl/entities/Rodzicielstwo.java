package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "rodzicielstwo")
public class Rodzicielstwo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Id
    @Column(name = "idRodzica", nullable = false)
    private Integer idRodzica;

    public void setIdUcznia(Integer idUcznia) {
        this.idUcznia = idUcznia;
    }

    public Integer getIdUcznia() {
        return idUcznia;
    }

    public void setIdRodzica(Integer idRodzica) {
        this.idRodzica = idRodzica;
    }

    public Integer getIdRodzica() {
        return idRodzica;
    }

    @Override
    public String toString() {
        return "Rodzicielstwo{" +
                "idUcznia=" + idUcznia + '\'' +
                "idRodzica=" + idRodzica + '\'' +
                '}';
    }
}
