import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public enum Piece {

    Blank(0),
    King(10),
    Queen(9),
    Rook(5),
    Bishop(3),
    Knight(3),
    Pawn(1);

    private final int value;

    Piece(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}