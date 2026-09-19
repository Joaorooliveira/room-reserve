package dev.joaorooliveira.room_reserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RoomReserveApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomReserveApplication.class, args);
	}

}
