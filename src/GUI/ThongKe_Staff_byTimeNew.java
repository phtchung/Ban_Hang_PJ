
package GUI;


import Utils.ValidateData;
import java.awt.Color;
import java.awt.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
//import static jdk.nashorn.internal.runtime.regexp.RegExpFactory.validate;
//import static sun.security.util.KeyUtil.validate;
//import static sun.security.util.KeyUtil.validate;

/**
 *
 * @author ADMIN
 */
public class ThongKe_Staff_byTimeNew extends javax.swing.JPanel {

    final String header[] = {"ID", "Họ và tên", "Email", "Số điện thoại", "Trạng thái", "Công việc"};
    final DefaultTableModel tb = new DefaultTableModel(header,0);
    Date date = new Date();
    Connect cn = new Connect();
    Connection conn = null;
    ResultSet rs;
    String Store_ID = Login.Store_ID;
    String hienThi;

    public ThongKe_Staff_byTimeNew() {
        initComponents();
        loadBang();
    }

 

    public void loadBang(){
        try {
         
            conn = cn.getConnectDB();
            int number;
            Vector row;
            String sql = "select staff_id, name, email, phone, active, manager_state  from sales.staffs\n" +
                        " where store_id = " + Store_ID;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            number = metaData.getColumnCount();
            tb.setRowCount(0);
             while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    if(i == 5)
                    {
                        row.addElement(rs.getString(i).equals("1") ? "Hoạt động" : "Đã Nghỉ");
                    } else if(i == 6){
                        row.addElement(rs.getString(i).equals("1") ? "Quản lý" : "Nhân viên");
                    }else {
                         row.addElement(rs.getString(i));
                    }
                }
                tb.addRow(row);
                tbl_NV.setModel(tb);
            }
             
            tbl_NV.getColumnModel().getColumn(0).setPreferredWidth(10);
            tbl_NV.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbl_NV.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbl_NV.getColumnModel().getColumn(3).setPreferredWidth(100);
            tbl_NV.getColumnModel().getColumn(4).setPreferredWidth(20);
            
            tbl_NV.setRowHeight(30);
             
            st.close();
            rs.close();
            conn.close();
        } catch (Exception e) {
        }
        tbl_NV.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(tbl_NV.getSelectedRow() >= 0){
                   String Staff_ID = tbl_NV.getValueAt(tbl_NV.getSelectedRow(), 0)+ "";
                    hienThi = tbl_NV.getValueAt(tbl_NV.getSelectedRow(), 5)+": "+tbl_NV.getValueAt(tbl_NV.getSelectedRow(), 1)+ "";
                    Map<String, Double> map = new LinkedHashMap<>();
                    createMap(Staff_ID, map);
                    initFrame(hienThi, map);
                      
                }
            }
        });
    }
    
    public void createMap(String Staff_ID, Map<String, Double> map) {
       
            try {
                Connect a = new Connect();
                Connection conn = a.getConnectDB();
                String sql_doanhthu = "select top (12) MonthYear, sum(DoanhThu) as DoanhThu from (\n" +
                                    "	select convert(datetime,CONCAT(datepart(mm, created_date),'-','01-',YEAR(created_date)),102) as MonthYear , soi.profit as DoanhThu from sales.staffs ss\n" +
                                    "	left join sales.orders so on so.staff_id = ss.staff_id\n" +
                                    "	left join sales.order_items soi on so.order_id = soi.order_id\n" +
                                    "	where ss.staff_id = ?) as Abc\n" +
                                    "	group by MonthYear\n" +
                                    "	order by MonthYear desc";
                PreparedStatement ps;
                ps = conn.prepareStatement(sql_doanhthu);
                ps.setString(1, Staff_ID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String MonthYear = rs.getString("MonthYear");
                    Double DoanhThu = rs.getDouble("DoanhThu");
                    map.put(MonthYear, DoanhThu);
                }

            } catch (Exception ex) {
                // System.out.println("Thong ke staff trong 12 tháng gần nhất "+ex.toString());
            }
        
    }
     public JFreeChart createChart(String x, Map<String, Double> map) {
        JFreeChart queryChart = ChartFactory.createLineChart(
                "Doanh thu các tháng gần nhất của " + x ,
                "Tháng", "Doanh thu",
                createDataset(map), PlotOrientation.VERTICAL, false, false, false);
        return queryChart;
    }
     
       public String cat7kytu(String ma){
        String cat = "";
        String arr[] = ma.split(" ");
        String a[] = arr[0].split("-");
        cat = a[1].concat("-").concat(a[0]);
        //System.out.println("***: "+ cat);
        return cat;
    }
       
    private CategoryDataset createDataset(Map<String, Double> map) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            dataset.addValue(value, "", cat7kytu(key));
        }
        return dataset;
    }
      public void initFrame(String x, Map<String, Double> map) {
        ChartPanel chartPanel = new ChartPanel(createChart(x, map));
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 500));
        jcontent.removeAll();
        jcontent.add(chartPanel);
        jcontent.setVisible(true);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnThoat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_NV = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jcontent = new javax.swing.JTabbedPane();
        jLabel2 = new javax.swing.JLabel();

        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 204, 255));
        jPanel1.setLayout(null);

        btnThoat.setBackground(new java.awt.Color(153, 0, 153));
        btnThoat.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        btnThoat.setContentAreaFilled(false);
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel1.add(btnThoat);
        btnThoat.setBounds(0, 690, 360, 120);

        tbl_NV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbl_NV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Họ và Tên", "Email", "Số điện thoại", "Trạng thái"
            }
        ));
        tbl_NV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_NVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_NV);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(530, 160, 820, 160);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 153));
        jLabel1.setText("Hãy click vào từng người để xem doanh thu những tháng gần đây");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(510, 80, 690, 70);
        jPanel1.add(jcontent);
        jcontent.setBounds(440, 350, 1080, 430);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thống kê time nhân viên .png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 1540, 820);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 1550, 820);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }//GEN-LAST:event_btnThoatActionPerformed
   
    private void tbl_NVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NVMouseClicked
        // TODO add your handling code here:
        int x = tbl_NV.getSelectedRow();
        if(x >= 0) {
            String Staff_ID = tbl_NV.getValueAt(tbl_NV.getSelectedRow(), 0)+ "";
            hienThi = tbl_NV.getValueAt(tbl_NV.getSelectedRow(), 5)+": "+tbl_NV.getValueAt(tbl_NV.getSelectedRow(), 1)+ "";
            Map<String, Double> map = new LinkedHashMap<>();
                    createMap(Staff_ID, map);
                    initFrame(hienThi, map);
        }    
    }//GEN-LAST:event_tbl_NVMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jcontent;
    private javax.swing.JTable tbl_NV;
    // End of variables declaration//GEN-END:variables
}
