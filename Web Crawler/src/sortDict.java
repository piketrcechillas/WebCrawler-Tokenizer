import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.SQLException;

public class sortDict
{   
	public static DB db = new DB();	
    public static int main() throws SQLException
    {   
        BufferedReader reader = null; 
        db.runSql2("TRUNCATE TABLE dictionary");
        
         
        BufferedWriter writer = null;
        BufferedWriter writer2 = null;
                 
        ArrayList<String> lines = new ArrayList<String>();
        
        int max = 0;
         
        try
        {
             
            reader = new BufferedReader(new FileReader("D:\\a.txt"));

             
            String currentLine = reader.readLine();
          
            
             
            while (currentLine != null) 
            {
                lines.add(currentLine);
                 
                currentLine = reader.readLine();
            }
             

            Collections.sort(lines);
             

            writer = new BufferedWriter(new FileWriter("D:\\sortedDict.txt"));
            writer2 = new BufferedWriter(new FileWriter("D:\\sortedDictWithWordType.txt"));
             

            for (String line : lines)
            {
            	
            	String newLine1 = line.replaceAll(" .+$", "").replace("_", " ").toLowerCase();
            	String newLine = line.replace("_", " ").toLowerCase();
            	
       
            	boolean noun = false;
            	boolean verb = false;
            	boolean adjective = false;
            	boolean onomatopoeia = false;
            	boolean pronoun = false;
            	boolean phrase = false;
            	
            	
            	
            	if(newLine.contains("{")) {
            	
            	String wordType = newLine.substring(newLine.indexOf("{")+1,newLine.indexOf("}"));
            	
            	  if(wordType.contains("n") || wordType.contains("np") || wordType.contains("nu"))
            		  noun = true;
            	  if(wordType.contains("v"))
            		  verb = true;
            	  if(wordType.contains("a"))
            		  adjective = true; 
            	  if(wordType.contains("o"))
            		  onomatopoeia = true;
            	  if(wordType.contains("p"))
            		  pronoun = true;
            	  if(wordType.contains("x"))
            		  phrase = true;}
            	
            	
            	
            	db.runSql2("CALL add_word('" + newLine1 + "', " + noun + ", " + verb +", " + adjective + ", " + onomatopoeia + ", " + pronoun + ", " + phrase + ")");
            	System.out.println(newLine1);
            	
            	
            	if(newLine1.length()>max)
            		max = newLine1.length();
            	
            	if(newLine1.length() > 1)
                writer.write(newLine1);
            	writer2.write(newLine);
                
                writer.newLine();
                writer2.newLine();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {

            try
            {
                if (reader != null)
                {
                    reader.close();
                }
                 
                if(writer != null)
                {
                    writer.close();
                }
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        return max;
    }   
}