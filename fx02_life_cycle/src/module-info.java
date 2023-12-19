module fx02_life_cycle {
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
}
