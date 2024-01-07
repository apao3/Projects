import SortedList.LinkedListNode;

import javax.swing.text.html.Option;
import java.util.Iterator;
import java.util.Optional;

/**
 * A linked list that stores its elements in sorted order.
 * <p>
 * When an element is inserted into the list (or updated), it is positioned such that
 * <tt>node.next</tt> is the next greater value being stored in the list and <tt>node.prev</tt> is
 * the next lesser value being stored in the list. Duplicate values are stored in a single list
 * node, with the node also holding a count of the number of elements with that value.
 * <p>
 * SortedList also has "hinted" variants of several methods, where the caller may provide a
 * reference to a node that is close to the search term in the sorted list.  If this is actually the
 * case, the runtime of these methods, which is normally linear <i>in the size of the list</i> will
 * drop to linear in the number of records between the hint and the search term.
 *
 * @param <T> The type of value stored in the list
 */
public class SortedList<T extends Comparable<T>> implements Iterable<T> {
    /**
     * Reference to the first node in the list, or null if list is empty
     */
    public LinkedListNode<T> headNode;
    /**
     * Reference to the last node in the list, or null if list is empty
     */
    public LinkedListNode<T> lastNode;
    /**
     * Number of elements in the list (which may be different from number of nodes)
     */
    public int length;

    /**
     * Creates an empty SortedList
     */
    public SortedList() {
        this.headNode = null;
        this.lastNode = null;
        this.length = 0;
    }

    /**
     * Find a reference to the node holding elem, or the node that would precede it
     *
     * @param elem the element to find
     * @return the node containing the greatest element less than or equal to <tt>elem</tt>
     * <p>
     * If the list contains <tt>elem</tt>, this function should return the list node containing it
     * <p>
     * If the list does not contain <tt>elem</tt>, this function should return a reference to the
     * node that would precede <tt>elem</tt> if it were in the list, or <tt>Optional.empty()</tt>
     * if
     * <tt>elem</tt> is smaller than the smallest element in the list.
     * <p>
     * This function should run in O(length)
     */
    public Optional<LinkedListNode<T>> findRefBefore(T elem) {
        LinkedListNode<T> current = this.headNode;

        while (current != null) {
            if (this.headNode.value.equals(elem)) {
                return Optional.of(this.headNode);
            }
            if (current.value.compareTo(elem) == 0) {
                return Optional.of(current);
            }
            else if (this.headNode.value.compareTo(elem) > 0) {
                return Optional.empty();
            }
            else if (current.value.compareTo(elem) > 0) {
                return current.prev;
            }
            else if (!current.next.isPresent()) {
                return Optional.of(this.lastNode);
            }
            current = current.next.get();
        }
        return Optional.empty();
    }

    /**
     * Find a reference to the node holding elem, or the node that would precede it
     *
     * @param elem the element to find
     * @param hint a reference to a node "close" to <tt>elem</tt>
     * @return the node containing the greatest element less than or equal to <tt>elem</tt>
     * <p>
     * If the list contains <tt>elem</tt>, this function should return the list node containing it
     * <p>
     * If the list does not contain <tt>elem</tt>, this function should return a reference to the
     * node that would precede <tt>elem</tt> if it were in the list, or <tt>Optional.empty()</tt> if
     * <tt>elem</tt> is smaller than the smallest element in the list.
     * <p>
     * If <tt>hint</tt> is at position i, and <tt>elem</tt> would be at position j, then this
     * function should run in O(|i-j|)
     */
    public Optional<LinkedListNode<T>> findRefBefore(T elem, LinkedListNode<T> hint) {
        LinkedListNode<T> current = hint;

        if (current.value.compareTo(elem) == 0) { //If starting point = elem, return
            return Optional.of(current);
        } else if (this.headNode.value.compareTo(elem) > 0) {
            return Optional.empty();
        } else if (this.lastNode.value.compareTo(elem) < 0) {
            return Optional.of(lastNode);
        }

        if (current.value.compareTo(elem) < 0) { //If starting point < elem, iterate forward
            while (current.next.isPresent()) {
                current = current.next.get();
                if (current.value.compareTo(elem) == 0) {
                    return Optional.of(current);
                } else if (current.value.compareTo(elem) > 0) {
                    return current.prev;
                }
            }
            return Optional.of(this.lastNode);
        }

        if (current.value.compareTo(elem) > 0) {
            while (current.prev.isPresent()) {
                if (current.value.compareTo(elem) == 0) {
                    return Optional.of(current);
                } else if (current.value.compareTo(elem) < 0) {
                    return Optional.of(current);
                }
                current = current.prev.get();
            }
            return Optional.of(this.headNode);
        }
        return Optional.empty();
    }

    /**
     * Find a reference to the specified element
     *
     * @param elem the element to find
     * @return <tt>Optional.of(node)</tt> where <tt>node</tt> contains <tt>elem</tt>, or
     * <tt>Optional.empty()</tt> if <tt>elem</tt> is not in the list
     * <p>
     * This function should run in O(length)
     */
    public Optional<LinkedListNode<T>> findRef(T elem) {
        LinkedListNode<T> current = this.headNode;

        if (current == null) {
            return Optional.empty();
        }
        while (current.value.compareTo(elem) != 0) {
            if (current.next.equals(Optional.empty())) {
                return Optional.empty();
            }
            current = current.next.get();
        }
        if (current.value.compareTo(elem) == 0) {
            return Optional.of(current);
        }
        return Optional.empty();
    }

    /**
     * Find a reference to the specified element
     *
     * @param elem the element to find
     * @param hint a reference to a node "close" to <tt>elem</tt>
     * @return <tt>Optional.of(node)</tt> where <tt>node</tt> contains <tt>elem</tt>, or
     * <tt>Optional.empty()</tt> if <tt>elem</tt> is not in the list
     * <p>
     * If <tt>hint</tt> is at position i, and <tt>elem</tt> would be at position j, then this
     * function should run in O(|i-j|)
     */
    public Optional<LinkedListNode<T>> findRef(T elem, LinkedListNode<T> hint) {
        LinkedListNode<T> current = hint;

        if (current.value.compareTo(elem) > 0) {
            while (!(current.value.compareTo(elem) == 0)){
                if (current.prev.equals(Optional.empty())) {
                    return Optional.empty();
                }
                current = current.prev.get();
                if (current.value.compareTo(elem) == 0) {
                    return Optional.of(current);
                }
            }
            return Optional.empty();
        }

        if (current.value.compareTo(elem) < 0) {
            while (!(current.value.compareTo(elem) == 0)) {
                if (current.next.equals(Optional.empty())) {
                    return Optional.empty();
                }
                current = current.next.get();
                if (current.value.compareTo(elem) == 0) {
                    return Optional.of(current);
                }
            }
            return Optional.empty();
        }

    return Optional.empty();
    }

    /**
     * Return the node holding the element at the specified index
     *
     * @param idx the index to look up
     * @return the node holding the element at position <tt>idk</tt>
     * @throws IndexOutOfBoundsException if <tt>idx</tt> < 0 or <tt>idx</tt> >= length
     *
     * Note that <tt>idx</tt> refers to the index of an element, not of a node.
     *
     * If the list changes, references to nodes whose values are unchanged should remain valid, even
     * if their index changes.
     *
     * This function should run in O(idx)
     */
    public LinkedListNode<T> getRef(int idx) throws IndexOutOfBoundsException {
        if (idx < 0 || idx >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        LinkedListNode<T> current = this.headNode;

        int counter = 0;
        for (int acc = 0; acc <= idx; acc += current.count) {
            if (counter == current.count) {
                current = current.next.get();
                counter = 0;
            }
            counter += current.count;
        }
        return current;
    }

    /**
     * Return the value at the specified index
     *
     * @param idx the index to look up
     * @return the value currently at index <tt>idx</tt>
     * @throws IndexOutOfBoundsException if <tt>idx</tt> < 0 or <tt>idx</tt> >= length
     *
     * Note that <tt>idx</tt> refers to the index of an element, not of a node.
     *
     * This function should run in O(idx)
     */
    public T get(int idx) throws IndexOutOfBoundsException {
        if (idx < 0 || idx >= this.length) {
            throw new IndexOutOfBoundsException();
        }

        return getRef(idx).value;
    }

    /**
     * Insert a new value into the list
     *
     * @param elem the value to insert
     * @return a reference to the node holding the inserted value
     * <p>
     * The value should be placed so that the list remains in sorted order. After the insertion, the
     * insertion points <tt>next</tt> field should be a reference holding the next largest element,
     * and the <tt>prev</tt> field should be a reference to the next smallest element.
     * <p>
     * If <tt>elem</tt> is already in the list, the existing node should just have its
     * <tt>count</tt> field updated instead of creating a new node.
     * <p>
     * This function should run in O(length)
     */

    /*findRefBefore(elem) outputs:
    Reference to the list node (elem exists);
    Reference to preceding node (elem doesn't exist);
    Optional.empty() (elem is smaller than every other elem);
    */
    public LinkedListNode<T> insert(T elem) {
        Optional<LinkedListNode<T>> current = findRefBefore(elem); //Sets iterator to findRefBefore(elem)
        LinkedListNode<T> insertElem = new LinkedListNode<>(elem, 1);

        if (this.headNode == null) { //Empty list
            this.headNode = insertElem;
            this.lastNode = insertElem;
            this.length++;
            return insertElem;
        }
        else if (current.equals(Optional.empty())) { //Elem is new head
            this.headNode.prev = Optional.of(insertElem);
            insertElem.next = Optional.of(this.headNode);
            this.headNode = insertElem;
            this.length++;
            return insertElem;
        }
        else if (current.get().value.compareTo(elem) == 0) { //Elem exists
            current.get().count++;
            this.length++;
            return current.get();
        }
        else if (current.get().value.equals(this.lastNode.value)) { //Elem is new tail
            this.lastNode.next = Optional.of(insertElem);
            insertElem.prev = Optional.of(this.lastNode);
            this.lastNode = insertElem;
            this.length++;
            return insertElem;
        }
        else if (current.get().value.compareTo(elem) < 0) { //Middle of list
            insertElem.next = current.get().next;
            current.get().next.get().prev = Optional.of(insertElem);
            insertElem.prev = current;
            current.get().next = Optional.of(insertElem);
            this.length++;
            return insertElem;
        }
        return insertElem;
    }

    /**
     * Insert a new value into the list
     *
     * @param elem the value to insert
     * @param hint a reference to a node "close" to <tt>elem</tt>
     * @return a reference to the node holding the inserted value
     * <p>
     * The value should be placed so that the list remains in sorted order. After the insertion, the
     * insertion points <tt>next</tt> field should be a reference holding the next largest element,
     * and the <tt>prev</tt> field should be a reference to the next smallest element.
     * <p>
     * If <tt>elem</tt> is already in the list, the existing node should just have its
     * <tt>count</tt> field updated instead of creating a new node.
     * <p>
     * If <tt>hint</tt> is at position i, and <tt>elem</tt> would be at position j, then this
     * function should run in O(|i-j|)
     */
    public LinkedListNode<T> insert(T elem, LinkedListNode<T> hint) {
        LinkedListNode<T> current = hint;
        LinkedListNode<T> insertElem = new LinkedListNode<>(elem, 1);

        if (this.headNode == null) {
            this.headNode = insertElem;
            this.lastNode = insertElem;
            this.length++;
            return insertElem;
        }

        while (!(current.value.compareTo(elem) == 0)) {
            if (current.value.compareTo(elem) > 0) {
                if (findRefBefore(elem).equals(Optional.empty())) {
                    this.headNode.prev = Optional.of(insertElem);
                    insertElem.next = Optional.of(this.headNode);
                    this.headNode = insertElem;
                    this.length++;
                    return insertElem;
                }
                current = current.prev.get();

                if (current.value.compareTo(elem) < 0) {
                    //2 4
                    insertElem.prev = Optional.of(current);
                    insertElem.next = current.next;
                    current.next.get().prev = Optional.of(insertElem);
                    current.next = Optional.of(insertElem);
                    this.length++;
                    return insertElem;
                }

            }

            if (current.value.compareTo(elem) < 0) {
                if (current.next.equals(Optional.empty())) {
                    this.lastNode.next = Optional.of(insertElem);
                    insertElem.prev = Optional.of(this.lastNode);
                    this.lastNode = insertElem;
                    this.length++;
                    return insertElem;
                }
                current = current.next.get();
                if (current.value.compareTo(elem) == 0) {
                    current.count++;
                    this.length++;
                    return current;
                }
                if (current.value.compareTo(elem) > 0) {
                    insertElem.next = Optional.of(current);
                    insertElem.prev = current.prev;
                    current.prev.get().next = Optional.of(insertElem);
                    current.prev = Optional.of(insertElem);
                    this.length++;
                    return insertElem;
                }
            }
        }
        if (current.value.compareTo(elem) == 0) {
            current.count++;
            this.length++;
            return current;
        }
        return null;
    }

    /**
     * Remove one instance of the value held by <tt>ref</tt> from the list
     *
     * @param ref the node holding the value to remove (must be part of the list)
     * @return the removed value
     * <p>
     * If the list only contains a single instance of the value being removed, then the node should
     * be removed from the list as well. If the list contains multiple instances of the value, the
     * node itself should remain in the list.
     * <p>
     * This function should run in O(1)
     */
    public T remove(LinkedListNode<T> ref) {
            if (ref.count > 1) {
                ref.count--;
                this.length--;
                return ref.value;
            }
            else if (ref.count == 1) {
                if (ref.equals(headNode) && ref.equals(lastNode)) {
                     this.headNode = null;
                     this.lastNode = null;
                     ref.next = Optional.empty();
                     ref.prev = Optional.empty();
                     ref.count--;
                    this.length--;
                     return ref.value;
                }
                if (ref.equals(headNode)) {
                    this.headNode = ref.next.get();
                    this.headNode.prev = Optional.empty();
                    ref.next = Optional.empty();
                    ref.prev = Optional.empty();
                    ref.count--;
                    this.length--;
                    return ref.value;
                }
                if (ref.equals(lastNode)) {
                    this.lastNode = ref.prev.get();
                    this.lastNode.next = Optional.empty();
                    ref.next = Optional.empty();
                    ref.prev = Optional.empty();
                    ref.count--;
                    this.length--;
                    return ref.value;
                }
                ref.next.get().prev = ref.prev;
                ref.prev.get().next = ref.next;
                ref.next = Optional.empty();
                ref.prev = Optional.empty();
                ref.count--;
                this.length--;
                return ref.value;
            }
        return null;
    }

    /**
     * Remove n instances of the value held by <tt>ref</tt> from the list
     *
     * @param ref the node holding the value to remove (must be part of the list)
     * @param n the number of instances of the value to remove
     * @return the removed value
     * @throws IllegalArgumentException if <tt>n</tt> > <tt>ref.count</tt>
     *
     * If the node contains more than <tt>n</tt> instances of the value, then the node itself should
     * remain in the list.
     *
     * This function should run in O(1)
     */
    public T removeN(LinkedListNode<T> ref, int n) throws IllegalArgumentException {
        if (ref.count < n) {
            throw new IllegalArgumentException();
        }  else if (ref.count > n) {
            ref.count -= n;
            this.length -= n;
            return ref.value;
        }
        else {
            if (ref.equals(headNode) && ref.equals(lastNode)) {
                this.headNode = null;
                this.lastNode = null;
                ref.next = Optional.empty();
                ref.prev = Optional.empty();
                ref.count -= n;
                this.length -= n;
                return ref.value;
            }
            if (ref.equals(this.headNode)) {
                this.headNode = ref.next.get();
                this.headNode.prev = Optional.empty();
                ref.prev = Optional.empty();
                ref.next = Optional.empty();
                ref.count -= n;
                this.length -= n;
                return ref.value;
            }
            if (ref.equals(this.lastNode)) {
                this.lastNode = ref.prev.get();
                this.lastNode.next = Optional.empty();
                ref.prev = Optional.empty();
                ref.next = Optional.empty();
                ref.count -= n;
                this.length -= n;
                return ref.value;
            }
            ref.prev.get().next = ref.next;
            ref.next.get().prev = ref.prev;
            ref.next = Optional.empty();
            ref.prev = Optional.empty();
            ref.count -= n;
            return ref.value;
        }
    }

    /**
     * Remove all instances of the value held by <tt>ref</tt> from the list
     *
     * @param ref the node holding the value to remove (must be part of the list)
     * @return the removed value
     * <p>
     * This function should run in O(1)
     */
    public T removeAll(LinkedListNode<T> ref) {
        if (ref.equals(headNode) && ref.equals(lastNode)) {
            this.headNode = null;
            this.lastNode = null;
            ref.next = Optional.empty();
            ref.prev = Optional.empty();
            this.length = 0;
            ref.count = 0;
            return ref.value;
        }
        if (ref.equals(this.headNode)) {
            this.headNode = ref.next.get();
            this.headNode.prev = Optional.empty();
            ref.prev = Optional.empty();
            ref.next = Optional.empty();
            this.length -= ref.count;
            ref.count = 0;
            return ref.value;
        }
        if (ref.equals(this.lastNode)) {
            this.lastNode = ref.prev.get();
            this.lastNode.next = Optional.empty();
            ref.prev = Optional.empty();
            ref.next = Optional.empty();
            this.length -= ref.count;
            ref.count = 0;
            return ref.value;
        }
        ref.prev.get().next = ref.next;
        ref.next.get().prev = ref.prev;
        ref.next = Optional.empty();
        ref.prev = Optional.empty();
        this.length -= ref.count;
        ref.count = 0;
        return ref.value;

    }

    /**
     * Modifies a single value presently in the list
     *
     * @param ref  a reference to the node with the value to be updated
     * @param elem the new value
     * @return a reference to the updated node
     * <p>
     * This code is provided for you.
     * <p>
     * If i is the position of ref before the update and j is the position of ref after the update,
     * then this function should run in O(|i-j|)
     */
    public LinkedListNode<T> update(LinkedListNode<T> ref, T elem) {
        LinkedListNode<T> ret = insert(elem, ref);
        remove(ref);
        return ret;
    }

    /**
     * Modifies a single value presently in the list
     *
     * @param idx  the index of the element to be updated
     * @param elem the new value
     * @return a reference to the updated node
     * <p>
     * This code is provided for you.
     * <p>
     * If i is the position of ref before the update and j is the position of ref after the update,
     * then this function should run in O(|i-j|)
     */
    public void update(int idx, T elem) {
        update(getRef(idx), elem);
    }

    /**
     * Return an iterator over the elements of the collection
     *
     * @return an iterator over the elements of the collection
     * <p>
     * The iterator should return elements in sorted order from least to greatest (according to the
     * types <tt>compareTo</tt> method).
     * <p>
     * The iterator's <tt>next</tt> and <tt>hasNext</tt> methods should both run in O(1)
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private LinkedListNode<T> current = headNode;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T ret = current.value;
                count++;
                if (count >= current.count) {
                    current = current.next.orElse(null);
                    count = 0;
                }
                return ret;
            }
        };
    }
}
