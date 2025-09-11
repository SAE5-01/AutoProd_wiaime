// Je declare mon package pour organiser mes classes dans le projet
package fr.imageupload.imageupload.controller;

// J'importe la classe ImageDocument que j'ai cree pour representer une image
import fr.imageupload.imageupload.model.ImageDocument;
// J'importe le service qui va gerer la logique metier des images
import fr.imageupload.imageupload.service.ImageService;
// J'importe Lombok pour generer automatiquement le constructeur
import lombok.RequiredArgsConstructor;
// J'importe les classes Spring pour gerer les headers HTTP
import org.springframework.http.HttpHeaders;
// J'importe les codes de statut HTTP que je vais utiliser
import org.springframework.http.HttpStatus;
// J'importe MediaType pour definir le type de contenu des reponses
import org.springframework.http.MediaType;
// J'importe ResponseEntity pour construire mes reponses HTTP
import org.springframework.http.ResponseEntity;
// J'importe les annotations Spring Web pour creer mon API REST
import org.springframework.web.bind.annotation.*;
// J'importe MultipartFile pour recevoir les fichiers uploades
import org.springframework.web.multipart.MultipartFile;

// J'importe IOException pour gerer les erreurs d'entree/sortie
import java.io.IOException;

/**
 * Controleur REST pour gerer les operations sur les images.
 * Expose les endpoints pour uploader et recuperer des images.
 * 
 * @author Etudiant BUT 2
 * @version 1.0
 */
// Je marque ma classe comme un controleur REST avec cette annotation
@RestController
// Je definis le chemin de base pour toutes mes routes d'images
@RequestMapping("/api/images")
// Lombok va generer un constructeur avec tous les champs final
@RequiredArgsConstructor
// Je declare ma classe controleur pour gerer les requetes d'images
public class ImageController {

    /**
     * Service injecte pour gerer la logique metier des images.
     */
    // Je declare mon service comme final pour l'injection de dependance
    private final ImageService imageService;

    /**
     * Endpoint pour uploader une image via une requete POST.
     * 
     * @param file Le fichier image a uploader
     * @return ResponseEntity avec un message de succes ou d'erreur
     */
    // Je cree une route POST pour uploader des images
    @PostMapping("/upload")
    // Ma methode recoit un fichier et retourne une reponse avec un message
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        // J'utilise try-catch pour gerer les erreurs potentielles
        try {
            // J'appelle mon service pour sauvegarder l'image et recuperer son ID
            String imageId = imageService.uploadImage(file);
            // Je retourne une reponse positive avec l'ID de l'image creee
            return ResponseEntity.ok("Image uploaded successfully with ID: " + imageId);
            // Je capture les erreurs d'input/output qui peuvent survenir
        } catch (IOException e) {
            // Je retourne une erreur 500 avec le message d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Image upload failed: " + e.getMessage());
        }
    }

    /**
     * Endpoint pour recuperer une image par son identifiant via une requete GET.
     * 
     * @param id L'identifiant unique de l'image a recuperer
     * @return ResponseEntity contenant les donnees binaires de l'image ou erreur
     *         404
     */
    // Je cree une route GET pour recuperer une image par son ID
    @GetMapping("/{id}")
    // Ma methode recoit un ID et retourne les donnees de l'image
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        // J'appelle mon service pour recuperer l'image depuis la base
        ImageDocument image = imageService.getImage(id);
        // Je verifie si l'image existe dans ma base de donnees
        if (image == null) {
            // Si pas d'image trouvee je retourne une erreur 404
            return ResponseEntity.notFound().build();
        }
        // Je construis ma reponse avec les donnees de l'image
        return ResponseEntity.ok()
                // J'ajoute un header pour telecharger le fichier avec son nom original
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                // Je definis le type MIME du contenu pour que le navigateur sache quoi faire
                .contentType(MediaType.parseMediaType(image.getContentType()))
                // Je retourne les donnees binaires de l'image dans le corps de la reponse
                .body(image.getImageData());
    }
}
