package ru.job4j;

class Item {
	
}

public final class TrackerSingle {
    private TrackerSingle() {
    }
    /**
     * 
     * @return INSTANCE
     */
    public static TrackerSingle getInstance() {
        return Holder.INSTANCE;
    }
    /**
     * 
     * @param model
     * @return model
     */
    public Item add(final Item model) {
        return model;
    }

    private static final class Holder {
        private static final TrackerSingle INSTANCE = new TrackerSingle();
    }
    /**
     * 
     * @param args
     */
    public static void main(final String[] args) {
        TrackerSingle tracker = TrackerSingle.getInstance();
    }
}
