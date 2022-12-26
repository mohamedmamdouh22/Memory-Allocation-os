
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

public class App {
    public static void main(String[] args) {
        // dataset
        FreeList list = new FreeList();
        list.free(30); // free list intially one chunk of size 4096kb
        // requests for memeory allocation
        request[] requestList;
        requestList = new request[3];
        requestList[0] = new request(0, 10, 3); // 1
        requestList[1] = new request(2, 20, 5); // 3
        // requestList[2]= new request(10, 50, 16); //5
        requestList[2] = new request(7, 5, 10); // 4
        // requestList[4]= new request(20, 100, 25); //7
        // requestList[5]=new request(12, 120, 15); //6
        // requestList[6]= new request(1, 5, 6); //2
        // requestList[7]= new request(22, 500, 26);//8

        int[] time = new int[requestList.length];
        for (int i = 0; i < time.length; i++) {
            time[i] = requestList[i].arrivalTime;
        }
        Arrays.sort(time);
        Queue<request> RequestsQueue = new LinkedList<request>();

        int size = requestList.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (time[i] == requestList[j].arrivalTime) {
                    RequestsQueue.add(requestList[j]);
                    break;
                }
            }
        }
        // end of dataset

        worestFit(RequestsQueue, list);

    }// end main

    public static void worestFit(Queue<request> q, FreeList list) {
        int reqs = q.size(); // no of requests in queue
        int[] fTimes = new int[0];
        int[] sizes = new int[0];

        for (int i = 0; i < reqs; i++) {

            request next = q.remove();
            for (int j = 0; j < sizes.length; j++) {
                if (fTimes[j] < next.arrivalTime) {
                    list.free(sizes[j]);
                    fTimes[j] = 100000;
                }
            }

            // first fit algorithm
            // System.out.println("free list befor allocating: ");
            // list.displayFreeList();
            // Node current=list.f();
            // while(current!=null)
            // {
            // if(current.size>next.size){
            // System.out.println("request size: "+next.size +" allocated in block size:
            // "+current.size);
            // current.size-=next.size;

            // break;
            // }
            // current=current.next;
            // }
            // System.out.println("free list after allocationg");
            // list.displayFreeList();

            // System.out.println("*********************************************************");

            // worst fit algorithm
            System.out.println("free list befor allocating: ");
            list.displayFreeList();
            int total = 100;
            long free = list.TotalSize();

            System.out.println("Total size " + total + " used: " + (total - free) + " free: " + free);

            Node curr = list.f();
            int x = 0;

            Node max = curr;
            while (curr.next != null) {
                if (curr.size < curr.next.size) {
                    max = curr.next;
                }
                curr = curr.next;
            }
            if (next.size > max.size) {
                System.out.println("Request size " + next.size + " can not be allocated");

            } else {
                System.out.println("Request size " + next.size + " allocated in block size " + max.size);
                max.size -= next.size;

                x = 1;
            }

            System.out.println("free list after allocationg");
            list.displayFreeList();

            System.out.println("*********************************************************");
            if (x == 1) { // sucessfully allocated
                fTimes = Arrays.copyOf(fTimes, fTimes.length + 1);
                fTimes[fTimes.length - 1] = next.freeTime;
                sizes = Arrays.copyOf(sizes, sizes.length + 1);
                sizes[sizes.length - 1] = next.size;

            }

            // 0
            // 10

        }
    }

}

class Node {

    long size;
    Node next;

    public Node(long s) {
        size = s;
    }

    public void displayNode() {
        System.out.print(size + " ");

    }

}

class FreeList {
    private Node first;
    long totalSize;

    public FreeList() {
        first = null;
        totalSize = 0;
    }

    public boolean isEmpty() {
        return (first == null);

    }

    public Node f() {
        return first;
    }

    public void free(long elem) {
        Node newLink = new Node(elem);
        Node curr = first;

        if (isEmpty()) {
            first = newLink;
        } else if (elem <= curr.size) {

            newLink.next = first;
            first = newLink;

        } else {

            while (curr.next != null && elem > curr.next.size) {
                curr = curr.next;
            }
            newLink.next = curr.next;
            curr.next = newLink;

        }

    }

    public long TotalSize() {
        long totalSize = 0;
        Node current = first;
        while (current != null) {
            totalSize += current.size;
            current = current.next;
        }
        return totalSize;

    }

    public void displayFreeList() {
        Node current = first;

        while (current != null) {
            current.displayNode();
            current = current.next;
        }

        System.out.println("");

    }

    public void remove(Node x) {
        Node current = first;
        Node prev = null;
        while (current != null) {
            if (current == x) {
                prev.next = current.next;
                break;

            }
            prev = current;
            current = current.next;

        }

    }

}

class request {
    int arrivalTime;
    int size;
    int freeTime;

    public request(int aTime, int s, int fTime) {
        arrivalTime = aTime;
        size = s;
        freeTime = fTime;
    }
}