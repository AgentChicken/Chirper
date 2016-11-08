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
        updateUserTree();
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
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(Group.getGroupMaster().size());
                for(Group group : Group.getGroupMaster())
                {
                    System.out.println(Objects.equals(group.getName(), userTree.getLastSelectedPathComponent().toString()));
                    if (Objects.equals(group.getName(), userTree.getLastSelectedPathComponent().toString()))
                    {
                        System.out.println("Selected: " + userTree.getLastSelectedPathComponent());
                        group.addUser(new User(addUserTextField.getText()));
                        break;
                    }
                    System.out.println("user master size: "+Group.getUserMaster().size());
                }
                System.out.println("Hello");
                updateUserTree();
            }
        });
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for(Group group : Group.getGroupMaster())
                {
                    if (Objects.equals(group.getName(), userTree.getLastSelectedPathComponent().toString()))
                    {
                        group.addGroup(new Group(addGroupTextField.getText()));
                        break;
                    }
                }
                updateUserTree();
            }
        });
        userTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                System.out.println(userTree.getLastSelectedPathComponent());
            }
        });
        showUserViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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
        userTree.addComponentListener(new ComponentAdapter() {
        });
        userTree.addComponentListener(new ComponentAdapter() {
        });
    }

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
