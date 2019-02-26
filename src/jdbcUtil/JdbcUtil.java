package jdbcUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JdbcUtil {
	static String driverClass = null;
	static String url = null;
	static String username = null;
	static String password = null;
	
	static{
		Properties properties = new Properties();
		InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
		try {
			properties.load(in);
			driverClass = properties.getProperty("driverClass");
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConn(){
		
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	
	public static void realease(Connection conn, PreparedStatement ps, ResultSet rs){
		closeRs(rs);
		closeSt(ps);
		closeConn(conn);
	}
	public static void realease(Connection conn, PreparedStatement ps){
		closeSt(ps);
		closeConn(conn);
	}
	
	private static void closeRs(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rs = null;
		}
	}
	
	private static void closeSt(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ps = null;
		}
	}
	
	private static void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn = null;
		}
	}
	
}
