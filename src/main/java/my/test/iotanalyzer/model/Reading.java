package my.test.iotanalyzer.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String metric;
    private double value;
    private Date date;

    public Reading() {
    }

    public Reading(String metric, double value) {
        this.metric = metric;
        this.value = value;
        date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reading{" +
                "id=" + id +
                ", metric='" + metric + '\'' +
                ", value=" + value +
                ", time=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reading)) {
            return false;
        }

        Reading reading = (Reading) o;

        if (Double.compare(reading.value, value) != 0) {
            return false;
        }
        if (date != reading.date) {
            return false;
        }
        return metric != null ? metric.equals(reading.metric) : reading.metric == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = metric != null ? metric.hashCode() : 0;
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (date.getTime() ^ (date.getTime() >>> 32));
        return result;
    }
}
