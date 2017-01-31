import java.util.*;
import java.io.*;

public class Node {
  ChessPiece value;
  Node next;
  
  public Node(ChessPiece val){
    value = val;
    next = null;
  }
  
  public ChessPiece getVal() {
    return value;
  }
  public void setVal(ChessPiece val) {
    value = val;
  }
  public Node getNext() {
    return next;
  }
  public void setNext(Node next) {
    this.next = next;
  }
}