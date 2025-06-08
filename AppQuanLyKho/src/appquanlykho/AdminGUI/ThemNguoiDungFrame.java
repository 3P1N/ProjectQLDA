package appquanlykho.AdminGUI;

import appquanlykho.Entity.KhoHang;
import appquanlykho.DAO.KhoHangDAO;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.Entity.NguoiDung;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ThemNguoiDungFrame extends JFrame {

    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JTextField txtHoTen;
    private JComboBox<String> cboVaiTro;
    private JComboBox<KhoHang> cboKho;
    private JButton btnLuu, btnHuy;

    public ThemNguoiDungFrame() {
        setTitle("Thêm người dùng");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadKhoHang();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập:");
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        JLabel lblHoTen = new JLabel("Họ tên:");
        JLabel lblVaiTro = new JLabel("Vai trò:");
        JLabel lblKho = new JLabel("Kho hàng:");

        txtTenDangNhap = new JTextField();
        txtMatKhau = new JPasswordField();
        txtHoTen = new JTextField();

        cboVaiTro = new JComboBox<>(new String[]{"Admin", "Nhân viên nhập", "Nhân viên xuất", "Quản lý"});
        cboKho = new JComboBox<>();

        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        panel.add(lblTenDangNhap);
        panel.add(txtTenDangNhap);
        panel.add(lblMatKhau);
        panel.add(txtMatKhau);
        panel.add(lblHoTen);
        panel.add(txtHoTen);
        panel.add(lblVaiTro);
        panel.add(cboVaiTro);
        panel.add(lblKho);
        panel.add(cboKho);
        panel.add(btnLuu);
        panel.add(btnHuy);

        add(panel);

        btnLuu.addActionListener(this::luuNguoiDung);
        btnHuy.addActionListener(e -> dispose());
    }

    private void loadKhoHang() {
        try {
            List<KhoHang> dsKho = KhoHangDAO.LayDSKhoHang(new KhoHang());
            for (KhoHang kho : dsKho) {
                cboKho.addItem(kho);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách kho: " + e.getMessage());
        }
    }

    private void luuNguoiDung(ActionEvent e) {
        try {
            NguoiDung nd = new NguoiDung();
            nd.setTenDangNhap(txtTenDangNhap.getText().trim());
            nd.setMatKhau(new String(txtMatKhau.getPassword()));
            nd.setHoTen(txtHoTen.getText().trim());
            nd.setVaiTro((String) cboVaiTro.getSelectedItem());
            KhoHang selectedKho = (KhoHang) cboKho.getSelectedItem();
            nd.setIdKhoHang(selectedKho != null ? selectedKho.getIdKhoHang() : null);
            nd.setTrangThaiXoa(0); // Mặc định chưa bị xóa

            NguoiDungDAO.ThemNguoiDung(nd);
            JOptionPane.showMessageDialog(this, "Đã tạo người dùng thành công!");

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu người dùng: " + ex.getMessage());
        }
    }
    
    static public void main(String[] args){
        SwingUtilities.invokeLater(() -> new ThemNguoiDungFrame().setVisible(true));
    }
}
