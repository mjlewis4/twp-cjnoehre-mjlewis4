import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.org.apache.regexp.internal.RE;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.net.URLEncoder.encode;

public class WikipediaPageParser {

    JsonArray array = null;
    InputStream inputStream;

    public PageOfRevisions parseJsonFile(String searchTitle, String revisionAmount) {
        try {
            searchTitle = encode(searchTitle, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URL url = null;
        try {
            url = new URL("https:");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URL url = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject query = rootObject.getAsJsonObject("query");
        String pageName = searchTitle;

        List<User> usernameList = new ArrayList<>();

        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");

        Boolean pageFound = true;
        for (Map.Entry<String, JsonElement> etnry : pages.entrySet()) {
            if (entry.getKey().equals("-1")) {
                pageFound = false;
            }
            JsonObject entryObject = entry.getValue().getAsJsonObject();
            array = entryObject.getAsJsonArray("revisions");
        }
        String redirectedAfter = pageName;
        String redirectedBefore = pageName;
        if (query.has("redirects")) {
            JsonArray redirected = query.getAsJsonArray("redirects");
            redirectedAfter = redirected.get(0).getAsJsonObject().get("to").getAsString();
            redirectedBefore = redirected.get(0).getAsJsonObject().get("from").getAsString();
        }

        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                String username = array.get(i).getAsJsonObject().get("user").getAsString();
                User user = new User(username);
                usernameList.add(user);

                String timestamp = array.get(i).getAsJsonObject().get("timestamp").getAsString();
                Revision revision = new Revision(timestamp);
                user.addRevision(revision);
            }
        }
        PageOfRevisions page = new PageOfRevisions(pageName, redirectedAfter, redirectedBefore, pageFound);

        page.searchSameUser(usernameList);

        return page;
    }

    public boolean isConnected() {
        if (inputStream == null) {
            return false;
        }
        return true;
    }

    public boolean isEmpty() {
        if (array == null) {
            return true;
        }
        return false;
    }

}