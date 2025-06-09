package appquanlykho.QuanLyGUI;

import appquanlykho.Components.MyTable;
import appquanlykho.DAO.ChiTietTonKhoDAO;
import appquanlykho.DAO.KhoHangDAO;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.Entity.ChiTietTonKho;
import appquanlykho.Entity.KhoHang;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.SanPham;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author HP
 */
public class KiemTraTonKhoPanel extends JPanel {

    private KhoHang khoHang;
    private MyTable table;

    public KiemTraTonKhoPanel(NguoiDung nguoiDung) throws SQLException, ClassNotFoundException, Exception {
        khoHang = new KhoHang();
        khoHang.setIdKhoHang(nguoiDung.getIdKhoHang());
        khoHang = KhoHangDAO.LayThongTinKhoHang(khoHang);
        this.setLayout(new BorderLayout());
        initUI();
        int delay = 600;
        new javax.swing.Timer(delay, e -> {
            try {
                HienThiDSTonKho();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KiemTraTonKhoPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(KiemTraTonKhoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

    }

    private void initUI() {

        // main
        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel lblKho = new JLabel(khoHang.getTenKhoHang());
        mainPanel.add(lblKho, BorderLayout.NORTH);

        String[] columns = ChiTietTonKho.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        table = new MyTable(columns, data);
        mainPanel.add(table, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);

        try {
            HienThiDSTonKho();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KiemTraTonKhoPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KiemTraTonKhoPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void HienThiDSTonKho() throws SQLException, ClassNotFoundException, Exception {
        ChiTietTonKho cttk = new ChiTietTonKho();
        cttk.setIdKhoHang(khoHang.getIdKhoHang());
        HienThiDSTonKho(cttk);
    }

    public void HienThiDSTonKho(ChiTietTonKho cttk) throws SQLException, ClassNotFoundException, Exception {
        java.util.List<ChiTietTonKho> dscttk = ChiTietTonKhoDAO.LayDSChiTietTonKho(cttk);
        String[] columns = ChiTietTonKho.getTableHeaders();
        Object[][] data = new Object[dscttk.size()][columns.length];

        for (int i = 0; i < dscttk.size(); i++) {
            data[i] = dscttk.get(i).toTableRow();
        }

        table.setTableData(data);
        

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Gói Hàng");

            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setIdNguoiDung(3);

            try {
                frame.setContentPane(new KiemTraTonKhoPanel(NguoiDungDAO.LayThongTinNguoiDung(nguoiDung)));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KiemTraTonKhoPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(KiemTraTonKhoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
