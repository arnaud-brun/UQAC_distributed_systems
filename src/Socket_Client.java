import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Socket_Client {

	public static void main(String[] args) throws Exception{		
		TCPClient client = new TCPClient("localhost", 8000);		
		client.display();
	}
}

class TCPClient {
	Socket ClientSoc;
	
	DataInputStream din;
	DataOutputStream dos;
	ObjectOutputStream oos;
	BufferedReader br;
	
	public TCPClient(String hostName, int port) {
		try {
			//Instantiation of Socket and relative streams for Data IN / Out & Object Out
			ClientSoc = new Socket(hostName, port);
			din = new DataInputStream(ClientSoc.getInputStream());
			dos = new DataOutputStream(ClientSoc.getOutputStream());
			oos = new ObjectOutputStream(ClientSoc.getOutputStream());
			
			//Initialization of user Input Stream
			br = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//User interface
	public void display() throws Exception{
		
		String nombreClient1;
		String nombreClient2;
		String messageServeur;
		
		boolean quitter = false;
		int choix = 0;
		
		

		while(!quitter) {
			
			choix = Integer.parseInt(acceptOnlyInteger("Bienvenue, vous voulez : \n	1 - Additionner deux nombres\n	2 - Quitter\n"));
			
			//Will process a computation between to integers if Choix=1 or Quit the application if choix=2
			if (choix == 1) {
				
				//User inputs
				nombreClient1 = acceptOnlyInteger("\nVeuillez rentrer le premier nombre : ");
				nombreClient2 = acceptOnlyInteger("\nVeuillez rentrer le second nombre : ");
				
				//Instanciate a Calc object with the 2 given inputs and write it to the server
				Calc calcul = new Calc(nombreClient1, nombreClient2);				
				oos.writeObject(calcul);
				dos.writeUTF("add");
				
				//Wait for computation result from server				
				messageServeur = din.readUTF();
				System.out.println("\nRésultat : " + messageServeur+"\n\n");
				
			} else if (choix == 2) {
				dos.close();
				oos.close();
				quitter = true;
			}
		}
		ClientSoc.close();
		System.out.println("-------------------------------------------------------");
	}
	
	//Function to accept only correct Integer as user input
	public String acceptOnlyInteger(String instruction){		
		int validNumber;
		String input;
		
		while(true){			
			try{
				System.out.println(instruction);
				input = br.readLine();
				validNumber = Integer.parseInt(input);
				return input;
			}catch(NumberFormatException | IOException e) {
				System.out.println("\n /_!_\\ Veuillez rentrer un nombre entier, compris entre "+Integer.MIN_VALUE+" et "+Integer.MAX_VALUE+"");
			}			
		}
	}

}