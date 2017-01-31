import java.io.*;
import java.util.*;

public class LinkedList{
  Node head;
  int count;
  public ChessPiece get(int i){
    Node current = head;
    int n = 0;
    while(current.next !=null && n<i){
      current = current.getNext();
      n++;
    }
    return current.getVal();
  }
  public LinkedList(){
    //add ChessPiece to linked list
  }
  public void remove(){
    head = null;
    count = 0;
  }
  public int size(){
    return count;
  }
  public void add(ChessPiece value){
    if (head==null){
      head = new Node(value);
    }
    Node temp = new Node(value);
    Node current = head;
    if(current != null){
      while (current.getNext()!=null){
        current = current.getNext();
      }
      current.setNext(temp);
    }
    count++;
  }
}