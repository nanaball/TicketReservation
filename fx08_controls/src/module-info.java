module fx08_controls {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	
	opens c1_button_base to javafx.graphics, javafx.fxml;
	opens c2_input to javafx.graphics, javafx.fxml;
}
