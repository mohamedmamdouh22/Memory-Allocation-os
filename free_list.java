public class free_list {
    public static void main(String[] args) {
        FreeList list=new FreeList();
        list.free(2);
        list.free(3);
        list.free(1);
       
        list.displayFreeList();
        }


    
}




class Node
{

long size; 
Node next;


public Node(long s) 
{ size = s; }
public void displayNode() 
{ 
    System.out.print( size +" " );

}

}


class FreeList{
private Node first; 

public FreeList() {
 first = null;
 }

public boolean isEmpty(){ 
 return (first==null);
 
}
public void free(long elem) 
 { 
 Node newLink = new Node(elem);
 Node curr=first;
 


 if(isEmpty())
    {
        first = newLink;
}
else if(elem<= curr.size){

 newLink.next = first; 
 first = newLink;

}
else{

    while(curr.next!=null && elem>curr.next.size)
    {
      curr=curr.next;
    } 
    newLink.next=curr.next;
    curr.next=newLink;


}

 }



public void displayFreeList()
 {
 Node current = first; 

 while(current != null) 
 {
    current.displayNode(); 
    current = current.next; 
}

System.out.println("");


}

}


