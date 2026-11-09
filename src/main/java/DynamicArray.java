@SuppressWarnings("unchecked")
public class DynamicArray<T> implements StrukturData<T> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public DynamicArray() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public DynamicArray(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative: " + capacity);
        this.elements = new Object[capacity];
        this.size = 0;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = Math.max(elements.length * 2, minCapacity);
            Object[] newArray = new Object[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
    }

    @Override
    public void add(T value) {
        ensureCapacity(size + 1);
        elements[size++] = value;
        validateInvariant();
    }

    @Override
    public void insert(int position, T value) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Position: " + position + ", Size: " + size);
        }
        ensureCapacity(size + 1);
        System.arraycopy(elements, position, elements, position + 1, size - position);
        elements[position] = value;
        size++;
        validateInvariant();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Position: " + position + ", Size: " + size);
        }
        return (T) elements[position];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int position, T value) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Position: " + position + ", Size: " + size);
        }
        T old = (T) elements[position];
        elements[position] = value;
        return old;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Position: " + position + ", Size: " + size);
        }
        T removed = (T) elements[position];
        int numMoved = size - position - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, position + 1, elements, position, numMoved);
        }
        elements[--size] = null;
        validateInvariant();
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == null ? value == null : elements[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        validateInvariant();
    }

    @Override
    public void validateInvariant() {
        if (size < 0 || size > elements.length) {
            throw new IllegalStateException("Invariant violated: size=" + size + ", capacity=" + elements.length);
        }
    }
}