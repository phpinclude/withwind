package kr.pe.withwind.nims.utils;

import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CPTest {
	
	private static final Logger logger = LogManager.getLogger(CPTest.class);

    private static Iterator list(ClassLoader CL)
        throws NoSuchFieldException, SecurityException,
        IllegalArgumentException, IllegalAccessException {
        Class CL_class = CL.getClass();
        while (CL_class != java.lang.ClassLoader.class) {
            CL_class = CL_class.getSuperclass();
        }
        java.lang.reflect.Field ClassLoader_classes_field = CL_class
                .getDeclaredField("classes");
        ClassLoader_classes_field.setAccessible(true);
        Vector classes = (Vector) ClassLoader_classes_field.get(CL);
        return classes.iterator();
    }

    public static void main(String args[]) throws Exception {
        ClassLoader myCL = Thread.currentThread().getContextClassLoader();
        while (myCL != null) {
            logger.debug("ClassLoader: " + myCL);
            for (Iterator iter = list(myCL); iter.hasNext();) {
                logger.debug("\t" + iter.next());
            }
            myCL = myCL.getParent();
        }
    }

}
