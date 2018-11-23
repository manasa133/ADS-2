
public class MoveToFront {

    public static void encode() {
    	char array[] = new char[256];
    	for(int i = 0;i<256;i++){
    		array[i]=(char) i ;
    	}
    	String s = BinaryStdIn.readString();
    	for(int i =0;i<s.length();i++){
    		char c = s.charAt(i);

    		int move=0;
    		for(int j =0;j< array.length;j++){
    			if(array[j]==c){
    				move=j;
    				break;
    			}
    		}
    		  BinaryStdOut.write((char) move);
    		System.arraycopy(array,0,array,1,move);
    		array[0] = c;
    	}
    	 BinaryStdOut.close();


    }

    public static void decode() {
    char[] sequence = new char[256];
        for (int i = 0; i < 256; i++) {
            sequence[i] = (char) i;
        }
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        for (int i = 0; i < input.length; i++) {
            int in = input[i];
            char ch = sequence[in];
            BinaryStdOut.write(ch);
            System.arraycopy(sequence, 0, sequence, 1, in);
            sequence[0] = ch;
        }
        BinaryStdOut.close();



    }

    public static void main(String[] args) {
    	 if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");

    }
}
