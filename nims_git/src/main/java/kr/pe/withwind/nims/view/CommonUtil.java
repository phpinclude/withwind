package kr.pe.withwind.nims.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import kr.pe.withwind.nims.customnode.CustomTextField;
import kr.pe.withwind.nims.customnode.NodeBindMap;

public class CommonUtil {
	
	@SuppressWarnings("rawtypes")
	public static void copySelectionToClipboard(final TableView<?> table) {
	    final Set<Integer> rows = new TreeSet<>();
	    for (final TablePosition tablePosition : table.getSelectionModel().getSelectedCells()) {
	        rows.add(tablePosition.getRow());
	    }
	    final StringBuilder strb = new StringBuilder();
	    boolean firstRow = true;
	    for (final Integer row : rows) {
	        if (!firstRow) {
	            strb.append('\n');
	        }
	        firstRow = false;
	        boolean firstCol = true;
	        for (final TableColumn<?, ?> column : table.getColumns()) {
	            if (!firstCol) {
	                strb.append('\t');
	            }
	            firstCol = false;
	            final Object cellData = column.getCellData(row);
	            strb.append(cellData == null ? "" : cellData.toString());
	        }
	    }
	    final ClipboardContent clipboardContent = new ClipboardContent();
	    clipboardContent.putString(strb.toString());
	    Clipboard.getSystemClipboard().setContent(clipboardContent);
	}
}
