package c2_input;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class InputController implements Initializable {
	
	@FXML private TextField txtTitle;
	@FXML private PasswordField txtPass;
	@FXML private ComboBox<String> comboBox;
	@FXML private ColorPicker colorPicker;
	@FXML private DatePicker datePicker;
	@FXML private TextArea txtContent;
	@FXML private Button btnReg, btnCancel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}