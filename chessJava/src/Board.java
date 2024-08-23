import java.util.ArrayList;
import java.util.Objects;

/**<h2><code><b>Board</b></code>:</h2>
 * <p>contains 0 parameters and creates an empty ArrayList when initialized</p>
 * <p>Contains an enum named GameState that keeps track of the state the game is in with 4
 * different values <ul><li>gameOver - used when the game ends to display end screen</li>
 * <li>mate - used when a player gets mated</li>
 * <li>checkMate - used to check if either player is in checkmate</li>
 * <li>baseTurn - used for most turns of the game to move, the base turn if there is no check going on</li>
 * </ul></p>
 * <p>Contains multiple getters and setters: <br>
 * <ol><li>setState(state) - sets the state of GameState</li>
 * <li><code><b>getState()</b></code> - gets the state of GameState</li>
 * <li><code><b>findPiece(row, col)</b></code> - returns a piece at the given row or col, or null if no piece is there</li>
 * <li><code><b>deletePiece()</b></code> - deletes piece from the pieces array and adds it to the dead pieces array</li>
 * <li><code><b>resetBoard()</b></code> - clears both the dead pieces array and the pieces array</li>
 * <li><code><b>getAllPieces()</b></code> - returns the array with all the pieces</li>
 * <li><code><b>getDeadPieces()</b></code> - returns the array with all dead pieces</li>
 * <li><code><b>setupBoard()</b></code> - adds each piece to the array to begin the game</li>
 * </ol></p>
 * <p>Other functions:
 * <ul><li><code><b>isTileOccupied(row, col)</b></code> - checks if the tile at the specific row and col is occupied. returns a boolean.</li>
 * <li><code><b>canTake(Board, Piece, Piece)</b></code> - checks if the first Piece is able to take the second Piece. returns a boolean</li>
 * </ul>
 * </p>
 * @author Gabriel Caligiuri
 */

public class Board {
    //Size of board
    private static final int BOARD_SIZE = 8;
    //initialize Game State
    public static Board.GameState GameState;
    //Initialize pieces array
    ArrayList<Piece> pieces;
    //Initialize dead pieces array
    ArrayList<Piece> deadPieces = new ArrayList<>();

    public Board(){
        //Initialize pieces array
        pieces = new ArrayList<>();
    }

    //Controls game state of the game
    public enum GameState{
        gameOver,
        mate,
        checkMate,
        baseTurn,
        pawnPromotion
    }

    //getter for game state
    public static Board.GameState getState(){
        return GameState;
    }

    //setter for game state
    public static void setState(Board.GameState state){
        GameState = state;
    }

    /**
     * @param row - row of the tile were checking
     * @param col - column of the tile were checking
     * @return true if tile's occupied and false if it isn't
     */
    public boolean isTileOccupied(int row, int col){
        for (Piece piece: pieces){
            if (piece.getRow() == row && piece.getCol() == col){
                return true;
            }
        }
        return false;
    }

    /**
     * @param row - row of tile were checking
     * @param col - column of tile were checking
     * @return null if there is no piece on that tile, otherwise returns the piece on the tile
     */
    public Piece findPiece(int row, int col){
        for(Piece piece: pieces){
            if (piece.getRow() == row && piece.getCol() == col){
                return piece;
            }
        }
        return null;
    }

    /**
     * called whenever a piece is taken
     * @param piece - deletes the piece then adds it to dead pieces
     */
    public void deletePiece(Piece piece){
        pieces.remove(piece);
        deadPieces.add(piece);
    }

    /**
     * used to update the boardDisplay with the deadPieces
     * @return deadPieces array
     */
    public ArrayList<Piece> getDeadPieces(){
        return deadPieces;
    }

    /**
     * used to reset the board after the game is over
     */
    public void resetBoard(){
        pieces.clear();
        deadPieces.clear();
    }

    /**
     * used to get the array list of pieces
     * @return list of pieces on board
     */
    public ArrayList<Piece> getAllPieces(){
        return pieces;
    }

    public void addPiece(Piece piece){
        pieces.add(piece);
    }

    /**
     * used for check defense reasoning to allow a piece to take the piece that is putting the king in check
     * @param chessBoard - the chessboard with all pieces on it
     * @param taker - piece that is attacking(taking)
     * @param taken - piece that is being taken
     * @return - true if the taker can take the taken, false if it cant
     */
    public boolean canTake(Board chessBoard, Piece taker, Piece taken){
        ArrayList<Tile> moves = taker.possibleMoves(chessBoard, taker);
        for (Tile move : moves){
            if (move.getRow() == taken.getRow() && move.getCol() == taken.getCol()){
                return true;
            }
        }
        return false;
    }

    public boolean checkPawnPromotion() {
        for (Piece piece : pieces) {
            switch (piece.getColor()) {
                case "Black":
                    if (Objects.equals(piece.getName(), "Pawn")) {
                        if (piece.getRow() == 7) {
                            return true;
                        }
                    }
                    break;
                case "White":
                    if (Objects.equals(piece.getName(), "Pawn")) {
                        if (piece.getRow() == 0) {
                            return true;
                        }
                    }
                    break;
            }
        }
        return false;
    }

    /**
     * sets up the board by adding all the pieces to the array at their respective starting positions
     */
    public void setupBoard(){
        setupKing();
        setupQueen();
        setupBishopKnightRook();
        setupPawns();
    }

    //adds knights bishops and rooks to the array
    private void setupBishopKnightRook(){
        for (int i = 0; i < 2; i++){
            pieces.add(new Knight("Knight", "White", 7, i*5+1));
            pieces.add(new Knight("Knight", "Black", 0, i*5+1));
            pieces.add(new Rook("Rook", "White", 7, i*7));
            pieces.add(new Rook("Rook", "Black", 0, i*7));
            pieces.add(new Bishop("Bishop", "White", 7, i*3+2));
            pieces.add(new Bishop("Bishop", "Black", 0, i*3+2));
        }
    }

    //adds kings to array
    private void setupKing() {
        pieces.add(new King("King", "White", 7, 4));
        pieces.add(new King("King", "Black", 0, 4));
    }

    //adds queens to array
    private void setupQueen() {
        pieces.add(new Queen("Queen", "White", 7, 3));
        pieces.add(new Queen("Queen", "Black", 0, 3));
    }

    //adds pawns to array
    private void setupPawns() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            pieces.add(new Pawn("Pawn", "White", 6, i));
            pieces.add(new Pawn("Pawn", "Black", 1, i));
        }
    }
}
