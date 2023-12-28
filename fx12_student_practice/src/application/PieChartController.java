package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.StudentVO;

public class PieChartController implements Initializable {

	@FXML private PieChart pieChart;
	@FXML private Button btnClose;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(RootController.student);
		
		pieChart.setData(FXCollections.observableArrayList(
			new PieChart.Data("국어", RootController.student.getKor()),
			new PieChart.Data("수학", RootController.student.getMath()),
			new PieChart.Data("영어", RootController.student.getEng())
		));
		
		btnClose.setOnAction(e->{
			((Stage)pieChart.getScene().getWindow()).close();
		});
	
	}

	public void setStudent(StudentVO student) {
		System.out.println(student);
		System.out.println(pieChart);
		System.out.println(btnClose);
	
		pieChart.setData(FXCollections.observableArrayList(
			new PieChart.Data("국어", student.getKor()),
			new PieChart.Data("수학", student.getMath()),
			new PieChart.Data("영어", student.getEng())
		));
	

	}
	
}
