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
    	Object obj;
    	String methodName;
    	String response = "";
    	
        while(true)
        {
        	
        	
        	// Get Object From client
        	try {
        		                
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
                		//Do the stuff on the serialized object
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
					dout.writeUTF(response);
				} catch (IOException e) {
					System.out.println("Socket has been disconnected because we cannot write response");
					return;
				}
        	}
            
        }
    }
    
    
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

