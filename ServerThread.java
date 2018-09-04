
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ServerThread extends Thread{
    
    Socket socket;
    ServerThread (Socket socket){
        this.socket = socket;
    }
    
    public void run(){
        try {
            String message = null;
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((message = bufferedreader.readLine()) != null) {
                System.out.println("Incoming Client Message: " + message);
            }
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }
    
}