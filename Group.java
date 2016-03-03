import java.io.File;

public class Group {
	
	private File group;
	private String user;
	private String administrator;

	public Group(String user, File group, String administrator) {
		this.user = user;
		this.group = group;
		this.administrator = administrator; 
	}
	
	public boolean addUser(String user, File Group) {
		
		boolean add = false;
		
		return false;
	}

}
