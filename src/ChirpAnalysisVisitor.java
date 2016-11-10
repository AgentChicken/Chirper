import java.util.ArrayList;

/**
 * Created by MStev on 11/7/2016.
 */
class ChirpAnalysisVisitor implements Visitor {
    private int positiveWordCount = 0;

    int getPositiveWordCount() {
        return positiveWordCount;
    }

    //analyses chirps for positive words
    @Override
    public void visit(Chirp chirp) {
        ArrayList<String> positiveWords = new ArrayList<>();
        positiveWords.add("good");
        positiveWords.add("great");
        positiveWords.add("spectacular");
        positiveWords.add("wonderful");
        positiveWords.add("amazing");
        positiveWords.add("nice");
        for (String keyword : positiveWords) {
            if (chirp.getText().contains(keyword)) positiveWordCount++;
        }
    }
}
