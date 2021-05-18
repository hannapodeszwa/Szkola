package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "książki")
public class Książki implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Tytuł", nullable = false)
    private String tytuł;

    @Column(name = "Autor")
    private Integer autor;

    public void setTytuł(String tytuł) {
        this.tytuł = tytuł;
    }

    public String getTytuł() {
        return tytuł;
    }

    public void setAutor(Integer autor) {
        this.autor = autor;
    }

    public Integer getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "Książki{" +
                "tytuł=" + tytuł + '\'' +
                "autor=" + autor + '\'' +
                '}';
    }
}
