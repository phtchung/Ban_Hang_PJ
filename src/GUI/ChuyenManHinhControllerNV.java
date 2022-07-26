package GUI; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author MyPC
 */
public class ChuyenManHinhControllerNV {

    private JPanel root;
    private String kindSelected = "";

    private Color color1 = new Color(234, 153, 47);
    private Color color2 = new Color(239, 65, 101);
    private List<DanhMucBean> listItem = null;

    public ChuyenManHinhControllerNV(JPanel jpnRoot) {
        this.root = jpnRoot;
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "TrangChu";

        jpnItem.setBackground(color1);
        jlbItem.setBackground(color1);

        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new TrangChu_Panel());
        root.validate();
        root.repaint();

    }

    public void setEvent(List<DanhMucBean> listItem) {
        this.listItem = listItem;
        for (DanhMucBean item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;

        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    node = new TrangChu_Panel();
                    break;
                case "QuanLy":
                    node = new NhanVienQuanLy_Panel();
                    //System.out.println("O Quan ly CTL");
                    break;
                case "ThongKe":
                    node = new ThongKe_Panel();
                    //System.out.println("Controller");
                    break;
                case "BanHang":
                    node = new BanHang_Panel();
                    break;
                default:
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(color1);
            jlbItem.setBackground(color1);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(color1);
            jlbItem.setBackground(color1);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(color2);
                jlbItem.setBackground(color2);
            }
        }

    }

    private void setChangeBackground(String kind) {
        for (DanhMucBean item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(color1);
                item.getJlb().setBackground(color1);
            } else {
                item.getJpn().setBackground(color2);
                item.getJlb().setBackground(color2);
            }
        }
    }
}
