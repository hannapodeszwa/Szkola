package pl.polsl;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class Window {
    private double defaultSize=600;
    //class
    public double manageClassFormWidth =defaultSize; //DO ZMIANY
    public double manageClassFormHeight =defaultSize;
    public  double addOrUpdateClassFormWidth = 450;
    public double addOrUpdateClassFormHeight = 300;
    //subject
    public  double manageSubjectsFormWidth =500;
    public  double manageSubjectsFormHeight = 450;
    public double addOrUpdateSubjectFormWidth = 400;
    public  double addOrUpdateSubjectFormHeight = 280;
    //teacher
    public double manageTeachersFormWidth = defaultSize;
    public double manageTeachersFormHeight = defaultSize;
    public  double addOrUpdateTeacherFormWidth = 470;
    public  double addOrUpdateTeacherFormHeight = 320;






    public static void resizeScene(AnchorPane window, double width, double height) {
        try {
            Stage stage = (Stage) window.getScene().getWindow();
            stage.setWidth(width);
            stage.setHeight(height);
        }
        catch(Exception e)
        {
            System.out.println("wyjatek");
        }
    }
}
