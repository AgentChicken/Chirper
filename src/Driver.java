import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private String selected;
    private boolean selectedIsGroup;

    private void updateUserTree()
    {
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
        userTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();

                if(node == null) {selected = "ROOT"; selectedIsGroup = true;}

                if(node.isLeaf())
                {
                    selected = node.toString();
                    selectedIsGroup = false;
                } else {
                    selected = node.toString();
                    selectedIsGroup = true;
                }
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(selectedIsGroup)
                {
                    for(Group group : Group.getGroupMaster())
                    {
                        if(group.getName().equals(selected))
                        {
                            group.addUser(new User(addUserTextField.getText()));
                        }
                    }
                } else {
                    for(Group group : Group.getGroupMaster())
                    {
                        for(User user : group.getUsers())
                        {
                            if(user.getId().equals(selected))
                            {
                                group.addUser(new User(addUserTextField.getText()));
                            }
                        }
                    }
                }
            }
        });
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(selectedIsGroup)
                {
                    for(Group group : Group.getGroupMaster())
                    {
                        if(group.getName().equals(selected))
                        {
                            group.addGroup(new Group(addGroupTextField.getText()));
                        }
                    }
                } else {
                    for(Group group : Group.getGroupMaster())
                    {
                        for(User user : group.getUsers())
                        {
                            if(user.getId().equals(selected))
                            {
                                group.addGroup(new Group(addGroupTextField.getText()));
                            }
                        }
                    }
                }
            }
        });
        showUserViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(selectedIsGroup)
                {
                    return;
                } else {
                    for(User user : Group.getUserMaster())
                    {
                        if(user.getId().equals(selected))
                        {
                            JFrame userFrame = new JFrame();
                            userFrame.setContentPane(new UserView(user).getUserViewPanel());
                            userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            userFrame.setSize(400,400);
                            userFrame.setVisible(true);
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(new Driver().panelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400,400);
        jFrame.setVisible(true);
    }
}
