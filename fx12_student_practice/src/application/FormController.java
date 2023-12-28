package application;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.StudentVO;

public class FormController {

	@FXML private TextField txtName, txtKor, txtMath, txtEng;
	@FXML private Button btnSave, btnCancel;
	
	
	// tableView의 리스트 정보, 현재 control이 등록된 form stage 정보 
	// initialaze 필요x
	public void init(ObservableList<StudentVO> list, Stage formStage) {	
		btnCancel.setOnAction(e->{
			formStage.close();
		});
		
		// 저장 버튼 event 
		btnSave.setOnAction(e->{
			// 저장 버튼 선택 시점에 TextField에 작성된 내용으로 RootController의 tableView list 학생 정보에 추가
			
			String name = txtName.getText().trim();
			String strKor = txtKor.getText().trim();
			String strMath = txtMath.getText().trim();
			String strEng = txtEng.getText().trim();
			
			boolean isChecked = checkInteger(strKor, strMath, strEng);
			System.out.println(isChecked);
			// int kor = Integer.parseInt(strKor);
					
			if(isChecked) {
				int kor = Integer.parseInt(strKor);
				int math = Integer.parseInt(strMath);
				int eng = Integer.parseInt(strEng);
				StudentVO vo = new StudentVO(name,kor,math,eng);
				// list.add(vo); // tableView list 항목에 학생 정보 추가
				RootController.list.add(vo); // tableView list 항목에 학생 정보 추가		
			}
			
			txtName.clear();
			txtKor.clear();
			txtMath.clear();
			txtEng.clear();
			txtName.requestFocus();
		});
	
	} // end init

	public boolean checkInteger(String... scores) {
		
		// input : 100 == [1][0][0]
		// char[] chars = scores[0].toCharArray();
		for(String str : scores) {
			System.out.println(str);
			for(char c : str.toCharArray()) {
				System.out.println(c);
				if(c < 48 || c > 57) {
					// 숫자로 변환할 수 없는 문자가 포함. 
					return false;
				}
			}
		}
		return true;
	}
	

}
