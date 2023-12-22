package c2_input;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class InputController implements Initializable {

	@FXML
	private TextField txtTitle;
	@FXML
	private PasswordField txtPass;
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TextArea txtContent;
	@FXML
	private Button btnReg, btnCancel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*
		 * btnReg.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) { } });
		 */
		// 등록 버튼의 action event처리
		btnReg.setOnAction((e) -> {
			// TextField에 작성된 문자열
			String titleText = txtTitle.getText();
			System.out.println("제목 : " + titleText);

			String pass = txtPass.getText();
			System.out.println("비번 : " + pass);

			// ComboBox<Stirng> generic으로 지정된 타입으로 선택된 값을 반환
			String comboData = comboBox.getValue();
			System.out.println("combo : " + comboData);

			// Color : 색상에 대한 정보를 저장하는 객체
			// RGBA(RED, GREEN, BLUE, ALPHA OR BRIGHTNESS)
			Color color = new Color(1, 0, 0, 1);
			System.out.println(color);

			Color pickColor = colorPicker.getValue();
			System.out.println("pickColor : " + pickColor);
			double red = pickColor.getRed();
			System.out.println("RED : " + pickColor.getRed());
			System.out.println("GREEN : " + pickColor.getGreen());
			System.out.println("BLUE : " + pickColor.getBlue());
			System.out.println("ALPHA : " + pickColor.getBrightness());

			LocalDate date = datePicker.getValue();
			System.out.println(date);
			
			if (date != null) {
				System.out.println("연도 : " + date.getYear());
				System.out.println("월 이름 : " + date.getMonth());
				System.out.println("월 숫자 : " + date.getMonthValue());
				System.out.println("월 중 날짜 : " + date.getDayOfMonth());
				System.out.println("년 중 날짜 : " + date.getDayOfYear());
			}
			String content = txtContent.getText();
			System.out.println("content : " + content);

		}); // end btnReg action

		// 취소버튼 click event
		btnCancel.setOnAction(e -> {
			// 기존 값을 새로운 텍스트로 대체
			txtTitle.setText("");
			txtPass.setText(null);
			txtContent.clear();

			// 콤보 박스 선택 목록
			ObservableList<String> oldList = comboBox.getItems();
			System.out.println(oldList);
			// 새로운 item 항목 생성
			ObservableList<String> newList = FXCollections.observableArrayList("공개", "비공개");
			newList.add("포기");
			newList.remove("공개");
			comboBox.setItems(newList);
			// 기존 선택항목 제거
			comboBox.getSelectionModel().clearSelection();
			// 힌트
			comboBox.setPromptText("선택하세요ㅛㅛㅛ");

			// 컬러피커의 선택 값을 흰색으로 지정
			colorPicker.setValue(new Color(1, 1, 1, 1));
			// datePicker 선택 항목 삭제
			datePicker.setValue(null);
			// datePicker의 선택 날짜를 현재 날짜로 지정
			datePicker.setValue(LocalDate.now());

			// TextFile 제목 입력 control에 포커스 요청
			txtTitle.requestFocus();
		}); // end btnCancel action

		// 입력필드에 값을 작성하기 위해서 키가 눌러졌을 때 이벤트 처리
		txtTitle.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				System.out.println(event.getCode());
				System.out.println(event.getSource());
				System.out.println(event.getCharacter());
				System.out.println(event.getCode().getChar());

				// KeyCode keyevent를 통해서 사용자가 입력한 코드 정보를 저장하는 객체
				if (event.getCode() == KeyCode.ENTER) {
					// 포커스를 패스워드 필드로 이동
					// txtPass.requestFocus();
					// event 강제 실행
					btnCancel.fire();

				}
			}

		}); // end setOnKeyPressed

		txtPass.textProperty().addListener((target, oldValue, newValue) -> {
			System.out.println(newValue);
			// 기존 텍스트를 삭제하고 새로운 텍스트를 추가
			// txtContent.setText(newValue +"\n");
			// 기존 값에 매개변수로 전달 받은 문자열을 이어서 추가
			txtContent.appendText(newValue + "\n");
		});

	} // end initialize

}