import java.util.List;

public class Main {
    public static void main(String[] args) {
        GenomeToDb post = new GenomeToDb();
        parseAndInsert(post);
        System.out.println("dna3: " + post.getCoincidencesPercent3(1, 2) * 100);
        System.out.println("dna5: " + post.getCoincidencesPercent5(1, 2) * 100);
        System.out.println("dna9: " + post.getCoincidencesPerCent9(1, 2) * 100);
    }

    private static void parseAndInsert(GenomeToDb post) {

        GenomeReader firstReader = new GenomeReader("D:\\JavaProjects\\GenomDNA\\src\\main\\resources\\Genome_1-1.txt");
        GenomeReader secondReader = new GenomeReader("D:\\JavaProjects\\GenomDNA\\src\\main\\resources\\Genome_2-1.txt");

        List<String> t3_1 = firstReader.take(3);
        List<String> t5_1 = firstReader.take(5);
        List<String> t9_1 = firstReader.take(9);
        Genome genome1 = new Genome(1, t3_1, t5_1, t9_1);
        List<String> t3_2 = secondReader.take(3);
        List<String> t5_2 = secondReader.take(5);
        List<String> t9_2 = secondReader.take(9);
        Genome genome2 = new Genome(2, t3_2, t5_2, t9_2);
        post.insertGenome3(genome1);
        post.insertGenome5(genome1);
        post.insertGenome9(genome1);
        post.insertGenome3(genome2);
        post.insertGenome5(genome2);
        post.insertGenome9(genome2);
    }
}
