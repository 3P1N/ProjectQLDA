package appquanlykho.QuanLyGUI;

import appquanlykho.NhanVienGUI.*;
import appquanlykho.Components.Sidebar;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.Entity.NguoiDung;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author HP
 */
public class QuanLySidebar extends Sidebar {

    private static final List<String> items = Arrays.asList("Quản lý phiếu nhập xuất", "Kiểm tra tồn kho", "Quản lý sản phẩm", "Lập phiếu kiểm kê", "Báo cáo", "Đăng xuất");
    private static final List<String> icons = Arrays.asList("order.png","kho.png", "product.png", "kho.png","report.png", "logout.png");

    public QuanLySidebar(NguoiDung nguoiDung) throws SQLException, ClassNotFoundException {
        super(items, icons, nguoiDung);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test MenuBar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700); // Kích thước của JFrame
            frame.setLocationRelativeTo(null);

            QuanLySidebar sidebar = null;

            NguoiDung nd = new NguoiDung();
            nd.setIdNguoiDung(1);
            try {
                nd = NguoiDungDAO.LayThongTinNguoiDung(nd);
                sidebar = new QuanLySidebar(nd);
            } catch (SQLException ex) {
                Logger.getLogger(QuanLySidebar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLySidebar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLySidebar.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setLayout(new BorderLayout());
            frame.add(sidebar, BorderLayout.WEST);  // Menu sẽ được đặt ở bên trái

            frame.setVisible(true);
        });
    }

}
