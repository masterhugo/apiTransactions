package co.neoris.apitransactions.repository;

import co.neoris.apitransactions.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    @Query("SELECT p FROM PersonEntity p WHERE p.identification = ?1")
    Optional<PersonEntity> findByIdentification(String identification);
}
