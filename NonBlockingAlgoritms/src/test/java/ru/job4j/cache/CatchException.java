package ru.job4j.cache;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class CatchException {
	/**
	 *  
	 * @throws InterruptedException
	 */
	@Test
	    public void whenThrowException() throws InterruptedException {
	        AtomicReference<Exception> ex = new AtomicReference<>();
	        Thread thread = new Thread(
	                () -> {
	                    try {
	                        throw new RuntimeException("Throw Exception in Thread");
	                    } catch (Exception e) {
	                        ex.set(e);
	                    }
	                }
	        );
	        thread.start();
	        thread.join();
	        Assert.assertThat(ex.get().getMessage(), is("Throw Exception in Thread"));
	    }
}
