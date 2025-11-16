import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <h2><code><b>BoardDisplay</b></code>:</h2>
 *
 * @author Gabriel Caligiuri
 */

public class BoardDisplay extends JPanel {
    //Size in px of the borderlines around the board
    public static final int BORDER_OFFSET = 9;
    //size in px of the space above the board for taken pieces
    public static final int HEIGHT_OFFSET = 110;
    //size of board
    public static final int BOARD_SIZE = 8;
    //Color for the light squares on the board
    private static float[] LIGHT_COLORS_RGB = Color.RGBtoHSB(196, 164, 132, null);
    //Color for the dark squares on the board
    private static float[] DARK_COLORS_RGB = Color.RGBtoHSB(136, 69, 19, null);
    //Color for the light squares on the board
    private static final Color LIGHT_COLOR = Color.getHSBColor(LIGHT_COLORS_RGB[0], LIGHT_COLORS_RGB[1], LIGHT_COLORS_RGB[2]);
    //Color for the dark squares on the board
    private static final Color DARK_COLOR = Color.getHSBColor(DARK_COLORS_RGB[0], DARK_COLORS_RGB[1], DARK_COLORS_RGB[2]);
    //size of each square on the board in px
    public static final int SQUARE_SIZE = 60;
    //row and col that is being highlighted when you click on a piece
    private int highlightedRow = -1;
    private int highlightedCol = -1;
    //used to highlight the tile the king is on when the king is under check
    private int kingCol;
    private int kingRow;
    private int pawnRow;
    private int pawnCol;
    private Piece promotedPawn;
    private boolean kingCheck = false;
    //used to store and display the possibleMoves.
    private ArrayList<Integer> movesRow = new ArrayList<Integer>();
    private ArrayList<Integer> movesCol = new ArrayList<Integer>();
    private boolean possibleMoves = false;
    //array list holding all pieces and dead pieces
    private ArrayList<Piece> pieces;
    private ArrayList<Piece> deadPieces;
    //connects the pieces to their images
    private Map<String, Image> pieceImages;
    //keeps track of the number of white and black pieces that are taken
    private int whiteTakenPieces;
    private int blackTakenPieces;
    //checks if game is over to display game over screen
    private boolean gameOver = false;
    private boolean pawnPromotionDisplay = false;
    private ArrayList<String> promotionOptions = new ArrayList<String>();
    //color that won the game
    private String colorWon;
    Font font = Font.getFont("Sans Serif");

    /**
     * Initializes board with a pieceImages array, pieces array, and dead pieces array.
     */
    public BoardDisplay(){
        pieces = new ArrayList<>();
        deadPieces = new ArrayList<>();
        pieceImages = new HashMap<>();
        promotionOptions.add("Queen");
        promotionOptions.add("Rook");
        promotionOptions.add("Knight");
        promotionOptions.add("Bishop");

    }

    /**
     * sets the highlighted row and column then repaints the window
     * @param row - row of tile you want highlighted
     * @param col - column of tile you want highlighted
     */
    public void highlightPiece(int row, int col) {
        highlightedRow = row;
        highlightedCol = col;
        this.repaint();
    }

    /**
     * highlights the kings row and column then repaints the window
     * tells us that the king is in check
     * @param king - king that is in check
     */
    public void highlightCheckedKing(Piece king){
        kingRow = king.getRow();
        kingCol = king.getCol();
        kingCheck = true;
        this.repaint();
    }

    /**
     * used if the user clicks to play again after the game ends
     * resets all values for the display and changes the check value of the king back to false
     * @param chessBoard - chessBoard with all pieces
     */
    public void resetBoard(Board chessBoard){
        this.setDeadPieces(chessBoard);
        this.setPieces(chessBoard);
        highlightedCol = -1;
        highlightedRow = -1;
        kingCheck = false;
    }

    /**
     * Loads the images of the pieces to the Map
     */
    public void loadPieceImages() {
        //Assign images to each piece that will be on the board
        try {
            pieceImages.put("Black_King", ImageIO.read(new File("src\\ChessPieces\\Black_King.png")));
            pieceImages.put("Black_Queen", ImageIO.read(new File("src\\ChessPieces\\Black_Queen.png")));
            pieceImages.put("Black_Knight", ImageIO.read(new File("src\\ChessPieces\\Black_Knight.png")));
            pieceImages.put("Black_Bishop", ImageIO.read(new File("src\\ChessPieces\\Black_Bishop.png")));
            pieceImages.put("Black_Rook", ImageIO.read(new File("src\\ChessPieces\\Black_Rook.png")));
            pieceImages.put("Black_Pawn", ImageIO.read(new File("src\\ChessPieces\\Black_Pawn.png")));
            pieceImages.put("White_King", ImageIO.read(new File("src\\ChessPieces\\White_King.png")));
            pieceImages.put("White_Queen", ImageIO.read(new File("src\\ChessPieces\\White_Queen.png")));
            pieceImages.put("White_Knight", ImageIO.read(new File("src\\ChessPieces\\White_Knight.png")));
            pieceImages.put("White_Bishop", ImageIO.read(new File("src\\ChessPieces\\White_Bishop.png")));
            pieceImages.put("White_Rook", ImageIO.read(new File("src\\ChessPieces\\White_Rook.png")));
            pieceImages.put("White_Pawn", ImageIO.read(new File("src\\ChessPieces\\White_Pawn.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBoard(Graphics g) {
        //for loop that goes from 1-9 in rows and 1-9 in columns
        for (int row = 0; row < BOARD_SIZE; row++){
            for (int col = 0; col < BOARD_SIZE; col++) {
                //if (row+col) is even make it a light square
                if ((row + col) % 2 == 0){
                    g.setColor(LIGHT_COLOR);
                }
                //if (row+col) is odd make it a dark square
                else {
                    g.setColor(DARK_COLOR);
                }
                //creates the squares
                g.fillRect(col*SQUARE_SIZE+BORDER_OFFSET, row * SQUARE_SIZE+HEIGHT_OFFSET, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        //creates the border around the board
        g.setColor(Color.BLACK);
        for (int i = 0; i < 10; i++){
            g.drawRect(BORDER_OFFSET-i, HEIGHT_OFFSET-i, (SQUARE_SIZE*BOARD_SIZE)+i*2, (SQUARE_SIZE*BOARD_SIZE)+i*2);
        }
    }

    private void drawDeadPieces(Graphics g) {
        //sets both taken values to 0
        whiteTakenPieces = 0;
        blackTakenPieces = 0;
        //makes sure deadPieces isn't an empty array
        if (!deadPieces.isEmpty()) {
            //iterates through all pieces in dead pieces
            for (Piece piece : deadPieces) {
                //maps the pieces to their images
                String key = piece.getColor() + "_" + piece.getName();
                Image image = pieceImages.get(key);
                //makes sure the images exist
                if (image != null) {
                    //displays the white pieces above the board when taken
                    if (Objects.equals(piece.getColor(), "White")) {
                        piece.setPosition(-1 - whiteTakenPieces / 8, whiteTakenPieces % 8);
                        whiteTakenPieces++;
                        //displays black pieces below the board when taken
                    } else {
                        piece.setPosition(8 + blackTakenPieces / 8, blackTakenPieces % 8);
                        blackTakenPieces++;
                    }
                    //gets x and y for where they will be placed then draws the images
                    int x = piece.getCol() * SQUARE_SIZE + BORDER_OFFSET;
                    int y = piece.getRow() * SQUARE_SIZE + HEIGHT_OFFSET + 5;
                    g.drawImage(image, x, y, SQUARE_SIZE, SQUARE_SIZE, this);
                }
            }
        }
    }


    private void drawPieces(Graphics g) {
        //iterates through all piece in pieces
        for (Piece piece : pieces) {
            //maps the piece to its image
            String key = piece.getColor() + "_" + piece.getName();
            Image image = pieceImages.get(key);
            //checks if the image exists
            if (image != null) {
                //gets the x and y for the piece and displays them on the board
                int x = piece.getCol() * SQUARE_SIZE + BORDER_OFFSET;
                int y = (piece.getRow() * SQUARE_SIZE) + HEIGHT_OFFSET + 5;
                g.drawImage(image, x, y, SQUARE_SIZE, SQUARE_SIZE, this);
            }
        }
    }

    private void drawTopPanel(Graphics g) {
        //sets color to the darker color
        g.setColor(DARK_COLOR);
        //draws a filled rect above the board for where the taken pieces go
        g.fillRect(0, 0, BOARD_SIZE * SQUARE_SIZE + BORDER_OFFSET * 2, HEIGHT_OFFSET - BORDER_OFFSET);
        //sets color to the lighter color
        g.setColor(LIGHT_COLOR);
        //draws a filled rect below the board for where the taken pieces go
        g.fillRect(0, (BOARD_SIZE * SQUARE_SIZE) + BORDER_OFFSET + HEIGHT_OFFSET, BOARD_SIZE * SQUARE_SIZE + BORDER_OFFSET * 2, HEIGHT_OFFSET);
    }

    private void drawPromotionPieces(Graphics g) {
        for (int i = 0; i < promotionOptions.size(); i++){
            String key = promotedPawn.getColor() + "_" + promotionOptions.get(i);
            Image image = pieceImages.get(key);
            if (image != null) {
                int x = promotedPawn.getCol()*SQUARE_SIZE + BORDER_OFFSET;
                int y = switch (promotedPawn.getColor()) {
                    case "Black" -> (promotedPawn.getRow() - i) * SQUARE_SIZE + HEIGHT_OFFSET + 5;
                    case "White" -> (promotedPawn.getRow() + i) * SQUARE_SIZE + HEIGHT_OFFSET + 5;
                    default -> 0;
                };
                g.drawImage(image, x, y, SQUARE_SIZE, SQUARE_SIZE, this);
            }
        }
    }


    //controls the size of the window
    @Override
    public Dimension getPreferredSize() {
        //width of the window is the size of the board + the size of the outline on both sides
        //height of the window is size of board + the sections where taken pieces go + outline
        return new Dimension(BOARD_SIZE*SQUARE_SIZE + BORDER_OFFSET*2, BOARD_SIZE * SQUARE_SIZE + HEIGHT_OFFSET*2 + BORDER_OFFSET);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //if the game isn't over
        if (!gameOver) {
            super.paintComponent(g);
            //draws the panels by calling on their respective functions
            drawTopPanel(g);
            drawBoard(g);
            drawPieces(g);
            drawDeadPieces(g);
            //Highlight the square if it's specified
            if (highlightedRow != -1 && highlightedCol != -1) {
                //if the king is in check set color to red and highlight the tile the king is on in red
                if (kingCheck) {
                    g.setColor(Color.RED);
                    g.drawRect(kingCol * SQUARE_SIZE + BORDER_OFFSET, kingRow * SQUARE_SIZE + HEIGHT_OFFSET, SQUARE_SIZE, SQUARE_SIZE);
                    //set KingCheck to false, if king is still in check it will reset it to true.
                    kingCheck = false;
                }
                //set the color to yellow and highlight the square that the user selected
                g.setColor(Color.YELLOW);
                g.drawRect(highlightedCol * SQUARE_SIZE + BORDER_OFFSET, highlightedRow * SQUARE_SIZE + HEIGHT_OFFSET, SQUARE_SIZE, SQUARE_SIZE);
            }
            //displays the possibleMoves the user can make
            if (possibleMoves) {
                //sets the color to blue
                g.setColor(Color.BLUE);
                //highlights all the moves the user can make using blue dots
                for (int i = 0; i < movesCol.size(); i++) {
                    g.fillOval(movesCol.get(i) * SQUARE_SIZE + BORDER_OFFSET + (SQUARE_SIZE / 2) - SQUARE_SIZE / 10, movesRow.get(i) * SQUARE_SIZE + HEIGHT_OFFSET + (SQUARE_SIZE / 2) - SQUARE_SIZE / 10, SQUARE_SIZE / 5, SQUARE_SIZE / 5);
                }
            }
            //sets possibleMoves to false
            possibleMoves = false;
            if(pawnPromotionDisplay){
                g.setColor(Color.WHITE);
                switch(promotedPawn.getColor()){
                    case "White":
                        g.fillRect(pawnCol*SQUARE_SIZE + BORDER_OFFSET, pawnRow*SQUARE_SIZE + HEIGHT_OFFSET, SQUARE_SIZE, SQUARE_SIZE*4);
                        break;
                    case "Black":
                        g.fillRect(pawnCol*SQUARE_SIZE + BORDER_OFFSET, (pawnRow-3)*SQUARE_SIZE + HEIGHT_OFFSET, SQUARE_SIZE, SQUARE_SIZE*4);
                        break;
                }
                drawPromotionPieces(g);
                pawnPromotionDisplay = false;
            }
            //is performed if the game is over. Creates the end game page with the window that allows you to play again
        } else {
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString("Game Over", (BOARD_SIZE * SQUARE_SIZE + BORDER_OFFSET * 2) / 2 - 30, (BOARD_SIZE * SQUARE_SIZE + HEIGHT_OFFSET * 2 + BORDER_OFFSET) / 2 - 60);
            g.drawString(colorWon + " Won", (BOARD_SIZE * SQUARE_SIZE + BORDER_OFFSET * 2) / 2 - 30, (BOARD_SIZE * SQUARE_SIZE + HEIGHT_OFFSET * 2 + BORDER_OFFSET) / 2 - 40);
            g.drawRect((BOARD_SIZE * SQUARE_SIZE + BORDER_OFFSET * 2) / 2 - 101, (BOARD_SIZE * SQUARE_SIZE + HEIGHT_OFFSET * 2 + BORDER_OFFSET) / 2 - 15, 60, 20);
            g.drawString("Play Again", (BOARD_SIZE * SQUARE_SIZE + BORDER_OFFSET * 2) / 2 - 100, (BOARD_SIZE * SQUARE_SIZE + HEIGHT_OFFSET * 2 + BORDER_OFFSET) / 2);
            g.drawRect((BOARD_SIZE * SQUARE_SIZE + BORDER_OFFSET * 2) / 2 + 69, (BOARD_SIZE * SQUARE_SIZE + HEIGHT_OFFSET * 2 + BORDER_OFFSET) / 2 - 15, 28, 20);
            g.drawString("Quit", (BOARD_SIZE * SQUARE_SIZE + BORDER_OFFSET * 2) / 2 + 70, (BOARD_SIZE * SQUARE_SIZE + HEIGHT_OFFSET * 2 + BORDER_OFFSET) / 2);
        }
    }

    /**
     * used to highlight the moves the piece can make
     * @param moves - arrayList containing all the tile's the piece can move to
     */
    public void highlightPossibleMoves(ArrayList<Tile> moves){
        movesCol.clear();
        movesRow.clear();
        for (Tile move : moves){
            movesCol.add(move.getCol());
            movesRow.add(move.getRow());
        }
        possibleMoves = true;
        repaint();
    }

    //setter for deadPieces allows the display to display the deadPieces after syncing them with the board
    public void setDeadPieces(Board board){
        this.deadPieces = board.getDeadPieces();
    }

    //setter for pieces allows the display to display them after syncing them with the board
    public void setPieces(Board board) {
       this.pieces = board.getAllPieces();
    }

    //if the game is over take the color that won and set gameOver to true then repaint
    public void gameOver(String colorWon){
        this.colorWon = colorWon;
        gameOver = true;
        this.repaint();
    }

    //if the user wants to play again set gameOver to false and then repaint the board.
    public void playAgain(){
        gameOver = false;
        this.repaint();
    }

    public void pawnPromotionDisplay(Board chessBoard, Piece piece){
        pawnCol = piece.getCol();
        pawnRow = piece.getRow();
        promotedPawn = piece;
        pawnPromotionDisplay = true;
        repaint();
    }
}
