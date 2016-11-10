import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by MStev on 11/8/2016.
 */
public class UserView {
    private JTextField followTextField;
    private JButton followButton;
    private JTextArea timelineArea;
    private JList followingList;
    private JButton chirpButton;
    private JButton refreshButton;
    private JPanel userViewPanel;
    private JLabel userLabel;
    private JTextField newChirpTextField;
    private ArrayList<String> modifiedFollowingList;

    JPanel getUserViewPanel() {
        return userViewPanel;
    }

    UserView(final User user) {
        user.follow(user.getId());
        //get a list of users and display them in a list
        modifiedFollowingList = new ArrayList<>(user.getFollowing());
        modifiedFollowingList.remove(user.getId());
        followingList.setListData(modifiedFollowingList.toArray());
        userLabel.setText("@" + user.getId()); //display the id of the user
        //follow the user whose ID was entered into the neighboring text area
        //update the user list
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(followTextField.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Enter the name of a user.");
                    return;
                }
                user.follow(followTextField.getText());
                modifiedFollowingList = new ArrayList<>(user.getFollowing());
                modifiedFollowingList.remove(user.getId());
                followingList.setListData(modifiedFollowingList.toArray());
            }
        });
        //make a chirp and update the list of chirps, just like Twitter
        chirpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!newChirpTextField.getText().equals(""))
                {
                    user.chirp(newChirpTextField.getText());

                    String result = "";

                    for(Chirp chirp : user.getTimeline())
                    {
                        result += chirp.getId() + ": " + chirp.getText() + "\n";
                    }

                    timelineArea.setText(result);

                    newChirpTextField.setText("");
                } else
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Enter text to post a chirp.");
                }
            }
        });
        //get new chirps, just like Twitter
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String result = "";

                for(Chirp chirp : user.getTimeline())
                {
                    result += chirp.getId() + ": " + chirp.getText() + "\n";
                }

                timelineArea.setText(result);
            }
        });
    }
}
