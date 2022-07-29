import txted.txted;

public class Main {

    public static void main(String[] args) {
        txted text = new txted(args);
    }

    private static void usage() {
        System.err.println("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE");
    }
}
