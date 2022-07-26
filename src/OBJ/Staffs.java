package OBJ;

public class Staffs{
    private int staffID;
    private String name;
    private String email;
    private String phone;
    private int active;
    private int storeID;
    private int managerState;
    private String gender;

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }
    public int getStaffID() {
        return this.staffID;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive() {
        return this.active;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getStoreID() {
        return this.storeID;
    }

    public void setManagerState(int managerState) {
        this.managerState = managerState;
    }

    public int getManagerState() {
        return this.managerState;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }
}