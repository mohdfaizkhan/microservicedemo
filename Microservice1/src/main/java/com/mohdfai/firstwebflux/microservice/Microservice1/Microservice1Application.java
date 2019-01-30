package com.mohdfai.firstwebflux.microservice.Microservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;


    @EnableDiscoveryClient
	@SpringBootApplication
	public class Microservice1Application {

		private final static String charachater = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		@Bean
		public Supplier<String> getHello() {
			return () -> "Hello";
		}

		@Bean
		public Function<String, String> getUpparCase() {
			return String::toUpperCase;
		}

		@Bean
		public Supplier<Flux<String>> getWords() {
			return () -> Flux.fromArray(new String[]{"FAIZ", "IMDAD"});
		}

		@Bean
		public Function<Flux<String>, Flux<String>> getLowerCase() {
			return flux -> flux.map(String::toLowerCase);
		}

		@Bean
		public Supplier<Flux<UserData>> getUserDatas() {
			return () -> Flux.just(
					new UserData("1"),
					new UserData("2"),
					new UserData("3")
			);
		}

		@Bean
		public Function<Flux<UserData>, Flux<UserData>> getUserWithSomeDetails() {
			return userData -> userData.map(value -> {
				value.miles = String.valueOf(ThreadLocalRandom.current().nextInt(100, 5000));
				return value;
			});
		}


		@Bean
		public Function<Flux<UserData>, Flux<UserData>> setUserDetails() {
			System.out.print("Method start");
			return userData -> userData.map(value -> {
				UserData user = new UserData(value.id);
				user.miles = value.miles;
				user.name = value.name;
				user.passport = value.passport;
				return user;
			});
		}

		@Bean
		public Function<Flux<UserData>, Flux<UserData>> getUserWithName() {
			return userData -> userData.map
					(
							value ->
							{
								StringBuilder autoGenratedSting = new StringBuilder();
								Stream.iterate(1, n -> n + 1).limit(10).forEach
										(
												s -> autoGenratedSting.append(charachater.charAt(ThreadLocalRandom.current().nextInt(9)))
										);
								value.name = autoGenratedSting.toString();
								return value;
							}
					);
		}

		public static void main(String[] args) {
			SpringApplication.run(Microservice1Application.class, args);
		}

	}

