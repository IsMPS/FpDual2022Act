package edu.fpdual.jdbc.ejemplojdbc.manager;

import edu.fpdual.jdbc.ejemplojdbc.dao.City;

import java.sql.Connection;
import java.util.List;

/**
 * City DTO Manager.
 *
 * Contains all the queries used to consult and manipulate Cities data.
 *
 * @author jose.m.prieto.villar
 *
 */
public interface CityManager {

    /**
     * Finds all the cities in the DB
     *
     * @param con DB connection
     * @return a {@link List} of {@link City}
     */
    public List<City> findAll(Connection con);

    /**
     * Find an specific cities from the DB
     *
     * @param con DB connection
     * @param id the city id
     * @return a {@link List} of {@link City}
     */
    public City findById(Connection con, int id);

    public City findByString(Connection con, String a);

    public List<City> findAllStartedBy(Connection con,String a);

    public List<City> findAllFinished(Connection con,String a);

    public void update(Connection con,City city);

    public void insert(Connection con, City city);
}