package pojos;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class FactsDataPojo {
    private String fact;
    private Integer lenght;

    public FactsDataPojo() {
    }

    public FactsDataPojo(String fact, Integer lenght) {
        this.fact = fact;
        this.lenght = lenght;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    @Override
    public String toString() {
        return "FactsDataPojo{" +
                "fact='" + fact + '\'' +
                ", lenght=" + lenght +
                '}';
    }
}
