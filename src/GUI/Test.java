/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI; 
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author ADMIN
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    static Connect kn = new Connect();
    private int SlotToInsert() {
        int missingSlot = 1;
        ArrayList<Integer> Slots = new ArrayList<Integer>();
        try{
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select customer_id from sales.customers order by customer_id;");
            if(rs.next() == false) return 0;
            else {
                do { 
                    Slots.add(rs.getInt(1));
                }while(rs.next());
                for(int i = 0; i < Slots.size(); i++, missingSlot++){
                    if(missingSlot != Slots.get(i)){
                        return missingSlot - 1;
                    }
                }
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return missingSlot - 1;
    }
    
    public static void main(String[] args) throws SQLException {
        
        Connection cn = kn.getConnectDB();
        Statement stm = null;
        ResultSet rs = null;
        String sql = "select * from sales.staffs";
        try{
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);
            while(rs.next()){
                System.out.println("staff_id: "+rs.getString(1)+"\tName: "+rs.getString(2)+"\tEmail: "+rs.getString(3)+"\t  password: "+rs.getString(9));
            }
        } catch (Exception e) {
            System.out.println("Loi gi do roi"+e);
        
        }
    }
    
}
