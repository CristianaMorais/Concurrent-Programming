package pc.bqueue;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.Arrays;

/**
 * Lock-free implementation of queue - unbounded variant.
 * 
 *
 * @param <E> Type of elements.
 */
public class LFBQueueU<E>  implements BQueue<E> {

  private E[] array;
  private final AtomicInteger head;
  private final AtomicInteger tail;
  private final AtomicBoolean addElementFlag;
  private final Rooms rooms;
  private final boolean useBackoff;


  /**
   * Constructor.
   * @param initialCapacity Initial queue capacity.
   * @param backoff Flag to enable/disable the use of back-off.
   * @throws IllegalArgumentException if {@code capacity <= 0}
   */
  @SuppressWarnings("unchecked")
  public LFBQueueU(int initialCapacity, boolean backoff) {
    head = new AtomicInteger(0);
    tail = new AtomicInteger(0);new AtomicMarkableReference<>(0, false);
    addElementFlag = new AtomicBoolean();
    array = (E[]) new Object[initialCapacity];
    useBackoff = backoff;
    rooms = new Rooms(3, backoff);
  }

  @Override
  public int capacity() {
    return UNBOUNDED;
  }
  
  @Override
  public int size() {
    rooms.enter(2);
    int size = tail.get() - head.get();
    rooms.leave(2);
    return size;
  }

  @Override
  public void add(E elem) {   
    rooms.enter(0);
    while(true) {
      if (addElementFlag.compareAndSet(false, true)) {
        int p = tail.getAndIncrement();
        if (p >= array.length) {
          int newCapacity = array.length*2;
          E[] newArray = Arrays.copyOf(array,newCapacity);
          array = newArray;
          for (int i = 0; i < newArray.length; i++) {
            array[i % array.length] = newArray[i];
          }
        }
        array[p % array.length] = elem;
        addElementFlag.set(false);
        break;
      }
    }

    rooms.leave(0);
  }
  
  @Override
  public E remove() {   
   E elem;
   while (true) {
    rooms.enter(1);
    int p = head.getAndIncrement();
    if (p < tail.get()) {
      int pos = p % array.length;
      elem = array[pos];
      array[pos] = null;
      break;
    } 
    else {    
      // "undo"
      head.getAndDecrement();
      rooms.leave(1);
    }
  }
  rooms.leave(1);
  return elem;

  }

  /**
   * Test instantiation.
   */
  public static final class Test extends BQueueTest {
    @Override
    <T> BQueue<T> createBQueue(int capacity) {
      return new LFBQueueU<>(capacity, false);
    }
  }
}
