import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.text.html.parser.Parser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;

public class URLConnectionTest {

    @Test
    public void testURLSoup() throws Exception {
        WikipediaPageParser jsonObject = new WikipediaPageParser();
        jsonObject.parseJsonFile("soup", "4");
        boolean result = jsonObject.isEmpty();
        Assert.assertEquals(false, result);
    }

    @Test
    public void testTimeZoneIsGreenwichMeanTime() throws ParseException {
        final Calendar calendar = javax.xml.bind.DatatypeConverter.parseDateTime("2010-04-05T17:16:00Z");
        Assert.assertEquals("gotten timezone" + "GMT+00:00", calendar.getTimeZone().getID());
    }

    @Test
    public void testURLInvalidText() throws Exception {
        WikipediaPageParser jsonObject = new WikipediaPageParser();
        jsonObject.parseJsonFile("SLKDJFF", "4");
        boolean result = jsonObject.isEmpty();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testNetworkConnection() throws Exception {
        WikipediaPageParser jsonObject = new WikipediaPageParser();
        jsonObject.parseJsonFile("Soup", "4");
        boolean result = jsonObject.isConnected();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testNetworkC3Connection() throws Exception {
        WikipediaPageParser jsonObject = new WikipediaPageParser();
        jsonObject.parseJsonFile("Soup", "4");
        boolean result = jsonObject.isConnected();
        Assert.assertEquals(true, result);
    }
}