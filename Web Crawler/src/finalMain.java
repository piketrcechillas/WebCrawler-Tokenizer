import java.text.ParseException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;



public class finalMain {

	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, ParseException {
		
		Scanner scan = new Scanner(System.in);
		
		Main.main(scan);
		Extract.main();
		//sortDict.main();
		//Test.main();
		wordSegmentation a = new wordSegmentation();
		a.main();
		query.main(scan);
		
		scan.close();
	} 

}
