package ru.job4j.cache;

public class OptimisticException extends RuntimeException {
	private String cause;
	/**
	 * 
	 * @param cause
	 */
	public OptimisticException(final String cause) {
		super();
		this.cause = cause;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.cause;
	}

	@Override
	public String toString() {
		return "OptimisticException [cause=" + cause + "]";
	}
	
}
