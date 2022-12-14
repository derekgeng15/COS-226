import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;

public class MoveToFront {

    // apply move-to-front encoding, reading from stdin and writing to stdout
    private static final int R = 256; // ascii size

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {
        char[] moveToFront = new char[R]; // store current order of ascii
        // start with array in ascii numerical order       
        for (int i = 0; i < moveToFront.length; i++)
            moveToFront[i] = (char) i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8); // read in next char
            // find matching char index
            char i = 0;
            while (c != moveToFront[i]) i++; 
            BinaryStdOut.write(i); // write index to output
            for (int j = i; j > 0; j--)
                moveToFront[j] = moveToFront[j - 1];  // shift elements down by 1
            moveToFront[0] = c; // move element to front
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
        // create array for all the ASCII chars
        char[] moveToFront = new char[R];
        // start with array in ascii numerical order       
        for (int i = 0; i < moveToFront.length; i++)
            moveToFront[i] = (char) i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8); // read in next char
            BinaryStdOut.write(moveToFront[c]); // print the char at that index
            char temp = moveToFront[c];
            for (char j = c; j > 0; j--)
                moveToFront[j] = moveToFront[j - 1]; // shift elements array down by 1
            // move selected character to front
            moveToFront[0] = temp; 
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
    }
}
