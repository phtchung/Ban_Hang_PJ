package GUI;
 
import java.awt.Toolkit;
import java.awt.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class ThongKe_Staff extends javax.swing.JPanel {
    
    Map<String, Double> map = new HashMap<>();
    
    public ThongKe_Staff() {
        this.map = new HashMap<>();
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
        date1.setDate(new Date());
        date2.setDate(new Date());
       
//        createMap();
//        initFrame();
    }
    
    public void createMap() {
        Date tdate1 = date1.getDate();
        Date tdate2 = date2.getDate();
        Date tdate = new Date();
        if (tdate1.after(tdate2)) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            return;
        } else if (tdate1.after(tdate)) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            return;
        }
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            String sql_doanhthu = "select ss.staff_id as StaffID, sum(soi.profit) as DoanhThu from sales.staffs ss\n" +
                                "left join sales.orders so on so.staff_id = ss.staff_id\n" +
                                "left join sales.order_items soi on soi.order_id = so.order_id \n" +
                                "where ss.store_id = ? and (so.created_date between ? and ? or ss.staff_id not in (select staff_id from sales.orders))\n" +
                                "group by ss.staff_id";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql_doanhthu);
            java.sql.Date jdate1 = new java.sql.Date(tdate1.getTime());
            java.sql.Date jdate2 = new java.sql.Date(tdate2.getTime());
            ps.setString(1, Login.Store_ID);
            ps.setDate(2, jdate1);
            ps.setDate(3, jdate2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String StaffID = rs.getString("StaffID");
                Double DoanhThu = rs.getDouble("DoanhThu");
                map.put(StaffID, DoanhThu);
            }

        } catch (Exception ex) {
            System.out.println("Thong ke doanh thu "+ex.toString());
        }
    }
     public JFreeChart createChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Doanh thu các nhân viên đạt được",
                "Tên nhân viên", "Doanh thu",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }
     
     public String GetNameFromStaffID(String StaffID) {
        String Name = "";
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            int number;
            Vector row, column;
            column = new Vector();
            Statement st = conn.createStatement();
            String sql = "select name from sales.staffs\n" + "where staff_id = (?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            ps.setString(1, StaffID);
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
                    Name = rs.getString(i);
                }
            }

        } catch (Exception ex) {
            System.out.println("Loi o store" + ex.toString());
        }
        return Name;
    }
    
    private CategoryDataset createDataset() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            dataset.addValue(value, "Doanh thu", GetNameFromStaffID(key)+"-"+key);
        }
        return dataset;
    }
      public void initFrame() {
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        jcontent.removeAll();
        jcontent.add(chartPanel);
        jcontent.setVisible(true);
    }
      
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        date1 = new com.toedter.calendar.JDateChooser();
        date2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jcontent = new javax.swing.JTabbedPane();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setLayout(null);

        date1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(date1);
        date1.setBounds(610, 140, 170, 50);

        date2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(date2);
        date2.setBounds(1100, 130, 180, 50);

        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(900, 210, 140, 60);
        jPanel1.add(jcontent);
        jcontent.setBounds(370, 340, 1150, 460);

        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(1085, 250, 150, 70);

        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(10, 690, 340, 120);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/THỐNG KÊ full nhân viên.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(-20, 0, 1570, 820);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1524, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        createMap();
        initFrame();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jcontent;
    // End of variables declaration//GEN-END:variables
}
