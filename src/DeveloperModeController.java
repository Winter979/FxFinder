package fxfinder;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zazu
 */
public class DeveloperModeController implements Initializable {

    @FXML
    private Button cont;
    @FXML
    private Button retn;
    
    private BorderPane normal;
    private BorderPane developer;
    
    BorderPane pane;
    
    public void openDeveloper()
    {
        Stage stage = (Stage) retn.getScene().getWindow();
        stage.close();
        developer.setVisible(true);
        normal.setVisible(false);
    }
            
    public void backToNormal()
    {
        Stage stage = (Stage) retn.getScene().getWindow();
        stage.close();
        normal.setVisible(true);
        developer.setVisible(false);
        
    }
    
    void setPanes(BorderPane normal, BorderPane developer) 
    {
        this.normal = normal;
        this.developer = developer;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        System.out.println("Do something");
        //Stage stage = (Stage)mainPane.getScene().getWindow();
//        stage.setOnCloseRequest((WindowEvent we) -> {
//            pane.setVisible(true);
//        });       
    }
}