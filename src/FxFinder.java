package fxfinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxFinder extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        stage.setTitle("The Finder");
        
        stage.show();
        
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight()+40);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
