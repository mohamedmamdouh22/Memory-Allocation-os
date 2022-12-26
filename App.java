package com.mycompany.app;

import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        // dataset
        FreeList list = new FreeList();
        list.free(100); // free list intially one chunk of size 4096kb
        // requests for memeory allocation
        request[] requestList;
        requestList = new request[8];
        requestList[0] = new request(0, 10, 3); // 1
        requestList[1] = new request(2, 20, 5); // 3
         requestList[2]= new request(10, 50, 16); //5
        requestList[3] = new request(7, 5, 10); // 4
         requestList[4]= new request(20, 50, 25); //7
         requestList[5]=new request(12, 120, 15); //6
         requestList[6]= new request(1, 5, 6); //2
         requestList[7]= new request(22, 15, 16);//8

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
        System.out.println("press 1 for worst fit \npress 2 for best fit \npress 3 for first fit");
        int x=input.nextInt();
        if(x==1)
            worestFit(RequestsQueue, list);
        else if(x==2)
            bestFit(RequestsQueue, list);
        else 
            firstFit(RequestsQueue, list);

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
                System.out.println("Request number ["+i+"]" + " with "+ next.size+" requested is allocated in block size " + max.size);
                max.size -= next.size;
                 if(max.size==0){
                    list.remove(max);
                }

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
        for (int i = 0; i < sizes.length; i++) {
            if(fTimes[i]!=100000){
                list.free(sizes[i]);
            }
        }
        System.out.println("*********************************************************");
        System.out.println("Free list after all allocations");
        list.displayFreeList();
        
    }
    
    public static void bestFit(Queue<request> q, FreeList list) {
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
             
            // best fit algorithm
            System.out.println("free list befor allocating: ");
            list.displayFreeList();
            int total = 100;
            long free = list.TotalSize();

            System.out.println("Total size " + total + " used: " + (total - free) + " free: " + free);
            Node curr = list.f();
            int x=0;
            Node best=curr;
            
            while (curr != null) {
                
                if (best.size<curr.size) {
                    best=curr;
                }
                curr=curr.next;
                
            }
            curr = list.f();
            while (curr != null) {
                
                if (best.size>curr.size&&curr.size>=next.size) {
                    best=curr;
                }
                curr=curr.next;
                
            }
            
            
            
            if(next.size>best.size) {
                System.out.println("Request size "+next.size+" can not be allocated");

            }else{
                System.out.println("Request number ["+i+"]" + " with "+ next.size+" requested is allocated in block size "+best.size);
                best.size-=next.size;
                x=1;
            }
            
            System.out.println("free list after allocationg");
            list.displayFreeList();

            //=======================
            System.out.println("*********************************************************");
            if(x==1){ //sucessfully allocated
                fTimes = Arrays.copyOf(fTimes, fTimes.length + 1);
                fTimes[fTimes.length - 1] = next.freeTime;
                sizes = Arrays.copyOf(sizes, sizes.length + 1);
                sizes[sizes.length - 1] = next.size;
    
            }
            
        }
        for (int i = 0; i < sizes.length; i++) {
            if(fTimes[i]!=100000){
                list.free(sizes[i]);
            }
        }
        System.out.println("*********************************************************");
        System.out.println("Free list after all allocations");
        list.displayFreeList();
    }
    
    public static void firstFit(Queue<request> q, FreeList list) {
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
            System.out.println("free list befor allocating: ");
            list.displayFreeList();
            int total = 100;
            long free = list.TotalSize();

            System.out.println("Total size " + total + " used: " + (total - free) + " free: " + free);

            Node curr = list.f();
            int x = 0;

            Node first_fit = curr;
            while (curr != null) {
                if(curr.size>=next.size)
                {
                    first_fit=curr;
                    break;
                    
                }
                    
                curr=curr.next;
            }
            if (next.size > first_fit.size) {
                System.out.println("Request size " + next.size + " can not be allocated");

            } else {
                System.out.println("Request number ["+i+"]" + " with "+ next.size+" requested is allocated in block size "+ first_fit.size);
                first_fit.size -= next.size;
                // if(first_fit.size==0)
                  //  list.remove(first_fit);
                

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
        for (int i = 0; i < sizes.length; i++) {
            if(fTimes[i]!=100000){
                list.free(sizes[i]);
            }
        }
        System.out.println("*********************************************************");
        System.out.println("Free list after all allocations");
        list.displayFreeList();   
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
        if(!isEmpty()){
        Node current = first;

        while (current != null) {
            if(current.size==0)
                current = current.next;
            else{
            current.displayNode();
            current = current.next;}
        }

        System.out.println("");
    }else{
        System.out.println(("empty free list"));
    }
    }

    public void remove(Node x) {
        Node current = first;
        Node prev = null;
        while (current != null) {
            if (current == x) {
                break;
            }
            prev = current;
            current = current.next;

        }
        if(current==first)
        {
            first=null;
        }else 
        prev.next=current.next;
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
