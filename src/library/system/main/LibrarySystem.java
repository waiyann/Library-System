/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.main;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.system.database.Database;
import library.system.util.MessageBox;

/**
 *
 * @author AMTCOMPUTER
 */
public class LibrarySystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        try {
            Database db = Database.getInstance();
        } catch (SQLException e) {
            MessageBox.showAndWaitErrorMessage("Connection Error", "Cannot connect to Database");
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
