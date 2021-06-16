package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "wiadomosci")
@NamedQueries({
        @NamedQuery(name = "Wiadomosci.getReceivedMessagesByTypeAndId",
                query = "SELECT w FROM Wiadomosci w WHERE w.typ = :TYPE AND w.odbiorca = :ID"),
        @NamedQuery(name = "Wiadomosci.getSentMessagesByTypeAndId",
                query = "SELECT w FROM Wiadomosci w WHERE w.typ = :TYPE AND w.nadawca = :ID"),
        @NamedQuery(name = "wiadomosci.findByTeacher",
                query = "SELECT w FROM Wiadomosci w WHERE " +
                        "((w.typ = 0 OR w.typ = 2) AND w.odbiorca = :ID) OR" +
                        " ((w.typ = 1 OR w.typ = 3) AND w.nadawca = :ID)"),
        @NamedQuery(name = "wiadomosci.findByParent",
                query = "SELECT w FROM Wiadomosci w WHERE (w.typ = 3 AND w.odbiorca = :ID) OR (w.typ = 2 AND w.nadawca = :ID)")
})
public class Wiadomosci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "typ", nullable = false)
    private Integer typ;

    @Column(name = "temat", nullable = false)
    private String temat;

    @Column(name = "tresc", nullable = false)
    private String tresc;

    @Column(name = "data")
    private Date data;

    @Column(name = "odbiorca")
    private Integer odbiorca;

    @Column(name = "nadawca")
    private Integer nadawca;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTemat(String temat) {
        this.temat = temat;
    }

    public String getTemat() {
        return temat;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getTresc() {
        return tresc;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setOdbiorca(Integer idRodzica) {
        this.odbiorca = odbiorca;
    }

    public Integer getOdbiorca() {
        return odbiorca;
    }

    public void setNadawca(Integer idUcznia) {
        this.nadawca = nadawca;
    }

    public Integer getNadawca() {
        return nadawca;
    }


    @Override
    public String toString() {
        return "Wiadomosci{" +
                "ID=" + ID + '\'' +
                "typ=" + typ + '\'' +
                "temat" + temat + '\'' +
                "tresc=" + tresc + '\'' +
                "data=" + data + '\'' +
                "odbiorca=" + odbiorca + '\'' +
                "nadawca=" + nadawca + '\'' +
                '}';
    }
}
