/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltodb.models;

/**
 *
 * @author programacion
 */
public class Movie {
    private int id;
    private String name;
    private String year;
    private String categories;

    public Movie() {
    }
    
    public Movie(int id, String name, String year, String categories) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.categories = categories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", name=" + name + ", year=" + year + ", categories=" + categories + '}';
    }
}
