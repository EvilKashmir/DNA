import java.util.List;

public class Genome {

    private final Integer id;
    private final List<String> taken3;
    private final List<String> taken5;
    private final List<String> taken9;

    public List<String> getTaken3() {
        return taken3;
    }

    public List<String> getTaken5() {
        return taken5;
    }

    public List<String> getTaken9() {
        return taken9;
    }

    public Integer getId() {
        return id;
    }

    public Genome(Integer id, List<String> taken3, List<String> taken5, List<String> taken9) {
        this.id = id;
        this.taken3 = taken3;
        this.taken5 = taken5;
        this.taken9 = taken9;
    }
}
