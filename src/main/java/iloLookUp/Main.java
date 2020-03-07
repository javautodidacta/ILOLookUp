package iloLookUp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Locale locale = Locale.getDefault();
        fxmlLoader.setResources(ResourceBundle.getBundle("locale/interface", locale));
        Parent root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/interface.fxml"));
        primaryStage.setTitle("ILO LookUp");
        Scene scene = new Scene(root, 552, 117);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("¡Gracias por utilizar este programa!\n" +
                "Creado por Miguel Callejón Berenguer\n\n" +
                "Abriendo... espera un poco.\n\n" +
                "Versión 2 (marzo de 2020)\n" +
                "Novedades en la versión 2:\n" +
                "# Interfaz localizada (es, en, fr y ru). " +
                "El idioma de la interfaz depende del idioma de Windows. El inglés es predeterminado.\n" +
                "# Tiene en cuenta una redirección incorrecta en la página web.\n" +
                "# Se puede especificar la sección (INS, LILS, POL, FPA) en GB.");
        launch(args);
    }
}
