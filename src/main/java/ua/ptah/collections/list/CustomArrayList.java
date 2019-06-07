package ua.ptah.collections.list;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class CustomArrayList<T> implements CustomList<T> {

  private static final int INIT_CAPACITY = 10;
  private static final double GROWTH_THRESHOLD = 0.8;
  private static final double SHRINK_THRESHOLD = 0.25;
  private T[] array;
  private int size;

  /**
   * Constructs an empty list with a default INIT_CAPACITY
   */
  public CustomArrayList() {
    this(INIT_CAPACITY);
  }

  /**
   * Constructs an empty list with the specified capacity.
   *
   * @param capacity the initial capacity of the list
   * @throws IllegalArgumentException if the specified initial capacity
   *                                  is negative
   */
  @SuppressWarnings("unchecked")
  public CustomArrayList(int capacity) {
    if (capacity < 1) {
      throw new IllegalArgumentException("Capacity should be more than 1");
    }
    array = (T[]) new Object[capacity];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(T el) {
    add(el, size);
  }

  /**
   * {@inheritDoc}
   *
   * @throws ArrayIndexOutOfBoundsException if the element index is outside of range
   */
  @Override
  public void add(T el, int index) {
    if (index < 0 || index > size) {
      throw new ArrayIndexOutOfBoundsException("Incorrect index, out of bound");
    }
    if (size + 1 >= array.length * GROWTH_THRESHOLD) {
      doubleCapacity();
    }
    // shift to the right if adding inside, otherwise just assign to the end
    if (index != size) {
      System.arraycopy(array, index, array, index + 1, size - index);
    }
    array[index] = el;
    size++;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IndexOutOfBoundsException if the element index is outside of range
   */
  @Override
  public T get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Incorrect index, nothing at this position");
    }
    return array[index];
  }

  /**
   * {@inheritDoc}
   *
   * @throws NoSuchElementException if trying to remove from an empty list
   */
  @Override
  public T remove() {
    return remove(size - 1);
  }

  /**
   * {@inheritDoc}
   *
   * @throws IndexOutOfBoundsException if the element index is outside of range
   * @throws NoSuchElementException    if trying to remove from an empty list
   */
  @Override
  public T remove(int index) {
    if (size == 0) {
      throw new NoSuchElementException("Cannot remove elements from an empty list");
    }
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Incorrect index, nothing at this position");
    }
    T removed = array[index];
    size--;
    // was the last element, null the reference to avoid memory leak
    if (index == size) {
      array[size] = null;
    }
    else {
      // was not the last element, shift all next elements one position to the left
      System.arraycopy(array, index + 1, array, index, size - index + 1);
    }
    if ((size <= array.length * SHRINK_THRESHOLD) && (array.length / 2 > INIT_CAPACITY)) {
      shrinkCapacity();
    }
    return removed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Readable form of CustomArrayList (with null-s)
   *
   * @return full String representation of internal array
   */
  @Override
  public String toString() {
    return Arrays.toString(array);
  }

  private void doubleCapacity() {
    array = Arrays.copyOf(array, array.length * 2);
  }

  private void shrinkCapacity() {
    array = Arrays.copyOf(array, array.length / 2);
  }
}
