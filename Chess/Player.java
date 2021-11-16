import java.util.ArrayList;

public class Player {

    public ArrayList<Piece> piecesCaptured = new ArrayList<Piece>();

    public int getScore(){
        int count = 0;
        for(Piece p: piecesCaptured){
            count += p.getValue();
        }
        return count;
    }
}