package my.test.iotanalyzer.controller;

import my.test.iotanalyzer.model.Reading;
import my.test.iotanalyzer.model.ReadingRepository;
import my.test.iotanalyzer.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QueryController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private ReadingRepository readingRepository;

    @RequestMapping(path = "/query/{metric}/{query}/{from}/{to}", method = RequestMethod.GET)
    @Secured("ROLE_USER")
    public ResponseEntity<String> query(
            @PathVariable String metric,
            @PathVariable String query,
            @PathVariable String from,
            @PathVariable String to) {

        return ResponseEntity.ok(String.format("%s : %s = %10.3f\n\r",
                metric, query, queryService.execute(metric, query, from, to)));
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    @Secured("ROLE_USER")
    public ResponseEntity<Iterable<Reading>> get() {
        return ResponseEntity.ok(readingRepository.findAll());
    }
}
