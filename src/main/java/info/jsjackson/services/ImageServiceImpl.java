/**
 * 
 */
package info.jsjackson.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan 
 *
 */

/*
 * Do not store images to the file system 
 * - you want to keep the application portable so you can 
 * - you can export out to different application servers let it run in a cluster
 * - otherwise you have problems determining which system  did you persist that image to.
 * 
 * - alternatives: either store in a db, or Amazon S3, then set up a service to serve them from S3  
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}


	@Override
	@Transactional
	public void saveImageFile(String recipeId, MultipartFile file) {
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
			Byte[] byteObjects = new Byte[file.getBytes().length];
			
			//no autoboxing available for arrays inside Java 
			//- so we have to live with this conversion from the primitive to the wrapper object
			int i = 0;
			for (byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			
			recipe.setImage(byteObjects);
			
			recipeRepository.save(recipe);
			
		} catch (IOException e) {
			//TODO: handle better
			log.error("Error occurred " + e);
			e.printStackTrace();
		}
	}

}
