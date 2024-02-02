package monprojet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }
    @Test
    public void testCalculatePopulationByCountryId() {
        Long countryId = 1L; // Remplace par un country ID existant
        Long population = countryDAO.calculatePopulationByCountryId(countryId); // Change en countryDAO
        assertNotNull(population);
        System.out.println("Population for Country ID " + countryId + ": " + population);
    }
    @Test
    public void testPopulationOfFranceIs12() {
        String countryCode = "FR"; // Replace with the actual country code for France
        Country france = countryDAO.findByCode(countryCode);
    
        assertNotNull(france, "France should exist in the database");
        
        // Assuming the population of France is 12
        Long expectedPopulation = 12L;
    
        // Convert the Integer to Long before passing it to the method
        Long actualPopulation = countryDAO.calculatePopulationByCountryId(france.getId().longValue());
    
        assertEquals(expectedPopulation, actualPopulation, "Population of France should be 12");
    }


    @Test
    public void testGetCountryPopulationList() {
        List<CountryProjection> countryPopulationList = countryDAO.getCountryPopulationList();
        assertNotNull(countryPopulationList);
        for (CountryProjection projection : countryPopulationList) {
            System.out.println("Country: " + projection.getName() + ", Population: " + projection.getPopulation());
        }
    }

}
