package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.dto.Board;

public class BoardDao {

   
    public List<Board> selectAll() {
        List<Board> list = new ArrayList<>();
        String sql = "SELECT * FROM board ORDER BY no DESC"; 

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Board board = new Board(
                    rs.getInt("no"),
                    rs.getString("title"),
                    rs.getString("writer"),
                    rs.getString("content"),
                    rs.getTimestamp("date"),
                    rs.getInt("hitcount")
                );
                list.add(board);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int updateHit(int no) {
        String sql = "UPDATE board SET hitcount = hitcount + 1 WHERE no = ?";
        int result = 0;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, no);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public int insert(Board board) {
    	String sql = "INSERT INTO board (title, writer, content, date, hitcount) VALUES (?, ?, ?, NOW(), 0)";
        int result = 0;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getWriter());
            pstmt.setString(3, board.getContent());
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public Board selectByNo(int no) {
        String sql = "SELECT * FROM board WHERE no = ?";
        Board board = null;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, no);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    board = new Board(
                        rs.getInt("no"),
                        rs.getString("title"),
                        rs.getString("writer"),
                        rs.getString("content"),
                        rs.getTimestamp("date"),
                        rs.getInt("hitcount")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return board;
    }


    public int update(Board board) {
        String sql = "UPDATE board SET title = ?, writer = ?, content = ? WHERE no = ?";
        int result = 0;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getWriter());
            pstmt.setString(3, board.getContent());
            pstmt.setInt(4, board.getNo());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(int no) {
        String sql = "DELETE FROM board WHERE no = ?";
        int result = 0;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, no);
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}


