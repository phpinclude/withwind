package kr.pe.withwind.nims.customnode;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import kr.pe.withwind.nims.view.BindNodeEventHandler;

public class CustomTextField extends AnchorPane{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private String valueKey;
	private TextField textField;
	private String[] bindKey;
	
	public ObjectProperty<Object> valueProperty() { return value; }
    private ObjectProperty<Object> value = new SimpleObjectProperty<Object>(this, "value");
	
    public CustomTextField() {
    	super();
    }
    
	public CustomTextField(String valueKey) {
		this.valueKey = valueKey;
		textField = new TextField();
		setVisible(false);
	}
	
	public void setRightNode(Node node) {
		
		getChildren().addAll(textField,node);
		setRightAnchor(node, 0d);
		setLeftAnchor(textField, 0d);
		setVisible(false);
		
		Platform.runLater(()->{
			setRightAnchor(textField, ((Region) node).getWidth() + 5d);
			setVisible(true);
		});
	}
	
	public void setLeftNode(Node node) {
		
		getChildren().addAll(node,textField);
		setLeftAnchor(node, 0d);
		setRightAnchor(textField, 0d);
		setVisible(false);
		
		Platform.runLater(()->{
			setLeftAnchor(textField, ((Region) node).getWidth() + 5d);
			setVisible(true);
		});
	}
	
	public void setText(String text) {
		textField.setText(text);
	}
	
	public String getText() {
		return textField.getText();
	}

	public void setUserData(Object value, String mapKey) {
		textField.setUserData(value);
		textField.textProperty().addListener((observable, oldValue, newValue ) -> {
			NodeBindMap userData = (NodeBindMap) textField.getUserData();
			userData.put(mapKey, newValue);
		});
	}

	public void setEnterHandler(BindNodeEventHandler bindNodeHandler) {
		
		logger.debug("setEnterHandler bind!!");
		textField.setOnKeyPressed((event)->{
			if (event.getCode() == KeyCode.ENTER) {
				
				logger.debug("key on enter!");
				
				if (bindNodeHandler != null) {
					
					logger.debug("textField.getUserData() class name : "+ textField.getUserData().getClass().getSimpleName());
					
					if (textField.getUserData() != null && textField.getUserData() instanceof NodeBindMap) {
						NodeBindMap bindMap = (NodeBindMap) textField.getUserData();
						bindNodeHandler.bindNodeHandle(bindMap, valueKey);
					}
				}
			}
		});
	}
}
