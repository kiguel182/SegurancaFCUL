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
	private ArrayList<Tuple<File, String>> atividades;

	public Group(String groupName, String user) {
		this.groupName = groupName;
		this.user = user;
		this.atividades = new ArrayList();
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
		BufferedReader br = new BufferedReader(new FileReader(file));
		System.out.println("Admin:" + admin);
		if(!isAdmin(file,admin)){
			System.out.println("Admin incorrecto");
		}
		else if(user.equals("admin:"+admin)){
			System.out.println("N�o � poss�vel remover administradores");
		}

		else {

			File fileTmp = new File(".tempFile.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileTmp));
			while(!((str=br.readLine())==null)){
				if(str.equals(admin)){
					bw.write(str);
					bw.newLine();
					bw.newLine();
				}
				if(!str.equals(user)){
					bw.write(str);
					bw.newLine();
				}


			}
			fileTmp.renameTo(file);
			bw.close();
		}


		br.close();

	}
	
	private void addList(File file, String contact) {
		Tuple<File, String> x = new Tuple<File, String>(file, contact);
		atividades.add(x);
	}

}
