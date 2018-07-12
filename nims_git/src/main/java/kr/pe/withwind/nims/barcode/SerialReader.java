package kr.pe.withwind.nims.barcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SerialReader implements Runnable {
	private final Logger logger = LogManager.getLogger(this.getClass());
	
    InputStream in;
    ReadForComPort mainClass;

    public SerialReader(InputStream in, ReadForComPort mainClass) {
        this.in = in;
        this.mainClass = mainClass;
	}

	public void run() {
        byte[] buffer = new byte[1024];
        int len = -1;
        
        StringBuffer sb = new StringBuffer();
        
        ArrayList<Byte> tmpByte = new ArrayList<Byte>();
        
        try {
            while ((len = this.in.read(buffer)) > -1) {
            	
                if (len > 0) {
                	for (int i=0; i < len; i++) {
                		
                		if (buffer[i] == 13 || buffer[i] == 10) {
//                			logger.debug("");
                			byte[] tmp = new byte[tmpByte.size()];
                			for (int j=0; j < tmp.length; j++) {
                				tmp[j] = tmpByte.get(j);
                			}
                			
                			mainClass.doExcute(tmp);
                			tmpByte = new ArrayList<Byte>();
                			break;
                		}else {
//                			System.out.print("["+buffer[i]+"]");
                    		tmpByte.add(new Byte(buffer[i]));
                		}
                	}
                }
            }
        } catch (IOException e) {
            logger.error(this.getClass().getSimpleName() + "오류!!", e);
        }
    }
}