package info.jsjackson.services;

import java.util.Optional;

import info.jsjackson.domain.Category;
import info.jsjackson.domain.UnitOfMeasure;

public interface UnitOfMeasureService extends CRUDService<UnitOfMeasure> {
	Optional<Category> findByDescription(String description);

}
