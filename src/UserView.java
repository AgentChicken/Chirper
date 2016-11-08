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
        followingList.setListData(user.getFollowing().toArray());
        userTextArea.setText(user.getId());
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                user.follow(followTextField.getText());
                followingList.setListData(user.getFollowing().toArray());
            }
        });
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
