
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import projekt2.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;

import static junit.framework.Assert.assertEquals;

public class JunitTest {

    private Download download = new Download();
    private String url = "http://api.nbp.pl/api/exchangerates/rates/c/usd/2016-04-04/?format=json";
    private String jsonTextTest = "{\"table\":\"C\",\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"rates\":[{\"no\":\"064/C/NBP/2016\",\"effectiveDate\":\"2016-04-04\",\"bid\":3.6929,\"ask\":3.7675}]}";
    private String jsonTest = new JSONObject(jsonTextTest).toString();

    @Test
    public void getJsonTextTest() throws IOException {
        String jsonText = download.getJsonText(url);
        assertEquals(jsonTextTest, jsonText);
    }

    @Test
    public void makeJsonTest() throws JSONException{
        download.makeJSON(jsonTextTest);
        String json = download.json.toString();
        assertEquals(jsonTest,json);
    }

    private CommandLineParser command = new CommandLineParser();
    String[] args = new String[5];
    @Test
    public void getCommandLineTest() throws Exception{
        args[0]="-gC";
        args[1]="-c";
        args[2]="GBP";
        args[3]="-date";
        args[4]="2016-07-01";
        command.getValues(args);
        command.urlMaker();
        assertEquals("2016-07-01",command.date.toString());
        assertEquals("GBP",command.code.toString());
    }

}