package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.NguoiDung;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {

    public static List<NguoiDung> LayDSNguoiDung(NguoiDung nd) throws Exception {
        List<NguoiDung> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM NguoiDung WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (nd.getIdNguoiDung() != null) {
            sql.append(" AND ID_NguoiDung = ?");
            params.add(nd.getIdNguoiDung());
        }

        if (nd.getTenDangNhap() != null && !nd.getTenDangNhap().isEmpty()) {
            sql.append(" AND TenDangNhap LIKE ?");
            params.add("%" + nd.getTenDangNhap() + "%");
        }

        if (nd.getVaiTro() != null && !nd.getVaiTro().isEmpty()) {
            sql.append(" AND VaiTro = ?");
            params.add(nd.getVaiTro());
        }

        if (nd.getIdKhoHang() != null) {
            sql.append(" AND ID_KhoHang = ?");
            params.add(nd.getIdKhoHang());
        }

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setIdNguoiDung(rs.getInt("ID_NguoiDung"));
            nguoiDung.setTenDangNhap(rs.getString("TenDangNhap"));
            nguoiDung.setMatKhau(rs.getString("MatKhau"));
            nguoiDung.setHoTen(rs.getString("HoTen"));
            nguoiDung.setTrangThaiXoa(rs.getInt("TrangThaiXoa"));
            nguoiDung.setVaiTro(rs.getString("VaiTro"));
            nguoiDung.setIdKhoHang(rs.getObject("ID_KhoHang") != null ? rs.getInt("ID_KhoHang") : null);
            list.add(nguoiDung);
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

    public static NguoiDung LayThongTinNguoiDung(NguoiDung nd) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM NguoiDung WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (nd.getIdNguoiDung() == null ){}
        else{
            sql.append(" AND ID_NguoiDung = ?");
            params.add(nd.getIdNguoiDung());
        }

        if (nd.getTenDangNhap() != null && !nd.getTenDangNhap().isEmpty()) {
            sql.append(" AND TenDangNhap LIKE ?");
            params.add("%" + nd.getTenDangNhap() + "%");
        }

        if (nd.getVaiTro() != null && !nd.getVaiTro().isEmpty()) {
            sql.append(" AND VaiTro = ?");
            params.add(nd.getVaiTro());
        }

        if (nd.getIdKhoHang() != null) {
            sql.append(" AND ID_KhoHang = ?");
            params.add(nd.getIdKhoHang());
        }

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setIdNguoiDung(rs.getInt("ID_NguoiDung"));
            nguoiDung.setTenDangNhap(rs.getString("TenDangNhap"));
            nguoiDung.setMatKhau(rs.getString("MatKhau"));
            nguoiDung.setHoTen(rs.getString("HoTen"));
            nguoiDung.setTrangThaiXoa(rs.getInt("TrangThaiXoa"));
            nguoiDung.setVaiTro(rs.getString("VaiTro"));
            nguoiDung.setIdKhoHang(rs.getObject("ID_KhoHang") != null ? rs.getInt("ID_KhoHang") : null);

            rs.close();
            stmt.close();
            conn.close();

            return nguoiDung;
        }

        rs.close();
        stmt.close();
        conn.close();

        return null;
    }
}
