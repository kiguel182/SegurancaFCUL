import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Log {
	
	
	public void writeLog(String sender,String receiver,String timeStamp,String message){
		writeLogSender(sender,receiver,timeStamp,message);
		writeLogSender(receiver,receiver,timeStamp,message);
	}
	
	private void createDir(){
		File theDir = new File("Log");
		if(!theDir.exists()){
			System.out.println("Creating directory");
			theDir.mkdir();
		}
	}
	
	
	private void writeLogSender(String sender,String receiver,String timeStamp,String message){
		createDir();
		String path = "Log" + File.separator + "log"+sender+".txt";
		File file = new File(path);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			bw.write("Contact:"+receiver);
			bw.newLine();
			bw.write("Me:"+message);
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
	
	public void readRecent(File logUser) throws FileNotFoundException{
		File file = new File(logUser.toString());
		String str = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String contact = null;
		String message = null;
		String timeStamp = null;
			
			try {
				while((str = br.readLine())!= null){
					if(str.contains("Contact:")){
						contact = str;
					}
					if(str.contains("Me:")){
						message = str;
					}
					if(str.contains("TimeStamp:")){
						timeStamp = str;
					}
					
			
					
				}
				br.close();
				System.out.println("-------");
				System.out.println(contact);
				System.out.println(message);
				System.out.println(timeStamp);
				
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
							
	}
	
	public void readAllRecent() throws FileNotFoundException{
		File[] files = new File("Log/").listFiles();
		
		for (File file : files) {
	       readRecent(file);
	    }
	    
	}
	
		
	
	
}
