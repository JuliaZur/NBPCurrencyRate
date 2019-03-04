package projekt2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Metoda wyciągająca z JSONa dane dotyczące waluty
 */
public class Currency {

    /**
     * EffectiveDate w postaci String
     */
    public String date;
    /**
     * Kod waluty
     */
    String code;
    /**
     * Kurs średni waluty(tabela A)
     */
    public Double mid;
    /**
     * Kurs kupna waluty
     */
    Double ask;
    /**
     * Kurs sprzedaży waluty
     */
    Double bid;

    /**
     * Pusty konstruktor
     */
    public Currency(){

    }

    /**
     * Konstruktor danych dla waluty
     * @param date data
     * @param code kod waluty
     * @param mid kurs średni waluty
     * @param ask kurs sprzedaży waluty
     * @param bid kurs kupna waluty
     */
    public Currency(String date, String code, Double mid, Double ask, Double bid){
        this.date = date;
        this.code = code;
        this.mid = mid;
        this.ask = ask;
        this.bid = bid;
    }

    /**
     * Metoda pobierająca kod waluty
     * @param json rozpatrywany json
     */
    public void getCode(JSONObject json){
        code =json.getString("code");
    }

    /**
     * Metoda pobierająca datę
     * @param json rozpatrywany json
     */
    public void getDate(JSONObject json){
        date =json.getString("effectiveDate");
    }

    /**
     * Metoda pobierająca kurs średni
     * @param json rozpatrywany json
     */
    public void getBid(JSONObject json){
        bid = json.getDouble("bid");
    }

    /**
     * Metoda pobierająca kurs sprzedaży
     * @param json rozpatrywany json
     */
    public void getAsk(JSONObject json){
        ask = json.getDouble("ask");
    }

    /**
     * Metoda pobierająca kurs kupna
     * @param json rozpatrywany json
     */
    public void getMid(JSONObject json){
        mid = json.getDouble("mid");
    }

}
