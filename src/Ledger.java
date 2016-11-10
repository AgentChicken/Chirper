import java.util.ArrayList;

/**
 * Created by MStev on 11/2/2016.
 */
class Ledger extends Subject {
    private static final Ledger INSTANCE = new Ledger();
    private Ledger() {}
    static Ledger getInstance() {
        return INSTANCE;
    }

    //arraylist of all chirps
    private ArrayList<Chirp> chirpArrayList = new ArrayList<>();

    ArrayList<Chirp> getChirpArrayList() {
        return chirpArrayList;
    }

    private Chirp newChirp;

    Chirp getNewChirp() {
        return newChirp;
    }

    //adds a new chirp and notifies everyone that a new chirp has been made
    //the new chirp is stored in newChirp. If a user is following the person that
    //submitted the chirp, that chirp is added to the users timeline
    void addChirp(Chirp chirp)
    {
        chirpArrayList.add(chirp);
        newChirp = chirp;
        notifyObservers();
    }

    //lets visitor analyse contents
    int positiveLedgerKeywords()
    {
        ChirpAnalysisVisitor chirpAnalysisVisitor = new ChirpAnalysisVisitor();
        for(Chirp chirp : chirpArrayList)
        {
            chirp.accept(chirpAnalysisVisitor);
        }
        return chirpAnalysisVisitor.getPositiveWordCount();
    }
}
