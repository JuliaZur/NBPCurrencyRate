package projekt2;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Klasa dla funkcji zwracającej walutę o najmniejszym kursie
 */
public class LowestCurrPrice {
    Download download = new Download();
    Currency curr = new Currency();
    /**
     * Pole najmniejszej ceny
     */
    public Double lowestPrice;
    /**
     * kod waluty
     */
    String code;

    /**
     * Metoda znajdująca najmniejszy kurs
     * @param url kod url danego jsona
     * @throws IOException błąd połączenia z serwerem
     */
    public void getLowestCurr(String url) throws IOException{
        String jsonText = download.getJsonText(url);
        download.makeJSON(jsonText);
        curr.getBid(download.jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(0));
        lowestPrice = curr.bid;

        for (int i=0; i<download.jsonArray.getJSONObject(0).getJSONArray("rates").length(); i++){
            curr.getBid(download.jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(i));
            curr.getCode(download.jsonArray.getJSONObject(0).getJSONArray("rates").getJSONObject(i));
            if (lowestPrice > curr.bid){
                lowestPrice = curr.bid;
                code = curr.code;
            }
        }
    }

    DecimalFormat decimalFormat = new DecimalFormat("##.####");

    /**
     * Metoda wypisująca w odpowiednim formacie najmniejszy kurs i kod waluty w podanym dniu
     */
    public void printLowest(){
        System.out.println("Najniższy kurs ma "+code+" i wynosi: "+decimalFormat.format(lowestPrice)+" zł za sztukę");
    }

}
