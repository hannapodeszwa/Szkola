package pl.polsl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.controller.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(loadFXML("common/signIn"), 600, 600);
        primaryStage.setTitle("Szkola");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(String fxml, Map params) throws IOException {
        scene.setRoot(loadFXML(fxml, params));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL url = Main.class.getResource("/view/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        return fxmlLoader.load();
    }

    private static Parent loadFXML(String fxml, Map params) throws IOException {
        URL url = Main.class.getResource(fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent ret = fxmlLoader.load();
        Object ob = fxmlLoader.getController();
        if (ob != null && ob.getClass() == (new AddOrUpdateStudentsController()).getClass())
            ((AddOrUpdateStudentsController)ob).passArguments(params);
        else if (ob != null && ob.getClass() == (new AddOrUpdateTeachersController()).getClass())
            ((AddOrUpdateTeachersController)ob).passArguments(params);
        else if (ob != null && ob.getClass() == (new AddOrUpdateSubjectController()).getClass())
            ((AddOrUpdateSubjectController)ob).passArguments(params);
        else if (ob != null && ob.getClass() == (new AddOrUpdateClassController()).getClass())
            ((AddOrUpdateClassController)ob).passArguments(params);
        return ret;
    }

    public static void main(String[] args) {
        MyManager m = MyManager.getInstance();


        launch(args);
    }
}
