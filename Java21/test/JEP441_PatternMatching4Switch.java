

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JEP441_PatternMatching4Switch {

    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            // case -> no break 
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> obj.toString();
        };
    }

    @Test
    public void testSwitchType() {
        assertEquals("String 0", formatterPatternSwitch("0"));
        assertEquals("long 1", formatterPatternSwitch(Long.valueOf(1L)));
        assertEquals("int 2", formatterPatternSwitch(Integer.valueOf(2)));
        assertEquals("4", formatterPatternSwitch(new StringBuffer("4")));
    }

    static String testStringEnhanced(String response) {
        return switch (response) {
            case null -> "Sorry?"; // handle null
            case "y", "Y" -> "OK"; // special case first
            case "n", "N" -> "Shame";
            case String s when s.equalsIgnoreCase("YES") -> "OK"; // then guarded pattern
            case String s when s.equalsIgnoreCase("NO") -> "Shame";
            case String s -> "Sorry?"; // Then remaining case, unconditional pattern same as default
            };
     }

     @Test
     public void testSwitchWithNullValue_Handle() {
         assertEquals("Sorry?", testStringEnhanced(null)); // handle null
     }

     @Test(expected = NullPointerException.class)
     public void testSwitchWithNullValue_ThrowException() {
         formatterPatternSwitch(null); // null not handled
     }

    @Test
    public void testSwitchWhenRefinement() {
        assertEquals("OK", testStringEnhanced("yes"));
        assertEquals("Shame", testStringEnhanced("NO"));
        assertEquals("Sorry?", testStringEnhanced("maybe"));
    }

    sealed interface CardClassification permits Suit, Tarot {}
    enum Suit implements CardClassification { CLUBS, DIAMONDS, HEARTS, SPADES }
    final class Tarot implements CardClassification {}
    static String exhaustiveSwitchWithBetterEnumSupport(CardClassification c) {
        return switch (c) {
            case Suit.CLUBS -> "CLUBS";
            case Suit.DIAMONDS -> "DIAMONDS";
            case Suit.HEARTS -> "HEARTS";
            case Suit.SPADES -> "SPADES";
            case Tarot t -> "TAROT";
        };
    }

    @Test
    public void testSwitchMixEnumAndType() {
        assertEquals("CLUBS", exhaustiveSwitchWithBetterEnumSupport(Suit.CLUBS));
        assertEquals("TAROT", exhaustiveSwitchWithBetterEnumSupport(new Tarot()));
    }

}
