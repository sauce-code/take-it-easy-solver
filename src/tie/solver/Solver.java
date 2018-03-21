package tie.solver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.IntStream;

import tie.core.Game;

public class Solver {

	public static final int STD_POPULATION_SIZE = 2000;

	/*
	 * seems like 1000 generations are enough. 2000 make it safe tho
	 */
	public static final int STD_GENERATIONS = 2000;

	/*
	 * 2 seems to be the best. way better than 1, 3 or 4
	 */
	public static final int STD_MUTATION_RATE = 2;

	public static final boolean STD_LOGGING_ENABLED = false;

	private int populationSize;

	private int generations;

	private int mutationRate;

	private boolean loggingEnabled;

	private BufferedWriter writerBest;

	private BufferedWriter writerPopulationAverage;

	private BufferedWriter writerKeepsAverage;

	public Solver() {
		this(STD_POPULATION_SIZE, STD_GENERATIONS, STD_MUTATION_RATE,
				STD_LOGGING_ENABLED);
	}

	public Solver(boolean loggingEnabled) {
		this(STD_POPULATION_SIZE, STD_GENERATIONS, STD_MUTATION_RATE,
				loggingEnabled);
	}

	public Solver(int populationSize, int generations, int mutationRate,
			boolean loggingEnabled) {

		this.populationSize = populationSize;
		this.generations = generations;
		this.mutationRate = mutationRate;
		this.loggingEnabled = loggingEnabled;

		// init filewriters for logging
		if (loggingEnabled) {
			try {
				Path pathBest = Paths
						.get(System.getProperty("user.dir") + "/best.txt");
				writerBest = Files.newBufferedWriter(pathBest);

				Path pathPopulationAverage = Paths
						.get(System.getProperty("user.dir")
								+ "/population-average.txt");
				writerPopulationAverage = Files
						.newBufferedWriter(pathPopulationAverage);

				Path pathKeepsAverage = Paths.get(
						System.getProperty("user.dir") + "/keeps-average.txt");
				writerKeepsAverage = Files.newBufferedWriter(pathKeepsAverage);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public Game tryToSolve() {

		// init population
		Game[] population = new Game[populationSize];
		IntStream.range(0, populationSize)
				.forEach(i -> population[i] = new Game());

		// init keeps
		LinkedList<Game> keeps = new LinkedList<Game>();
		IntStream.range(0, populationSize).forEach(i -> keeps.add(new Game()));
		Collections.sort(keeps);

		// GO
		for (int i = 0; i < generations; i++) {

			// keep
			IntStream.range(0, populationSize).forEach(j -> {
				if (population[j].getScore() > keeps.getFirst().getScore()) {
					keeps.removeFirst();
					keeps.addLast(new Game(population[j]));
				}
			});

			// replace
			Iterator<Game> iter = keeps.iterator();
			int k = 0;
			Game current;
			while (iter.hasNext()) {
				current = iter.next();
				population[k].clone(current);
				k++;
			}

			// mutate
			Arrays.stream(population).forEach(t -> t.swap(mutationRate));

			// sort keeps
			Collections.sort(keeps);

			// write files
			if (loggingEnabled) {
				try {

					// write into best file
					writerBest.write(keeps.getLast().getScore() + "\r\n");

					// write into population-average file
					int populationAverage = Arrays.stream(population)
							.mapToInt(t -> t.getScore()).sum() / populationSize;
					writerPopulationAverage.write(populationAverage + "\r\n");

					// write into keeps-average file
					int keepsAverage = keeps.stream()
							.mapToInt(t -> t.getScore()).sum() / populationSize;
					writerKeepsAverage.write(keepsAverage + "\r\n");

				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}

		}

		// close writers
		if (loggingEnabled) {
			try {
				writerBest.close();
				writerPopulationAverage.close();
				writerKeepsAverage.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		// return best result
		return keeps.getLast();
	}

	public static void main(String[] args) {
		Game perfect = null;
		int i = 0;
		do {
			i++;
			System.out.println("try #" + i + " ...");
			Solver s = new Solver();
			perfect = s.tryToSolve();
		} while (perfect.getScore() != Game.SCORE_MAX);
		System.out.println("success!");
		System.out.println(perfect);
	}

}
