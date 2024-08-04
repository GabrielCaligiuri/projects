import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;

//implements the MouseListener that already exists in java
public class ChessMouseListener implements MouseListener {
    //where the board starts in pixels from the left
    private static final int SIDE_BORDER = 9;
    //where the board ends in pixels from the left
    private static final int BOARD_BORDER_X = 490;
    //where the board ends in pixels from the top
    private static final int TOP_BORDER_Y = 590;
    //where the board starts in pixels from the top
    private static final int BOTTOM_BORDER_Y = 110;
    //initializes the board and the board display
    private BoardDisplay chessDisplay;
    private Board chessBoard;
    //intializes piece variables for the piece the user clicks and the king that is in check
    private Piece clickedPiece;
    private Piece checkedKing;
    private Piece placeholderPiece;
    //Initialize array list's for moves
    private ArrayList<Tile> checkMoves;
    private ArrayList<Tile> possibleMoves;
    //initialize player variable to control turns
    private Player player;
    //variables used to keep track of turns for enPassant
    private int turns = 0;
    private int enPassantTurn;
    //keeps track of oldRow and oldCol for enPassant and checks
    private int oldRow;
    private int oldCol;
    //used for checkmate to go to mate
    int count = 0;


    public ChessMouseListener(Board chessBoard, BoardDisplay chessDisplay, Player player) {
        this.chessBoard = chessBoard;
        this.chessDisplay = chessDisplay;
        this.player = player;
        possibleMoves = new ArrayList<>();
        checkMoves = new ArrayList<>();
    }

    //Overrides the initial mouseClicked
    @Override
    public void mouseClicked(MouseEvent e) {
        //Initialize variables to detect X and Y of mouse clicks
        int mouseX = e.getX();
        int mouseY = e.getY();
        //Initialize variable of square size
        int SQUARE_SIZE = 60;
        //Variables for col and row gained by subtracting borders from the location of the mouse then dividing it by the square size
        int row = (mouseY - BOTTOM_BORDER_Y) / SQUARE_SIZE;
        int col = (mouseX - SIDE_BORDER) / SQUARE_SIZE;
        //get the value of the king of the player that has the current turn
        for (Piece piece : chessBoard.getAllPieces()) {
            if (Objects.equals(piece.getName(), "King") && Objects.equals(piece.getColor(), player.getColor())) {
                checkedKing = piece;
                break;
            }
        }
        //if that king is in check set state to checkmate
        if (King.kingChecked(chessBoard, player, checkedKing) && Board.getState() != Board.GameState.gameOver) {
            Board.setState(Board.GameState.mate);
        } else if (Board.getState() != Board.GameState.pawnPromotion){
            Board.setState(Board.GameState.baseTurn);
        }
        //set count to 0 (one run through the for loop)
            //switch statement for game state
            switch (Board.GameState) {
                //normal case for average turn
                case baseTurn:
                    checkMoves.clear();
                    //making sure mouse is on the board
                    if (mouseX <= BOARD_BORDER_X && BOTTOM_BORDER_Y <= mouseY && mouseY <= TOP_BORDER_Y) {
                        //checks if tile is occupied and makes sure that the pieces color is the same color as the player
                        if (chessBoard.isTileOccupied(row, col) && Objects.equals(getPieceAt(row, col).getColor(), player.getColor())) {
                            //assign clicked piece to the piece on the clicked tile
                            clickedPiece = getPieceAt(row, col);
                            //highlight the piece
                            chessDisplay.highlightPiece(row, col);
                            //get all possible moves for the specific piece
                            possibleMoves = clickedPiece.possibleMoves(chessBoard, clickedPiece);
                            oldRow = clickedPiece.getRow();
                            oldCol = clickedPiece.getCol();
                            ArrayList<Tile> checkRemoves = new ArrayList<>();
                            for (Tile moves : possibleMoves){
                                if (chessBoard.isTileOccupied(moves.getRow(), moves.getCol())){
                                    placeholderPiece = getPieceAt(moves.getRow(), moves.getCol());
                                    if (!Objects.equals(placeholderPiece.getColor(), clickedPiece.getColor())){
                                        placeholderPiece.setPosition(999, -999);
                                        clickedPiece.setPosition(moves.getRow(), moves.getCol());
                                        if (King.kingChecked(chessBoard, player, checkedKing)) {
                                            checkRemoves.add(moves);
                                        }
                                    } else {
                                        checkRemoves.add(moves);
                                    }
                                    clickedPiece.setPosition(oldRow, oldCol);
                                    placeholderPiece.setPosition(moves.getRow(), moves.getCol());
                                } else {
                                    clickedPiece.setPosition(moves.getRow(), moves.getCol());
                                    if (King.kingChecked(chessBoard, player, checkedKing)) {
                                        checkRemoves.add(moves);
                                    }
                                    clickedPiece.setPosition(oldRow, oldCol);
                                }
                            }
                            for(Tile remove : checkRemoves){
                                possibleMoves.remove(remove);
                            }
                            //highlight the possible moves
                            chessDisplay.highlightPossibleMoves(possibleMoves);
                        }
                        //check if possible moves is empty
                        if (!possibleMoves.isEmpty()) {
                            //for move in possible moves
                            for (Tile moves : possibleMoves) {
                                //check if the row and col in the current move is the col and row clicked
                                if (moves.getRow() == row && moves.getCol() == col) {
                                    //check if the tile is occupied
                                    if (chessBoard.isTileOccupied(row, col)) {
                                        //if the tile is occupied check that it's the opposite color
                                        if (!Objects.equals(clickedPiece.getColor(), chessBoard.findPiece(row, col).getColor())) {
                                            //delete the piece that was taken
                                            chessBoard.deletePiece(chessBoard.findPiece(row, col));
                                            //update pieces and dead pieces on display
                                            chessDisplay.setDeadPieces(chessBoard);
                                            chessDisplay.setPieces(chessBoard);
                                            //repaint the board
                                            chessDisplay.repaint();
                                        }
                                    }
                                    //if enPassant is true
                                    if (Pawn.enPassant) {
                                        enPassant(chessDisplay, chessBoard, clickedPiece, row, col);
                                    }
                                    //look at function for more detail
                                    castle(chessDisplay, chessBoard, clickedPiece, row, col);
                                    //check if piece and player color is the same
                                    if (Objects.equals(clickedPiece.getColor(), player.getColor())) {
                                        //check if the piece is a pawn
                                        if (Objects.equals(clickedPiece.getName(), "Pawn")) {
                                            //save row of pawn
                                            oldRow = clickedPiece.getRow();
                                        }
                                        //move the piece to clicked position
                                        clickedPiece.setPosition(row, col);
                                        //clear possible moves
                                        possibleMoves.clear();
                                        if (Objects.equals(clickedPiece.getName(), "Pawn")) {
                                            if (clickedPiece.getRow() - oldRow == 2 || clickedPiece.getRow() - oldRow == -2) {
                                                Pawn.enPassant = true;
                                                //set enPassantturn to turns to keep track of turns passed since enPassant was available
                                                enPassantTurn = turns;
                                            }
                                        }
                                        //turns - enPassantturn == 1 the turn after enPassant is true
                                        if (turns - enPassantTurn == 1) {
                                            //set enPassant to false
                                            Pawn.enPassant = false;
                                        }
                                        //if the piece that was moved was a king
                                        if (Objects.equals(clickedPiece.getName(), "King")) {
                                            //get color of king
                                            switch (clickedPiece.getColor()) {
                                                case "White":
                                                    //set king movement to true so that king is unable to castle
                                                    King.whiteKingMoved = true;
                                                    break;
                                                case "Black":
                                                    //set king movement to true so that king is unable to castle
                                                    King.blackKingMoved = true;
                                                    break;
                                            }
                                        }
                                        //reset clicked piece
                                        clickedPiece = null;
                                        //update pieces to display
                                        chessDisplay.setPieces(chessBoard);
                                        //repaint display
                                        chessDisplay.repaint();
                                        //add to turns after turn happens
                                        turns++;
                                        //change color of player to change whose turn it is
                                        if (Objects.equals(player.getColor(), "Black")) {
                                            player.setColor("White");
                                        } else {
                                            player.setColor("Black");
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case mate:
                    //if king is in check highlight the king
                    chessDisplay.highlightCheckedKing(checkedKing);
                    //check if the click is on the board
                    if (mouseX <= BOARD_BORDER_X && BOTTOM_BORDER_Y <= mouseY && mouseY <= TOP_BORDER_Y) {
                        if(chessBoard.isTileOccupied(row, col) && Objects.equals(getPieceAt(row, col).getColor(), player.getColor())){
                            clickedPiece = getPieceAt(row, col);
                            chessDisplay.highlightPiece(row, col);
                            checkMoves.clear();
                            oldRow = clickedPiece.getRow();
                            oldCol = clickedPiece.getCol();
                        }
                        if(chessBoard.isTileOccupied(row, col) && Objects.equals(player.getColor(), checkedKing.getColor())){
                            possibleMoves = clickedPiece.possibleMoves(chessBoard, clickedPiece);
                            if (chessBoard.canTake(chessBoard, clickedPiece, King.checkedPiece)) {
                                if(clickedPiece == checkedKing){
                                    placeholderPiece =  King.checkedPiece;
                                    clickedPiece.setPosition(King.checkedPiece.getRow(), King.checkedPiece.getCol());
                                    placeholderPiece.setPosition(999, -999);
                                    if(!King.kingChecked(chessBoard, player, checkedKing)){
                                        checkMoves.add(new Tile(clickedPiece.getRow(), clickedPiece.getCol()));
                                    }
                                    placeholderPiece.setPosition(clickedPiece.getRow(), clickedPiece.getCol());
                                    clickedPiece.setPosition(oldRow, oldCol);
                                } else {
                                    checkMoves.add(new Tile(King.checkedPiece.getRow(), King.checkedPiece.getCol()));
                                }
                            }
                            for (Tile moves : possibleMoves){
                                clickedPiece.setPosition(moves.getRow(), moves.getCol());
                                if (King.kingChecked(chessBoard, player, checkedKing)){
                                    continue;
                                } else {
                                    checkMoves.add(new Tile(moves.getRow(), moves.getCol()));
                                }
                                clickedPiece.setPosition(oldRow, oldCol);
                            }
                            clickedPiece.setPosition(oldRow, oldCol);
                            chessDisplay.highlightPossibleMoves(checkMoves);
                            chessDisplay.repaint();
                        }
                        //check that checkMoves isn't empty
                        if (!checkMoves.isEmpty()) {
                            for (Tile moves : checkMoves) {
                                //moves = tile clicked
                                if (moves.getRow() == row && moves.getCol() == col) {
                                    //check if the tile is occupied
                                    if (chessBoard.isTileOccupied(row, col)) {
                                        //check if piece that is higlighted and piece that is clicked are diff colors
                                        if (!Objects.equals(clickedPiece.getColor(), chessBoard.findPiece(row, col).getColor())) {
                                            //deletes piece
                                            chessBoard.deletePiece(chessBoard.findPiece(row, col));
                                            clickedPiece.setPosition(row, col);
                                        }
                                    } else{
                                        clickedPiece.setPosition(row, col);
                                    }
                                    //update display
                                    chessDisplay.setPieces(chessBoard);
                                    chessDisplay.setDeadPieces(chessBoard);
                                    chessDisplay.repaint();
                                    clickedPiece = null;
                                    if (Objects.equals(player.getColor(), "Black")) {
                                        player.setColor("White");
                                    } else {
                                        player.setColor("Black");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if(checkMoves.isEmpty()){
                        possibleMoves.clear();
                        checkMoves.clear();
                        Board.setState(Board.GameState.checkMate);
                    } else if(King.kingChecked(chessBoard, player, checkedKing)) {
                        possibleMoves.clear();
                        Board.setState(Board.GameState.baseTurn);
                    }
                    break;
                case checkMate:
                    placeholderPiece = clickedPiece;
                    chessDisplay.highlightCheckedKing(checkedKing);
                    for (Piece piece : chessBoard.getAllPieces()) {
                        oldRow = piece.getRow();
                        oldCol = piece.getCol();
                        if (Objects.equals(piece.getColor(), checkedKing.getColor())) {
                            if (chessBoard.canTake(chessBoard, piece, King.checkedPiece)) {
                                if(piece == checkedKing){
                                    clickedPiece =  King.checkedPiece;
                                    piece.setPosition(King.checkedPiece.getRow(), King.checkedPiece.getCol());
                                    King.checkedPiece.setPosition(999, -999);
                                    if(!King.kingChecked(chessBoard, player, checkedKing)){
                                        checkMoves.add(new Tile(piece.getRow(), piece.getCol()));
                                    }
                                    King.checkedPiece.setPosition(clickedPiece.getRow(), clickedPiece.getCol());
                                    piece.setPosition(oldRow, oldCol);
                                } else {
                                    checkMoves.add(new Tile(King.checkedPiece.getRow(), King.checkedPiece.getCol()));
                                }
                            }
                            possibleMoves = piece.possibleMoves(chessBoard, piece);
                            for (Tile moves : possibleMoves) {
                                piece.setPosition(moves.getRow(), moves.getCol());
                                if (King.kingChecked(chessBoard, player, checkedKing)) {
                                    continue;
                                } else {
                                    if(!(Objects.equals(piece.getName(), checkedKing.getName()))) {
                                        checkMoves.add(new Tile(piece.getRow(), piece.getCol()));
                                    }
                                }
                            }
                            piece.setPosition(oldRow, oldCol);
                        }
                    }
                    if (checkMoves.isEmpty()) {
                        Board.setState(Board.GameState.gameOver);
                    } else {
                        chessDisplay.setPieces(chessBoard);
                        chessDisplay.repaint();
                        checkMoves.clear();
                        clickedPiece = placeholderPiece;
                        Board.setState(Board.GameState.mate);
                        break;
                    }
                case gameOver:
                    switch(checkedKing.getColor()){
                        case "White":
                            chessDisplay.gameOver("Black");
                        case "Black":
                            chessDisplay.gameOver("White");
                    }
                    int boardX = (BoardDisplay.BOARD_SIZE * SQUARE_SIZE + BoardDisplay.BORDER_OFFSET * 2) / 2;
                    int boardY = (BoardDisplay.BOARD_SIZE * SQUARE_SIZE + BoardDisplay.HEIGHT_OFFSET * 2 + BoardDisplay.BORDER_OFFSET) / 2;
                    if (boardX - 101 <= mouseX && mouseX <= boardX - 41) {
                        if (boardY - 35 <= mouseY && mouseY <= boardY + 25) {
                            Chess.playAgain();
                            chessDisplay.playAgain();
                            Board.setState(Board.GameState.baseTurn);
                            possibleMoves.clear();
                            count = 0;
                        }
                    }
                    if (boardX + 69 <= mouseX && mouseX <= boardX + 97) {
                        if (boardY - 15 <= mouseY && mouseY <= boardY) {
                            Chess.frame.dispatchEvent(new WindowEvent(Chess.frame, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                    break;
                case pawnPromotion:
                    if (col == placeholderPiece.getCol()){
                        switch (placeholderPiece.getColor()){
                            case "Black":
                                if (row == placeholderPiece.getRow()){
                                    placeholderPiece = (new Queen("Queen", placeholderPiece.getColor(), placeholderPiece.getRow(), placeholderPiece.getCol()));
                                    break;
                                }
                                if(row == placeholderPiece.getRow()-1){
                                    placeholderPiece = (new Rook("Rook", placeholderPiece.getColor(), placeholderPiece.getRow(), placeholderPiece.getCol()));
                                    break;
                                }
                                if(row == placeholderPiece.getRow()-2){
                                    placeholderPiece = (new Knight("Knight", placeholderPiece.getColor(), placeholderPiece.getRow(), placeholderPiece.getCol()));
                                    break;
                                }
                                if(row == placeholderPiece.getRow()-3){
                                    placeholderPiece = (new Bishop("Bishop", placeholderPiece.getColor(), placeholderPiece.getRow(), placeholderPiece.getCol()));
                                    break;
                                }
                            case "White":
                                if(row == placeholderPiece.getRow()){
                                    placeholderPiece = (new Queen("Queen", placeholderPiece.getColor(), placeholderPiece.getRow(), placeholderPiece.getCol()));
                                    break;
                                }
                                if(row == placeholderPiece.getRow()+1){
                                    placeholderPiece = (new Rook("Rook", placeholderPiece.getColor(), placeholderPiece.getRow(), placeholderPiece.getCol()));
                                    break;
                                }
                                if(row == placeholderPiece.getRow()+2){
                                   placeholderPiece = (new Knight("Knight", placeholderPiece.getColor(), placeholderPiece.getRow(), placeholderPiece.getCol()));
                                   break;
                                }
                                if(row == placeholderPiece.getRow()+3){
                                    placeholderPiece = (new Bishop("Bishop", placeholderPiece.getColor(), placeholderPiece.getRow(), placeholderPiece.getCol()));
                                    break;
                                }
                        }
                        chessBoard.addPiece(placeholderPiece);
                        chessDisplay.setPieces(chessBoard);
                        chessDisplay.repaint();
                        Board.setState(Board.GameState.baseTurn);
                    }
            }
            if (chessBoard.checkPawnPromotion()){
                placeholderPiece = Pawn.pawnPromotion(chessBoard, chessDisplay, row, col);
                Board.setState(Board.GameState.pawnPromotion);
                }
    }

    private Piece getPieceAt(int row, int col) {
        for (Piece piece : chessBoard.getAllPieces()){
            if (piece.getRow() == row && piece.getCol() == col) {
                return piece;
            }
        }
        return null;
    }

    private void castle(BoardDisplay chessDisplay, Board chessBoard, Piece clickedPiece, int row, int col){
        if(Objects.equals(clickedPiece.getName(), "King")){
            switch(clickedPiece.getColor()){
                case "White":
                    if(!King.whiteKingMoved){
                        for (Tile moves : possibleMoves){
                            if (moves.getRow() == clickedPiece.getRow() && moves.getCol() == clickedPiece.getCol() + 2){
                                if (row == clickedPiece.getRow() && col == clickedPiece.getCol() + 2){
                                    clickedPiece.setPosition(row, col);
                                    chessBoard.findPiece(row, col + 1).setPosition(row, clickedPiece.getCol() - 1);
                                }
                            }
                            if (moves.getRow() == clickedPiece.getRow() && moves.getCol() == clickedPiece.getCol() - 2){
                                if (row == clickedPiece.getRow() && col == clickedPiece.getCol() - 2){
                                    clickedPiece.setPosition(row, col);
                                    chessBoard.findPiece(row, col - 2).setPosition(row, clickedPiece.getCol() + 1);
                                }
                            }
                        }
                    }
                    break;
                case "Black":
                    if(!King.blackKingMoved){
                        for (Tile moves : possibleMoves){
                            if (moves.getRow() == clickedPiece.getRow() && moves.getCol() == clickedPiece.getCol() + 2){
                                if (row == clickedPiece.getRow() && col == clickedPiece.getCol() + 2){
                                    clickedPiece.setPosition(row, col);
                                    chessBoard.findPiece(row, col + 1).setPosition(row, clickedPiece.getCol() - 1);
                                }
                            }
                            if (moves.getRow() == clickedPiece.getRow() && moves.getCol() == clickedPiece.getCol() - 2){
                                if (row == clickedPiece.getRow() && col == clickedPiece.getCol() - 2){
                                    clickedPiece.setPosition(row, col);
                                    chessBoard.findPiece(row, col - 2).setPosition(row, clickedPiece.getCol() + 1);
                                }
                            }
                        }
                    }
                    break;
            }
            chessDisplay.setPieces(chessBoard);
            chessDisplay.repaint();
        }
    }

    private void enPassant(BoardDisplay chessDisplay, Board chessBoard, Piece clickedPiece, int row, int col){
        if (Objects.equals(clickedPiece.getName(), "Pawn")) {
            switch (clickedPiece.getColor()) {
                case "White":
                    if (chessBoard.isTileOccupied(row + 1, col)) {
                        if (Objects.equals(chessBoard.findPiece(row + 1, col).getName(), "Pawn")) {
                            if(!Objects.equals(chessBoard.findPiece(row + 1, col).getColor(), clickedPiece.getColor())) {
                                chessBoard.deletePiece(chessBoard.findPiece(row + 1, col));
                            }
                        }
                    }
                    break;
                case "Black":
                    if (chessBoard.isTileOccupied(row - 1, col)) {
                        if (Objects.equals(chessBoard.findPiece(row - 1, col).getName(), "Pawn")) {
                            if(!Objects.equals(chessBoard.findPiece(row - 1, col).getColor(), clickedPiece.getColor())) {
                                chessBoard.deletePiece(chessBoard.findPiece(row - 1, col));
                            }
                        }
                    }
            }
            chessDisplay.setDeadPieces(chessBoard);
            chessDisplay.setPieces(chessBoard);
            chessDisplay.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
