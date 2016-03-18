import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Log {


	public void writeLog(String fileName,String sender,String receiver,String timeStamp,String message) throws IOException{
		writeLogSender(fileName,sender,receiver,timeStamp,message);
		writeLogReceiver(fileName,sender,receiver,timeStamp,message);
		writeContacts(sender, receiver);
		writeContacts(receiver,receiver);
	}

	private void createDir(){
		File theDir = new File("Log");
		if(!theDir.exists()){
			System.out.println("Creating directory");
			theDir.mkdir();
		}
	}
	/**
	 * Method to write log for the sender of the message 
	 * @param fileName
	 * @param sender
	 * @param receiver
	 * @param timeStamp
	 * @param message
	 */

	private void writeLogSender(String fileName,String sender,String receiver,String timeStamp,String message){
		createDir();
		String path = "Log" + File.separator + "log"+sender+".txt";
		File file = new File(path);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			bw.write("FileName:"+fileName);
			bw.newLine();
			bw.write("Contact:"+receiver);
			bw.newLine();
			bw.write("Info-"+sender+":"+message);
			bw.newLine();
			bw.write("TimeStamp:"+timeStamp);
			bw.newLine();
			bw.write("----------");
			bw.newLine();
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	/**
	 * Method to write log for the receiver of the message
	 * @param fileName 
	 * @param sender
	 * @param receiver
	 * @param timeStamp
	 * @param message
	 */
	private void writeLogReceiver(String fileName,String sender,String receiver,String timeStamp,String message){
		createDir();
		String path = "Log" + File.separator + "log"+receiver+".txt";
		File file = new File(path);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			bw.write("FileName:"+fileName);
			bw.newLine();
			bw.write("Contact:"+receiver);
			bw.newLine();
			bw.write("Info-"+sender+":"+message);
			bw.newLine();
			bw.write("TimeStamp:"+timeStamp);
			bw.newLine();
			bw.write("----------");
			bw.newLine();
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}


	/**
	 * Auxiliary method to save all contacts
	 * @param user 
	 * @param contact
	 * @throws IOException 
	 */
	public void writeContacts(String user,String contact) throws IOException{
		File file = new File("Log/"+"Comunication"+user+".txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = null;
		Boolean isThere = false;
		while((str = br.readLine())!=null){
			if(str.equals(contact))
				isThere = true;
		}

		if(!isThere){
			bw.write(contact);
			bw.newLine();
		}

		br.close();
		bw.close();


	}

	/**
	 *  -r contact
	 *  
	 * @param user logged user
	 * @param requestedUser which log wants to be read
	 * @throws FileNotFoundException
	 */
	public void readLogContact(String user,String requestedUser) throws FileNotFoundException{

		File file = new File("Log/"+"log"+requestedUser+".txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder recent = new StringBuilder();
		String str = null;


		try {
			while((str = br.readLine())!= null){
				if(str.contains("Info")){
					String strSplit[] = str.split("-");
					if(user.equals(strSplit[1].split(":")[0])){
						recent.append("me:");
						recent.append(strSplit[1].split(":")[1]);
						recent.append("\n");
					}
					else {
						recent.append(strSplit[1]);
						recent.append("\n");
					}
				}
				if(str.contains("TimeStamp:")){
					String strSplit[] = str.split(":");
					recent.append(strSplit[1]);
					recent.append("\n");
				}					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(recent.toString());

	}

	/**
	 * -r 	
	 * @param user logged user
	 * @throws FileNotFoundException
	 */
	public void readRecent(String user) throws FileNotFoundException{
		File fileLog = new File("Log/"+"log"+user+".txt");
		File fileContact = new File("Log/"+"Comunication"+user+".txt");
		List<String> logArray = new ArrayList<String>();
		List<String> contactArray = new ArrayList<String>();
		List<String> recentLog = new ArrayList<String>();
		BufferedReader brLog = new BufferedReader(new FileReader(fileLog));
		BufferedReader brContact = new BufferedReader(new FileReader(fileContact));
		String str = null;
		StringBuilder tmp = new StringBuilder();
		try {
			while((str = brLog.readLine())!= null){
				if(!str.equals("----------")){	
					tmp.append(str);
					if(!str.contains("TimeStamp:"))
						tmp.append("||");

				}
				else {
					logArray.add(tmp.toString());
					tmp = new StringBuilder();
				}
			}
			brLog.close();
			str = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.reverse(logArray);

		try {
			while((str = brContact.readLine())!= null){
				contactArray.add(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i = 0; i < contactArray.size();i++){
			for(int j = 0; j < logArray.size();j++){
				if(logArray.get(j).contains("Contact:"+contactArray.get(i)+"||")){
					recentLog.add(logArray.get(j));
					break;
				}


			}
		}

	}


}
