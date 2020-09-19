package bst;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    private BinarySearchTree<Integer> intTree;
    private BinarySearchTree<String> stringTree;

    @BeforeEach
    public void setUp() {
        intTree = new BinarySearchTree<>();
        stringTree = new BinarySearchTree<>();
    }

    @AfterEach
    public void tearDown() {
        intTree = null;
        stringTree = null;
    }

    @Test
    void testAdd() {
        int n = 5;
        for (int i = 0; i < n; i++) {
            assertTrue(intTree.add(i^2));
            assertFalse(intTree.add(i^2));

            String randomString = String.valueOf(70+i);
            assertTrue(stringTree.add(randomString));
            assertFalse(stringTree.add(randomString));
        }
        assertEquals(intTree.size(), n);
        intTree.clear();
        assertEquals(intTree.size(), 0);
    }

    @Test
    void testHeight() {
        int numbers[] = {2, 5, 7, 1, 9, 4};
        //      2
        //     /  \
        //    1    5
        //        /  \
        //       4    7
        //             \
        //              9
        for (Integer nbr: numbers) {
            assertTrue(intTree.add(nbr));
        }
        assertEquals(intTree.height(), 4);
        assertEquals(intTree.size(), 6);

        intTree.clear();
        assertEquals(intTree.height(), 0);
        assertEquals(intTree.size(), 0);
    }

    @Test
    void testComparator() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>((o1, o2) -> {
            return -1 * o1.compareTo(o2);
        });
        int n = 10;
        for (int i = 0; i < n; i++) {
            assertTrue(intTree.add(i));
        }
    }

    @Test
    void clear() {
    }
}