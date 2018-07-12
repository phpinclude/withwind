package kr.pe.withwind.nims.customnode;

import org.apache.poi.ss.formula.functions.T;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import kr.pe.withwind.nims.domain.CommonCode;

public class ButtonCell<S,T> extends TableCell<S, T> {
	
	private BtnType btnType;
	private Button cellButton;
	private ButtonCellListener listener;

    public ButtonCell(BtnType btnType, String label, ButtonCellListener listener){
    	this.btnType = btnType;
    	this.cellButton = new Button(label);
    	this.listener = listener;
    	
    	cellButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				listener.doClick(getTableView().getItems().get(getIndex()), getIndex(), btnType);
			}
		});
    }

	//Display button if the row is not empty
    @Override
    protected void updateItem(T t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            setGraphic(cellButton);
        }
    }
    
    public interface ButtonCellListener{
    	public void doClick(Object obj, int idx, BtnType id);
    }
}
