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
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
//import static jdk.nashorn.internal.runtime.regexp.RegExpFactory.validate;
//import static sun.security.util.KeyUtil.validate;
//import static sun.security.util.KeyUtil.validate;

/**
 *
 * @author ADMIN
 */
public class QuanLyNhanVien extends javax.swing.JPanel {

    DefaultTableModel tbn = new DefaultTableModel();
    ValidateData validate = new ValidateData();
    static int old = -1;
    static String old1 = null;
    static String old2 = null;
    int store = Integer.parseInt(Login.Store_ID);
    

    public QuanLyNhanVien() {
        initComponents();
        loadData();
       // loadComobox();
        //loadComobox1();
       // loadComobox2();
        txtStaffID.setEnabled(false);
        BoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phone", "Email" }));
        cbActive.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoạt động", "Nghỉ"}));
        cbManagerstate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân viên", "Quản lý"}));
        cbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ"}));
        
        
    }
    public void loadComobox(){
        try{
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            PreparedStatement ps = conn.prepareStatement("Select Gender from tblStaff group by Gender");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cbGender.addItem(rs.getString("Gender"));           
            } 
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    public void loadComobox1(){
        // set combox active 
        try{
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            PreparedStatement ps = conn.prepareStatement("Select Active from tblStaff group by Active");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cbActive.addItem(rs.getInt("Active")+"");  
            } 
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void loadComobox2(){
        try{
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            PreparedStatement ps = conn.prepareStatement("Select ManagerState from tblStaff group by ManagerState");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cbManagerstate.addItem(rs.getInt("Managerstate")+"");  
            } 
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    

    public void loadData() {
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st =conn.createStatement();
            String query = "Select staff_id, name, email, phone, active, manager_state, gender from sales.staffs where store_id='"+store+"'";
            ResultSet rs=st.executeQuery(query);
            //ResultSetMetaData metadata =rs.getMetaData();
            number = jTable1.getColumnCount();
            for(int i = 0; i < jTable1.getColumnCount(); i++){
                column.add(jTable1.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);
            //tbn.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= number; i++){
                    if(i==5)//vị trí của column active
                    {
                        row.addElement(rs.getString(i).equals("1") ? "Hoạt động" : "Nghỉ");
                    }else if(i == 6){
                        row.addElement(rs.getString(i).equals("1") ? "Quản lý" : "Nhân viên");
                    }else {
                    row.addElement(rs.getString(i));
                    }
                    
                }
                tbn.addRow(row);
                jTable1.setModel(tbn);  
            }
            
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(30);
            
            jTable1.setRowHeight(30);
        st.close();
        rs.close();
        conn.close();  
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
               public void valueChanged (ListSelectionEvent e){
                   if(jTable1.getSelectedRow()>= 0){
                       txtStaffID.setText(jTable1.getValueAt(jTable1.getSelectedRow(),0)+ "");
                       old = Integer.parseInt(txtStaffID.getText());
                       //txtStaffID.setEnabled(true);
                       txtName.setText(jTable1.getValueAt(jTable1.getSelectedRow(),1)+ "");
                       txtEmail.setText(jTable1.getValueAt(jTable1.getSelectedRow(),2)+ "");
                       old1 = txtEmail.getText();
                       txtPhone.setText(jTable1.getValueAt(jTable1.getSelectedRow(),3)+ "");
                       old2 = txtPhone.getText();
                       cbActive.setSelectedItem(jTable1.getModel().getValueAt(jTable1.getSelectedRow(),4).equals("Hoạt động") ? "Hoạt động" : "Nghỉ" + "");
                       cbManagerstate.setSelectedItem(jTable1.getModel().getValueAt(jTable1.getSelectedRow(),5).equals("Quản lý") ? "Quản lý" : "Nhân viên" +"");
                       cbGender.setSelectedItem(jTable1.getModel().getValueAt(jTable1.getSelectedRow(),6)+ "");
                       //txtpass.setText(jTable1.getValueAt(jTable1.getSelectedRow(),8)+ "");
                   } 
                }
           });
    }
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtStaffID = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        cbActive = new javax.swing.JComboBox<>();
        cbManagerstate = new javax.swing.JComboBox<>();
        txtTimkiem = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtName = new javax.swing.JTextField();
        cbGender = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        BoxSearch = new javax.swing.JComboBox<>();
        Btn_Reset = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setLayout(null);

        txtStaffID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtStaffID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStaffIDActionPerformed(evt);
            }
        });
        jPanel1.add(txtStaffID);
        txtStaffID.setBounds(460, 150, 220, 40);

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });
        jPanel1.add(txtPhone);
        txtPhone.setBounds(460, 252, 220, 40);

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(txtEmail);
        txtEmail.setBounds(460, 302, 300, 40);

        cbActive.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbActiveActionPerformed(evt);
            }
        });
        jPanel1.add(cbActive);
        cbActive.setBounds(900, 190, 220, 40);

        cbManagerstate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(cbManagerstate);
        cbManagerstate.setBounds(900, 240, 220, 40);

        txtTimkiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimkiemKeyReleased(evt);
            }
        });
        jPanel1.add(txtTimkiem);
        txtTimkiem.setBounds(500, 450, 250, 50);

        btnThem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnThem.setContentAreaFilled(false);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem);
        btnThem.setBounds(1190, 100, 200, 90);

        btnSua.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSua.setContentAreaFilled(false);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel1.add(btnSua);
        btnSua.setBounds(1190, 250, 210, 110);

        btnXoa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnXoa.setContentAreaFilled(false);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoa);
        btnXoa.setBounds(1190, 390, 200, 110);

        btnThoat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnThoat.setContentAreaFilled(false);
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel1.add(btnThoat);
        btnThoat.setBounds(0, 690, 290, 110);

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ và tên", "Email", "Số điện thoại", "Trạng Thái", "Chức vụ", "Giới tính"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(345, 509, 1150, 270);

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel1.add(txtName);
        txtName.setBounds(460, 200, 220, 40);

        cbGender.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGenderActionPerformed(evt);
            }
        });
        jPanel1.add(cbGender);
        cbGender.setBounds(900, 134, 220, 40);

        btnSearch.setBackground(new java.awt.Color(51, 51, 255));
        btnSearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Tìm kiếm");
        btnSearch.setBorderPainted(false);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch);
        btnSearch.setBounds(770, 450, 130, 40);

        BoxSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        BoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(BoxSearch);
        BoxSearch.setBounds(360, 450, 100, 40);

        Btn_Reset.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Btn_Reset.setContentAreaFilled(false);
        Btn_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ResetActionPerformed(evt);
            }
        });
        jPanel1.add(Btn_Reset);
        Btn_Reset.setBounds(950, 400, 200, 90);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Frame Quản lý nhân viên.png"))); // NOI18N
        jLabel7.setText("jLabel7");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 10, 1540, 814);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1544, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtStaffIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStaffIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStaffIDActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed
  // Xoa
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        try{
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            PreparedStatement comm =conn.prepareStatement(" Delete  from sales.staffs where staff_id=?");
            comm.setString(1,jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString() );
            if(JOptionPane.showConfirmDialog(this, "Delete this Staff ?","Confirm",JOptionPane.YES_NO_OPTION) 
                    == JOptionPane.YES_NO_OPTION)
            {
                comm.executeUpdate();
                tbn.setRowCount(0);
                loadData(); 
                Reset();
            } 
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void cbActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbActiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbActiveActionPerformed

    private void cbGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGenderActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        
        try{
            int randomCode;
            Random rand = new Random();
            randomCode = rand.nextInt(999999);
            //sendEmail_QuenMK send = new sendEmail_QuenMK();
            //send.sendmail(txtEmail.getText(), randomCode);
            String matkhau = String.valueOf(randomCode);
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            String Name = validate.ChuanHoaChuoi(txtName.getText()); 
            String Email = txtEmail.getText().trim();
            if(Name.equals("") ||Email.equals("") ||txtPhone.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Không được bỏ trống ");
            }else if( !validate.kiemTraTen(txtName.getText())){
                JOptionPane.showMessageDialog(this, "Tên không được chứa chữ số");      
            }else if (!validate.kiemTraEmail(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Không đúng định dạng email");      
            }else if (!validate.kiemTraSDT(txtPhone.getText()).equals("")) {
            JOptionPane.showMessageDialog(this, validate.kiemTraSDT(txtPhone.getText()));
            
            }else{
                StringBuffer sb = new StringBuffer();
               // String check_pk = " Select staff_id from sales.staffs where staff_id='"+txtStaffID.getText()+"'";
                Statement st = conn.createStatement();
                //ResultSet rs = st.executeQuery(check_pk);
                String check_phone = " Select phone from sales.staffs where phone='"+txtPhone.getText()+"'";
                Statement st1 = conn.createStatement();
                ResultSet rs1 = st1.executeQuery(check_phone);
                String check_mail = " Select email from sales.staffs where email='"+Email+"'";
                Statement st2 = conn.createStatement();
                ResultSet rs2 = st2.executeQuery(check_mail);
//                if(rs.next()){
//                    JOptionPane.showMessageDialog(this,"StaffID đã tồn tại ");
//                
//                //if(sb.length()>0){
//                    //JOptionPane.showMessageDialog(this, sb.toString());   
//                }else 
                    if(rs1.next()){
                    JOptionPane.showMessageDialog(this, "Phone đã tồn tại");
                }else if(rs2.next()){
                    
                   JOptionPane.showMessageDialog(this,"Email đã tồn tại");
                }else{
                    
                    PreparedStatement ps = conn.prepareStatement(" insert into sales.staffs( name, email, phone, active, store_id, manager_state,gender, password) values(?,?,?,?,?,?,?,?)");
                    //ps.setString(1, txtStaffID.getText());
                    txtStaffID.setEnabled(false);
                    ps.setString(1, Name);
                    ps.setString(2, Email);
                    ps.setString(3, txtPhone.getText());
                    ps.setString(4, cbActive.getSelectedItem().toString().equals("Hoạt động") ? "1" : "0");
                    ps.setString(5, store+"");
                    ps.setString(6, cbManagerstate.getSelectedItem().toString().equals("Quản lý") ? "1" : "0");
                    ps.setString(7, cbGender.getSelectedItem().toString());
                    ps.setString(8, validate.md5(matkhau) );
                    System.out.println("MK: "+ validate.md5(matkhau) );
                    int check = ps.executeUpdate();
                    if (check > 0  ) {
                        //send.sendmail(txtEmail.getText(), randomCode);
                        JOptionPane.showMessageDialog(this, " Thêm thành công ");
                        Reset();
                        //send.sendmail(txtEmail.getText(), randomCode);
                        tbn.setRowCount(0);
                        loadData();
                        sendEmail_QuenMK send = new sendEmail_QuenMK();
                        String noidung = "Chào mừng bạn đã trở thành nhân viên của " + Login.Store_Name +"\nHãy đăng nhập bằng email của bạn, bạn có thể đổi lại mật khẩu sau khi đăng nhập. \nMật khẩu của bạn là:";
                        send.sendmail(Email, randomCode, "Mật khẩu của bạn", Login.Store_Name, noidung );
                    }
                }   
            }
            conn.close();
//            PreparedStatement ps =conn.prepareStatement("insert into tblStaff values(?,?,?,?,?,?,?,?,?)");
//            ps.setString(1,txtStaffID.getText());
//            ps.setString(2,txtName.getText());
//            ps.setString(3,txtEmail.getText());
//            ps.setString(4,txtPhone.getText());
//            ps.setString(5,cbActive.getSelectedItem().toString());
//            ps.setString(6,txtStoreid.getText());
//            ps.setString(7,cbManagerstate.getSelectedItem().toString());
//            ps.setString(8,cbGender.getSelectedItem().toString());
//            ps.setString(9,txtpass.getText());
//            
//            
//            int check = ps.executeUpdate();
//            if(check >0){
//                JOptionPane.showMessageDialog(this, " Thêm thành công ");
//                tbn.setRowCount(0);
//                loadData();
//            }  
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    
    }//GEN-LAST:event_btnThemActionPerformed
     // Sua 
    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        try{
            Connect a = new Connect();
            Connection conn =a.getConnectDB();
            String Name = validate.ChuanHoaChuoi(txtName.getText()); 
            String Email = txtEmail.getText().trim();
        if(Name.equals("") ||Email.equals("") ||txtPhone.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Không được bỏ trống ");
            }else if( !validate.kiemTraTen(Name)){
                JOptionPane.showMessageDialog(this, "Tên không được chứa chữ số");      
            }else if (!validate.kiemTraEmail(Email)) {
            JOptionPane.showMessageDialog(this, "Không đúng định dạng email");      
            }else if (!validate.kiemTraSDT(txtPhone.getText()).equals("")) {
            JOptionPane.showMessageDialog(this, validate.kiemTraSDT(txtPhone.getText()));
            }else{
                int key =0;
                int key1 =0;
                int key2 =0;
//                if (key == 0) {
//                while (rs1.next()) {
//                    for (int i = 1; i <= number; i++) {
//                        if (rs1.getString(i) == txtEmail.getText()) {
//                            JOptionPane.showMessageDialog(this, "Email đã tồn tại ");
//                            ktra = 1;
//                            key = 1;
//                            break;
//                        }
//                    }
//                    if (ktra == 1) {
//                        break;
//                    }
//                }
//            }
            if(txtStaffID.getText().equals(old+"")){
                    //chưa đổi id, key !=1
                        key = 0;
                }else{
                JOptionPane.showMessageDialog(this, "Không được thay đổi StaffID");
                key = 1;
            }       
//                else {
//                           //đã đổi key
//                String check_pk = " Select staffID from tblStaff where staffID ='"+txtStaffID.getText()+"'";
//
//                Statement st = conn.createStatement(
//                ResultSet.TYPE_SCROLL_INSENSITIVE,
//                ResultSet.CONCUR_READ_ONLY
//                );
//                ResultSet rs = st.executeQuery(check_pk);
//                if(rs.next()){
//                    JOptionPane.showMessageDialog(this,"StaffID đã tồn tại ");
//                    key = 1;  
//                }
//                //int number = rs.last() ? rs.getRow() : 0;
//               // System.out.println(number); // number != 0 thì đã tồn tại. = 0 chưa tồn tại, đỏi key ở đây
//               }
             
            if(txtEmail.getText().equals(old1)){
                    //chưa đổi id, key !=1
                        key1 = 0;
            }       
            else {
                           //đã đổi key
                String check_mail = " Select email from sales.staffs where email ='"+Email+"'";
                Statement st = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
                );
                ResultSet rs1 = st.executeQuery(check_mail);
                if(rs1.next()){
                    JOptionPane.showMessageDialog(this,"Email đã tồn tại ");
                    key1 = 1;  
                }   
            } 
            if(txtPhone.getText().equals(old2)){
                    //chưa đổi id, key !=1
                        key2 = 0;
            }else {
                           //đã đổi key
                String check_phone = " Select phone from sales.staffs where phone ='"+txtPhone.getText()+"'";
                Statement st = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
                );
                ResultSet rs2 = st.executeQuery(check_phone);
                if(rs2.next()){
                    JOptionPane.showMessageDialog(this,"Phone đã tồn tại ");
                    key2 = 1;  
                }
                
            }       
            if(key ==0 && key1 ==0 && key2==0){
        
            PreparedStatement comm =conn.prepareStatement(" Update sales.staffs set name=?,email=?,phone=?,active=?,store_id=?,manager_state=?,gender=? where staff_id=?");
            //txtStaffID.setEnabled(true);
            comm.setString(8,txtStaffID.getText());
            comm.setString(1, Name);
            comm.setString(2, Email);
            comm.setString(3, txtPhone.getText());
            comm.setString(4, cbActive.getSelectedItem().toString().equals("Hoạt động") ? "1" : "0");
            comm.setString(5, store+"");
            comm.setString(6, cbManagerstate.getSelectedItem().toString().equals("Quản lý") ? "1" : "0");
            comm.setString(7, cbGender.getSelectedItem().toString());
            //comm.setString(8, txtpass.getText());
            comm.executeUpdate();
            tbn.setRowCount(0);
            loadData();
            JOptionPane.showMessageDialog(this, "Sửa thành công ");
            Reset();
            }
        }  
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }//GEN-LAST:event_btnThoatActionPerformed
   public void Reset(){
        tbn.setRowCount(0);
        txtStaffID.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtTimkiem.setText("");
        
        Btn_Reset.setEnabled(true);
        btnSearch.setEnabled(true);
        btnSua.setEnabled(true);
        btnThem.setEnabled(true);
        btnThoat.setEnabled(true);
        btnXoa.setEnabled(true);
        loadData();
        
   }
    private void Btn_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ResetActionPerformed
        Reset();
// TODO add your handling code here:
    }//GEN-LAST:event_Btn_ResetActionPerformed

    private void txtTimkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimkiemKeyReleased
        // TODO add your handling code here:
        
            try{
                //DefaultTableModel defaultTableModel = new DefaultTableModel();
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            StringBuffer sb = new StringBuffer();
            PreparedStatement ps = null;
            //String search_pk = "Select * from tblStaff where (staffID='"+txtTimkiem.getText()+"') or (Phone='"+txtTimkiem.getText()+"') or (Name like N'"+txtTimkiem.getText()+"') ";
            String sql = "Select * from sales.staffs where store_id ='"+store+" 'and ";
            int number;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;
            if(BoxSearch.getSelectedItem().toString().equals("Phone")){
                ps = conn.prepareStatement(sql + "phone like ? ");
                str1 = "%"+ txtTimkiem.getText()+ "%";
                ps.setString(1, str1);
                rs = ps.executeQuery();  
            }else if(BoxSearch.getSelectedItem().toString().equals("Email")){
                ps = conn.prepareStatement(sql + "email like ? ");
                str1 = "%" + txtTimkiem.getText() + "%";
                ps.setString(1, str1);
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
                    if(i==5)//vị trí của column active
                    {
                        row.addElement(rs.getString(i).equals("1") ? "Hoạt động" : "Nghỉ");
                    }else if(i == 6){
                        row.addElement(rs.getString(i).equals("1") ? "Quản lý" : "Nhân viên");
                    }else {
                    row.addElement(rs.getString(i));
                    }
                }
                tbn.addRow(row);
                jTable1.setModel(tbn);
            }
            }catch(Exception ex){
                System.out.println(  ex.toString());
            }   
        
    }//GEN-LAST:event_txtTimkiemKeyReleased

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try{
                //DefaultTableModel defaultTableModel = new DefaultTableModel();
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            StringBuffer sb = new StringBuffer();
            PreparedStatement ps = null;
            //String search_pk = "Select * from tblStaff where (staffID='"+txtTimkiem.getText()+"') or (Phone='"+txtTimkiem.getText()+"') or (Name like N'"+txtTimkiem.getText()+"') ";
            String sql = "Select * from sales.staffs where store_id ='"+store+" 'and ";
            int number;
            Vector row, column;
            column = new Vector();
            ResultSet rs = null;
            String str1 = null;
            if(BoxSearch.getSelectedItem().toString().equals("Phone")){
                ps = conn.prepareStatement(sql + "phone = ? ");
                str1 =  txtTimkiem.getText();
                ps.setString(1, str1);
                rs = ps.executeQuery();  
            }else if(BoxSearch.getSelectedItem().toString().equals("Email")){
                ps = conn.prepareStatement(sql + "email = ? ");
                str1 =   txtTimkiem.getText() ;
                ps.setString(1, str1);
                rs = ps.executeQuery();  
            }
            
            tbn.setRowCount(0);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            
            for(int i = 1; i <= number; i++){
                column.add(metadata.getColumnName(i));
            }
            tbn.setColumnIdentifiers(column);
            
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i <= number; i++){
                    if(i==5)//vị trí của column active
                    {
                        row.addElement(rs.getString(i).equals("1") ? "Hoạt động" : "Nghỉ");
                    }else if(i == 7){
                        row.addElement(rs.getString(i).equals("1") ? "Quản lý" : "Nhân viên");
                    }else {
                    row.addElement(rs.getString(i));
                    }
                }
                tbn.addRow(row);
                jTable1.setModel(tbn);
            }
            }catch(Exception ex){
                System.out.println(  ex.toString());
            }   
        
// TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        btnThem.setEnabled(false);
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> BoxSearch;
    private javax.swing.JButton Btn_Reset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbActive;
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JComboBox<String> cbManagerstate;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtStaffID;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}
