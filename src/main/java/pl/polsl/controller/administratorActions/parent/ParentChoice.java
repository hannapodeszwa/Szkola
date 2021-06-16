package pl.polsl.controller.administratorActions.parent;

import javafx.scene.control.CheckBox;
import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

// CHYBA DO USUNIECIA

public class ParentChoice  {
   /* public CheckBox choose = new CheckBox();
    private EntityManager entityManager;
    public ParentChoice(Uczniowie u) {
        this.ID=u.ID;
        this.imie = u.imie;
        this.drugieImie = u.drugieImie;
        this.email=u.email;
        this.idKlasy=u.idKlasy;
        this.nazwisko=u.nazwisko;
        this.adres=u.adres;
    }
    public List findByParent(Rodzice r)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("rodzicielstwo.findByParent", Rodzicielstwo.class);
        List<Rodzicielstwo> results = query.setParameter("id", r.getID()).getResultList();

        return results;
    }

   /* public void setChoose(CheckBox choose) {//trzeba coś takiego zrobić żeby był checkbox w tablicy
this.choose=choose;
    }*/

    /*public CheckBox getChoose() {
        return choose;
    }*/
}
