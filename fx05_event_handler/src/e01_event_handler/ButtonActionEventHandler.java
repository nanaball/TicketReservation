package e01_event_handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonActionEventHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		System.out.println(event);
		Button btn = (Button)event.getTarget();
		String id = btn.getId();
		System.out.println("action event btn id : " + id);
		
		
	}

}
