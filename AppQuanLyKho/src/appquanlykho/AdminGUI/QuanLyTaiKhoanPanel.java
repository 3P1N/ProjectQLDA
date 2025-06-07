
package appquanlykho.AdminGUI;

import appquanlykho.Components.MyTable;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.Entity.NguoiDung;
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

public class QuanLyTaiKhoanPanel extends JPanel {

    private TopPanelQLTK topPanel;
    private MyTable table;


    public QuanLyTaiKhoanPanel() throws SQLException, ClassNotFoundException, Exception {
        this.setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() throws SQLException, ClassNotFoundException, Exception {
        // Panel Menu
        topPanel = new TopPanelQLTK();
        // main
        JPanel mainPanel = new JPanel(new BorderLayout());

        // thanh filter
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = NguoiDung.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        table = new MyTable(columns, data);
        mainPanel.add(table, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);

//        topPanel.getaddButton().addActionListener(e -> {
//            try {
//                ThemGoiHang();
//            } catch (SQLException ex) {
//                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (Exception ex) {
//                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });

        topPanel.getfilterButton().addActionListener(e->{
            try {
                XuLyTraCuu();
            } catch (Exception ex) {
                Logger.getLogger(QuanLyTaiKhoanPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        

        HienThiDSNguoiDung();
    }
    
    void XuLyTraCuu() throws ClassNotFoundException, Exception{
        NguoiDung nguoiDung = topPanel.getNguoiDung();
        HienThiDSNguoiDung(nguoiDung);
    }

    void HienThiDSNguoiDung() throws SQLException, ClassNotFoundException, Exception {
        
        HienThiDSNguoiDung(new NguoiDung());
    }

    void HienThiDSNguoiDung(NguoiDung nguoiDung) throws SQLException, ClassNotFoundException, Exception {
        java.util.List<NguoiDung> dsNguoiDung = NguoiDungDAO.LayDSNguoiDung(nguoiDung);
        String[] columns = NguoiDung.getTableHeaders();
        Object[][] data = new Object[dsNguoiDung.size()][columns.length];

        for (int i = 0; i < dsNguoiDung.size(); i++) {
            data[i] = dsNguoiDung.get(i).toTableRow();
        }

        table.setTableData(data);
    }
//
//    public void ThemGoiHang() throws SQLException, ClassNotFoundException {
//        ThemGoiHangFrame themGoiHang = new ThemGoiHangFrame(() -> {
//            try {
//                HienThiDSGoiHangTheoKho();
//            } catch (SQLException ex) {
//                Logger.getLogger(QuanLyGoiHangPanel.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(QuanLyGoiHangPanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }, nhanVienKho);
//        themGoiHang.setVisible(true);
//    }
//    
//    public void XuLyHoanThanhGoiHang() throws SQLException, ClassNotFoundException{
//        for (int i = 0; i < listOrder.getRowCount(); i++) {
//            Boolean isChecked = (Boolean) listOrder.getValueAt(i, 0); // Cột 0 là checkbox
//            if (Boolean.TRUE.equals(isChecked)) {
//                // Lấy thông tin dòng được chọn
//                Integer maGoiHang = (Integer) listOrder.getValueAt(i, 1); // cột 1: mã ĐH
//                System.out.println(maGoiHang);
//
//                // Gọi hàm xử lý
//                controller.HoanThanhGoiHang(maGoiHang);
//                JOptionPane.showMessageDialog(this, "Xác nhận gói hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                HienThiDSGoiHangTheoKho();
//            }
//        }
//    }
//    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Gói Hàng");
            
            try {
                frame.setContentPane(new QuanLyTaiKhoanPanel());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyTaiKhoanPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyTaiKhoanPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
