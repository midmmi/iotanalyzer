package my.test.iotanalyzer.service;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import my.test.iotanalyzer.model.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultQueryServiceImpl implements QueryService {

    @Autowired
    private ReadingRepository readingRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Double execute(String metric, String queryType, String fromStr, String toStr) {
        Date from = Date.from(LocalDateTime.parse(fromStr, formatter).atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(LocalDateTime.parse(toStr, formatter).atZone(ZoneId.systemDefault()).toInstant());

        QueryType qt = QueryType.valueOf(queryType);
        switch (qt) {
            case Max:
                return readingRepository.getMax(metric, from, to);
            case Min:
                return readingRepository.getMin(metric, from, to);
            case Avg:
                return readingRepository.getAverage(metric, from, to);
            case Mdn:
                int count = readingRepository.getCount(metric, from, to);
                if (count > 0) {
                    Double median = readingRepository.getMedian(metric, from, to, count / 2 + 1);
                    if (count > 1 && count % 2 == 0) {
                        median = (median + readingRepository.getMedian(metric, from, to, count / 2)) / 2;
                    }
                    return median;
                } else {
                    return 0.0;
                }
            case Cnt:
                return (double) readingRepository.getCount(metric, from, to);
            default:
                //This case is possible when somebody adds constant to QueryType enum
                throw new IllegalArgumentException("Unknown query type: " + queryType);
        }
    }
}
