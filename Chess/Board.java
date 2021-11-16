import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Board extends JPanel{
    public static final int BOARD_SIZE = 8;
    private int tileSize, tileGapeSize, x, y;

    private Piece[] order = new Piece[]{Piece.Rook, Piece.Knight, Piece.Bishop, Piece.Queen, Piece.King, Piece.Bishop, Piece.Knight, Piece.Rook};
    public static Tile[][] board = new Tile[8][8];


    private Image img;
    private Graphics dbg;

    public Board(int tileSize, int tileGapeSize, int x, int y){
        this.tileSize = tileSize;
        this.tileGapeSize = tileGapeSize;
        this.x = x;
        this.y = y;
        setUpBoard();
    }

    public void setUpBoard(){
        int count = 0;
        Color bg;
        for(int y = 0;y < board.length;y++) {
            for(int x = 0;x<board[0].length;x++){
                if(count % 2 == 0)
                    bg = new Color(230, 215, 210);
                else
                    bg = new Color(176, 130, 103);

                switch(y){
                    case 0:
                        board[y][x] = new Tile(tileSize, this.x + (x * tileSize) + (x * tileGapeSize), this.y + (y * tileSize) + (y * tileGapeSize), bg, Color.BLACK, new int[]{y,x});
                        board[y][x].setPiece(order[x]);
                        break;
                    case 1:
                        board[y][x] = new Tile(tileSize, this.x + (x * tileSize) + (x * tileGapeSize), this.y + (y * tileSize) + (y * tileGapeSize), bg, Color.BLACK, new int[]{y,x});
                        board[y][x].setPiece(Piece.Pawn);
                        break;
                    case 7:
                        board[y][x] = new Tile(tileSize, this.x + (x * tileSize) + (x * tileGapeSize), this.y + (y * tileSize) + (y * tileGapeSize), bg, Color.WHITE, new int[]{y,x});
                        board[y][x].setPiece(order[x]);
                        break;
                    case 6:
                        board[y][x] = new Tile(tileSize, this.x + (x * tileSize) + (x * tileGapeSize), this.y + (y * tileSize) + (y * tileGapeSize), bg, Color.WHITE, new int[]{y,x});
                        board[y][x].setPiece(Piece.Pawn);
                        break;
                    default:
                        board[y][x] = new Tile(tileSize, this.x + (x * tileSize) + (x * tileGapeSize), this.y + (y * tileSize) + (y * tileGapeSize), bg, Color.RED, new int[]{y,x});
                        board[y][x].setPiece(Piece.Blank);
                        break;
                }

                count++;
            }
            count++;
        }
    }

    public static Tile get(int[] location){
        return board[location[0]][location[1]];
    }

    public static void set(int[] location, Piece piece, Color color){
        board[location[0]][location[1]].setPiece(piece);
        board[location[0]][location[1]].setColor(color);
    }

    public void paint(Graphics g){
        img = createImage(getWidth(), getHeight());
        dbg = img.getGraphics();
        draw(dbg);
        g.drawImage(img, 0, 0, this);
    }

    public void draw(Graphics g){
        super.paintComponent(g);
        for(Tile[] t: board){
            for(Tile f: t) {
                f.draw(g);
            }
        }
        repaint();
    }
}