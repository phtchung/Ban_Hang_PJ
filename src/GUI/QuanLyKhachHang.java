/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI; 

import Utils.ValidateData;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;

/**
 *
 * @author ADMIN
 */
public class QuanLyKhachHang extends javax.swing.JPanel {
    DefaultTableModel tbn = new DefaultTableModel();
    Date date = new Date();
    ValidateData vd = new ValidateData();
    
    /**
     * Creates new form QuanLyKhachHang
     */
    public QuanLyKhachHang() {
        initComponents();
        loadData();
        jDateChooser1.setDate(new Date());
    }

    public void loadData(){
        try{
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            Vector row, column;
            column = new Vector();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from vRealCustomer;");
            
            for(int i = 0; i < jTable1.getColumnCount(); i++){
                column.add(jTable1.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTable1.getColumnCount(); i++){
                    row.addElement(rs.getString(i));
                }
                tbn.addRow(row);
                jTable1.setModel(tbn);
            }

            //jTable1.setFont(new Font("Serif", Font.BOLD, 20));
            // set the column width for each column table.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
            
            jTable1.setRowHeight(30);
            


            jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(jTable1.getSelectedRow() >= 0) {
                        txtID.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "");
                        txtName.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1) + "");
                        String sDate1 = jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString();  
                        Date date1 = null; 
                        try {
                            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                        } catch (ParseException ex) {
                            Logger.getLogger(QuanLyKhachHang.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jDateChooser1.setDate(date1);
                        txtAddress.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 3) + "");
                        txtPhone.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 4) + "");
                        txtEmail.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 5) + "");
                    }
                }
            });
            
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    private boolean isDeleteAble(int ID) {
        ArrayList<Integer> Slots = new ArrayList<Integer>();
        try{
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select sales.customers.customer_id from sales.customers "
                    + "left join sales.orders on sales.orders.customer_id = sales.customers.customer_id "
                    + "where order_id is null;");
            if(rs.next() == false) return false;
            else {
                do { 
                    Slots.add(rs.getInt(1));
                }while(rs.next());
                for(int i = 0; i < Slots.size(); i++){
                    if(ID == Slots.get(i)){
                        return true;
                    }
                }
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return false;
    }
    
    private boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    
    private boolean isValidPhone(String phone)
    {
        String phoneRegex = "^[0-9]{10}$";
                              
        Pattern pat = Pattern.compile(phoneRegex);
        if (phone == null)
            return false;
        return pat.matcher(phone).matches();
    }
    
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtName = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton4 = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jComboBoxSearch = new javax.swing.JComboBox<>();
        txtID = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setLayout(null);

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên", "Ngày Sinh", "Địa Chỉ", "SĐT", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(350, 510, 1140, 270);

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txtName);
        txtName.setBounds(490, 170, 350, 40);

        txtAddress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txtAddress);
        txtAddress.setBounds(490, 272, 350, 40);

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txtPhone);
        txtPhone.setBounds(490, 320, 350, 40);

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txtEmail);
        txtEmail.setBounds(490, 362, 350, 40);

        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(940, 180, 200, 90);

        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(1180, 160, 210, 110);

        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(930, 300, 200, 100);

        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(jDateChooser1);
        jDateChooser1.setBounds(490, 220, 350, 40);

        jButton4.setBackground(new java.awt.Color(51, 0, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Tìm kiếm");
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(950, 430, 100, 60);

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        add(txtSearch);
        txtSearch.setBounds(490, 440, 410, 43);

        jComboBoxSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Tên", "Ngày Sinh", "Địa Chỉ", "SĐT", "Email", " " }));
        add(jComboBoxSearch);
        jComboBoxSearch.setBounds(350, 440, 110, 40);

        txtID.setEditable(false);
        txtID.setBackground(new java.awt.Color(204, 204, 204));
        txtID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txtID);
        txtID.setBounds(490, 122, 350, 40);

        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(1190, 300, 210, 100);

        jButton6.setContentAreaFilled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(0, 680, 290, 130);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Frame Quản lý khách hàng.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        add(jLabel8);
        jLabel8.setBounds(0, 0, 1577, 814);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if(txtName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Thiếu tên.");
                return;
            } else if (txtAddress.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Thiếu địa chỉ.");
                return;
            } else if (txtPhone.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Thiếu SĐT.");
                return;
            } else if (txtEmail.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Thiếu Email.");
                return;
            } else if (date.getYear() - jDateChooser1.getDate().getYear() < 10 ) {
                JOptionPane.showMessageDialog(this, "Không đủ tuổi.");
                return;
            } else if (!isValidEmail(txtEmail.getText())) {
                JOptionPane.showMessageDialog(this, "Sai cú pháp Email.");
                return;
            } else if (!isValidPhone(txtPhone.getText())) {
                JOptionPane.showMessageDialog(this, "Sai cú pháp SĐT.");
                return;
            } else if (vd.containsDigit(txtName.getText())) {
                JOptionPane.showMessageDialog(this, "Sai cú pháp Tên.");
                return;
            }
            
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            
            int seed;
            if(SlotToInsert() >= 0){
                seed = SlotToInsert();
                System.out.println("Seed: " + seed);
                PreparedStatement ps1 = con.prepareStatement("DBCC CHECKIDENT ('sales.customers', RESEED, ?);");
                ps1.setInt(1, seed);
                ps1.execute();
            }
            
            PreparedStatement ps = con.prepareStatement("insert into sales.customers values (?, ?, ?, ?, ?)");
            ps.setString(1, vd.ChuanHoaChuoi(txtName.getText()));
            ps.setObject(2, jDateChooser1.getDate());
            ps.setString(3, vd.ChuanHoaChuoi(txtAddress.getText()));
            ps.setString(4, txtPhone.getText());
            ps.setString(5, txtEmail.getText());
            
            int check = ps.executeUpdate();
            if(check > 0) {
                Reset();
                JOptionPane.showMessageDialog(this, "Thêm thành công.");
                tbn.setRowCount(0);
                loadData();
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            if(ex.toString().contains("UQ1")) {
                JOptionPane.showMessageDialog(this, "Email này đã tồn tại trong hệ thống.");
            } else if (ex.toString().contains("UQ0")) {
                JOptionPane.showMessageDialog(this, "SĐT này đã tồn tại trong hệ thống.");
            } else if (ex.toString().contains("PK")) {
                JOptionPane.showMessageDialog(this, "Người này đã tồn tại trong hệ thống.");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try {
            if(txtName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Thiếu tên.");
                return;
            } else if (txtAddress.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Thiếu địa chỉ.");
                return;
            } else if (txtPhone.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Thiếu SĐT.");
                return;
            } else if (txtEmail.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Thiếu Email.");
                return;
            } else if (date.getYear() - jDateChooser1.getDate().getYear() < 10 ) {
                JOptionPane.showMessageDialog(this, "Không đủ tuổi.");
                return;
            } else if (!isValidEmail(txtEmail.getText())) {
                JOptionPane.showMessageDialog(this, "Sai cú pháp Email.");
                return;
            } else if (!isValidPhone(txtPhone.getText())) {
                JOptionPane.showMessageDialog(this, "Sai cú pháp SĐT.");
                return;
            } else if (vd.containsDigit(txtName.getText())) {
                JOptionPane.showMessageDialog(this, "Sai cú pháp Tên.");
                return;
            }
            
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            PreparedStatement ps = con.prepareStatement(
                    "update sales.customers set name = ?, "
                            + "birthday = ?, "
                            + "address = ?, "
                            + "phone = ?, "
                            + "email = ? "
                            + "where customer_id = ?");
            ps.setString(1, vd.ChuanHoaChuoi(txtName.getText()));
            ps.setObject(2, jDateChooser1.getDate());
            ps.setString(3, vd.ChuanHoaChuoi(txtAddress.getText()));
            ps.setString(4, txtPhone.getText());
            ps.setString(5, txtEmail.getText());
            ps.setString(6, jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "");
            
            int check = ps.executeUpdate();
            if(check > 0) {
                Reset();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công.");
                tbn.setRowCount(0);
                loadData();
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            if(ex.toString().contains("UQ1")) {
                JOptionPane.showMessageDialog(this, "Email này đã tồn tại trong hệ thống.");
            } else if (ex.toString().contains("UQ0")) {
                JOptionPane.showMessageDialog(this, "SĐT này đã tồn tại trong hệ thống.");
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(txtName.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Thiếu tên.");
            return;
        } else if (txtAddress.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Thiếu địa chỉ.");
            return;
        } else if (txtPhone.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Thiếu SĐT.");
            return;
        } else if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Thiếu Email.");
            return;
        }
        
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            
            if(isDeleteAble(Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0) + ""))) {
                PreparedStatement ps = con.prepareStatement("delete from sales.customers where customer_id = ?");
                ps.setString(1, jTable1.getValueAt(jTable1.getSelectedRow(), 0) + "");

                int check = ps.executeUpdate();
                if(check > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công.");
                    Reset();
                    tbn.setRowCount(0);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại.");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa.");
                return;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            String str = "Select * from vRealCustomer where ";
            PreparedStatement ps = null;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;
            if(txtSearch.getText().equals("")) {
                tbn.setRowCount(0);
                loadData();
                return;
            } else if(jComboBoxSearch.getSelectedItem().toString().equals("ID")) {
                ps = con.prepareStatement(str + "customer_id like ?");
                ps.setString(1, txtSearch.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Tên")) {
                ps = con.prepareStatement(str + "name like ?");
                ps.setString(1, txtSearch.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Địa Chỉ")) {
                ps = con.prepareStatement(str + "address like ?");
                ps.setString(1, txtSearch.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("SĐT")) {
                ps = con.prepareStatement(str + "phone like ?");
                ps.setString(1, txtSearch.getText());
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Email")) {
                ps = con.prepareStatement(str + "email like ?");
                ps.setString(1, txtSearch.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Ngày Sinh")) {
                ps = con.prepareStatement(str + "birthday = ?");
                ps.setString(1, txtSearch.getText());
                rs = ps.executeQuery();
            } 
            
            tbn.setRowCount(0);
            
            for(int i = 0; i < jTable1.getColumnCount(); i++){
                column.add(jTable1.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTable1.getColumnCount(); i++){
                    row.addElement(rs.getString(i));
                }
                tbn.addRow(row);
                jTable1.setModel(tbn);
            }
            
            if(tbn.getRowCount() == 0) JOptionPane.showMessageDialog(this, "Không tìm thấy.");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }//GEN-LAST:event_jButton4ActionPerformed
    public void Reset(){
        tbn.setRowCount(0);
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSearch.setText("");
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
        jButton5.setEnabled(true);
        loadData();
        jDateChooser1.setDate(new Date());
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       Reset();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            String str = "Select * from vRealCustomer where ";
            PreparedStatement ps = null;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;
            if(jComboBoxSearch.getSelectedItem().toString().equals("ID")) {
                ps = con.prepareStatement(str + "customer_id like ?");
                str1 = "%" + txtSearch.getText() + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Tên")) {
                ps = con.prepareStatement(str + "name like ?");
                str1 = "%" + txtSearch.getText() + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Địa Chỉ")) {
                ps = con.prepareStatement(str + "address like ?");
                str1 = "%" + txtSearch.getText() + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("SĐT")) {
                ps = con.prepareStatement(str + "phone like ?");
                str1 = "%" + txtSearch.getText() + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Email")) {
                ps = con.prepareStatement(str + "email like ?");
                str1 = "%" + txtSearch.getText() + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Ngày Sinh")) {
                ps = con.prepareStatement(str + "birthday = ?");
                ps.setString(1, txtSearch.getText());
                rs = ps.executeQuery();
            } 
            
            tbn.setRowCount(0);
            
            for(int i = 0; i < jTable1.getColumnCount(); i++){
                column.add(jTable1.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTable1.getColumnCount(); i++){
                    row.addElement(rs.getString(i));
                }
                tbn.addRow(row);
                jTable1.setModel(tbn);
            }
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
//        ManagerMain main = new ManagerMain();
//        main.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        jButton1.setEnabled(false);
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBoxSearch;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
