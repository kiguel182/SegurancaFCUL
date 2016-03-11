import java.io.File;
import java.text.SimpleDateFormat;

public class FileDesc {

	private File file;
	private String user;
	private String contact;

	public FileDesc(String user, String contact, File file) {
		this.user = user;
		this.contact = contact;
		this.file = file;
	}
	
	private boolean addTuple() {
		
		boolean add = false;
		
		// Adicionar ao USER/GROUP autenticado
		// login.addList(
		
		
		return add;
	}

	private StringBuilder showDesc() {

		StringBuilder sb = new StringBuilder();

		sb.append("Contact " + this.contact);
		sb.append("me: " + this.user);

		// Formato de data do ultimo ficheiro
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		sb.append(sdf.format(this.file.lastModified()));
		sb.append(this.file.getName());

		return sb;
	}
	
	private String showDescContact(String contact) { 
		
		return null;
	}
	

}
