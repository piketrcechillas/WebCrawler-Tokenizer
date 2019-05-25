import java.util.Scanner;
import java.sql.Statement;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class query {
	public static DB db = new DB();	
	public static void main(Scanner scan) throws SQLException {
		System.out.println("Bạn muốn tìm hiểu gì?");
		System.out.println("1. nếu muốn biết số từ vựng được dùng nhiều nhất");
		System.out.println("2. nếu muốn biết từ vựng được dùng nhiều nhất theo thời gian");
		System.out.println("3. nếu muốn biết từ vựng được dùng nhiều nhất theo loại từ");
		System.out.println("4. Đóng!");
		int i = 0;

		
		do {
			System.out.println("Nhập số như trên thôi bạn ơi");
	        try {
	        	i = scan.nextInt();
	        }
	        catch (InputMismatchException e) {
	        	scan.next();
	        } } while (i < 1 || i > 4);  
	        
	        
			do {	int n = 0;
	        	switch (i)
	        	{
	        	
	      
	        		
	        	case 1: {
	        			int max = 0;
	        			int desc = 0;
	        			do {
	        				System.out.println("Nhập giới hạn số từ: ");
        		        try {
        		        	max = scan.nextInt();
        		        }
        		        catch (InputMismatchException e) {
        		        	scan.next();
        		        } } while (max < 1);  
	        		
	        			Statement stmt = db.conn.createStatement();
	        			String SQL = "SELECT * FROM `Crawler`.`Segment` ORDER BY count DESC LIMIT " + max + ";";
	        			ResultSet rs = stmt.executeQuery(SQL);
	        			// tìm từ tìm xong viết ra file kết quả
	        			while (rs.next()) {
	        					desc = desc + 1;
	        					String result = rs.getString("word") + " - " + rs.getString("count") + " lần";
	        					System.out.println("Từ được dùng nhiều thứ " + desc + " là: " + result); }
	        				
	        			do {
	        				System.out.println("Bạn có muốn tìm hiểu thêm không? (1 = có, 2 = không)");
	        		        try {
	        		        	n = scan.nextInt();
	        		        }
	        		        catch (InputMismatchException e) {
	        		        	scan.next();
	        		        } } while (n < 1 || n > 2);  
	        			
	        			if(n == 1) {
	        				System.out.println("Bạn muốn tìm hiểu gì?");
	        				System.out.println("1. nếu muốn biết số từ vựng được dùng nhiều nhất");
	        				System.out.println("2. nếu muốn biết từ vựng được dùng nhiều nhất theo thời gian");
	        				System.out.println("3. nếu muốn biết từ vựng được dùng nhiều nhất theo loại từ");
	        				System.out.println("4. Đóng!");
	        				do {
	        					System.out.println("Nhập số như trên thôi bạn ơi");
	        			        try {
	        			        	i = scan.nextInt();
	        			        }
	        			        catch (InputMismatchException e) {
	        			        	scan.next();
	        			        } } while (i < 1 || i > 4); 
	        				break;
	        	
	        				}
	        			
	        			else {
	        				i = 4;
	        		
	        				
	        				};
	        			
	        			
	        	}
	        	
	        	
	        	case 2: {
	        			int j = -1;
	        			int k = -1;
	        			int max = 0;
	        			int desc = 0;
	        			do {
	        				System.out.println("Nhập tháng: (0 nếu muốn tìm theo năm)");
	        	        try {
	        	        	j = scan.nextInt();
	        	        }
	        	        catch (InputMismatchException e) {
	        	        	scan.next();
	        	        } } while (i < 0 || i > 12);  
	        			
	        			do {
	        				System.out.println("Nhập năm:");
	        	        try {
	        	        	k = scan.nextInt();
	        	        }
	        	        catch (InputMismatchException e) {
	        	        	scan.next();
	        	        } } while (i < 0 || i > 2020);  
	        			
	        			do {
	        				System.out.println("Nhập giới hạn số từ: ");
        		        try {
        		        	max = scan.nextInt();
        		        }
        		        catch (InputMismatchException e) {
        		        	scan.next();
        		        } } while (max < 1);  
	    
	        			System.out.println("Tháng " + j + " Năm " + k);
	        			
	        			if ( j == 0 ) {
	        				Statement stmt = db.conn.createStatement();
	        				String SQL = "SELECT * FROM `crawler`.`analdata` WHERE YEAR(postdate) = "  + k + " ORDER BY countword DESC LIMIT " + max + ";";
	        				ResultSet rs = stmt.executeQuery(SQL);
	        				// tìm từng từ 1. tìm xong viết ra file kết quả
	        				while (rs.next()) {
	        					desc = desc + 1;
	        					String result = rs.getString("word") + " -  " + rs.getString("countword") + " lần " + " (" + rs.getDate("postdate") + ")";
	        					System.out.println("Từ được dùng nhiều thứ " + desc + " là: " + result); } }
		            	
	        			else {
	        				Statement stmt = db.conn.createStatement();
	        				String SQL = "SELECT * FROM `crawler`.`analdata` WHERE YEAR(postdate) =" + k + " AND MONTH(postdate) =" + j + " ORDER BY countword DESC LIMIT 1;";
	        				ResultSet rs = stmt.executeQuery(SQL);
	        				// tìm từng từ 1. tìm xong viết ra file kết quả
	        				while (rs.next()) {
	        					desc = desc + 1;
	        					String result = rs.getString("word") + " - " + rs.getString("countword") + " lần ";
	        					System.out.println("Từ được dùng nhiều thứ " + desc + " là: " + result);}} 
	        			
	        			
	        			do {
	        				System.out.println("Bạn có muốn tìm hiểu thêm không? (1 = có, 2 = không)");
	        		        try {
	        		        	n = scan.nextInt();
	        		        }
	        		        catch (InputMismatchException e) {
	        		        	scan.next();
	        		        } } while (n < 1 || n > 2);  
	        			
	        			if(n == 1) {
	        				System.out.println("Bạn muốn tìm hiểu gì?");
	        				System.out.println("1. nếu muốn biết số từ vựng được dùng nhiều nhất");
	        				System.out.println("2. nếu muốn biết từ vựng được dùng nhiều nhất theo thời gian");
	        				System.out.println("3. nếu muốn biết từ vựng được dùng nhiều nhất theo loại từ");
	        				System.out.println("4. Đóng!");
	        				do {
	        					System.out.println("Nhập số như trên thôi bạn ơi");
	        			        try {
	        			        	i = scan.nextInt();
	        			        }
	        			        catch (InputMismatchException e) {
	        			        	scan.next();
	        			        } } while (i < 1 || i > 4); 
	        				break;
	        			}
	        			
	        			else {
	        				i = 4;
	        				
	        			};}
	        	
	        	
	        	
	        	
	        	
	        	
	        	case 3: {int m = 0;
	        			int max = 0;
	        			int desc = 0;
	        			
	        			System.out.println("1. Danh từ");
	        			System.out.println("2. Động từ");
	        			System.out.println("3. Tính từ");
	        			System.out.println("4. Từ tượng thanh");
	        			System.out.println("5. Đại từ/từ chỉ thời gian");
	        			System.out.println("6. Cụm từ");
	        			
	        			do {
	        				System.out.println("Hãy chọn loại từ bạn muốn xem:");
	        	        try {
	        	        	m = scan.nextInt();
	        	        }
	        	        catch (InputMismatchException e) {
	        	        	scan.next();
	        	        } } while (m < 1 || m > 6); 
	        			
	        			do {
	        				System.out.println("Nhập giới hạn số từ: ");
        		        try {
        		        	max = scan.nextInt();
        		        }
        		        catch (InputMismatchException e) {
        		        	scan.next();
        		        } } while (max < 1);  
	        			
	        			String wordType = null;
	        			if(m == 1) {wordType = "noun";}
		                if(m == 2) {wordType = "verb";}
		                if(m == 3) {wordType = "adjective";}
		                if(m == 4) {wordType = "onomatopoeia";}
		                if(m == 5) {wordType = "pronoun";}
		                if(m == 6) {wordType = "phrase";}
	        			
		                System.out.println("Loại từ: " + wordType);
		                
	        			Statement stmt = db.conn.createStatement();
	        			String SQL = "SELECT * FROM crawler.segment WHERE word IN (SELECT words FROM crawler.dictionary WHERE " + wordType + " = 1 ) ORDER BY count DESC LIMIT " + max + ";";
	        			ResultSet rs = stmt.executeQuery(SQL);
	        			// tìm từng từ 1. tìm xong viết ra file kết quả
	        			while (rs.next()) {
	        				desc = desc + 1;
	        				String result = rs.getString("word") + " - " + rs.getString("count") + " lần ";
	        				System.out.println("Từ được dùng nhiều thứ " + desc + " là: " + result);}
	        			
	        			do {
	        				System.out.println("Bạn có muốn tìm hiểu thêm không? (1 = có, 2 = không)");
	        		        try {
	        		        	n = scan.nextInt();
	        		        }
	        		        catch (InputMismatchException e) {
	        		        	scan.next();
	        		        } } while (n < 1 || n > 2);  
	        			
	        			if(n == 1) {
	        				System.out.println("Bạn muốn tìm hiểu gì?");
	        				System.out.println("1. nếu muốn biết số từ vựng được dùng nhiều nhất");
	        				System.out.println("2. nếu muốn biết từ vựng được dùng nhiều nhất theo thời gian");
	        				System.out.println("3. nếu muốn biết từ vựng được dùng nhiều nhất theo loại từ");
	        				System.out.println("4. Đóng!");
	        				do {
	        					System.out.println("Nhập số như trên thôi bạn ơi");
	        			        try {
	        			        	i = scan.nextInt();
	        			        }
	        			        catch (InputMismatchException e) {
	        			        	scan.next();
	        			        } } while (i < 1 || i > 4);  
	        				break;
	     
	        			}
	        			
	        			else {
	        				i = 4;
	        				
	        			};}  
		            
	        	case 4: {i = 4;
	        	}  
		            

	        	}
	      
	      		}
	while (i != 4);
				
		System.out.println("Cảm ơn bạn đã dùng chương trình");
;} }
	


