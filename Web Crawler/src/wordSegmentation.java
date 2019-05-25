import java.nio.file.Path;
import java.util.Date;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.nio.file.Files;
import java.io.*;
import java.nio.charset.Charset;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class wordSegmentation implements Serializable  {
	
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		int i = 1;
	public static DB db = new DB();	
 
	
	public void segmentation(String string, Trie trie, Path crawl, Date date, int max) throws IOException, ClassNotFoundException, SQLException {

	String text = "";
		

			
			if(i<string.length()) 
				
			 {
			text = string.substring(0, i);}
			
			else {
			text = "";
			}
			
			
			

			String miss = null;
			String match = trie.getMatchingPrefix(text);
			
			if(string.contains(" ")) {
				int spacePos = string.indexOf(" ");	
				miss = string.substring(0, spacePos);
			 }
			
			else {
				miss = string.substring(0, string.length());
			}
		
			
			if (match.isEmpty() )
				{if (i < max && i<string.length()) {
						i = i + 1; 
				}
	
				else {
					System.out.println();
					System.out.print("Word missing: ");
					System.out.println(miss);
					if(string.contains(" ") && string.indexOf(" ") < string.length() ) {
						int spacePos = string.indexOf(" ");		
						String del = string.substring(spacePos + 1, string.length());
						byte[] b = del.getBytes(Charset.forName("UTF-8"));
						Files.write(crawl, b); }
					
					else {	
						String del = "";
						byte[] b = del.getBytes(Charset.forName("UTF-8"));
						Files.write(crawl, b); 
					}
					
					i = 1;
					
				} } 
			
				
			
			else {
			
				if(i < max) {
					i = i + 1;
				}
				
				
				else {
				System.out.println();
				System.out.print("Found Word: ");
				ArrayList<String> wordType = new ArrayList<String>();
				String SQL2 = "SELECT * FROM `Crawler`.`dictionary` WHERE words = '" + match +"'";
				Statement stmt2 = db.conn.createStatement();
		        ResultSet rs = stmt2.executeQuery(SQL2);
		        

		            while (rs.next()) {
		                if(rs.getInt("noun") == 1) {wordType.add("Noun");}
		                if(rs.getInt("verb") == 1) {wordType.add("Verb");}
		                if(rs.getInt("adjective") == 1) {wordType.add("Adj");}
		                if(rs.getInt("onomatopoeia") == 1) {wordType.add("Ono");}
		                if(rs.getInt("pronoun") == 1) {wordType.add("Pronoun");}
		                if(rs.getInt("phrase") == 1) {wordType.add("Phrase");}

		            } 
		            
		            String types = String.join(" ", wordType);
		            System.out.println(match + "   {" + types + "}");
				
				
				
				
				String sql = "INSERT INTO  `Crawler`.`Segment` " + "(`word`) VALUES " + "(?) ON DUPLICATE KEY UPDATE count = count + 1;";
				PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, match);
				stmt.execute();
				

				
			
				if (date != null)
				db.runSql2("CALL new_procedure('" + match + "', '" + date + "')") ; //ghép từ với ngày, nếu đã có ngày đó thì + 1 từ
				else
				db.runSql2("CALL new_procedure('" + match + "', '0000-00-00')") ; // ngày = null thì để là 0000
				
				if(string.contains(" ") && match.length() + 1 < string.indexOf(" ") + 1) {
					String del = string.substring(string.indexOf(" ") + 1, string.length());
					byte[] b = del.getBytes(Charset.forName("UTF-8"));
					Files.write(crawl, b);	
					i = 1; }
				else
				{
					String del = string.substring(match.length() + 1, string.length());
					byte[] b = del.getBytes(Charset.forName("UTF-8"));
					Files.write(crawl, b);	
					i = 1; }
				}
		
			} } 
	
	
	public void main() throws IOException, ClassNotFoundException, SQLException, ParseException {
		final int max = 43;
		db.runSql2("TRUNCATE TABLE Segment;");
		db.runSql2("TRUNCATE TABLE analdata");
		for(int i = 1; i < 20; i ++) {
		
			Path crawl = Paths.get("D:/Copy/", "URLno" + i + "Copied.txt");
			Path origin = Paths.get("D:/Crawler/", "URLno" + i + ".txt");
			Path seri = Paths.get("D:/", "trie.ser");
			String regex = "(?:(?<!\\S)\\p{Punct}+)|(?:\\p{Punct}+(?!\\S))";
			String regexN = "\\d+";
			Path dates = Paths.get("D:/Date/", "URLno" + i + "Date.txt" );
			String str = new String(Files.readAllBytes(dates));
			System.out.println(str);
			Date date = null;
			if (!str.contains("No Date"))
				
			{ SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = formatter.parse(str);
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				date = sqlDate;}
			
			else
			
			{date = null;}
		
	

		
			Files.deleteIfExists(crawl);
			Files.copy(origin, crawl);
			
			String string = new String(Files.readAllBytes(crawl));
				string = string.toLowerCase();
				string = string.replace(".", " ").replaceAll(regex, "").replaceAll(regexN, "").replace("bản quyền", "");
				byte[] b = string.getBytes(Charset.forName("UTF-8"));
				Files.write(crawl, b);
				byte[] data = Files.readAllBytes(seri);
				Object deseri = Test.deserialize(data);
				Trie dict = (Trie) deseri;
		
				while (!string.isEmpty()) {
					string = new String(Files.readAllBytes(crawl));
					segmentation(string, dict, crawl, date, max);
				} }
		
			System.out.println("Analyzation Completed");
			try ( Statement stmt = db.conn.createStatement();) {
	            String SQL = "SELECT * FROM `Crawler`.`Segment` ORDER BY count DESC;";
	            ResultSet rs = stmt.executeQuery(SQL);

	            // tìm từng từ 1. tìm xong viết ra file kết quả
	            while (rs.next()) {
	            
	                String result = rs.getString("word") + ": " + rs.getString("count");
	                Path resultPath = Paths.get("D:/result/", "result.txt" );
	            	Files.deleteIfExists(resultPath);
					Files.write(resultPath,  (result + System.lineSeparator()).getBytes(Charset.forName("UTF-8")),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
	            }
	        }
	        //bắt lỗi
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
} }  
