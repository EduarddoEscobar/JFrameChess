import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;

public class Tile extends JPanel{

    //Image Variables\\
    private int size, x, y, timesMoved;
    private Color backgroundColor, pieceColor;
    private Piece p = Piece.Blank;
    public int[] position = new int[2];
    public boolean hovered = false, chosen = false;

    private Image img;
    private Graphics dbg;

    public Tile(int size, int x, int y, Color backgroundColor, Color pieceColor, int[] position){
        this.size = size;
        this.y = y;
        this.x = x;
        this.position = position;
        this.timesMoved = 0;
        this.backgroundColor = backgroundColor;
        this.pieceColor = pieceColor;
    }

    public void reset(){
        this.timesMoved = 0;
        this.pieceColor = Color.RED;
        this.p = Piece.Blank;
    }

    public void change(Tile t){
        this.timesMoved = t.timesMoved;
        this.pieceColor = t.pieceColor;
        this.p = t.p;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getLength(){
        return size;
    }

    public int getTimesMoved(){
        return timesMoved;
    }
    public void moved(){
        timesMoved++;
    }

    public Color getColor(){
        return pieceColor;
    }

    public void setColor(Color c){
        pieceColor = c;
    }

    public void setPiece(Piece p){
        this.p = p;
    }

    public Piece getPiece(){
        return p;
    }

    public void paint(Graphics g){
        img = createImage(getWidth(), getHeight());
        dbg = img.getGraphics();
        draw(dbg);
        g.drawImage(img, 0, 0, this);
    }

    public void draw(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Fills background of the tile\\
        if(hovered || chosen)
            g.setColor(Color.GREEN);
        else
            g.setColor(backgroundColor);
        g.fillRect(x,y,size,size);

        Path2D path = new Path2D.Double();
        //Draws the actual piece\\
        switch(p){
            case Pawn:
                //Triangle / Base\\
                path.moveTo(x + (size / 20), y + size);
                path.lineTo(x + size - (size / 20), y + size);
                path.lineTo(x + (size / 2), y + (size / 2) + (size / 20));
                path.closePath();
                g2.setColor(pieceColor);
                g2.fill(path);
                //Dot / Top\\
                g.setColor(pieceColor);
                g.fillOval(x + (size / 4), y + (size / 5), (size / 2), (size / 2));
                break;
            case Rook:
                g.setColor(pieceColor);
                //Base\\
                g.fillRect(x + (size / 20), y + size - (size / 20), size - (2 * size / 20), (size / 20));
                //Trunk\\
                g.fillRect(x + (size / 4), y + (size / 4), size - (2 * size / 4), size - (size / 4));
                //Top Base\\
                g.fillRect(x + (size / 8), y + (size / 5), size - (2 * size / 8), (size / 10));
                //Top Left Tower\\
                g.fillRect(x + (size / 8), y + (size / 6), (size - (2 * size / 10)) / 6, (size / 7));
                //Top Middle Tower\\
                g.fillRect(x + (size / 8) + ((size - (2 * size / 10)) / 4) + ((size - (2 * size / 10)) / 7), y + (size / 6), (size - (2 * size / 10)) / 6, (size / 6));
                //Top Right Tower\\
                g.fillRect(x + (size / 8) + (2 * (size - (2 * size / 10)) / 4) + (2 * (size - (2 * size / 10)) / 7), y + (size / 6), (size - (2 * size / 10)) / 6, (size / 6));
                break;
            case Bishop:
                g.setColor(pieceColor);
                //Base\\
                g.fillRect(x + (size / 10), y + size - (size / 20), size - (2 * size / 10), (size / 20));
                //Trunk\\
                g.fillRect(x + (size / 3), y + (size / 3), size - (2 * size / 3), size - (size / 3));
                //Tip / Top\\
                path.moveTo(x + (size / 10), y + (size / 3));
                path.lineTo(x + (size / 10) + (size - (2 * size / 10)), y + (size / 3));
                path.lineTo((x + ((size / 10) + size - (2 * size / 10)) / 2), y);
                path.closePath();
                g2.setColor(pieceColor);
                g2.fill(path);
                break;
            case Knight:
                //Base\\
                path.moveTo(x + (size / 20), y + size);//Bottom Left of triangle
                path.lineTo(x + (size / 20) + (size - (2 * size / 20)), y + size);//Bottom Right of triangle
                path.lineTo(x + (size / 20) + (size - (2 * size / 20)), y + size - (size / 20));//Right of triangle base
                path.lineTo(x + (size / 4), y + (size / 2));//Top Left of triangle
                //Head\\
                path.lineTo(x + (size / 15) + (size - (2 * size / 15)), y + (size / 2) + (size / 5));
                path.lineTo(x + (size / 20) + (size - (2 * size / 20)), y + (size / 3));
                path.lineTo(x + (size / 20) + (size - (2 * size / 20)), y + (size / 3));
                path.lineTo(x + (size / 5), y + (size / 10));
                path.lineTo(x + (size / 20), y + (size / 10));
                path.closePath();
                g2.setColor(pieceColor);
                g2.fill(path);
                break;
            case Queen:
                g.setColor(pieceColor);
                //Base\\
                g.fillRect(x + (size / 20), y + size - (size / 20), size - (2 * size / 20), (size / 20));
                //Trunk\\
                g.fillRect(x + (size / 4), y + (size / 4), size - (2 * size / 4), size - (size / 4));
                //Top Base\\
                g.fillRect(x + (size / 5), y + (size / 5), size - (2 * size / 5), (size / 10));
                //Top Crown Thing\\
                g.fillOval(x + ((size - (2 * size / 5)) / 2), y + (size / 20), (size - (2 * size / 14)) / 2, (size - (2 * size / 9)) / 2);
                break;
            case King:
                g.setColor(pieceColor);
                //Base\\
                g.fillRect(x + (size / 20), y + size - (size / 20), size - (2 * size / 20), (size / 20));
                //Trunk\\
                g.fillRect(x + (size / 4), y + (size / 3), size - (2 * size / 4), size - (size / 3));
                //Top Base\\
                g.fillRect(x + (size / 5), y + (size / 4), size - (2 * size / 5), (size / 8));
                //Top Cross\\
                g.fillRect(x + (size / 8) + ((size - (2 * size / 5)) / 2), y + (size / 15), size / 6, size / 5);
                g.fillRect(x + (size / 14) + ((size - (2 * size / 5)) / 2), y + (size / 10), size / 3, size / 8);
                break;
            default:
                break;
        }

        repaint();
    }

    public String toString(){
        return "The piece color is: " + pieceColor;
    }

}