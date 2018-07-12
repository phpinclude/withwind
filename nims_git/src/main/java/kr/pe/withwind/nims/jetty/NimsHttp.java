package kr.pe.withwind.nims.jetty;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class NimsHttp extends Thread {
	
	public static Logger logger = LogManager.getLogger(NimsHttp.class);

	private int port;
	private static NimsHttp nimsHttp;
	private static final String CONFIG_LOCATION_PACKAGE = "config.spring";

	private NimsHttp(int serverPort) {
		this.port = serverPort;
	}

	public static NimsHttp getInstance(int serverPort) {
		if (nimsHttp == null)
			nimsHttp = new NimsHttp(serverPort);

		return nimsHttp;
	}

	public boolean startHttp() {
		if (!isAlive())
			this.start();
		logger.debug("Nims http started!!");
		return true;
	}
	
	public void stopHttp() {
		if (!isAlive()) return;
		try {nimsHttp.interrupt();}catch(Exception e) {}
		nimsHttp = null;
	}

	@Override
	public void run() {
	
		// 서버 포트 세팅
		Server server = new Server(port);
		
		// 커넥터 셋팅
		// jetty9 부터 커넥터 생성 방식이 바뀜 링크 참조
		// http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tree/examples/embedded/src/main/java/org/eclipse/jetty/embedded/ManyConnectors.java
		
		
		
		// WebAppcontext 셋팅
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setResourceBase("webapp");
		webAppContext.setContextPath("/");
//		 Including the JSTL jars for the webapp.
		webAppContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*jstl.*\\.jar$");
	
//		 Enabling the Annotation based configuration
		org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");
        
     // Spring
//        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS); // SESSIONS requerido para JSP 
//        servletContextHandler.setErrorHandler(null);
//
//        servletContextHandler.setResourceBase("webapp");5
//        servletContextHandler.setContextPath("/");
//        
//        WebApplicationContext servletContext = getWebApplicationContext();
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletContext);
//        ServletHolder springServletHolder = new ServletHolder("mvc-dispatcher", dispatcherServlet);
//        servletContextHandler.addServlet(springServletHolder, "/*.do");
//        servletContextHandler.addEventListener(new ContextLoaderListener(servletContext));
        
        // Setting the handler and starting the Server
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {webAppContext, new DefaultHandler()});
       
		server.setHandler(handlers);

		try {
			server.start();
			server.join();
		}catch (InterruptedException e) {
			logger.debug("http server interrupt stop!!");
		}catch (Throwable t) {
			t.printStackTrace();
		}finally {
			try {
				server.stop();
			} catch (Exception e) {
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			}
		}
	}
	
	private static WebApplicationContext getWebApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION_PACKAGE);
        return context;
    }
}