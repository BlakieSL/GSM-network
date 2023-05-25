import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class BinarySaving {
    public void write(ArrayList<String> messages, VBD vbd){
        try(OutputStream os = new FileOutputStream("binary")){
            int id = vbd.getID();
            int size = messages.size();
            String strId = Integer.toString(id);
            String strSize = Integer.toString(size);
            os.write(strId.getBytes());
            os.write(strSize.getBytes());
            for(String mesage : messages){
                os.write(mesage.getBytes());
            }
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
