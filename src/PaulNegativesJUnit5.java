import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PaulNegativesJUnit5 {
    private HashMap<Integer, String> hashMap;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        hashMap = new HashMap<>();
    }

    // test heap constructor

    @Test(timeout = TIMEOUT)
    public void constructor1() {
        assertEquals(0, hashMap.size());
        assertArrayEquals(hashMap.toArray(), (MapEntry<Integer, String>[]) new MapEntry[HashMapInterface.STARTING_SIZE]);
    }

    // test add

    @Test(timeout = TIMEOUT)
    public void addNegative1() {
        hashMap.add(-1, "a");
        // create a new MapEntry array
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        //add MapEntry object with key of 1 and value of "a" to array
        //hashcode() of an int returns the int, so if key == 1, key.hashCode() returns 1
        //that is why MapEntry is added at index 1
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void addNegative2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void addNegative3() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");
        // check to make sure array does not resize with 7 entries
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");
        array[5] = new MapEntry<Integer, String>(-5, "e");
        array[6] = new MapEntry<Integer, String>(-6, "f");
        array[7] = new MapEntry<Integer, String>(-7, "g");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeChaining1() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");

        // only MapEntry(-1, "a") will be added to the array at index 1
        // MapEntry(-12, "b") will be chained to MapEntry(-1, "a")
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());

        // check for chaining
        assertEquals(new MapEntry<Integer, String>(-12, "b"), hashMap.toArray()[1].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeChaining2() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");
        hashMap.add(-56, "f");
        hashMap.add(-67, "g");

        // only MapEntry(-1, "a") will be added to the array at index 1
        // all other entries will be chained to MapEntry(-1, "a")
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());

        // check for chaining
        assertEquals(new MapEntry<Integer, String>(-12, "b"), hashMap.toArray()[1].getNext());
        assertEquals(new MapEntry<Integer, String>(-23, "c"), hashMap.toArray()[1].getNext().getNext());
        assertEquals(new MapEntry<Integer, String>(-34, "d"), hashMap.toArray()[1].getNext().getNext().getNext());
        assertEquals(new MapEntry<Integer, String>(-45, "e"), hashMap.toArray()[1].getNext().getNext().getNext().getNext());
        assertEquals(new MapEntry<Integer, String>(-56, "f"), hashMap.toArray()[1].getNext().getNext().getNext().getNext().getNext());
        assertEquals(new MapEntry<Integer, String>(-67, "g"), hashMap.toArray()[1].getNext().getNext().getNext().getNext().getNext().getNext());

    }

    @Test(timeout = TIMEOUT)
    public void addNegativeChaining3() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "l"); // element 12 collides with element 1
        hashMap.add(-2, "b");
        hashMap.add(-13, "m"); // element 13 collides with element 2
        hashMap.add(-3, "c");
        hashMap.add(-14, "n"); // element 14 collides with element 3
        hashMap.add(-4, "d");

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");

        assertArrayEquals(array, hashMap.toArray());

        // check for chaining
        assertEquals(new MapEntry<Integer, String>(-12, "l"), hashMap.toArray()[1].getNext());
        assertEquals(new MapEntry<Integer, String>(-13, "m"), hashMap.toArray()[2].getNext());
        assertEquals(new MapEntry<Integer, String>(-14, "n"), hashMap.toArray()[3].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeChaining4() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-12, "b");

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());

        // check that only 1 instance of chaining occurred
        assertEquals(new MapEntry<Integer, String>(-12, "b"), hashMap.toArray()[1].getNext());
        assertNull(hashMap.toArray()[1].getNext().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDoubleArray1() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");
        hashMap.add(-8, "h");

        // adding 8 elements surpasses load factor of original array (-8/11 = 0.73)
        // check to make sure that array size went from 11 to 23
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[23];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");
        array[5] = new MapEntry<Integer, String>(-5, "e");
        array[6] = new MapEntry<Integer, String>(-6, "f");
        array[7] = new MapEntry<Integer, String>(-7, "g");
        array[8] = new MapEntry<Integer, String>(-8, "h");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDoubleArray2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");
        hashMap.add(-1, "h");
        // adding the 8th element, even if it is a duplicate will cause the array
        // to resize
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[23];
        array[1] = new MapEntry<Integer, String>(-1, "h");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");
        array[5] = new MapEntry<Integer, String>(-5, "e");
        array[6] = new MapEntry<Integer, String>(-6, "f");
        array[7] = new MapEntry<Integer, String>(-7, "g");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDoubleArray3() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "l");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");

        // check that array reorganizes entries after resizing
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[23];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");
        array[5] = new MapEntry<Integer, String>(-5, "e");
        array[6] = new MapEntry<Integer, String>(-6, "f");
        array[7] = new MapEntry<Integer, String>(-7, "g");
        array[12] = new MapEntry<Integer, String>(-12, "l");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDoubleArray4() {
        hashMap.add(-1, "a");
        hashMap.add(-9, "i");
        hashMap.add(-2, "b");
        hashMap.add(-10, "j");
        hashMap.add(-3, "c");
        hashMap.add(-11, "k");
        hashMap.add(-4, "d");
        hashMap.add(-12, "l");
        hashMap.add(-5, "e");
        hashMap.add(-13, "m");
        hashMap.add(-6, "f");
        hashMap.add(-14, "n");
        hashMap.add(-7, "g");
        hashMap.add(-15, "o");
        hashMap.add(-8, "h");
        // check that array reorganizes entries after resizing
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[23];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");
        array[5] = new MapEntry<Integer, String>(-5, "e");
        array[6] = new MapEntry<Integer, String>(-6, "f");
        array[7] = new MapEntry<Integer, String>(-7, "g");
        array[8] = new MapEntry<Integer, String>(-8, "h");
        array[9] = new MapEntry<Integer, String>(-9, "i");
        array[10] = new MapEntry<Integer, String>(-10, "j");
        array[11] = new MapEntry<Integer, String>(-11, "k");
        array[12] = new MapEntry<Integer, String>(-12, "l");
        array[13] = new MapEntry<Integer, String>(-13, "m");
        array[14] = new MapEntry<Integer, String>(-14, "n");
        array[15] = new MapEntry<Integer, String>(-15, "o");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDoubleArray5() {
        hashMap.add(-1, "a");
        hashMap.add(-9, "i");
        hashMap.add(-2, "b");
        hashMap.add(-10, "j");
        hashMap.add(-3, "c");
        hashMap.add(-11, "k");
        hashMap.add(-4, "d");
        hashMap.add(-12, "l");
        hashMap.add(-5, "e");
        hashMap.add(-13, "m");
        hashMap.add(-6, "f");
        hashMap.add(-14, "n");
        hashMap.add(-7, "g");
        hashMap.add(-15, "o");
        hashMap.add(-8, "h");
        hashMap.add(-16, "p");
        // check that array reorganizes entries after resizing twice
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[47];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");
        array[5] = new MapEntry<Integer, String>(-5, "e");
        array[6] = new MapEntry<Integer, String>(-6, "f");
        array[7] = new MapEntry<Integer, String>(-7, "g");
        array[8] = new MapEntry<Integer, String>(-8, "h");
        array[9] = new MapEntry<Integer, String>(-9, "i");
        array[10] = new MapEntry<Integer, String>(-10, "j");
        array[11] = new MapEntry<Integer, String>(-11, "k");
        array[12] = new MapEntry<Integer, String>(-12, "l");
        array[13] = new MapEntry<Integer, String>(-13, "m");
        array[14] = new MapEntry<Integer, String>(-14, "n");
        array[15] = new MapEntry<Integer, String>(-15, "o");
        array[16] = new MapEntry<Integer, String>(-16, "p");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDuplicate1() {
        hashMap.add(-1, "a");
        // adding MapEntry(-1, "b") will replace "a" with "b" and will return "a"
        assertEquals("a", hashMap.add(-1, "b"));

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "b");

        assertArrayEquals(array, hashMap.toArray());

        // check that chaining did not occur
        assertNull(hashMap.toArray()[1].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDuplicate2() {
        for (int i = 0; i < 100; i++) {
            hashMap.add(-1, "a");
        }

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());

        // check that chaining did not occur
        assertNull(hashMap.toArray()[1].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDuplicate3() {
        for (int i = 0; i < 100; i++) {
            hashMap.add(-1, "a");
            hashMap.add(-2, "b");
            hashMap.add(-3, "c");
            hashMap.add(-4, "d");
            hashMap.add(-5, "e");
            hashMap.add(-6, "f");
        }

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");
        array[5] = new MapEntry<Integer, String>(-5, "e");
        array[6] = new MapEntry<Integer, String>(-6, "f");

        assertArrayEquals(array, hashMap.toArray());

        // check that chaining did not occur
        assertNull(hashMap.toArray()[1].getNext());
        assertNull(hashMap.toArray()[2].getNext());
        assertNull(hashMap.toArray()[3].getNext());
        assertNull(hashMap.toArray()[4].getNext());
        assertNull(hashMap.toArray()[5].getNext());
        assertNull(hashMap.toArray()[6].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDuplicate4() {
        for (int i = 0; i < 100; i++) {
            hashMap.add(-1, "a");
            hashMap.add(-2, "b");
            hashMap.add(-3, "c");
            hashMap.add(-4, "d");
            hashMap.add(-5, "e");
            hashMap.add(-6, "f");
            hashMap.add(-7, "g");
        }
        // ensure that array size doubled when 8th element, MapEntry(-1,"a"), was added
        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[23];
        array[1] = new MapEntry<Integer, String>(-1, "a");
        array[2] = new MapEntry<Integer, String>(-2, "b");
        array[3] = new MapEntry<Integer, String>(-3, "c");
        array[4] = new MapEntry<Integer, String>(-4, "d");
        array[5] = new MapEntry<Integer, String>(-5, "e");
        array[6] = new MapEntry<Integer, String>(-6, "f");
        array[7] = new MapEntry<Integer, String>(-7, "g");

        assertArrayEquals(array, hashMap.toArray());

        // check that chaining did not occur
        assertNull(hashMap.toArray()[1].getNext());
        assertNull(hashMap.toArray()[2].getNext());
        assertNull(hashMap.toArray()[3].getNext());
        assertNull(hashMap.toArray()[4].getNext());
        assertNull(hashMap.toArray()[5].getNext());
        assertNull(hashMap.toArray()[6].getNext());
        assertNull(hashMap.toArray()[7].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void addNegativeDuplicate5() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "a");
        hashMap.add(-12, "b");
        hashMap.add(-12, "c");
        hashMap.add(-12, "d");
        hashMap.add(-12, "e");

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());

        // check that only 1 instance of chaining occurred
        assertEquals(new MapEntry<Integer, String>(-12, "e"), hashMap.toArray()[1].getNext());
        assertNull(hashMap.toArray()[1].getNext().getNext());
    }

    // test remove

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeNegativeException1() {
        // trying to remove an entry from an empty array
        hashMap.remove(-1);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeNegativeException2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");

        hashMap.remove(-8);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeNegativeException3() {
        // all elements below should be added and chained at array index 1
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        //skip adding entry (-34, "d")
        hashMap.add(-45, "e");
        hashMap.add(-56, "f");
        hashMap.add(-67, "g");

        // calling remove on an entry that is not present in the chain
        hashMap.remove(-34);
    }

    @Test(timeout = TIMEOUT)
    public void removeNegative1() {
        hashMap.add(-1, "a");
        assertEquals("a", hashMap.remove(-1));

        assertNull((hashMap.toArray())[1]);

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void removeNegative2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        assertEquals("a", hashMap.remove(-1));

        assertNull((hashMap.toArray())[1]);

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[2] = new MapEntry<Integer, String>(-2, "b");

        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void removeNegative3() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");

        assertEquals("a", hashMap.remove(-1));
        assertEquals("b", hashMap.remove(-2));
        assertEquals("c", hashMap.remove(-3));
        assertEquals("d", hashMap.remove(-4));
        assertEquals("e", hashMap.remove(-5));
        assertEquals("f", hashMap.remove(-6));
        assertEquals("g", hashMap.remove(-7));

        assertNull((hashMap.toArray())[1]);
        assertNull((hashMap.toArray())[2]);
        assertNull((hashMap.toArray())[3]);
        assertNull((hashMap.toArray())[4]);
        assertNull((hashMap.toArray())[5]);
        assertNull((hashMap.toArray())[6]);
        assertNull((hashMap.toArray())[7]);

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void removeNegativeChaining1() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");

        assertEquals("b", hashMap.remove(-12));

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());

        // check that chained entry was removed
        assertNull(hashMap.toArray()[1].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void removeNegativeChaining2() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");

        assertEquals("a", hashMap.remove(-1));

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-12, "b");

        assertArrayEquals(array, hashMap.toArray());

        assertNull(hashMap.toArray()[1].getNext());
    }

    @Test(timeout = TIMEOUT)
    public void removeNegativeChaining3() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");

        assertEquals("b", hashMap.remove(-12));

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());

        // check that chained entry was removed
        assertEquals(new MapEntry<Integer, String>(-23, "c"), hashMap.toArray()[1].getNext());
        assertNull(hashMap.toArray()[1].getNext().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void removeNegativeChaining4() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");
        hashMap.add(-56, "f");
        hashMap.add(-67, "g");

        assertEquals("g", hashMap.remove(-67));

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        array[1] = new MapEntry<Integer, String>(-1, "a");

        assertArrayEquals(array, hashMap.toArray());

        // check that chained entry was removed
        assertEquals(new MapEntry<Integer, String>(-12, "b"), hashMap.toArray()[1].getNext());
        assertEquals(new MapEntry<Integer, String>(-23, "c"), hashMap.toArray()[1].getNext().getNext());
        assertEquals(new MapEntry<Integer, String>(-34, "d"), hashMap.toArray()[1].getNext().getNext().getNext());
        assertEquals(new MapEntry<Integer, String>(-45, "e"), hashMap.toArray()[1].getNext().getNext().getNext().getNext());
        assertEquals(new MapEntry<Integer, String>(-56, "f"), hashMap.toArray()[1].getNext().getNext().getNext().getNext().getNext());
        assertNull(hashMap.toArray()[1].getNext().getNext().getNext().getNext().getNext().getNext());
    }

    @Test(timeout = TIMEOUT)
    public void removeNegativeChaining5() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");
        hashMap.add(-56, "f");
        hashMap.add(-67, "g");

        assertEquals("a", hashMap.remove(-1));
        assertEquals("d", hashMap.remove(-34));
        assertEquals("g", hashMap.remove(-67));
        assertEquals("b", hashMap.remove(-12));
        assertEquals("e", hashMap.remove(-45));
        assertEquals("f", hashMap.remove(-56));
        assertEquals("c", hashMap.remove(-23));

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];

        assertArrayEquals(array, hashMap.toArray());
    }

    // test get

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getNegativeException3() {
        hashMap.add(-1, "a");
        hashMap.get(-2);
    }

    @Test(timeout = TIMEOUT)
    public void getNegative1() {
        hashMap.add(-1, "a");
        assertEquals("a", hashMap.get(-1));
    }

    @Test(timeout = TIMEOUT)
    public void getNegative2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        assertEquals("a", hashMap.get(-1));
        assertEquals("b", hashMap.get(-2));
    }

    @Test(timeout = TIMEOUT)
    public void getNegative3() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");
        hashMap.add(-8, "h");
        assertEquals("a", hashMap.get(-1));
        assertEquals("b", hashMap.get(-2));
        assertEquals("c", hashMap.get(-3));
        assertEquals("d", hashMap.get(-4));
        assertEquals("e", hashMap.get(-5));
        assertEquals("f", hashMap.get(-6));
        assertEquals("g", hashMap.get(-7));
        assertEquals("h", hashMap.get(-8));
    }

    @Test(timeout = TIMEOUT)
    public void getNegative4() {
        for (int i = 0; i > (-100); i--) {
            hashMap.add(i, "a");
        }
        for (int i = 0; i > (-100); i--) {
            assertEquals("a", hashMap.get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void getNegativeChaining1() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        assertEquals("a", hashMap.get(-1));
        assertEquals("b", hashMap.get(-12));
        assertEquals("c", hashMap.get(-23));
    }

    // test contains

    @Test(timeout = TIMEOUT)
    public void containsNegative1() {
        hashMap.add(-1, "a");
        assertTrue(hashMap.contains(-1));
        assertFalse(hashMap.contains(-2));
    }

    @Test(timeout = TIMEOUT)
    public void containsNegative2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");
        hashMap.add(-8, "h");

        assertTrue(hashMap.contains(-1));
        assertTrue(hashMap.contains(-2));
        assertTrue(hashMap.contains(-3));
        assertTrue(hashMap.contains(-4));
        assertTrue(hashMap.contains(-5));
        assertTrue(hashMap.contains(-6));
        assertTrue(hashMap.contains(-7));
        assertTrue(hashMap.contains(-8));
    }

    @Test(timeout = TIMEOUT)
    public void containsNegative3() {
        for (int i = 0; i > (-100); i--) {
            hashMap.add(i, "a");
        }
        for (int i = 0; i > (-100); i--) {
            assertTrue(hashMap.contains(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void containsNegativeChaining1() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");

        assertTrue(hashMap.contains(-1));
        assertTrue(hashMap.contains(-12));
    }

    @Test(timeout = TIMEOUT)
    public void containsNegativeChaining2() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");
        assertTrue(hashMap.contains(-1));
        assertTrue(hashMap.contains(-12));
        assertTrue(hashMap.contains(-23));
        assertTrue(hashMap.contains(-34));
        assertTrue(hashMap.contains(-45));
    }

    @Test(timeout = TIMEOUT)
    public void containsNegativeRemoved1() {
        hashMap.add(-1, "a");
        assertTrue(hashMap.contains(-1));

        hashMap.remove(-1);
        assertFalse(hashMap.contains(-1));
    }

    @Test(timeout = TIMEOUT)
    public void containsNegativeRemoved2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");
        hashMap.add(-8, "h");

        hashMap.remove(-1);
        assertFalse(hashMap.contains(-1));
        hashMap.remove(-2);
        assertFalse(hashMap.contains(-2));
        hashMap.remove(-3);
        assertFalse(hashMap.contains(-3));
        hashMap.remove(-4);
        assertFalse(hashMap.contains(-4));
        hashMap.remove(-5);
        assertFalse(hashMap.contains(-5));
        hashMap.remove(-6);
        assertFalse(hashMap.contains(-6));
        hashMap.remove(-7);
        assertFalse(hashMap.contains(-7));
        hashMap.remove(-8);
        assertFalse(hashMap.contains(-8));
    }

    @Test(timeout = TIMEOUT)
    public void containsNegativeRemoved3() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");

        hashMap.remove(-1);
        assertFalse(hashMap.contains(-1));
        assertTrue(hashMap.contains(-12));
        assertTrue(hashMap.contains(-23));
        assertTrue(hashMap.contains(-34));
        assertTrue(hashMap.contains(-45));

        hashMap.remove(-12);
        assertFalse(hashMap.contains(-12));
        assertTrue(hashMap.contains(-23));
        assertTrue(hashMap.contains(-34));
        assertTrue(hashMap.contains(-45));

        hashMap.remove(-23);
        assertFalse(hashMap.contains(-23));
        assertTrue(hashMap.contains(-34));
        assertTrue(hashMap.contains(-45));

        hashMap.remove(-34);
        assertFalse(hashMap.contains(-34));
        assertTrue(hashMap.contains(-45));

        hashMap.remove(-45);
        assertFalse(hashMap.contains(-45));
    }

    // test size

    @Test(timeout = TIMEOUT)
    public void sizeNegative1() {
        assertEquals(0, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegative2() {
        hashMap.add(-1, "a");
        assertEquals(1, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegative3() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");

        assertEquals(7, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegative4() {
        for (int i = 0; i > (-100); i--) {
            hashMap.add(i, "a");
        }

        assertEquals(100, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegativeChaining1() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");

        assertEquals(5, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegativeChaining2() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "l"); // element 12 collides with element 1
        hashMap.add(-2, "b");
        hashMap.add(-13, "m"); // element 13 collides with element 2
        hashMap.add(-3, "c");
        hashMap.add(-14, "n"); // element 14 collides with element 3
        hashMap.add(-4, "d");

        assertEquals(7, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegativeDuplicate1() {
        hashMap.add(-1, "a");
        hashMap.add(-1, "b");

        assertEquals(1, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegativeDuplicate2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");
        hashMap.add(-1, "h");

        assertEquals(7, hashMap.size());
    }

    public void sizeNegativeDuplicate3() {
        hashMap.add(-1, "a");
        hashMap.add(-1, "b");
        hashMap.add(-1, "c");
        hashMap.add(-1, "d");
        hashMap.add(-1, "e");
        hashMap.add(-1, "f");
        hashMap.add(-1, "g");
        hashMap.add(-1, "h");

        assertEquals(1, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegativeRemove1() {
        hashMap.add(-1, "a");
        hashMap.remove(-1);

        assertEquals(0, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegativeRemove2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.remove(-2);

        assertEquals(1, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void sizeNegativeRemove3() {
        for (int i = 0; i > (-100); i--) {
            hashMap.add(i, "a");
        }
        for (int i = 0; i > (-50); i--) {
            hashMap.remove(i);
        }

        assertEquals(50, hashMap.size());
    }

    // test clear

    @Test(timeout = TIMEOUT)
    public void clearNegative1() {
        hashMap.add(-1, "a");
        assertEquals(1, hashMap.size());

        hashMap.clear();
        assertEquals(0, hashMap.size());

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void clearNegative2() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        assertEquals(2, hashMap.size());

        hashMap.clear();
        assertEquals(0, hashMap.size());

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void clearNegative3() {
        for (int i = 0; i > (-100); i--) {
            hashMap.add(i, "a");
        }
        assertEquals(100, hashMap.size());

        hashMap.clear();
        assertEquals(0, hashMap.size());

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        assertArrayEquals(array, hashMap.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void clearNegativeChaining1() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");

        assertEquals(5, hashMap.size());

        hashMap.clear();
        assertEquals(0, hashMap.size());

        MapEntry<Integer, String>[] array = (MapEntry<Integer, String>[]) new MapEntry[11];
        assertArrayEquals(array, hashMap.toArray());
    }

    // test keySet

    @Test(timeout = TIMEOUT)
    public void keySetNegative1() {
        HashSet hashSet = new HashSet<Integer>();

        assertEquals(hashSet, hashMap.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void keySetNegative2() {
        hashMap.add(-1, "a");

        HashSet hashSet = new HashSet<Integer>();
        hashSet.add(-1);

        assertEquals(hashSet, hashMap.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void keySetNegative3() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");

        HashSet hashSet = new HashSet<Integer>();
        hashSet.add(-1);
        hashSet.add(-2);

        assertEquals(hashSet, hashMap.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void keySetNegative4() {
        for (int i = 0; i > (-100); i--) {
            hashMap.add(i, "a");
        }

        HashSet hashSet = new HashSet<Integer>();

        for (int i = 0; i > (-100); i--) {
            hashSet.add(i);
        }

        assertEquals(hashSet, hashMap.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void keySetNegativeChaining1() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");

        HashSet hashSet = new HashSet<Integer>();
        hashSet.add(-1);
        hashSet.add(-12);

        assertEquals(hashSet, hashMap.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void keySetNegativeChaining2() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");

        HashSet hashSet = new HashSet<Integer>();
        hashSet.add(-1);
        hashSet.add(-12);
        hashSet.add(-23);
        hashSet.add(-34);
        hashSet.add(-45);

        assertEquals(hashSet, hashMap.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void keySetNegativeDuplicate1() {
        hashMap.add(-1, "a");
        hashMap.add(-1, "b");

        HashSet hashSet = new HashSet<Integer>();
        hashSet.add(-1);

        assertEquals(hashSet, hashMap.keySet());
    }

    // test values

    @Test(timeout = TIMEOUT)
    public void valuesNegative1() {
        ArrayList a = new ArrayList<String>();

        assertEquals(a, hashMap.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesNegative2() {
        hashMap.add(-1, "a");

        ArrayList a = new ArrayList<String>();
        a.add("a");

        assertEquals(a, hashMap.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesNegative3() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");

        ArrayList a = new ArrayList<String>();
        a.add("a");
        a.add("b");

        assertEquals(a, hashMap.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesNegative4() {
        hashMap.add(-1, "a");
        hashMap.add(-2, "b");
        hashMap.add(-3, "c");
        hashMap.add(-4, "d");
        hashMap.add(-5, "e");
        hashMap.add(-6, "f");
        hashMap.add(-7, "g");
        hashMap.add(-8, "h");
        hashMap.add(-9, "i");
        hashMap.add(-10, "j");
        hashMap.add(-11, "k");
        hashMap.add(-12, "l");
        hashMap.add(-13, "m");
        hashMap.add(-14, "n");

        ArrayList a = new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
        a.add("d");
        a.add("e");
        a.add("f");
        a.add("g");
        a.add("h");
        a.add("i");
        a.add("j");
        a.add("k");
        a.add("l");
        a.add("m");
        a.add("n");

        assertEquals(a, hashMap.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesNegativeChaining1() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");

        ArrayList a = new ArrayList<String>();
        a.add("a");
        a.add("b");

        assertEquals(a, hashMap.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesNegativeChaining2() {
        hashMap.add(-1, "a");
        hashMap.add(-12, "b");
        hashMap.add(-23, "c");
        hashMap.add(-34, "d");
        hashMap.add(-45, "e");

        ArrayList a = new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
        a.add("d");
        a.add("e");

        assertEquals(a, hashMap.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesNegativeDuplicate1() {
        hashMap.add(-1, "a");
        hashMap.add(-1, "b");

        ArrayList a = new ArrayList<String>();
        a.add("b");

        assertEquals(a, hashMap.values());
    }

    @Test(timeout = TIMEOUT)
    public void valuesNegativeDuplicate2() {
        hashMap.add(-1, "a");
        hashMap.add(-1, "b");
        hashMap.add(-1, "c");
        hashMap.add(-1, "d");
        hashMap.add(-1, "e");
        hashMap.add(-1, "f");
        hashMap.add(-1, "g");
        hashMap.add(-1, "h");

        ArrayList a = new ArrayList<String>();
        a.add("h");

        assertEquals(a, hashMap.values());
    }

}
