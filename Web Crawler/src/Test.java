
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.*;
import java.io.Serializable;

public class Test implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main() throws IOException {
		final Trie dict = new Trie();

		Path trie = Paths.get("D:/", "sortedDict.txt");
		Path seri = Paths.get("D:/", "trie.ser");

		for (int i = 1; i < 31137; i++) {
			String text = Files.readAllLines(trie).get(i);
			System.out.println(text);
			dict.insert(text);
		}
		
		Files.write(seri, serialize(dict));

		
		
		/* System.out.println("Test Trie");
		String input = "ADN";
		System.out.print(input + ":   ");
		System.out.println(dict.getMatchingPrefix(input));

		input = "ADSL";
		System.out.print(input + ":   ");
		System.out.println(dict.getMatchingPrefix(input));

		input = "AIDS";
		System.out.print(input + ":   ");
		System.out.println(dict.getMatchingPrefix(input));

		input = "VNVD";
		System.out.print(input + ":   ");
		System.out.println(dict.getMatchingPrefix(input));

		input = "DMDIEU";
		System.out.print(input + ":   ");
		System.out.println(dict.getMatchingPrefix(input));

		input = "BGĐ";
		System.out.print(input + ":   ");
		System.out.println(dict.getMatchingPrefix(input)); */
	}
	
	public static byte[] serialize(Trie trie) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(trie);
	    return out.toByteArray();
	}
	
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}
}