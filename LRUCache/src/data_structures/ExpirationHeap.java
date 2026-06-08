package data_structures;

import java.util.ArrayList;

public class ExpirationHeap {
	private ArrayList<ClockNode> heap;

    public ExpirationHeap() {
        this.heap = new ArrayList<>();
    }

    public void insert(String key, long expireTime) {
        heap.add(new ClockNode(key, expireTime));
        bubbleUp(heap.size() - 1);
    }

    public ClockNode peekMin() {
        if (isEmpty()) return null;
        return heap.get(0);
    }

    public ClockNode extractMin() {
        if (isEmpty()) return null;
        
        ClockNode minNode = heap.get(0);
        ClockNode lastNode = heap.remove(heap.size() - 1);
        
        if (!heap.isEmpty()) {
            heap.set(0, lastNode);
            sinkDown(0);
        }
        return minNode;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).getExpireTime() >= heap.get(parentIndex).getExpireTime()) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void sinkDown(int index) {
        int size = heap.size();
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && heap.get(leftChild).getExpireTime() < heap.get(smallest).getExpireTime()) {
                smallest = leftChild;
            }
            if (rightChild < size && heap.get(rightChild).getExpireTime() < heap.get(smallest).getExpireTime()) {
                smallest = rightChild;
            }

            if (smallest == index) break;
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        ClockNode temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
