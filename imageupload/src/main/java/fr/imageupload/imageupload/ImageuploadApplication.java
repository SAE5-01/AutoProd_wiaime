package fr.imageupload.imageupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application Spring Boot pour le micro-service d'upload
 * d'images.
 * Point d'entree de l'application qui demarre le serveur web integre.
 * 
 * @author Etudiant BUT 2
 * @version 1.0
 */
@SpringBootApplication
public class ImageuploadApplication {

	/**
	 * Methode main qui lance l'application Spring Boot.
	 * 
	 * @param args Arguments de ligne de commande passes a l'application
	 */
	public static void main(String[] args) {
		SpringApplication.run(ImageuploadApplication.class, args);
	}

}
