package application;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RootController implements Initializable{
	
	@FXML private Label lblTime;
	@FXML private Button btnStart, btnStop;
	
	// timer 종료 flag
	private boolean isRun;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		btnStart.setOnAction(e->{
			isRun = true;
			Stage stage = (Stage) lblTime.getScene().getWindow();
			// 창이름 timer로 지정
			stage.setTitle("Timer");
			System.out.println(Thread.currentThread());
			// isDaemon - 주스레드 도와주는 스레드 / false면 데몬스레드x, 주스레드ㅇ
			System.out.println(Thread.currentThread().isDaemon());
			
			
			// while을 thread로 변경
			Thread t = new Thread(()->{

				LocalTime time = LocalTime.of(0, 0, 0);

				System.out.println(Thread.currentThread());
				
				while(isRun) {
					// nano times - 1,000,000,000, 10억분의 1초 
					// 1,000,000	millis
					// 10,000,000	10millis
					time = time.plusNanos(10000000);
					
					String data = time.format(
						DateTimeFormatter.ofPattern("mm:ss:SS")
					);
					// System.out.println(data);
					Platform.runLater(()->{
						lblTime.setText(data);
					});
					/*
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							lblTime.setText(data);							
						}	
					});
					*/
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {}
				} // end while 	
				
			}); // end Thread Runnable run()
			// 데몬스레드로 변경 및 주스레드 종료시 데몬도 종료 
			t.setDaemon(true);
			t.start();
			
		}); // end btnStart.setOnAction(e->{
		
		btnStop.setOnAction(e->{
			isRun = false;
		});
		
	}	// end initialize
	
}
