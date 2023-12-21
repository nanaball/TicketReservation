package l01_property_listener;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootController implements Initializable{

	@FXML private ImageView img;
	@FXML private Slider slider;
	@FXML private ToggleButton toggle;
	
	
	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		System.out.println(slider.valueProperty());
		double value = slider.valueProperty().doubleValue();
		System.out.println(value);
		setImageWidth(value);
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			// 함수형 인터페이스 
			@Override
			public void changed(
					ObservableValue<? extends Number> observable, 
					Number oldValue, 
					Number newValue) {
				System.out.println(observable);
				System.out.println("oldValue : " + oldValue);
				System.out.println("newValue : " + newValue);
			}
		});
		
		slider.valueProperty().addListener((target,oldValue,newValue)->{
			// Number 
			byte b = newValue.byteValue();
			System.out.println(b);
			int i = newValue.intValue();
			System.out.println(i);
			double d = newValue.doubleValue();
			System.out.println(d);
			setImageWidth(d);
		});
		
		toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> target, 
					Boolean oldValue, 
					Boolean newValue) {
				System.out.println(newValue);
				String path = "animals/dog.jpg";
				
				if(newValue) {
					path = "animals/cat3.jpg";
				}
				URL url = getClass().getResource(path);
				Image image = new Image(url.toString());
				img.setImage(image);
				
				
			}
		});
		
		
	}	// end initialize 
	
	public void setImageWidth(double value) {
		// slider 속성 값의 크기는 0-100
		double width = ((double)350/100) * value;
		width = (width == 0) ? 1 : width;
		img.setFitWidth(width);
		System.out.println("image 크기 : " + img.getFitWidth());
	}
	
}
