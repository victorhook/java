package queue_singlelinkedlist;
import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**	
	 * Inserts the specified element into this queue, if possible
	 * post:	The specified element is added to the rear of this queue
	 * @param	e the element to insert
	 * @return	true if it was possible to add the element 
	 * 			to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> newNode = new QueueNode<>(e);
		if (last == null) {
			last = newNode;
			newNode.next = last;
		} else {
			newNode.next = last.next;
			last.next = newNode;
			last = newNode;
		}
		this.size++;
		return true;
	}
	
	/**	
	 * Returns the number of elements in this queue
	 * @return the number of elements in this queue
	 */
	public int size() {		
		return this.size;
	}
	
	/**	
	 * Retrieves, but does not remove, the head of this queue, 
	 * returning null if this queue is empty
	 * @return 	the head element of this queue, or null 
	 * 			if this queue is empty
	 */
	public E peek() {
		return last == null ? null : last.next.element;
	}

	/**	
	 * Retrieves and removes the head of this queue, 
	 * or null if this queue is empty.
	 * post:	the head of the queue is removed if it was not empty
	 * @return 	the head of this queue, or null if the queue is empty 
	 */
	public E poll() {
		if (last == null) return null;
		E data = last.next.element;
		if (last.equals(last.next)) {
			last = null;
		} else {
			last.next = last.next.next;
		}
		this.size--;
		return data;
	}
	
	/**	
	 * Returns an iterator over the elements in this queue
	 * @return an iterator over the elements in this queue
	 */	
	public Iterator<E> iterator() {
		return new QueueIterator(last);
	}
	
	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

	private static class QueueIterator<E> implements Iterator<E> {
		private QueueNode<E> pos, end;

		private QueueIterator(QueueNode<E> last) {
			this.end = last;
			this.pos = last == null ? null : last.next;
		}

		@Override
		public boolean hasNext() {
			return pos != null;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (pos == null) {
				throw new NoSuchElementException();
			}
			E data = pos.element;
			if (pos.equals(end)) {
				pos = null;
			} else {
				pos = pos.next;
			}
			return data;
		}
	}

	public void append(FifoQueue<E> q) throws IllegalArgumentException {
		if (q.equals(this)) {
			throw new IllegalArgumentException("Can't append the same queue!");
		}
		if (q.last == null) return;			// If given queue is empty, we do nothing
		if (this.last == null) this.last = q.last; // If this queue is empty, simply switch the last reference.

		QueueNode<E> firstNode = q.last.next;
		q.last.next = this.last.next;		// Last item in added queue point to first item in this queue
		this.last.next = firstNode;			// Bind together the queues
		this.last = q.last;					// Change last reference to the new queue
		this.size += q.size();				// Increase the size variable.

		// Clear the given queue
		q.last = null;
		q.size = 0;
	}

	public static void main(String[] args) {
		FifoQueue<Integer> queue = new FifoQueue<>();
		FifoQueue<Integer> queue2 = new FifoQueue<>();
		for (int i = 0; i < 100; i+=10) {
			queue.offer(i);
			queue2.offer(i);
		}
		queue.append(queue2);
		queue.forEach(n -> System.out.println(n));

	}

}
