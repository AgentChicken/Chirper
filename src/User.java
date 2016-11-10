import java.util.ArrayList;

/**
 * Created by MStev on 11/1/2016.
 */

class User implements Observer
{
    private ArrayList<String> following = new ArrayList<String>();
    private ArrayList<String> followers = new ArrayList<String>();
    private ArrayList<Chirp> timeline = new ArrayList<Chirp>();
    private String id;

    ArrayList<String> getFollowing()
    {
        return following;
    }
    public ArrayList<String> getFollowers()
    {
        return followers;
    }
    ArrayList<Chirp> getTimeline()
    {
        return timeline;
    }
    String getId()
    {
        return this.id;
    }

    User(String id)
    {
        this.id = id;
        Ledger.getInstance().attach(this);
    }

    //add a new chirp to the timeline
    private void addChirp(Chirp chirp)
    {
        this.timeline.add(chirp);
    }

    //follow someone
    boolean follow(String id)
    {
        for(int i = 0; i < Group.getUserMaster().size(); i++)
        {
            if(id.equals(Group.getUserMaster().get(i).getId()))
            {
                following.add(Group.getUserMaster().get(i).getId());
                Group.getUserMaster().get(i).addFollower(this.id);
                return true;
            }
        }
        return false;
    }

    //add a new user to a list of followers
    private void addFollower(String id)
    {
        followers.add(id);
    }

    //if the new chirp was made by someone this was following, that chirp is added to this.timeline
    public void update(Subject subject) {
        if(following.contains(Ledger.getInstance().getNewChirp().getId()))
        {
            this.timeline.add(Ledger.getInstance().getNewChirp());
        }
    }

    //submit a chirp
    void chirp(String text)
    {
        Chirp chirp = new Chirp (this.id, text);
        Ledger.getInstance().addChirp(chirp);
    }

    //unused method; here for future improvement
    int positiveTimelineKeywords()
    {
        ChirpAnalysisVisitor chirpAnalysisVisitor = new ChirpAnalysisVisitor();
        for(Chirp chirp : timeline)
        {
            chirp.accept(chirpAnalysisVisitor);
        }
        return chirpAnalysisVisitor.getPositiveWordCount();
    }
}
