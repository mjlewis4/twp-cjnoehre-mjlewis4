import com.google.common.collect.HashMultiset;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;



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

    public void searchSameUser(List<User> usernames) {
        HashMultiset<String> revisionCount = HashMultiset.create();
        for (User user : usernames) {
            revisionCount.add(user.getUsername());
        }

        for (int i = 0; i < usernames.size(); i++) {
            for (int j = i+1; j < usernames.size(); j++) {
                int firstCount = revisionCount.count(usernames.get(i).getUsername());
                int secondCount = revisionCount.count(usernames.get(j).getUsername());
                if (secondCount > firstCount) {
                    User firstUser = usernames.get(i);
                    User secondUser = usernames.get(j);
                    usernames.set(i, secondUser);
                    usernames.set(j, firstUser);
                } else if (secondCount == firstCount) {
                    if (secondCount > 1) {
                        String firstTimeStamp = usernames.get(i).getRevisionList().get(0).getTimestamp();
                        String secondTimeStamp = usernames.get(j).getRevisionList().get(0).getTimestamp();
                        if (firstTimeStamp.equals(secondTimeStamp)) {
                            User firstUser = usernames.get(i);
                            User secondUser = usernames.get(j);
                            usernames.set(i, secondUser);
                            firstUser.getRevisionList().add(secondUser.getRevisionList().get(0));
                            usernames.set(j, firstUser);

                        }
                    }
                }
            }
        }
        for (User user : usernames) {
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


