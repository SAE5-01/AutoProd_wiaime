package fr.imageupload.imageupload.repository;

import fr.imageupload.imageupload.model.ImageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface de repository pour gerer les operations CRUD sur les images.
 * Herite de MongoRepository pour beneficier des methodes de base de donnees.
 * 
 * @author Etudiant BUT 2
 * @version 1.0
 */
public interface ImageRepository extends MongoRepository<ImageDocument, String> {
}
