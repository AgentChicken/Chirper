import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.Objects;

/**
 * Created by MStev on 11/7/2016.
 */
public class Driver {
    private JButton showUserViewButton;
    private JPanel panelMain;
    private JTextField addUserTextField;
    private JButton addUserButton;
    private JTextField addGroupTextField;
    private JButton addGroupButton;
    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton showChirpTotalButton;
    private JButton showPositivityButton;
    private JTree userTree;

    private void updateUserTree()
    {
        System.out.println("in updateUserTree");
        DefaultTreeModel model = (DefaultTreeModel)userTree.getModel();
        model.setRoot(populate(Group.getRoot()));
        //this.userTree = new JTree(populate(Group.getRoot()));
    }

    private DefaultMutableTreeNode populate(Group group)
    {
        DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(group.getName());

        for(User user : group.getUsers())
        {
            defaultMutableTreeNode.add(new DefaultMutableTreeNode(user.getId()));
        }

        for(Group subgroup : group.getGroups())
        {
            defaultMutableTreeNode.add(populate(subgroup));
        }

        return defaultMutableTreeNode;
    }

    private Driver() {
        updateUserTree(); //replace example values with real ones straight away
        //these next four show data on how many users, groups, chirps, and positive chirps there are
        showUserTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(new JFrame(), Integer.toString(Group.getUserMaster().size()));
            }
        });
        showGroupTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(new JFrame(), Integer.toString(Group.getGroupMaster().size()));
            }
        });
        showChirpTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(new JFrame(), Integer.toString(Ledger.getInstance().getChirpArrayList().size()));
            }
        });
        showPositivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(new JFrame(), Integer.toString(Ledger.getInstance().positiveLedgerKeywords()) + " of " + Integer.toString(Ledger.getInstance().getChirpArrayList().size()) + " chirps contain positive messages.");
            }
        });
        //add a user
        //a parent group must be selected
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(addUserTextField.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a name for the user.");
                }
                else if(userTree.getLastSelectedPathComponent() == null)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Please select a parent group.");
                    return;
                }
                for(User user : Group.getUserMaster())
                {
                    if(user.getId().equals(addUserTextField.getText()))
                    {
                        JOptionPane.showMessageDialog(new JFrame(), "User already exists.");
                        return;
                    }
                }
                for(Group group : Group.getGroupMaster())
                {
                    if(group.getName().equals(addUserTextField.getText())){
                        JOptionPane.showMessageDialog(new JFrame(), "A group and a user can't share a name.");
                        return;
                    }
                }
                for(Group treeGroup : Group.getGroupMaster())
                {
                    if (Objects.equals(treeGroup.getName(), userTree.getLastSelectedPathComponent().toString()))
                    {
                        treeGroup.addUser(new User(addUserTextField.getText()));
                        break;
                    }
                }
                updateUserTree();
            }
        });
        //add a group
        //a parent group must be selected
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(addGroupTextField.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a name for the group.");
                    return;
                }
                else if(userTree.getLastSelectedPathComponent() == null)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Please select a parent group.");
                    return;
                }
                for(Group group : Group.getGroupMaster())
                {
                    if(group.getName().equals(addGroupTextField.getText())){
                        JOptionPane.showMessageDialog(new JFrame(), "Group already exists.");
                        return;
                    }
                }
                for(User user : Group.getUserMaster())
                {
                    if(user.getId().equals(addGroupTextField.getText()))
                    {
                        JOptionPane.showMessageDialog(new JFrame(), "A group and a user can't share a name.");
                        return;
                    }
                }
                for(Group treeGroup : Group.getGroupMaster())
                {
                    if (Objects.equals(treeGroup.getName(), userTree.getLastSelectedPathComponent().toString()))
                    {
                        treeGroup.addGroup(new Group(addGroupTextField.getText()));
                        break;
                    }
                }
                updateUserTree();
            }
        });
        //listen to which node is selected
        userTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                //for logging
                System.out.println(userTree.getLastSelectedPathComponent());
            }
        });
        //open the view of the selected user
        showUserViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(userTree.getLastSelectedPathComponent() == null)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Please select a user.");
                }
                for (User user : Group.getUserMaster())
                {
                    if(user.getId().equals(userTree.getLastSelectedPathComponent().toString()))
                    {
                        JFrame userFrame = new JFrame();
                        userFrame.setContentPane(new UserView(user).getUserViewPanel());
                        userFrame.setSize(400,400);
                        userFrame.setVisible(true);
                    }
                }
            }
        });
        /*userTree.addComponentListener(new ComponentAdapter() {
        });
        userTree.addComponentListener(new ComponentAdapter() {
        });*/
    }

    //this class was to be made as a Singleton, but it's easier and just as safe to put the main method inside
    //of the UI class (the Ledger class of chirps is a Singleton, however)
    //the main method creates the initial window
    public static void main(String[] args)
    {
        System.out.println(Group.getGroupMaster().get(0).getName());
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(new Driver().panelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600,400);
        jFrame.setVisible(true);
    }
}
