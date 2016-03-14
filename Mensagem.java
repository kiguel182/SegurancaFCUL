import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Mensagem {
	
	private static int inc = 1;
	private String contact;
	private String message;

	public Mensagem(String contact, String message) {
		this.contact = contact;
		this.message = message;
	}
	
	public boolean sendMessage(String contact, String message) throws IOException {
		
		Boolean send = false;
		
		if(contact != null && message != null) {
			
			if (!new File("\\user.txt").exists()) {
			   throw new FileNotFoundException("File not found");
			}
			else {
				
				inc++;
				File newFile = new File("fileName" + "inc" + ".txt");
				
				BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, true));
				bw.write(message);
				
				// POR FAZER  BUSCAR LOG
				
				
				bw.close();
				send = true;
				
			}
		}
		
		return send;
		
	}

}
