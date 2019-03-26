package fxfinder;

import java.io.Serializable;
import javafx.scene.control.CheckBox;

public class History implements Serializable{
   
   public String name;
   public String ext;
   public String dir;
   public String mod;
   public String acc;
   public String min;
   public String max;
   public transient CheckBox hidden;
   public boolean b1;
   public transient CheckBox sym;
   public boolean b2;
   public transient CheckBox exact;
   public boolean b3;
   
   public History(String name, String dir, String ext, Settings settings)
   {
      this.name = name;
      this.ext = ext;
      this.dir = dir;

      if(settings.mod == -1)
      {
         this.mod = "-";
      }
      else
      {
         this.mod = settings.mod + "";
      }

      if(settings.acc == -1)
      {
         this.acc = "-";
      }
      else
      {
         this.acc = settings.acc + "";
      }

      if(settings.min == -1)
      {
         this.min = "-";
      }
      else
      {
         this.min = settings.min + "";
      }

      if(settings.max == -1)
      {
         this.max = "-";
      }
      else
      {
         this.max = settings.max + "";
      }

         b1=settings.hidden;
         b3=settings.exact;
         b2=settings.sym;
      
         reset();
   }
   
   public void reset()
   {
      hidden = new CheckBox();
      hidden.setSelected(b1);      
      sym = new CheckBox();
      sym.setSelected(b2);
      exact = new CheckBox();
      exact.setSelected(b3);    
   }
   
   public CheckBox getHidden()
   {
      return hidden;
   }
   public CheckBox getSym()
   {
      return sym;
   }
   public CheckBox getExact()
   {
      return exact;
   }
   public String getMod()
   {
      return mod;
   }
   public String getAcc()
   {
      return acc;
   }
   public String getMin()
   {
      return min;
   }
   public String getMax()
   {
      return max;
   }
   public String getDir()
   {
      return dir;
   }
   public String getName()
   {
      return name;
   }
   public String getExt()
   {
      return ext;
   }
}
