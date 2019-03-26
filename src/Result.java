package fxfinder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

/**
 *
 * @author zazu
 */
public final class Result implements Serializable{
    
    public final String modified;
    public final String name;
    public final String location;
    public final String owner;
    public final String accessed;
    
    public Result(String location, String name, String owner, String accessed, String modified)
    {
        this.name = name;
        this.location = location;
        this.modified = modified;   
        this.owner = owner;
        this.accessed = accessed;
    }
    
    public void openFile()
    {
       try {
          Runtime.getRuntime().exec(new String[]{"xdg-open",new File(location).getAbsolutePath() + "/" + name});
       } catch (IOException ex) {
          Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public void openLocation()
    {
       try {
          Runtime.getRuntime().exec(new String[]{"xdg-open",new File(location).getAbsolutePath()});
       } catch (IOException ex) {
          Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public String getModified() {
       return modified;
    }
    
    public String getAccessed() {
       return accessed;
    }
    
    public String getName() {
       return name;
    }
    
    public String getLocation() {
       return location;
    }
    
    public String getOwner() {
       return owner;
    }
}
