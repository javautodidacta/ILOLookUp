/*
 * This work is licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 *
 */

/*
 * This work is licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
 *
 */

package iloLookUp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("¡Gracias por utilizar este programa!\n" +
                "Creado por Miguel Callejón Berenguer\n\n" +
                "Abriendo... espera un poco.\n\n" +
                "Versión 3 (marzo de 2020)\n" +
                "Novedades en la versión 3:\n" +
                "# Recursos para traductores.\n" +
                "# Logo y colores de la OIT.\n" +
                "# Nuevas opciones: ILO mail, Rodis y Multitrans Web.");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Locale locale = Locale.getDefault();
        fxmlLoader.setResources(ResourceBundle.getBundle("locale/interface", locale));
        Parent root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/interface.fxml"));
        primaryStage.setTitle("ILO LookUp");
        Scene scene = new Scene(root, 774, 130);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
