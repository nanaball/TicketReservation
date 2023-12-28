module fx_chat_client {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens chat_client to javafx.graphics, javafx.fxml;
}
