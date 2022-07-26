// user: tranducmanh@gmail
// pass: manh135792468
// size: 1536va814
package GUI; 

 
import Utils.ValidateData;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public static String Staff_ID;
    public static String  Store_ID;
    public static String Store_Name;
    public static int manager_state;
    ValidateData vd = new ValidateData();
    public Login() {
        this.setResizable(false); // not resizeble now
        this.setVisible(true);
        initComponents();
        AutoSuggestor autoSuggestor = new AutoSuggestor(txt_email, this, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {
 
                //create list for dictionary this in your case might be done via calling a method which queries db and returns results as arraylist
                ArrayList<String> words = new ArrayList<>();
                String tmp;
                try {
                Connect a = new Connect();
                Connection conn = a.getConnectDB();
                int number;
                Statement st = conn.createStatement();
                String sql = "select * from dbo.suggestLogin";
                PreparedStatement ps;
                ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData metadata = rs.getMetaData();
                number = metadata.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= number; i++) {
                        System.out.println("+" + rs.getString(i));
                        words.add(rs.getString(i));
                    }
                }

            } catch (Exception ex) {
                System.out.println("Loi o store" + ex.toString());
            }
 
                setDictionary(words);
                //addToDictionary("bye");//adds a single word
 
                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        };
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        System.out.println(xsize + "va" + ysize);
        this.setSize(xsize, ysize);
        AutoDelete ad = new AutoDelete();
        ad.AutoDeleteOrder();
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
        jButton1 = new javax.swing.JButton();
        txt_email = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        label_saiTkMK = new javax.swing.JLabel();
        TKDaBiKhoa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jButton1.setBackground(new java.awt.Color(51, 255, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(920, 600, 270, 70);

        txt_email.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(txt_email);
        txt_email.setBounds(940, 340, 250, 40);

        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        jPanel1.add(txt_password);
        txt_password.setBounds(940, 450, 250, 41);

        label_saiTkMK.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        label_saiTkMK.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(label_saiTkMK);
        label_saiTkMK.setBounds(890, 550, 250, 28);

        TKDaBiKhoa.setForeground(new java.awt.Color(204, 102, 255));
        jPanel1.add(TKDaBiKhoa);
        TKDaBiKhoa.setBounds(1030, 230, 297, 32);

        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel1MouseMoved(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        jPanel1.add(jLabel1);
        jLabel1.setBounds(1040, 500, 150, 30);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/app Bán HÀNG.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 1540, 830);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1529, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        public String GetStoreIDFromEmail(String email) {
        String store_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select store_id from sales.staffs\n" + "where email = (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    store_id = rs.getString(i);
                }
            }

        } catch (Exception ex) {
            System.out.println("Loi o store" + ex.toString());
        }
        return store_id;
    }
        
    public String GetStoreNameFromID(String store_id) {
        String name = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select name from sales.stores\n" + "where store_id = (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, store_id);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    name = rs.getString(i);
                }
            }

        } catch (Exception ex) {
            System.out.println("Loi o store" + ex.toString());
        }
        return name;
    }
        
    public String GetStaff_IDFromEmail(String email) {
        String staff_id = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select staff_id from sales.staffs\n" + "where email = (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    staff_id = rs.getString(i);
                }
            }

        } catch (Exception ex) {
            System.out.println("Loi o store" + ex.toString());
        }
        return staff_id;
    }
    public Integer GetActiveFromEmail(String email) {
        int active = 0;
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select active from sales.staffs\n" + "where email = (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    active = rs.getInt(i);
                }
            }

        } catch (Exception ex) {
            System.out.println("Loi o store" + ex.toString());
        }
        return active;
    }
    
    public Integer GetManager_StateFromEmail(String email) {
        int managerState = 0;
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select manager_state from sales.staffs\n" + "where email = (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++) {
                column.add(metadata.getColumnName(i));
            }
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                    managerState = rs.getInt(i);
                }
            }

        } catch (Exception ex) {
            System.out.println("Loi o store" + ex.toString());
        }
        
        return managerState;
    }
    
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Connect cn = new Connect();
        Connection conn = null;
        int active = 0;
        try { 
            conn = cn.getConnectDB();
            String sql = "Select * from sales.staffs where email = ? and password = ?";
            PreparedStatement pst = conn.prepareCall(sql);
            pst.setString(1, txt_email.getText());
            pst.setString(2, vd.md5(txt_password.getText()));
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                Store_ID = GetStoreIDFromEmail(txt_email.getText());
                Staff_ID = GetStaff_IDFromEmail(txt_email.getText());
                active = GetActiveFromEmail(txt_email.getText());
                Store_Name = GetStoreNameFromID(Store_ID);
                manager_state = GetManager_StateFromEmail(txt_email.getText());
                System.out.println("Staff_ID: *** : "+ Staff_ID);
                System.out.println("active: ***: " + active);
                System.out.println("STore_ID: *** : "+ Store_ID);
                System.out.println("manager_state: ***: " + manager_state);
                System.out.println("Store_name: *** : "+ Store_Name);
                if(active == 0){
                    JOptionPane.showMessageDialog(this, "Tài khoản này đã bị khóa");
                    txt_email.setText("");
                    txt_password.setText("");
                    return;
                } else {
                    if(manager_state == 1){
                        Main_QuanLy mm = new Main_QuanLy();
                        mm.show();
                    } else {
                        Main_NhanVien sm = new Main_NhanVien();
                        sm.show();
                    }
                this.hide(); 
                }
                String sql1 = "select * from dbo.suggestLogin where email = ?";
            PreparedStatement pst1 = conn.prepareCall(sql1);
            pst1.setString(1, txt_email.getText());
            ResultSet rs1 = pst1.executeQuery();
            if(!rs1.next()){
                String sql2 = "insert into dbo.suggestLogin (email) values (?)";
            PreparedStatement pst2 = conn.prepareCall(sql2);
            pst2.setString(1, txt_email.getText());
            pst2.execute();
            }
            } else {
                 label_saiTkMK.setText("Sai tài khoản hoặc mật khẩu!");
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        } catch(Exception ex) {
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseMoved
        // TODO add your handling code here:
        jLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel1MouseMoved

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:
        SendCode sc = new SendCode();
        this.setVisible(false);
        sc.setVisible(true);
    }//GEN-LAST:event_jLabel1MousePressed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
        jButton1ActionPerformed(evt);
    }//GEN-LAST:event_txt_passwordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TKDaBiKhoa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_saiTkMK;
    private javax.swing.JTextField txt_email;
    private javax.swing.JPasswordField txt_password;
    // End of variables declaration//GEN-END:variables
}