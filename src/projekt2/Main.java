package projekt2;

public class Main {
    /**
     * Obsługa programu
     * @param args linia komend
     * @throws Exception błędy linii komend
     * @author Julia Żur
     */
    public static void main(String[] args) throws Exception {

            CommandLineParser comm = new CommandLineParser();
            comm.getValues(args);
            comm.urlMaker();
    }
}
