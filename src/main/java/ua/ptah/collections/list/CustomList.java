package ua.ptah.collections.list;

public interface CustomList<T> {

  void add(T el);

  void add(T el, int index);

  T get(int index);

  T remove();

  T remove(int index);

  int size();
}
