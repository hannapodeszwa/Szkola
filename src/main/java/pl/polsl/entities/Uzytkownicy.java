package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "uzytkownicy")
public class Uzytkownicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "haslo", nullable = false)
    private String haslo;
    @Column(name = "dostep", nullable = false)
    private String dostep;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getHaslo() {
        return haslo;
    }

    @Override
    public String toString() {
        return "Uzytkownicy{" +
                "ID=" + ID + '\'' +
                "email=" + email + '\'' +
                "login=" + login + '\'' +
                "haslo=" + haslo + '\'' +
                '}';
    }

    public void setDostep(String dostep) {
        this.dostep = dostep;
    }

    public String getDostep() {
        return dostep;
    }
}
