@SuppressWarnings("unchecked")
public class CustomStack<T> {
    private Object[] storage;
    private int top;
    private static final int INITIAL_CAPACITY = 16;

    public CustomStack() {
        storage = new Object[INITIAL_CAPACITY];
        top = -1;
    }

    public void push(T value) {
        if (top == storage.length - 1) {
            Object[] newStorage = new Object[storage.length * 2];
            System.arraycopy(storage, 0, newStorage, 0, storage.length);
            storage = newStorage;
        }
        storage[++top] = value;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack underflow");
        }
        T val = (T) storage[top];
        storage[top--] = null;
        return val;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return (T) storage[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}