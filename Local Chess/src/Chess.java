import javax.swing.*;

public class Chess {
    //creates a new Jframe instance titled chess
    public static JFrame frame = new JFrame("Chess");
    //creates a new BoardDisplay
    private static BoardDisplay chessDisplay = new BoardDisplay();
    //creates a new Board
    private static Board chessBoard = new Board();
    //creates a player to help rotate turns
    private static Player player = new Player("White");

    public static void main(String[] args) {
        //calls the starting function
        displayStart(chessDisplay, chessBoard, frame);
        //sets the state of the board to baseTurn
        Board.setState(Board.GameState.baseTurn);
        //creates a mouseListener to allow the game to run and take mouse clicks
        chessDisplay.addMouseListener(new ChessMouseListener(chessBoard, chessDisplay, player));
    }

    //called when the user selects play again after the game is over
    public static void playAgain(){
        //sets color to have first turn as white
        player.setColor("White");
        //resets the board and the display
        chessBoard.resetBoard();
        chessDisplay.resetBoard(chessBoard);
        //recalls displayStart
        displayStart(chessDisplay, chessBoard, frame);
    }

    public static void displayStart(BoardDisplay chessDisplay, Board chessBoard, JFrame frame){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //adds the display to the frame
        frame.add(chessDisplay);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //sets up chessBoard
        chessBoard.setupBoard();
        //sets up chessDisplay
        chessDisplay.setPieces(chessBoard);
        chessDisplay.loadPieceImages();
        //repaint
        chessDisplay.repaint();
    }


}
