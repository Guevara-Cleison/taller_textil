package com.taller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class TallerTextilApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerTextilApplication.class, args);
		System.out.println("Se ejecuto la aplicacion");
		
		/*String contra="admin";
		
		System.out.println(contra);
		
		System.out.println("encriptado : " +encriptar(contra));*/
		
		
		
		
	}
	
	/*public static String encriptar(String contra) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(contra);
	}*/
	
}
