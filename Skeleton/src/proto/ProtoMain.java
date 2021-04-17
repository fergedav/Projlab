package proto;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import logic.*;

public class ProtoMain {

    public static void main(String[] args) 
    {
      BufferedReader r = new BufferedReader (new InputStreamReader (System.in));

      // false => normal parancsertelmezo konzolos modban
      // true => lefuttatja az osszes tesztet, �s az out<teszt eset szama>.txt file-ba 
      // �rja az eredm�nyt. majd konzolos mode ba l�p.
      final boolean AUTOMODE = false;
      if(AUTOMODE)
      {
         for (int i = 0; i < 50; i ++)
         {
            String command = "loadTest Teszt"+i+".txt Out"+i+".txt";
            System.out.println(command);
            CommandHandler.processCommand(command);
         }
      }

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