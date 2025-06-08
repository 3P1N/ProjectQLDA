package appquanlykho.NhanVienGUI;

import appquanlykho.Components.MyTable;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.DAO.PhieuNhapXuatDAO;
import appquanlykho.Entity.SanPham;
import appquanlykho.DAO.SanPhamDAO;
import appquanlykho.Entity.ChiTietNhapXuat;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.PhieuNhapXuat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaoPhieuNhapXuatFrame extends JFrame {

    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong;
    private JButton btnThemSanPham;
    private MyTable table;
    private JButton btnTaoPhieu, btnHuy;
    private List<ChiTietNhapXuat> listCTNX;
    private NguoiDung nguoiDung;
    private PhieuNhapXuat phieuNhapXuat;

    public TaoPhieuNhapXuatFrame(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
        phieuNhapXuat = new PhieuNhapXuat();
        phieuNhapXuat.setIdNguoiDung(nguoiDung.getIdNguoiDung());

        if (nguoiDung.getVaiTro().equals("Nhân viên nhập")) {
            setTitle("Tạo phiếu nhập");
            phieuNhapXuat.setLoaiPhieu("Nhap");
        } else {
            setTitle("Tạo phiếu xuất");
            phieuNhapXuat.setLoaiPhieu("Xuat");

        }
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
        loadSanPham();
        listCTNX = new ArrayList<>();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cboSanPham = new JComboBox<>();
        txtSoLuong = new JTextField(5);
        btnThemSanPham = new JButton("Thêm sản phẩm");

        topPanel.add(new JLabel("Sản phẩm:"));
        topPanel.add(cboSanPham);
        topPanel.add(new JLabel("Số lượng:"));
        topPanel.add(txtSoLuong);
        topPanel.add(btnThemSanPham);

        // Table
//        tblModel = new DefaultTableModel(new String[]{"ID", "Tên sản phẩm", "Số lượng", "Đơn vị"}, 0);
//        tblChiTiet = new JTable(tblModel);
        String[] columns = ChiTietNhapXuat.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        table = new MyTable(columns, data);
        JScrollPane scrollPane = new JScrollPane(table);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTaoPhieu = new JButton("Tạo phiếu");
        btnHuy = new JButton("Hủy");
        bottomPanel.add(btnTaoPhieu);
        bottomPanel.add(btnHuy);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Sự kiện
        btnThemSanPham.addActionListener(this::themSanPhamVaoBang);
        btnHuy.addActionListener(e -> dispose());
        btnTaoPhieu.addActionListener(this::taoPhieu);
    }

    private void loadSanPham() {
        try {
            List<SanPham> ds = SanPhamDAO.LayDSSanPham(new SanPham());
            for (SanPham sp : ds) {
                cboSanPham.addItem(sp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải sản phẩm: " + e.getMessage());
        }
    }

    private void themSanPhamVaoBang(ActionEvent e) {
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        String soLuongStr = txtSoLuong.getText().trim();
        if (sp == null || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm và nhập số lượng.");
            return;
        }

        try {
            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                throw new NumberFormatException();
            }
            ChiTietNhapXuat ctnx = new ChiTietNhapXuat();
            ctnx.setIdSanPham(sp.getIdSanPham());
            ctnx.setSoLuong(soLuong);
            listCTNX.add(ctnx);
            HienThiCTNX();
            txtSoLuong.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ.");
        } catch (Exception ex) {
            Logger.getLogger(TaoPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void HienThiCTNX() throws Exception {

        String[] columns = NguoiDung.getTableHeaders();
        Object[][] data = new Object[listCTNX.size()][columns.length];

        for (int i = 0; i < listCTNX.size(); i++) {
            data[i] = listCTNX.get(i).toTableRow();
        }

        table.setTableData(data);
    }

    private void taoPhieu(ActionEvent e) {
        // Xử lý lưu vào DB sẽ làm ở DAO
        int rowCount = table.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu.");
            return;
        }

        try {
            PhieuNhapXuatDAO.TaoPhieuNhapXuat(phieuNhapXuat, listCTNX);
        } catch (SQLException ex) {
            Logger.getLogger(TaoPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaoPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(this, "Phiếu " + " đã được tạo!");
        dispose(); // Hoặc xóa bảng nếu muốn tiếp tục tạo
    }

    public static void main(String[] args) {
        NguoiDung tmp = new NguoiDung();
        tmp.setIdNguoiDung(3);
        try {
            NguoiDung nd = NguoiDungDAO.LayThongTinNguoiDung(tmp);
            if (nd == null) {
                JOptionPane.showMessageDialog(null, "Người dùng không tồn tại!");
                return;
            }

            SwingUtilities.invokeLater(() -> new TaoPhieuNhapXuatFrame(nd).setVisible(true));
        } catch (Exception ex) {
            Logger.getLogger(TaoPhieuNhapXuatFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
