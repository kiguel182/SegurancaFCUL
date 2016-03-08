import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Login {

	private String user;
	private String pass;
	private ArrayList atividades;

	public Login(String user, String pass) {
		this.user = user;
		this.pass = pass;
		this.atividades = new ArrayList();
	}

	void autenthicator(String user,String passwd) throws IOException{

		if(user != null && passwd != null) {

			File file = new File("user.txt");
			System.out.println("File created");
			BufferedWriter bw;
			try {
				
				bw = new BufferedWriter(new FileWriter(file,true));
				if(existingUser(user,passwd,file)){
					bw.write(user+ ":" + passwd);
					bw.newLine();
				}
				else
					System.out.println("User already exists");

				bw.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private boolean existingUser(String user, String pwd,File file) throws IOException {

		
		boolean existUser = false;
		
		if(user != null && pwd != null) {
			
			String str = null;
			String line = user + ":" + pwd;
			
			try {
				
				BufferedReader br = new BufferedReader(new FileReader(file));
				while((str = br.readLine())!= null){
					if(str.equals(line)){
						existUser = true;
						break;
					}
				}
				br.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
		}
		return existUser;

	}	

}
