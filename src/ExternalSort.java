import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExternalSort {

	public void externalSort(String inputFile, String outputFile, int n, int k) throws IOException {
		int chunk = 0;
		int chunkSize = n / k;
		Path input = Paths.get(inputFile);
		Path output = Paths.get(outputFile);

		try (BufferedReader reader = Files.newBufferedReader(input)) {
			String line = reader.readLine();
			while (line != null) {
				float arr[] = new float[chunkSize];

				for (int i = 0; i < chunkSize; i++) {
					arr[i] = Float.parseFloat(line);
					line = reader.readLine();
				}

				//sort(arr);

				try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("temp" + (chunk < 10 ? "0" + ++chunk : ++chunk)))) {
					for (float element : arr) {
						writer.write(element + "\n");
					}
				}
			}
		}
	}

	public static void main(String args[]) throws IOException {
		ExternalSort sort = new ExternalSort();
		sort.externalSort("input", "output", 4, 2);
	}
}
