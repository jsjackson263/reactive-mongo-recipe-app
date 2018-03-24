/**
 * 
 */
package info.jsjackson.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author josan 
 *
 */
public interface ImageService {
	
	void saveImageFile(Long recipeId, MultipartFile file);

}
