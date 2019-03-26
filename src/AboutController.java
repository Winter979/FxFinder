/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxfinder;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zazu
 */
public class AboutController implements Initializable {

   @FXML
   private ImageView logo;
   
    @FXML
    private AnchorPane ap;   
    /**
     * Initializes the controller class.
     */
    @FXML
    private void close()
    {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("yep");
        
//        
//
//        Image image = new Image("../../Logo.png");
//        
//        logo.setImage(image);
    }    
    
}
