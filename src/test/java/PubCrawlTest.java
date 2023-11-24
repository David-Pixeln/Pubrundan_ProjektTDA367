import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;
import com.google.android.gms.maps.model.LatLng;

public class PubCrawlTest {

    @Test
    void testAllPubsOpenTrue() {
        Pub myPub1 = new Pub("namePub1", LocalTime.of(15,00), LocalTime.of(03,00), Position());
        Pub myPub2 = new Pub("namePub2", LocalTime.of(15,00), LocalTime.of(01,00), Position());
        PubCrawl myPubCrawl = new PubCrawl("name");
        myPubCrawl.addPub(myPub1);
        myPubCrawl.addPub(myPub2);
        assertEquals(myPubCrawl);
    }

    @Test
    void testAddPub() {
        Pub myPub = new Pub();
        PubCrawl myPubCrawl = new PubCrawl("name");
        myPubCrawl.addPub(myPub);
        assertEquals(myPubCrawl.getPubs()[0], MyPub);
    }

    @Test
    void testRemovePub() {
        Pub myPub = new Pub();
        PubCrawl myPubCrawl = new PubCrawl("name");
        myPubCrawl.addPub(myPub);
        myPubCrawl.removePub(myPub);
        assertEquals(myPubCrawl.getPubs().length(), 0);
    }

    @Test
    void testGetClosingTime() {}

    @Test
    void testGetOpeningTime() {
        
    }
}
