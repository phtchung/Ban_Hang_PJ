package GUI;
 

//import static com.sun.org.apache.xalan.internal.lib.ExsltDynamic.map;
import java.awt.Window;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class ThongKeBrands extends javax.swing.JPanel {
    DefaultTableModel tbn = new DefaultTableModel();
    static int store = Integer.parseInt(Login.Store_ID);
   
    
     
    public ThongKeBrands() {
        initComponents();
        BoxTKBrand_Chay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số lượng", "Doanh Thu" }));
        BoxTKBrand_Cham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Số lượng", "Doanh Thu" }));
        datefrom.setDate(new Date());
        dateto.setDate(new Date());
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        BtnBanchay = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BoxTKBrand_Chay = new javax.swing.JComboBox<>();
        BtnBancham = new javax.swing.JButton();
        BoxTKBrand_Cham = new javax.swing.JComboBox<>();
        datefrom = new com.toedter.calendar.JDateChooser();
        dateto = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

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
        BtnBanchay.setBounds(610, 250, 150, 70);

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        jScrollPane1.setBounds(510, 370, 930, 360);

        BoxTKBrand_Chay.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BoxTKBrand_Chay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BoxTKBrand_Chay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxTKBrand_ChayActionPerformed(evt);
            }
        });
        add(BoxTKBrand_Chay);
        BoxTKBrand_Chay.setBounds(450, 270, 120, 50);

        BtnBancham.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BtnBancham.setContentAreaFilled(false);
        BtnBancham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBanchamActionPerformed(evt);
            }
        });
        add(BtnBancham);
        BtnBancham.setBounds(1110, 260, 140, 60);

        BoxTKBrand_Cham.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BoxTKBrand_Cham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BoxTKBrand_Cham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxTKBrand_ChamActionPerformed(evt);
            }
        });
        add(BoxTKBrand_Cham);
        BoxTKBrand_Cham.setBounds(950, 270, 120, 50);

        datefrom.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(datefrom);
        datefrom.setBounds(640, 130, 190, 50);

        dateto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(dateto);
        dateto.setBounds(1120, 130, 200, 50);

        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(10, 680, 390, 120);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TK brands.png"))); // NOI18N
        jLabel6.setText("jLabel6");
        add(jLabel6);
        jLabel6.setBounds(0, -10, 1560, 830);
    }// </editor-fold>//GEN-END:initComponents

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
            String sql_soluong = "SELECT TOP 5 With TIES pb.brand_id,pb.brand_name,pb.country,count(PPRO.product_id) as number_product,sum(quantity) as total from "
                    + "sales.orders as SO inner join sales.order_items as ODI on SO.order_id = ODI.order_id " 
                    + " inner join production.products as PPRO on ODI.product_id = PPRO.product_id " 
                    + " inner join production.brands as pb on PPRO.brand_id = pb.brand_id " 
                    + " where SO.created_date between (?) and (?) " 
                    + " group by PB.brand_id,pb.brand_name,pb.country order by total DESC " ;
            String sql_doanhthu = "SELECT TOP 5 With TIES pb.brand_id,pb.brand_name,pb.country,count(PPRO.product_id)as number_product,sum(ODI.profit*quantity) as total "
                    + " from sales.orders as SO inner join sales.order_items as ODI on SO.order_id = ODI.order_id " 
                    + " inner join production.products as PPRO on ODI.product_id = PPRO.product_id " 
                    + " inner join production.brands as pb on PPRO.brand_id = pb.brand_id " 
                    + " where SO.created_date between (?) and (?) " 
                    + " group by PB.brand_id,pb.brand_name,pb.country order by total DESC ";
            
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
            if(BoxTKBrand_Chay.getSelectedItem().toString().equals("Số lượng")){
                tbn.setRowCount(0);
            String []colsName = {"Mã thương hiệu", "Tên thương hiệu","Quốc gia","Số loại sản phẩm","Số lượng"};
            tbn.setColumnIdentifiers(colsName);
                while(rs.next()){
                    String value[] = new String[5];
                    value[0]=rs.getString("brand_id");
                    value[1]=rs.getString("brand_name");
                    value[2]=rs.getString("country");
                    value[3]=rs.getInt("number_product")+"";
                    value[4]=rs.getInt("total")+"";
                    tbn.addRow(value);
                    jTable1.setModel(tbn);       
                }  
            }else if(BoxTKBrand_Chay.getSelectedItem().toString().equals("Doanh Thu")){
                tbn.setRowCount(0);
            String []colsName = {"Mã thương hiệu", "Tên thương hiệu","Quốc gia","Số loại sản phẩm","Doanh thu"};
            tbn.setColumnIdentifiers(colsName);
                while(rs1.next()){
                    String value[] = new String[5];
                    value[0]=rs1.getString("brand_id");
                    value[1]=rs1.getString("brand_name");
                    value[2]=rs1.getString("country");
                    value[3]=rs1.getInt("number_product")+"";
                    value[4]=rs1.getInt("total")+"";
                    tbn.addRow(value);
                    jTable1.setModel(tbn);       
                }  
            }
            
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        
        
        
    }//GEN-LAST:event_BtnBanchayActionPerformed

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
            String sql_soluong = "SELECT TOP 5 With TIES pb.brand_id,pb.brand_name,pb.country,count(PPRO.product_id) as number_product,sum(quantity) as total from "
                    + "sales.orders as SO inner join sales.order_items as ODI on SO.order_id = ODI.order_id " 
                    + " inner join production.products as PPRO on ODI.product_id = PPRO.product_id " 
                    + " inner join production.brands as pb on PPRO.brand_id = pb.brand_id " 
                    + " where SO.created_date between (?) and (?) " 
                    + " group by PB.brand_id,pb.brand_name,pb.country order by total ASC " ;
            
            String sql_doanhthu = "SELECT TOP 5 With TIES pb.brand_id,pb.brand_name,pb.country,count(PPRO.product_id)as number_product,sum(ODI.profit*quantity) as total "
                    + " from sales.orders as SO inner join sales.order_items as ODI on SO.order_id = ODI.order_id " 
                    + " inner join production.products as PPRO on ODI.product_id = PPRO.product_id " 
                    + " inner join production.brands as pb on PPRO.brand_id = pb.brand_id " 
                    + " where SO.created_date between (?) and (?) " 
                    + " group by PB.brand_id,pb.brand_name,pb.country order by total ASC ";
           
            
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
            if(BoxTKBrand_Cham.getSelectedItem().toString().equals("Số lượng")){
                tbn.setRowCount(0);
            String []colsName = {"Mã thương hiệu", "Tên thương hiệu","Quốc gia","Số loại sản phẩm","Số lượng"};
            tbn.setColumnIdentifiers(colsName);
                while(rs.next()){
                    String value[] = new String[5];
                    value[0]=rs.getString("brand_id");
                    value[1]=rs.getString("brand_name");
                    value[2]=rs.getString("country");
                    value[3]=rs.getInt("number_product")+"";
                    value[4]=rs.getInt("total")+"";
                    tbn.addRow(value);
                    jTable1.setModel(tbn);       
                }  
            }else if(BoxTKBrand_Cham.getSelectedItem().toString().equals("Doanh Thu")){
                tbn.setRowCount(0);
            String []colsName = {"Mã thương hiệu", "Tên thương hiệu","Quốc gia","Số loại sản phẩm","Doanh thu"};
            tbn.setColumnIdentifiers(colsName);
                while(rs1.next()){
                    String value[] = new String[5];
                    value[0]=rs1.getString("brand_id");
                    value[1]=rs1.getString("brand_name");
                    value[2]=rs1.getString("country");
                    value[3]=rs1.getInt("number_product")+"";
                    value[4]=rs1.getDouble("total")+"";
                    tbn.addRow(value);
                    jTable1.setModel(tbn);       
                }  
            }
            
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        
    }//GEN-LAST:event_BtnBanchamActionPerformed

    private void BoxTKBrand_ChayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxTKBrand_ChayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BoxTKBrand_ChayActionPerformed

    private void BoxTKBrand_ChamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxTKBrand_ChamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BoxTKBrand_ChamActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> BoxTKBrand_Cham;
    private javax.swing.JComboBox<String> BoxTKBrand_Chay;
    private javax.swing.JButton BtnBancham;
    private javax.swing.JButton BtnBanchay;
    private com.toedter.calendar.JDateChooser datefrom;
    private com.toedter.calendar.JDateChooser dateto;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    // End of variables declaration//GEN-END:variables
}
