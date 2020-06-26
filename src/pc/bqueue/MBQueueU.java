package pc.bqueue;

import java.util.Arrays;

/**
 * Monitor-based implementation of queue. 
 * 
 *
 * @param <E> Type of elements.
 */
public class MBQueueU<E> extends MBQueue<E> {

  /**
   * Constructor.
   * @param initialCapacity Initial queue capacity.
   * @throws IllegalArgumentException if {@code capacity <= 0}
   */
  public MBQueueU(int initialCapacity) {
    super(initialCapacity);
  }
  
  @Override
  public synchronized int capacity() {
    return UNBOUNDED;
  }
  
  private void increaseCapacity(){
         
    int newCapacity = array.length*2;
    E[] newArray = Arrays.copyOf(array,newCapacity);

    //"adiciona" o novo array à queue
    array = newArray;
    
  }

  @Override 
  /*Para tal, o método add(), quando o array actual estiver cheio e antes
  de colocar na fila um novo elemento, deverá criar um novo array com o dobro do tamanho do
  actual, em vez de bloquear a thread em contexto. Os elementos do array anterior deverão
  é claro passar para o novo array, e o estado interno deverá também ser actualizado de
  forma consistente.
  */
  public synchronized void add(E elem) {
    
    while (size == array.length) {
      increaseCapacity();
    }
    array[(head + size) % array.length] = elem;
    notifyAll(); 
    size++; 
  }

  
  /**
   * Test instantiation.
   */
  public static final class Test extends BQueueTest {
    @Override
    <T> BQueue<T> createBQueue(int initialCapacity) {
      return new MBQueueU<>(initialCapacity);
    }
  }
}
