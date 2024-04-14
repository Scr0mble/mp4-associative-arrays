package structures;

import static java.lang.reflect.Array.newInstance;

// Changelog: added calls to find in a few of my methods to eliminate repeated code. Added code to handle null as key in set, get, and hasKey.

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author William Pitchford
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> newAssocArray = new AssociativeArray<K, V>();

    for(int i = 0; i < this.pairs.length; i++) {
      try {
        newAssocArray.pairs[i] = new KVPair<K, V>(this.pairs[i].key, this.pairs[i].value);
        newAssocArray.size++;
      } catch (NullPointerException e) {
        newAssocArray.pairs[i] = null;
      }
    }
    return newAssocArray;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    if (this.pairs[0] == null) {
      return "{}";
    }
    String arrString = "{ ";
    for (int i = 0; i < this.size; i++) {
      arrString = arrString + this.pairs[i].key + ": " + this.pairs[i].value;
      if (i == this.size - 1) {
        arrString = arrString + " }";
      } else {
        arrString = arrString + ", ";
      } // if else
    } // for loop
    return arrString; // STUB
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    //checks to make sure key is valid
    if(key == null) {
      throw new NullKeyException("Invalid Key");
    }

    //keeps track of array position
    int pos = 0;

    //loops through list to find an empty slot and fill it with new value
    for(KVPair<K, V> pair : this.pairs) {
      if(pair == null) {
        pair = new KVPair<K, V>(key, value);
        if(++this.size == this.pairs.length) {
          expand();
        }
        this.pairs[pos] = pair;
        return;
      } else if(pair.key.equals(key)) {
        pair.value = value;
        return;
      }
      pos++;
    }
    return;
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    if(key == null) {
      throw new KeyNotFoundException("Invalid Key");
    }
    V val = this.pairs[find(key)].value;
    return val;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   * @throws NullKeyException 
   */
  public boolean hasKey(K key) {
    if(key == null) {
      return false;
    }
    try {
      find(key);
    } catch (KeyNotFoundException e) {
      return false;
    }
    return true;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    int i;
    if (this.size != 0) {
      try {
        i = find(key);
      } catch (KeyNotFoundException e) {
        return;
      }
      this.pairs[i] = this.pairs[this.size - 1];
      this.pairs[this.size - 1] = null;
      --this.size;
    }
    return;
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    if(key == null) {
      
    } 
    int i = 0;
    for(KVPair<K, V> pair : this.pairs) {
      try {
        if(pair.key.equals(key)) {
          return i;
        }
        i++;
      } catch (NullPointerException e) {
        i++;
      }
    }
    throw new KeyNotFoundException();
  } // find(K)

} // class AssociativeArray
