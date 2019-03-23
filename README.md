# CS-245-Assignment-02

Part 1: Hybrid Sort
Quadratic Sort: Insertion Sort
	Insertion sort is used here as it is one of the faster quadratic sort algorithms that we have learned thus far.
Array Size for Changing Sorts: 50
	The array size of 50 was chosen as it seemed that, after testing the left-side pivot quicksort and the insertion sort from Practice Assignment 05 at small numbers, my quick sort performed more quickly around an array size of 50. However, I have heard from other students that it's around 10 for them, and an online (1) seems to say it is around 5-15, so it could be because of the system-dependent factor.

Notes: 	Testing my random pivot quick sort seems to have an insertion sort speed threshold of around 200...
(1) https://cs.stackexchange.com/questions/37956/why-is-the-optimal-cut-off-for-switching-from-quicksort-to-insertion-sort-machin
	
Part 2: External Sort 
Sorting Algorithm: Merge Sort
	I chose to use merge sort for sorting the chunks simply because I do not know what the size of k could be and it seemed less complicated to implement.

Notes: 	I use try-with-resource blocks so as to not have to worry about calling *.close().

*main() functions used for testing purposes only* 