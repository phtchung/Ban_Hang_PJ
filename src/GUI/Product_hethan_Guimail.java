package GUI;


 
import java.awt.Window;
import javax.mail.PasswordAuthentication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.security.auth.kerberos.ServicePermission;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class Product_hethan_Guimail extends javax.swing.JPanel {
    DefaultTableModel tbn = new DefaultTableModel();
    
    public Product_hethan_Guimail() {
        initComponents();
        loadData();
         txt_discount.setEnabled(false);
    }
     public void sendmail(String mail) {
        try {
            Email email = new SimpleEmail();

            // Cấu hình thông tin Email Server
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("hellomoinguinhe@gmail.com", "hello135792468")); //Nhớ nhập đúng với tài khoản thật nhé :))

            // Với gmail cái này là bắt buộc.
            email.setSSLOnConnect(true);

            // Người gửi
            email.setFrom("hellomoinguinhe@gmail.com", Login.Store_Name);

            // Tiêu đề
            email.setSubject("Các mặt hàng giảm giá"); //Tiêu đề khi gửi email

            // Nội dung email
            //String covert = String.valueOf(rand);
            email.setMsg("Xin chào quý khách, tại " + Login.Store_Name + " có một số mặt hàng giảm giá sau. Nếu có thời gian, quý khách hãy đến cửa hàng nhé. \\n"); //Nội dung email bạn muốn gửi.
            // Người nhận
            email.addTo(mail); //Đia chỉ email người nhận
            email.send(); //Thực hiện gửi.
            System.err.println("Gửi email thành công ! Vui lòng kiểm tra email !");
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println("Gửi không thành công !");
            System.out.println("");
        }
    }
     
     
      public void loadData() {
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row,column;
            column = new Vector();
            Statement st =conn.createStatement();
            String sql_hetdate = "Select SG.product_id as N'Mã nhân viên',PPRO.product_name as N'Tên sản phẩm' ,PPRO.price as N'Giá sản phẩm', "
                    + "SG.good_till as N'Hạn sử dụng',SG.discount as N'Giảm giá (%)' from sales.goods as SG inner join production.products as PPRO "
                    + "on SG.product_id = PPRO.product_id where( DATEDIFF(day,getdate(),SG.good_till) between 0 and 30)";
            ResultSet rs =st.executeQuery(sql_hetdate);
            ResultSetMetaData metadata =rs.getMetaData();
            number = metadata.getColumnCount();
            
            for(int i=1;i<=number;i++){
                column.add(metadata.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);
            while(rs.next()){
                row = new Vector();
                for(int i=1;i<=number;i++){    
                    row.addElement(rs.getString(i));      
                }
                tbn.addRow(row);
                jTable1.setModel(tbn);  
            } 
            jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
             //nap du lieu tu jtable len textfield
               public void valueChanged (ListSelectionEvent e){
                   if(jTable1.getSelectedRow()>= 0){
                       txtproduct_id.setText(jTable1.getValueAt(jTable1.getSelectedRow(),0)+ "");
                       txtproduct_name.setText(jTable1.getValueAt(jTable1.getSelectedRow(),1)+ "");
                       txtprice.setText(jTable1.getValueAt(jTable1.getSelectedRow(),2)+ "");
                       txt_date.setText(jTable1.getValueAt(jTable1.getSelectedRow(),3)+ "");
                       txt_discount.setText(jTable1.getValueAt(jTable1.getSelectedRow(),4)+ "");                 
                       
                   } 
                }
           });
           
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnsend = new javax.swing.JButton();
        txt_discount = new javax.swing.JTextField();
        btn_suadiscout = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtproduct_id = new javax.swing.JLabel();
        txtproduct_name = new javax.swing.JLabel();
        txtprice = new javax.swing.JLabel();
        txt_date = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bgtksp.png"))); // NOI18N

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        setLayout(null);

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(420, 460, 1060, 350);

        btnsend.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnsend.setContentAreaFilled(false);
        btnsend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsendActionPerformed(evt);
            }
        });
        add(btnsend);
        btnsend.setBounds(1100, 350, 150, 70);

        txt_discount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_discountActionPerformed(evt);
            }
        });
        add(txt_discount);
        txt_discount.setBounds(600, 362, 180, 30);

        btn_suadiscout.setContentAreaFilled(false);
        btn_suadiscout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suadiscoutActionPerformed(evt);
            }
        });
        add(btn_suadiscout);
        btn_suadiscout.setBounds(810, 350, 150, 70);

        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(0, 680, 370, 130);
        add(txtproduct_id);
        txtproduct_id.setBounds(610, 170, 170, 30);
        add(txtproduct_name);
        txtproduct_name.setBounds(1070, 150, 170, 30);
        add(txtprice);
        txtprice.setBounds(600, 260, 180, 30);
        add(txt_date);
        txt_date.setBounds(1070, 250, 170, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thống kê sp sắp hết hạn.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        add(jLabel1);
        jLabel1.setBounds(0, 0, 1540, 820);
    }// </editor-fold>//GEN-END:initComponents

    
 
    private void btnsendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsendActionPerformed
        // TODO add your handling code here:
     try{
        Connect a = new Connect();
        Connection conn =a.getConnectDB();
        String sql_mail = "Select email from vRealCustomer";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql_mail);
        while ( rs.next()){         
                sendmail(rs.getString("email"));   
        }
     }catch( Exception ex){
         System.out.println(ex.toString());
     }
                            
    }//GEN-LAST:event_btnsendActionPerformed

    private void txt_discountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_discountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_discountActionPerformed

    private void btn_suadiscoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suadiscoutActionPerformed
        // TODO add your handling code here:
        try{
            Connect a = new Connect();
            Connection conn =a.getConnectDB(); 
            PreparedStatement comm =conn.prepareStatement(" Update sales.goods set discount=? where product_id=?");
            
            comm.setString(2,txtproduct_id.getText());
            comm.setString(1, txt_discount.getText());
            comm.executeUpdate();
            tbn.setRowCount(0);
            loadData();
            JOptionPane.showMessageDialog(this, "Sửa thành công ");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        
    }//GEN-LAST:event_btn_suadiscoutActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        txt_discount.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_suadiscout;
    private javax.swing.JButton btnsend;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JLabel txt_date;
    private javax.swing.JTextField txt_discount;
    private javax.swing.JLabel txtprice;
    private javax.swing.JLabel txtproduct_id;
    private javax.swing.JLabel txtproduct_name;
    // End of variables declaration//GEN-END:variables
}
