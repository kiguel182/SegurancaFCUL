import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

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
					String name = (String) inStream.readObject();
					receive(name, inStream);

					//writeLog(sender, reciever, timestamp, message)
					//writeLog(user, contact, timestampCreate(), message);


				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case "-f":

				try {
					String contact = (String) inStream.readObject();
					String file = (String) inStream.readObject();
					receive(file, inStream);
					//writelog(user, contact, timestampCreate(), file);

				} catch (ClassNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				break;

			case "-r":

				String contact = null;
				String file = null;
				Log log = new Log();

				try {
					contact = (String) inStream.readObject();
					file = (String) inStream.readObject();

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if(contact != null && file !=null) {
					sendFile(file, outStream);
				}

				else if(contact != null){
					//log.read -- por fazer --();
				}

				else {
					log.readAllRecent();
				}

				break;

			case "-a":

				try {
					String groupNameAdd = (String)inStream.readObject();
					String contactAdd = (String)inStream.readObject();

					Group gAdd = new Group(groupNameAdd, user);
					gAdd.createGroup(groupNameAdd, user);
					gAdd.addUser(groupNameAdd, user, contactAdd);	


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

	private void receive(String name, ObjectInputStream inStream) throws IOException, ClassNotFoundException{
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

	private String timestampCreate() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		return sdf.format(date);

	}

}