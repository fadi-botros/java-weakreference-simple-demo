package com.experiments.weakreferencedemo;

import java.lang.ref.Reference;
//import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

interface ListenerInterface {
	public void onEvent(int time);
}

class TheListener implements ListenerInterface {
	public void onEvent(int time) {
		System.out.println("An event is listened to, at time: " + time);
	}

	public void start() {
		// Pretend to do a long task
		new Thread() {
			public void run() {
				System.out.println("Starting thread");
				try {
					Thread.sleep(20000);
				} catch(InterruptedException ex) {
				}
				System.out.println("Stopping thread");
			};
		}.start();
	}
}

class TimerTickEventEmitter {
	Reference<ListenerInterface> listener;

	public void start() {
		new Thread() {
			public void run() {
				// Make a timer with duration of 60 seconds with a tick every second
				for(int i = 0; i < 60; i++) {
					// Sleep for a second
					try {
						Thread.sleep(1000);
					} catch(InterruptedException ex) {
					}
					// If the thread is interrupted, exit
					if (isInterrupted()) { return; }
					// If the reference listener is not null, and not referencing a null object, notify it
					if ((null != listener) && (null != listener.get())) { listener.get().onEvent(i); }
				}
			};
		}.start();
	}
}

public class WeakReferenceDemo { 
	public static void main(String[] args) {
		// Make the listener object
		TheListener mainListener = new TheListener();
		// Make the emitter object
		TimerTickEventEmitter mainEmitter = new TimerTickEventEmitter();
		// Start timing
		mainEmitter.start();
		mainEmitter.listener = new WeakReference<ListenerInterface>(mainListener); // Try to replace it with SoftReference and see the change
		// Wait for 10 seconds
		try { Thread.sleep(10000); } catch(InterruptedException ex) { }
		// Expose the listener to the GC
		mainListener = null;
		// Force GC
		System.gc();
		// Wait for 30 seconds, to test whether it will be really released or no
		//    Now no variable refers to the main listener except a weak reference
		//       so it should be released as soon as possible.
		try { Thread.sleep(30000); } catch(InterruptedException ex) { }
	}
}
