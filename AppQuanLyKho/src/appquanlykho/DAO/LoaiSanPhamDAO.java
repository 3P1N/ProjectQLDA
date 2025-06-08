package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.LoaiSanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDAO {

    public static List<LoaiSanPham> LayDSLoaiSanPham(LoaiSanPham loaiSearch) throws Exception {
        List<LoaiSanPham> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM LoaiSanPham WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (loaiSearch.getIdLoaiSanPham() != null) {
            sql.append(" AND ID_LoaiSanPham = ?");
            params.add(loaiSearch.getIdLoaiSanPham());
        }

        if (loaiSearch.getTenLoaiSanPham() != null && !loaiSearch.getTenLoaiSanPham().isEmpty()) {
            sql.append(" AND TenLoaiSanPham LIKE ?");
            params.add("%" + loaiSearch.getTenLoaiSanPham() + "%");
        }

        try (
            Connection conn = ConnectionUtils.getMyConnection();
            PreparedStatement stmt = conn.prepareStatement(sql.toString())
        ) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LoaiSanPham lsp = new LoaiSanPham();
                lsp.setIdLoaiSanPham(rs.getInt("ID_LoaiSanPham"));
                lsp.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
                list.add(lsp);
            }
        }

        return list;
    }
    
    public static LoaiSanPham LayThongTinLoaiSanPham(LoaiSanPham loaiSearch) throws Exception {
        
        StringBuilder sql = new StringBuilder("SELECT * FROM LoaiSanPham WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (loaiSearch.getIdLoaiSanPham() != null) {
            sql.append(" AND ID_LoaiSanPham = ?");
            params.add(loaiSearch.getIdLoaiSanPham());
        }

        if (loaiSearch.getTenLoaiSanPham() != null && !loaiSearch.getTenLoaiSanPham().isEmpty()) {
            sql.append(" AND TenLoaiSanPham LIKE ?");
            params.add("%" + loaiSearch.getTenLoaiSanPham() + "%");
        }

        try (
            Connection conn = ConnectionUtils.getMyConnection();
            PreparedStatement stmt = conn.prepareStatement(sql.toString())
        ) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LoaiSanPham lsp = new LoaiSanPham();
                lsp.setIdLoaiSanPham(rs.getInt("ID_LoaiSanPham"));
                lsp.setTenLoaiSanPham(rs.getString("TenLoaiSanPham"));
                return lsp;
            }
        }

        return null;
    }
}
