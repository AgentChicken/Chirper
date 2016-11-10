import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

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
        //userLabel.setText("@" + user.getId()); //display the id of the user
        //follow the user whose ID was entered into the neighboring text area
        //update the user list
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(followTextField.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Enter the name of a user.");
                    return;
                } else if (followTextField.getText().equals(user.getId()))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "You already follow yourself.");
                    return;
                } else if (user.getFollowing().contains(followTextField.getText()))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "You already follow " + followTextField.getText() + ".");
                    return;
                }
                if(!user.follow(followTextField.getText()))
                {
                    JOptionPane.showMessageDialog(new JFrame(), "User not found.");
                    return;
                }
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

                    ArrayList<Chirp> formattedTimeline = user.getTimeline();
                    Collections.reverse(formattedTimeline);

                    for(Chirp chirp : formattedTimeline)
                    {
                        result += chirp.getId() + ": " + chirp.getText() + "\n";
                    }

                    timelineArea.setText(result);
                    timelineArea.setCaretPosition(0);

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

                ArrayList<Chirp> formattedTimeline = user.getTimeline();
                Collections.reverse(formattedTimeline);

                for(Chirp chirp : formattedTimeline)
                {
                    result += chirp.getId() + ": " + chirp.getText() + "\n";
                }

                timelineArea.setText(result);
                timelineArea.setCaretPosition(0);
            }
        });
    }
}
