package my.test.iotanalyzer.service;

public interface QueryService {

    Double execute(String metric, String queryType, String from, String to);
}
