import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

class ServerThread extends Thread {

	private Socket socket = null;
	private boolean autenticated;

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
			autenticated = login.autenthicator();

			if(autenticated){
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
						// Envia confirma�ao se pode ou nao escrever mensagem
						outStream.writeObject((boolean) autenticated);

						String contact = (String) inStream.readObject();
						String name = (String) inStream.readObject();
						String m = null;
						
						createDir("Group");
						createDir("Mensagem");
						
						if(checkGroup(contact)) {
							createDir(contact);
							receive("Group" + File.separator + contact, name, inStream);
							m = createString(new File("Group" + File.separator + contact + File.separator + name));
						}
						
						else  {
							receive("Mensagem" + File.separator + user, contact, name, inStream);
							m = createString(new File("Mensagem" + File.separator + user + "-" + contact + File.separator + name));
						}


						Log l = new Log();
						l.writeLog(name, user, contact, timestampCreate(), m);

					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;

				case "-f":

					try {
						String contact = (String) inStream.readObject();
						String file = (String) inStream.readObject();
						createDir("Files");
						receive("Files" + File.separator + contact, file, inStream);
						Log l = new Log();
						l.writeLog(file, user, contact, timestampCreate(), file);

					} catch (ClassNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					break;

				case "-r":

					String contact = null;
					String file = null;
					Log log = new Log();
					String whichR = null;

					try {
						whichR = (String) inStream.readObject();

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					switch(whichR) {

					case "r1":
						try {
							contact = (String) inStream.readObject();
							file = (String) inStream.readObject();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if(contact != null && file !=null) {
							outStream.writeObject(file);
							sendFile("Files" + File.separator + contact + File.separator + file, outStream);
							Log l = new Log();
							l.writeLog(file, user, contact, timestampCreate(), file);
						}

						break;

					case "r2":

						try {
							contact = (String) inStream.readObject();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if(contact != null){
							String s = (String) log.readLogContact(user, contact);
							outStream.writeObject(s);
						}

						break;

					case "r3":

						String s = (String) log.readRecent(user);
						outStream.writeObject(s);

						break;

					default:
						System.out.println("No R recieved");
						break;
					}

					break;

				case "-a":

					try {
						String groupNameAdd = (String)inStream.readObject();
						String contactAdd = (String)inStream.readObject();
						Group gAdd = new Group(groupNameAdd, user);
						gAdd.createGroup(groupNameAdd, user);
						createDir("Group");
						createDir("Group" + File.separator + groupNameAdd);
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
			}
			else{
				System.out.println("Wrong password");
			}

			outStream.close();
			inStream.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void receive(String contact, String name, ObjectInputStream inStream) throws IOException, ClassNotFoundException{
		//vai ter de receber nome primeiro antes de criar o ficheiro
		System.out.println(contact);
		createDir(contact);

		File result = new File(contact + File.separator + name);

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

	private void receive(String user, String contact, String name, ObjectInputStream inStream) throws IOException, ClassNotFoundException{
		//vai ter de receber nome primeiro antes de criar o ficheiro
		Boolean str = createDirMessage(user, contact);
		File result = null;

		if(str)
			result = new File(user + "-" + contact + File.separator + name);
		else 
			result = new File(contact + "-" + user + File.separator + name);


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
		System.out.println(result.toString());
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
				out.writeObject(envio);
				envio = new byte[1024];
			}
		}
	}

	private String timestampCreate() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		return sdf.format(date);

	}

	private void createDir(String contact){
		File theDir = new File(contact);
		if(!theDir.exists()){
			System.out.println("Creating directory");
			theDir.mkdir();
		}
	}

	private Boolean createDirMessage(String user, String contact){
		boolean dirCreated = false;
		File theUserCon = null;
		if(new File(contact + "-" + user).exists()){
			System.out.println("Directoria inversa");

		}
		else {
			theUserCon = new File(user + "-" + contact);
			theUserCon.mkdir();
			dirCreated = true;
		}

		return dirCreated;

	}

	private String createString(File file) throws IOException {

		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));

		String str;
		while((str = br.readLine())!= null) {
			// Se houver mais do que 1 linha, ira criar um tab
			sb.append(str + "\t");
		}

		br.close();
		return sb.toString();

	}

	private boolean checkGroup(String contact) {

		boolean isGroup = false;
		File file = new File("Group" + File.separator + contact);

		if(file.exists()) {
			isGroup = true;
		}

		return isGroup;
	}

}