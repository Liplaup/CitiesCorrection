package monprojet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import monprojet.entity.City;
import monprojet.entity.Country;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query("SELECT SUM(c.population) FROM City c WHERE c.country.id = :countryId")
    Long calculatePopulationByCountryId(@Param("countryId") Long countryId);
    Country findByCode(String code);

    
    @Query("SELECT new monprojet.dao.CountryPopulationProjection() FROM Country c LEFT JOIN City ci ON c.id = ci.country.id GROUP BY c.name")
    List<CountryPopulationProjection> getCountryPopulationList();
}

