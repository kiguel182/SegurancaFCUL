import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;


public class myWhats {

	public static void main(String[] args) {

		String user = null, passwd = null, serverPort = null;

		serverPort = args[1];
		String[] parts = serverPort.split(":");
		String server = parts[0]; 
		int port = Integer.parseInt(parts[1]);

		try {
			Socket socket = new Socket(server, port);

			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

			if(args[2].equals("-p")) {
				user = args[0];
				passwd = args[3];
			}

			outStream.writeObject(user);
			outStream.writeObject(passwd);

			switch(args[4]) {

			case "-m":
				outStream.writeObject("-m");
				// Contact e Message
				if(args[5] != null && args[6] != null) {
					outStream.writeObject(args[5]);
					outStream.writeObject(args[6]);
				}

				break;

			case "-f":
				outStream.writeObject("-f");

				if(args[5] != null && args[6] != null) {
					outStream.writeObject(args[5]);
					outStream.writeObject(args[6]);
					sendFile(args[6], outStream);
				}

				break;

			case "-r":
				outStream.writeObject("-r");

				if(args[5]!= null && args[6] != null) {
					outStream.writeObject(args[5]);
					outStream.writeObject(args[6]);

				}
				else if(args[5] != null) {

					outStream.writeObject(args[5]);

				}

				else {
					System.out.println("Most recent");
				}

				break;

			case "-a":
				outStream.writeObject("-a");

				if(args[5] != null && args[6] != null) {
					outStream.writeObject(args[6]);
					outStream.writeObject(args[5]);
				}

				break;

			case "-d":
				outStream.writeObject("-d");

				if(args[5] != null && args[6] != null) {
					outStream.writeObject(args[6]);
					outStream.writeObject(args[5]);
				}

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

	private static void sendFile(String name, ObjectOutputStream out) throws IOException{

		File toSend = new File(name);

		if(!toSend.exists()) {
			System.out.println("File does not exist");
		}
		else {

			byte[] fileInByte = Files.readAllBytes(toSend.toPath());
			out.writeInt(fileInByte.length);
			byte[] envio = new byte[1024];

			int currentByte = 0;
			for(int i = 0; i <= fileInByte.length/1024; i++){
				for(int a = 0; a < 1024; a++){
					if(currentByte < fileInByte.length){
						envio[a] = fileInByte[currentByte];
						currentByte++;
					}
				}
			}

			out.writeObject(envio);
			envio = new byte[1024];
		}
	}
}
