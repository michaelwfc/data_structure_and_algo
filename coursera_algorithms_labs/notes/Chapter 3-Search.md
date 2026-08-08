# 3.4 [HASH TABLES](https://algs4.cs.princeton.edu/34hash/)

## 1. Hash Basics

Save items in a key-indexed table (index is a function of the key).

### Hash function.

Method for computing array index from key.

### Issues.

- Computing the hash function.
- Equality test: Method for checking whether two keys are equal.
- Collision resolution: Algorithm and data structure
  to handle two keys that hash to the same array index.

### Classic space-time tradeoff.

- No space limitation: trivial hash function with key as index.
- No time limitation: trivial collision resolution with sequential search.
- Space and time limitations: hashing (the real world).

## hash functions

Computing the hash function
Idealistic goal. Scramble the keys uniformly to produce a table index.

- Efficiently computable.
- Each table index equally likely for each key.
  (thoroughly researched problem,still problematic in practical applications)

### Java’s hash code conventions

All Java classes inherit a method hashCode(), which returns a 32-bit int.
**Requirement**. If x.equals(y), then (x.hashCode() == y.hashCode()).
**Highly desirable**. If !x.equals(y), then (x.hashCode() != y.hashCode()).
**Default implementation**. Memory address of x.
**Legal (but poor) implementation**. Always return 17.
**Customized implementations**. Integer, Double, String, File, URL, Date, …
**User-defined types**. Users are on their own.

### Uniform hashing assumption

## 2. Hashing with separate chaining

### Collisions

Collision. Two distinct keys hashing to same index.

- Birthday problem ⇒ can't avoid collisions unless you have a ridiculous (quadratic) amount of memory.
- Coupon collector + load balancing ⇒ collisions are evenly distributed

## 3. Hashing with linear probing

### Open addressing.

[Amdahl-Boehme-Rocherster-Samuel, IBM 1953]
When a new key collides, find next empty slot, and put it there.

Hash. Map key to integer i between 0 and M-1.
Insert. Put at table index i if free; if not try i+1, i+2, etc.
Search. Search table index i; if occupied but no match, try i+1, i+2, etc.

Note. Array size M must be greater than number of key-value pairs N.

### Knuth's parking problem

Model. Cars arrive at one-way street with M parking spaces.
Each desires a random space i : if space i is taken, try i + 1, i + 2, etc.
Q. What is mean displacement of a car?

Half-full. With M / 2 cars, mean displacement is ~ 3 / 2.
Full. With M cars, mean displacement is ~ sqrt(π M / 8) .

# context
