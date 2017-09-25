import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

public class PageOfRevisions {
    public ArrayList<User> userList = new ArrayList();
    public String name;
    public ArrayList<User> usernameList;

    public PageOfRevisions(String name) { this.name = name; }

    public void searchSameUser(ArrayList<User> usernameList) {
        this.usernameList = usernameList;
        int count = 0;
        for(int i = 0; i < usernameList.size(); i++) {
            for(int j = i + 1; j < usernameList.size(); j++){
                //System.out.println("Size of List: " + usernameList.size());
                User firstUser = usernameList.get(i);
                User secondUser = usernameList.get(j);
                if(firstUser.getName().equals(secondUser.getName())){
                    count++;
                    System.out.println(firstUser.getName());
                    System.out.println(secondUser.getName());
                    firstUser.revisionList.add(secondUser.getRevisions().get(0));
                    //System.out.println(secondUser.getRevisions().get(0).getTimestamp());
                    usernameList.remove(secondUser);
                }
            }
        }
        for (User user: usernameList){
            System.out.println("username: " + user.getName());
        }
    }

    public int getUserNameSizeBefore() { return usernameList.size();}

    public int getUserNameSizeAfter() { return userList.size(); }

    public ArrayList<User> getUserList() { return usernameList; }
}


