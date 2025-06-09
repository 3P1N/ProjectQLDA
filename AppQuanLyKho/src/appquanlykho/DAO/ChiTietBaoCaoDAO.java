/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appquanlykho.DAO;

import appquanlykho.Entity.ChiTietBaoCao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author HP
 */
public class ChiTietBaoCaoDAO {
    public static void ThemDSChiTietBaoCao(Connection conn, List<ChiTietBaoCao> list) throws SQLException {
        for (ChiTietBaoCao ctbc : list) {
            ThemChiTietBaoCao(conn, ctbc);
        }
    }

    public static void ThemChiTietBaoCao(Connection conn, ChiTietBaoCao ctbc) throws SQLException {
        String sql = "INSERT INTO ChiTietBaoCao(ID_BaoCao, ID_SanPham, SoLuong) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ctbc.getIdBaoCao());
            stmt.setInt(2, ctbc.getIdSanPham());
            stmt.setInt(3, ctbc.getSoLuong());

            stmt.executeUpdate();
        }
    }
}
