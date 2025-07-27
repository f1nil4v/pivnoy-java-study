package ttv.poltoraha.pivka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.Edition;

@Repository
public interface EditionRepository extends CrudRepository<Edition, Integer> {

}
