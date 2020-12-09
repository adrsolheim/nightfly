package no.adrsolheim.nightfly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class NightflyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NightflyApplication.class, args);
	}

}
