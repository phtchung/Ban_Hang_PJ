/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OBJ;

/**
 *
 * @author ファム。タイン。チュン
 */
public class brands {
    private String brand_id;
    private String name;
    private String country;
    
    public brands() {
    }
    public brands( String brand_id, String name , String country ) {
        this.brand_id = brand_id;
        this.name = name;
        this.country = country;
    }
    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
