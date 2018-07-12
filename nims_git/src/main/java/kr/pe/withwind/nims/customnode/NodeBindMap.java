package kr.pe.withwind.nims.customnode;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import kr.pe.withwind.nims.domain.CommonCode;


/**
 * 자체데이터 (Map)과 Node 데이터를 관리한다.
 * 자체데이터가 바뀔때 해당 하는 Node가 있을 경우 Node의 값도 같이 바꿔준다.
 * @author smpark
 *
 */
public class NodeBindMap extends HashMap<String, String>{
	
	private static final long serialVersionUID = 2048592534103018195L;
	private HashMap<String,Node> nodeMap = new HashMap<String,javafx.scene.Node>();

	public NodeBindMap() {
		super();
	}

	public NodeBindMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public NodeBindMap(int initialCapacity) {
		super(initialCapacity);
	}

	public NodeBindMap(Map<? extends String, ? extends String> m) {
		super(m);
	}

	public void addNode(String key,Node node) {
		nodeMap.put(key, node);
	}

	/**
	 * 데이터 map의 값을 바꾸거나 추가해주고 node map에도 같은 키를 가진 것이 있으면 해당 node의 표현 값을 바꿔준다.
	 * @param key map 데이터 키와 node 데이터 키로 활용된다.
	 * @param value 바꿔줄값
	 */
	public void setValue(String key, String value) {
		super.put(key, value);
		Node node = nodeMap.get(key);
		if (node == null) return;
		
		if (node instanceof TextField) {
			((TextField)node).setText(value);
		}else if (node instanceof DatePicker) {
			LocalDate ld = LocalDate.now();
			if (value.length() == 8) {
				ld = LocalDate.parse(value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6));
			}
			((DatePicker)node).setValue(ld);
		}else if (node instanceof CustomTextField) {
			((CustomTextField)node).setText(value);
		}else if (node instanceof ComboBox) {
			
			List<CommonCode> objList = ((ComboBox) node).getItems();
			for (int i=0; i < objList.size(); i++) {
				if (value.equals(objList.get(i).getCode())) {
					((ComboBox) node).getSelectionModel().select(i);
				}
			}
		}
	}
//	
//	public static void main(String[] arg) {
//		String test = "20180604";
//		logger.debug(test.substring(0, 4) + "-" + test.substring(4, 6) + "-" + test.substring(6));
//	}
}
