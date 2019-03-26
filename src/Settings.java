package fxfinder;

/**
 *
 * @author zazu
 */
public class Settings {
    public boolean hidden;
    public boolean sym; 
    public boolean exact;
    public int mod;
    public int acc;
    public int min; 
    public int max;
            
    public Settings(){};
    
    public Settings(boolean hidden, boolean sym, boolean exact, int mod, int acc, int min, int max)
    {
        this.hidden = hidden;
        this.sym = sym;
        this.exact = exact;
        this.mod = mod;
        this.acc = acc;
        this.min = min;
        this.max = max;
    }
    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }
    public void setSym(boolean sym)
    {   
        this.sym = sym;
        
    }
    public void setExact(boolean exact)
    {
        this.exact = exact;
    }
    
    public void setMod(String mod) throws InvalidSearchException
    {
        try{
            if(mod.equals("-") || "".equals(mod.trim()) )
                this.mod = -1;
            else{
                this.mod = Integer.parseInt(mod);
                if(this.mod < 0)
                    throw new InvalidSearchException("Invalid Last Modified value");
            }
                
        }
        catch(NumberFormatException e)
        {
            throw new InvalidSearchException("Invalid Last Modified value");
        }
    }
    public void setAcc(String acc) throws InvalidSearchException
    {
        try{
            if(acc.equals("-")|| "".equals(acc.trim()))
                this.acc = -1;
            else{
                this.acc = Integer.parseInt(acc);
                if(this.acc < 0)
                    throw new InvalidSearchException("Invalid Date Accessed value");
            }
                
        }
        catch(NumberFormatException e)
        {
            throw new InvalidSearchException("Invalid Date Accessed value");
        }
    }
    public void setMin(String min) throws InvalidSearchException
    {
        try{
            if(min.equals("-")|| "".equals(min.trim()))
                this.min = -1;
            else{
                this.min = Integer.parseInt(min);
                if(this.min < 0)
                    throw new InvalidSearchException("Invalid Minimum Depth value");
            }
                
        }
        catch(NumberFormatException e)
        {
            throw new InvalidSearchException("Invalid Minimum Depth value");
        }
    }
    public void setMax(String max) throws InvalidSearchException
    {
        try{
            if(max.equals("-")|| "".equals(max.trim()))
                this.max = -1;
            else{
                this.max = Integer.parseInt(max);
                if(this.max < 0)
                    throw new InvalidSearchException("Invalid Maximum Depth value");
            }
                
        }
        catch(NumberFormatException e)
        {
            throw new InvalidSearchException("Invalid Maximum Depth value");
        }
    }
    
    
    
    
    
            
}
