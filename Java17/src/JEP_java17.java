import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.random.RandomGeneratorFactory;

import org.junit.Test;

public class JEP_java17 {
    public static void main(String[] args) {
        // New Random algorithm
        RandomGeneratorFactory.all().forEach(f -> System.out.println(String.format("%s - %s - %s", f.group(), f.name(), f.period())));
        
        // Text bloc
        String textBloc = """
                line 0
                    line 1
                        line 2
            line 3
        """;
        System.out.println(textBloc);

        // Message give the name of null  variable
        Object n = null;
        System.out.println(n.toString());
    }

    sealed abstract class Color permits Red, Green, Blue {}
    final class  Red extends Color {}
    sealed class Green extends Color permits LightGreen {}
    final class  LightGreen extends Green {}
    non-sealed class Blue extends Color {}

    @Test
    public void testJEP360_SealedClasses() {  // since java15
        Color c = new Red();
        assertTrue(c instanceof Red r && r.getClass() == Red.class);

        c = new LightGreen();
        assertTrue(c instanceof Green g && g instanceof LightGreen && g.getClass() == LightGreen.class);
    }
    
    @Test
    public void testJEP394_Instanceof() {  // java16
        Object o = new String("une chaine");
        if (o instanceof String s && /* s in scope */  s.isEmpty() == false) {
            // No cast! s in scope
            assertEquals(s, "une chaine");
        }
        else 
            assertFalse(true);
        
        // s not in scope
    }
    
    record Range(int low, int high)  implements Comparable<Range> { // Record can not extend but can implement
        Range {
            if (low > high)  // referring here to the implicit constructor parameters
                throw new IllegalArgumentException(String.format("(%d,%d)", low, high));
        }

        @Override
        public int compareTo(Range o) {
            throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
        }
    }

    @Test
    public void testJEP395_Record() { // since java 14
        Range r1 = new Range(0,5);
        Range r2 = new Range(0,5);

        assertEquals(5, r1.high());
        assertEquals(0, r1.low());
        assertEquals(r1.hashCode(), r2.hashCode());
        assertEquals(r1, r2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJEP395_Record_throw_exception() { 
        new Range(10,5);
    }
}
