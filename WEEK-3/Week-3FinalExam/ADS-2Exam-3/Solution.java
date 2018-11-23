import java.util.*;


public class Solution {

	// Don't modify this method.
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String cases = scan.nextLine();

		switch (cases) {
		case "loadDictionary":
			// input000.txt and output000.txt
			BinarySearchST<String, Integer> hash = loadDictionary("/Files/t9.csv");
			while (scan.hasNextLine()) {
				String key = scan.nextLine();
				System.out.println(hash.get(key));
			}
			break;

		case "getAllPrefixes":
			// input001.txt and output001.txt
			T9 t9 = new T9(loadDictionary("/Files/t9.csv"));
			while (scan.hasNextLine()) {
				String prefix = scan.nextLine();
				for (String each : t9.getAllWords(prefix)) {
					System.out.println(each);
				}
			}
			break;

		case "potentialWords":
			// input002.txt and output002.txt
			t9 = new T9(loadDictionary("/Files/t9.csv"));
			int count = 0;
			while (scan.hasNextLine()) {
				String t9Signature = scan.nextLine();
				for (String each : t9.potentialWords(t9Signature)) {
					count++;
					System.out.println(each);
				}
			}
			if (count == 0) {
				System.out.println("No valid words found.");
			}
			break;

		case "topK":
			// input003.txt and output003.txt
			t9 = new T9(loadDictionary("/Files/t9.csv"));
			Bag<String> bag = new Bag<String>();
			int k = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				bag.add(line);
			}
			for (String each : t9.getSuggestions(bag, k)) {
				System.out.println(each);
			}

			break;

		case "t9Signature":
			// input004.txt and output004.txt
			t9 = new T9(loadDictionary("/Files/t9.csv"));
			bag = new Bag<String>();
			k = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				for (String each : t9.t9(line, k)) {
					System.out.println(each);
				}
			}
			break;

		default:
			break;

		}
	}

	// Don't modify this method.
	public static String[] toReadFile(String file) {
		In in = new In(file);
		return in.readAllStrings();
	}

	public static BinarySearchST<String, Integer> loadDictionary(String file) {
		BinarySearchST<String, Integer>  st = new BinarySearchST<String, Integer>();
		String[] input = toReadFile(file);
		String[] inputl = new String[input.length];
		for(int i =0 ;i<input.length;i++){
			inputl[i] = input[i].toLowerCase();
		}
		List<String> list=Arrays.asList(inputl);
		Set<String> unique = new HashSet<String>(list);
		for (String key : unique) {
		    st.put(key,Collections.frequency(list, key));
		}


		return st;
	}

}

class T9 {
	TST<Integer> myTst;

	public T9(BinarySearchST<String, Integer> st) {
		// your code goes here
		myTst = new TST<Integer>();
		for (String key : st.keys()) {
			myTst.put(key,st.get(key));

		}
	}

	// get all the prefixes that match with given prefix.
	public Iterable<String> getAllWords(String prefix) {
		// your code goes here

		return myTst.keysWithPrefix(prefix);
	}

	public Iterable<String> potentialWords(String t9Signature) {

		//hashmap:
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		String[] a = {"a", "b","c"};
		map.put("2",a);
		String[] a1= {"d", "e","f"};
		map.put("3",a1);
		String[] a2 = {"g", "h","i"};
		map.put("4",a2);
		String[] a3= {"j", "k","l"};
		map.put("5",a3);
		String[] a4 = {"m", "n","o"};
		map.put("6",a4);
		String[] a5 = {"p", "q","r","s"};
		map.put("7",a5);
		String[] a6 = { "t","u","v"};
		map.put("8",a6);
		String[] a7 = {"w","x","y","z"};
		map.put("9",a7);

		for(int i =0 ;i< t9Signature.length();i++){
			String num = t9Signature.charAt(i)+"";
		}

		return null;
	}

	// return all possibilities(words), find top k with highest frequency.
	public Iterable<String> getSuggestions(Iterable<String> words, int k) {
		List<String> wordlist = new ArrayList<String>();
        MaxPQ<Integer> maxpq = new MaxPQ<>();
        for (String str : words) {
            maxpq.insert(myTst.get(str));
        }
        for (int i = 0; i < k; i++) {
            int max = maxpq.delMax();
            for (String s : words) {
                if (max == myTst.get(s)) {
                    wordlist.add(s);
                }
            }
        }
        Collections.sort(wordlist);
        return wordlist;
	}



	// final output
	// Don't modify this method.
	public Iterable<String> t9(String t9Signature, int k) {
		return getSuggestions(potentialWords(t9Signature), k);
	}
}
