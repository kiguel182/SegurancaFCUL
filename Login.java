import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Login {

	private String user;
	private String pass;

	public Login(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	public boolean autenthicator() throws IOException{

		if(user != null && pass != null) {

			File file = new File("user.txt");
			BufferedWriter bw;

			try {

				bw = new BufferedWriter(new FileWriter(file,true));
				if(!existingUser(user,pass,file)){
					bw.write(user+ ":" + pass);
					bw.newLine();
					bw.close();
					return true;
				}
				else if(!wrongPassword(user,pass,file)){
					//aqui deve verificar que a pass esta errada em fez disso. Ou que esta certa.
					System.out.println("User and Password correct");
					return true;
				}
				else{
					System.out.println("Incorrect Password");
					return false;
				}

				

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
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
			//String line = user + ":" + pwd;

			try {

				BufferedReader br = new BufferedReader(new FileReader(file));
				while((str = br.readLine())!= null){
					String[] possibleUser = str.split(":");
					if(str.equals(possibleUser[0])){
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
	public boolean wrongPassword(String user, String pass, File file) throws IOException{
		return userExists(user,file) && !existingUser(user,pass,file);
	}

}
