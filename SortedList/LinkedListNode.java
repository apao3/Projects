import java.util.Optional;

public class LinkedListNode<T> {
    public T value;
    public int count;
    public Optional<LinkedListNode<T>> next;
    public Optional<LinkedListNode<T>> prev;

    public LinkedListNode(T value, int count) {
        this.value = value;
        this.count = count;
        this.next = Optional.empty();
        this.prev = Optional.empty();
    }
}
