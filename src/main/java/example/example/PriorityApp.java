package example.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import co.paralleluniverse.actors.BasicActor;
import co.paralleluniverse.fibers.FiberFactory;
import co.paralleluniverse.fibers.FiberSchedulerTask;
import co.paralleluniverse.fibers.SuspendExecution;
import example.example.quasar.QFiber;
import example.example.quasar.QFiberExecutorScheduler;

public class PriorityApp {

	public static void main(String[] args) {
		int parallelism = Runtime.getRuntime().availableProcessors();
		Comparator<? super Runnable> comparator = (a, b) -> {
			FiberSchedulerTask fiberSchedulerTaskA = (FiberSchedulerTask) a;
			FiberSchedulerTask fiberSchedulerTaskB = (FiberSchedulerTask) b;
			QFiber fiberA = (QFiber) fiberSchedulerTaskA.getFiber();
			QFiber fiberB = (QFiber) fiberSchedulerTaskB.getFiber();
			return fiberA.compareTo(fiberB);
		};
		BlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<Runnable>(11, comparator);
		Executor executor = new ThreadPoolExecutor(parallelism, parallelism, Long.MAX_VALUE, TimeUnit.NANOSECONDS, workQueue);
		QFiberExecutorScheduler fiberExecutorScheduler = new QFiberExecutorScheduler("Prioritized Fiber Executor Scheduler", executor);
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

			BasicActor<Void, Void> actor = new BasicActor<Void, Void>("Actor " + number) {

				@Override
				protected Void doRun() throws InterruptedException, SuspendExecution {
					// TODO Auto-generated method stub
					System.out.println(this.getName());
					return null;
				}
			};

			fiberExecutorScheduler.setComparable(number);
			actor.spawn(fiberfactory);
		}

	}

}
