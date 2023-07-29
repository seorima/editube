package team_iproject_main.team_iproject_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TeamIProjectMainApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TeamIProjectMainApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TeamIProjectMainApplication.class, args);
	}

}
