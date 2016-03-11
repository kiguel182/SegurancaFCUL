import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ServerThread extends Thread {

	private Socket socket = null;
	private static File lastOperation = null;
	private static String userSend = null;
	private static String contactTo = null;

	public ServerThread(Socket inSoc) {
		socket = inSoc;
	}

	public void run(){
		try {

			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

			String user = null;
			String passwd = null;

			try {
				user = (String)inStream.readObject();
				passwd = (String)inStream.readObject();

			}catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			Login login = new Login(user, passwd);
			login.autenthicator(user, passwd);
			userSend = user;

			String command = null;
			
			try {
				command = (String)inStream.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			switch(command) {

			case "-m":
				// Contact e Message
				try {
					String contact = (String) inStream.readObject();
					String message = (String) inStream.readObject();
					Mensagem m = new Mensagem(contact, message);
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "-f":
				
				try {
					String contact = (String) inStream.readObject();
					receive((ObjectInputStream) inStream.readObject());
					
				} catch (ClassNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				break;

			case "-r":
				
				String contact = null;
				
				try {
					contact = (String) inStream.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(contact != null) {
					contactTo = contact;
					// operacao conforme o contacto
				}
				
				else {
					
					FileDesc description = new FileDesc(userSend, null, lastOperation);
					//operacao mais recente
				}
				
				break;

			case "-a":
				
				try {
					String groupNameAdd = (String)inStream.readObject();
					String contactAdd = (String)inStream.readObject();
					
					Group gAdd = new Group(groupNameAdd, contactAdd);
					gAdd.createGroup(groupNameAdd, contactAdd);
					gAdd.addUser(groupNameAdd, user, contactAdd);	
					break;
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;

			case "-d":
				
				try {
					String groupNameDel = (String)inStream.readObject();
					String contactDel = (String) inStream.readObject();
					
					Group gDel = new Group(groupNameDel, user);
					gDel.deleteUser(groupNameDel, user, contactDel);
					break;
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;

			default:
				System.out.println("No viable command issued");
				break;
			}	

			outStream.close();
			inStream.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void receive(ObjectInputStream inStream) throws IOException, ClassNotFoundException{
		//vai ter de receber nome primeiro antes de criar o ficheiro
		File result = new File("result.txt");
		int fileArraySize = inStream.readInt();
		byte[] fullByteFile = new byte[fileArraySize];
		int ciclos = fileArraySize/1024;
		int currentByte = 0;

		for(int i = 0; i <= ciclos; i++){
			byte[] receive = new byte[1024];
			receive = (byte[]) inStream.readObject();
			for(int a = 0; a < 1024; a++){
				if(currentByte < fileArraySize){
					fullByteFile[currentByte] = receive[a];
					currentByte++;
				}
			}
		}

		FileOutputStream stream = new FileOutputStream(result);
		stream.write(fullByteFile);
		stream.close();	
	}

}