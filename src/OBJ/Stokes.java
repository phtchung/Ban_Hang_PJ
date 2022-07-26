package OBJ;

public class Stokes{
    private int storeID;
    private int productID;
    private String createAt;
    private String goodTill;
    private int quantity;

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getStoreID() {
        return this.storeID;
    }
    
    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductID() {
        return this.productID;
    }

    public void setCreatAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreateAt() {
        return this.createAt;
    }

    public void setGoodTill(String goodTill) {
        this.goodTill = goodTill;
    }

    public String getGoodTill() {
        return this.goodTill;
    }

    public void setQuanity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuanity() {
        return this.quantity;
    }
}