/**
 * 
 */
package com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Niranjhani
 *
 */
public class ArrayListWrapper<T> {

	private AtomicReference<List<T>> list = new AtomicReference<List<T>>(new ArrayList<T>());

	public void add(T item) {
		List<T> oldlist;
		List<T> newlist;
		do {
			oldlist = list.get();
			newlist = new ArrayList<T>(oldlist);
			newlist.add(item);
		} while (!list.compareAndSet(oldlist, newlist));

	}

	public List<T> getList() {
		List<T> result = list.get();
		return result;
	}

	public Boolean contains(T item) {
		List<T> oldlist = list.get();
		return oldlist.contains(item);
	}

	public void remove(T item) {
		List<T> oldlist;
		List<T> newlist;
		do {
			oldlist = list.get();
			newlist = new ArrayList<T>(oldlist);
			newlist.remove(item);
		} while (!list.compareAndSet(oldlist, newlist));
	}

	public int size() {
		List<T> oldlist = list.get();
		return oldlist.size();
	}
}
