package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.ChiTietTonKho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChiTietTonKhoDAO {

    public static ChiTietTonKho LayThongTinTonKho(ChiTietTonKho cttk) throws ClassNotFoundException, SQLException {
        Connection con = ConnectionUtils.getMyConnection();
        // bạn cần có class Database với phương thức connect()
        String sql = "SELECT SoLuong FROM ChiTietTonKho WHERE ID_KhoHang = ? AND ID_SanPham = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, cttk.getIdKhoHang());
        ps.setInt(2, cttk.getIdSanPham());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            cttk.setSoLuong(rs.getInt("SoLuong"));
        } else {
            // Nếu không có dữ liệu thì gán số lượng = 0 hoặc xử lý tùy logic
            cttk.setSoLuong(0);
        }

        rs.close();
        ps.close();
        con.close();

        return cttk;
    }
}
