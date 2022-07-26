package GUI; 

import java.awt.Toolkit;
import java.awt.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashMap;
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


public class TK_Store_byTime extends javax.swing.JFrame {
    Map<String, Double> map = new LinkedHashMap<>();
    
    public TK_Store_byTime() {
        this.setAlwaysOnTop(true); // this interface is always on top
        this.setResizable(false); // not resizeble now
        this.setVisible(true);
        initComponents();
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
        createMap();
        initFrame();
        
    }
    
    public void createMap() {
       
            try {
                Connect a = new Connect();
                Connection conn = a.getConnectDB();
                String sql_doanhthu = "select top (12) MonthYear, sum(DoanhThu) as DoanhThu from (\n" +
                                    "	select convert(datetime,CONCAT(datepart(mm, created_date),'-','01-',YEAR(created_date)),102) as MonthYear , soi.profit as DoanhThu \n" +
                                    "	from sales.order_items soi\n" +
                                    "	left join sales.orders so on so.order_id = soi.order_id\n" +
                                    "	where store_id = ?) as Abc\n" +
                                    "	group by MonthYear\n" +
                                    "	order by MonthYear desc";
                PreparedStatement ps;
                ps = conn.prepareStatement(sql_doanhthu);
                ps.setString(1, Login.Store_ID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String MonthYear = rs.getString("MonthYear");
                    Double DoanhThu = rs.getDouble("DoanhThu");
                    map.put(MonthYear, DoanhThu);
                }

            } catch (Exception ex) {
                System.out.println("Thong ke store by time: "+ex.toString());
            }
        
    }
     public JFreeChart createChart() {
        JFreeChart queryChart = ChartFactory.createLineChart(
                "Doanh thu 12 tháng gần nhất",
                "Tháng", "Doanh thu",
                createDataset(), PlotOrientation.VERTICAL, false, false, false);
        return queryChart;
    }
     
    public String cat7kytu(String ma){
        String cat = "";
        String arr[] = ma.split(" ");
        String a[] = arr[0].split("-");
        cat = a[1].concat("-").concat(a[0]);
        return cat;
    }
     
    private CategoryDataset createDataset() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            dataset.addValue(value, "", cat7kytu(key));
        }
        return dataset;
    }
      public void initFrame() {
        ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 500));
        jcontent.removeAll();
        jcontent.add(chartPanel);
        jcontent.setVisible(true);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jcontent = new javax.swing.JTabbedPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(20, 700, 330, 109);
        jPanel1.add(jcontent);
        jcontent.setBounds(450, 340, 990, 450);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Thống Kê doanh thu 12 tháng gần đây nhất");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(480, 170, 770, 65);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/THỐNG KÊ cửa hàng.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 1540, 820);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1540, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JComponent comp = (JComponent) evt.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(TK_Store_byTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TK_Store_byTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TK_Store_byTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TK_Store_byTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TK_Store_byTime().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jcontent;
    // End of variables declaration//GEN-END:variables
}
