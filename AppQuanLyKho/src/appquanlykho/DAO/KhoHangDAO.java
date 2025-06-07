package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.KhoHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KhoHangDAO {

    public static List<KhoHang> LayDSKhoHang(KhoHang khoSearch) throws Exception {
        List<KhoHang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM KhoHang WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (khoSearch.getIdKhoHang() != null) {
            sql.append(" AND ID_KhoHang = ?");
            params.add(khoSearch.getIdKhoHang());
        }

        if (khoSearch.getTenKhoHang() != null && !khoSearch.getTenKhoHang().isEmpty()) {
            sql.append(" AND TenKhoHang LIKE ?");
            params.add("%" + khoSearch.getTenKhoHang() + "%");
        }

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            KhoHang kho = new KhoHang();
            kho.setIdKhoHang(rs.getInt("ID_KhoHang"));
            kho.setTenKhoHang(rs.getString("TenKhoHang"));
            kho.setSoLuongToiDa(rs.getObject("SoLuongToiDa") != null ? rs.getInt("SoLuongToiDa") : null);
            kho.setSoLuongTonKho(rs.getObject("SoLuongTonKho") != null ? rs.getInt("SoLuongTonKho") : null);
            list.add(kho);
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

    public static KhoHang LayThongTinKhoHang(KhoHang khoSearch) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM KhoHang WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (khoSearch.getIdKhoHang() != null) {
            sql.append(" AND ID_KhoHang = ?");
            params.add(khoSearch.getIdKhoHang());
        }

        if (khoSearch.getTenKhoHang() != null && !khoSearch.getTenKhoHang().isEmpty()) {
            sql.append(" AND TenKhoHang LIKE ?");
            params.add("%" + khoSearch.getTenKhoHang() + "%");
        }

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        ResultSet rs = stmt.executeQuery();
        KhoHang kho = null;
        if (rs.next()) {
            kho = new KhoHang();
            kho.setIdKhoHang(rs.getInt("ID_KhoHang"));
            kho.setTenKhoHang(rs.getString("TenKhoHang"));
            kho.setSoLuongToiDa(rs.getObject("SoLuongToiDa") != null ? rs.getInt("SoLuongToiDa") : null);
            kho.setSoLuongTonKho(rs.getObject("SoLuongTonKho") != null ? rs.getInt("SoLuongTonKho") : null);
        }

        rs.close();
        stmt.close();
        conn.close();

        return kho;
    }
}
