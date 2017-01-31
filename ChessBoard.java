import java.util.*;
import java.io.*;

public class ChessBoard {
  int boardSize;
  public static LinkedList pieces;
  
  
  //LinkedList<ChessPiece> pieces = new LinkedList<ChessPiece>();
  
  ChessBoard (int boardSize){
    this.boardSize = boardSize;
  }
  
  //Add chess piece to linked list
  void addPiece(ChessPiece piece) {
    pieces.add(piece);
  }
  
  //Create new board and add pieces from input line to the board 
  static ChessBoard createNewBoard(String boardLine) {
    String [] line1Tokens = boardLine.split("\\s+");
    
    int boardSize = Integer.parseInt(line1Tokens[0]);
    ChessBoard board = new ChessBoard(boardSize);
    
    for (int i = 1; i < line1Tokens.length; i = i + 3 ) {
      char pieceType = line1Tokens[i].charAt(0);
      int col = Integer.parseInt(line1Tokens[i+1]);
      int row = Integer.parseInt(line1Tokens[i+2]);
      ChessPiece piece = createChessPiece(pieceType, col, row);
      board.addPiece(piece);
    }
    return board;
  }
  
  //Makes sure that no pieces are placed in the same square 
  boolean validatePieces () {
    for (int i = 0; i < pieces.size(); i++) {
      ChessPiece currentPiece = pieces.get(i);
      for (int j = 0; j < pieces.size(); j++) {
        //Makes sure that you aren't comparing the same pieces 
        if (j != i) {
          ChessPiece nextPiece = pieces.get(j);
          if (currentPiece.col == nextPiece.col && currentPiece.row == nextPiece.row) {
            return false;
          }
        }
      }
    }
    return true;
  }
  // if there is not exactly one black and one white king, return false
  boolean validateKings () {
    boolean whiteKing = false;
    int white = 0;
    boolean blackKing = false;
    int black = 0;
    for (int i = 0; i < pieces.size(); i ++ ) {
      ChessPiece c = pieces.get(i);
      if (c.piece == 'k') {
        whiteKing = true;
        white++;
      }
      if (c.piece == 'K') {
        blackKing = true;
        black++;
      }
    }
    if (whiteKing && blackKing) {
      if (white == 1 && black == 1) {
        return true;
      }
    }
    return false;
  }
  
  
  //Returns a string of the type of chess piece from coordinates of 2nd line of input
  //Returns "-" if no piece is found at query square
  String querySquare(int col, int row) {
//    for (ChessPiece piece : pieces) {
//      if (piece.col == col && piece.row == row) {
//        return Character.toString(piece.piece);
//      }
//    }
    for (int i = 0; i < pieces.size(); i++) {
      ChessPiece piece = pieces.get(i);
      if (piece.col == col && piece.row == row) {
        return Character.toString(piece.piece);
      }
    }
    return "-";
  }
  
  //Returns string of attacking pieces' type, col, row to analysis.txt
String determineAttackPieces() {
  for (int i = 0; i < pieces.size(); i++) {
   ChessPiece currentPiece = pieces.get(i);
   for (int j = 0; j < pieces.size(); j++) {
    //Makes sure that you aren't comparing the same pieces 
    if (j != i) {
     ChessPiece nextPiece = pieces.get(j);
     if (currentPiece.isAttacking(nextPiece)) {
      return currentPiece.piece + " " +currentPiece.col + " " + currentPiece.row + " " + nextPiece.piece + " " +nextPiece.col + " " + nextPiece.row;
     }
    }
   }
  }
  return "-";
 }
  
  static ChessPiece createChessPiece(char type, int col, int row) {
    char lowerPiece = Character.toLowerCase(type);
    ChessPiece piece = null;
    
    if (lowerPiece == 'k') {
      piece = new King (type, col, row);
    }
    else if (lowerPiece == 'q') {
      piece = new Queen (type, col, row);
    }
    else if (lowerPiece == 'r') {
      piece = new Rook (type, col, row);
    }
    else if (lowerPiece == 'b') {
      piece = new Bishop (type, col, row);
    }
    else if (lowerPiece == 'n') {
      piece = new Knight (type, col, row);
    }
    
    return piece;
  }
  
  //If chess board is valid, return string of piece at query square and attacking pieces with their location 
  //If chess board is invalid, return string "Invalid"
  static String analyzeBoard (ChessBoard board, String queryLine) {
    if (board.validatePieces() && board.validateKings()) {
      String [] line2Tokens = queryLine.split("\\s+");
      int col = Integer.parseInt(line2Tokens[0]);
      int row = Integer.parseInt(line2Tokens[1]);
      String valid = board.querySquare(col, row) + " " + board.determineAttackPieces();
      return valid;
    }
    
    return "Invalid";
  }
  
  //main method
  public static void main(String[] args) throws IOException{
    //open files
    Scanner in = new Scanner(new File("input.txt"));
    PrintWriter out = new PrintWriter(new FileWriter("analysis.txt"));
    //read lines from input.txt
    while (in.hasNextLine()){
      pieces = new LinkedList();
      String line1 = in.nextLine();
      String line2 = in.nextLine();
      ChessBoard board = createNewBoard(line1);
      String results = analyzeBoard(board, line2);
      out.println(results);
    }
    in.close();
    out.close();
  }
}
