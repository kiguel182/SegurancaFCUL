
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class myWhats {

	public static void main(String[] args) {
		System.out.println("servidor: main");
		String user = null, passwd = null, serverPort = null;


		try {
			Socket socket = new Socket("127.0.0.1", 23456);

			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

			if(args[2].equals("-p")) {
				user = args[0];
				passwd = args[3];
				serverPort = args[1];
			}
			
			outStream.writeObject(user);
			outStream.writeObject(passwd);
			
			switch(args[4]) {
				case "-m":
					break;
					
				case "-f":
					break;
				
				case "-r":
					break;
					
				case "-a":
					break;
					
				default:
					System.out.println("No viable command issued");
					break;
			}

			
			outStream.close();
			socket.close();

		}catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
