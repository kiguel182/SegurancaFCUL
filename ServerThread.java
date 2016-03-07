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

			/**
			 * Ainda por completar
			 */

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