package post;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MainController implements Initializable {

	// 달력을 표시할 그리드
	@FXML
	private GridPane calendarPane;

	@FXML
	private ImageView ImgPost;
	@FXML
	private TextArea txtEX;
	@FXML
	private Button btnRe, btnBMonth, // 이전달 버튼
			btnToday, // 이번달 버튼
			btnNMonth; // 다음달 버튼
	
	@FXML
	private TableView<CastVO> tableView;
	
	@FXML 
	private ListView<String> listView;

	public static ObservableList<CastVO> list;

	public static CastVO student;
	
	@FXML
	private TextFlow txtfTitle;

	// 선택 월
	private LocalDate date;
	// 현재의 월
	private LocalDate now;

	
	// 이번달 달력을 표시
	@FXML
	void TodayClick(ActionEvent e) {
		date = LocalDate.now();
		String strYearMonth = (date.getYear()) + "년" + (date.getMonthValue()) + "월";
		btnToday.setText(strYearMonth);
		setButton();
	}

	// 이전달 달력 표시 
	@FXML
	void BMonthClick(ActionEvent e) {
		date = date.minusMonths(1);
		String strYearMonth = (date.getYear()) + "년" + (date.getMonthValue()) + "월";
		btnToday.setText(strYearMonth);
		setButton();
	}
		
	// 다음달 달력 표시
	@FXML
	void NMonthClick(ActionEvent e) {
		date = date.plusMonths(1);
		String strYearMonth = (date.getYear()) + "년" + (date.getMonthValue()) + "월";
		btnToday.setText(strYearMonth);
		setButton();
	}
	
	
	
	
	
	// 버튼 클릭시 달력 생성 
	private void setButton() {
		calendarPane.getChildren().clear();
		
		
		LocalDate firstDate = date.withDayOfMonth(1);
		System.out.println(firstDate);
		System.out.println(firstDate.getDayOfWeek());
		// sunday == 7 , 토요일 == 6, 월요일 == 1
		int weekDay = firstDate.getDayOfWeek().getValue();
		System.out.println(weekDay);
		// LocalDate lastDate = date.withDayOfMonth(date.lengthOfMonth());
		// System.out.println(lastDate);
		// int lastDay = lastDate.getDayOfMonth();
		// System.out.println(lastDay);
		int lastDay = date.lengthOfMonth();
		if(weekDay == 1) {
			lastDay += 1;
		}else if(weekDay == 2) {
			lastDay += 2;
		}else if(weekDay == 3) {
			lastDay += 3;
		}else if(weekDay == 4) {
			lastDay += 4;
		}else if(weekDay == 5) {
			lastDay += 5;
		}else if(weekDay == 6) {
			lastDay += 6;
		}
		double weekCount = Math.ceil(lastDay / 7.0);
		System.out.println(weekCount);
		
		
		LocalDate day =  date.withDayOfMonth(1);
		int dayCount = 1;
		System.out.println(lastDay );
		btnLabel : for(int i = 1; i <= weekCount; i++) {
			// System.out.println(i);
			for(int j = 0; j < 7; j++) {
				if(dayCount > date.lengthOfMonth()) {
					break btnLabel;
				}
				if(i == 1 && weekDay > j) {
					System.out.print("그리면 안됨");
				}else {
					// System.out.print((i+":"+j)+"그려줌"); 버튼 크려줌
					// 버튼 날짜 생성
					String strDay = (dayCount < 10) ? "0"+dayCount: String.valueOf(dayCount);
					Button btn = new Button(strDay);
					// 버튼 넓이
					btn.setPrefWidth(30);
					calendarPane.add(btn, j, i-1);
					btn.setUserData(day);
					System.out.println(date.equals(now));
					
					// 현재 날짜 이전 버튼 비활성화  
					if((date.getYear() < now.getYear()) || (date.getYear() == now.getYear() && date.getMonthValue() < now.getMonthValue()) || date.equals(now) && dayCount < date.getDayOfMonth()) {
						btn.setDisable(true);
					}
					
					// 버튼 클릭시 예약 처리
					btn.setOnAction(e->{
						reservation(e);
					});
					
					// 일요일 색상 지정
					if(day.getDayOfWeek().getValue() == 7) {
						btn.setStyle("-fx-text-fill:red");
					// 토요일 색상 지정
					}else if(day.getDayOfWeek().getValue() == 6) {
						btn.setStyle("-fx-text-fill:blue");
					}
											
					
					System.out.print(" day : " + day.getDayOfMonth());
					System.out.print(", week : "+day.getDayOfWeek().getValue()+"  ");
					day = day.plusDays(1);
					dayCount++;
				}
			}
			System.out.println();
		}
	}

	// 버튼 클릭시 예약 처리
	private void reservation(ActionEvent e) {
		Button btn = (Button)e.getTarget();
		LocalDate selectDate = (LocalDate)btn.getUserData();
		System.out.println(selectDate);	// 선택한 날짜
		
		date = LocalDate.now();	// 오늘 날짜
		System.out.println(date);	
		
		System.out.println(date.getDayOfMonth()); // 오늘 일자	
		System.out.println(selectDate.getDayOfMonth());	// 선택한 일자 
		
		
		// 예매일 관련 안내 	
		if( (date.getDayOfYear()+7)< selectDate.getDayOfYear() ){
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setHeaderText("해당일자는 예약이 불가합니다.");
		    alert.setContentText("예매는 공연일 일주일 전 오후 2시부터 가능합니다.");
		    alert.showAndWait();
			}
		
		
		
		
	} 
	
	
		
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// 서버 소켓 연결 
			try {	
				Socket socket = new Socket("10.100.205.92", 5001);
				// TODO 추가 아이피 수정							
			} catch (UnknownHostException e) {
				Platform.exit();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		 
		
		// 오늘 날짜 
		date = LocalDate.now();
		now = LocalDate.now();
		
		
		// 리스트에 공연 시간 안내 
		
		list = FXCollections.observableArrayList(
			new CastVO("2024-01-10", "7시","광개토대왕, 왕건, 이순신, 안중근, 김유신"), 
			new CastVO("2024-01-11", "7시","박혁거세, 세종대왕, 단군왕검, 이순신, 김구"),
			new CastVO("2024-01-12", "7시","고길동, 장보고, 서희, 강감찬, 이황"),
			new CastVO("2024-01-13", "7시","이순신, 장보고, 서희, 광개토대왕, 안중근"),
			new CastVO("2024-01-14", "7시","대조영, 장보고, 서희, 고길동, 이황"),
			new CastVO("2024-01-15", "7시","단군왕검, 장보고, 이순신, 강감찬, 이황"),
			new CastVO("2024-01-16", "7시","대조영, 안중근, 서희, 강감찬, 단군왕검"),
			new CastVO("2024-01-17", "7시","고길동, 장보고, 서희, 광개토대왕, 이황"),
			new CastVO("2024-01-18", "7시","대조영, 이순신, 고길동, 이순신, 이황"),
			new CastVO("2024-01-19", "7시","안중근, 장보고, 서희, 강감찬, 이황"),
			new CastVO("2024-01-20", "7시","대조영, 장보고, 서희, 강감찬, 이황"),
			new CastVO("2024-01-21", "7시","대조영, 광개토대왕, 서희, 단군왕검, 이순신"),
			new CastVO("2024-01-22", "7시","대조영, 안중근, 서희, 강감찬, 이황"),
			new CastVO("2024-01-23", "7시","고길동, 장보고, 서희, 강감찬, 이황"),
			new CastVO("2024-01-24", "7시","대조영, 고길동, 서희, 강감찬, 이황"),
			new CastVO("2024-01-25", "7시","이순신, 장보고, 안중근, 강감찬, 이황"),
			new CastVO("2024-01-26", "7시","대조영, 광개토대왕, 서희, 강감찬, 안중근"),
			new CastVO("2024-01-27", "7시","대조영, 장보고, 서희, 강감찬, 고길동"),
			new CastVO("2024-01-28", "7시","이순신, 김유신, 서희, 이순신, 이황"),
			new CastVO("2024-01-29", "7시","대조영, 장보고, 서희, 강감찬, 이황"),
			new CastVO("2024-01-30", "7시","고길동, 단군왕검, 서희, 강감찬, 이황"),
			new CastVO("2024-01-31", "7시","대조영, 장보고, 김유신, 고길동, 이황"),
			new CastVO("2024-02-01", "7시","단군왕검, 장보고, 서희, 강감찬, 김유신")
			);
				
						
			Class<CastVO> clazz = CastVO.class;
			Field[] fields = clazz.getDeclaredFields();
					
			for(Field f : fields) {
				String time = f.getName();
				TableColumn<CastVO,String> tc = new TableColumn<>(time);
				tc.setCellValueFactory(new PropertyValueFactory<>(time));
				tc.setStyle("-fx-alignment:center; -fx-text-fill:black;");
				tableView.getColumns().add(tc);
				}
				tableView.setItems(list);
			
		
		
				
				
				
				
			
				
				
		
		// 예매하기 버튼 클릭시 로그인 화면 이동 
		btnRe.setOnAction(e -> {

			try {
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
				stage.setScene(new Scene(root));
				stage.setTitle("로그인 화면");
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	


}
