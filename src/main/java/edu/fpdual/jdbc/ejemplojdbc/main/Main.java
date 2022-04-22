package edu.fpdual.jdbc.ejemplojdbc.main;

import edu.fpdual.jdbc.ejemplojdbc.connector.MySQLConnector;
import edu.fpdual.jdbc.ejemplojdbc.dao.City;
import edu.fpdual.jdbc.ejemplojdbc.manager.impl.CityManagerImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Connects to the DB

        try (Connection con = new MySQLConnector().getMySQLConnection()){
            // Looks for all the cities in the DB and prints them.
            System.out.println(new CityManagerImpl().findById(con, 2));
            // Looks for the City Madrid
            System.out.println(new CityManagerImpl().findByString(con,"madrid"));
            // Find all the cities which starts with A
            System.out.println(new CityManagerImpl().findAllStartedBy(con,"A").size());
            // Find all the cities that end with A
            System.out.println(new CityManagerImpl().findAllFinished(con,"s").size());
            // Update the city with id=1
            new CityManagerImpl().update(con,new City(1,"a","a",1500));
//			List<Country> countries = new CountryManager().findBySurfaceAreaBetween(con, BigDecimal.valueOf(100),
//					BigDecimal.valueOf(1000));
//			System.out.println(countries.size());
//			countries.forEach(country -> System.out.println(country));
//			new GeneralManager().findLanguajeDataWithPercentageGreaterThan(con, 0)
//					.forEach(data -> System.out.printf(
//							"Datos de la ciudad %s: lenguaje -> %s - Porcentaje de habla: %f - Pais: (%s) %s ",
//							data.getCityName(), data.getCityLanguage(), data.getLanguagePercentage(),
//							data.getCountryCode(), data.getCountryName() + "\n"));
        }
    }

}
