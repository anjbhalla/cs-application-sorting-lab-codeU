/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		int half = list.size()/2;
		List<T> list1 = new ArrayList<T>();
		List<T> list2 = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			if (i > half) {
				list2.add(list.get(i));
			}
			else {
				list1.add(list.get(i));
			}
		}
		if (list1.size() < 6) {
			insertionSort(list1, comparator);		
			insertionSort(list2, comparator);
		}
		else {
			list1 = mergeSort(list1, comparator);
			list2 = mergeSort(list1, comparator);
		}
		List<T> sorted = merge(list1, list2, comparator);
	        return sorted;
	}

	private List<T> merge(List<T> list1, List<T> list2, Comparator<T> comparator) {
		int i = 0;
		for (int k = 0; k < list2.size(); k++) {
			T elt = list2.get(k);
			int j = 0;
			while (j == 0) {
				int cmp = comparator.compare(elt, list1.get(i));
				if (cmp <= 0) {
					list1.add(i, elt);
					j++;
				}
				i++;
			}
		}
		return list1;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		PriorityQueue<T> heap = new PriorityQueue(list.size(), comparator);
		heap.addAll(list);
		for (int j = 0; j < list.size(); j++) {
			list.set(j, heap.poll());
		}
	}
	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		PriorityQueue<T> boundedHeap = new PriorityQueue(k, comparator);
		for (int i = 0; i < list.size(); i++) {
			T elt = list.get(i);
			if (boundedHeap.size() < k) {
				boundedHeap.offer(elt);
			}
			else {
				int cmp = comparator.compare(elt, boundedHeap.peek());
				if (cmp > 0) {
					boundedHeap.poll();
					boundedHeap.offer(elt);
				}
			}
		}
		List<T> topK = new ArrayList<T>();
		for (int j = 0; j < k; j++) {
                        topK.add(boundedHeap.poll());
                }
	        return topK;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
