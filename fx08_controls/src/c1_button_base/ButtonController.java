package c1_button_base;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ButtonController implements Initializable{
	
	@FXML private BorderPane root;
	@FXML private CheckBox chk1, chk2;
	@FXML private ToggleGroup group;
	@FXML private ImageView chkImg, radioImg;
	@FXML private Button btnExit;
	@FXML private Hyperlink hyperLink;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		chk1.setOnAction((e)->{
			handlerChkAction(e);
		});
		
		chk2.setOnAction((e)->{
			handlerChkAction(e);
		});
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(
					ObservableValue<? extends Toggle> target,
					Toggle oldValue,	// 이전에 selected 된 버튼
					Toggle newValue) {	// 새로 selected 된 버튼
				RadioButton value = (RadioButton)newValue;
				String text = value.getText();
				System.out.println(text);
				
				text = "/images/"+text+".png";
				System.out.println(text);
				
				String path = getClass().getResource(text).toString();
				Image image = new Image(path);
				radioImg.setImage(image);
			}
			
		});	// end toggle
		
		btnExit.setOnAction((e)->{
			// 시스템 group thread 종료, 강제종료 
			System.exit(0);
			// UI thread 종료
			// Platform.exit();
		});
		
		hyperLink.setOnAction(e->{
			System.out.println("hyperLink!!");
			// hyperLink.setUserData(new String("https://www.nate.com"));
			String link = (String)hyperLink.getUserData();
			System.out.println(link);
			
			// 도메인 정보를 가지고 웹화면을 애플리케이션에 출력하는 View
			WebView webView = new WebView();
			WebEngine we = webView.getEngine();
			we.load(link);
			
			// 현재화면
			// root.setTop(webView);		
			
			// 새로운 화면 WebView를 보여주기 위한 무대 생성
			Stage stage = new Stage();
			BorderPane pane = new BorderPane();
			pane.setCenter(webView);
			stage.setScene(new Scene(pane));
			stage.setWidth(1074);
			stage.setHeight(700);
			stage.show();
			
		});

	}	// end initialize
	
	// checkbox check시 이미지 변경
	public void handlerChkAction(ActionEvent event) {
		boolean isCheck1 = chk1.isSelected();
		boolean isCheck2 = chk2.isSelected();
		System.out.println("1 : " + isCheck1);
		System.out.println("2 : " + isCheck2);
		
		// geek-glasses.gif 		안경		- true, false
		// geek-glasses-hair.gif	안경 모자	- true, true
		// geek-hair.gif			모자		- false, true
		// geek.gif					둘다 x	- false, false
		
		String resource = "";
		if(isCheck1 && isCheck2) {
			resource = "/images/geek-glasses-hair.gif";
		}else if(isCheck1) {
			resource = "/images/geek-glasses.gif";
		}else if(isCheck2) {
			resource = "/images/geek-hair.gif";
		}else {
			resource = "/images/geek.gif";
		}
		
		URL url = getClass().getResource(resource);
		// 지정된 이미지 경로로 ImageView의 이미지 변겅
		chkImg.setImage(new Image(url.toString()));
	} // end handlerChkAction
	
}
