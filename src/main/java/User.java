import com.google.gson.JsonArray;
import java.util.ArrayList;

public class User {

    public ArrayList<Revision> revisionList = new ArrayList();
    public String username;
    public String revisions;

    public Revision getRevision(String searchName) { return revisionList.get(0); }

    public User(String username) { this.username = username; }

    public void addRevision(Revision revision) { revisionList.add(revision); }

    public String getRevisions(){
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (Revision revision : revisionList){
            if(count < 1) {
                builder.append(revision.getTimestamp());
                count++;
            }else{
                builder.append(" -- ");
                builder.append(revision.getTimestamp());
                count++;
            }
        }
        return builder.toString();
    }

    public String getUsername() { return this.username; }

    public ArrayList<Revision> getRevisionList() {
        return revisionList;
    }
}
