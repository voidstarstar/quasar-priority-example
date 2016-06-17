package example.example.quasar;

import java.util.concurrent.Executor;

import co.paralleluniverse.fibers.RunnableFiberTask;

public class QRunnableFiberTask<V> extends RunnableFiberTask<V> implements Comparable<QRunnableFiberTask<V>> {

	private QFiber fiber;

	public QRunnableFiberTask(QFiber<V> fiber, Executor executor) {
		super(fiber, executor);
		this.fiber = fiber;
	}

	@Override
	public int compareTo(QRunnableFiberTask<V> arg0) {
		return this.fiber.compareTo(arg0.fiber);
	}

}
