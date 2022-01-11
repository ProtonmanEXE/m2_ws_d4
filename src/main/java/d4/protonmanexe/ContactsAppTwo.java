// Dev.: ProtonmanEXE
// Dev. Notes: 
// this is the initialisation for SpringBootApplication for Day 4 Workshop whereby ContactsApp is
// connected to Redis database

package d4.protonmanexe;

import java.io.IOException; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactsAppTwo {

	public static void main(String[] args) throws IOException{
		SpringApplication.run(ContactsAppTwo.class, args);
	}

}