import java.util.Random;

/**
 *
 * Sorts the data in an array using a hybrid of two sorts, a quick sort and an insertion sort.
 *
 */
public class HybridSort {

	/**
	 * Performs a hybrid sort. Uses quick sort for large arrays and insertion for small arrays.
	 * @param arr the array
	 * @param left the left index of the subarray
	 * @param right the right index of the subarray
	 */
	public void hybridSort(double []arr, int left, int right) {
		// Use 10 as changing point for sort algorithm
		if (right - left > 10) {
			quickSort(arr, left, right);
		} else {
			quadraticSort(arr, left, right);
		}
	}

	/**
	 * Performs an insertion sort on the array.
	 * @param arr the array
	 * @param left the left index of the subarray
	 * @param right the right index of the subarray
	 */
	private void quadraticSort(double []arr, int left, int right) {
		for (int i = 1; i < arr.length; i++) {
			double temp = arr[i];
			int j = i - 1;

			while (j >= 0 && arr[j] > temp) {
				arr[j + 1] = arr[j];
				j--;
			}

			arr[j + 1] = temp;
		}
	}

	/**
	 * Performs a quick sort on the array.
	 * @param arr the array
	 * @param left the left index of the subarray
	 * @param right the right index of the subarray
	 * @return the index of the pivot after sorting
	 */
	private int quickSort (double[] arr, int left, int right) {
		int p = -1;
		// base case
		if (left < right) {
			p = partition(arr, left, right);
			hybridSort(arr, left, p - 1);
			hybridSort(arr, p + 1, right);
		}
		return p;
	}

	/**
	 * Performs a partition on the array.
	 * @param arr the array
	 * @param left the left index of the subarray
	 * @param right the right index of the subarray
	 * @return the index of the pivot after sorting
	 */
	private int partition (double[] arr, int left, int right) {
		if (left < right) {
			Random random = new Random();
			int pivot = random.nextInt(right - left + 1) + left; // random in the range of left to right (subarray indices)

			// swap the pivot with the left most index so we can use the same partition code with slight adjustment
			swap(arr, pivot, left);

			pivot = left;
			int i = left + 1;
			int j = right;

			while (i < j) {
				while (i <= right && arr[i] <= arr[pivot]) {
					i++;
				}
				while (j >= i && arr[j] > arr[pivot]) {
					j--;
				}
				if (i <= right && i < j) {
					swap(arr, i, j);

				}
			}

			if (arr[pivot] > arr[j]) {
				swap(arr, pivot, j);  // pivot to the middle
			}

			return j;
		}

		return left;
	}

	/**
	 * Swaps two elements of an array
	 * @param a the array
	 * @param i the first element
	 * @param j the second element
	 */
	private void swap(double[] a, int i, int j) {
		double temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main (String []args) {
		HybridSort sort = new HybridSort();
		double[] arr = new double[] {4.2, 23, 6, 18, 22, 5, 10, 2, 8, 11, 12, 15, 203, 234, 5346, 235, 23154, 231, 453};
		sort.hybridSort(arr, 0, arr.length - 1);
		for (double element : arr) {
			System.out.print(element + ",");
		}
	}
}
