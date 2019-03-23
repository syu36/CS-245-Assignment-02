import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * Reads in data from a file and separates them into sorted chunks, placing them in a temp file. The temp files are then merged and sorted and written to an output file.
 *
 */
public class ExternalSort {

	/**
	 * Sorts a set of data from a file and writes it out to an output file.
	 * @param inputFile the input file
	 * @param outputFile the output file
	 * @param n the number of items in the file
	 * @param k the amount of memory available
	 * @throws IOException
	 */
	public void externalSort(String inputFile, String outputFile, int n, int k) throws IOException {
		int chunk = 0;
		Path input = Paths.get(inputFile);
		Path output = Paths.get(outputFile);

		// sort chunks and write to temp file
		chunk = sortChunks(input, k);

		// merge files
		float[] merged = new float[n];
		mergeChunks(merged, chunk);

		// write out sorted data
		writeSortedArray(merged, output);
	}

	/**
	 * Merges the chunks (temp files) into one sorted array.
	 * @param merged the sorted array
	 * @param chunk the number of chunks
	 * @throws IOException
	 */
	private void mergeChunks(float[] merged, int chunk) throws IOException {
		float[] tempArray = new float[0];

		// number of chunks is also number of temp files
		for (int i = chunk; i > 0; i--) {
			// use a count of items in the array to know size of temparray
			int prevArrayCount = 0;

			try (BufferedReader tempFile = Files.newBufferedReader(Paths.get("temp" + (chunk < 10 ? "0" + chunk-- : chunk--)))) {
				int mergedi = 0;
				int tempi = 0;

				String fileItem = tempFile.readLine();

				// Basically use merge algorithm, temparray is the left array and the tempFile is the right array
				while (tempi < tempArray.length && fileItem != null) {
					float item = Float.parseFloat(fileItem);

					if (tempArray[tempi] <= item) {
						merged[mergedi++] = tempArray[tempi++];
					} else {
						merged[mergedi++] = item;
						fileItem = tempFile.readLine();
					}

					prevArrayCount++;
				}

				while (tempi < tempArray.length) {
					merged[mergedi++] = tempArray[tempi++];
					prevArrayCount++;
				}

				while (fileItem != null) {
					float item = Float.parseFloat(fileItem);
					merged[mergedi++] = item;
					fileItem = tempFile.readLine();
					prevArrayCount++;
				}
			}
			// Set tempArray to all data in merged but not as large as merged is. This allows us to merge each file into the merged array
			tempArray = Arrays.copyOf(merged, prevArrayCount);
		}
	}

	/**
	 * Splits the input data into chunks and sorts them, then writes those chunks out to a tempfile.
	 * @param input the input file
	 * @param k the amount of available memory
	 * @return the number of chunks
	 * @throws IOException
	 */
	private int sortChunks(Path input, int k) throws IOException {
		int chunk = 0;

		// Read input, sort into chunks, place in temp files
		try (BufferedReader reader = Files.newBufferedReader(input)) {
			String line = reader.readLine();
			while (line != null) {

				float arr[] = new float[k];

				for (int i = 0; i < k; i++) {

					try {
						arr[i] = Float.parseFloat(line);
						System.out.println(line);
					} catch (NullPointerException e){
						// turns arr into a new array that is the size of the remaining elements in last chunk
						arr = Arrays.copyOf(arr, i);
						// set i to k to get out of loop
						i = k;
					}
					line = reader.readLine();
				}

				// Use mergesort for sorting chunks
				mergeSort(arr);

				try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("temp" + (chunk < 9 ? "0" + ++chunk : ++chunk)))) {
					for (float element : arr) {
						writer.write(element + "\n");
					}
				}
			}
		}
		return chunk;
	}

	/**
	 * Writes the sorted data out to the output file.
	 * @param arr the sorted array
	 * @param output the output file
	 * @throws IOException
	 */
	private void writeSortedArray(float[] arr, Path output) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(output)) {
			for (float item : arr) {
				writer.write(Float.toString(item));
				writer.newLine();
			}
		}
	}

	/**
	 * Performs a merge sort.
	 * @param a the array to merge sort
	 */
	private void mergeSort(float[] a) {
		float[] left = getLeft(a);
		float[] right = getRight(a);
		if (left.length > 1) {
			mergeSort(left);
		}
		if (right.length > 1) {
			mergeSort(right);
		}
		merge(a, left, right);
	}

	/**
	 * Merges two arrays together, sorting as they merge
	 * @param array the array to sort
	 * @param left the left split of the array
	 * @param right the right split of the array
	 */
	private void merge(float[] array, float[] left, float[] right) {
		int lefti = 0;
		int righti = 0;
		int arrayi = 0;
		while (lefti < left.length && righti < right.length) {
			if(left[lefti] <= right[righti]) {
				array[arrayi++] = left[lefti++];
			} else {
				array[arrayi++] = right[righti++];
			}
		}

		while (lefti < left.length) {
			array[arrayi++] = left[lefti++];
		}

		while (righti < right.length) {
			array[arrayi++] = right[righti++];
		}
	}

	/**
	 * Helper method to return the left half of an array
	 * @param array the array
	 * @return the right half of the array
	 */
	private float[] getLeft(float[] array) {
		return Arrays.copyOfRange(array, 0, array.length / 2);
	}

	/**
	 * Helper method to return the right half of an array
	 * @param array the array
	 * @return the right half of the array
	 */
	private float[] getRight(float[] array) {
		return Arrays.copyOfRange(array, array.length / 2, array.length);
	}

	public static void main(String args[]) throws IOException {
		ExternalSort sort = new ExternalSort();
		sort.externalSort("input", "output", 30, 4);
	}
}
