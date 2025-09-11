package fr.imageupload.imageupload.service;

import fr.imageupload.imageupload.model.ImageDocument;
import fr.imageupload.imageupload.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service contenant la logique metier pour la gestion des images.
 * Gere l'upload, la sauvegarde et la recuperation des images.
 * 
 * @author Etudiant BUT 2
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    /**
     * Repository pour les operations de base de donnees sur les images.
     */
    private final ImageRepository imageRepository;

    /**
     * Upload et sauvegarde une image dans la base de donnees.
     * 
     * @param file Le fichier image a uploader
     * @return L'identifiant unique de l'image sauvegardee
     * @throws IOException Si une erreur survient lors de la lecture du fichier
     */
    public String uploadImage(MultipartFile file) throws IOException {
        ImageDocument image = new ImageDocument();
        image.setName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setImageData(file.getBytes());

        image = imageRepository.save(image);
        return image.getId();
    }

    /**
     * Recupere une image depuis la base de donnees grace a son identifiant.
     * 
     * @param id L'identifiant unique de l'image a recuperer
     * @return L'objet ImageDocument correspondant ou null si non trouve
     */
    public ImageDocument getImage(String id) {
        return imageRepository.findById(id).orElse(null);
    }
}
