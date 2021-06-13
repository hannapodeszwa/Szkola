package pl.polsl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.controller.*;
import pl.polsl.controller.common.ChangePasswordController;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class Main extends Application {
    private static Scene scene;
    private static Stage stage;
    private static double defaultWidth = 600;
    private static double defaultHeight =600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        scene = new Scene(loadFXML("common/signIn"), defaultWidth, defaultHeight);
        primaryStage.setTitle("Szkola");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml, Map params, double... size) throws IOException {
        scene.setRoot(loadFXML(fxml, params));
        resizeScene(size);
    }
    public static void setRoot(String fxml,double... size) throws IOException {
        scene.setRoot(loadFXML(fxml));
        resizeScene(size);
    }
    public static void resizeScene(double[] size) {
        if (size.length >= 2) {
            stage.setWidth(size[0]);
            stage.setHeight(size[1]);
        } else {
            stage.setWidth(defaultWidth);
            stage.setHeight(defaultHeight);
         }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL url = Main.class.getResource("/view/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        return fxmlLoader.load();
    }

    private static Parent loadFXML(String fxml, Map params) throws IOException {
        URL url = Main.class.getResource("/view/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent ret = fxmlLoader.load();
        Object ob = fxmlLoader.getController();
        if (ob != null && ob instanceof ParametrizedController)
            ((ParametrizedController)ob).receiveArguments(params);
        return ret;
    }

    public static void main(String[] args) {
        MyManager m = MyManager.getInstance();
        launch(args);
    }
}
