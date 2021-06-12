package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "kodyweryfikacyjne")
public class Kodyweryfikacyjne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "kod")
    private String kod;

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getKod() {
        return kod;
    }

    @Override
    public String toString() {
        return "Kodyweryfikacyjne{" +
                "login=" + login + '\'' +
                "email=" + email + '\'' +
                "kod=" + kod + '\'' +
                '}';
    }
}
