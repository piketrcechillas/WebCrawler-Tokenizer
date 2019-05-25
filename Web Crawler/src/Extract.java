import java.io.IOException;
import java.util.Date;
import java.text.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class Extract {
	public static DB db = new DB();
	

	public static void main() throws SQLException, IOException {
			
		db.runSql2("TRUNCATE TABLE Content;");
		//from i = 1 to 1000
		int num = 0;
		String sql1 = "SELECT * FROM `Crawler`.`Record` ORDER BY RecordID DESC LIMIT 1;";
		PreparedStatement stmt1 = db.conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs1 = stmt1.executeQuery(sql1);
        if (rs1.next()) {
     		   num = rs1.getInt("RecordID");}
		
		
		for (int i = 0; i < num ; i++)
		{	     int j = i+1;
		
		
			Path origin = Paths.get("D:/Crawler/", "URLno" + j + ".txt");
			Path date = Paths.get("D:/Date/", "URLno" + j + "Date.txt");
			Files.deleteIfExists(origin);
			Files.deleteIfExists(date);
			
			ResultSet rs = db.runSql("SELECT * FROM Record ORDER BY RecordID ASC LIMIT " + Integer.toString(i) + ",1" );
			while (rs.next()) 
			try {
			{
				
			 System.out.println("URL so " + rs.getString(1));
			 String html = rs.getString(2);
		     Document document = Jsoup.connect(html).get();
		     Element body = document.body();
		     Elements paragraphs = body.select("p");
		     Elements dates = body.select("header.clearfix > span.time.left");
		     String extracted = null;
			 String realDate = dates.text();
	    	 String[] values = realDate.split("\\s*,\\s*"); 
	    	 
	    	 
	    	 try {
	    	 extracted = values[1];
	    	 System.out.println(extracted); }
	    	 catch(Exception e) {
	    		 System.out.println("No Date"); }
	    	
             
		     for (Element paragraph : paragraphs) {
		    	 
	
		    	 if(extracted != null) {
		    		 
		    		 	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    		 	Date date1 = formatter.parse(extracted);
		    		 	java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
		    		 	String sql = "INSERT INTO  `Crawler`.`Content` " + "(`Content`, `PostDay`) VALUES " + "(?, ?);";
		    		 	
		    		 	
		    		 	PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    		 	stmt.setString(1, paragraph.text());
		    		 	stmt.setDate(2, sqlDate);
		    		 	stmt.execute();}
		    	 
		    	 
		    	 else {
		    		 	String sql = "INSERT INTO  `Crawler`.`Content` " + "(`Content`) VALUES " + "(?);";
		    		 	PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    		 	stmt.setString(1, paragraph.text());
		    		 	stmt.execute();
		    	 }
		    	 
		    	 
         
				/*  */
			    	
					System.out.println(paragraph.text());
					
		         try {
		        	 if(extracted != null) {
			    		 
			    		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			    		 Date date1 = formatter.parse(extracted);
			    		 java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			    		 String b = sqlDate.toString();
			    		 String c = paragraph.text();
			    		 Files.write(date, b.getBytes(Charset.forName("UTF-8")));
			    		 Files.write(origin, c.getBytes(Charset.forName("UTF-8")),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
		        	 }
		        	 
		        	 else {
		        		 Files.write(date, "No Date".getBytes(Charset.forName("UTF-8")));
		        		 String c = paragraph.text();
		        		 Files.write(origin, c.getBytes(Charset.forName("UTF-8")),StandardOpenOption.CREATE,StandardOpenOption.APPEND);} }
		             
		         catch (IOException e) {
		             e.printStackTrace();
		         }
		     }
		         Thread.sleep(10);
			}
	         System.out.println();
	         System.out.println();
		   }
		catch (Exception e) {
			 e.printStackTrace();
		}
			}
		System.out.println("Extraction Completed");
	} }
	

		
		
		
