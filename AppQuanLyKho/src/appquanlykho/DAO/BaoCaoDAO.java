/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appquanlykho.DAO;

import appquanlykho.ConnectDB.ConnectionUtils;
import appquanlykho.Entity.BaoCao;
import appquanlykho.Entity.ChiTietBaoCao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author HP
 */
public class BaoCaoDAO {
    public static void TaoBaoCao(BaoCao baoCao, List<ChiTietBaoCao> list) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionUtils.getMyConnection();
            conn.setAutoCommit(false);

            // 1. Lấy ID tiếp theo từ Sequence
            stmt = conn.prepareStatement("SELECT SEQ_BaoCao.NEXTVAL FROM dual");
            rs = stmt.executeQuery();
            int newId = -1;
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            baoCao.setIdBaoCao(newId);

            // 2. Insert Phiếu nhập xuất
            String sql = "INSERT INTO BaoCao(ID_BaoCao, ID_NguoiDung) "
                    + "VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newId);
            stmt.setInt(2, baoCao.getIdNguoiDung());
            
            stmt.executeUpdate();

            // 3. Gán ID vào từng chi tiết và insert
            for (ChiTietBaoCao ctkk : list) {
                ctkk.setIdBaoCao(newId);
            }
            ChiTietBaoCaoDAO.ThemDSChiTietBaoCao(conn, list);

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Tạo phiếu thất bại", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
}
