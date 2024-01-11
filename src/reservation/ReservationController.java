package reservation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import main.Receivable;
import pay.PayController;

public class ReservationController implements Initializable, Receivable {

	@FXML
	private Button btnSc, btnBack;
	@FXML
	private VBox btnBox;
	@FXML
	private TableView<SeatVO> tableView;
	@FXML
	private TextField selectS;
	@FXML
	private Label selectDate;
	@FXML
	private Label selectTime, selectMusical;
	@FXML 
	private ImageView musicalImg;
	

	public static ObservableList<SeatVO> list;
	ObservableList<String> ttime; // TableView

	public String reservSeat; // 선택된 좌석

	// 선택된 버튼
	public Button selectedButton;
	public String btnStyle;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.thread.reservationController = this;
		selectDate.setText(Main.castVO.getDate());
		selectTime.setText(Main.castVO.getTime());
		selectMusical.setText(Main.castVO.getMusicalNa());
		Main.thread.sendData("1|"+Main.castVO.getMusicalNa()+","+Main.castVO.getDate()+","+Main.castVO.getTime());

		
		// 레미제라블
		Image image = new Image(
				getClass().getResource("/img/les.gif").toString()
			);
		// 노트르담드파리
		Image image1 = new Image(
				getClass().getResource("/img/PARIS.gif").toString()
			);
		// 레베카
		Image image2 = new Image(
				getClass().getResource("/img/rebecca.gif").toString()
			);
		
		
		switch(Main.castVO.getMusicalNa()) {
		case "레미제라블" :
			musicalImg.setImage(image);
			break;
		case "노트르담드파리" :
			musicalImg.setImage(image1);
			break;
		case "레베카" :
			musicalImg.setImage(image2);
			break;
		}
		
			
		btnBack.setOnAction((e)->{
			selectDate = null;
			selectTime = null;
			selectMusical = null;
			alertWarning("좌석 선택을 취소하였습니다.");
			Stage end = (Stage)btnBack.getScene().getWindow();
			end.close();
		});
		
		// 예매하기 버튼 클릭시 창 띄우기 파일명 넣기 null 자리에 "pay.fxml"+fxml 파일에서 컨트롤러 확인
		btnSc.setOnAction((e) -> {
			if(Main.reservTicket == null) {
				alertWarning("좌석을 먼저 선택해주세요.");
				return;
			}
			
			try {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/pay/PayMain.fxml"));
				Parent root = loader.load();
				PayController controller = loader.getController();
				
				controller.setStage(stage, (Stage)btnSc.getScene().getWindow());
				stage.setScene(new Scene(root));
				stage.setTitle("결제하기");
				stage.initOwner(btnSc.getScene().getWindow());
				stage.initModality(Modality.WINDOW_MODAL);
				stage.show();
				

				// 창 닫기 눌렀을 때 나오는 확인 알림창
				// 확인을 누르면 창이 결제 취소, 취소를 누르면 결제 진행
				// 근데 화면이 뒤로 숨어요
				stage.setOnCloseRequest(event->{
					event.consume();
					Cancel(stage);
				});

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});


		// 좌석 정보 노출
		ObservableList<SeatVO> list = FXCollections.observableArrayList();
		SeatVO vips = new SeatVO("VIP 좌석", "100,000원");
		SeatVO order = new SeatVO("일반좌석", "30,000원");

		
		list.add(vips);
		list.add(order);

		ObservableList<TableColumn<SeatVO, ?>> columnList = tableView.getColumns();
		TableColumn<SeatVO, ?> gradeColumn = columnList.get(0);
		TableColumn<SeatVO, ?> priceColumn = columnList.get(1);
		
		gradeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		gradeColumn.setStyle("-fx-alignment:center");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceColumn.setStyle("-fx-alignment:center");
		tableView.setItems(list);
		
		
	} // end initialize

	
	/**
	 * 서버 수신 데이터
	 */
	@Override
	public void receiveData(String message) {
		// 예약 좌석 목록
		// 1|E-9,c-7, ...
		// 1|D-9,F-6,E-10,C-10,B-8,D-8,D-6
		Platform.runLater(()->{
			String[] orders = message.split("\\|");
			String[] seats = null;
			if(orders.length > 1) {
				seats = orders[1].split(",");
			}
			System.out.println(message);
			System.out.println(Arrays.toString(seats));
			if(seats != null) {
				setSeats(Arrays.asList(seats));
			}else {
				setSeats(new ArrayList<>());
			}
		});
	} // end receiveData
	
	public void setSeats(List<String> list) {
		// 버튼 만들기
		for (int i = 1; i < 11; i++) {
			HBox hbox = new HBox();
			hbox.setPrefWidth(500);
			hbox.setSpacing(20);
			for (int j = 1; j < 11; j++) {
				int val = 64 + i;
				char value = (char) val;
				Button b = new Button(value + "-" + j);
				if(list.contains(b.getText())) {
					b.setDisable(true);
				}
				b.setMaxWidth(Double.MAX_VALUE);
				b.setStyle("-fx-border-color:black");
				HBox.setHgrow(b, Priority.ALWAYS);

				// 스타일 적용. 자리마다 다른 색
				if (i < 6) {
					b.setStyle("-fx-background-color:lightblue");
					VBox.setMargin(hbox, new Insets(0, 0, 10, 0));
				} else {
					b.setStyle("-fx-background-color:lightpink");
					VBox.setMargin(hbox, new Insets(10, 0, 10, 0));
				}

				hbox.getChildren().add(b);
				// 버튼 클릭 event
				b.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						reservSeat = b.getText();
						selectS.setText(reservSeat);
						String receiveData = reservSeat;
						if (selectedButton != null && btnStyle != null) {
							selectedButton.setStyle(btnStyle);
						}
						selectedButton = b;
						btnStyle = b.getStyle();
						b.setStyle("-fx-background-color:gray");
						b.setDefaultButton(false);
						String[] ticket = receiveData.split("\\-");
						String code = ticket[0];

						Main.reservTicket = new TicketVO();
						Main.reservTicket.setUserID(Main.loginMember.getUserID());
						Main.reservTicket.setMusical(Main.castVO.getMusicalNa());
						Main.reservTicket.setDate(Main.castVO.getDate());
						Main.reservTicket.setTime(Main.castVO.getTime());
						Main.reservTicket.setSeatNum(reservSeat);
						if (code.equals("A") || code.equals("B") || code.equals("C") || code.equals("D") || code.equals("E")) {
							System.out.println("10만원");
							selectS.setText("VIP 좌석      " + reservSeat + "      10만원");
							Main.reservTicket.setPay(100000);
						} else {
							selectS.setText("일반좌석       " + reservSeat + "      3만원");
							System.out.println("3만원");
							Main.reservTicket.setPay(30000);
						}
						System.out.println(Main.reservTicket);
					} // end handle
				}); // end action event
			}
			btnBox.getChildren().add(hbox);
		}
	}
	
	
	// 경고 알림창
	public void alertWarning(String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.show();
		
	}
	

	// PayMain 창 닫기 눌렀을 때 알림창
	public void Cancel(Stage stage){
	    Alert alert1 = new Alert(AlertType.CONFIRMATION);
	    alert1.setTitle("결제 창 나가기");				
	    alert1.setHeaderText("정말로 결제 창에서 나가시겠습니까?");
	    alert1.setContentText("작성한 정보는 저장되지 않습니다.");
	    
	    if(alert1.showAndWait().get() == ButtonType.OK) {
	  	  System.out.println("결제 취소");
	  	  stage.close();
	    }else {
	    	System.out.println("결제 진행");
	    }
	}
	

}
