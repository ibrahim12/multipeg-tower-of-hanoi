package MPTOH;


public class Deque {

    private int maxSize = 400;
    private final int[] array;
    private int front,  rear;
    public int numberOfItems;

    public Deque() {
        array = new int[maxSize];
        front = 0;
        rear = -1;
        numberOfItems = 0;
    }

    public boolean empty() {
        return (numberOfItems == 0);
    }

    public void push_front(int item) {
        if (front == 0) {
            front = maxSize;
        }
        array[--front] = item;
        numberOfItems++;
    }

    public void push_back(int item) {
        if (rear == maxSize - 1) {
            rear = -1;
        }
        array[++rear] = item;
        numberOfItems++;
    }

    public int pop_front() {
        int temp = array[front++];
        if (front == maxSize) {
            front = 0;
        }
        numberOfItems--;
        return temp;
    }

    public int pop_back() {

        int temp = array[rear--];
        if (rear == -1) {
            rear = maxSize - 1;
        }

        numberOfItems--;
        return temp;
    }

    public int front() {
        return array[front];
    }

    public int back() {
        return array[rear];
    }
}