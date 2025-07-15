package view.ui;

import model.dao.BoardDao;
import model.dto.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewDialog extends JDialog {
    private JTextField txtTitle, txtWriter;
    private JTextArea txtContent;
    private JButton btnUpdate, btnDelete, btnClose;

    private BoardDao boardDao = new BoardDao();
    private Board board;
    private BoardMain parent;

    public ViewDialog(BoardMain parent, int no) {
        super(parent, "게시글 보기", true);
        this.parent = parent;

        board = boardDao.selectByNo(no);
        if (board == null) {
            JOptionPane.showMessageDialog(this, "해당 게시글을 찾을 수 없습니다.");
            dispose();
            return;
        }

        setSize(400, 300);
        setLocationRelativeTo(parent);
        getContentPane().add(buildForm(), BorderLayout.CENTER);
        getContentPane().add(buildButtons(), BorderLayout.SOUTH);
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 제목
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        panel.add(new JLabel("제목"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.9;
        txtTitle = new JTextField(board.getTitle());
        panel.add(txtTitle, gbc);

        // 작성자
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        panel.add(new JLabel("글쓴이"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.9;
        txtWriter = new JTextField(board.getWriter());
        panel.add(txtWriter, gbc);

        // 내용
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weighty = 1; gbc.fill = GridBagConstraints.BOTH;
        txtContent = new JTextArea(board.getContent());
        txtContent.setBorder(new EtchedBorder());
        panel.add(new JScrollPane(txtContent), gbc);

        return panel;
    }

    private JPanel buildButtons() {
        JPanel panel = new JPanel();

        btnUpdate = new JButton("수정");
        btnDelete = new JButton("삭제");
        btnClose = new JButton("닫기");

        btnUpdate.addActionListener(this::updateAction);
        btnDelete.addActionListener(this::deleteAction);
        btnClose.addActionListener(e -> dispose());

        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClose);

        return panel;
    }

    private void updateAction(ActionEvent e) {
        board.setTitle(txtTitle.getText().trim());
        board.setWriter(txtWriter.getText().trim());
        board.setContent(txtContent.getText().trim());

        int result = boardDao.update(board);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "수정 성공!");
            parent.refresh();  
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "수정 실패...");
        }
    }

    private void deleteAction(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int result = boardDao.delete(board.getNo());
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "삭제 성공!");
                parent.refresh(); 
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "삭제 실패...");
            }
        }
    }
}

