import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Mensagem {

	private static int inc = 0;
	private String contact;
	private String message;

	public Mensagem(String contact, String message) {
		this.contact = contact;
		this.message = message;
	}

	public File createMessage() throws IOException {

		File newFile = null;
		
		if(userExists(this.contact) && this.message != null) {

			newFile = new File("Mensagem" + inc + ".txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, true));
			bw.write(message);
			Mensagem.inc++;
			bw.close();

		}

		return newFile;

	}

	public boolean userExists(String contact) throws IOException{

		boolean userExists = false;
		String str = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader("user.txt"));
			while((str = br.readLine()) != null){
				String[] userF = str.split(":");
				if(userF[0].equals(contact)){
					userExists = true;
				}
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userExists;

	}

}
