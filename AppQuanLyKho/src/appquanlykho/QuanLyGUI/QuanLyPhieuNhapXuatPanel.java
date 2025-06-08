
package appquanlykho.QuanLyGUI;

import appquanlykho.Components.MyTable;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.DAO.PhieuNhapXuatDAO;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.PhieuNhapXuat;
import appquanlykho.Entity.SanPham;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author HP
 */
public class QuanLyPhieuNhapXuatPanel extends JPanel {

    private TopPanelQLPNX topPanel;
    private MyTable table;
    private NguoiDung nguoiDung;
    public QuanLyPhieuNhapXuatPanel(NguoiDung nguoiDung) throws SQLException, ClassNotFoundException, Exception {
        this.setLayout(new BorderLayout());
        this.nguoiDung = nguoiDung;
        initUI();
    }

    private void initUI() {

        try {
            // Panel Menu
            topPanel = new TopPanelQLPNX();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        topPanel.getDuyetButton().addActionListener(e
                -> {
            try {
                XuLyPhieu("Đã duyệt");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getfilterButton().addActionListener(e -> {
            try {
                XuLyTraCuu();
            } catch (Exception ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getHuyButton().addActionListener(e -> {
            try {
                XuLyPhieu("Đã hủy");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        

        topPanel.getRefreshButton().addActionListener(e -> {
            try {
                HienThiDSPhieuNhapXuat();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            HienThiDSPhieuNhapXuat();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void XuLyTraCuu() throws ClassNotFoundException, Exception {
        PhieuNhapXuat phieuNhapXuat = topPanel.getPhieuNhapXuat();
        HienThiDSPhieuNhapXuat(phieuNhapXuat);
    }

    public void HienThiDSPhieuNhapXuat() throws SQLException, ClassNotFoundException, Exception {

        HienThiDSPhieuNhapXuat(new PhieuNhapXuat());
    }

    public void HienThiDSPhieuNhapXuat(PhieuNhapXuat phieuNhapXuat) throws SQLException, ClassNotFoundException, Exception {
        
        java.util.List<PhieuNhapXuat> dsPhieuNhapXuat = PhieuNhapXuatDAO.LayDSPhieuNhapXuat(phieuNhapXuat);
        String[] columns = NguoiDung.getTableHeaders();
        Object[][] data = new Object[dsPhieuNhapXuat.size()][columns.length];

        for (int i = 0; i < dsPhieuNhapXuat.size(); i++) {
            data[i] = dsPhieuNhapXuat.get(i).toTableRow();
        }

        table.setTableData(data);
    }

   

    public void XuLyPhieu(String trangThai) throws SQLException, ClassNotFoundException, Exception {
        
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isChecked = (Boolean) table.getValueAt(i, 0); // Cột 0 là checkbox
            if (Boolean.TRUE.equals(isChecked)) {
                // Lấy thông tin dòng được chọn
                Integer idPhieuNhapXuat = (Integer) table.getValueAt(i, 1); // cột 1: mã ĐH
                
                // Gọi hàm xử lý
                PhieuNhapXuat pnx = new PhieuNhapXuat();
                pnx.setIdPhieuNhapXuat(idPhieuNhapXuat);
                pnx.setTrangThai(trangThai);
                PhieuNhapXuatDAO.SuaPhieuNhapXuat(pnx);
                JOptionPane.showMessageDialog(this, "Xác nhận phiếu nhập xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                HienThiDSPhieuNhapXuat();
            }
        }
    }
//    

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Phiếu Nhập Xuất");

            try {
                
                frame.setContentPane(new QuanLyPhieuNhapXuatPanel(new NguoiDung()));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyPhieuNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

