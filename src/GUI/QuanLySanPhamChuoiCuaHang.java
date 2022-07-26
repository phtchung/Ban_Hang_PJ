/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI; 

import Utils.ValidateData;
import java.awt.Window;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLySanPhamChuoiCuaHang extends javax.swing.JPanel {

    DefaultTableModel tbn = new DefaultTableModel();
    static int store = Integer.parseInt(Login.Store_ID);
    ValidateData vd = new ValidateData();
    
    public QuanLySanPhamChuoiCuaHang() {
        initComponents();
        loadData();
        txt_id.setEnabled(false);
        btn_xoa.setEnabled(false);

    }

    public void loadData() {
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select production.products.product_id as N'Mã sản phẩm', product_name as N'Tên sản phẩm', production.products.price as N'Giá nhập', brand_name as N'Hãng', category_name as N'Loại hàng', country as N'Xuất xứ' from production.products\n"
                    + "inner join production.brands on production.products.brand_id = production.brands.brand_id\n"
                    + "inner join production.categories on production.categories.category_id = production.products.category_id\n";
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    if(i == 3){
                        row.addElement(vd.DangTienTe(rs.getString(i)));
                    } else {
                        row.addElement(rs.getString(i));
                    }
                        
                }
                tbn.addRow(row);
                tbl_sp.setModel(tbn);
            }
            
            tbl_sp.getColumnModel().getColumn(0).setPreferredWidth(10);
            tbl_sp.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbl_sp.getColumnModel().getColumn(2).setPreferredWidth(50);
            tbl_sp.getColumnModel().getColumn(3).setPreferredWidth(30);
            tbl_sp.getColumnModel().getColumn(4).setPreferredWidth(30);
            tbl_sp.getColumnModel().getColumn(5).setPreferredWidth(30);
            
            tbl_sp.setRowHeight(30);

            tbl_sp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    if (tbl_sp.getSelectedRow() >= 0) {
                        txt_id.setText(tbl_sp.getValueAt(tbl_sp.getSelectedRow(), 0) + "");
                        txt_name.setText(tbl_sp.getValueAt(tbl_sp.getSelectedRow(), 1) + "");
                        txt_brand.setText(tbl_sp.getValueAt(tbl_sp.getSelectedRow(), 3) + "");
                        txt_cat.setText(tbl_sp.getValueAt(tbl_sp.getSelectedRow(), 4) + "");
                        txt_country.setText(tbl_sp.getValueAt(tbl_sp.getSelectedRow(), 5) + "");
                        txt_price.setText(tbl_sp.getValueAt(tbl_sp.getSelectedRow(), 2) + "");
                        btn_them.setEnabled(false);
                        btn_xoa.setEnabled(true);

                    }
                }
            }
            );
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public String Lay_brand_id(String brand_name) {
        String brand_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            System.out.println(brand_name);
            String sql = "select brand_id from production.brands\n" + "where brand_name like (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, brand_name);
            ResultSet rs = ps.executeQuery();
            //ResultSet rs = st.executeQuery(sql);
            //System.out.println("Here ");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            //tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    brand_id = rs.getString(i);
                }
                //tbn.addRow(row);
                //tbl_sp.setModel(tbn);
            }
            System.out.println(brand_id);

        } catch (Exception ex) {
            System.out.println("Loi o brand " + ex.toString());
        }
        System.out.println(brand_id);
        return brand_id;
    }

    public String Lay_cat_id(String cat_name) {
        String cat_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select category_id from production.categories\n" + "where category_name like (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, cat_name);
            ResultSet rs = ps.executeQuery();

            // ResultSet rs = st.executeQuery(sql);
            //System.out.println("Hare too ");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            //tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    cat_id = rs.getString(i);
                }

                //tbn.addRow(row);
                //tbl_sp.setModel(tbn);
            }
            System.out.println(cat_id);

        } catch (Exception ex) {
            System.out.println("Loi o cat" + ex.toString());
        }
        System.out.println(cat_id);
        return cat_id;
    }

    public String Lay_product_id(String product_name) {
        String product_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select product_id from production.products\n" + "where product_name like (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_name);
            ResultSet rs = ps.executeQuery();

            // ResultSet rs = st.executeQuery(sql);
            //System.out.println("Hare too ");
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            //tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    product_id = rs.getString(i);
                }

                //tbn.addRow(row);
                //tbl_sp.setModel(tbn);
            }
            System.out.println(product_id);

        } catch (Exception ex) {
            System.out.println("Loi o product" + ex.toString());
        }
        System.out.println(product_id);
        return product_id;
    }

    public String Lay_brand_id_Tu_Product_Id(String product_id) {
        String brand_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            //System.out.println(brand_name);
            String sql = "select brand_id from production.products\n" + "where product_id like (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            ResultSet rs = ps.executeQuery();;
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            //tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    brand_id = rs.getString(i);
                }
                //tbn.addRow(row);
                //tbl_sp.setModel(tbn);
            }
            //System.out.println(brand_id);

        } catch (Exception ex) {
            System.out.println("Loi o brand " + ex.toString());
        }
        System.out.println(brand_id);
        return brand_id;
    }

    public String Lay_cat_id_Tu_Product_Id(String product_id) {
        String cat_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            //System.out.println(brand_name);
            String sql = "select brand_id from production.categories\n" + "where product_id like (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            ResultSet rs = ps.executeQuery();;
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            //tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    cat_id = rs.getString(i);
                }
                //tbn.addRow(row);
                //tbl_sp.setModel(tbn);
            }
            //System.out.println(brand_id);

        } catch (Exception ex) {
            System.out.println("Loi o brand " + ex.toString());
        }
        System.out.println(cat_id);
        return cat_id;
    }

    public boolean kiemTra_brand_con_khong(String brand_id) {
        String product_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();

            String sql = "select product_id from production.products\n" + "where brand_id like (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, brand_id);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            //tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    product_id = rs.getString(i);
                    if (product_id.length() > 0) {
                        return true;
                    }
                }
                //tbn.addRow(row);
                //tbl_sp.setModel(tbn);
            }
            System.out.println(product_id);

        } catch (Exception ex) {
            System.out.println("Loi o brand khi sua " + ex.toString());
        }
        return false;
    }

    public boolean kiemTra_cat_con_khong(String cat_id) {
        String product_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            System.out.println(cat_id);
            String sql = "select product_id from production.products\n" + "where category_id like (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, cat_id);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            //tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    product_id = rs.getString(i);
                    if (product_id.length() > 0) {
                        return true;
                    }
                }
                //tbn.addRow(row);
                //tbl_sp.setModel(tbn);
            }
            System.out.println(product_id);

        } catch (Exception ex) {
            System.out.println("Loi o cat khi sua " + ex.toString());
        }
        return false;
    }

    public void reset() {
        btn_them.setEnabled(true);
        btn_xoa.setEnabled(false);
        txt_brand.setText("");
        txt_cat.setText("");
        txt_country.setText("");
        txt_id.setText("");
        txt_name.setText("");
        txt_price.setText("");
        txt_search.setText("");
        tbn.setRowCount(0);
        loadData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_sp = new javax.swing.JTable();
        txt_price = new javax.swing.JTextField();
        txt_brand = new javax.swing.JTextField();
        txt_id = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        btn_them = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        txt_cat = new javax.swing.JTextField();
        txt_country = new javax.swing.JTextField();
        btn_thoat = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        jComboBoxSearch = new javax.swing.JComboBox<>();
        txt_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(5, 5));
        setPreferredSize(new java.awt.Dimension(1920, 1030));
        setLayout(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );

        add(jPanel1);
        jPanel1.setBounds(1920, 0, 0, 352);

        jPanel2.setMinimumSize(new java.awt.Dimension(5, 5));
        jPanel2.setPreferredSize(new java.awt.Dimension(1920, 1030));

        tbl_sp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbl_sp.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_sp);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1140, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
        );

        add(jPanel2);
        jPanel2.setBounds(350, 510, 1140, 260);

        txt_price.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_priceKeyReleased(evt);
            }
        });
        add(txt_price);
        txt_price.setBounds(500, 360, 300, 40);

        txt_brand.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txt_brand);
        txt_brand.setBounds(500, 280, 300, 40);

        txt_id.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txt_id);
        txt_id.setBounds(500, 150, 300, 40);

        txt_name.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txt_name);
        txt_name.setBounds(500, 200, 300, 40);

        btn_them.setContentAreaFilled(false);
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });
        add(btn_them);
        btn_them.setBounds(930, 160, 200, 100);

        btn_sua.setContentAreaFilled(false);
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });
        add(btn_sua);
        btn_sua.setBounds(1190, 160, 200, 100);

        btn_xoa.setContentAreaFilled(false);
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });
        add(btn_xoa);
        btn_xoa.setBounds(920, 290, 210, 100);

        txt_cat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_catActionPerformed(evt);
            }
        });
        add(txt_cat);
        txt_cat.setBounds(500, 240, 300, 40);

        txt_country.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        add(txt_country);
        txt_country.setBounds(500, 320, 300, 40);

        btn_thoat.setContentAreaFilled(false);
        btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thoatActionPerformed(evt);
            }
        });
        add(btn_thoat);
        btn_thoat.setBounds(0, 680, 290, 110);

        btn_reset.setContentAreaFilled(false);
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        add(btn_reset);
        btn_reset.setBounds(1183, 290, 210, 110);

        jComboBoxSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name", "Brand", "Category", "Country" }));
        add(jComboBoxSearch);
        jComboBoxSearch.setBounds(350, 430, 100, 50);

        txt_search.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });
        add(txt_search);
        txt_search.setBounds(480, 430, 290, 50);

        btn_search.setBackground(new java.awt.Color(51, 51, 255));
        btn_search.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_search.setForeground(new java.awt.Color(255, 255, 255));
        btn_search.setText("Tìm kiếm");
        btn_search.setBorderPainted(false);
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        add(btn_search);
        btn_search.setBounds(800, 430, 100, 50);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Frame Quản lý sản phảm.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        add(jLabel8);
        jLabel8.setBounds(0, 0, 1540, 800);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_catActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_catActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        String price = txt_price.getText().replace(",", "");
        
        if (txt_name.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Name không được trống");
            return;
        } else if (txt_cat.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Category không được trống");
            return;
        } else if (txt_brand.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Brand không được trống");
            return;
        } else if (txt_price.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Price không được trống");
            return;
        } else if (txt_country.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Country không được trống");
            return;
        }if(!vd.IntOrReal(price)){
            JOptionPane.showMessageDialog(this, "Hãy nhập đúng giá. VD: 1234 ; 1,234; 1234.00, 1,234.00");
            return;
        }
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();

            String brand_id = null;
            String cat_id = null;
            String product_id = null;

            brand_id = Lay_brand_id(vd.ChuanHoaChuoi(txt_brand.getText()));
            cat_id = Lay_cat_id(vd.ChuanHoaChuoi(txt_cat.getText()));
            product_id = Lay_product_id(vd.ChuanHoaChuoi(txt_name.getText()));

            String brand_id_copy = brand_id;
            String cat_id_copy = cat_id;

            System.out.println("Day la brand : " + brand_id + " va chieu dai: " + brand_id.length());
            System.out.println("Day la cat : " + cat_id + " va chieu dai: " + cat_id.length());
            System.out.println("Day la product : " + product_id + " va chieu dai: " + product_id.length());

            int chk_brand = 1;
            int chk_cat = 1;
            int chk_product = 1;

            if (brand_id.length() == 0) {
                String sql_brand = "insert into production.brands(brand_name,country) Values(?,?)";
                PreparedStatement ps_brand = conn.prepareStatement(sql_brand);
                ps_brand.setString(1, vd.ChuanHoaChuoi(txt_brand.getText()));
                ps_brand.setString(2, vd.ChuanHoaChuoi(txt_country.getText()));
                chk_brand = ps_brand.executeUpdate();
                brand_id = Lay_brand_id(vd.ChuanHoaChuoi(txt_brand.getText()));
            }

            if (cat_id.length() == 0) {
                String sql_cat = "insert into production.categories(category_name) Values(?)";
                PreparedStatement ps_cat = conn.prepareStatement(sql_cat);
                ps_cat.setString(1, vd.ChuanHoaChuoi(txt_cat.getText()));
                chk_cat = ps_cat.executeUpdate();
                cat_id = Lay_cat_id(vd.ChuanHoaChuoi(txt_cat.getText()));

            }

            if (product_id.length() == 0) {
                String sql_product = "insert into production.products(product_name, brand_id, category_id, price) Values(?,?,?,?)";
                PreparedStatement ps_product = conn.prepareStatement(sql_product);
                ps_product.setString(1, vd.ChuanHoaChuoi(txt_name.getText()));
                ps_product.setString(2, brand_id);
                ps_product.setString(3, cat_id);
                ps_product.setString(4, price);
                chk_product = ps_product.executeUpdate();
            } else {
                System.out.println("Da co mat hang nay");
            }
            
            if (chk_cat > 0 && chk_brand > 0 && chk_product > 0) {
                JOptionPane.showMessageDialog(this, "Them Thanh cong");
                reset();
                tbn.setRowCount(0);
                loadData();
            }
        } catch (Exception ex) {
            System.out.println("O btn them " + ex.toString());
        }
        
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        // TODO add your handling code here:
        String price = txt_price.getText().replace(",", "");
        if (txt_name.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Name không được trống");
            return;
        } else if (txt_cat.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Category không được trống");
            return;
        } else if (txt_brand.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Brand không được trống");
            return;
        } else if (txt_price.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Price không được trống");
            return;
        } else if (txt_country.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Country không được trống");
            return;
        }if(!vd.IntOrReal(price)){
            JOptionPane.showMessageDialog(this, "Hãy nhập đúng giá. VD: 1234 ; 1,234; 1234.00, 1,234.00");
            return;
        }

        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();

            String brand_id = null;
            String cat_id = null;
            String product_id = null;
            //String brand_name_copy;
            //String cat_name_copy;

            brand_id = Lay_brand_id(vd.ChuanHoaChuoi(txt_brand.getText()));
            cat_id = Lay_cat_id(vd.ChuanHoaChuoi(txt_cat.getText()));
            product_id = txt_id.getText();

            String brand_id_copy = Lay_brand_id_Tu_Product_Id(product_id);
            String cat_id_copy = Lay_cat_id_Tu_Product_Id(product_id);

            System.out.println("Day la brand : " + brand_id + " va chieu dai: " + brand_id.length());
            System.out.println("Day la cat : " + cat_id + " va chieu dai: " + cat_id.length());
            System.out.println("Day la product : " + product_id + " va chieu dai: " + product_id.length());

            int chk_brand = 1;
            int chk_cat = 1;
            int chk_product = 1;

            if (brand_id.length() == 0) {
                String sql_brand = "insert into production.brands(brand_name,country) Values(?,?)";
                PreparedStatement ps_brand = conn.prepareStatement(sql_brand);
                ps_brand.setString(1, vd.ChuanHoaChuoi(txt_brand.getText()));
                ps_brand.setString(2, vd.ChuanHoaChuoi(txt_country.getText()));
                chk_brand = ps_brand.executeUpdate();
                brand_id = Lay_brand_id(vd.ChuanHoaChuoi(txt_brand.getText()));
            }

            if (cat_id.length() == 0) {
                String sql_cat = "insert into production.categories(category_name) Values(?)";
                PreparedStatement ps_cat = conn.prepareStatement(sql_cat);
                ps_cat.setString(1, vd.ChuanHoaChuoi(txt_cat.getText()));
                chk_cat = ps_cat.executeUpdate();
                cat_id = Lay_cat_id(vd.ChuanHoaChuoi(txt_cat.getText()));
            }

            PreparedStatement comm = conn.prepareStatement("Update production.products set product_name = ?, brand_id = ?, category_id = ?, price = ? where product_id = ?");
            comm.setString(1, vd.ChuanHoaChuoi(txt_name.getText()));
            comm.setString(3, cat_id);
            comm.setString(2, brand_id);
            comm.setString(4, price);
            comm.setString(5, vd.ChuanHoaChuoi(txt_id.getText()));
            comm.executeUpdate();

            //brand_id = Lay_brand_id(brand_name_copy);
            // System.out.println("Name: " + brand_name_copy);
            System.out.println("ID: " + brand_id_copy);
            if (!kiemTra_brand_con_khong(brand_id_copy)) {
                //xoa brand ra khoi bang brand
                System.out.println("Da thuc thi");
                PreparedStatement con_brand = conn.prepareStatement("Delete production.brands where brand_id = ?");
                con_brand.setString(1, brand_id_copy);
                con_brand.executeUpdate();
                tbn.setRowCount(0);
                loadData();
            }

            //cat_id = Lay_cat_id(cat_name_copy);
            if (!kiemTra_cat_con_khong(cat_id_copy)) {
                //xoa cat ra khoi bang brand
                PreparedStatement con_cat = conn.prepareStatement("Delete production.categories where category_id = ?");
                con_cat.setString(1, cat_id_copy);
                con_cat.executeUpdate();
                tbn.setRowCount(0);
                loadData();
            }
            reset();
            JOptionPane.showMessageDialog(this, "Sua thanh cong");
            tbn.setRowCount(0);
            loadData();

        } catch (Exception ex) {
            System.out.println("O btn sua " + ex.toString());
        }
        
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        // TODO add your handling code here:

        String product_id = txt_id.getText();
        String brand_id_copy = Lay_brand_id_Tu_Product_Id(product_id);
        String cat_id_copy = Lay_cat_id_Tu_Product_Id(product_id);
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            PreparedStatement comm = conn.prepareStatement("Delete production.products where product_id = ?");
            comm.setString(1, txt_id.getText());
            if (JOptionPane.showConfirmDialog(this, "Are you want to delete ?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                comm.executeUpdate();
                tbn.setRowCount(0);
                loadData();
                if (!kiemTra_brand_con_khong(brand_id_copy)) {
                    //xoa brand ra khoi bang brand
                    System.out.println("Da thuc thi");
                    PreparedStatement con_brand = conn.prepareStatement("Delete production.brands where brand_id = ?");
                    con_brand.setString(1, brand_id_copy);
                    con_brand.executeUpdate();
                    tbn.setRowCount(0);
                    loadData();
                }

                //cat_id = Lay_cat_id(cat_name_copy);
                if (!kiemTra_cat_con_khong(cat_id_copy)) {
                    //xoa cat ra khoi bang brand
                    PreparedStatement con_cat = conn.prepareStatement("Delete production.categories where category_id = ?");
                    con_cat.setString(1, cat_id_copy);
                    con_cat.executeUpdate();
                    tbn.setRowCount(0);
                    loadData();
                }
                reset();
            }
        } catch (Exception ex) {
            System.out.println("Loi o xoa: " + ex.toString());
        }
        
    }//GEN-LAST:event_btn_xoaActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thoatActionPerformed
        // TODO add your handling code here:
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
        //frm_Main main = new frm_Main();
        //main.setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_btn_thoatActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        // TODO add your handling code here:

        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            String sql = "select production.products.product_id as N'Mã sản phẩm', product_name as N'Tên sản phẩm', production.products.price as N'Giá nhập', brand_name as N'Hãng', category_name as N'Loại hàng', country as N'Xuất xứ' from production.products\n"
                    + "inner join production.brands on production.products.brand_id = production.brands.brand_id\n"
                    + "inner join production.categories on production.categories.category_id = production.products.category_id where ";
            PreparedStatement ps = null;
            int number;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;

            if (jComboBoxSearch.getSelectedItem().toString().equals("ID")) {
                ps = con.prepareStatement(sql + "product_id like ?");
                str1 = "%" + vd.ChuanHoaChuoi(txt_search.getText()) + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Name")) {
                ps = con.prepareStatement(sql + "product_name like ?");
                str1 = "%" + vd.ChuanHoaChuoi(txt_search.getText()) + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Brand")) {
                ps = con.prepareStatement(sql + "brand_name like ?");
                str1 = "%" + vd.ChuanHoaChuoi(txt_search.getText()) + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Category")) {
                ps = con.prepareStatement(sql + "category_name like ?");
                str1 = "%" + vd.ChuanHoaChuoi(txt_search.getText()) + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Country")) {
                ps = con.prepareStatement(sql + "country like ?");
                str1 = "%" + vd.ChuanHoaChuoi(txt_search.getText()) + "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();
            }

            tbn.setRowCount(0);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    if(i == 3){
                         row.addElement(vd.DangTienTe(rs.getString(i)));
                    } else {
                         row.addElement(rs.getString(i));
                    }
                   
                }
                tbn.addRow(row);
                tbl_sp.setModel(tbn);
            }

        } catch (Exception ex) {
            System.out.println("Loi o tim kiem " + ex.toString());
        }
    }//GEN-LAST:event_txt_searchKeyReleased

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        try {
            Connect a = new Connect();
            Connection con = a.getConnectDB();
            String sql = "select production.products.product_id as N'Mã sản phẩm', product_name as N'Tên sản phẩm', production.products.price as N'Giá nhập', brand_name as N'Hãng', category_name as N'Loại hàng', country as N'Xuất xứ' from production.products\n"
                    + "inner join production.brands on production.products.brand_id = production.brands.brand_id\n"
                    + "inner join production.categories on production.categories.category_id = production.products.category_id where ";
            PreparedStatement ps = null;
            int number;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;

            if (jComboBoxSearch.getSelectedItem().toString().equals("ID")) {
                ps = con.prepareStatement(sql + "product_id = ?");
                ps.setString(1, vd.ChuanHoaChuoi(txt_search.getText()));
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Name")) {
                ps = con.prepareStatement(sql + "product_name = ?");
                ps.setString(1, vd.ChuanHoaChuoi(txt_search.getText()));
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Brand")) {
                ps = con.prepareStatement(sql + "brand_name = ?");
                ps.setString(1, vd.ChuanHoaChuoi(txt_search.getText()));
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Category")) {
                ps = con.prepareStatement(sql + "category_name = ?");
                ps.setString(1, vd.ChuanHoaChuoi(txt_search.getText()));
                rs = ps.executeQuery();
            } else if (jComboBoxSearch.getSelectedItem().toString().equals("Country")) {
                ps = con.prepareStatement(sql + "country like ?");
                ps.setString(1, vd.ChuanHoaChuoi(txt_search.getText()));
                rs = ps.executeQuery();
            }

            tbn.setRowCount(0);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);

            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    if(i == 3){
                         row.addElement(vd.DangTienTe(rs.getString(i)));
                    } else {
                         row.addElement(rs.getString(i));
                    }
                }
                tbn.addRow(row);
                tbl_sp.setModel(tbn);
            }

        } catch (Exception ex) {
            System.out.println("Loi o tim kiem " + ex.toString());
        }
    }//GEN-LAST:event_btn_searchActionPerformed

    private void txt_priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_priceKeyReleased
        // TODO add your handling code here:
        txt_price.setText(vd.DangTienTe(txt_price.getText().replace(",", "")));
    }//GEN-LAST:event_txt_priceKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_thoat;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JComboBox<String> jComboBoxSearch;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_sp;
    private javax.swing.JTextField txt_brand;
    private javax.swing.JTextField txt_cat;
    private javax.swing.JTextField txt_country;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_price;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
