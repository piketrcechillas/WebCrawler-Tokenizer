import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	 
	public Connection conn = null;
 	private String YOUR_DB_USERNAME = null;
	private String YOUR_PASSWORD = null;
	public DB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/Crawler";
			conn = DriverManager.getConnection(url, YOUR_DB_USERNAME, YOUR_PASSWORD);
			System.out.println("Database Connected");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
 
	public ResultSet runSql(String sql) throws SQLException {
		Statement stm = conn.createStatement();
		return stm.executeQuery(sql);
	}
 
	public boolean runSql2(String sql) throws SQLException {
		Statement stm = conn.createStatement();
		return stm.execute(sql);
	}
 
	@Override
	protected void finalize() throws Throwable {
		if (conn != null || !conn.isClosed()) {
			conn.close();
		}
	}
}
