package pl.polsl.model;

import javafx.scene.control.CheckBox;
import pl.polsl.entities.Nieobecnosci;

public class Presentv2 extends Nieobecnosci {
    public CheckBox Usp = new CheckBox();


    public Presentv2(Nieobecnosci act){
        this.czyUsprawiedliwiona = act.czyUsprawiedliwiona;
        this.ID = act.ID;
        this.data = act.data;
        this.godzina = act.godzina;
        this.idPrzedmiotu = act.idPrzedmiotu;
        this.idUcznia =act.idUcznia;
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
