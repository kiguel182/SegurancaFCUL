import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;


public class myWhats {

	public static void main(String[] args) throws ClassNotFoundException {

		if(args.length <= 0) {
			System.out.println("Por favor introduza os argumentos necessarios");
		}

		else {

			String user = null, passwd = null, serverPort = null;

			serverPort = args[1];
			String[] parts = serverPort.split(":");
			String server = parts[0]; 
			int port = Integer.parseInt(parts[1]);

			try {
				Socket socket = new Socket(server, port);
				ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
				user = args[0];
				if(args[2].equals("-p")) {
					passwd = args[3];
					outStream.writeObject(user);
					outStream.writeObject(passwd);
					options(0,args,outStream,inStream);
				}

				else {
					System.out.println("Escreva a sua password");
					Scanner sc = new Scanner(System.in);
					passwd = sc.next();

					outStream.writeObject(user);
					outStream.writeObject(passwd);
					options(2,args,outStream,inStream);
					sc.close();

				}

				outStream.close();
				socket.close();

			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void options(int i,String []args,ObjectOutputStream outStream, ObjectInputStream inStream) throws IOException, ClassNotFoundException{
		switch(args[4-i]) {

		case "-m":
			outStream.writeObject("-m");
			// Contact e Message
			if(args[5-i] != null && args[6-i] != null) {

				boolean mess = false;
				mess = (boolean) inStream.readObject();

				if(mess) {
					Mensagem m = new Mensagem(args[5-i], args[6-i]);
					File f = m.createMessage();
					outStream.writeObject(args[5-i]);
					outStream.writeObject(f.getName());
					sendFile(f.getName(), outStream);
				}
				else {
					System.out.println("Autenticacao mal feita");
				}
			}

			break;

		case "-f":
			outStream.writeObject("-f");

			if(args[5-i] != null && args[6-i] != null) {
				outStream.writeObject(args[5-i]);
				outStream.writeObject(args[6-i]);
				sendFile(args[6-i], outStream);
			}

			break;

		case "-r":
			outStream.writeObject("-r");

			if(args.length == (7-i)) {
				outStream.writeObject("r1");
				outStream.writeObject(args[5-i]);
				outStream.writeObject(args[6-i]);
				// Receber do servidor
				String name = (String) inStream.readObject();
				receive(name, inStream);

			}
			else if(args.length == (6-i)) {
				outStream.writeObject("r2");
				outStream.writeObject(args[5-i]);

				String s = (String) inStream.readObject();
				System.out.println(s);
			}

			else if(args.length == (5-i)){
				outStream.writeObject("r3");

				String s = (String) inStream.readObject();
				System.out.println(s);
			}

			break;

		case "-a":
			outStream.writeObject("-a");

			if(args[5-i] != null && args[6-i] != null) {
				outStream.writeObject(args[6-i]);
				outStream.writeObject(args[5-i]);
			}

			break;

		case "-d":
			outStream.writeObject("-d");

			if(args[5-i] != null && args[6-i] != null) {
				outStream.writeObject(args[6-i]);
				outStream.writeObject(args[5-i]);
			}

			break;

		default:
			System.out.println("No viable command issued");
			outStream.writeObject("erro");
			break;
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
				out.writeObject(envio);
				envio = new byte[1024];
			}
		}
	}

	private static void receive(String name, ObjectInputStream inStream) throws IOException, ClassNotFoundException{
		//vai ter de receber nome primeiro antes de criar o ficheiro
		File result = new File(name);

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
