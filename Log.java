import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Log {
	
	
	public void writeLog(String sender,String receiver,String timeStamp,String message){
		
		File file = new File("log.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			bw.write("Contact:"+receiver);
			bw.newLine();
			bw.write("Me:"+message);
			bw.newLine();
			bw.write(timeStamp);
			bw.newLine();
			bw.write("----------");
			bw.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	/**
	public String readAllRecent() throws FileNotFoundException{
		
		File file = new File("log.txt");
		String str = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		
			
			try {
				while((str = br.readLine())!= null){
					if(str.contains("Contact:")){
						String[] userCont = str.split(":");
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		
		
		
	}
	*/
	
}
