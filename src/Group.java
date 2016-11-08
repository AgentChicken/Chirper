import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by MStev on 11/6/2016.
 */

class Group {
    private static Group root = new Group("ROOT");

    String name;
    private static ArrayList<User> userMaster = new ArrayList<>();
    private static ArrayList<Group> groupMaster = new ArrayList<Group>() {{add(root);}};
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();

    String getName() {
        return name;
    }
    static Group getRoot() {
        return root;
    }
    static ArrayList<User> getUserMaster()
    {
        return userMaster;
    }
    static ArrayList<Group> getGroupMaster() {
        return groupMaster;
    }
    ArrayList<User> getUsers() {
        return users;
    }
    ArrayList<Group> getGroups() {
        return groups;
    }

    void addUser(User user)
    {
        this.users.add(user);
        userMaster.add(user);
    }
    void addGroup(Group group)
    {
        this.groups.add(group);
        groupMaster.add(group);
    }

    Group(String name)
    {
        this.name = name;
    }
}