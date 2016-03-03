import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
				
				outStream.close();
				inStream.close();
				socket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
}