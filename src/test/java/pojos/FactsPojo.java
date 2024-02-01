package pojos;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class FactsPojo {

    private Integer currentPage;
    private FactsDataPojo data;
    private Integer total;

    public FactsPojo() {
    }

    public FactsPojo(Integer currentPage, FactsDataPojo data, Integer total) {
        this.currentPage = currentPage;
        this.data = data;
        this.total = total;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public FactsDataPojo getData() {
        return data;
    }

    public void setData(FactsDataPojo data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "FactsPojo{" +
                "currentPage=" + currentPage +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}
