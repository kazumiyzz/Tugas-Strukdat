public interface StrukturData<T> {
    void add(T value);
    void insert(int position, T value);
    T get(int position);
    T set(int position, T value);
    T remove(int position);
    int size();
    boolean isEmpty();
    boolean contains(T value);
    void clear();
    void validateInvariant();
}