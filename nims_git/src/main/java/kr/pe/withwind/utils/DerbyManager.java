package kr.pe.withwind.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.pe.withwind.nims.domain.XlsMapping;

import org.apache.derby.jdbc.ClientConnectionPoolDataSource;
import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DerbyManager {
	
	private static final Logger logger = LogManager.getLogger(DerbyManager.class);
	
	private static final String dbPath = "nimsDB";
	
	private static DerbyManager derby;
	
	private SqlSessionFactory ssf;
//	EmbeddedConnectionPoolDataSource ds = new EmbeddedConnectionPoolDataSource();
//	ClientConnectionPoolDataSource ds;
	
	private DerbyManager(){
//		ds = new EmbeddedConnectionPoolDataSource();
//		ds.setDatabaseName(dbPath);
//		ds.setCreateDatabase("create");
		
//		ds = new ClientConnectionPoolDataSource();
//		ds.setDatabaseName("C:/dev/oxygen_workspace/withwind_nims/nimsDB");
		
		try {
			ssf = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("config/mybatis/mybatis-config.xml"));
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
	public static DerbyManager getInstance(){
		if (derby == null) derby = new DerbyManager();
		return derby;
	}
	
	public SqlSession getSqlSession() throws SQLException, IOException{
//		Connection conn = ds.getConnection();
//		conn.setAutoCommit(false);
		return ssf.openSession(false);
	}
	
	public int update(String schem, Object param) throws DerbyException {
		int reValue = 0;
		
		SqlSession session = null;
		
		try {
			session = getSqlSession();
			reValue = session.update(schem, param);
			session.commit();
		}catch(Exception e) {
			if (session != null) session.rollback();
			throw new DerbyException(e);
		}finally {
			if (session != null) session.close();
		}
		
		return reValue;
	}
	
	public int insert(String schem, Object param) throws DerbyException {
		int reValue = 0;
		
		SqlSession session = null;
		
		try {
			session = getSqlSession();
			reValue = session.insert(schem, param);
			session.commit();
		}catch(Exception e) {
			if (session != null) session.rollback();
			throw new DerbyException(e);
		}finally {
			if (session != null) session.close();
		}
		
		return reValue;
	}
	
	public int delete(String schem, Object param) throws DerbyException {
		int reValue = 0;
		
		SqlSession session = null;
		
		try {
			session = getSqlSession();
			reValue = session.delete(schem, param);
			session.commit();
		}catch(Exception e) {
			if (session != null) session.rollback();
			throw new DerbyException(e);
		}finally {
			if (session != null) session.close();
		}
		
		return reValue;
	}
	
	public List<?> list(String schem, Object param) throws DerbyException{
		SqlSession session = null;
		try {
			session = getSqlSession();
			return session.selectList(schem, param);
		}catch(Exception e) {
			throw new DerbyException(e);
		}finally {
			if (session != null) session.close();
		}
	}
	
	public <T> T listOne(String schem, Object param) throws DerbyException {
		
		SqlSession session = null;
		try {
			session = getSqlSession();
			return session.selectOne(schem, param);
		}catch(Exception e) {
			throw new DerbyException(e);
		}finally {
			if (session != null) session.close();
		}
	}
	
	public static void main(String[] args) throws SQLException, IOException {
        
		SqlSession session = DerbyManager.getInstance().getSqlSession();
	    List<XlsMapping> list = session.selectList("XlsMapping.getXlsMapping");
	    
	    for (XlsMapping data : list){
	    	logger.debug(data);
	    }
        session.close();
    }
}
