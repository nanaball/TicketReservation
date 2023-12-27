package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class RootController implements Initializable{
	
	// 도화지
	@FXML private Canvas canvas;
	
	// 그리기도구
	private GraphicsContext gc;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		gc = canvas.getGraphicsContext2D();
		
		// 그리기 도구 활용 방법
		
		// 그리기 도구에 채우기 색상 지정
		gc.setFill(Color.RED);
		
		// 사각형 그리기
		// (가로위치,세로위치,너비(폭),높이)
		gc.fillRect(100, 100, 100, 100);
		
		gc.setFill(Color.YELLOW);
		gc.fillRect(200, 200, 200, 200);

		gc.setFill(Color.GREENYELLOW);
		gc.fillRect(100, 300, 150, 150);
		
		// 외곽선 색 지정
		gc.setStroke(Color.BLUE);
		gc.strokeRect(150, 150, 100, 100);
		
		// 선그리기 도구
		// (시작x, 시작y, 끝x, 끝y)
		gc.strokeLine(200, 200, 200, 250);
		
		
		// 여러선을 이어 그려주는 도구
		gc.beginPath();	// 선이어 그리기 시작
		gc.setStroke(Color.CADETBLUE);
		// lineTo(x, y)
		gc.lineTo(10, 10);
		gc.lineTo(20, 30);
		gc.setStroke(Color.RED);
		gc.lineTo(50, 40);
		gc.lineTo(10, 10);
		gc.stroke(); // 선그리기
		
		gc.setStroke(Color.BLACK);
		
		// 호를 기르는 도구 
		gc.strokeOval(100,100,50,50);
		
		// 이미지 그리기 도구
		// 그려줄 이미지
		Image image = new Image(
			getClass().getResource("img/cat4.jpg").toString()
		);
		
		// 		  (그려줄 이미지, 시작x, 시작y)
		// gc.drawImage(image, 300, 300);
		// 		(그려줄 이미지, 시작x, 시작y, 너비, 높이)
		gc.drawImage(image, 300, 300, 100, 100);
	
		Thread t = new Thread(()->{
			
			for(int i = 0; i < canvas.getWidth(); i++) {
				// 지우고 새로 그려서 잔상이 안남음
				gc.clearRect(i, i, 100, 100);
				gc.drawImage(image, i, i, 100, 100);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}); 
		// 주스레드로 하면 스테이지를 삭제해도 영상이 끝날때까지 작업하고 있어서 데몬으로 변경
 		t.setDaemon(true);
		t.start();
				
	}	// end initialize
	
	// 이미지 그리기 지우기 시작 x,y 좌표와 너비 높이
	double x,y, width, height;

	// Main에서 stage가 show 하기 전에 Scene정보를 받아옴

	public void setScene(Scene scene) {
		Image image2 = new Image(
			getClass().getResource("img/dog.jpg").toString()
		);
		
		x = 300;
		y = 0;
		width = height = 100;
		
		gc.drawImage(image2, x, y, width, height);

		scene.setOnKeyPressed(e->{
			System.out.println(e.getCode());
			width = height = 100;
	
			if(e.getCode() == KeyCode.UP) {
				y--;
				height++;
			}else if(e.getCode() == KeyCode.DOWN) {
				y++;
			}else if(e.getCode() == KeyCode.LEFT) {
				x--;
				width++;
			}else if(e.getCode() == KeyCode.RIGHT) {
				x++;
			}
			gc.clearRect(x, y, width, height);
			gc.drawImage(image2, x, y, 100, 100);
			
			
		});
		
	}
	
	
}
