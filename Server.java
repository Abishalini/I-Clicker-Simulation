
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server{
    
    public static int PORT;
    public int A = 0;
    public static String ques = "Sup? ";
    public static String correct;
    public static int ClientsConnected = 0;
    public static int MaxClients;
    
    public static void main(String[] arg) throws IOException{
        String PORTin = arg[0];
        PORT = Integer.parseInt(PORTin);
        
        String Maxclients = arg[1];
        MaxClients = Integer.parseInt(Maxclients);
        
        new Server().runServer();

    }
    
    public void runServer() throws IOException{
        
        ServerSocket serversocket = new ServerSocket(PORT);
        System.out.println("Server up & ready for connection:");
        
        //Get Server IP address:
        InetAddress ServerIP;
        try {
            //ServerIP = 127.0.0.1;
            ServerIP = InetAddress.getLocalHost();
            System.out.println("The server's IP address is " + ServerIP.getHostAddress() + "\n");
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Enter question with options : ");
        BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
        ques = reader.readLine();
        
        System.out.println("What is the correct option? ");
        BufferedReader reader2 = new BufferedReader (new InputStreamReader(System.in));
        correct = reader2.readLine();
        
        while (ClientsConnected <= MaxClients){
            Socket socket = serversocket.accept();
            new ServerThread(socket).start();
            ClientsConnected++;
        }
    }

}

class ServerThread extends Thread{
    
    Server ServerValues = new Server();
    int A = ServerValues.A;
    String ques = ServerValues.ques;
    String correct = ServerValues.correct;
    
    Socket socket;
    ServerThread (Socket socket){
        this.socket = socket;
    }
    
    public void run(){
        try {
            String message = null;
            
            //Send question to Clients
            PrintWriter printwriter = new PrintWriter(socket.getOutputStream(),true);
            printwriter.println(ques);
            printwriter.flush();
            
            //To get option from Client
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //System.out.println("user '" + bufferedreader.readLine() + "is now connected to the server..");
            
            //To write the option into file - GradeBook.txt
            BufferedWriter writer = new BufferedWriter(new FileWriter("GradeBook.txt", true));
            BufferedWriter writename = new BufferedWriter(new FileWriter("Name.txt",true));
            
            //while ((message = bufferedreader.readLine()) != null) {
            
            message = bufferedreader.readLine();
                //write into file
                writer.write(message + "\n");
                writer.flush();
                
                //console output of option selescted by Client
                System.out.println("Incoming Client Message: " + message);
            
            String name = bufferedreader.readLine();
            writename.write(name + "\n");
            writename.flush();
                
                
            //Server communication back to Client (This should be modified to send whether option was right or wrong)
            try {
            Thread.sleep(10000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            
            String line;
            int A = 0;
            int B = 0;
            int C = 0;
            FileReader filereader = new FileReader("GradeBook.txt");
            BufferedReader br = new BufferedReader(filereader);
            while ((line = br.readLine()) != null) {
                if (line.equals("A"))
                    A++;
                else if (line.equals("B"))
                    B++;
                else if (line.equals("C"))
                    C++;
            }
            
            if (message.equals(correct)){
                printwriter.println("Your answer is correct!!   A -> " + A + " B -> "+ B + " C -> "+ C);
            }
            else
                printwriter.println("Your answer is wrong!!   A -> " + A + " B -> "+ B + " C -> "+ C);
            //}
            
            
            
            writer.close();
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }
    
}

