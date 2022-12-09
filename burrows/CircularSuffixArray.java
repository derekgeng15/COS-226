public class CircularSuffixArray {

    private String s;
    private String[] indexes;
    private int size;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        this.s = s;
        size = s.length();
        indexes = new String[size];
    }
    // length of s
    public int length() {
        return size;
    }

    // returns index of ith sorted suffix
    public int index(int i) {

    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}

