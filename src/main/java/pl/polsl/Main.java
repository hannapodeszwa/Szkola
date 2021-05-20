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
import java.util.logging.Logger;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        primaryStage.setTitle("Hello World");
        scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();*/

        scene = new Scene(loadFXML("signIn"), 300, 300);
        primaryStage.setTitle("Logowanie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        var url = Main.class.getResource("/view/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        MyManager m = MyManager.getInstance();


        launch(args);
    }
}
