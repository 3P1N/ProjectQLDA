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
        StringBuilder sql = new StringBuilder("SELECT * FROM NguoiDung WHERE TrangThaiXoa = 0 ");
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
        sql.append("ORDER BY ID_NguoiDung DESC");

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
        StringBuilder sql = new StringBuilder("SELECT * FROM NguoiDung WHERE TrangThaiXoa=0 ");
        List<Object> params = new ArrayList<>();

        if (nd.getIdNguoiDung() == null) {
        } else {
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

    public static void ThemNguoiDung(NguoiDung nguoiDung) throws Exception {
        String sql = "INSERT INTO NguoiDung (ID_NguoiDung, TenDangNhap, MatKhau, HoTen, TrangThaiXoa, VaiTro, ID_KhoHang) "
                + "VALUES (SEQ_NGUOIDUNG.NEXTVAL, ?, ?, ?, NULL, ?, ?)";

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, nguoiDung.getTenDangNhap());
        stmt.setString(2, nguoiDung.getMatKhau());
        stmt.setString(3, nguoiDung.getHoTen());
        stmt.setString(4, nguoiDung.getVaiTro());

        if (nguoiDung.getIdKhoHang() != null) {
            stmt.setInt(5, nguoiDung.getIdKhoHang());
        } else {
            stmt.setNull(5, java.sql.Types.INTEGER);
        }

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public static void SuaNguoiDung(NguoiDung nguoiDung) throws Exception {
        if (nguoiDung.getIdNguoiDung() == null) {
            throw new IllegalArgumentException("ID người dùng không được null.");
        }

        StringBuilder sql = new StringBuilder("UPDATE NguoiDung SET ");
        List<Object> params = new ArrayList<>();
        List<String> fields = new ArrayList<>();

        if (nguoiDung.getTenDangNhap() != null) {
            fields.add("TenDangNhap = ?");
            params.add(nguoiDung.getTenDangNhap());
        }

        if (nguoiDung.getMatKhau() != null) {
            fields.add("MatKhau = ?");
            params.add(nguoiDung.getMatKhau());
        }

        if (nguoiDung.getHoTen() != null) {
            fields.add("HoTen = ?");
            params.add(nguoiDung.getHoTen());
        }

        if (nguoiDung.getVaiTro() != null) {
            fields.add("VaiTro = ?");
            params.add(nguoiDung.getVaiTro());
        }

        if (nguoiDung.getIdKhoHang() != null) {
            fields.add("ID_KhoHang = ?");
            params.add(nguoiDung.getIdKhoHang());
        }

        if (fields.isEmpty()) {
            throw new IllegalArgumentException("Không có dữ liệu nào để cập nhật.");
        }

        sql.append(String.join(", ", fields));
        sql.append(" WHERE ID_NguoiDung = ?");
        params.add(nguoiDung.getIdNguoiDung());

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql.toString());
        

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        int affected = stmt.executeUpdate();
        if (affected == 0) {
            throw new Exception("Không tìm thấy người dùng để cập nhật.");
        }

    }

    public static void XoaNguoiDung(Integer idNguoiDung) throws Exception {
        if (idNguoiDung == null) {
            return;
        }

        String sql = "UPDATE NguoiDung SET TrangThaiXoa = 1 WHERE ID_NguoiDung = ?";

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idNguoiDung);

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

}
