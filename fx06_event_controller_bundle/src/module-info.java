module fx06_event_controller_bundle {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	
	opens e01_controller to javafx.graphics, javafx.fxml;
	opens e02_bundle to javafx.graphics, javafx.fxml;

}
