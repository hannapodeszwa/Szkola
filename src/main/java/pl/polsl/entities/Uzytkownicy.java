package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name="Uzytkownicy.getUserByLoginAndPassword",
        query = "SELECT U FROM Uzytkownicy U WHERE U.login = :LOGIN AND U.haslo = :PASSWORD"),

        @NamedQuery(name="Uzytkownicy.getUserByLogin",
        query = "SELECT U FROM Uzytkownicy U WHERE U.login = :LOGIN"),

        @NamedQuery(name = "Uzytkownicy.updatePasswordByIdAndRole",
        query = "UPDATE Uzytkownicy U SET U.haslo = :PASSWORD WHERE U.ID = :ID AND U.dostep = :ROLE")
})
@Table(name = "uzytkownicy")
public class Uzytkownicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "haslo", nullable = false)
    private String haslo;

    @Column(name = "email")
    private String email;

    @Column(name = "dostep", nullable = false)
    private String dostep;

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setDostep(String dostep) {
        this.dostep = dostep;
    }

    public String getDostep() {
        return dostep;
    }

    @Override
    public String toString() {
        return "Uzytkownicy{" +
                "login=" + login + '\'' +
                "ID=" + ID + '\'' +
                "haslo=" + haslo + '\'' +
                "email=" + email + '\'' +
                "dostep=" + dostep + '\'' +
                '}';
    }
}
