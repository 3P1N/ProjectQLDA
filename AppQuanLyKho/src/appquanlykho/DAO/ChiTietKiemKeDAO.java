package appquanlykho.DAO;

import appquanlykho.Entity.ChiTietKiemKe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChiTietKiemKeDAO {

    public static void ThemDSChiTietKiemKe(Connection conn, List<ChiTietKiemKe> list) throws SQLException {
        for (ChiTietKiemKe ctkk : list) {
            ThemChiTietKiemKe(conn, ctkk);
        }
    }

    public static void ThemChiTietKiemKe(Connection conn, ChiTietKiemKe ctkk) throws SQLException {
        String sql = "INSERT INTO ChiTietKiemKe(ID_PhieuKiemKe, ID_SanPham, SoLuongHeThong, SoLuongThucTe, ChenhLech) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ctkk.getIdPhieuKiemKe());
            stmt.setInt(2, ctkk.getIdSanPham());
            stmt.setInt(3, ctkk.getSoLuongHeThong());
            stmt.setInt(4, ctkk.getSoLuongThucTe());
            stmt.setInt(5, ctkk.getChenhLech());

            stmt.executeUpdate();
        }
    }
}
