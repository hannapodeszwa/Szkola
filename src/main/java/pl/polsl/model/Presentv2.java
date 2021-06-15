package pl.polsl.model;

import javafx.scene.control.CheckBox;
import pl.polsl.entities.Nieobecnosci;

public class Presentv2 extends Nieobecnosci {
    public CheckBox Usp = new CheckBox();


    public Presentv2(Nieobecnosci akt){
        this.czyUsprawiedliwiona = akt.czyUsprawiedliwiona;
        this.ID = akt.ID;
        this.data = akt.data;
        this.godzina = akt.godzina;
        this.idPrzedmiotu = akt.idPrzedmiotu;
        this.idUcznia =akt.idUcznia;
        if (czyUsprawiedliwiona == 0) {
            Usp.setSelected(false);
        } else {
            Usp.setSelected(true);
        }
    }

    public void setCzyUsp(CheckBox czyUsp) {//trzeba coś takiego zrobić żeby był checkbox w tablicy
        if (czyUsp.isSelected()) {
            this.setCzyUsprawiedliwiona(1);
        } else {
            this.setCzyUsprawiedliwiona(0);
        }
        Usp = czyUsp;
    }

    public CheckBox getCzyUsp() {
        if (czyUsprawiedliwiona == 0) {
            Usp.setSelected(false);
        } else {
            Usp.setSelected(true);
        }
        return Usp;
    }

}
