package projekt2;

import org.json.JSONObject;

/**
 * Klasa dla funkcji znajdywania dat, kiedy waluta była najdroższa i najtańsza
 */
public class LowestHighestCurrPrice {

    public Currency lowest = new Currency("","",0.0,0.0,0.0);
    public Currency highest = new Currency("","",0.0,0.0,0.0);

    /**
     * Metoda wyznaczająca daty, kiedy waluta była najdroższa i najtańsza
     * @param json kolejno rozpatrywane jsony
     */
    public void getDate(JSONObject json){
        if (lowest.mid==0.0 || highest.mid == 0.0) {
            Currency curr = new Currency();
            curr.getMid(json.getJSONArray("rates").getJSONObject(0));
            curr.getDate(json.getJSONArray("rates").getJSONObject(0));
            lowest.mid = highest.mid = curr.mid;
        }
        for (int i=0; i<json.getJSONArray("rates").length(); i++){
            Currency curr = new Currency();
            curr.getMid(json.getJSONArray("rates").getJSONObject(i));
            curr.getDate(json.getJSONArray("rates").getJSONObject(i));
            if (lowest.mid>curr.mid){
                lowest = curr;
            }
            if (highest.mid<curr.mid){
                highest = curr;
            }
        }
    }

    /**
     * Metoda wypisująca w odpowiednim formacie daty
     */
    public void printDate(){
        System.out.println("Podana waluta była najtańsza: "+lowest.date+" a najdroższa: "+highest.date);
    }

}
