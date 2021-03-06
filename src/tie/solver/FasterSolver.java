package tie.solver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.IntStream;

import tie.core.FasterGame;
import tie.core.Game;

public class FasterSolver extends Solver {

	public static final int STD_POPULATION_SIZE = 1000;

	/*
	 * seems like 1000 generations are enough. 2000 make it safe tho
	 */
	public static final int STD_GENERATIONS = 1000;

	/*
	 * 2 seems to be the best. way better than 1, 3 or 4
	 */
	public static final int STD_MUTATION_RATE = 2;

	public static final boolean STD_LOGGING_ENABLED = false;

	public FasterSolver() {
		this(STD_POPULATION_SIZE, STD_GENERATIONS, STD_MUTATION_RATE,
				STD_LOGGING_ENABLED);
	}

	public FasterSolver(boolean loggingEnabled) {
		this(STD_POPULATION_SIZE, STD_GENERATIONS, STD_MUTATION_RATE,
				loggingEnabled);
	}

	public FasterSolver(int populationSize, int generations, int mutationRate,
			boolean loggingEnabled) {
		super(populationSize, generations, mutationRate, loggingEnabled);
	}

	@Override
	public Game tryToSolve() {

		// init population
		FasterGame[] population = new FasterGame[populationSize];
		IntStream.range(0, populationSize)
				.forEach(i -> population[i] = new FasterGame());

		// init keeps
		LinkedList<Game> keeps = new LinkedList<Game>();
		IntStream.range(0, populationSize)
				.forEach(i -> keeps.add(new FasterGame()));
		Collections.sort(keeps);

		// GO
		for (int i = 0; i < generations; i++) {

			// keep
			IntStream.range(0, populationSize).forEach(j -> {
				if (population[j].getScore() > keeps.getFirst().getScore()) {
					keeps.removeFirst();
					keeps.addLast(new FasterGame(population[j]));
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
			FasterSolver s = new FasterSolver();
			perfect = s.tryToSolve();
		} while (perfect.getScore() != Game.SCORE_MAX);
		System.out.println("success!");
		System.out.println(perfect);
	}

}
