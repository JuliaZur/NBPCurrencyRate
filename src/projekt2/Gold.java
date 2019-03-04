package projekt2;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Metoda pobiera z JSONa potrzebne wartości pól
 */
public class Gold {

    /**
     * Pole daty zawierające datę publikacji notowania
     */
    public String date;
    /**
     * Pole ceny złota w konkretnym dniu
     */
    public Double price;

    /**
     * Metoda otrzymuje JSONObject (w przypadku złota zawsze z JSONArray)
     * i pobiera wartość pola data
     * @param json JSON z którego pobieramy dane
     */
    public void getDate(JSONObject json){
        date = json.getString("data");
    }

    /**
     * Metoda otrzymuje JSONObject (w przypadku złota zawsze z JSONArray)
     * i pobiera wartość pola cena
     * @param json JSON z którego pobieramy dane
     */
    public void getPrice(JSONObject json){
        price = json.getDouble("cena");
    }
}
