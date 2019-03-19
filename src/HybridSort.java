import java.util.Random;

public class HybridSort {

	private int quicksort (int[] arr, int left, int right) {
		// base case
		if (left < right) {
			int p = partition(arr, left, right);
			quicksort(arr, left, p - 1);
			quicksort(arr, p + 1, right);
		}

		return -1;
	}

	private int partition (int[] arr, int left, int right) {
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

	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main (String []args) {
		HybridSort sort = new HybridSort();
		int[] arr = new int[] {4, 23, 6, 18, 22, 5, 10, 2, 8, 11};
		sort.quicksort(arr, 0, arr.length - 1);
		System.out.println("Final");
		for (int element : arr) {
			System.out.print(element + ",");
		}

	}
}
