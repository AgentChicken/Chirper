import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MStev on 11/8/2016.
 */
public class UserView {
    private JTextArea userTextArea;
    private JTextField followTextField;
    private JButton followButton;
    private JTextArea timelineArea;
    private JList followingList;
    private JTextArea newChirpArea;
    private JButton chirpButton;
    private JButton refreshButton;
    private JPanel userViewPanel;

    JPanel getUserViewPanel() {
        return userViewPanel;
    }

    UserView(final User user) {
        user.follow(user.getId());
        //get a list of users and display them in a list
        followingList.setListData(user.getFollowing().toArray());
        userTextArea.setText(user.getId()); //display the id of the user
        //follow the user whose ID was entered into the neighboring text area
        //update the user list
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                user.follow(followTextField.getText());
                followingList.setListData(user.getFollowing().toArray());
            }
        });
        //make a chirp and update the list of chirps, just like Twitter
        chirpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(newChirpArea.getText() != null)
                {
                    user.chirp(newChirpArea.getText());

                    String result = "";

                    for(Chirp chirp : user.getTimeline())
                    {
                        result += chirp.getId() + ": " + chirp.getText() + "\n";
                    }

                    timelineArea.setText(result);
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
