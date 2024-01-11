package pay;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.Receivable;
import reservation.TicketVO;

public class ReserveCheckController implements Initializable, Receivable {

	@FXML
	private TableView<TicketVO> tableView;
	@FXML
	Button btnCk;
	
	public static ObservableList<TicketVO> list;
	

	Connection conn = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.thread.ReserveCheckController = this;
		// 예매내역 안내 
		// 예약 목록 요청
		// userID 정보 가져오기 
		Main.thread.sendData("2|3|"+Main.loginMember.getUserID());
		
//		tableView 목록 리스트 초기화
		list = FXCollections.observableArrayList();
// 		TableColumn 초기화
		Class<TicketVO> clazz = TicketVO.class;
		Field[] fields = clazz.getDeclaredFields();

		for (Field f : fields) {
			// musicalName, date, time, seatNum
			String fileName = f.getName();
			// 필요한 항목만 컬럼에 출력
			if(fileName.equals("musical") || fileName.equals("seatNum") || fileName.equals("date") || fileName.equals("time")) {
				TableColumn<TicketVO, String> tc = new TableColumn<>(fileName);
				tc.setCellValueFactory(new PropertyValueFactory<>(fileName));
				tc.setStyle("-fx-alignment:center; -fx-text-fill:black;");
				tableView.getColumns().add(tc);
			}
		}
		

		// 테이블 더블 클릭시 예매 확인 창 뜨기 
		tableView.getSelectionModel().selectedItemProperty().addListener((target,o,n)->{
			System.out.println(n);
		});
		
		// 더블 클릭시
		tableView.setOnMouseClicked(e->{
			int clickCount = e.getClickCount();
			System.out.println(clickCount);
			
			MouseButton btn = e.getButton();
			System.out.println(btn);
			
			// 왼쪽 마우스 두번 클릭
			if(btn == MouseButton.PRIMARY && clickCount == 2) {
				Main.reservTicket = tableView.getSelectionModel().getSelectedItem();
				if(Main.reservTicket == null) return;
				// 이걸 티켓에서 가져오는게 아니고 db에서 가져오게 해야 할 것 같은데..
				/*
				String regReserv = "2|3|" + Main.reservTicket.getUserID() + "," + Main.reservTicket.getMusical() + "," + Main.reservTicket.getSeatNum() + ","
						+ Main.reservTicket.getPay() + "," + Main.reservTicket.getDate() + "," + Main.reservTicket.getTime();
				
				System.out.println(regReserv);
				Main.thread.sendData(regReserv);
				*/
				Stage stage = new Stage(StageStyle.UTILITY); 
				Parent root = null;
				FXMLLoader loader = null;
				
				try {
					loader = new FXMLLoader(getClass().getResource("/pay/PayCheck.fxml"));
					root = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
				
				stage.setScene(new Scene(root));							
				stage.setTitle("예매확인");
				stage.show();
			}

		});
		
		
		// 예매 확인창 꺼짐
		btnCk.setOnAction((e) -> {
			Stage now = (Stage) btnCk.getScene().getWindow();
			now.close();
		});
		
	}

	@Override
	public void receiveData(String message) {
				
		System.out.println(message);
		// 0 1 2 3
		// 2|3|0|18!33!레베카!F-6!30000!2024-01-11!18:00:00!^20!33!레베카!A-10!100000!2024-01-11!18:00:00!^22!33!레미제라블!E-10!100000!2024-01-11!22:00:00!^27!33!레베카!I-6!30000!2024-01-11!18:00:00!^32!33!레미제라블!C-10!100000!2024-01-11!22:00:00!^41!33!레미제라블!G-6!30000!2024-01-12!22:00:00!^42!33!노트르담드파리!E-10!100000!2024-01-12!23:00:00!^48!33!레베카!B-8!100000!2024-01-11!21:00:00!^50!33!레미제라블!D-8!100000!2024-01-11!22:00:00!^51!33!레베카!B-5!100000!2024-01-11!21:00:00!^52!33!레미제라블!D-6!100000!2024-01-11!22:00:00!^54!33!레미제라블!C-5!100000!2024-01-11!19:00:00!^55!33!레미제라블!B-8!100000!2024-01-11!19:00:00!^57!33!레베카!C-9!100000!2024-01-11!18:00:00!^58!33!레베카!C-6!100000!2024-01-11!18:00:00!^59!33!레베카!B-6!100000!2024-01-17!21:00:00!^60!33!레미제라블!C-8!100000!2024-01-11!22:00:00!^61!33!레미제라블!C-8!100000!2024-01-11!22:00:00!^62!33!노트르담드파리!D-6!100000!2024-01-11!20:00:00!^63!33!레베카!B-8!100000!2024-01-11!18:00:00!^66!33!레베카!G-2!30000!2024-01-11!18:00:00!^78!33!레미제라블!A-6!100000!2024-01-11!22:00:00!^
		String results = message.split("\\|")[3];
		// 18!33!레베카!F-6!30000!2024-01-11!18:00:00!
		// 20!33!레베카!A-10!100000!2024-01-11!18:00:00!
		// 22!33!레미제라블!E-10!100000!2024-01-11!22:00:00!^27!33!레베카!I-6!30000!2024-01-11!18:00:00!^32!33!레미제라블!C-10!100000!2024-01-11!22:00:00!^41!33!레미제라블!G-6!30000!2024-01-12!22:00:00!^42!33!노트르담드파리!E-10!100000!2024-01-12!23:00:00!^48!33!레베카!B-8!100000!2024-01-11!21:00:00!^50!33!레미제라블!D-8!100000!2024-01-11!22:00:00!^51!33!레베카!B-5!100000!2024-01-11!21:00:00!^52!33!레미제라블!D-6!100000!2024-01-11!22:00:00!^54!33!레미제라블!C-5!100000!2024-01-11!19:00:00!^55!33!레미제라블!B-8!100000!2024-01-11!19:00:00!^57!33!레베카!C-9!100000!2024-01-11!18:00:00!^58!33!레베카!C-6!100000!2024-01-11!18:00:00!^59!33!레베카!B-6!100000!2024-01-17!21:00:00!^60!33!레미제라블!C-8!100000!2024-01-11!22:00:00!^61!33!레미제라블!C-8!100000!2024-01-11!22:00:00!^62!33!노트르담드파리!D-6!100000!2024-01-11!20:00:00!^63!33!레베카!B-8!100000!2024-01-11!18:00:00!^66!33!레베카!G-2!30000!2024-01-11!18:00:00!^78!33!레미제라블!A-6!100000!2024-01-11!22:00:00!^
		String[] resultTicket = results.split("\\^");
		for(String ticket : resultTicket) {
			String[] t = ticket.split("\\!");
			TicketVO vo = new TicketVO(
				Integer.parseInt(t[0]),
				t[1],
				t[2],
				t[3],
				Integer.parseInt(t[4]),
				t[5],
				t[6]
			);
			list.add(vo);
		}
		Platform.runLater(()->{
			tableView.setItems(list);
		});

	}

}
