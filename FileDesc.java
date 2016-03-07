import java.io.File;
import java.text.SimpleDateFormat;

public class FileDesc {
	
	private File file;
	
	public FileDesc(File file) {
		this.file = file;
	}
	
	private String showDesc() {
		
		StringBuilder sb = new StringBuilder();
		
		// Formato de data do ultimo ficheiro
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		sb.append(sdf.format(file.lastModified()));
		
		return sb.toString();
	}

}
