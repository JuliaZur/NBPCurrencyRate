package projekt2;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Klasa dla funkcji obliczania różnicy w cenie kupna i sprzedaży
 */
public class DiffSellBuyCurrPrice {

    /**
     * Klasa dla listy przechowującej różnice cen i kod waluty
     */
    public class Node{
        public Double diff;
        public String code;
    }

    /**
     * Komparator dla listy wartości typu Node, porównujący różnicę cen
     */
    public class Compare implements Comparator<Node>{

        @Override
        public int compare(Node diff1,Node diff2){
            if (diff2 == null) return -1;
            if (diff1.diff > diff2.diff) return 1;
            else if (diff1.diff < diff2.diff) return -1;
            else return 0;
        }
    }

    DecimalFormat decimalFormat = new DecimalFormat("#.####");
    Download download = new Download();
    Currency curr = new Currency();
    /**
     * lista różnic kursów
     */
    public ArrayList<Node> diffrences = new ArrayList<>();
    Compare compare = new Compare();

    /**
     * Metoda obliczająca różnice kursu sprzedaży i kupna walut
     * @param url kod url jsona do pobrania
     * @throws IOException błąd połączenia z serwerem
     */
    public void getDiff(String url) throws IOException{
        String jsonText = download.getJsonText(url);
        download.makeJSON(jsonText);
        for (int i=0; i<download.jsonArray.getJSONObject(0).getJSONArray("rates").length(); i++){
            Node node = new Node();
            curr.getBid(download.jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(i));
            curr.getAsk(download.jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(i));
            curr.getCode(download.jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(i));
            node.diff = curr.ask-curr.bid;
            node.code = curr.code;
            diffrences.add(node);
        }
    }

    /**
     * Metoda wypisująca tabelę różnic kursów walut
     */
    public void printDiff(){
        Collections.sort(diffrences,compare);
        for (int i=0; i<diffrences.size(); i++){
            System.out.println("Waluta: "+diffrences.get(i).code+"    Różnica: "+decimalFormat.format(diffrences.get(i).diff));
        }
    }
}
