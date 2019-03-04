import org.json.JSONException;
import org.junit.Test;
import projekt2.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;

public class IntegrationTest {

    private Download download = new Download();
    private String url = "http://api.nbp.pl/api/cenyzlota/2013-01-02/?format=json";

    @Test
    public void DownloadTest() throws IOException{
        String jsonText = download.getJsonText(url);
        download.makeJSON(jsonText);
        if (download.json != null){
            assertEquals(download.json.toString(),"[{\"data\":\"2013-01-02\",\"cena\":165.83}]");
        }
        else {
            assertEquals(download.jsonArray.toString(),"[{\"data\":\"2013-01-02\",\"cena\":165.83}]");
        }
    }

    private Gold gold = new Gold();
    private Currency curr = new Currency();
    private ActualGoldAndCurrPrice a = new ActualGoldAndCurrPrice();
    private String goldURL = "http://api.nbp.pl/api/cenyzlota/2016-01-08/?format=json";
    private String currURL = "http://api.nbp.pl/api/exchangerates/rates/a/usd/2016-01-08/?format=json";

    @Test
    public void ActualGoldAndCurrPriceTest() throws IOException{
        String jsonText = download.getJsonText(goldURL);
        download.makeJSON(jsonText);
        gold.getPrice(download.jsonArray.getJSONObject(0));

        String jsonText2 = download.getJsonText(currURL);
        download.makeJSON(jsonText2);
        curr.getMid(download.json.getJSONArray("rates").getJSONObject(0));

        a.getGold(goldURL);
        a.getCurr(currURL);

        assertEquals(gold.price,a.gold.price);
        assertEquals(curr.mid,a.curr.mid);
    }

    private LowestCurrPrice l = new LowestCurrPrice();
    private String lowestUrl = "http://api.nbp.pl/api/exchangerates/tables/c/2014-07-03/?format=json";

    @Test
    public void LowestCurrPriceTest() throws IOException{
        String jsonText = download.getJsonText(lowestUrl);
        download.makeJSON(jsonText);
        l.getLowestCurr(lowestUrl);

        assertEquals(0.013177,l.lowestPrice);
    }

    private DiffSellBuyCurrPrice d = new DiffSellBuyCurrPrice();
    private String diffUrl = "http://api.nbp.pl/api/exchangerates/tables/c/2016-08-30/?format=json";
    private DecimalFormat decimalFormat = new DecimalFormat("#.####");

    @Test
    public void DiffSellBuyCurrPriceTest() throws IOException{
        String jsonText = download.getJsonText(diffUrl);
        download.makeJSON(jsonText);
        ArrayList<String> listCode = new ArrayList<>();
        listCode.add("USD");
        listCode.add("AUD");
        listCode.add("CAD");
        listCode.add("EUR");
        listCode.add("HUF");
        listCode.add("CHF");
        listCode.add("GBP");
        listCode.add("JPY");
        listCode.add("CZK");
        listCode.add("DKK");
        listCode.add("NOK");
        listCode.add("SEK");
        listCode.add("XDR");

        ArrayList<String> listDiff = new ArrayList<>();
        listDiff.add("0,0776");
        listDiff.add("0,0588");
        listDiff.add("0,0596");
        listDiff.add("0,0868");
        listDiff.add("0,0003");
        listDiff.add("0,0792");
        listDiff.add("0,1016");
        listDiff.add("0,0008");
        listDiff.add("0,0032");
        listDiff.add("0,0116");
        listDiff.add("0,0094");
        listDiff.add("0,0092");
        listDiff.add("0,1078");

        d.getDiff(diffUrl);

        for (int i=0; i<d.diffrences.size(); i++){
            assertEquals(listCode.get(i),d.diffrences.get(i).code);
            assertEquals(listDiff.get(i),decimalFormat.format(d.diffrences.get(i).diff));
        }
    }

    private ActualGoldAndCurrPrice ap = new ActualGoldAndCurrPrice();
    private String gURL = "http://api.nbp.pl/api/cenyzlota/2016-01-08/?format=json";
    private String cURL = "http://api.nbp.pl/api/exchangerates/rates/a/usd/2016-01-08/?format=json";

    @Test
    public void getGoldTest() throws JSONException, IOException {
        ap.getGold(gURL);
        assertEquals(142.58, ap.gold.price);
    }

    @Test
    public void  getCurrTest() throws JSONException, IOException {
        ap.getCurr(cURL);
        assertEquals(3.9963, ap.curr.mid);
    }

    private LowestCurrPrice lw = new LowestCurrPrice();
    private String tableCUrl = "http://api.nbp.pl/api/exchangerates/tables/c/2016-08-30/?format=json";

    @Test
    public void getLowestCurrTest() throws IOException {
        lw.getLowestCurr(tableCUrl);
        assertEquals(0.013909,lw.lowestPrice);
    }

    private DiffSellBuyCurrPrice ds = new DiffSellBuyCurrPrice();
    @Test
    public void getDiffTest() throws IOException{
        ds.getDiff(tableCUrl);
        String val0 = "USD 0,0776";
        assertEquals(val0,ds.diffrences.get(0).code+" "+decimalFormat.format(ds.diffrences.get(0).diff));
    }

    private LowestHighestCurrPrice h = new LowestHighestCurrPrice();
    private String urlLH = "http://api.nbp.pl/api/exchangerates/rates/a/xdr/2002-01-02/2002-01-31/?format=json";
    @Test
    public void getDateTest() throws IOException{
        download.makeJSON(download.getJsonText(urlLH));
        h.getDate(download.json);
        assertEquals("2002-01-07",h.lowest.date.toString());
        assertEquals("2002-01-18",h.highest.date.toString());
    }

    private Diagram diagram = new Diagram();
    @Test
    public void diagramTest() throws IOException {
        download.makeJSON(download.getJsonText("http://api.nbp.pl/api/exchangerates/rates/a/gbp/2012-01-02/?format=json"));
        diagram.getPrices(download.json);
        diagram.addDay();
        assertEquals(5.3480,diagram.prices.get(0).mid);
        assertEquals(LocalDate.parse("2012-01-02").getDayOfWeek(),diagram.pricesWithDays.get(0).day);
    }

    private CurrAmplitude amplitude = new CurrAmplitude();
    @Test
    public void getAmplitudeTest() throws Exception{
        download.makeJSON(download.getJsonText("http://api.nbp.pl/api/exchangerates/tables/a/2012-01-01/2012-01-31/?format=json"));
        amplitude.getAmplitude(download.jsonArray);
        amplitude.makeAmplitude();
        assertEquals("XDR",amplitude.amplitudes.get(36).code);
        assertEquals("0,4253",decimalFormat.format(amplitude.amplitudes.get(36).amplitude));
    }

    private AverageGoldPrice average = new AverageGoldPrice();
    @Test
    public void getAverageTest() throws IOException{
        download.makeJSON(download.getJsonText("http://api.nbp.pl/api/cenyzlota/2013-01-01/2013-01-04/?format=json"));
        average.getAverage(download.jsonArray);
        assertEquals("166,7433",decimalFormat.format(average.averageGoldPrice).toString());
    }

    Call call = new Call("http://api.nbp.pl/api/cenyzlota/2013-01-01/2013-01-04/?format=json");
    private AverageGoldPrice avg = new AverageGoldPrice();
    @Test
    public void callTest() throws IOException{
           avg.getAverage(call.call2());
           assertEquals("166,7433",decimalFormat.format(avg.averageGoldPrice).toString());
    }
}
