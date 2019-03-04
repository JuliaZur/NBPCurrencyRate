package projekt2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Obsługa linii komend
 */
public class CommandLineParser {

    /**
     * początek okresu
     */
    public LocalDate startDate;
    /**
     * koniec okresu
     */
    public LocalDate endDate;
    /**
     * dzień
     */
    public LocalDate date;
    /**
     * kod waluty
     */
    public String code = "";
    /**
     * symbol słupka diagramu
     */
    public char s;
    Boolean periodS=false;
    Boolean periodE=false;
    Boolean isDate=false;
    Boolean goldAndCurr=false;
    Boolean avgGold=false;
    Boolean amplitude=false;
    Boolean lowest=false;
    Boolean diff=false;
    Boolean lowestHighestPrice=false;
    Boolean diagram=false;
    Boolean symbol =false;

    /**
     * Najwcześniejsza możliwa data w przypadku walut
     */
    LocalDate triggerCurr = LocalDate.parse("2002-01-02");
    /**
     * Najwcześniejsza możliwa data w przypadku złota
     */
    LocalDate triggerGold = LocalDate.parse("2013-01-02");
    /**
     * kod url
     */
    String url;

    /**
     * Metoda definiująca dane w linii komend
     * @param args linia komend
     * @throws Exception wszelkie błędy zakresów dat oraz niepodania wystarczających danych
     */
    public void getValues(String[] args) throws Exception{
        for (int i=0; i<args.length-1; i++) {
            if (args[i].equals("-gC")){
                goldAndCurr=true;
                for (int j=i+1; j<args.length-1;j++){
                    if (args[j].equals("-date")){
                        date = LocalDate.parse(args[j+1]);
                        isDate = true;
                    }
                    if (args[j].equals("-c")){
                        code = args[j+1];
                    }
                }
                if (!isDate || code.isEmpty() || date.isAfter(LocalDate.now())){
                    throw new Exception("\033[31mBrak danych\033[0m");
                }
                if (date.isAfter(LocalDate.now())){
                    throw new Exception("\033[31mNiepoprawna data\033[0m");
                }
                if (date.isBefore(triggerGold)){
                    throw new Exception("\033[31mData wykracza poza zasięg danych\033[0m");
                }
                break;
            }
            if (args[i].equals("-aG")){
                avgGold=true;
                for (int j=i+1; j<args.length-1;j++){
                    if (args[j].equals("-startDate")){
                        startDate = LocalDate.parse(args[j+1]);
                        periodS = true;
                    }
                    if (args[j].equals("-endDate")){
                        endDate = LocalDate.parse(args[j+1]);
                        periodE = true;
                    }
                }
                if (!periodE || !periodS){
                    throw new Exception("\033[31mBrak daty\033[0m");
                }
                if (startDate.isAfter(endDate) || endDate.isAfter(LocalDate.now())){
                    throw new Exception("\033[31mNiepoprawna data\033[0m");
                }
                if (startDate.isBefore(triggerGold)){
                    throw new Exception("\033[31mData wykracza poza zasięg danych\033[0m");
                }
                break;
            }
            if (args[i].equals("-aT")){
                amplitude=true;
                for (int j=i+1; j<args.length-1;j++){
                    if (args[j].equals("-startDate")){
                        startDate = LocalDate.parse(args[j+1]);
                        periodS = true;
                        endDate = LocalDate.now();
                    }
                }
                if (!periodS){
                    throw new Exception("\033[31mBrak daty\033[0m");
                }
                if (startDate.isAfter(LocalDate.now())){
                    throw new Exception("\033[31mNiepoprawna data\033[0m");
                }
                if (startDate.isBefore(triggerCurr)){
                    throw new Exception("\033[31mData wykracza poza zasięg danych\033[0m");
                }
                break;
            }
            if (args[i].equals("-lT")){
                lowest=true;
                for (int j=i+1; j<args.length-1; j++){
                    if (args[j].equals("-date")){
                        date = LocalDate.parse(args[j+1]);
                        isDate=true;
                    }
                }
                if (!isDate){
                    throw new Exception("\033[31mBrak daty\033[0m");
                }
                if (date.isAfter(LocalDate.now())){
                    throw new Exception("\033[31mNiepoprawna data\033[0m");
                }
                if (date.isBefore(triggerCurr)){
                    throw new Exception("\033[31mData wykracza poza zasięg danych\033[0m");
                }
                break;
            }
            if (args[i].equals("-dF")){
                diff=true;
                for (int j=i+1; j<args.length-1;j++){
                    if (args[j].equals("-date")){
                        date = LocalDate.parse(args[j+1]);
                        isDate = true;
                    }
                }
                if (!isDate){
                    throw new Exception("\033[31mBrak daty\033[0m");
                }
                if (date.isBefore(triggerCurr)){
                    throw new Exception("\033[31mData wykracza poza zasięg danych\033[0m");
                }
                break;
            }
            if (args[i].equals("-lH")){
                lowestHighestPrice=true;
                for (int j =i+1; j<args.length-1;j++){
                    if (args[j].equals("-c")){
                        code = args[j+1];
                        startDate = LocalDate.parse("2002-01-02");
                        endDate = LocalDate.now();
                    }
                }
                if (code.isEmpty()){
                    throw new Exception("\033[31mBrak danych\033[0m");
                }
                if (startDate.isBefore(triggerCurr)){
                    throw new Exception("\033[31mData wykracza poza zasięg danych\033[0m");
                }
            }
            if (args[i].equals("-dG")){
                diagram=true;
                for (int j=i+1; j<args.length-1;j++){
                    if (args[j].equals("-startDate")){
                        periodS = true;
                        String[] parts = args[j+1].split("-");
                        if (parts.length!=3){
                            throw new Exception("\033[31mNiepoprawny format daty\033[0m");
                        }
                        int firstDay = Integer.parseInt(parts[2]);
                        firstDay = (firstDay * 7) - 6;
                        String day="";
                        if (firstDay<10) {
                            day = "0"+String.valueOf(firstDay);
                        }
                        else {
                            day = String.valueOf(firstDay);
                        }
                        startDate = LocalDate.parse(parts[0]+"-"+parts[1]+"-"+day);
                        while (startDate.getDayOfWeek()!= DayOfWeek.MONDAY){
                            startDate = startDate.plusDays(1);
                        }
                    }
                    if (args[j].equals("-endDate")){
                        periodE = true;
                        String[] parts = args[j+1].split("-");
                        if (parts.length!=3){
                            throw new Exception("\033[31mNiepoprawny format daty\033[0m");
                        }
                        int lastDay = Integer.parseInt(parts[2]);
                        lastDay = (lastDay * 7);
                        String day="";
                        if (lastDay<10) {
                            day = "0"+String.valueOf(lastDay);
                        }
                        else {
                            day = String.valueOf(lastDay);
                        }
                        endDate = LocalDate.parse(parts[0]+"-"+parts[1]+"-"+day);
                        while (endDate.getDayOfWeek()!= DayOfWeek.FRIDAY){
                            endDate = endDate.minusDays(1);
                        }
                    }
                    if (args[j].equals("-c")){
                        code = args[j+1];
                    }
                    if (args[j].equals("-s")){
                        symbol = true;
                        s = args[j+1].charAt(0);
                    }
                }
                if (!symbol){
                    s = '█';
                }
                if (!periodS || !periodE || code.isEmpty()){
                    throw new Exception("\033[31mBrak daty\033[0m");
                }
                if (startDate.isAfter(endDate) || endDate.isAfter(LocalDate.now())){
                    throw new Exception("\033[31mNiepoprawna data\033[0m");
                }
                if (startDate.isBefore(triggerCurr)){
                    throw new Exception("\033[31mData wykracza poza zasięg danych\033[0m");
                }
                break;
            }
        }
        if (!goldAndCurr && !avgGold && !amplitude && !lowest && !diff && !lowestHighestPrice && !diagram || args.length ==0){
            System.out.println("Cena złota i waluty w podanym dniu:\n-gC\n-date data w formacie \"yyyy-mm-dd\"\n-c kod waluty\n");
            System.out.println("Średnia cena złota w podanym okresie:\n-aG\n-startDate data początku okresu \"yyyy-mm-dd\"\n-endDate data końca okresu \"yyyy-mm-dd\"\n");
            System.out.println("Waluta o największej amplitudzie zmian kursu:\n-aT\n-startDate data, od której obliczane są dane w formacie \"yyyy-mm-dd\"\n");
            System.out.println("Waluta o najmniejszym kursie w podanym dniu:\n-lT\n-date data w formacie \"yyyy-mm-dd\"\n");
            System.out.println("Różnice cen sprzedaży i kupna walut w podanym dniu:\n-dF\n-date data w formacie \"yyyy-mm-dd\"\n");
            System.out.println("Daty, kiedy dana waluta była najdroższa i najtańsza:\n-lH\n-c kod waluty\n");
            System.out.println("Diagram w układzie tygodniowym cen w podanym okresie danej waluty:\n-dG\n-startDate data początku okresu w formacie \"yyyy-mm-w\"\n-endDate data końca okresu w formacie \"yyyy-mm-w\"\n-c kod waluty\n-s znak słupka diagramu\n");
        }
    }

    /**
     * Metoda tworząca url i pobierająca dane wielowątkowo
     * @throws IOException błąd połączenia z serwerem
     * @throws JSONException błąd utworzenia jsona
     * @throws Exception błąd zbyt małych przedziałów czasowych
     */
    public void urlMaker() throws IOException, JSONException, Exception{
        if (goldAndCurr){
            ActualGoldAndCurrPrice a = new ActualGoldAndCurrPrice();
            url = "http://api.nbp.pl/api/exchangerates/rates/a/"+code+"/"+date.toString()+"/?format=json";
            a.getCurr(url);
            url = "http://api.nbp.pl/api/cenyzlota/"+date.toString()+"/?format=json";
            a.getGold(url);
            a.printActual(a.gold,a.curr);
        }
        if (avgGold) {
            AverageGoldPrice b = new AverageGoldPrice();
            LocalDate period = startDate;
            int full93Days = 0;
            period = period.plusDays(93);
            while (period.isBefore(endDate)) {
                full93Days++;
                period = period.plusDays(93);
            }
            if (period.isAfter(LocalDate.now())){
                full93Days--;
            }
            List<JSONArray> jsons = new ArrayList<>();
            for (int i=0; i<full93Days;i++){
                LocalDate temporaryEnd = startDate.plusDays(93);
                if (temporaryEnd.isBefore(endDate)) {
                    url = "http://api.nbp.pl/api/cenyzlota/" + startDate.toString() + "/" + temporaryEnd.toString();
                    Call call = new Call(url);
                    jsons.add(call.call2());
                    startDate = startDate.plusDays(94);
                }
                else {
                    break;
                }
            }
            url = "http://api.nbp.pl/api/cenyzlota/" + startDate.toString() + "/" +endDate.toString()+ "/?format=json";
            Call call = new Call(url);
            jsons.add(call.call2());
            for (int i=0; i<jsons.size(); i++) {
                b.getAverage(jsons.get(i));
            }
            b.printAvgGoldPrice(b.averageGoldPrice);
        }
        if (amplitude){
            CurrAmplitude c = new CurrAmplitude();
            LocalDate period = startDate;
            int full93Days = 0;
            period = period.plusDays(93);
            while (period.isBefore(endDate)){
                full93Days++;
                period = period.plusDays(93);
            }
            List<JSONArray> jsons = new ArrayList<>();
            for (int i=0; i<full93Days;i++){
                LocalDate temporaryEnd = startDate.plusDays(93);
                if (temporaryEnd.isBefore(endDate)) {
                    url = "http://api.nbp.pl/api/exchangerates/tables/a/" + startDate.toString() + "/" + temporaryEnd.toString() + "/?format=json";
                    Call call = new Call(url);
                    jsons.add(call.call2());
                    startDate = startDate.plusDays(94);
                }
                else {
                    break;
                }
            }
            url = "http://api.nbp.pl/api/exchangerates/tables/a/"+startDate.toString()+"/"+endDate.toString()+"/?format=json";
            Call call = new Call(url);
            jsons.add(call.call2());
            for (int i=0; i<jsons.size(); i++) {
                c.getAmplitude(jsons.get(i));
            }
            c.makeAmplitude();
            c.printMaxAmplitude();
        }
        if (lowest){
            LowestCurrPrice d = new LowestCurrPrice();
            url = "http://api.nbp.pl/api/exchangerates/tables/c/"+date.toString()+"/?format=json";
            d.getLowestCurr(url);
            d.printLowest();
        }
        if (diff){
            DiffSellBuyCurrPrice e = new DiffSellBuyCurrPrice();
            url = "http://api.nbp.pl/api/exchangerates/tables/c/"+date.toString()+"/?format=json";
            e.getDiff(url);
            e.printDiff();
        }
        if (lowestHighestPrice){
            LowestHighestCurrPrice f = new LowestHighestCurrPrice();
            LocalDate period = startDate;
            int full93Days = 0;
            period = period.plusDays(93);
            while (period.isBefore(endDate)){
                full93Days++;
                period = period.plusDays(93);
            }
            List<JSONObject> jsons = new ArrayList<>();
            for (int i=0; i<full93Days;i++){
                LocalDate temporaryEnd = startDate.plusDays(93);
                if (temporaryEnd.isBefore(endDate)) {
                    url = "http://api.nbp.pl/api/exchangerates/rates/a/"+code+"/"+startDate.toString()+"/"+temporaryEnd.toString()+"/?format=json";
                    Call call = new Call(url);
                    jsons.add(call.call());
                    startDate = startDate.plusDays(94);
                }
                else {
                    break;
                }
            }
            url = "http://api.nbp.pl/api/exchangerates/rates/a/"+code+"/"+startDate.toString()+"/"+endDate.toString()+"/?format=json";
            Call call = new Call(url);
            jsons.add(call.call());
            for (int i=0; i<jsons.size(); i++) {
                f.getDate(jsons.get(i));
            }
            f.printDate();
        }
        if (diagram){
            Diagram g = new Diagram();
            LocalDate period = startDate;
            int full93Days = 0;
            period = period.plusDays(93);
            while (period.isBefore(endDate)){
                full93Days++;
                period = period.plusDays(93);
            }
            List<JSONObject> jsons = new ArrayList<>();
            for (int i=0; i<full93Days;i++){
                LocalDate temporaryEnd = startDate.plusDays(93);
                if (temporaryEnd.isBefore(endDate)) {
                    url = "http://api.nbp.pl/api/exchangerates/rates/a/"+code+"/"+startDate.toString()+"/"+temporaryEnd.toString()+"/?format=json";
                    Call call = new Call(url);
                    jsons.add(call.call());
                    startDate = startDate.plusDays(94);
                }
                else {
                    break;
                }
            }
            url = "http://api.nbp.pl/api/exchangerates/rates/a/"+code+"/"+startDate.toString()+"/"+endDate.toString()+"/?format=json";
            Call call = new Call(url);
            jsons.add(call.call());
            for (int i=0; i<jsons.size(); i++) {
                g.getPrices(jsons.get(i));
            }
            g.addDay();
            g.printDiagram(s);
        }

    }

}
