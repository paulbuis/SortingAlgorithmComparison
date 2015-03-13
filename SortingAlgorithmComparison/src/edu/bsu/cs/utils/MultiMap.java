package edu.bsu.cs.utils;


import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Like a Map<K,V> but when putting, will not replace old value, just append to a list of values
 * 
 *
 * @param <K> key type
 * @param <V> value type
 */
public class MultiMap<K extends Comparable<? extends K>, V> implements Map<K, List<V>>, Iterable<K>{
	final private TreeMap<K, LinkedList<V>> map = new TreeMap<>();
	
	public MultiMap() {
	}
	
	public void put(K key, V value) {
		if (key == null)
			return;
		LinkedList<V> list = map.get(key);
		if (list == null) {
			list = new LinkedList<>();
			map.put(key, list);
		}
		list.add(value); 
	}
	
	@Override
	public final int size() {
		return map.size();
	}

	@Override
	public final boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public final boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public final List<V> get(Object key) {
		if (key == null)
			return null;
		return map.get(key);
	}
	
	@Override
	public List<V> put(K key, List<V> value) {
		if (key == null)
			return null;
		for (V item : value) {
			put(key, item);
		}
		return null; // does not conform with spec!
	}

	@Override
	public final List<V> remove(Object key) {
		return map.remove(key);
	}

	@Override
	public final void clear() {
		map.clear();
	}

	@Override
	public final Set<K> keySet() {
		return map.keySet();
	}
	
	/**
	 * 
	 */
	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException("MultiMap does not support containsValue method");
	}

	/**
	 * Unsupported
	 */
	@Override
	public Collection<List<V>> values() {
		throw new UnsupportedOperationException("MultiMap does not support values method");
	}

	/**
	 * Unsupported
	 */
	@Override
	public Set<Map.Entry<K, List<V>>> entrySet() {
		throw new UnsupportedOperationException("MultiMap does not support entrySet method");
	}

	/**
	 * Unsupported
	 */
	@Override
	public void putAll(Map<? extends K, ? extends List<V>> m) {
		throw new UnsupportedOperationException("MultiMap does not support putAll method");
	}

	/**
	 * iterator over the key set
	 */
	@Override
	public Iterator<K> iterator() {
		return map.keySet().iterator();
	}
	
	public Stream<K> stream() {
		return map.keySet().stream();
	}
	
}