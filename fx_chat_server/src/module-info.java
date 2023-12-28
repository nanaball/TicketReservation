module fx_chat_server {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens chat_server to javafx.graphics, javafx.fxml;
}
