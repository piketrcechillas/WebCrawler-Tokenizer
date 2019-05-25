import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
 
public class Main {
	
	static int i = 0;
	static int result = 0;
	static int URLnum = 50;
	public static DB db = new DB();
 
	public static void main(Scanner scan) throws SQLException, IOException {
		
		 do {
			System.out.println("Nhập số trang cần Input");
	        try {
	        	URLnum = scan.nextInt();
	        }
	        catch (InputMismatchException e) {
	        	scan.next();
	        } } while (URLnum < 0 || URLnum > 100000); 
		
		db.runSql2("TRUNCATE TABLE Record;");
		String URL = null;
		db.runSql2("INSERT INTO  Crawler.Record (URL) VALUES ('https://vnexpress.net/thoi-su/ong-nguyen-thien-nhan-tong-bi-thu-nguyen-phu-trong-se-som-xuat-hien-lam-viec-3920125.html');");
		//Scanner URL = new Scanner(System.in);
		//System.out.println("Enter the URL you want to crawl: ");
		do
		{	i = i + 1;
			String sql = "select * from Record where RecordID = "+ i + ";";
			ResultSet rs = db.runSql(sql);
			if(rs.next()){
				URL = rs.getString("URL");
				crawlPage(URL);}
				System.gc();} while (result < URLnum);
		//URL.close();
		System.out.println("Crawl Completed!");

	}
	
	public static void crawlPage(String URL) throws SQLException, IOException{

			 try {				

				 	Document doc = Jsoup.connect(URL).get(); //kết nối vào URL
				 	Elements hyper = doc.select("a[href]");  //lấy tất cả các link
				 	for(Element link: hyper){
				 		if(!link.attr("href").contains("vnexpress.net") || link.attr("href").contains("video") || link.attr("href").contains("startup") || link.attr("href").contains("e.vnexpress")|| link.attr("href").contains("shop")|| link.attr("href").contains("facebook") ||  link.attr("href").contains("raovat") ||  link.attr("href").contains("cuoi") || link.attr("href").contains("pay") || link.attr("href").contains("tag")) {}
				 		else  {
				
					String sql = "INSERT IGNORE INTO  `Crawler`.`Record` " + "(`URL`) VALUES " + "(?); ";  //insert sql
					PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, link.attr("abs:href")); 
					stmt.execute();
					
					 String SQL = "SELECT * FROM `Crawler`.`Record` ORDER BY RecordID DESC LIMIT 1;"; //đếm số URL hiện có
			            ResultSet rs = stmt.executeQuery(SQL);
			           if (rs.next()) {																
			        	   if(result < URLnum) { 
			        		   result = rs.getInt("RecordID"); 
			        		   System.out.println(result);}
			        	   else { break;} }
					
					System.out.println(link.attr("abs:href"));  // in ra URL

		
					
					} }
		} 
			catch (Exception | StackOverflowError e) { //bắt các loại lỗi
				System.out.println("URL unavailable");

			}
		
			
		
	
}	 
	
	public static int getURLnum() {
		return URLnum;
	}
}