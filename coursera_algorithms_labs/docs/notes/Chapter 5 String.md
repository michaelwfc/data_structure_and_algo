
## Definition: Stable Sort
If two elements A and B are equal (A.compareTo(B) == 0) and A appears before B in the original array,
then after sorting, A will still appear before B.


## Key-Indexed Counting
Key-indexed counting is a linear-time sorting algorithm used when:
- You are sorting items with integer keys from a small fixed range.
- You want a stable sort.
It is a key technique behind radix sort and is also commonly used in symbol table implementations.

Given:
An array of items, each with a key in the range 0 to R - 1.
You want to sort the items by their keys, in stable order.

* Goal. Sort an array a[] of N integers between 0 and R - 1.

1. Count Frequencies  
   Count how many times each key appears.
   Count frequencies of each letter using key as index.

2. Compute Start Indices 
   Compute frequency cumulates which specify destinations.
   Transform the counts into starting indices (cumulative sum).

3. Distribute Items  
   Use the start indices to place each item into the correct position
   Access cumulates using key as index to move items.

4. Copy Back  
   Copy back into original array.

```Java
int N = a.length;
String[] aux = new String[N];
int[] count = new int[R+1];
// Compute frequency counts.
for (int i = 0; i < N; i++)
    count[a[i].key() + 1]++; // count frequency for each key
for (int r = 0; r < R; r++)
    count[r+1] += count[r];  // compute the frequency cumulate as the start index for key
// Distribute the records.
for (int i = 0; i < N; i++)
// for each k=a[i].key(), put it on the start index in aux, then increament the start index
    // count[k] initially point to the first free slot for key k in auz
    // as we place the items of key k, we increament count[k].
    // therefore,   the first input item with key k goes to the earliest position reserved for key k
    //  the second input item with key k goes to the next position, and so on.
    // No reordering among equal keys: Since items with the same key are placed in aux in exactly the same sequence they appear in the original, their order is unchanged.
    aux[count[a[i].key()]++] = a[i];  
// Copy back.
for (int i = 0; i < N; i++)
    a[i] = aux[i];

```

## Least-significant-digit-first string sort
LSD string (radix) sort.
- Consider characters from right to left.
- Stably sort using dth character as the key (using key-indexed counting).

Least-Significant-Digit-First (LSD) String Sort is a variant of radix sort tailored for sorting fixed-length (or suitably padded) strings 
by processing characters from the least significant position (rightmost) to the most significant (leftmost). 

At each character position (digit), it performs a stable sort (typically key-indexed counting) on that character. 
Because each pass is stable and processes one “digit” at a time from right to left, the final result is correctly sorted by the entire string.

