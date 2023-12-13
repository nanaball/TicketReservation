package application;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;

public class LifeCycleMain extends Application{
	
	public LifeCycleMain() {
		System.out.println(Thread.currentThread()+"- 생성자 호출");
	}
	
	@Override
	public void init() throws Exception {
		System.out.println(Thread.currentThread()+" init() 호출");
		
		// main method에서 실행 시 넘겨받은 arguments(인자값)을 application에서 사용성있게 가지고 오는 법
		Parameters params = getParameters();
		List<String> allList = params.getRaw();
		System.out.println("전체 : " + allList);
		
		List<String> unNamedList = params.getUnnamed();
		System.out.println(unNamedList);

		Map<String,String> map = params.getNamed();
		System.out.println(map);
		System.out.println(map.get("PORT"));
	}

	@Override
	public void stop() throws Exception {
		System.out.println(Thread.currentThread()+" stop() 호출");
	
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println(Thread.currentThread()+" start() 호출");
		stage.show();
	}
		
	
	public static void main(String[] args) {
		
		// Application.launch(args);
		System.out.println(Arrays.toString(args));
		System.out.println(Thread.currentThread()+"- Main 시작");
		launch(args);
		System.out.println(Thread.currentThread()+"- Main 종료");
	
		
	}

}
