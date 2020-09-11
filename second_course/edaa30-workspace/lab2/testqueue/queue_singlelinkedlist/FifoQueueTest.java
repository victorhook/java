package queue_singlelinkedlist;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class FifoQueueTest extends FifoQueue {

    private FifoQueue<Integer> queue1;
    private FifoQueue<Integer> queue2;

    @BeforeEach
    void setUp() {
        queue1 = new FifoQueue<>();
        queue2 = new FifoQueue<>();
    }

    @AfterEach
    void tearDown() {
        queue1 = null;
        queue2 = null;
    }

    @Test
    void testAppendBothEmpty() {
        queue1.append(queue2);
        assertEquals(0, queue1.size(), "Queue not empty");
        assertEquals(0, queue2.size(), "Queue not empty");
    }

    @Test
    void testAppendOneEmpty() {
        int n = 5;
        for (int i = 0; i < n; i++) {
            queue1.offer(i);
        }
        // Not empty + empty
        queue1.append(queue2);
        assertEquals(n, queue1.size(), "Queue size should be " + n);
        assertEquals(0, queue2.size(), "Queue not empty");
        for (int i = 0; i < n; i++) {
            int value = queue1.poll();
            assertEquals(i, value, String.format("Expected %s, got %s", i, value));
        }

        for (int i = 0; i < n; i++) {
            queue1.offer(i);
        }
        // Empty + not empty
        queue2.append(queue1);
        assertEquals(n, queue2.size(), "Queue size should be " + n);
        assertEquals(0, queue1.size(), "Queue not empty");
        for (int i = 0; i < n; i++) {
            int value = queue2.poll();
            assertEquals(i, value, String.format("Expected %s, got %s", i, value));
        }
    }

    @Test
    void testAppendNotEmpty() {
        int n = 5;
        for (int i = 0; i < n; i++) {
            queue1.offer(i);
            queue2.offer(i);
        }
        queue1.append(queue2);
        assertEquals(2*n, queue1.size(), "Two non-empty queues not concatenated correctly");
        assertEquals(0, queue2.size(), "Queue not empty");

        for (int queue = 0; queue < 2; queue++) {
            for (int i = 0; i < n; i++) {
                int value = queue1.poll();
                assertEquals(i, value, String.format("Expected %s, got %s", i, value));
            }
        }
    }

    @Test
    void testAppendSameQueue() {
        int n = 5;
        for (int i = 0; i < n; i++) {
            queue1.offer(i);
        }
        assertThrows(IllegalArgumentException.class, () -> queue1.append(queue1));
    }

}