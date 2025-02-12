import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.SequencedCollection;
import java.util.SequencedSet;
import java.util.TreeSet;
import org.junit.Test;

public class JEP431_SequencedCollections {
    @Test
    public void testList() {
        SequencedCollection<String> l = new LinkedList<>();
        l.addFirst("Deux");
        l.addFirst("Un");
        l.addLast("Trois");

        assertEquals("Un", l.getFirst());
        assertEquals("Trois", l.getLast());

        l.removeFirst();
        assertEquals("Deux", l.getFirst());
        assertEquals("Trois", l.getLast());

        l.addFirst("Un");

        l.removeLast();
        assertEquals("Un", l.getFirst());
        assertEquals("Deux", l.getLast());

        l.addLast("Trois");
        assertEquals(Arrays.asList("Trois", "Deux", "Un"), l.reversed());

    }
    @Test
    public void testLinkedHashSet() {
        SequencedSet<String> s = new LinkedHashSet<>();
        s.addFirst("Deux");
        s.addFirst("Un");
        s.addLast("Trois");

        assertEquals("Un", s.getFirst());
        assertEquals("Trois", s.getLast());

        s.removeFirst();
        assertEquals("Deux", s.getFirst());
        assertEquals("Trois", s.getLast());

        s.addFirst("Un");

        s.removeLast();
        assertEquals("Un", s.getFirst());
        assertEquals("Deux", s.getLast());

        s.addLast("Trois");
        assertEquals(new LinkedHashSet<>(Arrays.asList("Trois", "Deux", "Un")), s.reversed());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testHashSet() {
        SequencedSet<String> s = new TreeSet<String>();
        s.addFirst("Deux");
    }
}
