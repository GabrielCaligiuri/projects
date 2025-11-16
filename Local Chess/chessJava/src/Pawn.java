import java.util.ArrayList;
import java.util.Objects;

public class Pawn extends Piece{
    public static boolean enPassant = false;
    private static Piece promotingPawn;
    public Pawn(String name, String color, int row, int col) {
        super(name, color, row, col);
    }

    public ArrayList<Tile> possibleMoves(Board chessBoard, Piece piece){
        ArrayList<Tile> moves = new ArrayList<Tile>();

        if (Objects.equals(piece.getColor(), "White")) {
            if (chessBoard.isTileOccupied(piece.getRow()-1, piece.getCol()+1)) {
                if (!Objects.equals(chessBoard.findPiece(piece.getRow() - 1, piece.getCol() + 1).getColor(), piece.getColor())) {
                    moves.add(new Tile(piece.getRow() - 1, piece.getCol() + 1));
                }
            }
            if (chessBoard.isTileOccupied(piece.getRow()-1, piece.getCol()-1)) {
                if (!Objects.equals(chessBoard.findPiece(piece.getRow() - 1, piece.getCol() - 1).getColor(), piece.getColor())){
                    moves.add(new Tile(piece.getRow()-1, piece.getCol()-1));
                }
            }
            if(!chessBoard.isTileOccupied(piece.getRow() - 2, piece.getCol())) {
                if(!chessBoard.isTileOccupied(piece.getRow() - 1, piece.getCol())) {
                    if (piece.getRow() == 6) {
                        moves.add(new Tile(piece.getRow() - 2, piece.getCol()));
                    }
                }
            }
            if(!chessBoard.isTileOccupied(piece.getRow() - 1, piece.getCol())) {
                moves.add(new Tile(piece.getRow() - 1, piece.getCol()));
            }
            if (enPassant){
                if (piece.getRow() == 3) {
                    if (chessBoard.isTileOccupied(piece.getRow(), piece.getCol() - 1)) {
                        if (Objects.equals(chessBoard.findPiece(piece.getRow(), piece.getCol() - 1).getName(), "Pawn")) {
                            moves.add(new Tile(piece.getRow() - 1, piece.getCol() - 1));
                        }
                    }
                    if (chessBoard.isTileOccupied(piece.getRow(), piece.getCol() + 1)) {
                        if (Objects.equals(chessBoard.findPiece(piece.getRow(), piece.getCol() + 1).getName(), "Pawn")) {
                            moves.add(new Tile(piece.getRow() - 1, piece.getCol() + 1));
                        }
                    }
                }
            }
        }
        if (Objects.equals(piece.getColor(), "Black")) {
            if (chessBoard.isTileOccupied(piece.getRow()+1, piece.getCol()+1)) {
                if (!Objects.equals(chessBoard.findPiece(piece.getRow() + 1, piece.getCol() + 1).getColor(), piece.getColor())) {
                    moves.add(new Tile(piece.getRow() + 1, piece.getCol() + 1));
                }
            }
            if (chessBoard.isTileOccupied(piece.getRow()+1, piece.getCol()-1)) {
                if (!Objects.equals(chessBoard.findPiece(piece.getRow() + 1, piece.getCol() - 1).getColor(), piece.getColor())) {
                    moves.add(new Tile(piece.getRow() + 1, piece.getCol() - 1));
                }
            }
            if(!chessBoard.isTileOccupied(piece.getRow() + 2, piece.getCol())) {
                if(!chessBoard.isTileOccupied(piece.getRow() + 1, piece.getCol())) {
                    if (piece.getRow() == 1) {
                        moves.add(new Tile(piece.getRow() + 2, piece.getCol()));
                    }
                }
            }
            if(!chessBoard.isTileOccupied(piece.getRow() + 1, piece.getCol())) {
                moves.add(new Tile(piece.getRow() + 1, piece.getCol()));
            }
            if (enPassant){
                if (piece.getRow() == 4) {
                    if (chessBoard.isTileOccupied(piece.getRow(), piece.getCol() - 1)) {
                        if (Objects.equals(chessBoard.findPiece(piece.getRow(), piece.getCol() - 1).getName(), "Pawn")) {
                            moves.add(new Tile(piece.getRow() + 1, piece.getCol() - 1));
                        }
                    }
                    if (chessBoard.isTileOccupied(piece.getRow(), piece.getCol() + 1)) {
                        if (Objects.equals(chessBoard.findPiece(piece.getRow(), piece.getCol() + 1).getName(), "Pawn")) {
                            moves.add(new Tile(piece.getRow() + 1, piece.getCol() + 1));
                        }
                    }
                }
            }
        }
        return moves;
    }

    public static Piece pawnPromotion(Board chessboard, BoardDisplay boardDisplay, int col, int row){
        ArrayList<Piece> pieces = chessboard.getAllPieces();
        for(Piece piece : chessboard.getAllPieces()){
            switch (piece.getColor()) {
                case "Black":
                    if (Objects.equals(piece.getName(), "Pawn")) {
                        if (piece.getRow() == 7) {
                            promotingPawn = piece;
                        }
                    }
                    break;
                case "White":
                    if (Objects.equals(piece.getName(), "Pawn")) {
                        if (piece.getRow() == 0) {
                            promotingPawn = piece;
                        }
                    }
                    break;
            }
        }
        pieces.remove(promotingPawn);
        int pieceRow = promotingPawn.getRow();
        boardDisplay.pawnPromotionDisplay(chessboard, promotingPawn);
        return promotingPawn;
    }
}
