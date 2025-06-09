package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.ChiTietTonKho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<ChiTietTonKho> LayDSChiTietTonKho(ChiTietTonKho cttk) throws Exception {
        List<ChiTietTonKho> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT ID_KhoHang, ID_SanPham, SoLuong FROM ChiTietTonKho WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (cttk.getIdKhoHang() != null) {
            sql.append(" AND ID_KhoHang = ?");
            params.add(cttk.getIdKhoHang());
        }
        if (cttk.getIdSanPham() != null) {
            sql.append(" AND ID_SanPham = ?");
            params.add(cttk.getIdSanPham());
        }

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ChiTietTonKho ct = new ChiTietTonKho();
                ct.setIdKhoHang(rs.getInt("ID_KhoHang"));
                ct.setIdSanPham(rs.getInt("ID_SanPham"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                list.add(ct);
            }
        }

        return list;
    }
}
