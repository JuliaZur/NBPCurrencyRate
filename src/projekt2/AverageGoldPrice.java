package projekt2;

import org.json.JSONArray;
import java.text.DecimalFormat;

/**
 * Klasa dla funkcji obliczania średniej ceny złota
 */
public class AverageGoldPrice {

    /**
     * Double przechowujący wyliczaną średnią z kolejnych pobieranych jsonów
     */
    public Double averageGoldPrice=0.0;

    /**
     * Metoda obliczająca średnią cenę złota
     * @param json aktualnie rozpatrywany pobrany json
     */
    public void getAverage(JSONArray json){
        for (int i=0; i<json.length(); i++){
            Gold gold = new Gold();
            gold.getPrice(json.getJSONObject(i));
            averageGoldPrice += gold.price;
        }
        averageGoldPrice = averageGoldPrice/json.length();
    }

    private DecimalFormat decimalFormat = new DecimalFormat(".##");

    /**
     * Metoda wypisująca średnią cenę złota w odpowiednim formacie
     * @param averageGoldPrice średnia cena złota w podanym okresie
     */
    public void printAvgGoldPrice(Double averageGoldPrice){
        System.out.println("Średnia cena złota w podanym okresie wynosi: "+decimalFormat.format(averageGoldPrice)+ " zł za 1 g złota (w próbie 1000)");
    }

}
