import java.util.HashMap; 
import java.io.*;
   
class TrieNode implements Serializable { 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	public TrieNode(char ch)  { 
        value = ch; 
        children = new HashMap<>(); 
        bIsEnd = false; 
    } 
    public HashMap<Character,TrieNode> getChildren() {   return children;  } 
    public char getValue()                           {   return value;     } 
    public void setIsEnd(boolean val)                {   bIsEnd = val;     } 
    public boolean isEnd()                           {   return bIsEnd;    } 
  
    private char value; 
    private HashMap<Character,TrieNode> children; 
    private boolean bIsEnd; 
} 
  
class Trie implements Serializable{ 
	private static final long serialVersionUID = 1L;

	public Trie()   {     root = new TrieNode((char)0);       }     
    public void insert(String word)  { 
    	
        int length = word.length(); 
        TrieNode crawl = root; 
  
        for( int level = 0; level < length; level++) 
        { 
            HashMap<Character,TrieNode> child = crawl.getChildren(); 
            char ch = word.charAt(level); 
   
            if( child.containsKey(ch)) 
                crawl = child.get(ch); 
            else 
            { 
                TrieNode temp = new TrieNode(ch); 
                child.put( ch, temp ); 
                crawl = temp; 
            } 
        } 
  
        crawl.setIsEnd(true); 
    } 
  
   
    public String getMatchingPrefix(String input)  { 
        String result = ""; 
        int length = input.length();   
  

        TrieNode crawl = root;    
  
        
        int level, prevMatch = 0; 
        for( level = 0 ; level < length; level++ ) 
        { 
       
            char ch = input.charAt(level);     
  
            
            HashMap<Character,TrieNode> child = crawl.getChildren();                         
  
     
            if( child.containsKey(ch) ) 
            { 
               result += ch;       
               crawl = child.get(ch);
               if( crawl.isEnd() ) 
                    prevMatch = level + 1; 
            } 
            else  break; 
        } 
  
        if( !crawl.isEnd() ) 
                return result.substring(0, prevMatch);         
  
        else return result; 
    } 
  
    private TrieNode root; 
} 
  


