package projekt2;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.time.LocalDate.parse;

/**
 * Metoda dla funkcji rysowania diagramu
 */
public class Diagram {

    /**
     * Klasa dla listy dni tygodnia z ceną
     */
    public class Days{
        public Enum day;
        Currency curr;
    }

    /**
     * lista cen waluty
     */
    public ArrayList<Currency> prices = new ArrayList<>();
    /**
     * lista cen waluty wraz z dniem
     */
    public ArrayList<Days> pricesWithDays = new ArrayList<>();

    /**
     * Metoda pobierająca ceny waluty z jsona
     * @param json json, z którego aktualnie pobierane są dane
     */
    public void getPrices(JSONObject json){
        for (int i=0; i<json.getJSONArray("rates").length();i++){
            Currency curr = new Currency();
            curr.getMid(json.getJSONArray("rates").getJSONObject(i));
            curr.getDate(json.getJSONArray("rates").getJSONObject(i));
            prices.add(curr);
        }
    }

    /**
     * Metoda przypisująca dacie dzień tygodnia
     */
    public void addDay(){
        for (int i=0; i<prices.size();i++){
            Days day = new Days();
            LocalDate localDate;
            localDate = parse(prices.get(i).date);
            day.curr = prices.get(i);
            day.day = localDate.getDayOfWeek();
            pricesWithDays.add(day);
        }
    }

    DecimalFormat decimalFormat = new DecimalFormat("###.##");

    /**
     * Metoda rysująca diagram
     * @param symbol znak słupka diagramu
     */
    public void printDiagram(char symbol){
        int t=0;
        for (int i=0; i<pricesWithDays.size(); i++) {
            if (pricesWithDays.get(i).day == DayOfWeek.MONDAY) {
                t++;
                System.out.format("%-7s","Pon"+t+"  ");
                for (int j = 0; j < pricesWithDays.get(i).curr.mid*10; j++) {
                    System.out.print("\033[36m"+symbol+"\033[0m");
                }
                System.out.println("  " + decimalFormat.format(pricesWithDays.get(i).curr.mid) + " zł");
            }
        }
        t=0;
        for (int i=0; i<pricesWithDays.size(); i++) {
            if (pricesWithDays.get(i).day == DayOfWeek.TUESDAY) {
                t++;
                System.out.format("%-7s","Wto"+t+"  ");
                for (int j = 0; j < pricesWithDays.get(i).curr.mid*10; j++) {
                    System.out.print("\033[32m"+symbol+"\033[0m");
                }
                System.out.println("  " + decimalFormat.format(pricesWithDays.get(i).curr.mid) + " zł");
            }
        }
        t=0;
        for (int i=0; i<pricesWithDays.size(); i++) {
            if (pricesWithDays.get(i).day == DayOfWeek.WEDNESDAY) {
                t++;
                System.out.format("%-7s","Śro"+t+"  ");
                for (int j = 0; j < pricesWithDays.get(i).curr.mid*10; j++) {
                    System.out.print("\033[33m"+symbol+"\033[0m");
                }
                System.out.println("  " + decimalFormat.format(pricesWithDays.get(i).curr.mid) + " zł");
            }
        }
        t=0;
        for (int i=0; i<pricesWithDays.size(); i++) {
            if (pricesWithDays.get(i).day == DayOfWeek.THURSDAY) {
                t++;
                System.out.format("%-7s","Czw"+t+"  ");
                for (int j = 0; j < pricesWithDays.get(i).curr.mid*10; j++) {
                    System.out.print("\033[34m"+symbol+"\033[0m");
                }
                System.out.println("  " + decimalFormat.format(pricesWithDays.get(i).curr.mid) + " zł");
            }
        }
        t=0;
        for (int i=0; i<pricesWithDays.size(); i++) {
            if (pricesWithDays.get(i).day == DayOfWeek.FRIDAY) {
                t++;
                System.out.format("%-7s","Pia"+t+"  ");
                for (int j = 0; j < pricesWithDays.get(i).curr.mid*10; j++) {
                    System.out.print("\033[31m"+symbol+"\033[0m");
                }
                System.out.println("  " + decimalFormat.format(pricesWithDays.get(i).curr.mid) + " zł");
            }
        }
    }
}
