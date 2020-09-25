package ru.job4j;

/**
 * This class demonstrate volatile synchronization.
 * @author Kirill
 */
public final class DCLSingleton {
    private static volatile DCLSingleton inst;
    /**
	 * This method get DCLSingleton instance.
	 * @return DCLSingleton
	 */
    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }
    
    
    private DCLSingleton() {
    }
}