import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

//Needs to extend JPanel so that the other classes can also draw graphics
public class Run extends JPanel{

    public static final int WIDTH = 700, HEIGHT = 700;
    JFrame f;
    HashMap<Integer, Color> turnChecker= new HashMap<Integer, Color>();

    //Highlight and selection variables\\
    int player = 1, selected = 0;
    int[] start = new int[2], end = new int[2];

    //Board and gameplay variables\\
    Board b = new Board(60, 5, 80, 80);
    boolean gameGoing = false, startButtonHovered, endButtonHovered, invalidMove, resetHovered, player1Won = false, player2Won = false;//DON'T FORGET TO MAKE THIS FALSE WHILE MAKING THE TITLE SCREEN
    Player p1 = new Player(), p2 = new Player();
    /*
     * How Double Buffered Graphics work
     * You write all images to an offscreen image
     * Then you copy the buffered contents to the screen all at once
     */
    private Image img;
    private Graphics dbg;


    public void paint(Graphics g){
        //Creates the image with all needed information
        img = createImage(getWidth(), getHeight());
        //Gets the buffered content
        dbg = img.getGraphics();
        //Draws the buffered content to the screen
        draw(dbg);
        //Buffers the image once more for smoothness
        g.drawImage(img, 0, 0, this);
    }

    public void draw(Graphics g){
        super.paintComponent(g);
        this.setBackground(new Color(57,57,57));

        if(!gameGoing && !player1Won && !player2Won){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.drawString("Chess", 300,100);
            if(startButtonHovered)
                g.setColor(Color.MAGENTA);
            else
                g.setColor(Color.CYAN);
            g.fillRect(250,500,150, 50);
            g.setColor(Color.WHITE);
            g.drawString("Start!", 290, 530);
        }

        if(gameGoing){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 26));
            //Demarcates board\\
            g.drawString("A        B      C       D       E       F       G       H", 75,50);
            for(int i=1;i<9;i++)
                g.drawString(i + "", 50, 30 + (70 * i));
            //Invalid Move Text\\
            if(invalidMove)
                g.drawString("Invalid Move Try Again!", 200, 650);

            //Reset Button\\
            if(resetHovered)
                g.setColor(Color.MAGENTA);
            else
                g.setColor(Color.CYAN);
            g.fillRect(75, 600, 75, 30);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Reset", 85, 620);

            b.draw(g);
        }

        if(player2Won || player1Won){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 26));
            if(player1Won)
                g.drawString("White side has won", 200,100);
            if(player2Won)
                g.drawString("Black side has won", 200,100);
            if(endButtonHovered)
                g.setColor(Color.MAGENTA);
            else
                g.setColor(Color.CYAN);
            g.fillRect(250,500,150, 50);
            g.setColor(Color.WHITE);
            g.drawString("End!", 290, 530);
        }

        repaint();
    }

    //For Running the application
    public Run(){
        turnChecker.put(1, Color.WHITE);
        turnChecker.put(2, Color.BLACK);
        f = new JFrame("Chess");
        Handler handler = new Handler();
        f.add(this);
        f.addMouseListener(handler);
        f.addMouseMotionListener(handler);
        f.setSize(WIDTH,HEIGHT);
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]){Run r = new Run();}

    private class Handler implements MouseMotionListener, MouseListener{

        @Override
        public void mouseMoved(MouseEvent e) {
            if(!gameGoing){
                if(e.getX() > 250 && e.getX() < 400 && e.getY() > 525 && e.getY() < 575)
                    startButtonHovered = true;
                else
                    startButtonHovered = false;
            }
            if(player1Won || player2Won){
                if(e.getX() > 250 && e.getX() < 400 && e.getY() > 525 && e.getY() < 575)
                    endButtonHovered = true;
                else
                    endButtonHovered = false;
            }

            if(gameGoing) {
                for (Tile[] ts : Board.board) {
                    for (Tile t : ts) {
                        if ((e.getX() > t.getX() && e.getX() < t.getX() + t.getLength()) && (e.getY() > t.getY() + (t.getLength() / 2) && e.getY() < t.getY() + t.getLength() + (t.getLength() / 2)))
                            t.hovered = true;
                        else
                            t.hovered = false;
                    }
                }
                if(e.getX() > 75 && e.getX() < 150 && e.getY() > 615 && e.getY() < 645){
                    resetHovered = true;
                }else{
                    resetHovered = false;
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            if(!gameGoing){
                if(e.getX() > 250 && e.getX() < 400 && e.getY() > 500 && e.getY() < 550) {
                    gameGoing = true;
                    b = new Board(60, 5, 80, 80);
                    player = 1;
                    selected = 0;
                    p1 = new Player();
                    p2 = new Player();
                    invalidMove = false;
                }
            }

            if(player1Won || player2Won){
                if(e.getX() > 250 && e.getX() < 400 && e.getY() > 525 && e.getY() < 575){
                    player1Won = player2Won = false;
                    gameGoing = false;
                }
            }

            if(gameGoing){
                if(e.getX() > 75 && e.getX() < 150 && e.getY() > 625 && e.getY() < 655){
                    b = new Board(60, 5, 80, 80);
                    gameGoing = false;
                    player = 1;
                    selected = 0;
                    p1 = new Player();
                    p2 = new Player();
                    invalidMove = false;
                }
                for(Tile[] ts: Board.board) {
                    for (Tile t : ts) {
                        if ((e.getX() > t.getX() && e.getX() < t.getX() + t.getLength()) && (e.getY() > t.getY() + (t.getLength() / 2) && e.getY() < t.getY() + t.getLength() + (t.getLength() / 2))) {
                            //Highlights the spots that are chosen\\
                            t.chosen = !t.chosen;
                            if (t.chosen) {//If you chose a spot then add 1 to the selected count
                                if (selected < 2) {//If the total amount selected is less than 2 then add 1 to select
                                    if (selected == 0)
                                        start = t.position;
                                    else
                                        end = t.position;
                                    selected++;
                                } else {//If you chose more than 2 it will turn off what you just picked
                                    t.chosen = false;
                                }
                            } else {// When you chose you pick the same spot you turn off the spot and make the amount selected to 1 less
                                selected--;
                            }
                            //Changes Pieces\\
                            if (selected == 2) {
                                //System.out.println("Start: " + start[0] + ", " + start[1] + "\nEnd: " + end[0] + ", " + end[1]);
                                if (Checker.isValidMove(start, end, player)) {
                                    invalidMove = false;
                                    //Updates the current tile to show that it was moved\\
                                    Board.get(start).moved();

                                    //Adds The Piece Captured to the captured piece list\\
                                    if (Board.get(end).getPiece() != Piece.Blank) {
                                        if(player == 1) {
                                            p2.piecesCaptured.add(Board.get(end).getPiece());
                                            if(Board.get(end).getPiece() == Piece.King){
                                                player1Won = true;
                                                gameGoing = false;
                                            }
                                            p1.piecesCaptured.add(Board.get(end).getPiece());
                                        }else {
                                            p2.piecesCaptured.add(Board.get(end).getPiece());
                                            if(Board.get(end).getPiece() == Piece.King) {
                                                player2Won = true;
                                                gameGoing = false;
                                            }
                                        }
                                    }

                                    Board.get(end).change(Board.get(start));
                                    Board.get(start).reset();
                                    player++;
                                    if (player == 3)
                                        player = 1;
                                }else{
                                    invalidMove = true;
                                }
                                Board.get(end).chosen = false;
                                Board.get(start).chosen = false;
                                selected = 0;
                            }

                        }
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseDragged(MouseEvent e) {}

        @Override
        public void mouseClicked(MouseEvent e) {}
    }

}