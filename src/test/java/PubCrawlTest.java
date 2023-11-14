import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PubCrawlTest {

    @Test
    void testAllPubsOpenTrue() {
    }

    @Test
    void testAddPub() {
        Pub myPub = new Pub();
        PubCrawl myPubCrawl = new PubCrawl("name");
        myPubCrawl.addPub(MyPub);
        assertEquals(myPubCrawl.getPubs()[0], MyPub);
    }

    @Test
    void testRemovePub() {
        Pub myPub = new Pub();
        PubCrawl myPubCrawl = new PubCrawl("name");
        myPubCrawl.addPub(MyPub);
        myPubCrawl.removePub(MyPub);
        assertEquals(myPubCrawl.getPubs().length(), 0);
    }

}
