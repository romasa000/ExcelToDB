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
public class Category {
    
    private int Id;
    private String CategoryName;

    public Category(int Id, String CategoryName) {
        this.Id = Id;
        this.CategoryName = CategoryName;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public int getId() {
        return Id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    @Override
    public String toString() {
        return "Category{" + "Id=" + Id + ", CategoryName=" + CategoryName + '}';
    }
    
    
}
