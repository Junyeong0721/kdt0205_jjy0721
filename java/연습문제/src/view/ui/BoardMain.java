package view.ui;

import model.dao.BoardDao;
import model.dto.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BoardMain extends JFrame {
    private JTable jTable;
    private JPanel pSouth;
    private JButton btnInsert;
    private DefaultTableModel tableModel;
    private BoardDao boardDao = new BoardDao();

    public BoardMain() {
        setTitle("게시판 리스트");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new JScrollPane(getJTable()), BorderLayout.CENTER);
        getContentPane().add(getPSouth(), BorderLayout.SOUTH);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JTable getJTable() {
        if (jTable == null) {
            jTable = new JTable();
            tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            jTable = new JTable(tableModel);


            tableModel.addColumn("번호");
            tableModel.addColumn("제목");
            tableModel.addColumn("글쓴이");
            tableModel.addColumn("날짜");
            tableModel.addColumn("조회수");


            jTable.getColumn("번호").setPreferredWidth(20);
            jTable.getColumn("제목").setPreferredWidth(250);
            jTable.getColumn("글쓴이").setPreferredWidth(50);
            jTable.getColumn("날짜").setPreferredWidth(100);
            jTable.getColumn("조회수").setPreferredWidth(20);


            CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
            jTable.getColumn("번호").setCellRenderer(ctcr);
            jTable.getColumn("제목").setCellRenderer(ctcr);
            jTable.getColumn("글쓴이").setCellRenderer(ctcr);
            jTable.getColumn("날짜").setCellRenderer(ctcr);
            jTable.getColumn("조회수").setCellRenderer(ctcr);

            jTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = jTable.getSelectedRow();
                        if (row != -1) {
                            int no = (int) jTable.getValueAt(row, 0);

                            boardDao.updateHit(no);                   

                            new ViewDialog(BoardMain.this, no).setVisible(true);
                            refresh();
                        }
                    }
                }
            });

            refresh();
        }
        return jTable;
    }

    public void refresh() {
        tableModel.setRowCount(0);
        List<Board> list = boardDao.selectAll();
        for (Board board : list) {
            Object[] row = {
                board.getNo(),
                board.getTitle(),
                board.getWriter(),
                board.getDate(),
                board.getHitcount()
            };
            tableModel.addRow(row);
        }
    }

    public class CenterTableCellRenderer extends JLabel implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText(value.toString());
            setFont(new Font(null, Font.PLAIN, 12));
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
            setBackground(isSelected ? Color.YELLOW : Color.WHITE);
            return this;
        }
    }

    public JPanel getPSouth() {
        if (pSouth == null) {
            pSouth = new JPanel();
            pSouth.add(getBtnInsert());
        }
        return pSouth;
    }

    public JButton getBtnInsert() {
        if (btnInsert == null) {
            btnInsert = new JButton("추가");
            btnInsert.addActionListener(e -> {
                InsertDialog dialog = new InsertDialog(BoardMain.this);
                dialog.setVisible(true);
                refresh();
            });
        }
        return btnInsert;
    }

    public static void main(String[] args) {
        new BoardMain();
    }
}

