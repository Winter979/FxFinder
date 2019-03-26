package fxfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Execute {
    
    private static String[] concatString(String[] one, String[] two)
    {
        String[] newS = new String[one.length + two.length];
        int ii = 0;
       
        for (String oneS : one) {
            newS[ii++] = oneS;
        }
        
        for (String twoS : two) {
            newS[ii++] = twoS;
        }
        
        return newS;
    }
    
    private boolean isEmpty(String text)
    {
        return !((text == null) || "".equals(text.trim()));
    }
    
    protected BufferedReader command(String[] command)
    {
        StringBuilder output = new StringBuilder();
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        String s = null;
        
        try{
            Process p;
            p = Runtime.getRuntime().exec(command);
            
            stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    
            System.out.println("Here is the Errors:");
            
        }
        catch(IOException e)
        {
            System.out.println("Error occured: \n" + e);
        }
        
        return stdInput;
    }

    protected String[] create(String name, String dir, String ext, Settings settings)
    {
        String[] command = new String[]{"find"};
        command = concatString(command, new String[] {dir, "-type", "f"});

        if(!settings.exact) //Not exact match. use regex
        {
            if(isEmpty(name))
            {
                if(isEmpty(ext))
                {
                    System.out.println("Name: present. Ext: present");
                    command = concatString(command, new String[]{"-iname", "*" + name + "*." + ext});
                }
                else
                {
                    System.out.println("Name: present. Ext: null");
                    command = concatString(command, new String[]{"-iname", "*" + name + "*.*"});
                }
            }
            else if(isEmpty(ext))
            {
                System.out.println("Name: null. Ext: present");
                command = concatString(command, new String[]{"-iname", "*." + ext});
            }
            else
            {
                System.out.println("Name: null. Ext: null");
                command = concatString(command, new String[]{"-iname", "*"});
            }
        }
        else //Name is to be exact match
        {
            if(isEmpty(name))
            {
                if(isEmpty(ext))
                {
                    System.out.println("Name: present. Ext: present");
                    command = concatString(command, new String[]{"-iname", name + "." + ext});
                }
                else
                {
                    System.out.println("Name: present. Ext: null");
                    command = concatString(command, new String[]{"-iname", name + ".*"});
                }
            }
            else if(isEmpty(ext))
            {
                System.out.println("Name: null. Ext: present");
                command = concatString(command, new String[]{"-iname", "*." + ext});
            }
            else
            {
                System.out.println("Name: null. Ext: null");
                command = concatString(command, new String[]{"-iname", "*"});
            }
        }
        
        if(!settings.hidden)
            command = concatString(command, new String[]{"!", "-iname", ".*","!", "-path", "*/.*"});
        
        if(settings.mod != -1)
            command = concatString(command, new String[]{"-mtime","-" + settings.mod});

        if(settings.acc != -1)
            command = concatString(command, new String[]{"-atime","-" + settings.acc});

        //                                                location| name|owner|accessed|modified|
        command = concatString(command, new String[]{"-printf","%h|%f|%u|%Aa, %Ab %Ad %AY|%Ta, %Tb %Td %TY\n"});
        
        return command;
    }
}
