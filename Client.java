
import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client{
    
    public static void main(String[] args) throws UnknownHostException, IOException{
        String name = args[0];
        String ServerIP = args[1];
        
        String PORTIn = args[2];
        int PortNumber = Integer.parseInt(PORTIn);
        
        Socket socket = new Socket(ServerIP, PortNumber);
        String readerInput = "c";
        
        //Gets question from server and prints it on console
        BufferedReader bufferedreaderfromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(bufferedreaderfromClient.readLine());
        
        
        while (!readerInput.equals("A") && !readerInput.equals("B") && !readerInput.equals("C")){
        //printwriter.println(name);
            System.out.println("Enter option : ");
        
        //Gets option from console
            BufferedReader bufferedreaderfromCommandPrompt = new BufferedReader(new InputStreamReader(System.in));
            readerInput = bufferedreaderfromCommandPrompt.readLine();
        }
        
        //To send back option
        PrintWriter printwriter = new PrintWriter(socket.getOutputStream(),true);
        printwriter.println(readerInput);
        printwriter.println(name);
    
        
        System.out.println(bufferedreaderfromClient.readLine());
        
        
        /*while(true){
            
            //Sends option to Server
            String readerInput = bufferedreaderfromCommandPrompt.readLine();
            printwriter.println(readerInput);
            
            //Server communication - echos client message
            System.out.println(bufferedreaderfromClient.readLine());
        }*/
        
    }
    
}