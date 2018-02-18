package my.test.iotanalyzer.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReadingTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReadingRepository readingRepository;

    @Test
    public void testEntity() {
        double val = Math.random();
        String metric = "Some_metric";
        this.entityManager.persist(new Reading(metric, val));
        Iterator<Reading> iterator = readingRepository.findAll().iterator();
        assertTrue(iterator.hasNext());
        Reading reading = iterator.next();
        assertNotNull(reading);
        assertEquals(metric, reading.getMetric());
        assertEquals(val, reading.getValue(), Double.MIN_NORMAL);
    }
}
