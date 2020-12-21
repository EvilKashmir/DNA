import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class GenomeToDb {

    private final Sql2o sql2o;

    public GenomeToDb() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("D:\\JavaProjects\\GenomDNA\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver.classname"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        sql2o = new Sql2o(new HikariDataSource(hikariConfig));
    }

    final String INSERT_DNA2_INTO_TABLE = "insert into DNA3 (id, part) values(:id, :part)";

    final String INSERT_DNA5_INTO_TABLE = "insert into DNA5 (id, part) values(:id, :part)";

    final String INSERT_DNA9_INTO_TABLE = "insert into DNA9 (id, part) values(:id, :part)";

    final String SELECT3 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select part, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, part, count(*) as count from dna3 where id = :id1 or id = :id2 group by id, part)\n" +
            "                          as it2c\n" +
            "                group by part)\n" +
            "                 as t2m where count_id > 1) /\n" +
            "       (select count(*) from dna3 where id = :id1 or id = :id2)::float as coincidences;";

    final String SELECT5 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select part, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, part, count(*) as count from dna5 where id = :id1 or id = :id2 group by id, part)\n" +
            "                          as it2c\n" +
            "                group by part)\n" +
            "                 as t2m where count_id > 1) /\n" +
            "       (select count(*) from dna5 where id = :id1 or id = :id2)::float as coincidences;";

    final String SELECT9 = "select (select sum(min_count)\n" +
            "        from (\n" +
            "                 select part, 2*min(count) as min_count, count(id) as count_id\n" +
            "                 from (select id, part, count(*) as count from dna9 where id = :id1 or id = :id2 group by id, part)\n" +
            "                          as it2c\n" +
            "                group by part)\n" +
            "                 as t2m where count_id > 1) /\n" +
            "       (select count(*) from dna9 where id = :id1 or id = :id2)::float as coincidences;";

    public void insertGenome3(Genome genome) {
        List<String> dnaList = genome.getTaken3();
        for (String dnam : dnaList) {
            try (Connection connection = sql2o.open()) {
                connection.createQuery(INSERT_DNA2_INTO_TABLE)
                        .addParameter("id", genome.getId())
                        .addParameter("part", dnam).executeUpdate();
            }
        }
    }

    public void insertGenome5(Genome genome) {
        List<String> dnaList = genome.getTaken5();
        for (String dnam : dnaList) {
            try (Connection connection = sql2o.open()) {
                connection.createQuery(INSERT_DNA5_INTO_TABLE)
                        .addParameter("id", genome.getId())
                        .addParameter("part", dnam).executeUpdate();
            }
        }
    }

    public void insertGenome9(Genome genome) {
        List<String> genomeList = genome.getTaken9();
        for (String dnam : genomeList) {
            try (Connection connection = sql2o.open()) {
                connection.createQuery(INSERT_DNA9_INTO_TABLE)
                        .addParameter("id", genome.getId())
                        .addParameter("part", dnam).executeUpdate();
            }
        }
    }

    public Float getCoincidencesPercent3(Integer id1, Integer id2) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(SELECT3)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2).executeScalar(Float.class);
        }
    }

    public Float getCoincidencesPercent5(Integer id1, Integer id2) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(SELECT5)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2).executeScalar(Float.class);
        }
    }

    public Float getCoincidencesPerCent9(Integer id1, Integer id2) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(SELECT9)
                    .addParameter("id1", id1)
                    .addParameter("id2", id2).executeScalar(Float.class);
        }
    }
}
