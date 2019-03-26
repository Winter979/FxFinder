package fxfinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableRow;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    ObservableList<Result> list = FXCollections.observableArrayList();
    ObservableList<String> advList = FXCollections.observableArrayList ();
    ObservableList<History> historyList = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane mainPane;    
    @FXML
    private BorderPane paneNormal;
    @FXML
    private BorderPane paneDeveloper;
    @FXML
    private BorderPane paneHistory;
    @FXML
    private Pane leftPane;
    @FXML
    private Label lbl_count;
    @FXML
    private ComboBox txt_ext;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_dir;
    @FXML
    private TableView<Result> table;
    @FXML
    private TableColumn<Result,String> col_name;
    @FXML
    private TableColumn<Result,String> col_dir;
    @FXML
    private TableColumn<Result,String> col_owner;
    @FXML
    private TableColumn<Result,String> col_mod;
    @FXML
    private TableColumn<Result,String> col_access;
    @FXML
    private Button searchBtn;
    
    //ADVANCED
    @FXML
    private ListView advResults;
    @FXML
    private TextField txtAdv;
    @FXML
    private Label lbl_countAdv;
    @FXML
    private CheckMenuItem showWarning;
    
    //HISTORY
    @FXML
    private TableView<History> historyResults;
    @FXML
    private Button historyClear;
    @FXML
    private Label historyCount;
    @FXML
    private TableColumn<Result,String> col_Hname;
    @FXML
    private TableColumn<Result,String> col_Hext;
    @FXML
    private TableColumn<Result,String> col_Hdir;
    @FXML
    private TableColumn<Result,String> col_Hmod;
    @FXML
    private TableColumn<Result,String> col_Hacc;
    @FXML
    private TableColumn<Result,String> col_Hmin;
    @FXML
    private TableColumn<Result,String> col_Hmax;
    @FXML
    private TableColumn<Result,CheckBox> col_Hhidden;
    @FXML
    private TableColumn<Result,CheckBox> col_Hsym;
    @FXML
    private TableColumn<Result,CheckBox> col_Hexact;
    
    //SETTTINGS
    @FXML
    private CheckBox setHistory;
    @FXML
    private CheckBox setHidden;
    @FXML
    private CheckBox setExact;
    @FXML
    private CheckBox setSymLink;
    @FXML
    private TextField setMod;
    @FXML
    private TextField setAccess;
    @FXML
    private TextField setMinDepth;
    @FXML
    private TextField setMaxDepth;
    
    private Execute execute;
    private DirectoryChooser chooser;
    private boolean simple = false;
    private boolean advanced = false;
    
    
    @FXML
    private void enterPressed1(KeyEvent evt)
    {
      if(evt.getCode().equals(KeyCode.ENTER))
      {
         executeSearch();
      }
    }
    
    @FXML
    private void enterPressed2(KeyEvent evt)
    {
      if(evt.getCode().equals(KeyCode.ENTER))
      {
         advancedSearch();
      }
    }
    
    @FXML
    private void save()
    {
       FileChooser fileChooser = new FileChooser();
       FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Result files (*.res)", "*.res");
       fileChooser.getExtensionFilters().add(extensionFilter);
       File file = fileChooser.showSaveDialog(new Stage());
       if (file != null) {
          if(!file.getName().endsWith(".res"))
             file = new File(file.getAbsolutePath() + ".res");
          try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
             out.writeObject(new ArrayList<>(list));
          } catch (Exception exc) {
          }
       }
       
       // write object to file
    }

    @FXML
    private void load()
    {
       FileChooser fileChooser = new FileChooser();
       FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Result files (*.res)", "*.res");
       fileChooser.getExtensionFilters().add(extensionFilter);
       File file = fileChooser.showOpenDialog(new Stage());
       if (file != null) {
          try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
             List<Result> loadResults = (List<Result>) in.readObject() ;
             list.setAll(loadResults);
          } catch (Exception exc) {
          }
       }

    }
    
    @FXML
    private void saveAdvanced()
    {
      FileChooser fileChooser = new FileChooser();
       FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Result files (*.sve)", "*.sve");
       fileChooser.getExtensionFilters().add(extensionFilter);
       File file = fileChooser.showSaveDialog(new Stage());
       if (file != null) {
          if(!file.getName().endsWith(".sve"))
             file = new File(file.getAbsolutePath() + ".sve");
          try (FileWriter writer = new FileWriter(file)) 
          {
             advList.forEach((str) -> {
                try {
                   writer.write(str + "\n");
                } catch (IOException ex) {
                   Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
             });
          } 
          catch (Exception exc) 
          {
          }
       }
    }

    @FXML
    private void loadAdvaned()
    {
       FileChooser fileChooser = new FileChooser();
       FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Result files (*.sve)", "*.sve");
       fileChooser.getExtensionFilters().add(extensionFilter);
       File file = fileChooser.showOpenDialog(new Stage());
       if (file != null) {
          try (Scanner sc = new Scanner(file)) {
            int count = 0;
            while(sc.hasNextLine())
            {
               advList.add(sc.nextLine());
               count++;
            }
            lbl_countAdv.setText(""+count);       
          } catch (Exception exc) {
          }
       }
    }

    @FXML
    private void openHistory()
    {
       paneDeveloper.setVisible(false);
       paneHistory.setVisible(true);
       paneNormal.setVisible(false);
    }
    
    private void saveHistory()
    {
       
      File historyFile = new File("resources/history.ser");

       System.out.println("saving");
      
         try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(historyFile))) {
            out.writeObject(new ArrayList<>(historyList));
         } 
         catch (Exception exc) {
            System.out.print(exc.getMessage());
         }

         System.out.println("HISTORY BEEN SAVED");
    }

    private void loadHistory()
    {

      File historyFile = new File("resources/history.ser");

       System.out.println("loading");
      
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(historyFile))) {
          List<History> loadResults = (List<History>) in.readObject() ;
          
          loadResults.forEach((hist) -> {
             hist.reset();
             historyCount.setText(""+(Integer.parseInt(historyCount.getText())+1));
          });
          
          historyList.setAll(loadResults);
       } catch (Exception exc) {
          System.out.print(exc.getMessage());
       }
    
    }


    @FXML
    private void openAbout()
    {
        try {
            Stage st = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));

            Parent sceneMain = loader.load();

            //AboutController controller = loader.<AboutController>getController();

            Scene scene = new Scene(sceneMain);
            st.setResizable(false);
            st.setScene(scene);
            st.show();
            

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void openNormal()
    {
        paneDeveloper.setVisible(false);
        paneHistory.setVisible(false);
        paneNormal.setVisible(true);
    }
    
    @FXML
    private void openDeveloper()
    {
        if(showWarning.isSelected())
        {
            try {
                Stage st = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DeveloperMode.fxml"));

                Parent sceneMain = loader.load();

                DeveloperModeController controller = loader.<DeveloperModeController>getController();
                controller.setPanes(paneNormal, paneDeveloper);

                Scene scene = new Scene(sceneMain);
                st.setScene(scene);
                st.show();
                
                paneNormal.setVisible(true);
                paneDeveloper.setVisible(false);
                paneHistory.setVisible(false);
                simple = true;
                advanced = false;

            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            paneDeveloper.setVisible(true);
            paneNormal.setVisible(false);
            paneHistory.setVisible(false);
            simple = true;
            advanced = false;
        }
    }
    
    @FXML
    private void setDefault()
    {
        setHidden.setSelected(false);
        setExact.setSelected(false);
        setSymLink.setSelected(false);
        setMod.setText("-");
        setAccess.setText("-");
        setMinDepth.setText("-");
        setMaxDepth.setText("-");
        setHistory.setSelected(true);
    }
    
    @FXML
    private void setDir(ActionEvent event)
    {
        String option = ((MenuItem)(event.getSource())).getText();
        String home = System.getProperty("user.home");
        switch(option)
        {
            case "Documents":
                txt_dir.setText(home+"/Documents");
            break;
            case "Downloads":
                txt_dir.setText(home+"/Downloads");
            break;
            case "Videos":
                txt_dir.setText(home+"/Videos");
            break;
            case "Pictures":
                txt_dir.setText(home+"/Pictures");
            break;
          case "Home":
                txt_dir.setText(home);
            break;
            case "Custom":
                customDir();
                
            break;
        }
    }
    
    
    private void customDir()
    {
       System.out.println("Custom directory will be chosen");
       
       File selectedDirectory = chooser.showDialog(new Stage());
                
       if(selectedDirectory == null){
          
       }else{
          txt_dir.setText(selectedDirectory.getAbsolutePath());
       } 
    }
    
    @FXML
    private void showSettings()
    {
        if(paneNormal.getLeft() == null)
        {
            paneNormal.setLeft(leftPane);
        }
        else
        {
            paneNormal.setLeft(null);
        }
    }
    
    @FXML
    private void clear()
    {
        list.clear();
        lbl_count.setText("0");
    }
    
    @FXML
    private void clearHistory()
    {
       historyList.clear();
       historyCount.setText("0");
       saveHistory();
    }
    
    private void clearSingleHistory(History history)
    {
      historyList.remove(history);
      saveHistory();
    }

    @FXML
    private void advancedSearch()
    {
        clearAdv();
        BufferedReader output = execute.command(txtAdv.getText().split(" "));
        int count = 0;
        try {
            
            String s = "";
            while((s = output.readLine())!= null)
            {
                advList.add(s);
                count++;
            }
        }
        catch (IOException ex) {
            
        }
        finally
        {
            lbl_countAdv.setText(""+count);
            //Write history
        }  
    }
    
    @FXML
    private void clearAdv()
    {
        advList.clear();
        lbl_countAdv.setText("0");
    }
    
    @FXML
    private void executeSearch()
    {
        try{  
            list.clear();
            lbl_count.setText("0");
            
            Settings settings = new Settings();
                settings.setHidden(setHidden.isSelected());
                settings.setExact(setExact.isSelected());
                settings.setSym(setSymLink.isSelected());
                settings.setMod(setMod.getText());
                settings.setAcc(setAccess.getText());
                settings.setMin(setMinDepth.getText());
                settings.setMax(setMaxDepth.getText());
                
            String[] command = execute.create(txt_name.getText(), txt_dir.getText(), txt_ext.getEditor().getText(), settings);
            
            BufferedReader output = execute.command(command);

            String s = "";
            String[] ss;

            int count = 0;
            try {
                while((s = output.readLine())!= null)
                {
                    ss = s.split("\\|");
    //                ss[0] = location
    //                ss[1] = Name
    //                ss[2] = Owner
    //                ss[3] = Accessed
    //                ss[4] = Modified
                    list.add(new Result(ss[0], ss[1], ss[2], ss[3], ss[4]));
                    count++;
                }
                System.out.println("Add to list");
                History history = new History(txt_name.getText(), txt_dir.getText(), txt_ext.getEditor().getText(), settings);
                

                if(setHistory.isSelected())
                {
                  historyList.add(history);
                  saveHistory();
                  historyCount.setText(""+(Integer.parseInt(historyCount.getText())+1));
                }
                
            }
            catch (IOException ex) {
                throw new InvalidSearchException("An unknonw IO Exception occured. Please try again.");
            }
            catch (NumberFormatException e)
            {
                throw new InvalidSearchException("An unknown error has occured. Please contact us regarding what happened.");
            }
            finally
            {
                lbl_count.setText(""+count);
                if(count == 0)
                    table.setPlaceholder(new Label("No results were found. Try a different search perhaps."));
                //Write history
            }  
        }
        catch(InvalidSearchException e)
        {
            //DISPLAY ERROR 
            table.setPlaceholder(new Label(e.getMessage()));
            System.out.println(e.getMessage());
        }
    }
    
    public void runHistory(History history)
    {
       txt_name.setText(history.name);
       txt_dir.setText(history.dir);
       txt_ext.getEditor().setText(history.ext);
       setHidden.setSelected(history.hidden.isSelected());
       setExact.setSelected(history.exact.isSelected());
       setSymLink.setSelected(history.sym.isSelected());
       setAccess.setText(history.acc);
       setMinDepth.setText(history.min);
       setMaxDepth.setText(history.max);   
       
       paneDeveloper.setVisible(false);
        paneHistory.setVisible(false);
        paneNormal.setVisible(true); 
        simple = true;
        advanced = false;
        
        boolean saveHis = setHistory.isSelected();
        setHistory.setSelected(false);
        executeSearch();
        setHistory.setSelected(saveHis);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_dir.setCellValueFactory(new PropertyValueFactory<>("location"));
        col_mod.setCellValueFactory(new PropertyValueFactory<>("modified"));
        col_owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        col_access.setCellValueFactory(new PropertyValueFactory<>("accessed")); 

        //History     
        col_Hname.setCellValueFactory(new PropertyValueFactory<>("name"));    
        col_Hext.setCellValueFactory(new PropertyValueFactory<>("ext"));    
        col_Hdir.setCellValueFactory(new PropertyValueFactory<>("dir"));    
        col_Hmod.setCellValueFactory(new PropertyValueFactory<>("mod"));    
        col_Hacc.setCellValueFactory(new PropertyValueFactory<>("acc"));    
        col_Hmin.setCellValueFactory(new PropertyValueFactory<>("min"));    
        col_Hmax.setCellValueFactory(new PropertyValueFactory<>("max"));    
        col_Hhidden.setCellValueFactory(new PropertyValueFactory<>("hidden"));    
        col_Hsym.setCellValueFactory(new PropertyValueFactory<>("sym"));    
        col_Hexact.setCellValueFactory(new PropertyValueFactory<>("exact"));    

        table.setItems(list);
        table.requestFocus();
        
        table.setOnKeyPressed( new EventHandler<KeyEvent>()
         {
           @Override
           public void handle( final KeyEvent keyEvent )
           {
             final Result selectedItem = table.getSelectionModel().getSelectedItem();

             if ( selectedItem != null )
             {
               if ( keyEvent.getCode().equals( KeyCode.DELETE ) )
               {
                 table.getItems().remove(selectedItem);
                 lbl_count.setText(""+(Integer.parseInt(lbl_count.getText())-1));     
               }

                //... other keyevents
             }
           }
         } );
        
        table.setRowFactory((TableView<Result> tableView) -> {
           final TableRow<Result> row = new TableRow<>();
           final ContextMenu contextMenu = new ContextMenu();
           final MenuItem removeResult = new MenuItem("Remove");
           final MenuItem openResult = new MenuItem("Launch File");
           final MenuItem openLocation = new MenuItem("Open Location");
           
           row.setOnMouseClicked(event -> {
              if (event.getClickCount() == 2 && (! row.isEmpty()) )
              {
                 row.getItem().openFile();
              }
           });
           
           openResult.setOnAction((ActionEvent event) -> {
              row.getItem().openFile();
           });
           
           openLocation.setOnAction((ActionEvent event) -> {
              row.getItem().openLocation();
           });
           
           removeResult.setOnAction((ActionEvent event) -> {
              table.getItems().remove(row.getItem());
              lbl_count.setText(""+(Integer.parseInt(lbl_count.getText())-1));
           });
           contextMenu.getItems().add(openResult);
           contextMenu.getItems().add(openLocation);
           contextMenu.getItems().add(removeResult);
           
           row.contextMenuProperty().bind(
                   Bindings.when(row.emptyProperty())
                           .then((ContextMenu)null)
                           .otherwise(contextMenu)
           );
           return row ;
        });  
        
        historyResults.setItems(historyList);
        
        historyResults.setOnKeyPressed( new EventHandler<KeyEvent>()
         {
           @Override
           public void handle( final KeyEvent keyEvent )
           {
             final History selectedItem = historyResults.getSelectionModel().getSelectedItem();

             if ( selectedItem != null )
             {
               if ( keyEvent.getCode().equals( KeyCode.DELETE ) )
               {
                 clearSingleHistory(selectedItem);
                  historyCount.setText(""+(Integer.parseInt(historyCount.getText())-1));    
               }

                //... other keyevents
             }
           }
         } );
        
        historyResults.setRowFactory((TableView<History> tableView) -> {
           final TableRow<History> row = new TableRow<>();
           final ContextMenu contextMenu = new ContextMenu();
           final MenuItem redoHistory = new MenuItem("Launch");
           final MenuItem removeHistory = new MenuItem("Remove");
           
           row.setOnMouseClicked(event -> {
              if (event.getClickCount() == 2 && (! row.isEmpty()) )
              {
                 runHistory(row.getItem());
              }
           });
           
//           row.setOnKeyReleased(evt -> {
//              System.out.println(evt.getCode());
//              if(evt.getCode() == KeyCode.DELETE)
//              {
//                 clearSingleHistory(row.getItem());
//                  historyCount.setText(""+(Integer.parseInt(historyCount.getText())-1));
//              }
//           });
          
           redoHistory.setOnAction((ActionEvent event) -> {
              runHistory(row.getItem());
           });
           
           removeHistory.setOnAction((ActionEvent event) -> {
              clearSingleHistory(row.getItem());
              historyCount.setText(""+(Integer.parseInt(historyCount.getText())-1));
           });
           contextMenu.getItems().add(redoHistory);
           contextMenu.getItems().add(removeHistory);
           
           row.contextMenuProperty().bind(
                   Bindings.when(row.emptyProperty())
                           .then((ContextMenu)null)
                           .otherwise(contextMenu)
           );
           return row ;
        });  
        
        table.setPlaceholder(new Label("Select \"Search\" To start a search based on your settings."));
        txt_ext.getItems().addAll(
                "jpg",
                "png",
                "pdf",
                "mp3",
                "mp4",
                "txt",
                "odt",
                "ppt"
        );
        loadHistory();
        execute = new Execute();
        
        chooser = new DirectoryChooser();
        chooser.setTitle("Title here");
        
        showWarning.setSelected(true);
        
        paneDeveloper.setVisible(false);
        paneNormal.setVisible(true);
        simple = true;
        advanced = false;
        paneHistory.setVisible(false);
        
        advResults.setItems(advList);
        
        searchBtn.requestFocus();
        
        paneNormal.setLeft(null);
        txt_dir.setText(System.getProperty("user.home"));
        
        
    }    
    
}
