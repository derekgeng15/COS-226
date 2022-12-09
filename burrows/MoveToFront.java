import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdIn;

public class MoveToFront {

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {
        char[] moveToFront = new char[256];
        for(int i = 0; i < moveToFront.length; i++)
            moveToFront[i] = (char) i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8);
            for (char i = 0; i < moveToFront.length; i++) {
                if (c == moveToFront[i]) { 
                    BinaryStdOut.write(i);
                    for (int j = i; j > 0; j--)
                        moveToFront[j] = moveToFront[j - 1];
                    moveToFront[0] = c; 
                    break;
                }
            }
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
        char[] moveToFront = new char[256];
        for (int i = 0; i < moveToFront.length; i++)
            moveToFront[i] = (char) i;
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8);
            BinaryStdOut.write(moveToFront[c]);
            char temp = moveToFront[c];
            for (char j = c; j > 0; j--)
                moveToFront[j] = moveToFront[j - 1];
            moveToFront[0] = temp; 
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        
    }
}
