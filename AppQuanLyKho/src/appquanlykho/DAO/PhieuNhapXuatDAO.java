package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.ChiTietNhapXuat;
import appquanlykho.Entity.PhieuNhapXuat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapXuatDAO {

    public static void TaoPhieuNhapXuat(PhieuNhapXuat phieuNhapXuat, List<ChiTietNhapXuat> list) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionUtils.getMyConnection();
            conn.setAutoCommit(false);

            // 1. Lấy ID tiếp theo từ Sequence
            stmt = conn.prepareStatement("SELECT SEQ_PhieuNhapXuat.NEXTVAL FROM dual");
            rs = stmt.executeQuery();
            int newId = -1;
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            phieuNhapXuat.setIdPhieuNhapXuat(newId);

            // 2. Insert Phiếu nhập xuất
            String sql = "INSERT INTO PhieuNhapXuat(ID_PhieuNhapXuat, ID_NguoiDung, LoaiPhieu) "
                    + "VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newId);
            stmt.setInt(2, phieuNhapXuat.getIdNguoiDung());
            stmt.setString(3, phieuNhapXuat.getLoaiPhieu());
            stmt.executeUpdate();

            // 3. Gán ID vào từng chi tiết và insert
            for (ChiTietNhapXuat ctnx : list) {
                ctnx.setIdPhieuNhapXuat(newId);
            }
            ChiTietNhapXuatDAO.ThemDSChiTietNhapXuat(conn, list);

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Tạo phiếu nhập/xuất thất bại", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    static public List<PhieuNhapXuat> LayDSPhieuNhapXuat(PhieuNhapXuat phieuNhapXuat) throws SQLException, ClassNotFoundException {
        List<PhieuNhapXuat> list = new ArrayList<>();
        Connection conn = ConnectionUtils.getMyConnection();

        // Câu truy vấn cơ bản, thêm điều kiện WHERE nếu có giá trị
        String sql = "SELECT ID_PhieuNhapXuat, ID_NguoiDung, NgayTao, TrangThai, LoaiPhieu FROM PhieuNhapXuat WHERE 1=1";

        // Tạo list tham số để set vào PreparedStatement
        List<Object> params = new ArrayList<>();

        // Nếu phieuNhapXuat != null thì thêm điều kiện tương ứng
        if (phieuNhapXuat != null) {
            if (phieuNhapXuat.getIdNguoiDung() != null) {
                sql += " AND ID_NguoiDung = ?";
                params.add(phieuNhapXuat.getIdNguoiDung());
            }
            if (phieuNhapXuat.getNgayTao() != null) {
                sql += " AND NgayTao = ?";
                params.add(phieuNhapXuat.getNgayTao());
            }
            if (phieuNhapXuat.getTrangThai() != null && !phieuNhapXuat.getTrangThai().isEmpty()) {
                sql += " AND TrangThai = ?";
                params.add(phieuNhapXuat.getTrangThai());
            }
            if (phieuNhapXuat.getLoaiPhieu() != null && !phieuNhapXuat.getLoaiPhieu().isEmpty()) {
                sql += " AND LoaiPhieu = ?";
                params.add(phieuNhapXuat.getLoaiPhieu());
            }
        }

        PreparedStatement stmt = conn.prepareStatement(sql);

        // Set tham số tương ứng
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof Integer) {
                stmt.setInt(i + 1, (Integer) param);
            } else if (param instanceof java.sql.Date) {
                stmt.setDate(i + 1, (java.sql.Date) param);
            } else if (param instanceof String) {
                stmt.setString(i + 1, (String) param);
            } else {
                stmt.setObject(i + 1, param);
            }
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            PhieuNhapXuat pnx = new PhieuNhapXuat();
            pnx.setIdPhieuNhapXuat(rs.getInt("ID_PhieuNhapXuat"));
            pnx.setIdNguoiDung(rs.getInt("ID_NguoiDung"));
            pnx.setNgayTao(rs.getDate("NgayTao"));
            pnx.setTrangThai(rs.getString("TrangThai"));
            pnx.setLoaiPhieu(rs.getString("LoaiPhieu"));
            list.add(pnx);
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

}
