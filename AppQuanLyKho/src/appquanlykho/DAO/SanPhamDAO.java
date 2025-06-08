package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    public static SanPham LayThongTinSanPham(SanPham sp) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM SanPham WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (sp.getIdSanPham() != null) {
            sql.append(" AND ID_SanPham = ?");
            params.add(sp.getIdSanPham());
        }

        if (sp.getTenSanPham() != null && !sp.getTenSanPham().isEmpty()) {
            sql.append(" AND TenSanPham LIKE ?");
            params.add("%" + sp.getTenSanPham() + "%");
        }

        try (
                Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                SanPham sanPham = new SanPham();
                sanPham.setIdSanPham(rs.getInt("ID_SanPham"));
                sanPham.setTenSanPham(rs.getString("TenSanPham"));
                sanPham.setDonViTinh(rs.getString("DonViTinh"));
                sanPham.setIdLoaiSanPham(rs.getObject("ID_LoaiSanPham") != null ? rs.getInt("ID_LoaiSanPham") : null);
                return sanPham;
            }

            return null;
        }
    }

    public static List<SanPham> LayDSSanPham(SanPham spSearch) throws Exception {
        List<SanPham> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM SanPham WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (spSearch.getIdSanPham() != null) {
            sql.append(" AND ID_SanPham = ?");
            params.add(spSearch.getIdSanPham());
        }

        if (spSearch.getTenSanPham() != null && !spSearch.getTenSanPham().isEmpty()) {
            sql.append(" AND TenSanPham LIKE ?");
            params.add("%" + spSearch.getTenSanPham() + "%");
        }

        if (spSearch.getIdLoaiSanPham() != null) {
            sql.append(" AND ID_LoaiSanPham = ?");
            params.add(spSearch.getIdLoaiSanPham());
        }

        sql.append("ORDER BY ID_SanPham DESC");

        try (
                Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setIdSanPham(rs.getInt("ID_SanPham"));
                sp.setTenSanPham(rs.getString("TenSanPham"));
                sp.setDonViTinh(rs.getString("DonViTinh"));
                sp.setIdLoaiSanPham(rs.getObject("ID_LoaiSanPham") != null ? rs.getInt("ID_LoaiSanPham") : null);
                list.add(sp);
            }
        }

        return list;
    }

    static public void ThemSanPham(SanPham sanPham) throws Exception {
        String sql = "INSERT INTO SanPham (ID_SanPham, TenSanPham, DonViTinh, ID_LoaiSanPham) VALUES (Seq_SanPham.NEXTVAL, ?, ?, ?)";

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, sanPham.getTenSanPham());
        stmt.setString(2, sanPham.getDonViTinh());

        if (sanPham.getIdLoaiSanPham() != null) {
            stmt.setInt(3, sanPham.getIdLoaiSanPham());
        } else {
            stmt.setNull(3, java.sql.Types.INTEGER);
        }

        stmt.executeUpdate();

    }

    static public void SuaSanPham(SanPham sanPham) throws Exception {
        if (sanPham.getIdSanPham() == null) {
            throw new IllegalArgumentException("ID sản phẩm không được null khi cập nhật.");
        }

        StringBuilder sql = new StringBuilder("UPDATE SanPham SET ");
        List<Object> params = new ArrayList<>();

        if (sanPham.getTenSanPham() != null) {
            sql.append("TenSanPham = ?, ");
            params.add(sanPham.getTenSanPham());
        }
        if (sanPham.getDonViTinh() != null) {
            sql.append("DonViTinh = ?, ");
            params.add(sanPham.getDonViTinh());
        }
        if (sanPham.getIdLoaiSanPham() != null) {
            sql.append("ID_LoaiSanPham = ?, ");
            params.add(sanPham.getIdLoaiSanPham());
        }

        // Xóa dấu phẩy cuối cùng nếu có
        if (params.isEmpty()) {
            throw new IllegalArgumentException("Không có thông tin nào để cập nhật.");
        }

        sql.setLength(sql.length() - 2); // Xóa dấu phẩy cuối
        sql.append(" WHERE ID_SanPham = ?");
        params.add(sanPham.getIdSanPham());

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }
        stmt.executeUpdate();

    }

}
