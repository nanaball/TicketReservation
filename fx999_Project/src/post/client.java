package post;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class client extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Socket socket = new Socket("10.100.205.92", 5001);
			
			
			OutputStream output = socket.getOutputStream();
			
			String realStr = "THIS IS WOOLBRO";
			byte[] data = realStr.getBytes();
			output.write(data);
					
			PrintWriter writer = new PrintWriter(output, true);
			writer.print("This");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
				
	}
	
	
}




