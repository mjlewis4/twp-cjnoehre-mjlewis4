import com.google.gson.JsonArray;
import java.util.ArrayList;

public class User {

    public ArrayList<Revision> revisionList = new ArrayList();
    public String username;

    public Revision getRevision(String searchName) { return revisionList.get(0); }

    public User(String username) {
        this.username = username;
        //System.out.println(username);
    }

    public void addRevision(Revision revision) { revisionList.add(revision); }

    public ArrayList<Revision> getRevisions() { return this.revisionList; }

    public String getName() { return this.username; }
}
