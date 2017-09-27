import com.google.gson.JsonArray;
import java.security.Timestamp;
import java.util.*;
import java.time.ZonedDateTime;

public class PageOfRevisions {
    public String name;
    public String redirectedAfter;
    public String redirectedBefore;
    public boolean isNotFound;
    public ArrayList<User> usernameList= new ArrayList();

    public PageOfRevisions(String name, String redirectedAfter, String redirectedBefore, boolean isNotFound) {
        this.redirectedAfter = redirectedAfter;
        this.redirectedBefore = redirectedBefore;
        this.name = name;
        this.isNotFound = isNotFound;
    }

    public void searchSameUser(List<User> username) {
        HashMultiset<String> revisionCount = HashMultiset.create();
        for (User user : username) {
            revisionCount.add(user.getUsername());
        }

        for (int i = 0; i < usernameList.size(); i++) {
            for (int j = i + 1; j < username.size(); j++) {
                int firstCount = revisionCount.count(username.get(i).getUsername());
                int secondCount = revisionCount.count(username.get(i).getUsername());
                if (secondCount > firstCount) {
                    User firstUser = username.get(i);
                    User secondUser = username.get(j);
                    username.set(i, secondUser);
                    username.set(j, firstUser);
                } else if (secondCount == firstCount) {
                    if (secondCount > 1) {
                        Timestamp firstTimeStamp = Timestamp.valueOf(username.get(i).getRevisionList().get(0).getTimestamp());
                        Timestamp secondTimeStamp = Timestamp.valueOf(username.get(j).getRevisionList().get(0).getTimestamp());
                        if (firstTimeStamp.after(secondTimeStamp)) {
                            User firstUser = username.get(i);
                            User secondUser = username.get(j);
                            username.set(i, secondUser);
                            username.set(j, firstUser);
                            firstUser.getRevisionList().add(secondUser.getRevisionList().get(0));
                        }
                    }
                }
            }
        }
        for (User user : username) {
            usernameList.add(user);
        }
    }

    public String isRedirected(){
        if(redirectedBefore.equals(redirectedAfter)) {
            return redirectedBefore;
        }else{
            return redirectedBefore + " changed to " + redirectedAfter;
        }
    }

    public List <User> getUserList() { return usernameList;}

    public boolean isPageFound() { return isNotFound; }
}


