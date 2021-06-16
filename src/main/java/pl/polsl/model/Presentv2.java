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
        Usp.setSelected(czyUsprawiedliwiona != 0);
    }

    public CheckBox getCzyUsp() {
        Usp.setSelected(czyUsprawiedliwiona != 0);
        return Usp;
    }

}
