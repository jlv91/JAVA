import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.random.RandomGeneratorFactory;

import org.junit.Test;

import jdk.incubator.vector.IntVector;

public class JEP_Java21 { // --add-modules jdk.incubator.vector at runtime
    @Test
    public void testJEP338_Vectorization() { // since java 17, use Single Instruction Multiple Data.
        var drg = RandomGeneratorFactory.getDefault().create();

        var a = drg.ints(8).toArray();
        var b = drg.ints(8).toArray();
        var expected = new int[8];

        // classique, peut etre vectorisé per JIT
        for (var i = 0; i < 8; i++) {
            expected[i] = a[i] + b[i];
        }

        var aVectorized = IntVector.fromArray(IntVector.SPECIES_PREFERRED, a, 0);
        var bVectorized = IntVector.fromArray(IntVector.SPECIES_PREFERRED, b, 0);
        var cVectorized = aVectorized.add(bVectorized);

        assertArrayEquals(expected, cVectorized.toArray());
    }
}
