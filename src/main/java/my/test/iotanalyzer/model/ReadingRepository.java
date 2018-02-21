package my.test.iotanalyzer.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReadingRepository extends CrudRepository<Reading, Long> {

    @Query("SELECT AVG(r.value) from Reading r where r.metric = :#{#metric} AND r.date BETWEEN :#{#from} AND :#{#to}")
    Double getAverage(
            @Param("metric") String metric,
            @Param("from") Date from,
            @Param("to") Date to);

    @Query("SELECT MAX(r.value) from Reading r where r.metric = :#{#metric} AND r.date BETWEEN :#{#from} AND :#{#to}")
    Double getMax(
            @Param("metric") String metric,
            @Param("from") Date from,
            @Param("to") Date to);

    @Query("SELECT MIN(r.value) from Reading r where r.metric = :#{#metric} AND r.date BETWEEN :#{#from} AND :#{#to}")
    Double getMin(
            @Param("metric") String metric,
            @Param("from") Date from,
            @Param("to") Date to);

    @Query("SELECT count(r) from Reading r where r.metric = :#{#metric} AND r.date BETWEEN :#{#from} AND :#{#to}")
    Integer getCount(
            @Param("metric") String metric,
            @Param("from") Date from,
            @Param("to") Date to);

    @Query(value = "SELECT value FROM (SELECT * from Reading r where r.metric = ?1  AND r.date BETWEEN ?2 AND ?3 " +
            "ORDER BY value) where ROWNUM = ?4", nativeQuery = true)
    Double getMedian(
            @Param("metric") String metric,
            @Param("from") Date from,
            @Param("to") Date to,
            @Param("rownum") Integer rn);
}
