package proto;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import logic.*;

public class ProtoMain {

    public static void main(String[] args) 
    {
      BufferedReader r = new BufferedReader (new InputStreamReader (System.in));
      while(true)
      {
         try {
            String line = r.readLine();
            CommandHandler.processCommand(line);
         } catch (Exception e) {
            //TODO: handle exception
         }
         
      }
  
    }
}