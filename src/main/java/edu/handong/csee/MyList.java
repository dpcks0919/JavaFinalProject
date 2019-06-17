package edu.handong.csee;

public class MyList<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private Object element[];
	private int index;

	public MyList() {
		element = new Object[DEFAULT_CAPACITY];
	}

	public int size() {
		return index;
	}

	public void add(E e) {
		this.element[index++] = e;
	}

	public E get(int index) {
		return (E) element[index];
	}
}