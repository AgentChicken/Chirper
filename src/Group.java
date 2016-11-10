import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by MStev on 11/6/2016.
 */

class Group {
    //initial root group
    private static Group root = new Group("ROOT");

    private String name;
    //master list of all users
    private static ArrayList<User> userMaster = new ArrayList<>();
    //master list of all groups; initially contains the root group
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

    //add a new user to the group
    void addUser(User user)
    {
        this.users.add(user);
        userMaster.add(user);
    }

    //add a new group to the group
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