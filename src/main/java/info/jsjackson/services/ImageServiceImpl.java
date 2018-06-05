/**
 * 
 */
package info.jsjackson.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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

	private final RecipeReactiveRepository recipeReactiveRepository;
	
	public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
		this.recipeReactiveRepository = recipeReactiveRepository;
	}


	@Override
	public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
		
		Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId)
		.map(recipe -> {
			
			Byte[] byteObjects = new Byte[0];
			
			try {
			
				byteObjects = new Byte[file.getBytes().length];
			
				int i =0;
			
				for (byte b : file.getBytes()) {
					byteObjects[i++] = b;
				}
			
				recipe.setImage(byteObjects);
				
				return recipe;
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		});
		
		recipeReactiveRepository.save(recipeMono.block()).block();
		
		return Mono.empty();
		
		
		/*try {
			Recipe recipe = recipeReactiveRepository.findById(recipeId).get();
			Byte[] byteObjects = new Byte[file.getBytes().length];
			
			//no autoboxing available for arrays inside Java 
			//- so we have to live with this conversion from the primitive to the wrapper object
			int i = 0;
			for (byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			
			recipe.setImage(byteObjects);
			
			recipeReactiveRepository.save(recipe);
			
		} catch (IOException e) {
			//TODO: handle better
			log.error("Error occurred " + e);
			e.printStackTrace();
		}*/
	}

}
