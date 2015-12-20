import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Your implementation of a HashMap, using external chaining as your collision
 * policy.  Read the PDF for more instructions on external chaining.
 *
 * @author Julia Neuman
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries.
     */
    public HashMap() {

        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;
    }

    /**
     * resizes the array to 2n+1 the previous size
     *
     */

    private void resize() {
        int prevsize = table.length;
        MapEntry<K, V>[] newTable =
                (MapEntry<K, V>[]) new MapEntry[2 * prevsize + 1];
        MapEntry<K, V>[] oldTable = table;
        table = newTable;
        size = 0;
        for (int i = 0; i < oldTable.length; i++) {
            MapEntry<K, V> entry = oldTable[i];
            while (entry != null) {
                add(entry.getKey(), entry.getValue());

                entry = entry.getNext();
            }
        }

    }

    @Override
    public V add(K key, V value) {

        if (key == null || value == null) {
            throw new IllegalArgumentException("Neither key "
                    + "nor value can be null");
        }

        if (((size + 1) / (double) table.length) > MAX_LOAD_FACTOR) {
            resize();
        }

        int code = Math.abs(key.hashCode());
        int index = code % table.length;
        MapEntry<K, V> entry = table[index];
        if (entry == null) {
            table[index] = new MapEntry<K, V>(key, value);
            size++;
            return null;
        }
        while (entry.getNext() != null) {
            if (entry.getKey().equals(key)) {
                V prevValue = entry.getValue();
                entry.setValue(value);
                return prevValue;
            }
            entry = entry.getNext();
        }
        if (entry.getKey().equals(key)) {
            V prevValue = entry.getValue();
            entry.setValue(value);
            return prevValue;
        }
        entry.setNext(new MapEntry<K, V>(key, value));
        size++;

        return null;



    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int code = Math.abs(key.hashCode());
        int index = code % table.length;
        MapEntry<K, V> entry = table[index];
        MapEntry<K, V> prevEntry = table[index];
        if (entry == null) {
            throw new java.util.NoSuchElementException("The key "
                    + "does not exist");
        }
        if (entry.getKey().equals(key)) {
            V theValue = entry.getValue();
            table[index] = entry.getNext();
            size--;
            return theValue;
        }
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                V theValue = entry.getValue();
                prevEntry.setNext(entry.getNext());
                size--;
                return theValue;

            }
            prevEntry = entry;
            entry = entry.getNext();

        }
        throw new java.util.NoSuchElementException("The key does not exist");



    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int code = Math.abs(key.hashCode());
        int index = code % table.length;
        MapEntry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
            entry = entry.getNext();
        }
        throw new java.util.NoSuchElementException("Key is not in the map");


    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int code = Math.abs(key.hashCode());
        int index = code % table.length;
        MapEntry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return true;
            }
            entry = entry.getNext();
        }
        return false;


    }


    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> theKeySet = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> entry = table[i];
            while (entry != null) {
                theKeySet.add(entry.getKey());
                entry = entry.getNext();
            }
        }
        return theKeySet;

    }

    @Override
    public List<V> values() {
        List<V> theValues = new ArrayList<V>();
        int j = 0;
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> entry = table[i];
            while (entry != null) {
                theValues.add(entry.getValue());
                j++;
                entry = entry.getNext();
            }
        }
        return theValues;


    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE.  IT IS FOR TESTING ONLY
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] toArray() {
        return table;
    }

}