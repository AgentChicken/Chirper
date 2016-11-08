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

    private ArrayList<Chirp> chirpArrayList = new ArrayList<>();

    ArrayList<Chirp> getChirpArrayList() {
        return chirpArrayList;
    }

    private Chirp newChirp;

    Chirp getNewChirp() {
        return newChirp;
    }

    void addChirp(Chirp chirp)
    {
        chirpArrayList.add(chirp);
        newChirp = chirp;
        notifyObservers();
    }

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
