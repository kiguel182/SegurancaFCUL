import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Group {

	private String groupName;
	private String user;

	public Group(String groupName, String user) {
		this.groupName = groupName;
		this.user = user;
	}

	void createGroup(String groupName,String user){

		File file = new File(groupName + ".txt");
		if(file.exists()){
			System.out.println("File exists");
		}
		else {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));

				bw.write("admin:" + user);
				bw.newLine();
				bw.newLine();
				bw.close();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	void addUser(String groupFile,String admin,String user) throws IOException{
		String str = null;
		File file = new File(groupFile + ".txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));

		if(!file.exists()){
			System.out.println("No such file");
		}
		else {
			if(!isAdmin(file,admin)){
				System.out.println("Admin incorrecto");
			}
			else {
				if(!existingUserInGroup(file,user)){
					bw.write(user);
					bw.newLine();
				}
				else {
					System.out.println("User exists");
				}

			}
		}

		bw.close();
	}

	private boolean isAdmin (File groupFile,String admin) throws IOException  {
		String str = null;
		BufferedReader br = new BufferedReader(new FileReader(groupFile));
		str = br.readLine();
		br.close();
		return str.equals("admin:" + admin);


	}

	private boolean existingUserInGroup(File groupFile,String user) throws IOException {
		Boolean exists = false;
		String str = null;
		BufferedReader br = new BufferedReader(new FileReader(groupFile));


		while(!((str = br.readLine())==null)){
			if(user.equals(str)){
				exists = true;
				break;
			}
		};

		br.close();
		return exists;
	}

	void deleteUser(String groupFile,String admin,String user) throws IOException{
		String str = null;
		File file = new File(groupFile + ".txt");	

		if(!file.exists()) {
			System.out.println("Ficheiro nao existe");
		}

		else {

			BufferedReader br = new BufferedReader(new FileReader(file));
			
			if(!isAdmin(file,admin)){
				System.out.println("Admin incorrecto");
			}
			else if(user.equals("admin:"+admin)){
				System.out.println("Nai foi possivel remover administradores");
			}

			else {
				StringBuilder sb = new StringBuilder();

				while((str = br.readLine()) != null){

					if(!(str.equals(user))){
						sb.append(str);
						sb.append("\n");
					}

				}
				br.close();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(sb.toString());
				bw.close();
			}

		}
	}

}
