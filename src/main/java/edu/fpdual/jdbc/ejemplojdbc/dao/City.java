package edu.fpdual.jdbc.ejemplojdbc.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class City implements Comparable<City>{

    int id;
    String name;
    Country country;
    String district;
    BigDecimal population;

    public City() {

    }
    public City(ResultSet result) {
        try {
            this.id = result.getInt("ID");
            this.name = result.getString("name");
            this.district = result.getString("District");
            this.population = result.getBigDecimal("Population");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public City(int id, String name, String district, int population) {
            this.id = id;
            this.name = name;
            this.district = district;
            this.population = BigDecimal.valueOf(population) ;
    }

    @Override
    public int compareTo(City o) {
        return this.name.compareTo(o.getName());
    }
}
