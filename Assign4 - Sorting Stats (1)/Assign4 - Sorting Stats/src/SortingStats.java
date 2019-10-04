/**
 * This class provides a way to collect statistics on the performance of sorting algorithms.
 *
 * @author James Dean Mathias
 */
public class SortingStats {
    private int swapCount = 0;
    private int compareCount = 0;
    private long timeInMs = 0;

    public void incrementSwapCount() {
        swapCount++;
    }

    public int getSwapCount() {
        return swapCount;
    }

    public void incrementCompareCount() {
        compareCount++;
    }

    public int getCompareCount() {
        return compareCount;
    }

    public void setTime(long timeInMs) {
        this.timeInMs = timeInMs;
    }

    public long getTimeInMs() {
        return timeInMs;
    }
}
