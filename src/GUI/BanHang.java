/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI; 

import Utils.ValidateData;
import java.awt.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class BanHang extends javax.swing.JPanel {
    DefaultTableModel tbnCustomer = new DefaultTableModel();
    DefaultTableModel tbnProduct = new DefaultTableModel();
    DefaultTableModel tbnBill = new DefaultTableModel();
    String customerID = null;
    String customerPhone = null;
    String customerEmail = null;
    String billID = null;
    double totalBillPrice = 0;
    static int store = Integer.parseInt(Login.Store_ID);
    static int staff = Integer.parseInt(Login.Staff_ID); 
    ValidateData vd = new ValidateData();
    NumberFormat formatter = new DecimalFormat("#0.00");
    /**
     * Creates new form BanHang
     */
    public BanHang() {
        initComponents();
        loadDataCustomer();
        loadDataProduct();
        loadDataStaff();
        loadDataBill();
        jButton11.setEnabled(false);
        jButton5.setEnabled(false);
        jButton8.setEnabled(false);
        if(jTableBill.getRowCount() == 0) {
            jButton7.setEnabled(false);
            jButton9.setEnabled(false);
            jButton6.setEnabled(false);
        }
    }
    
    private void loadDataStaff() {
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            PreparedStatement ps = con.prepareStatement("Select name from sales.staffs where staff_id = ?;");
            ps.setString(1, String.valueOf(staff));
            ResultSet rs = ps.executeQuery();
            rs.next();
            txtStaff.setText(rs.getString(1));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    private void loadDataCustomer(){
        try{
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            Vector row, column;
            column = new Vector();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from vRealCustomer");
            
            for(int i = 0; i < jTableCustomer.getColumnCount(); i++){
                column.add(jTableCustomer.getColumnName(i));
            }
            tbnCustomer.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTableCustomer.getColumnCount(); i++){
                    row.addElement(rs.getString(i));
                }
                tbnCustomer.addRow(row);
                jTableCustomer.setModel(tbnCustomer);
            }
            
            jTableCustomer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (jTableCustomer.getSelectedRow() >= 0) {
                        jButton11.setEnabled(true);
                        txtCustomer.setText(jTableCustomer.getValueAt(jTableCustomer.getSelectedRow(), 1) + "");
                    }
                }
            }
            );
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    private void loadDataProduct(){
        try{
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            Vector row, column;
            column = new Vector();
            PreparedStatement ps = con.prepareStatement("select vCurrentProduct.product_id,\n" +
                "production.products.product_name,\n" +
                "vCurrentProduct.created_at,\n" +
                "vCurrentProduct.good_till,\n" +
                "production.categories.category_name,\n" +
                "production.brands.brand_name,\n" +
                "production.brands.country,\n" +
                "vCurrentProduct.quantity,\n" +
                "vCurrentProduct.price,\n" +
                "vCurrentProduct.discount from vCurrentProduct\n" +
                "inner join production.products on vCurrentProduct.product_id = production.products.product_id\n" +
                "inner join production.categories on production.categories.category_id = production.products.category_id\n" +
                "inner join production.brands on production.brands.brand_id = production.products.brand_id\n" +
                "where store_id = ?");
            ps.setString(1, String.valueOf(store));
            ResultSet rs = ps.executeQuery();
            
            for(int i = 0; i < jTableProduct.getColumnCount(); i++){
                column.add(jTableProduct.getColumnName(i));
            }
            tbnProduct.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTableProduct.getColumnCount(); i++){
                    if(i == 9) {
                        row.addElement(vd.DangTienTe(rs.getString(i)));
                    } else {
                        row.addElement(rs.getString(i));
                    }
                }
                tbnProduct.addRow(row);
                jTableProduct.setModel(tbnProduct);
            }
            
            jTableProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (jTableProduct.getSelectedRow() >= 0) {
                        jButton5.setEnabled(true);
                        txtProduct.setText(jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 1) + "");
                    }
                }
            }
            );
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    private void loadDataBill() {
        Vector column = new Vector();
        for(int i = 0; i < jTableBill.getColumnCount(); i++){
            column.add(jTableBill.getColumnName(i));
        }
        tbnBill.setColumnIdentifiers(column);
        tbnBill.setRowCount(0);
        jTableBill.setModel(tbnBill);
        jTableBill.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (jTableBill.getSelectedRow() >= 0) {
                        jButton8.setEnabled(true);
                    } else {
                        jButton8.setEnabled(false);
                    }
                }
            }
        );
        jTableBill.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if(jTableBill.getRowCount() > 0) {
                        jButton7.setEnabled(true);
                        jButton9.setEnabled(true);
                        jButton6.setEnabled(true);
                    } else {
                        jButton7.setEnabled(false);
                        jButton9.setEnabled(false);
                        jButton6.setEnabled(false);
                    }
                }
            }
        );
    }
    
    public static boolean isDouble(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
           double i = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static boolean isInt(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    private int SlotToInsert() {
        int missingSlot = 1;
        ArrayList<Integer> Slots = new ArrayList<Integer>();
        try{
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select order_id from sales.orders order by order_id");
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

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCustomer = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jComboBoxCustomerSearch = new javax.swing.JComboBox<>();
        txtSearchCustomer = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtCustomer = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableBill = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtBillID = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        txtCustomerName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        txtTotalBillPrice = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtStaff = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProduct = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jComboBoxProductSearch = new javax.swing.JComboBox<>();
        txtSearchProduct = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtProduct = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 255));
        setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 102, 102));
        jPanel2.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Khách Hàng");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(12, 13, 190, 30);

        jTableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
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
        jScrollPane1.setViewportView(jTableCustomer);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(12, 147, 696, 110);

        jButton3.setBackground(new java.awt.Color(0, 0, 255));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Tìm kiếm");
        jButton3.setToolTipText("");
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);
        jButton3.setBounds(360, 50, 90, 30);

        jComboBoxCustomerSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tên", "SĐT", "Email" }));
        jPanel2.add(jComboBoxCustomerSearch);
        jComboBoxCustomerSearch.setBounds(280, 50, 60, 30);

        txtSearchCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchCustomerKeyReleased(evt);
            }
        });
        jPanel2.add(txtSearchCustomer);
        txtSearchCustomer.setBounds(12, 50, 250, 30);

        jButton11.setBackground(new java.awt.Color(51, 51, 255));
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Xác nhận");
        jButton11.setBorderPainted(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton11);
        jButton11.setBounds(490, 50, 80, 30);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Tên khách hàng");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(10, 100, 140, 20);

        txtCustomer.setEditable(false);
        txtCustomer.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.add(txtCustomer);
        txtCustomer.setBounds(140, 100, 200, 30);

        add(jPanel2);
        jPanel2.setBounds(30, 130, 720, 270);

        jPanel3.setBackground(new java.awt.Color(255, 102, 102));
        jPanel3.setLayout(null);

        jLabel4.setBackground(new java.awt.Color(51, 51, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Hóa đơn");
        jLabel4.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel3.add(jLabel4);
        jLabel4.setBounds(10, 10, 90, 29);

        jTableBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên sản phẩm", "NSX", "HSD", "Đơn giá", "Số lượng", "Giảm giá", "Thành tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableBill);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(12, 222, 676, 260);

        jButton7.setText("Thanh toán");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7);
        jButton7.setBounds(290, 170, 100, 40);

        jButton8.setText("Xóa sản phẩm");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8);
        jButton8.setBounds(10, 170, 107, 40);

        jButton9.setText("Cập nhật đơn");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton9);
        jButton9.setBounds(440, 170, 110, 40);

        jLabel7.setText("ID");
        jPanel3.add(jLabel7);
        jLabel7.setBounds(10, 50, 110, 16);
        jPanel3.add(txtBillID);
        txtBillID.setBounds(140, 42, 231, 30);

        jButton10.setText("Tìm đơn");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton10);
        jButton10.setBounds(390, 45, 77, 30);

        txtCustomerName.setEditable(false);
        txtCustomerName.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.add(txtCustomerName);
        txtCustomerName.setBounds(140, 130, 157, 30);

        jLabel8.setText("Tên khách hàng");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 130, 85, 16);

        jButton12.setText("Xóa");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton12);
        jButton12.setBounds(310, 130, 76, 25);

        jButton6.setText("Xóa tất cả sản phẩm");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6);
        jButton6.setBounds(130, 170, 150, 40);

        txtTotalBillPrice.setEditable(false);
        txtTotalBillPrice.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.add(txtTotalBillPrice);
        txtTotalBillPrice.setBounds(540, 500, 150, 22);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Tổng tiền");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(430, 500, 90, 30);

        jLabel10.setText("Nhân viên bán hàng");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(10, 100, 107, 16);

        txtStaff.setEditable(false);
        txtStaff.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.add(txtStaff);
        txtStaff.setBounds(140, 90, 160, 30);

        jButton13.setText("Đơn mới");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton13);
        jButton13.setBounds(570, 170, 110, 40);

        add(jPanel3);
        jPanel3.setBounds(780, 140, 700, 540);

        jPanel4.setBackground(new java.awt.Color(255, 102, 102));
        jPanel4.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Sản phẩm");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(10, 10, 90, 29);

        jTableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên", "NSX", "HSD", "Loại", "Hãng", "Xuất xứ", "Số lượng", "Đơn giá", "Giảm giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableProduct);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(12, 148, 696, 161);

        jButton4.setBackground(new java.awt.Color(102, 51, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Tìm kiếm");
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4);
        jButton4.setBounds(360, 40, 110, 30);

        jComboBoxProductSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Tên", "Loại", "Hãng", "Xuất xứ" }));
        jPanel4.add(jComboBoxProductSearch);
        jComboBoxProductSearch.setBounds(250, 42, 80, 30);

        txtSearchProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchProductKeyReleased(evt);
            }
        });
        jPanel4.add(txtSearchProduct);
        txtSearchProduct.setBounds(12, 42, 220, 30);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Số lượng");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(10, 120, 90, 20);
        jPanel4.add(txtQuantity);
        txtQuantity.setBounds(130, 112, 117, 30);

        jButton5.setBackground(new java.awt.Color(51, 0, 204));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Thêm vào giỏ hàng");
        jButton5.setBorderPainted(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5);
        jButton5.setBounds(540, 90, 170, 40);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tên sản phẩm");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(12, 84, 120, 20);

        txtProduct.setEditable(false);
        txtProduct.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.add(txtProduct);
        txtProduct.setBounds(130, 80, 340, 30);

        add(jPanel4);
        jPanel4.setBounds(30, 420, 720, 260);

        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(0, 700, 250, 100);

        jButton2.setBackground(new java.awt.Color(51, 51, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton2.setToolTipText("");
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(30, 20, 170, 80);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Frame Bán hàng (1).png"))); // NOI18N
        jLabel6.setText("jLabel6");
        add(jLabel6);
        jLabel6.setBounds(0, 0, 1530, 800);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            String str = "Select * from vRealCustomer where ";
            PreparedStatement ps = null;
            int number;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;
            if(txtSearchCustomer.getText().equals("")) {
                tbnCustomer.setRowCount(0);
                loadDataCustomer();
                return;
            } else if (jComboBoxCustomerSearch.getSelectedItem().toString().equals("Tên")) {
                ps = con.prepareStatement(str + "name like ?");
                ps.setString(1, txtSearchCustomer.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxCustomerSearch.getSelectedItem().toString().equals("SĐT")) {
                ps = con.prepareStatement(str + "phone like ?");
                ps.setString(1, txtSearchCustomer.getText());
            } else if (jComboBoxCustomerSearch.getSelectedItem().toString().equals("Email")) {
                ps = con.prepareStatement(str + "email like ?");
                ps.setString(1, txtSearchCustomer.getText());
                rs = ps.executeQuery();
            }
            
            tbnCustomer.setRowCount(0);
            
            for(int i = 0; i < jTableCustomer.getColumnCount(); i++){
                column.add(jTableCustomer.getColumnName(i));
            }
            tbnCustomer.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTableCustomer.getColumnCount(); i++){
                    row.addElement(rs.getString(i));
                }
                tbnCustomer.addRow(row);
                jTableCustomer.setModel(tbnCustomer);
            }
            
            if(tbnCustomer.getRowCount() == 0) JOptionPane.showMessageDialog(this, "You searched for nothing.");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            String str = "select vCurrentProduct.product_id,\n" +
                "production.products.product_name,\n" +
                "vCurrentProduct.created_at,\n" +
                "vCurrentProduct.good_till,\n" +
                "production.categories.category_name,\n" +
                "production.brands.brand_name,\n" +
                "production.brands.country,\n" +
                "vCurrentProduct.quantity,\n" +
                "vCurrentProduct.price,\n" +
                "vCurrentProduct.discount from vCurrentProduct\n" +
                "inner join production.products on vCurrentProduct.product_id = production.products.product_id\n" +
                "inner join production.categories on production.categories.category_id = production.products.category_id\n" +
                "inner join production.brands on production.brands.brand_id = production.products.brand_id\n" +
                "where ";
            PreparedStatement ps = null;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            if(txtSearchProduct.getText().equals("")) {
                tbnProduct.setRowCount(0);
                loadDataProduct();
                return;
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("Tên")) {
                ps = con.prepareStatement(str + "store_id = ? and production.products.product_name like ?");
                ps.setString(1, String.valueOf(store));
                ps.setString(2, txtSearchProduct.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("ID")) {
                ps = con.prepareStatement(str + "store_id = ? and vCurrentProduct.product_id like ?");
                ps.setString(1, String.valueOf(store));
                ps.setString(1, txtSearchProduct.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("Loại")) {
                ps = con.prepareStatement(str + "store_id = ? and production.categories.category_name like ?");
                ps.setString(1, String.valueOf(store));
                ps.setString(1, txtSearchProduct.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("Hãng")) {
                ps = con.prepareStatement(str + "store_id = ? and production.brands.brand_name like ?");
                ps.setString(1, String.valueOf(store));
                ps.setString(1, txtSearchProduct.getText());
                rs = ps.executeQuery();
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("Xuất xứ")) {
                ps = con.prepareStatement(str + "store_id = ? and production.brands.country like ?");
                ps.setString(1, String.valueOf(store));
                ps.setString(1, txtSearchProduct.getText());
                rs = ps.executeQuery();
            }
            
            tbnProduct.setRowCount(0);
            
            for(int i = 0; i < jTableProduct.getColumnCount(); i++){
                column.add(jTableProduct.getColumnName(i));
            }
            tbnProduct.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTableProduct.getColumnCount(); i++){
                    row.addElement(rs.getString(i));
                }
                tbnProduct.addRow(row);
                jTableProduct.setModel(tbnProduct);
            }
            
            if(tbnProduct.getRowCount() == 0) JOptionPane.showMessageDialog(this, "You searched for nothing.");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
        if (Login.manager_state == 1){
            Main_QuanLy main = new Main_QuanLy();
            main.setVisible(true); 
        } else {
            Main_NhanVien main = new Main_NhanVien();
            main.setVisible(true); 
        }
               
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tbnCustomer.setRowCount(0);
        tbnProduct.setRowCount(0);
        tbnBill.setRowCount(0);
        jTableCustomer.setModel(tbnCustomer);
        jTableProduct.setModel(tbnProduct);
        jTableBill.setModel(tbnBill);
        txtBillID.setText("");
        txtCustomer.setText("");
        txtProduct.setText("");
        txtCustomerName.setText("");
        txtQuantity.setText("");
        txtSearchCustomer.setText("");
        txtSearchProduct.setText("");
        txtTotalBillPrice.setText("");
        loadDataCustomer();
        loadDataProduct();
        loadDataBill();
        loadDataStaff();
        totalBillPrice = 0;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtSearchCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCustomerKeyReleased
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            String str = "Select * from vRealCustomer where ";
            PreparedStatement ps = null;
            int number;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;
            if (jComboBoxCustomerSearch.getSelectedItem().toString().equals("Tên")) {
                ps = con.prepareStatement(str + "name like ?");
                str1 = "%" + txtSearchCustomer.getText() + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxCustomerSearch.getSelectedItem().toString().equals("SĐT")) {
                ps = con.prepareStatement(str + "phone like ?");
                str1 = "%" + txtSearchCustomer.getText() + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxCustomerSearch.getSelectedItem().toString().equals("Email")) {
                ps = con.prepareStatement(str + "email like ?");
                str1 = "%" + txtSearchCustomer.getText() + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } 
            
            tbnCustomer.setRowCount(0);
            
            for(int i = 0; i < jTableCustomer.getColumnCount(); i++){
                column.add(jTableCustomer.getColumnName(i));
            }
            tbnCustomer.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTableCustomer.getColumnCount(); i++){
                    row.addElement(rs.getString(i));
                }
                tbnCustomer.addRow(row);
                jTableCustomer.setModel(tbnCustomer);
            }
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_txtSearchCustomerKeyReleased

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        txtCustomerName.setText(jTableCustomer.getValueAt(jTableCustomer.getSelectedRow(), 1) + "");
        customerID = jTableCustomer.getValueAt(jTableCustomer.getSelectedRow(), 0) + "";
        customerPhone = jTableCustomer.getValueAt(jTableCustomer.getSelectedRow(), 4) + "";
        customerEmail = jTableCustomer.getValueAt(jTableCustomer.getSelectedRow(), 5) + "";
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        txtCustomerName.setText("");
        customerID = "";
        customerPhone = "";
        customerEmail = "";
    }//GEN-LAST:event_jButton12ActionPerformed

    private void txtSearchProductKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProductKeyReleased
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            String str = "select vCurrentProduct.product_id, "
                    + "production.products.product_name, "
                    + "vCurrentProduct.created_at, "
                    + "vCurrentProduct.good_till,"
                    + "production.categories.category_name, "
                    + "production.brands.brand_name, "
                    + "production.brands.country, "
                    + "vCurrentProduct.quantity, "
                    + "vCurrentProduct.price, "
                    + "vCurrentProduct.discount from vCurrentProduct\n" +
                "inner join production.products on vCurrentProduct.product_id = production.products.product_id\n" +
                "inner join production.categories on production.categories.category_id = production.products.category_id\n" +
                "inner join production.brands on production.brands.brand_id = production.products.brand_id\n"
                    + "where ";
            PreparedStatement ps = null;
            int number;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;
            if (jComboBoxProductSearch.getSelectedItem().toString().equals("Tên")) {
                ps = con.prepareStatement(str + "store_id = ? and production.products.product_name like ?");
                ps.setString(1, String.valueOf(store));
                str1 = "%" + txtSearchProduct.getText() + "%";
                ps.setString(2, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("ID")) {
                ps = con.prepareStatement(str + "store_id = ? and vCurrentProduct.product_id like ?");
                ps.setString(1, String.valueOf(store));
                str1 = "%" + txtSearchProduct.getText() + "%";
                ps.setString(2, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("Loại")) {
                ps = con.prepareStatement(str + "store_id = ? and production.categories.category_name like ?");
                ps.setString(1, String.valueOf(store));
                str1 = "%" + txtSearchProduct.getText() + "%";
                ps.setString(2, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("Hãng")) {
                ps = con.prepareStatement(str + "store_id = ? and production.brands.brand_name like ?");
                ps.setString(1, String.valueOf(store));
                str1 = "%" + txtSearchProduct.getText() + "%";
                ps.setString(2, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxProductSearch.getSelectedItem().toString().equals("Xuất xứ")) {
                ps = con.prepareStatement(str + "store_id = ? and production.brands.country like ?");
                ps.setString(1, String.valueOf(store));
                str1 = "%" + txtSearchProduct.getText() + "%";
                ps.setString(2, str1);
                rs = ps.executeQuery();
            }
            
            tbnProduct.setRowCount(0);
            
            for(int i = 0; i < jTableProduct.getColumnCount(); i++){
                column.add(jTableProduct.getColumnName(i));
            }
            tbnProduct.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= jTableProduct.getColumnCount(); i++){
                    row.addElement(rs.getString(i));
                }
                tbnProduct.addRow(row);
                jTableProduct.setModel(tbnProduct);
            }
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_txtSearchProductKeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(!isInt(txtQuantity.getText()) || Integer.parseInt(txtQuantity.getText()) <= 0) {
            JOptionPane.showMessageDialog(this, "Thiếu thông tin Số lượng.");
            return;
        }
        
        int countInStocks = 0;
        int buying = 0;
        buying += Integer.parseInt(txtQuantity.getText());
        int rowToDelete = -1;
        
        for(int i = 0; i < jTableBill.getRowCount(); i++) {
            if(jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 1).equals(jTableBill.getValueAt(i, 1)) &&
                    jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 2).equals(jTableBill.getValueAt(i, 2)) &&
                    jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 3).equals(jTableBill.getValueAt(i, 3))) {
                buying += Integer.parseInt(jTableBill.getValueAt(i, 5) + "");
                rowToDelete = i;
                break;
            }
        }
        
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            PreparedStatement ps = con.prepareStatement("select quantity from vCurrentProduct "
                    + "where product_id = ? and "
                    + "created_at = ? and "
                    + "good_till = ? and "
                    + "store_id = ?;");
            ps.setString(1, jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 0) + "");
            ps.setString(2, jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 2) + "");
            ps.setString(3, jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 3) + "");
            ps.setString(4, String.valueOf(store));
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            countInStocks = rs.getInt(1);
            if(countInStocks < buying) {
                String str = "Hàng không đủ trong kho.";
                
                PreparedStatement ps1 = con.prepareStatement("select sales.stores.name, sum(quantity) from vCurrentProduct "
                        + "inner join sales.stores on sales.stores.store_id = vCurrentProduct.store_id "
                        + "where product_id = ? and "
                        + "vCurrentProduct.store_id != ? "
                        + "and sales.stores.state = 'Open' "
                        + "group by sales.stores.store_id, sales.stores.name;");
                ps1.setString(1, jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 0) + "");
                ps1.setString(2, String.valueOf(store));
                ResultSet rs1 = ps1.executeQuery();
                
                if(rs1.next() == false) {
                    str = str + "\nKhông kho nào còn hàng.";
                } else {
                    str = str + "\nCòn hàng tại:\n";
                    do {
                        str = str + rs1.getString(1) + ": " + rs1.getString(2) + "\n";
                    } while(rs1.next());
                }
                
                JOptionPane.showMessageDialog(this, str);
                return;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        String productName = jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 1) + "";
        double singlePrice = Double.parseDouble(vd.ReverseDangTienTe(jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 8) + ""));
        double productDiscount = Double.parseDouble(jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 9) + "");
        double totalSinglePrice = singlePrice * (1.00 - productDiscount/100.00) * (double)buying;
               
        Vector row = new Vector();
        Vector column = new Vector();
        for(int j = 0; j < jTableBill.getColumnCount(); j++){
            column.add(jTableBill.getColumnName(j));
        }
        tbnBill.setColumnIdentifiers(column);
        if(rowToDelete > -1) tbnBill.removeRow(rowToDelete);
        row.add(jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 0) + "");
        row.add(productName);
        row.add(jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 2) + "");
        row.add(jTableProduct.getValueAt(jTableProduct.getSelectedRow(), 3) + "");
        row.add(vd.DangTienTe(formatter.format(singlePrice)));
        row.add(String.valueOf(buying));
        row.add(String.valueOf(productDiscount));
        row.add(vd.DangTienTe(formatter.format(totalSinglePrice)));
        row.add("Mới");
        tbnBill.addRow(row);
        jTableBill.setModel(tbnBill);
        
        totalBillPrice += totalSinglePrice;
        txtTotalBillPrice.setText(vd.DangTienTe(formatter.format(totalBillPrice)));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        double removedItem = Double.parseDouble(vd.ReverseDangTienTe(jTableBill.getValueAt(jTableBill.getSelectedRow(), 7) + ""));
        totalBillPrice -= removedItem;
        txtTotalBillPrice.setText(vd.DangTienTe(String.valueOf(totalBillPrice)));
        
        tbnBill.removeRow(jTableBill.getSelectedRow());
        jTableBill.setModel(tbnBill);
        if(jTableBill.getRowCount() == 0) {
           txtTotalBillPrice.setText("");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        totalBillPrice = 0;
        tbnBill.setRowCount(0);
        jTableBill.setModel(tbnBill);
        txtTotalBillPrice.setText("");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int result1 = JOptionPane.showConfirmDialog(this ,"Xác nhận để tiếp tục.", "Làm ơn xác nhận",
           JOptionPane.YES_NO_OPTION,
           JOptionPane.QUESTION_MESSAGE);
        if(result1 == JOptionPane.YES_OPTION){
            int seed = 0;
            if(txtCustomerName.getText().equals("")){
                int result = JOptionPane.showConfirmDialog(this ,"Chưa rõ khách hàng.\nBạn có muốn tiếp tục?", "Làm ơn xác nhận",
                   JOptionPane.YES_NO_OPTION,
                   JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                   customerID = "1";
                }else if (result == JOptionPane.NO_OPTION){
                   return;
                }else {
                   return;
                }
            }
            
            String mess = JOptionPane.showInputDialog(this, "Tiền khách đưa:");
            
            if(!isDouble(mess)) {
                JOptionPane.showMessageDialog(this, "Không phải số, vui lòng bấm Thanh toán lại.");
                return;
            } else if (Double.parseDouble(mess) < Double.parseDouble(vd.ReverseDangTienTe(txtTotalBillPrice.getText()))) {
                JOptionPane.showMessageDialog(this, "Số tiền này không đủ, vui lòng bấm Thanh toán lại.");
                return;
            } else {
                double change = Double.parseDouble(mess) - Double.parseDouble(vd.ReverseDangTienTe(txtTotalBillPrice.getText()));
                String strChange = String.valueOf(change);
                JOptionPane.showMessageDialog(this, "Tiền thừa: " + vd.DangTienTe(strChange) + "\nThanh toán thành công.");
            }

            try {
                Connect a = new Connect();
                Connection con = a.getConnectDB();

                if(SlotToInsert() >= 0){
                    seed = SlotToInsert();
                    System.out.println("Seed: " + seed);
                    PreparedStatement ps1 = con.prepareStatement("DBCC CHECKIDENT ('sales.orders', RESEED, ?);");
                    ps1.setInt(1, seed);
                    ps1.execute();
                }

                if(!txtCustomerName.getText().equals("")) {
                    PreparedStatement ps = con.prepareStatement("select customer_id from vRealCustomer "
                            + "where "
                            + "phone like ? and "
                            + "email like ? ;");
                    ps.setString(1, customerPhone);
                    ps.setString(2, customerEmail);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    customerID = rs.getString(1);
                }

                PreparedStatement ps1 = con.prepareStatement("insert into sales.orders values (?, ?, ?);");
                ps1.setString(1, customerID);
                ps1.setString(2, String.valueOf(staff));
                ps1.setObject(3, new Date());
                int update = ps1.executeUpdate();

                int update1 = 0, update2  = 0;
                PreparedStatement ps2 = con.prepareStatement("insert into sales.order_items values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
                for(int i=0; i < jTableBill.getRowCount(); i++) {
                    ps2.setString(1, String.valueOf(seed + 1));
                    ps2.setString(2, jTableBill.getValueAt(i, 0) + "");
                    ps2.setString(3, jTableBill.getValueAt(i, 2) + "");
                    ps2.setString(4, jTableBill.getValueAt(i, 3) + "");
                    ps2.setString(5, String.valueOf(store));
                    ps2.setString(6, jTableBill.getValueAt(i, 5) + "");
                    ps2.setString(7, vd.ReverseDangTienTe(jTableBill.getValueAt(i, 7) + ""));
                    ps2.setString(8, jTableBill.getValueAt(i, 6) + "");
                    
                    double profit = Profit(jTableBill.getValueAt(i, 0) + "", 
                            Double.parseDouble(vd.ReverseDangTienTe(jTableBill.getValueAt(i, 7) + "")), 
                            Double.parseDouble(jTableBill.getValueAt(i, 5) + ""));
                    ps2.setString(9, String.valueOf(profit));
                    
                    update1 = ps2.executeUpdate();


                    PreparedStatement ps3 = con.prepareStatement("update vCurrentProduct set quantity = quantity - ? "
                            + "where product_id = ? "
                            + "and created_at = ? "
                            + "and good_till = ? "
                            + "and store_id = ?;");
                    ps3.setInt(1, Integer.parseInt(jTableBill.getValueAt(i, 5) + ""));
                    ps3.setString(2, jTableBill.getValueAt(i, 0) + "");
                    ps3.setString(3, jTableBill.getValueAt(i, 2) + "");
                    ps3.setString(4, jTableBill.getValueAt(i, 3) + "");
                    ps3.setString(5, String.valueOf(store));
                    update2 = ps3.executeUpdate();
                }
                tbnProduct.setRowCount(0);
                jTableProduct.setModel(tbnProduct);
                loadDataProduct();

                if(update > 0 && update1 > 0 && update2 > 0) {
                    JOptionPane.showMessageDialog(this, "Chốt đơn thành công");
                    totalBillPrice = 0;
                    tbnBill.setRowCount(0);
                    jTableBill.setModel(tbnBill);
                    txtTotalBillPrice.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Chốt đơn không thành công");
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }else if (result1 == JOptionPane.NO_OPTION){
           return;
        }else {
           return;
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if(txtBillID.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không có từ khóa.");
            return;
        } else {
            tbnBill.setRowCount(0);
            jTableBill.setModel(tbnBill);
            jButton9.setEnabled(true);
            try {
                Connect a = new Connect();
                Connection con = a.getConnectDB();
                PreparedStatement ps = con.prepareStatement("select distinct "
                        + "sales.order_items.product_id, "
                        + "production.products.product_name, "
                        + "sales.order_items.created_at, "
                        + "sales.order_items.good_till, "
                        + "cast(sales.order_items.price / (1.00 - sales.order_items.discount/100.00) as decimal(10,2)), "
                        + "sales.order_items.quantity, "
                        + "sales.order_items.discount, "
                        + "sales.order_items.price "
                        + "from sales.orders "
                        + "inner join sales.order_items on sales.orders.order_id = sales.order_items.order_id "
                        + "inner join sales.goods on sales.order_items.product_id = sales.goods.product_id "
                            + "and sales.order_items.created_at = sales.goods.created_at "
                            + "and sales.order_items.good_till = sales.goods.good_till "
                        + "inner join production.products on production.products.product_id = sales.goods.product_id "
                        + "where sales.order_items.store_id = ? and sales.orders.order_id = ?;");
                ps.setString(1, String.valueOf(store));
                ps.setString(2, txtBillID.getText());
                ResultSet rs = ps.executeQuery();
                Vector row, column;
                column = new Vector();
                for(int i = 0; i < jTableBill.getColumnCount(); i++){
                    column.add(jTableBill.getColumnName(i));
                }
                tbnBill.setColumnIdentifiers(column);
                while(rs.next()){
                    row = new Vector();
                    for(int i = 1; i <= jTableBill.getColumnCount() - 1; i++){
                        if(i == 5 || i == 8) 
                            row.addElement(vd.DangTienTe(rs.getString(i)));
                        else
                            row.addElement(rs.getString(i));
                    }
                    row.add("Cũ");
                    tbnBill.addRow(row);
                    jTableBill.setModel(tbnBill);
                }
                if(jTableBill.getRowCount() == 0) {
                    txtTotalBillPrice.setText("");
                    txtCustomerName.setText("");
                    totalBillPrice = 0;
                    JOptionPane.showMessageDialog(this, "Không tìm thấy.");
                    return;
                }
                
                PreparedStatement ps1 = con.prepareStatement("select name from sales.orders "
                        + "inner join sales.customers on sales.orders.customer_id = sales.customers.customer_id "
                        + "where sales.orders.order_id = ?;");
                ps1.setString(1, txtBillID.getText());
                ResultSet rs1 = ps1.executeQuery();
                rs1.next();
                txtCustomerName.setText(rs1.getString(1));
                
                PreparedStatement ps2 = con.prepareStatement("select sum(price) from sales.order_items "
                        + "where order_id = ?;");
                ps2.setString(1, txtBillID.getText());
                ResultSet rs2 = ps2.executeQuery();
                rs2.next();
                txtTotalBillPrice.setText(rs2.getString(1));
                totalBillPrice = Double.parseDouble(rs2.getString(1));
                
                billID = txtBillID.getText();
                
                PreparedStatement ps3 = con.prepareStatement("select sales.staffs.name from sales.orders "
                        + "inner join sales.staffs on sales.staffs.staff_id = sales.orders.staff_id "
                        + "where order_id = ?;");
                ps3.setString(1, txtBillID.getText());
                ResultSet rs3 = ps3.executeQuery();
                rs3.next();
                txtStaff.setText(rs3.getString(1));
            } catch(Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        
        int result = JOptionPane.showConfirmDialog(this ,"Xác nhận để tiếp tục.", "Làm ơn xác nhận",
           JOptionPane.YES_NO_OPTION,
           JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
           try {
                Connect a = new Connect();
                Connection con = a.getConnectDB();
                PreparedStatement ps = con.prepareStatement("delete from sales.order_items where order_id = ?;");
                ps.setString(1, billID);
                int update = ps.executeUpdate();
                
                int update1 = 0, update2  = 0;
                PreparedStatement ps2 = con.prepareStatement("insert into sales.order_items values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
                for(int i=0; i < jTableBill.getRowCount(); i++) {
                    ps2.setString(1, billID);
                    ps2.setString(2, jTableBill.getValueAt(i, 0) + "");
                    ps2.setString(3, jTableBill.getValueAt(i, 2) + "");
                    ps2.setString(4, jTableBill.getValueAt(i, 3) + "");
                    ps2.setString(5, String.valueOf(store));
                    ps2.setString(6, jTableBill.getValueAt(i, 5) + "");
                    ps2.setString(7, vd.ReverseDangTienTe(jTableBill.getValueAt(i, 7) + ""));
                    ps2.setString(8, jTableBill.getValueAt(i, 6) + "");
                    double profit = Profit(jTableBill.getValueAt(i, 0) + "", 
                            Double.parseDouble(vd.ReverseDangTienTe(jTableBill.getValueAt(i, 7) + "")), 
                            Double.parseDouble(jTableBill.getValueAt(i, 5) + ""));
                    ps2.setString(9, String.valueOf(profit));
                    update1 = ps2.executeUpdate();

                    if(jTableBill.getValueAt(i, 8).toString().equals("Mới")) {
                        PreparedStatement ps3 = con.prepareStatement("update vCurrentProduct set quantity = quantity - ? "
                                + "where product_id = ? "
                                + "and created_at = ? "
                                + "and good_till = ? "
                                + "and store_id = ?;");
                        ps3.setInt(1, Integer.parseInt(jTableBill.getValueAt(i, 5) + ""));
                        ps3.setString(2, jTableBill.getValueAt(i, 0) + "");
                        ps3.setString(3, jTableBill.getValueAt(i, 2) + "");
                        ps3.setString(4, jTableBill.getValueAt(i, 3) + "");
                        ps3.setString(5, String.valueOf(store));
                        update2 = ps3.executeUpdate();
                    }
                }
                tbnProduct.setRowCount(0);
                jTableProduct.setModel(tbnProduct);
                loadDataProduct();
                
                if(update > 0 && update1 > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật đơn thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật đơn không thành công");
                }
           } catch (Exception ex) {
               System.out.println(ex.toString());
           }
        }else if (result == JOptionPane.NO_OPTION){
           return;
        }else {
           return;
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        tbnBill.setRowCount(0);
        jTableBill.setModel(tbnBill);
        loadDataBill();
        loadDataStaff();
        txtCustomerName.setText("");
        txtTotalBillPrice.setText("");
        txtBillID.setText("");
    }//GEN-LAST:event_jButton13ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBoxCustomerSearch;
    private javax.swing.JComboBox<String> jComboBoxProductSearch;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableBill;
    private javax.swing.JTable jTableCustomer;
    private javax.swing.JTable jTableProduct;
    private javax.swing.JTextField txtBillID;
    private javax.swing.JTextField txtCustomer;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtProduct;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearchCustomer;
    private javax.swing.JTextField txtSearchProduct;
    private javax.swing.JTextField txtStaff;
    private javax.swing.JTextField txtTotalBillPrice;
    // End of variables declaration//GEN-END:variables

    private double Profit(String product_ID, double totalSinglePrice, double quantity) {
        double primePrice = 0;
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            PreparedStatement ps = con.prepareStatement("select price from production.products where product_id = ?");
            ps.setString(1, product_ID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            primePrice = rs.getDouble(1);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return totalSinglePrice - primePrice * quantity;
    }
}
