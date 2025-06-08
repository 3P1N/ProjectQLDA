package appquanlykho.NhanVienGUI;

import appquanlykho.Components.MyTable;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.DAO.PhieuNhapXuatDAO;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.PhieuNhapXuat;

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
public class QuanLyNhapXuatPanel extends JPanel {

    private TopPanelQLNX topPanel;
    private MyTable table;
    private NguoiDung nguoiDung;

    public QuanLyNhapXuatPanel(NguoiDung nguoiDung) throws SQLException, ClassNotFoundException, Exception {
        this.nguoiDung = nguoiDung;
        this.setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {

        try {
            // Panel Menu
            topPanel = new TopPanelQLNX(nguoiDung);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // main
        JPanel mainPanel = new JPanel(new BorderLayout());

        // thanh filter
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = PhieuNhapXuat.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        table = new MyTable(columns, data);
        mainPanel.add(table, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);

        topPanel.getaddButton().addActionListener(e
                -> new TaoPhieuNhapXuatFrame(nguoiDung).setVisible(true));

        topPanel.getfilterButton().addActionListener(e -> {
            try {
                XuLyTraCuu();
            } catch (Exception ex) {
                Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getRefreshButton().addActionListener(e -> {
            try {
                HienThiDSPhieuNhapXuat();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            HienThiDSPhieuNhapXuat();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        if (nguoiDung.getVaiTro().equals("Nhân viên nhập")) {
            phieuNhapXuat.setLoaiPhieu("Nhap");
        } else {
            phieuNhapXuat.setLoaiPhieu("Xuat");

        }
        java.util.List<PhieuNhapXuat> dsPhieuNhapXuat = PhieuNhapXuatDAO.LayDSPhieuNhapXuat(phieuNhapXuat);
        String[] columns = NguoiDung.getTableHeaders();
        Object[][] data = new Object[dsPhieuNhapXuat.size()][columns.length];

        for (int i = 0; i < dsPhieuNhapXuat.size(); i++) {
            data[i] = dsPhieuNhapXuat.get(i).toTableRow();
        }

        table.setTableData(data);
    }

//    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Gói Hàng");
            NguoiDung nd = new NguoiDung();
            nd.setIdNguoiDung(3);
            try {
                frame.setContentPane(new QuanLyNhapXuatPanel(NguoiDungDAO.LayThongTinNguoiDung(nd)));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyNhapXuatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
