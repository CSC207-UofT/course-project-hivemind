import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class PerishableFoodTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(timeout=1000)
    public void testExpiryDate() {
        PerishableFood food = new PerishableFood("Bagel", LocalDate.of(2000, 1, 1));
        assertTrue(food.getExpiryStatus());
    }
}