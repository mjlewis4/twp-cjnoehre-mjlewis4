import com.google.gson.JsonArray;

import java.util.ArrayList;

public class PageOfRevisions {

    public ArrayList<Revision> revisionList = new ArrayList();
    public String name;

    public PageOfRevisions(String name) { this.name = name; }

    public void addRevision(Revision revision)  { revisionList.add(revision); }
}

