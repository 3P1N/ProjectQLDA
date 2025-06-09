package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.ChiTietNhapXuat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ChiTietNhapXuatDAO {

    public static void ThemDSChiTietNhapXuat(Connection conn, List<ChiTietNhapXuat> list) throws SQLException {
        for (ChiTietNhapXuat ctnx : list) {
            ThemChiTietNhapXuat(conn, ctnx);
        }
    }

    public static void ThemChiTietNhapXuat(Connection conn, ChiTietNhapXuat ctnx) throws SQLException {
        String sql = "INSERT INTO ChiTietNhapXuat(ID_PhieuNhapXuat, ID_SanPham, SoLuong) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ctnx.getIdPhieuNhapXuat());
            stmt.setInt(2, ctnx.getIdSanPham());
            stmt.setInt(3, ctnx.getSoLuong());
            stmt.executeUpdate();
        }
    }

    public static List<ChiTietNhapXuat> LayDSChiTietNhapXuat(ChiTietNhapXuat ctnx) throws SQLException, ClassNotFoundException {
        List<ChiTietNhapXuat> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT ID_PhieuNhapXuat, ID_SanPham, SoLuong FROM ChiTietNhapXuat WHERE 1=1");

        if (ctnx.getIdPhieuNhapXuat() != null) {
            sql.append(" AND ID_PhieuNhapXuat = ?");
        }
        if (ctnx.getIdSanPham() != null) {
            sql.append(" AND ID_SanPham = ?");
        }

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (ctnx.getIdPhieuNhapXuat() != null) {
                stmt.setInt(paramIndex++, ctnx.getIdPhieuNhapXuat());
            }
            if (ctnx.getIdSanPham() != null) {
                stmt.setInt(paramIndex++, ctnx.getIdSanPham());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietNhapXuat ct = new ChiTietNhapXuat();
                    ct.setIdPhieuNhapXuat(rs.getInt("ID_PhieuNhapXuat"));
                    ct.setIdSanPham(rs.getInt("ID_SanPham"));
                    ct.setSoLuong(rs.getInt("SoLuong"));
                    list.add(ct);
                }
            }
        }

        return list;
    }

}
