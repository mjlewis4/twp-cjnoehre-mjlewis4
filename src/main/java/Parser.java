import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import static java.net.URLEncoder.encode;

public class Parser {

    JsonArray array = null;
    InputStream inputStream;
    public PageOfRevisions pageOfRevisions;

    public PageOfRevisions parseJsonFile(String searchTitle, int revisionAmount) throws IOException {
        searchTitle = encode(searchTitle, "UTF-8");
        URL url = new URL("https;//en.wikipeida.org/w/api.php?action=query&format=json&prop=revisions&titles=" + searchTitle + "&rvprop=timestamp|user&rvlimit=" + revisionAmount + "&redirects");
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Revision Tracker/0.1 (ttp://www.cs.bsu.edu/~pvg/courses/cs222Fa17; me@bsu.edu)");
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

        inputStream = connection.getInputStream();
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        String pageName = null;
        try {
            JsonObject rootObject = null;
            JsonObject pages = rootObject.getAsJsonObject("query").getAsJsonObject("pages");
            rootObject = rootElement.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : pages.entrySet()) {
                JsonObject entryObject = entry.getValue().getAsJsonObject();
                array = entryObject.getAsJsonArray("revisions");
                pageName = entryObject.get("title").getAsString();
            }

            PageOfRevisions pageOfRevision = new PageOfRevisions(pageName);
            ArrayList<User> usernameList = new ArrayList<>();

            for (int i = 0; i < array.size(); i++) {
                String username = array.get(i).getAsJsonObject().get("user").getAsString();
                User user = new User(username);
                usernameList.add(user);

                String timestamp = array.get(i).getAsJsonObject().get("timestamp").getAsString();
                Revision revision = new Revision(timestamp);

                user.addRevision(revision);
            }

            pageOfRevisions.searchSameUser(usernameList);
            return pageOfRevision;
        }catch (Exception e) {
            return pageOfRevisions;
        }
    }

    public boolean isConnected() {
        if(inputStream == null) {
            return false;
        }

        return true;
    }

    public boolean isEmpty() {
        if(array == null){
            return true;
        }

        return false;
    }

}
