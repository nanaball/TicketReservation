module fx07_listener_binding {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens l01_property_listener to javafx.graphics, javafx.fxml;
}
