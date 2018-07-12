package kr.pe.withwind.nims.view;

import javafx.stage.Stage;

public interface PopupController {

	public void setSearchText(String searchText);
	public <T> T getData();
	public void setDialogStage(Stage dialogStage);
	public void setData(Object obj);
}
