package appquanlykho.QuanLyGUI;

import appquanlykho.DAO.LoaiSanPhamDAO;
import appquanlykho.DAO.SanPhamDAO;
import appquanlykho.Entity.LoaiSanPham;
import appquanlykho.Entity.SanPham;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ThemSanPhamFrame extends JFrame {

    private JTextField txtTenSanPham;
    private JTextField txtDonViTinh;
    private JComboBox<LoaiSanPham> cbLoaiSP;
    private JButton btnLuu, btnHuy;

    public ThemSanPhamFrame() {
        setTitle("Thêm sản phẩm");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadLoaiSanPham();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTenSanPham = new JLabel("Tên sản phẩm:");
        JLabel lblDonViTinh = new JLabel("Đơn vị tính:");
        JLabel lblLoaiSP = new JLabel("Loại sản phẩm:");

        txtTenSanPham = new JTextField();
        txtDonViTinh = new JTextField();

        cbLoaiSP = new JComboBox<>();

        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");

        panel.add(lblTenSanPham);
        panel.add(txtTenSanPham);
        panel.add(lblDonViTinh);
        panel.add(txtDonViTinh);
        panel.add(lblLoaiSP);
        panel.add(cbLoaiSP);

        panel.add(btnLuu);
        panel.add(btnHuy);

        add(panel);

        btnLuu.addActionListener(this::luuSanPham);
        btnHuy.addActionListener(e -> dispose());
    }

    private void loadLoaiSanPham() {
        try {
            List<LoaiSanPham> dsLoaiSP = LoaiSanPhamDAO.LayDSLoaiSanPham(new LoaiSanPham());
            for (LoaiSanPham lsp : dsLoaiSP) {
                cbLoaiSP.addItem(lsp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách loại sản phẩm: " + e.getMessage());
        }
    }

    private void luuSanPham(ActionEvent e) {
        try {
            SanPham sp = new SanPham();
            sp.setTenSanPham(txtTenSanPham.getText().trim());
            sp.setDonViTinh(txtDonViTinh.getText().trim());

            LoaiSanPham loai = (LoaiSanPham) cbLoaiSP.getSelectedItem();

            sp.setIdLoaiSanPham(loai.getIdLoaiSanPham());

            SanPhamDAO.ThemSanPham(sp);
            JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm thành công!");

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu sản phẩm: " + ex.getMessage());
        }
    }

    static public void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThemSanPhamFrame().setVisible(true));
    }
}
