import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
            url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" + searchTitle + "&rvprop=timestamp|user&rvlimit=" + revisionAmount + "&redirects");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("User-Agent", "Revision Tracker/0.1 (http://www.cs.bsu.edu/~pvg/courses/cs222Fa17; me@bsu.edu}");
        JsonParser parser = new JsonParser();
        try {
            inputStream = connection.getInputStream();
        }catch (IOException e) {
            e.printStackTrace();
        }
        JsonElement rootElement = parser.parse(new InputStreamReader(inputStream));
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
        String pageName = searchTitle;

        List<User> usernameList = new ArrayList<>();


        Boolean pageFound = true;
        for (Map.Entry<String, JsonElement> entry : pages.entrySet()) {
            if (entry.getKey().equals("-1")) {
                pageFound = false;
            }
            JsonObject entryObject = entry.getValue().getAsJsonObject();
            array = entryObject.getAsJsonArray("revisions");
        }
        String redirectedAfter = pageName;
        String redirectedBefore = pageName;
        if (pages.has("redirects")) {
            JsonArray redirected = pages.getAsJsonArray("redirects");
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