package pojos;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BreedPojo {

    private Integer current_page;
    private BreedDataPojo data;
    private Integer total;


    public BreedPojo() {

    }

    public BreedPojo(Integer current_page, BreedDataPojo data, Integer total) {
        this.current_page = current_page;
        this.data = data;
        this.total = total;
    }

    public Object getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public BreedDataPojo getData() {
        return data;
    }

    public void setData(BreedDataPojo data) {
        this.data = data;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "breedPojo{" +
                "current_page='" + current_page + '\'' +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}

