# 소스 코드

```

package streamproject.Testpack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import streamproject.Testpack.Nation;

public class NationApp extends JFrame {
	private final JButton btnSort;
	private JTable jTable;
	JComboBox<String> cmbOrder;

	public NationApp() {
		setTitle("국가");
		add(new JScrollPane(makeTable()), BorderLayout.CENTER);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		String[] cmbString = { "국가별", "인구수", "GDP" };
		cmbOrder = new JComboBox<String>(cmbString);
		panel.add(cmbOrder);

		btnSort = new JButton("정렬");
		panel.add(btnSort);

		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillTableWithData();
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 300);
		setVisible(true);

		fillTableWithData();
	}

	JTable makeTable() {
		if (jTable == null) {
			jTable = new JTable();

			DefaultTableModel tableModel = new DefaultTableModel();
			tableModel.addColumn("번호");
			tableModel.addColumn("국가명");
			tableModel.addColumn("유형");
			tableModel.addColumn("인구수");
			tableModel.addColumn("GDP순위");

			jTable.setModel(tableModel);

			String[] headers = { "번호", "국가명", "유형", "인구수", "GDP순위" };
			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
			for (String header : headers) {
				jTable.getColumn(header).setCellRenderer(ctcr);
			}
		}
		return jTable;
	}

	void fillTableWithData() {
		DefaultTableModel model = (DefaultTableModel) jTable.getModel();
		model.setRowCount(0);

		List<Nation> sortedNationList = Nation.nations;

		String selected = (String) cmbOrder.getSelectedItem();
		if ("국가별".equals(selected)) {
			sortedNationList = sortedNationList.stream().sorted(Comparator.comparing(Nation::getName)).collect(Collectors.toList());
		} else if ("인구수".equals(selected)) {
			sortedNationList = sortedNationList.stream().sorted(Comparator.comparingDouble(Nation::getPopulation).reversed())
					.collect(Collectors.toList());
		} else if ("GDP".equals(selected)) {

			sortedNationList = sortedNationList.stream().sorted(Comparator.comparing(Nation::getGdpRank)).collect(Collectors.toList());
		}

		int no = 1;
		for (Nation n : sortedNationList) {
			model.addRow(new Object[] { no++, n.getName(), n.getType(), n.getPopulation(), n.getGdpRank() });
		}
	}

	public class CenterTableCellRenderer extends JLabel implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			setText(value.toString());
			setFont(new Font(null, Font.PLAIN, 12));
			setHorizontalAlignment(JLabel.CENTER);
			setOpaque(true);
			setBackground(isSelected ? Color.YELLOW : Color.WHITE);
			return this;
		}
	}

	public static void main(String[] args) {
		new NationApp();
	}
}
```
