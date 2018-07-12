package kr.pe.withwind.nims.customnode;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import kr.pe.withwind.nims.customnode.ButtonCell.ButtonCellListener;

public class ButtonCellFactory<T> implements Callback<TableColumn<T, Boolean>, TableCell<T, Boolean>> {

	private BtnType btnType;
	private String label;
	private ButtonCellListener listener;
	
	public ButtonCellFactory (BtnType btnType, String label, ButtonCellListener listener) {
		this.btnType = btnType;
		this.label = label;
		this.listener = listener;
	}
	@Override
	public TableCell<T, Boolean> call(TableColumn<T, Boolean> param) {
		return new ButtonCell(btnType, label, listener);
	}

}
