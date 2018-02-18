package my.test.iotanalyzer.controller;

import my.test.iotanalyzer.model.Reading;
import my.test.iotanalyzer.model.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConsumerController {

    @Autowired
    private ReadingRepository readingRepository;

    @RequestMapping(path="/store/{metric}/{value:.+}", method = RequestMethod.POST)
    public ResponseEntity<String> storeMetric(@PathVariable String metric, @PathVariable double value) {
        readingRepository.save(new Reading(metric, value));
        return ResponseEntity.ok("Registered");
    }
}
