
import java.net.*;
import java.util.Date;
import java.io.*;

public class TCPServer {
	
	
	private Socket clientSoc;
	private ServerSocket serverSoc;    
   	private int portNumber;
   	
   	public TCPServer(int port){
   		this.portNumber = port;
   		
		try {
			this.serverSoc = new ServerSocket(this.portNumber);
		} catch (IOException e) {
			System.out.println("Failed to start ServerSocket");
			e.printStackTrace();
			return;
		}
		
		System.out.println("\nTCP Server Started on Port Number: " + this.portNumber);
		
		
		while(true)
        {
            System.out.println("Waiting for Connection ...");
            try {
				this.clientSoc = serverSoc.accept();
				TCPServerThread serverThread = new TCPServerThread(clientSoc);
			} catch (IOException e) {
				System.out.println("Failed to start connection with new client");
				e.printStackTrace();
			}
            
        }
		
		
   	}
}
		



class TCPServerThread extends Thread
{
    private Socket ClientSoc;
    private ObjectInputStream InObject;
    private DataInputStream InMethod;
    private DataOutputStream dout;

    public TCPServerThread(Socket soc)
    {
        try
        {
            this.ClientSoc = soc;
            
            this.InObject = new ObjectInputStream (ClientSoc.getInputStream());
            this.InMethod = new DataInputStream(ClientSoc.getInputStream());
            this.dout = new DataOutputStream(ClientSoc.getOutputStream());
            
            System.out.println("TPC Client Connected ...");
            start();
        }
        catch(Exception ex)
        {
        	System.out.println("Failed to start communication thread");
        }
    }
        
        
    @Override
    public void run()
    {
    	Object o;
    	String method;
    	
        while(true)
        {
            try
            {
              
                // Get Object From client
                o = this.InObject.readObject();
                System.out.println("----> received the class object : " + o.getClass().getName());
                
                if (! o.getClass().getName().equals("Calc")){
                	Exception ex = new Exception("The received class is not the <Calc> Class");
                }
                
                //Get Method name from client
                method = this.InMethod.readUTF();
                System.out.println("----> received the method : " + method);
                
                //FAir eune boucle sur le nombre de methodes associées à la classe Calc et voir si la strng envoyée est dans cette liste.
                
                
                
                
                //Out: received message + current time of server
//                dout.writeUTF( msg + "  ["+(new Date().toString())+ "]");
            }
            catch(Exception ex)
            {
            }
        }
    }
}

