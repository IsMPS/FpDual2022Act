package edu.fpdual.jdbc.ejemplojdbc.manager.impl;

import edu.fpdual.jdbc.ejemplojdbc.dao.City;
import edu.fpdual.jdbc.ejemplojdbc.dao.Country;
import edu.fpdual.jdbc.ejemplojdbc.manager.CityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * City DTO Manager.
 *
 * Contains all the queries used to consult and manipulate Cities data.
 *
 * @author jose.m.prieto.villar
 *
 */
public class CityManagerImpl implements CityManager {

    public List<City> findAll(Connection con) {
        // Create general statement
        try (Statement stmt = con.createStatement()) {
            // Queries the DB
            ResultSet result = stmt.executeQuery("SELECT * FROM City");
            // Set before first registry before going through it.
            result.beforeFirst();

            // Initializes variables
            List<City> cities = new ArrayList<>();
            Map<Integer, String> countries = new HashMap();

            // Run through each result
            while (result.next()) {
                // Initializes a city per result
                cities.add(new City(result));
                // Groups the countried by city
                countries.put(result.getInt("ID"), result.getString("CountryCode"));
            }

            // Fills the country of each city
            fillCountries(con, countries, cities);

            return cities;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public City findById(Connection con, int id) {
        //prepare SQL statement
        String sql = "select * "
                + "from city a, Country b "
                + "where a.id = ? "
                + "and a.CountryCode = b.Code";

        // Create general statement
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            //Add Parameters
            stmt.setInt(1, id);
            // Queries the DB
            ResultSet result = stmt.executeQuery();
            // Set before first registry before going through it.
            result.beforeFirst();

            // Initialize variable
            City city = null;

            // Run through each result
            while (result.next()) {
                // Initializes a city per result
                city = new City(result);
                Country country = new Country(result);
                city.setCountry(country);
            }

            return city;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public City findByString(Connection con, String a) {
        //prepare SQL statement
        String sql = "select * "
                + "from city a, Country b "
                + "where a.name = ? "
                + "and a.CountryCode = b.Code";

        // Create general statement
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            //Add Parameters
            stmt.setString(1, a);
            // Queries the DB
            ResultSet result = stmt.executeQuery();
            // Set before first registry before going through it.
            result.beforeFirst();

            // Initialize variable
            City city = null;

            // Run through each result
            while (result.next()) {
                // Initializes a city per result
                city = new City(result);
                Country country = new Country(result);
                city.setCountry(country);
            }

            return city;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<City> findAllStartedBy(Connection con,String a){
        //prepare SQL statement
        String sql = "select * "
                + "from city a, Country b "
                + "where a.name like ? ";

        // Create general statement
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            //Add Parameters
            stmt.setString(1, a+"%");
            // Queries the DB
            ResultSet result = stmt.executeQuery();
            // Set before first registry before going through it.
            result.beforeFirst();

            // Initialize variable
            City city = null;
            List<City> cities = new ArrayList<>();

            // Run through each result
            while (result.next()) {
                // Initializes a city per result
                city = new City(result);
                Country country = new Country(result);
                city.setCountry(country);
                cities.add(city);
            }

            return cities;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<City> findAllFinished(Connection con,String a){
        //prepare SQL statement
        String sql = "select * "
                + "from city a, Country b "
                + "where a.name like ? ";

        // Create general statement
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            //Add Parameters
            stmt.setString(1, "%"+a);
            // Queries the DB
            ResultSet result = stmt.executeQuery();
            // Set before first registry before going through it.
            result.beforeFirst();

            // Initialize variable
            City city = null;
            List<City> cities = new ArrayList<>();

            // Run through each result
            while (result.next()) {
                // Initializes a city per result
                city = new City(result);
                Country country = new Country(result);
                city.setCountry(country);
                cities.add(city);
            }

            return cities;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Connection con,City city){
        //prepare SQL statement
        String sql = "update City set District = ?, name = ?, Population = ? where id = ?";

        // Create general statement
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            //Add Parameters
            stmt.setString(1,city.getDistrict() );
            stmt.setString(2,city.getName() );
            stmt.setBigDecimal(3,city.getPopulation());
            stmt.setInt(4,city.getId() );
            // Queries the DB
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Connection con, City city){

    }

    /**
     * Fills all the countries for each city.
     *
     * @param con       the Db connection
     * @param countries the map of cities and countries.
     * @param cities    the list of cities to update.
     */
    private void fillCountries(Connection con, Map<Integer, String> countries, List<City> cities) {
        // Obtains all the country codes to search
        Set<String> countryCodes = new HashSet<>(countries.values());

        // Looks for all countries and groups them by id.
        Map<String, Country> countriesMap = new CountryManagerImpl().findAllById(con, countryCodes).stream()
                .collect(Collectors.toMap(Country::getId, data -> data));

        // Associates the corresponding Country to each City
        cities.forEach(city -> {
            String countryCode = countries.get(city.getId());
            Country foundCountry = countriesMap.get(countryCode);
            city.setCountry(foundCountry);
        });

    }

}
