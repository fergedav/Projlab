package proto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logic.*;

public class ProtoMain {

    public static void main(String[] args) 
    {
        Controller c = Controller.getInstance();

        Asteroid a = new Asteroid(0, 0, 0, new Uran(), false);
        c.addOrbit(a);
        Settler s = new Settler(a);
        c.addSettler(s);
        s.mining();
        int x = 0;

        try {
            FileOutputStream fileOut =
            new FileOutputStream("test.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(c);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in test.ser");
         } catch (IOException i) {
            i.printStackTrace();
         }

         try {
            FileInputStream fileIn = new FileInputStream("test.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Controller c2 = (Controller) in.readObject();
            in.close();
            fileIn.close();
         } catch (IOException i) {
            i.printStackTrace();
            return;
         } catch (ClassNotFoundException e) {
            System.out.println("Employee class not found");
            e.printStackTrace();
            return;
         }//бых
    }
}