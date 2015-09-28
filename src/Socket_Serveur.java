import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;


public class Socket_Serveur {

	public static void main(String[] args) {		
		//Port = 8000
		TCPServer server = new TCPServer(8000);
	}

}


class TCPServer {	
	
	private Socket clientSoc;
	private ServerSocket serverSoc;    
   	private int portNumber;
   	
   	
   	public TCPServer(int port){
   		//initialize a ServerSocket with the given port
		try {
			this.portNumber = port;
			this.serverSoc = new ServerSocket(this.portNumber);
		} catch (IOException e) {
			System.out.println("Failed to start ServerSocket");
			e.printStackTrace();
			return;
		}
		
		System.out.println("\nTCP Server Started on Port Number: " + this.portNumber);
		
		//Wait for client connections ...
		while(true)
        {
            System.out.println("Waiting for Connection ...");
            try {
            	//Catch a client connection and delegate the client communication to a thread
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
        	//Bind this thread to the given socket, to communicate with a client
            this.ClientSoc = soc;
            
            //Instanciation of relative streams for Object IN, Data IN / OUT
            this.InObject = new ObjectInputStream (ClientSoc.getInputStream());
            this.InMethod = new DataInputStream(ClientSoc.getInputStream());
            this.dout = new DataOutputStream(ClientSoc.getOutputStream());
            
            System.out.println("TPC Client Connected ...");
            
            //Run the thread
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
    	Object obj;
    	String methodName;
    	String response = "";
    	
        while(true)
        {	
        	try {
        	
        		// Get Object From client            
				obj = this.InObject.readObject();				
                if (! obj.getClass().getName().equals("Calc")){                	
                	response = "The received class is not the <Calc> Class";
                }                
                System.out.println("----> received the class object : " + obj.getClass().getName());
                
                                
                // Get Method name from client and check if method is valid
                Boolean found = false;
                methodName = this.InMethod.readUTF();
                System.out.println("----> received the method : " + methodName);
                Method[] methodList = obj.getClass().getMethods();
                
                for(int i = 0; i < methodList.length; i++){
             
                	if (methodList[i].getName().equals(methodName)){
                		//If method is valid, execute it on the given object
                		found = true;	
                		Calc calcul = (Calc) obj;
                		response = (String) this.execute(calcul, methodList[i]);                		
                		break;                		
                	}    
                	
                }
                
                if (!found){
                	response = "The method name doesn't exist inside the class";
                }
        	
        	}
        	catch (ClassNotFoundException e) {
        		response = "Class not found";
				e.printStackTrace();
			} catch (IOException e) {
				response = "IO exception";
				e.printStackTrace();
			}
        	finally{        		
        		try {
        			//Send the result of computation to the client
					dout.writeUTF(response);
				} catch (IOException e) {
					System.out.println("Socket has been disconnected because we cannot write response");
					return;
				}
        	}
            
        }
    }
    
    
    //Execute a given method on a given Object; returns result of Computation
    public Object execute(Object instance, Method toExec){    	
    	try {			
			Object res = null;   
			res = (Integer)toExec.invoke(instance);
			return res.toString();			
		} catch (IllegalAccessException	| IllegalArgumentException | InvocationTargetException e) {
			return "cannot invoke";			
		}
    }
    
}

