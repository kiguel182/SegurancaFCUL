import java.io.File;

public class Mensagem {
	
	private String contact;
	private String message;

	public Mensagem(String contact, String message) {
		this.contact = contact;
		this.message = message;
	}
	
	public boolean sendMessage(String contact, String message) {
		
		boolean send = false;
		
		if(contact != null && message != null) {
			
			send = true;
		}
		
		return send;
		
	}
	
	

}
