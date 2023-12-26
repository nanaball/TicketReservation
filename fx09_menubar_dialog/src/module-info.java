module fx09_menubar_dialog {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens main to javafx.graphics, javafx.fxml;
}
