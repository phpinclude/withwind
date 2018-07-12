package kr.pe.withwind.nims.barcode;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.domain.BarcodeInfo;
import kr.pe.withwind.nims.utils.RFIDRead;

public class ReadForComPort {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private ArrayList<BarcodeListener> listeners;
	
	private static ReadForComPort readForComPort;
	
	private ReadForComPort() throws NimsException {
		listeners = new ArrayList<BarcodeListener>();
		
		Serial serial = new Serial(this);
		
		for (int i=1; i < 10; i++) {
			try {
	            serial.connect("COM" + i);
	            break;
	        } catch (Exception e) {
	        	if (i==9) throw new NimsException("바코드 리더기를 확인 해주세요.");
	        }
		}
	}

	public static ReadForComPort getInstance() throws NimsException {
		if (readForComPort == null) readForComPort = new ReadForComPort();
		
		return readForComPort;
	}
		
	public void addBarcodeListener(BarcodeListener listener) {
		listeners.add(listener);
	}
	
	public void removeBarcodeListener(BarcodeListener listener) {
		listeners.remove(listener);
	}
	
	public void removeAllListener() {
		listeners.removeAll(listeners);
	}

	protected void doExcute(byte[] data) {
		
		String rawStr = new String(data);
		
		BarcodeInfo barcodeInfo = null;
		
		if ((char)data[0] == '3' && (char)data[1] == '0') {
			barcodeInfo = RFIDRead.getBarcodeInfoByRFID(new String(rawStr));
		}else {
			barcodeInfo = RFIDRead.getBarcodeInfoByBarcode(data);
		}
		
		logger.debug(barcodeInfo);
		
		for (BarcodeListener listener : listeners) {
			listener.listenBarcode(barcodeInfo);
		}
	}
    
    public interface BarcodeListener{
    	public void listenBarcode(BarcodeInfo barcodeInfo);
    }
}