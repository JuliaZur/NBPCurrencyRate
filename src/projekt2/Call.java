package projekt2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Klasa implmentująca Callable w celu obsługi wielowątkowości pobierania wielu jsonów
 */
public class Call implements Callable<JSONObject>{

    /**
     * Pole przechowujące kod url jsona
     */
    private final String url;
    Download download = new Download();

    /**
     * Konstruktor klasy Call
     * @param url kod url jsona
     */
    public Call(String url){
        this.url = url;
    }

    /**
     * Metoda call służąca do pobierania JSONObjects
     * @return JSONObject pobrany z podanego adresu url
     * @throws IOException błąd połączenia z serwerem
     * @throws JSONException błąd utworzenia jsona z pobranego tekstu
     */
    @Override
    public JSONObject call() throws IOException, JSONException{
        String jsonText = download.getJsonText(url);
        download.makeJSON(jsonText);
        JSONObject json = download.json;
        return json;
    }

    /**
     * Metoda call służąca do pobierania JSONArrays
     * @return JSONArray pobrany z podanego adresu url
     * @throws IOException błąd połączenia z serwerem
     * @throws JSONException błąd utworzenia jsona z pobranego tekstu
     */
    public JSONArray call2() throws IOException, JSONException{
        String jsonText = download.getJsonText(url);
        download.makeJSON(jsonText);
        JSONArray json = download.jsonArray;
        return json;
    }

}
