package kr.pe.withwind.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Mybatis Interceptor를 이용하여
 * 쿼리 수행시에 RowBound를 셋팅 할 경우 해당 쿼리를 각각의 DB에 맞도록 Pageing 쿼리로 바꾸어 준다.
 * 현재는 Oracle과 Mysql이 셋팅되어 있다.
 * @author cho
 *
 */
@Intercepts({
	 @Signature(type = StatementHandler.class, method = "prepare", args= {Connection.class, Integer.class})
})
public class MybatisInterCeptor implements Interceptor {

	private static final Logger logger = LogManager.getLogger(MybatisInterCeptor.class);
	
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final ReflectorFactory DEFAULT_REFLECTORFACTORY = new DefaultReflectorFactory();
	
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		logger.debug("intercept call!!");
		
		Connection conn = (Connection) invocation.getArgs()[0];
		String dbName = conn.getMetaData().getDatabaseProductName().toUpperCase();
		
		logger.debug("dbName : " + dbName);
		
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,DEFAULT_REFLECTORFACTORY);
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		
		RowBounds rb = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		
		if (rb == null || rb == RowBounds.DEFAULT) return invocation.proceed();
		
		logger.debug("rb.getOffset() : "  + rb.getOffset());
		logger.debug("rb.getLimit() : "  + rb.getLimit());

		if (dbName.contains("MYSQL")){
			
			StringBuilder sb = new StringBuilder(originalSql);
			sb.append(" limit ");
			sb.append(rb.getOffset());
			sb.append(',');
			sb.append(rb.getLimit());
			
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
	        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
			metaStatementHandler.setValue("delegate.boundSql.sql", sb.toString());
			
		}else if (dbName.contains("ORACLE")){
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM ( ");
			sb.append("SELECT ROWNUM AS RNUM, WT.* FROM ( ");
			sb.append(originalSql);
			sb.append(") WT WHERE ROWNUM <= " + (rb.getOffset() + rb.getLimit()));
			sb.append(") WHERE RNUM > " + rb.getOffset());
			
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
	        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
			metaStatementHandler.setValue("delegate.boundSql.sql", sb.toString());
		}
		
		return invocation.proceed();
	}
	
	

	@Override
	public Object plugin(Object target) {
		logger.debug("plugin call!! " + target.getClass().getName());
//		if (target instanceof CachingExecutor){
//			CachingExecutor obj = (CachingExecutor) target;
//		}
//		
//		if (target instanceof DefaultParameterHandler){
//			DefaultParameterHandler obj = (DefaultParameterHandler) target;
//		}
//		
//		if (target instanceof FastResultSetHandler){
//			FastResultSetHandler obj = (FastResultSetHandler) target;
//		}
//		
//		if (target instanceof RoutingStatementHandler){
//			RoutingStatementHandler obj = (RoutingStatementHandler) target;
//		}s
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		logger.debug("setProperties call!!");

	}
}
