@SuppressWarnings("unchecked")
public class CustomQueue<T> {
    private Object[] storage;
    private int head;
    private int tail;
    private int count;
    private static final int INITIAL_CAPACITY = 16;

    public CustomQueue() {
        storage = new Object[INITIAL_CAPACITY];
        head = 0;
        tail = 0;
        count = 0;
    }

    public void enqueue(T value) {
        if (count == storage.length) {
            Object[] newStorage = new Object[storage.length * 2];
            for (int i = 0; i < count; i++) {
                newStorage[i] = storage[(head + i) % storage.length];
            }
            storage = newStorage;
            head = 0;
            tail = count;
        }
        storage[tail] = value;
        tail = (tail + 1) % storage.length;
        count++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue underflow");
        }
        T val = (T) storage[head];
        storage[head] = null;
        head = (head + 1) % storage.length;
        count--;
        return val;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return (T) storage[head];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }
}