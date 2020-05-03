package assignment2;

import java.util.ArrayList;

/**
 * This class implements a dictionary using a hash table with separate chaining.
 * You will decide on the size of the table, keeping in mind that the size of
 * the table must be a prime number.
 * 
 * @author MingCong Zhou
 *
 */
public class HashDictionary  implements DictionaryADT  {
	// constants that are all prime numbers, used for the hash function
	private final int ADD = 5393;
	private final int MULTIPLY = 17;
	private final int PRIME = 12451;

	// field variables
	private int size;
	private ArrayList<Configuration>[] hashTable;

	/**
	 * Constructs a new hash dictionary that uses a hash table to store all entries,
	 * and uses separate chaining to handle collision.
	 * 
	 * @param size the size of the dictionary, better to be a prime number
	 */
	public HashDictionary(int size) {
		this.size = size;
		hashTable = new ArrayList[size];
	}

	/**
	 * Inserts the given Configuration object referenced by data in the dictionary.
	 * 
	 * @param data data the configuration object as an entry
	 * @return 1 if the insertion of the object referenced by data into the hash
	 *         table produces a collision, and 0 otherwise.
	 * @throws DictionaryException if the configuration string stored in data, is
	 *                             already in the dictionary.
	 */
	public int put(Configuration data) throws DictionaryException {
		if (contain(data.getStringConfiguration())) {
			throw new DictionaryException("The data has already been stored!");
		}

		int index = getStringHashValue(data.getStringConfiguration());
		ArrayList<Configuration> al = hashTable[index];
		// Put the data in the ArrayList on the index of the table,
		// create a new one if there is no ArrayList yet.
		if (al == null) {
			al = new ArrayList<Configuration>();
			al.add(data);
			hashTable[index] = al;
			return 0;

		} else {
			al.add(data);
			return 1;
		}
	}

	/**
	 * Removes the entry with configuration string config from the dictionary.
	 * 
	 * @param config the string representation of the entry
	 * @throws DictionaryException if the configuration is not in the dictionary
	 */
	public void remove(String config) throws DictionaryException {
		// use a separate method to check if the data is in the dictionary.
		if (!contain(config)) {
			throw new DictionaryException("The data has not yet been stored!");
		}

		int index = getStringHashValue(config);
		ArrayList<Configuration> al = hashTable[index];

		// no need to check if al is null because we have already validated with
		// contain()
		// check where the data entry is and remove it accordingly.
		// cannot use the built in method in ArrayList because Configuration does not
		// have a ComparaTo method written by us, so ArrayList cannot find the right
		// object to be removed.
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).getStringConfiguration().equals(config)) {
				al.remove(i);
				// if the array list becomes empty, then we should remove the array list
				if (al.isEmpty()) {
					hashTable[index] = null;
				}
				return;
			}
		}

	}

	/**
	 * 
	 * @param config String representation of the game board
	 * @return the score stored in the dictionary for the given configuration
	 *         string, or -1 if the configuration string is not in the dictionary.
	 */
	public int getScore(String config) {

		int index = getStringHashValue(config);
		ArrayList<Configuration> al = hashTable[index];

		// if there is no array list on the index,
		// the configuration is not in the dictionary
		if (al == null) {
			return -1;
		}

		// check all the entries with the same index for the mathcing string
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).getStringConfiguration().equals(config)) {
				return al.get(i).getScore();
			}
		}

		// return -1 to indicate it is not stored in the dictionary because we have
		// checked all the possible locations
		return -1;

	}

	/**
	 * Check if the dictionary contains the key
	 * 
	 * @param str
	 * @return
	 */
	private boolean contain(String str) {
		int index = getStringHashValue(str);
		ArrayList<Configuration> al = hashTable[index];

		// check if an array list exists on the corresponding index
		if (al == null) {
			return false;
		} else {
			// if there is an array list, check all the entries for the corresponding key
			for (int i = 0; i < al.size(); i++) {
				if (al.get(i).getStringConfiguration().equals(str)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Hash a string into an index usable in this dictionary
	 * 
	 * @param str the string to be hashed
	 * @return the hash value of the string
	 */
	private int getStringHashValue(String str) {
		// First use hash code to randomize the hash value
		// and then use the compression function to make the index usable by the
		// dictionary
		return compress(hash(str));
		// !!both methods have similar methods online or in other applications, but the
		// specific implementation, its customization for the use with letters and out
		// tester, and also their combination are all original.
	}

	/**
	 * get the hash code for a string
	 * 
	 * @param str the String to be hashed
	 * @return the hash code for the string
	 */
	private int hash(String str) {
		int hashCode = 0;
		// apply bitwise operations to the letter to randomize the value
		// and add each letter once after the bit operation so the order of the letters
		// also matters (as they result in different values)
		for (int i = 0; i < str.length(); i++) {
			int letterValue = str.charAt(i);
			hashCode = ((hashCode << 5) + letterValue) % PRIME;
			// notice, this hash code might be a negative number when 1 is shifted to the
			// left most place, so the compression value has to handle this
		}
		return hashCode;
	}

	/**
	 * make the hash code value usable for the dictionary while retaining its
	 * randomness
	 * 
	 * @param hashValue the hash value to be compressed
	 * @return the compressed hash value
	 */
	private int compress(int hashValue) {
		// the absolute value method is used to make sure we do not get negative index
		// and the MAD (multiplication - Addition - Division) method is used to minimize
		// the number of collisions
		return (int) Math.abs(((MULTIPLY * (hashValue % Long.MAX_VALUE) + ADD) % PRIME) % size);
	}

}
