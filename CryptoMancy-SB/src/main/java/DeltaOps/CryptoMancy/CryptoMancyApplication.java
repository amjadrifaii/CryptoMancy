package DeltaOps.CryptoMancy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j

public class CryptoMancyApplication implements CommandLineRunner {
	private final DataSource dataSource;
	public CryptoMancyApplication(final DataSource dataSource){ this.dataSource=dataSource;}
	public static void main(String[] args) {
		SpringApplication.run(CryptoMancyApplication.class, args);
	}

	@Override
	public void run(final String... args) {
		log.info("Datasource: " + dataSource.toString() );
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");
	}
}
