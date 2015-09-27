import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Socket_Client {

<<<<<<< HEAD
	public static void main(String[] args) throws Exception{
		TCPClient client = new TCPClient("localhost", 8000);
		
		client.display();
	}
=======

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
			ClientSoc = new Socket(hostName, port);
			din = new DataInputStream(ClientSoc.getInputStream());
			dos = new DataOutputStream(ClientSoc.getOutputStream());
			oos = new ObjectOutputStream(ClientSoc.getOutputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void display() throws Exception{
		
		String nombreClient1;
		String nombreClient2;
		String messageServeur;
		
		boolean quitter = false;
		int choix = 0;
		
		
		System.out.println("Bienvenue, vous voulez : \n");
		while(!quitter) {			
			System.out.println("	1 - Additionner deux nombres");
			System.out.println("	2 - Quitter");
			
			choix = Integer.parseInt(br.readLine());
			if (choix == 1) {
				System.out.println("\nVeuillez rentrer le premier nombre : ");
				nombreClient1 = br.readLine();
				System.out.println("\nVeuillez rentrer le deuxième nombre : ");
				nombreClient2 = br.readLine();
				
				Calc calcul = new Calc(nombreClient1, nombreClient2);
				
				oos.writeObject(calcul);
				dos.writeUTF("add");
				
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
>>>>>>> 84f6e240c2393b57eb70fd11e9d3565284db92a9
}

class TCPClient {
	Socket ClientSoc;
	
	DataInputStream din;
	DataOutputStream dos;
	ObjectOutputStream oos;
	BufferedReader br;
	
	public TCPClient(String hostName, int port) {
		try {
			ClientSoc = new Socket(hostName, port);
			din = new DataInputStream(ClientSoc.getInputStream());
			dos = new DataOutputStream(ClientSoc.getOutputStream());
			oos = new ObjectOutputStream(ClientSoc.getOutputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void display() throws Exception{
		
		String nombreClient1;
		String nombreClient2;
		String messageServeur;
		
		boolean quitter = false;
		int choix = 0;
		
		while(!quitter) {
			System.out.println("Bienvenue, vous voulez : \n");
			System.out.println("	1 - Additionner deux nombres");
			System.out.println("	2 - Quitter");
			
			choix = Integer.parseInt(br.readLine());
			if (choix == 1) {
				System.out.println("\nVeuillez rentrer le premier nombre : ");
				nombreClient1 = br.readLine();
				System.out.println("\nVeuillez rentrer le deuxième nombre : ");
				nombreClient2 = br.readLine();
				
				Calc calcul = new Calc(nombreClient1, nombreClient2);
				
				oos.writeObject(calcul);
				dos.writeUTF("add");
				
				messageServeur = din.readUTF();
				System.out.println("\nRésultat : " + messageServeur);
				
			} else if (choix == 2) {
				dos.close();
				oos.close();
				quitter = true;
			}
		}
	}
}