package projekt2;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.*;
import java.io.IOException;

/**
 * Klasa pobierająca jsony
 */
public class Download {

    /**
     * json typu JSONObject
     */
    public JSONObject json;
    /**
     * json typu JSONArray
     */
    public JSONArray jsonArray;

    /**
     * StringBuilder buffora pobranych linii tekstu
     * @param br BufforReader
     * @return zawartość buffora w formie String
     * @throws IOException błąd połączenia z serwerem
     */
    public static String readAll(Reader br) throws IOException{
        StringBuilder sb = new StringBuilder();
        int er;
        while ((er = br.read()) != -1){
            sb.append((char) er);
        }
        return sb.toString();
    }

    /**
     * Metoda pobierająca zawartość jsona w formie tekstu String
     * @param url kod url pobieranego jsona
     * @return treść jsona
     * @throws IOException błąd połączenia z serwerem
     */
    public String getJsonText(String url) throws IOException{
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(br);
            is.close();
            return jsonText;
        }  catch (IOException e){
            System.out.printf("\033[31mNie udało się pobrać JSONa pod podanym linkiem\033[0m\n");
            System.out.println("\033[31mPrawdopodnie brak danych w bazie\033[0m");
            System.exit(1);
            return null;
        }
    }

    /**
     * Metoda stwarzająca json
     * @param jsonText zawartość tekstowa pobrana z API
     * @throws JSONException błąd utworzenia jsona
     */
    public void makeJSON(String jsonText) throws JSONException {
        if (jsonText != null) {
            if (jsonText.charAt(0) == '[') {
                try {
                    jsonArray = new JSONArray(jsonText);
                } catch (JSONException e) {
                    System.out.println("\033[31mBłąd utworzenia tablicy JSON\033[0m");
                    System.exit(1);
                }
            } else {
                try {
                    json = new JSONObject(jsonText);
                } catch (JSONException es) {
                    System.out.println(jsonText);
                    System.out.println("\033[31mBłąd utworzenia obiektu JSON\033[0m");
                    System.exit(1);
                }
            }
        }
    }
}
