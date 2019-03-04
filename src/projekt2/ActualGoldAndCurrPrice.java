package projekt2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Klasa dla funkcji wyznaczającej cenę złota i cenę kursu podanej waluty w podanym dniu
 */
public class ActualGoldAndCurrPrice {

    Download download = new Download();
    public Gold gold = new Gold();
    public Currency curr = new Currency();

    /**
     * Metoda pobierająca z jsona cenę złota
     * @param url Kod url, z którego pobierany jest json
     * @throws IOException Błąd połączenia z serwerem
     * @throws JSONException Błąd utworzenia jsona z pobranego tekstu
     */
    public void getGold(String url) throws IOException, JSONException{
        String jsonText = download.getJsonText(url);
        download.makeJSON(jsonText);
        gold.getPrice(download.jsonArray.getJSONObject(0));
    }

    /**
     * Metoda pobierająca z jsona cenę waluty
     * @param url Kod url, z którego pobierany jest json
     * @throws IOException Błąd połączenia z serwerem
     * @throws JSONException Błąd utworzenia jsona z pobranego tekstu
     */
    public void getCurr(String url) throws IOException, JSONException{
        String jsonText = download.getJsonText(url);
        download.makeJSON((jsonText));
        curr.getCode(download.json);
        curr.getMid(download.json.getJSONArray("rates").getJSONObject(0));
    }

    DecimalFormat decimalFormat = new DecimalFormat("###.####");
    /**
     * Metoda wypisująca w odpowiednim formacie ceny złota i waluty
     * @param gold cena złota
     * @param curr cena waluty
     */
    public void printActual(Gold gold, Currency curr){
        System.out.println("Cena złota: "+gold.price+" zł za 1 g złota (w próbie 1000)");
        System.out.println("Cena "+curr.code+":   "+decimalFormat.format(curr.mid)+" zł za sztukę");
    }
}
