package my.test.iotanalyzer.service;

import static org.junit.Assert.assertEquals;

import my.test.iotanalyzer.model.Reading;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DefaultQueryServiceImplTest {
    private static String metric = "metric";


    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public QueryService transferService() {
            return new DefaultQueryServiceImpl();
        }
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QueryService queryService;

    @Before
    public void setUp() {
        double val1 = -5.0;
        double val2 = 0;
        double val3 = 5.0;

        entityManager.persist(new Reading(metric, val1));
        entityManager.persist(new Reading(metric, val2));
        entityManager.persist(new Reading(metric, val3));
    }

    @Test
    public void testService() {
        Double max = queryService
                .execute(metric, QueryType.Max.name(), "2017-01-01 00:00:00", "2019-01-01 00:00:00");
        assertEquals(5.0, max, Double.MIN_NORMAL);

        Double min = queryService
                .execute(metric, QueryType.Min.name(), "2017-01-01 00:00:00", "2019-01-01 00:00:00");
        assertEquals(-5.0, min, Double.MIN_NORMAL);

        Double avg = queryService
                .execute(metric, QueryType.Avg.name(), "2017-01-01 00:00:00", "2019-01-01 00:00:00");
        assertEquals(0, avg, Double.MIN_NORMAL);

        Double cnt = queryService
                .execute(metric, QueryType.Cnt.name(), "2017-01-01 00:00:00", "2019-01-01 00:00:00");
        assertEquals(3.0, cnt, Double.MIN_NORMAL);
    }
}
