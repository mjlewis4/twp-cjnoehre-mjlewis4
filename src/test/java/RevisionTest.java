import org.junit.Test;

import java.io.IOException;

public class RevisionTest {

    @Test
    public void firstUserKorveto() throws IOException {
        Parser parser = new Parser();
        parser.parseJsonFile("Soup", 4);
    }
}
