package example.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import co.paralleluniverse.actors.BasicActor;
import co.paralleluniverse.fibers.FiberFactory;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import example.example.quasar.PriorityFiberExecutorScheduler;

public class PriorityApp {

	public static void main(String[] args) {
		PriorityFiberExecutorScheduler fiberExecutorScheduler = new PriorityFiberExecutorScheduler("Prioritized Fiber Executor Scheduler");
		FiberFactory fiberfactory = fiberExecutorScheduler;


		// Create a list of actor numbers
		List<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < 1000; i++)
			numbers.add(i);

		// Shuffle the numbers
		Collections.shuffle(numbers);

		// Print the numbers to verify they are shuffled
		System.out.println(numbers);

		// Create the actors
		for (Integer number : numbers){

			final MutableComparable<Integer> mutableComparable = new MutableComparable<Integer>(number);
			BasicActor<Void, Void> actor = new BasicActor<Void, Void>("Actor " + number) {
				private final MutableComparable<Integer> c = mutableComparable;

				@Override
				protected Void doRun() throws InterruptedException, SuspendExecution {
					// TODO Auto-generated method stub
					Random r = new Random();
					mutableComparable.setComparable(r.nextInt());
					Strand.sleep(1000);
					Thread.sleep(1);
					System.out.println(this.getName() + " with order " + this.c.getComparable());
					return null;
				}
			};

			fiberExecutorScheduler.setComparable(mutableComparable);
			actor.spawn(fiberfactory);
		}

	}

}
