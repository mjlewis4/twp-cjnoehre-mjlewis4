import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class URLConnectionTest {

    @Test
    public void testURLSoup() throws Exception {
        Parser jsonObject = new Parser();
        jsonObject.parseJsonFile("soup", 4);
        boolean result = jsonObject.isEmpty();
        Assert.assertEquals(false, result);
    }

    @Test
    public void testURLEmpty() throws Exception {
        Parser jsonObject = new Parser();
        jsonObject.parseJsonFile("Soup", 4);
        boolean result -jsonObject.isEmpty();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testURLInvalidText() throws Exception {
        Parser jsonObject = new Parser ();
        jsonObject.parse.JsonFile("SLKDJFF", 4);
        boolean result = jsonObject.isEmpty();
        Assert.assertEqualsIs(true, result);
    }

    @Test
    public void testNetworkConnection() throws Exception {
        Parser jsonObject = new Parser();
        jsonObject.parseJsonFile("Soup", 4);
        boolean result = jsonObject.isConnected();
        Assert.assertEquals(true, result);
    }
}