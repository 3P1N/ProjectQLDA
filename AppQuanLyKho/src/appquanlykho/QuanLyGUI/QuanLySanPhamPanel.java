
package appquanlykho.QuanLyGUI;

import appquanlykho.Components.MyTable;
import appquanlykho.DAO.SanPhamDAO;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.SanPham;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author HP
 */
public class QuanLySanPhamPanel extends JPanel {

    private TopPanelQLSP topPanel;
    private MyTable table;

    public QuanLySanPhamPanel() throws SQLException, ClassNotFoundException, Exception {
        this.setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {

        try {
            // Panel Menu
            topPanel = new TopPanelQLSP();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // main
        JPanel mainPanel = new JPanel(new BorderLayout());

        // thanh filter
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = SanPham.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        table = new MyTable(columns, data);
        mainPanel.add(table, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);

        topPanel.getaddButton().addActionListener(e
                -> new ThemSanPhamFrame().setVisible(true));

        topPanel.getfilterButton().addActionListener(e -> {
            try {
                XuLyTraCuu();
            } catch (Exception ex) {
                Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getupdateButton().addActionListener(e -> HienThiFrameSuaSanPham());

      

        topPanel.getRefreshButton().addActionListener(e -> {
            try {
                HienThiDSSanPham();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            HienThiDSSanPham();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void XuLyTraCuu() throws ClassNotFoundException, Exception {
        SanPham sanPham = topPanel.getSanPham();
        HienThiDSSanPham(sanPham);
    }

    public void HienThiDSSanPham() throws SQLException, ClassNotFoundException, Exception {

        HienThiDSSanPham(new SanPham());
    }

    public void HienThiDSSanPham(SanPham sanPham) throws SQLException, ClassNotFoundException, Exception {
        java.util.List<SanPham> dsSanPham = SanPhamDAO.LayDSSanPham(sanPham);
        String[] columns = NguoiDung.getTableHeaders();
        Object[][] data = new Object[dsSanPham.size()][columns.length];

        for (int i = 0; i < dsSanPham.size(); i++) {
            data[i] = dsSanPham.get(i).toTableRow();
        }

        table.setTableData(data);
    }

    public void HienThiFrameSuaSanPham() {
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isChecked = (Boolean) table.getValueAt(i, 0); // Cột 0 là checkbox
            if (Boolean.TRUE.equals(isChecked)) {
                // Lấy thông tin dòng được chọn
                Integer idSanPham = (Integer) table.getValueAt(i, 1); // cột 1: mã ĐH

                // Gọi hàm xử lý
                new SuaSanPhamFrame(idSanPham).setVisible(true);
            }
        }
    } 

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Gói Hàng");

            try {
                frame.setContentPane(new QuanLySanPhamPanel());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLySanPhamPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
