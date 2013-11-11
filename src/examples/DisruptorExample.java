package examples;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorExample {

	private static int RING_SIZE = 1 * 1024;

	private static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Disruptor<ValueEvent> disruptor = new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY, RING_SIZE, EXECUTOR_SERVICE);

		final EventHandler<ValueEvent> handler = new EventHandler<ValueEvent>() {
			public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception {
				System.out.println("Sequence: " + sequence);
				System.out.println("ValueEvent: " + event.getValue());
			}
		};

		disruptor.handleEventsWith(handler);
		RingBuffer<ValueEvent> ringBuffer = disruptor.start();

		for (long i = 0; i < 2000; i++) {
			String uuid = UUID.randomUUID().toString();
			// Two phase commit. Grab one of the 1024 slots
			long seq = ringBuffer.next();
			ValueEvent valueEvent = ringBuffer.get(seq);
			valueEvent.setValue(uuid);
			ringBuffer.publish(seq);
		}
		disruptor.shutdown();
		EXECUTOR_SERVICE.shutdown();
	}

}
