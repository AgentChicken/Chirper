import java.util.ArrayList;

/**
 * Created by MStev on 11/6/2016.
 */
class Chirp implements Visitable{
    private String text;
    private String id;

    String getId() {
        return id;
    }

    String getText()
    {
        return text;
    }

    Chirp(String id, String text)
    {
        this.id = id;
        this.text = text;
    }

    //lets a visitor analyse contents
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
