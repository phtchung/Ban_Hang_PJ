/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OBJ;

/**
 *
 * @author MyPC
 */
public class sales_product {
    private String creat_at_date;
    private String product_id;
    private String good_at_date;
    private String discount;
    private String price;

    public sales_product() {
    }

    public sales_product(String creat_at_date, String product_id, String good_at_date, String discount, String price) {
        this.creat_at_date = creat_at_date;
        this.product_id = product_id;
        this.good_at_date = good_at_date;
        this.discount = discount;
        this.price = price;
    }

    public String getCreat_at_date() {
        return creat_at_date;
    }

    public void setCreat_at_date(String creat_at_date) {
        this.creat_at_date = creat_at_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getGood_at_date() {
        return good_at_date;
    }

    public void setGood_at_date(String good_at_date) {
        this.good_at_date = good_at_date;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    
}
