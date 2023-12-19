module fx03_layout_basic {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	opens test_fxml to javafx.graphics, javafx.fxml;
}
