package tie.solver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import tie.core.Game;

public class SolverBenchmark {

	// generates a perfect game
	public static int solve(int populationSize, int generations,
			int mutationRate) {
		Game perfect = null;
		int i = 0;
		do {
			FasterSolver s = new FasterSolver(populationSize, generations, mutationRate,
					false);
			perfect = s.tryToSolve();
			i++;
		} while (perfect.getScore() != Game.SCORE_MAX);
		return i;
	}

	public static String benchmarkParameters(int runs, int populationSize,
			int generations, int mutationRate) {

		long start = System.currentTimeMillis();

		int tries = 0;
		for (int i = 0; i < runs; i++) {
			tries += solve(populationSize, generations, mutationRate);
		}

		long delta = System.currentTimeMillis() - start;

		String output = "average time needed for " + runs + " runs, ("
				+ populationSize + " popsize, " + generations + " generations, "
				+ mutationRate + "mutationrate): " + delta / runs + "ms, "
				+ (double) tries / runs + " tries\r\n";
		System.out.println(output);

		return output;
	}

	public static void main(String[] args) throws IOException {

		int tries = 100;
		Path pathBest = Paths
				.get(System.getProperty("user.dir") + "/benchmarks.txt");
		BufferedWriter writerBenchmarks = Files.newBufferedWriter(pathBest);

		for (int populationSize = 1000; populationSize <= 2000; populationSize += 200) {
			for (int generations = 1000; generations <= 2000; generations += 200) {
				writerBenchmarks.write(benchmarkParameters(tries,
						populationSize, generations, Solver.STD_MUTATION_RATE));
			}
		}

		writerBenchmarks.close();
	}

}
