package application;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RootController implements Initializable{
	
	@FXML private Button btnAdd;
	@FXML private Button btnLine;
	@FXML private TableView<score> tableView;
	@FXML private PieChart Pie;
	@FXML private BarChart<Integer, String> Bar;
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	@FXML private TextField txtName;
	@FXML private TextField txtKor;
	@FXML private TextField txtMath;
	@FXML private TextField txtEng;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String[] strs = new String[] {
				"홍길동A","홍길동B","홍길동C"
		};
		
		List<String> strList = Arrays.asList(strs);
		
		ObservableList<String> list = FXCollections.observableArrayList(strList);
		
		System.out.println(list);
		
		ObservableList<score> scoreList = FXCollections.observableArrayList();

		ObservableList<TableColumn<score, ?>> columnList = tableView.getColumns();
		System.out.println(columnList);
		
		Field[] fields = score.class.getDeclaredFields();
		
		score scoreA = new score("홍길동A", 40, 60, 80);
		score scoreB = new score("홍길동B", 60, 80, 40);
		score scoreC = new score("홍길동C", 80, 40, 60);
		scoreList.add(scoreA);
		scoreList.add(scoreB);
		scoreList.add(scoreC);
		

		TableColumn<score, ?> columnName = columnList.get(0);
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<score, ?> columnKor = columnList.get(1);
		columnKor.setCellValueFactory(new PropertyValueFactory<>("kor"));
		
		TableColumn<score, ?> columnMath = columnList.get(2);
		columnMath.setCellValueFactory(new PropertyValueFactory<>("math"));
		
		TableColumn<score, ?> columnEng = columnList.get(3);
		columnEng.setCellValueFactory(new PropertyValueFactory<>("eng"));
		
		tableView.setItems(scoreList);

		//////// 기본 점수 입력 완 <no.1>
		
		
		
		
		// <no.2> TableView Item 선택 시 학생의 점수 %를 보여주는 Pie Chart가 포함된 Custom Dialog를 작성하시오.

		tableView.setOnMousePressed(e -> {
			
			ObservableList<PieChart.Data> listA = FXCollections.observableArrayList();
			
			// listA.add(new PieChart.Data(tableView<score>, scoreA));
			listA.add(new PieChart.Data("국어", 40));	// DATA명, 적용될 수치(double)
			PieChart.Data data = new PieChart.Data("수학", 60);
			listA.add(data);
			listA.add(new PieChart.Data("영어", 80));
			
			Pie.setData(listA);	
		});
		
		
		
		
		
		// <no.4> 학생별 막대 그래프 선택 시 학생들의 정보를 보여주는 BarChart를 포함하는  Custom Dialog를 완성하세요.
		
		Thread t2 = new Thread(()->{

		btnLine.setOnAction(e->{
				
			// 막대 카테고리별 데이터가 어떤 정보를 저장하고 있는지를 표현하는 Series
			XYChart.Series<Integer, String> allscore =  new XYChart.Series<>();
			allscore.setName("점수");
			
			ObservableList<XYChart.Data<Integer, String>> listBar = FXCollections.observableArrayList();
			listBar.add(new XYChart.Data<>(40, "홍길동"));		
			
			Bar.getData().add(allscore);

			allscore.setData(listBar);
			
			
			
			
		});	
		
		});
		
	}
	
	
	
}
