package appquanlykho.QuanLyGUI;

import appquanlykho.DAO.LoaiSanPhamDAO;

import appquanlykho.Entity.LoaiSanPham;
import appquanlykho.Entity.PhieuNhapXuat;

import appquanlykho.Entity.SanPham;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopPanelQLPNX extends JPanel {

    private JButton duyetButton = new JButton("Duyệt");
    private JButton filterButton = new JButton("Lọc");
    private JButton huyButton = new JButton("Hủy");
    private JButton refreshButton = new JButton("Refresh");

    private final JTextField idField = new JTextField("");
    private final JComboBox<String> cbLoaiPhieu;
    private final JComboBox<String> cbTrangThai;

    public TopPanelQLPNX() throws SQLException, ClassNotFoundException {

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        // TextField - ID
        idField.setPreferredSize(new Dimension(80, 40));
        idField.setBorder(BorderFactory.createTitledBorder("ID"));
        add(idField);

        // ComboBox - Trạng thái
        String[] dsTrangThai = new String[]{"Tất cả", "Chờ duyệt", "Đã duyệt", "Đã hủy"};
        cbTrangThai = new JComboBox<>(dsTrangThai);
        cbTrangThai.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        add(cbTrangThai);

        String[] dsLoaiPhieu = new String[]{"Tất cả", "Phiếu nhập", "Phiếu xuất"};
        cbLoaiPhieu = new JComboBox<>(dsLoaiPhieu);
        cbLoaiPhieu.setBorder(BorderFactory.createTitledBorder("Loại phiếu"));
        add(cbLoaiPhieu);

        // Button - Lọc (màu xanh đậm)
        filterButton.setPreferredSize(new Dimension(60, 30));
        filterButton.setBackground(new Color(0, 136, 153));
        filterButton.setForeground(Color.WHITE);

        add(filterButton);

        // Button - Thêm mới (màu xanh lá)
        duyetButton.setPreferredSize(new Dimension(100, 30));
        duyetButton.setBackground(new Color(0, 153, 76));
        duyetButton.setForeground(Color.WHITE);

        add(duyetButton);

        huyButton.setPreferredSize(new Dimension(100, 30));
        huyButton.setBackground(new Color(0, 153, 76));
        huyButton.setForeground(Color.WHITE);
        add(huyButton);

        refreshButton.setPreferredSize(new Dimension(150, 30));
        refreshButton.setBackground(new Color(0, 136, 153));
        refreshButton.setForeground(Color.WHITE);
        add(refreshButton);

    }

    public JButton getRefreshButton() {
        return this.refreshButton;
    }

    public JButton getDuyetButton() {
        return this.duyetButton;
    }

    public JButton getHuyButton() {
        return this.huyButton;
    }

    public JButton getfilterButton() {
        return this.filterButton;
    }

 

    public PhieuNhapXuat getPhieuNhapXuat() {
        PhieuNhapXuat phieuNhapXuat = new PhieuNhapXuat();
        String idText = idField.getText().trim();
        if (!idText.isEmpty()) {
            phieuNhapXuat.setIdPhieuNhapXuat(Integer.valueOf(idText));
        } else {
            phieuNhapXuat.setIdPhieuNhapXuat(null);
        }

        String loai = (String) cbLoaiPhieu.getSelectedItem();
        if (loai.equals("Tất cả")) {
            phieuNhapXuat.setLoaiPhieu(null);
        } else if (loai.equals("Phiếu nhập")) {
            phieuNhapXuat.setLoaiPhieu("Nhap");
        } else {
            phieuNhapXuat.setLoaiPhieu("Xuat");

        }

        
        return phieuNhapXuat;
    }

    static public void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lọc đơn hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 120);
            frame.setLocationRelativeTo(null);

            try {
                frame.add(new TopPanelQLPNX());
            } catch (SQLException ex) {
                Logger.getLogger(TopPanelQLPNX.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TopPanelQLPNX.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setVisible(true);
        });
    }
}
