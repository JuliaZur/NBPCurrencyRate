package projekt2;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Klasa dla funkcji obliczania największej amplitudy zmian cen waluty
 */
public class CurrAmplitude {

    /**
     * Klasa stworzona dla listy obliczanych amplitud
     */
    public class Amplitude{
        /**
         * amplituda zmian
         */
        public Double amplitude;
        /**
         * kod waluty
         */
        public String code;
    }

    /**
     * lista najmniejszych wartości zmian cen walut
     */
    ArrayList<Currency> mins = new ArrayList<>();
    /**
     * lista największych wartości zmian cen walut
     */
    ArrayList<Currency> maxs = new ArrayList<>();
    /**
     * lista amplitud zmian cen walut
     */
    public ArrayList<Amplitude> amplitudes = new ArrayList<>();

    /**
     * Metoda wyznacza najmniejsze i największe wartości zmian cen walut
     * @param json aktualnie rozpatrywany pobrany json
     * @throws Exception błąd zbyt małego przedziału czasowego
     */
    public void getAmplitude(JSONArray json) throws Exception{
        if (mins.size() == 0 || maxs.size() == 0) {
            for (int t = 0; t < json.getJSONObject(0).getJSONArray("rates").length(); t++) {
                Currency start = new Currency();
                start.getCode(json.getJSONObject(0).getJSONArray("rates").getJSONObject(t));
                start.getMid(json.getJSONObject(0).getJSONArray("rates").getJSONObject(t));
                mins.add(start);
                maxs.add(start);
            }
        }
        if (json.length()==1){
            throw new Exception("Zbyt mały przedział czasowy");
        }
        for (int i=1; i<json.length(); i++){
            for (int j=0; j<json.getJSONObject(i).getJSONArray("rates").length();j++) {
                Currency curr = new Currency();
                curr.getMid(json.getJSONObject(i).getJSONArray("rates").getJSONObject(j));
                curr.getCode(json.getJSONObject(i).getJSONArray("rates").getJSONObject(j));
                if (mins.get(j).mid>curr.mid){
                    mins.set(j,curr);
                }
                if (maxs.get(j).mid<curr.mid){
                    maxs.set(j,curr);
                }
            }
        }
    }

    /**
     * Metoda tworzy amplitudy zmian na podstawie list najmniejszych i największych zmian cen walut
     */
    public void makeAmplitude(){
        for (int i=0; i<mins.size(); i++) {
            Amplitude amplitude = new Amplitude();
            amplitude.code = mins.get(i).code;
            amplitude.amplitude = maxs.get(i).mid-mins.get(i).mid;
            amplitudes.add(amplitude);
        }
    }

    /**
     * Metoda wyznacza największą amplitudę i wypisuje w odpowiednim formacie
     */
    public void printMaxAmplitude(){
        Double max = 0.0;
        String code = "";
        DecimalFormat decimalFormat = new DecimalFormat("###.####");
        for (int i=0; i<amplitudes.size(); i++){
            if (max < amplitudes.get(i).amplitude){
                max = amplitudes.get(i).amplitude;
                code = amplitudes.get(i).code;
            }
        }
        System.out.println("Największą amplitudę zmian ma "+code+" i wynosi ona: "+decimalFormat.format(max)+" zł");
    }

}
