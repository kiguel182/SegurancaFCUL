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
				if(!existingUser(user,passwd,file) && !userExists(user,file)){
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
	/**
	 * Verifica que um user com uma password 
	 * no ficheiro
	 * @param user utilizador a verificar
	 * @param pwd password a verificar 
	 * @param file ficheiro da base de dados
	 * @return devolve falso se o utilizador com a passsword existir
	 * @throws IOException
	 */
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
	/**
	 * Verificar que um utilizador existe 
	 * @param user utilizador a verificar
	 * @param file ficheiro com a lista de passwords
	 * @return devolve verdadeiro se o utilizador existir
	 * @throws IOException
	 */
	public boolean userExists(String user,File file) throws IOException{
		boolean userExists = false;
		String str = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((str = br.readLine()) != null){
				String[] userF = str.split(":");
				if(userF[0].equals(user)){
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
	/**
	 * Metodo que verifica 
	 * se a password do utilizador 
	 * esta correcta
	 * @param user utilizador para o qual se quer verificar a password
	 * @param pwd password que se quer verificar a password
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public boolean wrongPassword(String user,String passwd) throws IOException{
		File file = new File("user.txt");
		return userExists(user,file) && !existingUser(user,passwd,file);
	}

}
