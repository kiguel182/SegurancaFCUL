
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;


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
			
			outStream.close();
			socket.close();

		}catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
