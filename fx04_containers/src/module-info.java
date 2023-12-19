module fx04_containers {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens c1_anchor_pane to javafx.graphics, javafx.fxml;
	opens c2_hbox_vbox to javafx.graphics, javafx.fxml;
	opens c3_border_pane to javafx.graphics, javafx.fxml;
	opens c4_flow_pane to javafx.graphics, javafx.fxml;
	opens c5_tile_pane to javafx.graphics, javafx.fxml;
	opens c6_grid_pane to javafx.graphics, javafx.fxml;
	opens c7_stack_pane to javafx.graphics, javafx.fxml;
	
}