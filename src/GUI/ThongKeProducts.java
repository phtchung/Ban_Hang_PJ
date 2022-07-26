package GUI; 



import java.awt.Window;
import javax.mail.PasswordAuthentication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.security.auth.kerberos.ServicePermission;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class ThongKeProducts extends javax.swing.JPanel {
    DefaultTableModel tbn = new DefaultTableModel();
    sendMail sm=new sendMail();
    
    
    
    public ThongKeProducts() {
        initComponents();
        BoxBanchay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số lượng", "Doanh Thu" }));
        BoxBancham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số lượng", "Doanh Thu" }));
        datefrom.setDate(new Date());
        dateto.setDate(new Date());
    }
     
  
    
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        BtnBanchay = new javax.swing.JButton();
        BtnBancham = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BoxBanchay = new javax.swing.JComboBox<>();
        BoxBancham = new javax.swing.JComboBox<>();
        datefrom = new com.toedter.calendar.JDateChooser();
        dateto = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bgtksp.png"))); // NOI18N

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        setLayout(null);

        BtnBanchay.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BtnBanchay.setContentAreaFilled(false);
        BtnBanchay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBanchayActionPerformed(evt);
            }
        });
        add(BtnBanchay);
        BtnBanchay.setBounds(610, 260, 150, 70);

        BtnBancham.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BtnBancham.setContentAreaFilled(false);
        BtnBancham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBanchamActionPerformed(evt);
            }
        });
        add(BtnBancham);
        BtnBancham.setBounds(1100, 260, 160, 60);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(510, 380, 940, 350);

        BoxBanchay.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BoxBanchay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BoxBanchay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxBanchayActionPerformed(evt);
            }
        });
        add(BoxBanchay);
        BoxBanchay.setBounds(480, 270, 120, 50);

        BoxBancham.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BoxBancham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BoxBancham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxBanchamActionPerformed(evt);
            }
        });
        add(BoxBancham);
        BoxBancham.setBounds(960, 270, 120, 50);

        datefrom.setDate(new java.util.Date(1610103917000L));
        datefrom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(datefrom);
        datefrom.setBounds(640, 140, 170, 50);

        dateto.setDate(new java.util.Date(1641261337000L));
        dateto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(dateto);
        dateto.setBounds(1150, 140, 160, 40);

        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(10, 690, 380, 110);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/frame TK sp.png"))); // NOI18N
        jLabel6.setText("jLabel6");
        add(jLabel6);
        jLabel6.setBounds(0, 0, 1540, 810);
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBanchamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBanchamActionPerformed
        // TODO add your handling code here:
            try{
             Date tdate1 = datefrom.getDate();
             Date tdate2 = dateto.getDate();
             Date tdate = new Date();  
             if (tdate1.after(tdate2)) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            return;
        } else if (tdate1.after(tdate)) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            return;
        }
            Connect a = new Connect();
            Connection conn =a.getConnectDB();
           
            String sql_soluong = "Select TOP 5 With TIES sales.order_items.product_id, sum(sales.order_items.quantity) as total "
                    + ",production.products.product_name,production.products.price from sales.orders inner join sales.order_items on "
                    + "sales.orders.order_id =sales.order_items.order_id "
                    + "inner join production.products on sales.order_items.product_id = production.products.product_id "
                    + " where sales.orders.created_date between (?) and (?) " 
                    + " group by sales.order_items.product_id,production.products.product_name,production.products.price "
                    + " order by total ASC ";
            
            String sql_doanhthu = "Select TOP 5 With TIES sales.order_items.product_id, sum(sales.order_items.quantity*sales.order_items.profit) as total "
                    + ",production.products.product_name,production.products.price from sales.orders inner join sales.order_items on "
                    + " sales.orders.order_id =sales.order_items.order_id "
                    + "inner join production.products on sales.order_items.product_id = production.products.product_id "
                    + " where sales.orders.created_date between (?) and (?) "
                    + " group by sales.order_items.product_id,production.products.product_name,production.products.price "
                    + " order by total ASC ";
            
            PreparedStatement ps = conn.prepareStatement(sql_soluong);
            PreparedStatement ps1 = conn.prepareStatement(sql_doanhthu);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = sdf.format(datefrom.getDate());
            String date2 = sdf.format(dateto.getDate());
            ps.setString(1, date1);
            ps.setString(2, date2);
            ps1.setString(1, date1);
            ps1.setString(2, date2);
            ResultSet rs = ps.executeQuery(); 
            ResultSet rs1 = ps1.executeQuery(); 
          
            if(BoxBancham.getSelectedItem().toString().equals("Số lượng")){
                tbn.setRowCount(0);
            String []colsName = {"Mã sản phẩm", "Tên sản phẩm","Giá","Số lượng"};
            tbn.setColumnIdentifiers(colsName);
            while(rs.next()){
                    String value[] = new String[4];
                    value[0]=rs.getInt("product_id")+"";
                    value[1]=rs.getString("product_name");
                    value[2]=rs.getDouble("price")+"";
                    value[3]=rs.getInt("total")+"";
                    tbn.addRow(value);
                    jTable1.setModel(tbn);       
            }     
            } else if(BoxBancham.getSelectedItem().toString().equals("Doanh Thu")){
                tbn.setRowCount(0);
                    String []colsName = {"Mã sản phẩm", "Tên sản phẩm","Giá","Doanh thu"};
            tbn.setColumnIdentifiers(colsName);
            while(rs1.next()){
                    String value[] = new String[4];
                    value[0]=rs1.getInt("product_id")+"";
                    value[1]=rs1.getString("product_name");
                    value[2]=rs1.getDouble("price")+"";
                    value[3]=rs1.getDouble("total")+"";
                    tbn.addRow(value);
                    jTable1.setModel(tbn);       
            } 
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_BtnBanchamActionPerformed

    private void BtnBanchayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBanchayActionPerformed
        // TODO add your handling code here:
        try{
            Date tdate1 = datefrom.getDate();
             Date tdate2 = dateto.getDate();
             Date tdate = new Date();  
             if (tdate1.after(tdate2)) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            return;
        } else if (tdate1.after(tdate)) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            return;
        }
            Connect a = new Connect();
            Connection conn =a.getConnectDB();
             String sql_soluong = "Select TOP 5 With TIES sales.order_items.product_id, sum(sales.order_items.quantity) as total "
                    + ",production.products.product_name,production.products.price from sales.orders inner join sales.order_items on "
                    + "sales.orders.order_id =sales.order_items.order_id "
                    + "inner join production.products on sales.order_items.product_id = production.products.product_id "
                    + " where sales.orders.created_date between (?) and (?) " 
                    + " group by sales.order_items.product_id,production.products.product_name,production.products.price "
                    + " order by total DESC ";
            
             String sql_doanhthu = "Select TOP 5 With TIES sales.order_items.product_id, sum(sales.order_items.quantity*sales.order_items.profit) as total "
                    + ",production.products.product_name,production.products.price from sales.orders inner join sales.order_items on "
                    + " sales.orders.order_id =sales.order_items.order_id "
                    + "inner join production.products on sales.order_items.product_id = production.products.product_id "
                    + " where sales.orders.created_date between (?) and (?) "
                    + " group by sales.order_items.product_id,production.products.product_name,production.products.price "
                    + " order by total DESC ";
            PreparedStatement ps = conn.prepareStatement(sql_soluong);
            PreparedStatement ps1 = conn.prepareStatement(sql_doanhthu);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = sdf.format(datefrom.getDate());
            String date2 = sdf.format(dateto.getDate());
            ps.setString(1, date1);
            ps.setString(2, date2);
            ps1.setString(1, date1);
            ps1.setString(2, date2);
            ResultSet rs = ps.executeQuery(); 
            ResultSet rs1 = ps1.executeQuery(); 
            if(BoxBanchay.getSelectedItem().toString().equals("Số lượng")){
                   tbn.setRowCount(0);
            String []colsName = {"Mã sản phẩm", "Tên sản phẩm","Giá","Số lượng"};
            tbn.setColumnIdentifiers(colsName);
            while(rs.next()){
                    String value[] = new String[4];
                    value[0]=rs.getInt("product_id")+"";
                    value[1]=rs.getString("product_name");
                    value[2]=rs.getDouble("price")+"";
                    value[3]=rs.getInt("total")+"";
                    tbn.addRow(value);
                    jTable1.setModel(tbn);       
            }     
            } else if(BoxBanchay.getSelectedItem().toString().equals("Doanh Thu")){
                    tbn.setRowCount(0);
                    String []colsName = {"Mã sản phẩm", "Tên sản phẩm","Giá","Doanh thu"};
            tbn.setColumnIdentifiers(colsName);
            while(rs1.next()){
                    String value1[] = new String[4];
                    value1[0]=rs1.getInt("product_id")+"";
                    value1[1]=rs1.getString("product_name");
                    value1[2]=rs1.getDouble("price")+"";
                    value1[3]=rs1.getDouble("total")+"";
                    tbn.addRow(value1);
                    jTable1.setModel(tbn);       
            } 
            }   
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
         
    }//GEN-LAST:event_BtnBanchayActionPerformed

    private void BoxBanchayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxBanchayActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_BoxBanchayActionPerformed

    private void BoxBanchamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxBanchamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BoxBanchamActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> BoxBancham;
    private javax.swing.JComboBox<String> BoxBanchay;
    private javax.swing.JButton BtnBancham;
    private javax.swing.JButton BtnBanchay;
    private com.toedter.calendar.JDateChooser datefrom;
    private com.toedter.calendar.JDateChooser dateto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    // End of variables declaration//GEN-END:variables
}
