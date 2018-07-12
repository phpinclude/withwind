package kr.pe.withwind.nims.customnode;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class ButtonCellValueFactory<T> implements Callback<TableColumn.CellDataFeatures<T, Boolean>, ObservableValue<Boolean>> {

	@Override
	public ObservableValue<Boolean> call(CellDataFeatures<T, Boolean> param) {
		return new SimpleBooleanProperty(param.getValue() != null);
	}
}
