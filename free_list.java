
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
public class free_list {
    public static void main(String[] args) {
        //dataset 
        FreeList list=new FreeList();
        list.free(4096); //free list intially one chunk of size 4096kb
        // requests for memeory allocation
       request[] requestList;
       requestList =new request[8];
        requestList[0]=new request(0, 10, 3);
        requestList[1]=new request(2, 20, 5);
        requestList[2]= new request(10, 50, 16);
        requestList[3]= new request(7, 30, 10);
        requestList[4]= new request(20, 100, 25);
        requestList[5]=new request(12, 120, 15);
        requestList[6]= new request(1, 5, 6);
        requestList[7]= new request(22, 500, 26);
       
        int[] time=new int[requestList.length];
        for (int i = 0; i < time.length; i++) {
            time[i]=requestList[i].arrivalTime;
        }
        Arrays.sort(time);
        Queue<request> RequestsQueue = new LinkedList<request>();
        
        int size=requestList.length;
        for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            if(time[i]==requestList[j].arrivalTime)
            {
                RequestsQueue.add(requestList[j]);
                break;
            }
         }
        }
        //end of dataset
        


        
        }


    
}

/*
 * new request(0, 10, 3),
         new request(2, 20, 5),
        new request(10, 50, 16),
        new request(7, 30, 10),
        new request(20, 100, 25),
        new request(12, 120, 15),
        new request(1, 5, 6),
        new request(22, 500, 26)
 */


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
class request{
    int arrivalTime;
    int size;
    int freeTime;
    public request(int aTime, int s, int fTime)
    {
        arrivalTime=aTime;
        size=s;
        freeTime=fTime;
    }
}