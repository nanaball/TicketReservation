package post;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import main.Receivable;

public class PostController implements Initializable, Receivable {

	// 달력을 표시할 그리드
	@FXML
	private GridPane calendarPane;

	@FXML
	private ImageView ImgPost;
	@FXML
	private TextArea txtEX;
	@FXML
	private Button btnRe, // 예매하기
			btnBMonth, // 이전달 버튼
			btnToday, // 이번달 버튼
			btnNMonth, // 다음달 버튼
			btnCk; // 예약확인

	@FXML
	private TableView<CastVO> tableView;

	public static ObservableList<CastVO> list;

	public static CastVO student;

	@FXML
	private TextFlow txtfTitle;

	// 선택 월
	private LocalDate date;
	// 현재의 월
	private LocalDate now;

	// 선택된 버튼
	public Button selectedButton;
	public String btnStyle;

	// 이번달 달력을 표시
	@FXML
	void TodayClick(ActionEvent e) {
		date = LocalDate.now();
		String strYearMonth = (date.getYear()) + "년  " + (date.getMonthValue()) + "월 ";
		btnToday.setText(strYearMonth);
		setButton();
	}

	// 이전달 달력 표시
	@FXML
	void BMonthClick(ActionEvent e) {
		date = date.minusMonths(1);
		String strYearMonth = (date.getYear()) + "년  " + (date.getMonthValue()) + "월 ";
		btnToday.setText(strYearMonth);
		setButton();
	}

	// 다음달 달력 표시
	@FXML
	void NMonthClick(ActionEvent e) {
		date = date.plusMonths(1);
		String strYearMonth = (date.getYear()) + "년  " + (date.getMonthValue()) + "월 ";
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
		if (weekDay == 1) {
			lastDay += 1;
		} else if (weekDay == 2) {
			lastDay += 2;
		} else if (weekDay == 3) {
			lastDay += 3;
		} else if (weekDay == 4) {
			lastDay += 4;
		} else if (weekDay == 5) {
			lastDay += 5;
		} else if (weekDay == 6) {
			lastDay += 6;
		}
		double weekCount = Math.ceil(lastDay / 7.0);
		System.out.println(weekCount);

		LocalDate day = date.withDayOfMonth(1);
		int dayCount = 1;
		System.out.println(lastDay);
		btnLabel: for (int i = 1; i <= weekCount; i++) {
			// System.out.println(i);
			for (int j = 0; j < 7; j++) {
				if (dayCount > date.lengthOfMonth()) {
					break btnLabel;
				}
				if (i == 1 && weekDay > j) {
					System.out.print("그리면 안됨");
				} else {
					// System.out.print((i+":"+j)+"그려줌"); 버튼 크려줌
					// 버튼 날짜 생성
					String strDay = (dayCount < 10) ? "0" + dayCount : String.valueOf(dayCount);
					Button btn = new Button(strDay);
					// 버튼 넓이 및 높이
					btn.setPrefWidth(45);
					btn.setPrefHeight(45);
					btn.setStyle("-fx-background-color:#FFF2E6; -fx-background-radius:180;");
					calendarPane.add(btn, j, i - 1);
					btn.setUserData(day);
					System.out.println(date.equals(now));

					// 현재 날짜 이전 버튼 비활성화
					if ((date.getYear() < now.getYear())
							|| (date.getYear() == now.getYear() && date.getMonthValue() < now.getMonthValue())
							|| date.equals(now) && dayCount < date.getDayOfMonth()) {
						btn.setDisable(true);
					}

					// 버튼 클릭시 예약 처리
					btn.setOnAction(e -> {
						reservation(e);
					});

					// 일요일 색상 지정
					if (day.getDayOfWeek().getValue() == 7) {
						btn.setStyle("-fx-text-fill:red; -fx-background-color:#FFF2E6; -fx-background-radius:45;");

						// 토요일 색상 지정
					} else if (day.getDayOfWeek().getValue() == 6) {
						btn.setStyle("-fx-text-fill:blue; -fx-background-color:#FFF2E6; -fx-background-radius:45;");

						// 현재 날짜 색상 지정
					} else if (day.equals(now)) {
						btn.setStyle(
								"-fx-text-fill:#47C83E; -fx-font-weight:bolder; -fx-background-color:#FFF2E6; -fx-background-radius:45;");
					}

					System.out.print(" day : " + day.getDayOfMonth());
					System.out.print(", week : " + day.getDayOfWeek().getValue() + "  ");
					day = day.plusDays(1);
					dayCount++;
				}
			}
			System.out.println();
		}
	}

	// 버튼 클릭시 예약 처리
	private void reservation(ActionEvent e) {
		Button btn = (Button) e.getTarget();
		LocalDate selectDate = (LocalDate) btn.getUserData();
		System.out.println(selectDate); // 선택한 날짜

		date = LocalDate.now(); // 오늘 날짜
		System.out.println(date);

		System.out.println(date.getDayOfMonth()); // 오늘 일자
		System.out.println(selectDate.getDayOfMonth()); // 선택한 일자

		// 클릭한 버튼 색상 변경
		if (selectedButton != null && btnStyle != null) {
			selectedButton.setStyle(btnStyle);
		}
		selectedButton = btn;
		btnStyle = btn.getStyle();
		btn.setStyle(
				"-fx-background-color:orange; -fx-background-radius:45; -fx-text-fill:white; -fx-font-weight:bolder; ");
		btn.setDefaultButton(false);

		// 예매일 관련 안내
		if ((date.getDayOfYear() + 7) < selectDate.getDayOfYear()) {
			list.clear();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("해당일자는 예약이 불가합니다.");
			alert.setContentText("예매는 공연일 일주일 전 오후 2시부터 가능합니다.");
			alert.showAndWait();			
		} else {
			main.Main.thread.sendData("3|0|" + selectDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		main.Main.thread.postController = this;
		// 오늘 날짜
		date = LocalDate.now();
		now = LocalDate.now();

		// 리스트에 공연 시간 안내
		main.Main.thread.sendData("3|0|" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		list = FXCollections.observableArrayList();
//		list = FXCollections.observableArrayList(
//				new CastVO("2024-01-17", "7시", "고길동, 장보고, 서희, 광개토대왕, 이황"),
//			);

		Class<CastVO> clazz = CastVO.class;
		Field[] fields = clazz.getDeclaredFields();

		for (Field f : fields) {
			String time = f.getName();
			TableColumn<CastVO, String> tc = new TableColumn<>(time);
			tc.setCellValueFactory(new PropertyValueFactory<>(time));
			tc.setStyle("-fx-alignment:center; -fx-text-fill:black;");
			// tc.setPrefWidth(double value);

			tableView.getColumns().add(tc);

			list.clear();
		}
	



		// 테이블 뷰에서 캐스팅 정보 선택
		tableView.getSelectionModel().selectedItemProperty().addListener((t, o, n) -> {
			Main.castVO = n;
			System.out.println(Main.castVO + "--------");
		});
		;

		// 예매하기 버튼 클릭시 화면 이동
		btnRe.setOnAction(e -> {
			try {
				Stage stage = new Stage();
				// 기존창에 포커스 주지 않음
				stage.initModality(Modality.APPLICATION_MODAL);
				if (Main.castVO == null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("예매하실 날짜와 시간을 먼저 선택해주세요.");
					alert.showAndWait();
				} else {
					Parent root = FXMLLoader.load(getClass().getResource("/reservation/Reservation.fxml"));
					stage.setScene(new Scene(root));
					stage.setTitle("좌석 선택");
					stage.show();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		// 예매확인 버튼 클릭시 화면 이동
		btnCk.setOnAction(e -> {
			try {
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);

				Parent root;
				root = FXMLLoader.load(getClass().getResource("/pay/ReserveCheck.fxml"));
				stage.setScene(new Scene(root));
				stage.setTitle("예매확인");

				stage.show();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}

	/**
	 * server 에서 mainThread도 전달된 data를 컨트롤러에 전달
	 */
	@Override
	public void receiveData(String message) {
		// TODO receive된 데이터로 결과 처리
		System.out.println("PostController receive Data: " + message);

		tableView.refresh();
		list.clear();
		tableView.setItems(list);

		String[] row = message.split("\\|");
		String firstOrder = row[0]; // == 3
		// 3| == Post
		String secondOrder = row[1];
		// 3|0 == musicalInfo
		// 3|0|0 == 뮤지컬 정보 있음
		// 3|0|1 == 뮤지컬 정보 없음.

		// 추가 기능
		// 3|1 ...
		if (secondOrder.equals("0")) {
			String thirdOrder = row[2];
			// 3|0 == musicalInfo
			if (thirdOrder.equals("0")) {
				// 뮤지컬 정보 있음 tableView에 출력
				// 3|0|0| ~~~~~~~ 뮤지컬 목록 나열
				// 3|0|0|2!2024-01-08!23:11:10!한글2!^3!2024-01-08!23:23:10!한글3!^
				String musicalList = row[3];

				for (String data : musicalList.split("\\^")) {
					System.out.println(data);
					if (data.equals("")) {
						continue;
					}
					String[] castData = data.split("\\!");
					list.add(new CastVO(Integer.parseInt(castData[0]), castData[1], castData[2], castData[3],
							castData[4]));
				}
			}
			// else {// 해당 되는 날짜에 뮤지컬 정보 없음.} // tableView에 목록 미출력
		} else if (secondOrder.equals("1")) {
			// 3|1 추가 기능...
		}
	}
}
