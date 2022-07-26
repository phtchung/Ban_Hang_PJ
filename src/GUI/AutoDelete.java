package GUI; 

import java.sql.*;

public class AutoDelete {
    public void AutoDeleteOrder() {
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            Statement st = con.createStatement();
            int updated = st.executeUpdate("delete from sales.orders where year(getdate()) - year(created_date) = 50;");
            System.out.println(updated);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}