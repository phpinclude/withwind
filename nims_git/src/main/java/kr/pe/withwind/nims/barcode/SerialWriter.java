package kr.pe.withwind.nims.barcode;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SerialWriter implements Runnable {
	private final Logger logger = LogManager.getLogger(this.getClass());
	
    OutputStream out;

    public SerialWriter(OutputStream out) {
        this.out = out;
    }

    public void run() {
        try {
            int c = 0;
            while ((c = System.in.read()) > -1) {
                this.out.write(c);
            }
        } catch (IOException e) {
            logger.error(this.getClass().getSimpleName() + "오류!!", e);
        }
    }
}