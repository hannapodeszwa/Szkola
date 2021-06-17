package pl.polsl.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.GodzinyLekcji;
import pl.polsl.entities.Rozklady;

import javax.persistence.EntityManager;
import java.util.List;

public class ScheduleTableTeacher {

    EntityManager entityManager;
    Integer number;
    String hours;
    ScheduleTeacher mon;
    ScheduleTeacher tue;
    ScheduleTeacher wen;
    ScheduleTeacher thu;
    ScheduleTeacher fri;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public ScheduleTeacher getMon() {
        return mon;
    }

    public void setMon(ScheduleTeacher mon) {
        this.mon = mon;
    }

    public ScheduleTeacher getTue() {
        return tue;
    }

    public void setTue(ScheduleTeacher tue) {
        this.tue = tue;
    }

    public ScheduleTeacher getWen() {
        return wen;
    }

    public void setWen(ScheduleTeacher wen) {
        this.wen = wen;
    }

    public ScheduleTeacher getThu() {
        return thu;
    }

    public void setThu(ScheduleTeacher thu) {
        this.thu = thu;
    }

    public ScheduleTeacher getFri() {
        return fri;
    }

    public void setFri(ScheduleTeacher fri) {
        this.fri = fri;
    }




    public ObservableList<ScheduleTableTeacher> getSchedule(Integer id, ObservableList<GodzinyLekcji> lessonTime){


        List<Rozklady> results = (new ScheduleModel()).findByTeacher(id);


        ObservableList<ScheduleTableTeacher> list = FXCollections.observableArrayList();

        for(Integer i = 0; i<lessonTime.size();i++){
            list.add(new ScheduleTableTeacher());
            list.get(i).number= lessonTime.get(i).getNumer();
            list.get(i).hours = lessonTime.get(i).getPoczatek().getHours() + ":" +lessonTime.get(i).getPoczatek().getMinutes()+  " - " + lessonTime.get(i).getKoniec().getHours() + ":" +lessonTime.get(i).getKoniec().getMinutes();
        }


        for(Rozklady act : results){
            Integer num= (new LessonTimeModel()).getNumberById(act.getGodzina());
            switch (act.getDzien()){
                case "pon":
                    list.get(num-1).mon = new ScheduleTeacher(act);
                    break;
                case "wto":
                    list.get(num-1).tue = new ScheduleTeacher(act);
                    break;
                case "sro":
                    list.get(num-1).wen = new ScheduleTeacher(act);
                    break;
                case "czw":
                    list.get(num-1).thu = new ScheduleTeacher(act);
                    break;
                case "pia":
                    list.get(num-1).fri = new ScheduleTeacher(act);
                    break;
            }
        }

        return list;
    }


}
