package appquanlykho.QuanLyGUI;

import appquanlykho.Components.MyTable;
import appquanlykho.DAO.ChiTietTonKhoDAO;
import appquanlykho.DAO.NguoiDungDAO;
import appquanlykho.DAO.PhieuKiemKeDAO;

import appquanlykho.DAO.SanPhamDAO;
import appquanlykho.Entity.ChiTietKiemKe;
import appquanlykho.Entity.ChiTietTonKho;
import appquanlykho.Entity.NguoiDung;
import appquanlykho.Entity.PhieuKiemKe;
import appquanlykho.Entity.SanPham;
import appquanlykho.NhanVienGUI.QuanLyNhapXuatPanel;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LapPhieuKiemKePanel extends JPanel {

    private JTable tblKiemKe;
    private MyTable myTable;

    private DefaultTableModel tblModel;
    private JButton btnThemSanPham, btnLuuPhieu;
    private JLabel lblNgayTao;

    private JTextField txtNgayTao;
    private JComboBox cbSanPham;
    private List<ChiTietKiemKe> listCTKK;
    private PhieuKiemKe phieuKiemKe;
    private NguoiDung nguoiDung;

    public LapPhieuKiemKePanel(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
        phieuKiemKe = new PhieuKiemKe();
        phieuKiemKe.setIdNguoiDung(nguoiDung.getIdNguoiDung());
        setLayout(new BorderLayout());
        listCTKK = new ArrayList<>();
        initUI();
        // Top panel chứa ngày tạo và các nút

        // Bảng kiểm kê
        loadSanPham();
        // Sự kiện nút Thêm, Xóa, Lưu bạn sẽ xử lý tiếp tùy logic
    }

    public void initUI() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblNgayTao = new JLabel("Ngày tạo:");
        txtNgayTao = new JTextField(10);
        txtNgayTao.setText(java.time.LocalDate.now().toString());
        txtNgayTao.setFocusable(false);

        btnThemSanPham = new JButton("Thêm sản phẩm");
        btnLuuPhieu = new JButton("Lưu phiếu kiểm kê");
        
        cbSanPham = new JComboBox<>();
        topPanel.add(lblNgayTao);
        topPanel.add(txtNgayTao);
        topPanel.add(cbSanPham);
        topPanel.add(btnThemSanPham);
        topPanel.add(btnLuuPhieu);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = ChiTietKiemKe.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        myTable = new MyTable(columns, data);

        add(new JScrollPane(myTable), BorderLayout.CENTER);
        btnThemSanPham.addActionListener(this::themSanPhamVaoBang);
        btnLuuPhieu.addActionListener(this::taoPhieu);

    }

    private void taoPhieu(ActionEvent e) {
        // Xử lý lưu vào DB sẽ làm ở DAO
        int rowCount = myTable.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào trong phiếu.");
            return;
        }

        try {
            PhieuKiemKeDAO.TaoPhieuKiemKe(phieuKiemKe, listCTKK);
            JOptionPane.showMessageDialog(this, "Phiếu đã được tạo! Đã cập nhật lại theo số lượng thực tế!");
            // Hoặc xóa bảng nếu muốn tiếp tục tạo
            resetForm();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LapPhieuKiemKePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LapPhieuKiemKePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LapPhieuKiemKePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void resetForm() throws Exception {
        // Reset lại danh sách kiểm kê
        listCTKK.clear();

        // giữ nguyên người dùng
        txtNgayTao.setText(java.time.LocalDate.now().toString());

        // Reset lại table
        HienThiCTKK();

    }

    private void loadSanPham() {
        try {
            java.util.List<SanPham> ds = SanPhamDAO.LayDSSanPham(new SanPham());
            for (SanPham sp : ds) {
                cbSanPham.addItem(sp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải sản phẩm: " + e.getMessage());
        }
    }

    // Hàm thêm dòng mới (từ DAO hoặc dialog chọn sản phẩm)
    public void themSanPhamVaoBang(ActionEvent e) {
        SanPham sp = (SanPham) cbSanPham.getSelectedItem();
        for (ChiTietKiemKe ctkk : listCTKK) {
            if (Objects.equals(ctkk.getIdSanPham(), sp.getIdSanPham())) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã được thêm, vui lòng chọn sản phẩm khác!");
                return;
            }
        }

        //Lấy thông tin số lượng tồn kho
        ChiTietTonKho cttk = new ChiTietTonKho();
        cttk.setIdKhoHang(nguoiDung.getIdKhoHang());
        cttk.setIdSanPham(sp.getIdSanPham());
        try {
            cttk = ChiTietTonKhoDAO.LayThongTinTonKho(cttk);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LapPhieuKiemKePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LapPhieuKiemKePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ChiTietKiemKe ctkk = new ChiTietKiemKe();
        ctkk.setIdSanPham(sp.getIdSanPham());
        ctkk.setSoLuongHeThong(cttk.getSoLuong()); // 
        listCTKK.add(ctkk);
        try {
            HienThiCTKK();
        } catch (Exception ex) {
            Logger.getLogger(LapPhieuKiemKePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void HienThiCTKK() throws Exception {
        String[] columns = ChiTietKiemKe.getTableHeaders();
        Object[][] data = new Object[listCTKK.size()][columns.length];

        for (int i = 0; i < listCTKK.size(); i++) {
            data[i] = listCTKK.get(i).toTableRow();
        }

        myTable.table.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // chỉ cho sửa cột "Số lượng thực tế"
            }
        });
        myTable.table.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 3 && row >= 0) { // Cột SL Thực tế
                try {

                    int slThucTe = Integer.parseInt(myTable.table.getValueAt(row, 3).toString());
                    int slHeThong = Integer.parseInt(myTable.table.getValueAt(row, 2).toString());
                    int chenhLech = slThucTe - slHeThong;
                    System.out.println("here");

                    myTable.table.setValueAt(chenhLech, row, 4);
                    // Cập nhật listCTKK
                    ChiTietKiemKe ct = listCTKK.get(row);
                    ct.setSoLuongThucTe(slThucTe); // tự cập nhật chênh lệch
                    ct.setChenhLech(chenhLech);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!");
                }
            }
        });
        myTable.setTableData(data);

    }
    
    

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lập phiếu kiểm kê");
            NguoiDung nd = new NguoiDung();
            nd.setIdNguoiDung(3);
            try {
                frame.setContentPane(new LapPhieuKiemKePanel(NguoiDungDAO.LayThongTinNguoiDung(nd)));
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
