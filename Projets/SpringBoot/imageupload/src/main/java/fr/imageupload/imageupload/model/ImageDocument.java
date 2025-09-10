package fr.imageupload.imageupload.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Classe entite representant une image stockee en base de donnees MongoDB.
 * Utilise Lombok pour generer automatiquement les getters, setters et autres
 * methodes.
 * 
 * @author Etudiant BUT 2
 * @version 1.0
 */
@Data
@Document(collection = "images")
public class ImageDocument {

    /**
     * Identifiant unique de l'image genere automatiquement par MongoDB.
     */
    @Id
    private String id;

    /**
     * Nom original du fichier image uploade par l'utilisateur.
     */
    private String name;

    /**
     * Type MIME du fichier (par exemple: image/jpeg, image/png).
     */
    private String contentType;

    /**
     * Donnees binaires de l'image stockees sous forme de tableau de bytes.
     */
    private byte[] imageData;
}
