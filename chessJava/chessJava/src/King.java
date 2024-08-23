import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class King extends Piece {
    private static final int BOARD_SIZE = BoardDisplay.BOARD_SIZE - 1;
    public static Piece checkedPiece;
    public static boolean whiteKingMoved = false;
    public static boolean blackKingMoved = false;

    public King(String name, String color, int row, int col) {
        super(name, color, row, col);
    }

    @Override
    public ArrayList<Tile> possibleMoves(Board chessBoard, Piece piece) {
        ArrayList<Tile> moves = new ArrayList<Tile>();
        boolean ifRow;
        boolean ifCol;
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                if(row == 0 && col == 0){
                    continue;
                }
                ifRow = false;
                ifCol = false;
                if (0 <= piece.getRow() + row && piece.getRow() + row <= BOARD_SIZE && row != 0) {
                    if (chessBoard.isTileOccupied(piece.getRow() + row, piece.getCol())) {
                        if (!Objects.equals(chessBoard.findPiece(piece.getRow() + row, piece.getCol()).getColor(), piece.getColor())) {
                            moves.add(new Tile(piece.getRow() + row, piece.getCol()));
                        }
                    } else {
                        moves.add(new Tile(piece.getRow() + row, piece.getCol()));
                    }
                    ifRow = true;
                }
                if (0 <= piece.getCol() + col && piece.getCol() + col <= BOARD_SIZE && col != 0) {
                    if (chessBoard.isTileOccupied(piece.getRow(), piece.getCol() + col)) {
                        if (!Objects.equals(chessBoard.findPiece(piece.getRow(), piece.getCol() + col).getColor(), piece.getColor())) {
                            moves.add(new Tile(piece.getRow(), piece.getCol() + col));
                        }
                    } else {
                        moves.add(new Tile(piece.getRow(), piece.getCol() + col));
                    }
                    ifCol = true;
                }
                if (ifCol && ifRow) {
                    if (chessBoard.isTileOccupied(piece.getRow() + row, piece.getCol() + col)) {
                        if (!Objects.equals(chessBoard.findPiece(piece.getRow() + row, piece.getCol() + col).getColor(), piece.getColor())) {
                            moves.add(new Tile(piece.getRow() + row, piece.getCol() + col));
                        }
                    } else {
                        moves.add(new Tile(piece.getRow() + row, piece.getCol() + col));
                    }
                }
            }
        }
        switch(piece.getColor()){
            case "White":
                if (!whiteKingMoved){
                    if (!chessBoard.isTileOccupied(piece.getRow(), piece.getCol() + 1)){
                        if (!chessBoard.isTileOccupied(piece.getRow(), piece.getCol() + 2)){
                            moves.add(new Tile(piece.getRow(), piece.getCol() + 2));
                        }
                    }
                    if (!chessBoard.isTileOccupied(piece.getRow(), piece.getCol() - 1)){
                        if (!chessBoard.isTileOccupied(piece.getRow(), piece.getCol() - 2)){
                            moves.add(new Tile(piece.getRow(), piece.getCol() - 2));
                        }
                    }
                }
                break;
            case "Black":
                if (!blackKingMoved){
                    if (!chessBoard.isTileOccupied(piece.getRow(), piece.getCol() - 1)){
                        if (!chessBoard.isTileOccupied(piece.getRow(), piece.getCol() - 2)){
                            moves.add(new Tile(piece.getRow(), piece.getCol() - 2));
                        }
                    }
                    if (!chessBoard.isTileOccupied(piece.getRow(), piece.getCol() + 1)){
                        if (!chessBoard.isTileOccupied(piece.getRow(), piece.getCol() + 2)){
                            moves.add(new Tile(piece.getRow(), piece.getCol() + 2));
                        }
                    }
                }
        }
        return moves;
    }

    public static boolean confirmCheck(ArrayList<Tile> moves, Piece king){
        for(Tile move : moves){
            if (move.getRow() == king.getRow() && move.getCol() == king.getCol()){
                return true;
            }
        }
        return false;
    }

    public static Boolean kingChecked(Board chessBoard, Player player, Piece savedKing) {
        ArrayList<Piece> pieces = chessBoard.getAllPieces();
        ArrayList<Tile> moves;
        Piece king = null;
        if (savedKing == null){
            for (Piece piece : pieces) {
                if (Objects.equals(piece.getName(), "King")) {
                    if (Objects.equals(piece.getColor(), player.getColor())) {
                        king = piece;
                        break;
                    }
                }
            }
        } else{
            king = savedKing;
        }
        if (king != null) {
            for (Piece piece : pieces) {
                if (!Objects.equals(piece.getColor(), king.getColor())) {
                        moves = piece.possibleMoves(chessBoard, piece);
                        if (confirmCheck(moves, king)){
                            checkedPiece = piece;
                            return true;
                        }
                    }
                }
            }
        return false;
    }
}


