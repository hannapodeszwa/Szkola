package pl.polsl.controller.administratorActions.parent;

import javafx.scene.control.CheckBox;
import pl.polsl.entities.Rodzice;

public class ParentChoice extends Rodzice{
    public CheckBox choose = new CheckBox();

    public void setChoose(CheckBox choose) {//trzeba coś takiego zrobić żeby był checkbox w tablicy
this.choose=choose;
    }

    public CheckBox getChoose() {
        return choose;
    }
}
