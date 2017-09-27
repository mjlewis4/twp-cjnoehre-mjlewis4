import org.junit.Assert;
import org.junit.Test;

public class PageOfRevisionsTest {

    @Test
    public void testRedirection(){
        WikipediaPageParser parser = new WikipediaPageParser();
        PageOfRevisions page = parser.parseJsonFile("Soup", "4");
        Assert.assertEquals("soup", page.isRedirected());
    }

    @Test
    public void testPageNotFound(){
        WikipediaPageParser parser = new WikipediaPageParser();
        PageOfRevisions page = parser.parseJsonFile("aksjdfkldas", "2");
        Assert.assertEquals(false, page.isPageFound());
    }
}
