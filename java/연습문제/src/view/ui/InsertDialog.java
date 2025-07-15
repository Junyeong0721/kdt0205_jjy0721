package view.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JOptionPane;

import model.dao.BoardDao;
import model.dto.Board;

public class InsertDialog extends JDialog {
    private BoardMain boardApp;
    private JPanel pCenter, pTitle, pContent, pWriter, pSouth;
    private JTextField txtTitle, txtWriter;
    private JTextArea txtContent;
    private JButton btnOk, btnCancel;
    private BoardDao boardDao = new BoardDao();

    public InsertDialog(BoardMain boardApp) {
        super(boardApp);
        this.boardApp = boardApp;
        this.setTitle("게시물 작성");					
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);	
        this.setModal(true);
        this.setSize(400, 270);

        this.setLocation(
            boardApp.getLocationOnScreen().x + (boardApp.getWidth() - this.getWidth()) / 2,
            boardApp.getLocationOnScreen().y + (boardApp.getHeight() - this.getHeight()) / 2
        );

        this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
        this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
    }

    public JPanel getPCenter() {
        if (pCenter == null) {
            pCenter = new JPanel();
            pCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
            pCenter.add(getPTitle());
            pCenter.add(getPWriter());
            pCenter.add(getPContent());
        }
        return pCenter;
    }

    public JPanel getPTitle() {
        if (pTitle == null) {
            pTitle = new JPanel();
            pTitle.setLayout(new BorderLayout());
            JLabel label = new JLabel("제목");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            pTitle.add(label, BorderLayout.WEST);
            txtTitle = new JTextField();
            txtTitle.setPreferredSize(new Dimension(300, 30));
            pTitle.add(txtTitle, BorderLayout.CENTER);
        }
        return pTitle;
    }

    public JPanel getPWriter() {
        if (pWriter == null) {
            pWriter = new JPanel();
            pWriter.setLayout(new BorderLayout());
            JLabel label = new JLabel("글쓴이");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            pWriter.add(label, BorderLayout.WEST);
            txtWriter = new JTextField();
            txtWriter.setPreferredSize(new Dimension(300, 30));
            pWriter.add(txtWriter, BorderLayout.CENTER);
        }
        return pWriter;
    }

    public JPanel getPContent() {
        if (pContent == null) {
            pContent = new JPanel();
            pContent.setLayout(new BorderLayout());
            JLabel label = new JLabel("내용");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            pContent.add(label, BorderLayout.WEST);
            txtContent = new JTextArea();
            txtContent.setBorder(new EtchedBorder());
            txtContent.setPreferredSize(new Dimension(300, 100));
            pContent.add(txtContent, BorderLayout.CENTER);
        }
        return pContent;
    }

    public JPanel getPSouth() {
        if (pSouth == null) {
            pSouth = new JPanel();
            pSouth.setBackground(Color.WHITE);
            pSouth.add(getBtnOk());
            pSouth.add(getBtnCancel());
        }
        return pSouth;
    }

    public JButton getBtnOk() {
        if (btnOk == null) {
            btnOk = new JButton();
            btnOk.setText("저장");
            btnOk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String title = txtTitle.getText().trim();
                    String writer = txtWriter.getText().trim();
                    String content = txtContent.getText().trim();

                    if (title.isEmpty() || writer.isEmpty() || content.isEmpty()) {
                        JOptionPane.showMessageDialog(InsertDialog.this, "모든 항목을 입력해주세요.");
                        return;
                    }

                    Board board = new Board();
                    board.setTitle(title);
                    board.setWriter(writer);
                    board.setContent(content);
                    board.setDate(new Date());    
                    board.setHitcount(0);        

                    int result = boardDao.insert(board);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(InsertDialog.this, "등록 성공!");
                        dispose();  
                    } else {
                        JOptionPane.showMessageDialog(InsertDialog.this, "등록 실패...");
                    }
                }
            });
        }
        return btnOk;
    }

    public JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setText("취소");
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();  
                }
            });
        }
        return btnCancel;
    }
}
