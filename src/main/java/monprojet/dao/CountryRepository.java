package monprojet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import monprojet.entity.City;
import monprojet.entity.Country;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("SELECT SUM(c.population) FROM City c WHERE c.country.id = :countryId")
    Long calculatePopulationByCountryId(@Param("countryId") Long countryId);
    Country findByCode(String code);


        // Méthode pour récupérer une liste de paires (nom du pays, population)
        @Query("SELECT country.name, city.population FROM Country c JOIN c.cities city GROUP BY country.id")
        List<CountryProjection> getCountryPopulationList();
    }
