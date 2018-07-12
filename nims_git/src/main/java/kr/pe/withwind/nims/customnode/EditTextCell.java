package kr.pe.withwind.nims.customnode;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class EditTextCell<T> extends TableCell<T, String> {

	private TextField textField;

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			createTextField();
			setText(null);
			setGraphic(textField);
			textField.requestFocus();
			textField.selectAll();
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText((String)getItem());
		setGraphic(null);
	}

	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		
		if (empty) {
			setText(null);
			setGraphic(null);
		}else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getString());
				}
				setText(null);
				setGraphic(textField);
			}else{
				setText(getString());
				setGraphic(null);
			}
		}
	}

	private void createTextField() {
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if (!newValue) {
					commitEdit(textField.getText());
					setItem(textField.getText());
				}
			}
		});
		
		textField.setOnKeyPressed(event-> {
			if (event.getCode() == KeyCode.ENTER) {
				setItem(textField.getText());
				commitEdit(textField.getText());
			}
		});
	}
	
	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}
