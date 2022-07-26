
package GUI;

//import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author MyPC
 */
public class ThongKe_Cat extends javax.swing.JPanel {

    /**
     * Creates new form ThongKe_Cat
     */
    Map<String, Double> map = new HashMap<>();
    Map<String, Integer> map2 = new HashMap<>();
    static int store = Integer.parseInt(Login.Store_ID);

    public ThongKe_Cat() {
        this.map2 = new HashMap<>();
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight() - 50;
        this.setSize(xsize, ysize);
        createMap();
        initFrame();
//        date1.setDate(new Date());
//        date2.setDate(new Date());
    }

    public void createMap() {
        map = new HashMap<>();
        Date tdate1 = date1.getDate();
        Date tdate2 = date2.getDate();
        Date tdate = new Date();
        try{
        if (tdate1.after(tdate2)) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            return;
        } else if (tdate1.after(tdate)) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            return;
        }
        }catch(Exception ex){
            System.out.println("* Loi ơ: "+ ex.toString());
        }
        try {
            Connect a = new Connect();
            Connection conn = a.getConnectDB();
            String sql_doanhthu = "select top(9) pc.category_name as Ten, sum(so.profit) as Tien from production.categories pc\n" +
                                "left join production.products pp on pc.category_id = pp.category_id\n" +
                                "left join sales.order_items so on so.product_id = pp.product_id\n" +
                                "left join sales.orders sod on sod.order_id = so.order_id\n" +
                                "where so.order_id not in (select order_id from sales.orders where store_id = ?) or (so.store_id = ? and sod.created_date between  ? and ?)\n" +
                                "group by pc.category_name\n" +
                                "order by Tien desc";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql_doanhthu);
            java.sql.Date jdate1 = new java.sql.Date(tdate1.getTime());
            java.sql.Date jdate2 = new java.sql.Date(tdate2.getTime());
            ps.setInt(1, store);
            ps.setInt(2, store);
            ps.setDate(3, jdate1);
            ps.setDate(4, jdate2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("1");
                String name = rs.getString("Ten");
                Double tien = rs.getDouble("Tien");
                map.put(name, tien);
            }

        } catch (Exception ex) {
            System.out.println("Thong ke doanh thu " + ex.toString());
        }
    }

    public JFreeChart createChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Thống kê doanh thu loại mặt hàng",
                "Tên mặt hàng", "Số tiền",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }

    private CategoryDataset createDataset() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            dataset.addValue(value, "Số tiền", key);
        }
        return dataset;
    }

    public void initFrame() {

        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(1412, 372));
        jcontent.removeAll();
        jcontent.add(chartPanel);
        jcontent.setVisible(true);
        /*
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setTitle("Biểu đồ mặt hàng");
        frame.setSize(1000, 400);
        frame.setMaximumSize(getMaximumSize());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
         */

    }

    public void createMap2() {
        map2 = new HashMap<>();
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
            String sql_doanhthu = "select top(9) pc.category_name as Ten, count(so.profit) as So from production.categories pc\n" +
                            "left join production.products pp on pc.category_id = pp.category_id\n" +
                            "left join sales.order_items so on so.product_id = pp.product_id\n" +
                            "left join sales.orders sod on sod.order_id = so.order_id\n" +
                            "where so.order_id not in (select order_id from sales.orders where store_id = ?) or (so.store_id = ? and sod.created_date between  ? and ?)\n" +
                            "group by pc.category_name\n" +
                            "order by so desc";
            PreparedStatement ps;
            ps = conn.prepareStatement(sql_doanhthu);
            java.sql.Date jdate1 = new java.sql.Date(tdate1.getTime());
            java.sql.Date jdate2 = new java.sql.Date(tdate2.getTime());
            ps.setInt(1, store);
            ps.setInt(2, store);
            ps.setDate(3, jdate1);
            ps.setDate(4, jdate2);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            Integer number = metadata.getColumnCount();
            System.out.println(number);
            while (rs.next()) {
                String name = rs.getString("Ten");
                Double so = rs.getDouble("So");
                map.put(name, so);
            }

        } catch (Exception ex) {
            System.out.println("Thong ke so luong " + ex.toString());
        }
    }

    public JFreeChart createChart2() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Thống kê số lượng bán đợc của mặt hàng",
                "Tên mặt hàng", "Số lượng",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }

    private CategoryDataset createDataset2() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            dataset.addValue(value, "Số lượng", key);
        }
        return dataset;
    }

    public void initFrame2() {

        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(1412, 372));
        jcontent.removeAll();
        jcontent.add(chartPanel);
        jcontent.setVisible(true);
        //jcontent.setSize(1000, 500);

        /*
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setTitle("Biểu đồ mặt hàng");
        frame.setSize(1000, 400);
        frame.setMaximumSize(getMaximumSize());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
         */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcontent = new javax.swing.JTabbedPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        date1 = new com.toedter.calendar.JDateChooser();
        date2 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setLayout(null);
        add(jcontent);
        jcontent.setBounds(400, 340, 1140, 450);

        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(610, 250, 160, 70);

        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(1110, 250, 150, 70);

        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(0, 690, 370, 110);

        date1.setDate(new java.util.Date(1578102937000L));
        date1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(date1);
        date1.setBounds(620, 130, 180, 50);

        date2.setDate(new java.util.Date(1641722768000L));
        date2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(date2);
        date2.setBounds(1110, 130, 160, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/THỐNG KÊ loại hàng.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        add(jLabel1);
        jLabel1.setBounds(0, 0, 1530, 810);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        createMap();
        initFrame();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        createMap2();
        initFrame2();
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTabbedPane jcontent;
    // End of variables declaration//GEN-END:variables
}
