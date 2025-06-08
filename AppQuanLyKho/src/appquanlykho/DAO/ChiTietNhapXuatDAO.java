package appquanlykho.DAO;

import appquanlykho.Entity.ChiTietNhapXuat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
}
