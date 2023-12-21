package e02_bundle;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		System.out.println(Locale.getDefault());
		// Locale 각나라 지역의 [언어][나라]등의 공통 정보를 담고 있는 class
		// getAvailableLocales() 사용 가능한 국가별 언어 코드 정보 
		
		for(Locale locale : Locale.getAvailableLocales()) {
			// 한글언어이름
			System.out.printf("Display Language : %s, ", locale.getDisplayLanguage());
			// 언어코드
			System.out.printf("Language Code: %s, ", locale.getLanguage());
			// 국가이름
			System.out.printf("Country : %s, ", locale.getDisplayCountry());
			// 국가콛, 국가/언어코드
			System.out.printf("Country code : %s, code %s %n ", locale.getCountry(),locale.toString());
		}
		
		System.out.println("=============================================");
		Locale locale = Locale.getDefault();
		System.out.println(locale);
		Locale localeJP = new Locale("ja", "JP");
		System.out.println(localeJP);
		Locale localeCN = new Locale("zh", "CN");
		System.out.println(localeCN);
		Locale.setDefault(new Locale("en", "US"));
		// en_US
		
		ResourceBundle bundle
			= ResourceBundle.getBundle("prop.s", new Locale("ko","KR"));
		// prop.s_ko_KR.properties
		// bundle = ResourceBundle.getBundle("prop.s");
		Locale.setDefault(localeCN);
		bundle = ResourceBundle.getBundle("prop.s");
		
		try {
			HBox root = FXMLLoader.load(getClass().getResource("Bundle.fxml"),bundle);
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			
			
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
