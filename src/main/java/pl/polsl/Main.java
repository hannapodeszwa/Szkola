package pl.polsl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.var;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(loadFXML("signIn"), 600, 600);
        primaryStage.setTitle("Szkola");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL url = Main.class.getResource("/view/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        MyManager m = MyManager.getInstance();


        launch(args);
    }
}
