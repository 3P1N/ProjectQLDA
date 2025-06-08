package appquanlykho.QuanLyGUI;

import appquanlykho.DAO.LoaiSanPhamDAO;
import appquanlykho.DAO.SanPhamDAO;
import appquanlykho.Entity.LoaiSanPham;
import appquanlykho.Entity.SanPham;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SuaSanPhamFrame extends JFrame {

    private JTextField txtTenDangNhap;
    private JTextField txtTenSanPham;
    private JTextField txtDonViTinh;
    private JComboBox<LoaiSanPham> cbLoaiSP;
    private JButton btnLuu, btnHuy;
    private SanPham sanPham;

    public SuaSanPhamFrame(int idSanPham) {
        setTitle("Sửa người dùng");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        sanPham = new SanPham();
        sanPham.setIdSanPham(idSanPham);
        try {
            sanPham = SanPhamDAO.LayThongTinSanPham(sanPham);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải thông tin người dùng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        initUI();
        loadLoaiSanPham();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTenSanPham = new JLabel("Tên sản phẩm:");
        JLabel lblDonViTinh = new JLabel("Đơn vị tính:");
        JLabel lblLoaiSP = new JLabel("Loại sản phẩm:");

        txtTenSanPham = new JTextField(sanPham.getTenSanPham());
        txtDonViTinh = new JTextField(sanPham.getDonViTinh());

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

                if (sanPham.getIdLoaiSanPham() != null && lsp.getIdLoaiSanPham().equals(sanPham.getIdLoaiSanPham())) {
                    cbLoaiSP.setSelectedItem(lsp);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách loại sản phẩm: " + e.getMessage());
        }
    }

    private void luuSanPham(ActionEvent e) {
        try {
            sanPham.setTenSanPham(txtTenSanPham.getText().trim());
            sanPham.setDonViTinh(txtDonViTinh.getText().trim());
            LoaiSanPham loai = (LoaiSanPham) cbLoaiSP.getSelectedItem();
            sanPham.setIdLoaiSanPham(loai.getIdLoaiSanPham());
            
            SanPhamDAO.SuaSanPham(sanPham);
            JOptionPane.showMessageDialog(this, "Đã sửa sản phẩm thành công!");

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu sản phẩm: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Ví dụ chạy sửa người dùng với ID = 1
        SwingUtilities.invokeLater(() -> new SuaSanPhamFrame(1).setVisible(true));
    }
}
