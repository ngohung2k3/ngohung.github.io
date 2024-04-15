package B9D53.qlcf.view;

import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class StatisticalView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel totalLBillLabel;
    private JLabel quantityBillLabel;

    private JLabel totalBillTextLabel;
    private JLabel quantityBillTextJLabel;
    private JTable statisticalTable;
    private JScrollPane jScrollPaneStatisTable;
    private String[] columnNames = new String[]{
        "STT", "Time", "Date", "Product", "Total"};
    private Object data = new Object[][]{};

    private class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {

        public MultiLineTableCellRenderer() {
            setLineWrap(true); // cho phép text tự động xuống dòng khi vượt quá chiều rộng của ô
            setWrapStyleWord(true); // đảm bảo từng từ được hiển thị trên một dòng mới
            setOpaque(true); // đảm bảo background của JTextArea sẽ hiển thị đúng màu khi được sử dụng làm renderer trong bảng
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setFont(table.getFont()); // Đảm bảo font của JtextArea sẽ giống với font của bảng
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());

            TableColumnModel columnModel = table.getColumnModel();
            setSize(columnModel.getColumn(column).getWidth(), getPreferredSize().height); // Đặt kích thước của JTextArea phù hợp với kích thước của ô trong bảngc
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }

            return this;
        }
    }

    public StatisticalView() {
        initComponents();
    }

    private void initComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        totalLBillLabel = new JLabel("Total Bill:");
        totalLBillLabel.setFont(labelFont);
        quantityBillLabel = new JLabel("Quantity bill:");
        quantityBillLabel.setFont(labelFont);
        totalBillTextLabel = new JLabel("0");
        totalBillTextLabel.setFont(labelFont);
        quantityBillTextJLabel = new JLabel("0");
        quantityBillTextJLabel.setFont(labelFont);

        jScrollPaneStatisTable = new JScrollPane();
        statisticalTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Không cho phép chỉnh sửa bất kỳ ô nào
                return false;
            }
        };

        statisticalTable.getTableHeader().setReorderingAllowed(false);
        statisticalTable.getTableHeader().setResizingAllowed(false);
        statisticalTable.setFillsViewportHeight(true);
        statisticalTable.setModel(new DefaultTableModel((Object[][]) data, columnNames));

        TableColumn productColumn = statisticalTable.getColumnModel().getColumn(3);
        productColumn.setCellRenderer(new MultiLineTableCellRenderer());
        
        int windowWidth = 1050; // Kích thước cửa sổ JFrame
        int tableWidth = windowWidth; // Trừ đi một số khoảng trống và các đối tượng khác (nếu có)
        double[] columnWidthRatio = {0.05, 0.1, 0.15, 0.6, 0.1}; // Tỉ lệ kích thước của mỗi cột

        for (int i = 0; i < columnWidthRatio.length; i++) {
            int columnWidth = (int) (tableWidth * columnWidthRatio[i]);
            TableColumn column = statisticalTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth);
        }
        jScrollPaneStatisTable.setViewportView(statisticalTable);

        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel();
        panel.setLayout(layout);

        panel.add(jScrollPaneStatisTable);

        panel.add(totalLBillLabel);
        panel.add(quantityBillLabel);

        panel.add(totalBillTextLabel);
        panel.add(quantityBillTextJLabel);

        layout.putConstraint(SpringLayout.WEST, jScrollPaneStatisTable, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollPaneStatisTable, 20, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, jScrollPaneStatisTable, -20, SpringLayout.EAST, panel); // Đặt ràng buộc cho phần đuôi của JScrollPane

        layout.putConstraint(SpringLayout.WEST, quantityBillLabel, 0, SpringLayout.WEST, jScrollPaneStatisTable);
        layout.putConstraint(SpringLayout.NORTH, quantityBillLabel, 20, SpringLayout.SOUTH, jScrollPaneStatisTable);

        layout.putConstraint(SpringLayout.WEST, totalLBillLabel, 0, SpringLayout.WEST, quantityBillLabel);
        layout.putConstraint(SpringLayout.NORTH, totalLBillLabel, 20, SpringLayout.SOUTH, quantityBillLabel);

        layout.putConstraint(SpringLayout.WEST, quantityBillTextJLabel, 10, SpringLayout.EAST, quantityBillLabel);
        layout.putConstraint(SpringLayout.NORTH, quantityBillTextJLabel, 0, SpringLayout.NORTH, quantityBillLabel);

        layout.putConstraint(SpringLayout.WEST, totalBillTextLabel, 10, SpringLayout.EAST, totalLBillLabel);
        layout.putConstraint(SpringLayout.NORTH, totalBillTextLabel, 0, SpringLayout.NORTH, totalLBillLabel);

        this.add(panel);
        this.pack();
        setTitle("Statistics");
        setSize(windowWidth, 570);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void performStatistics(List<Object[]> billDataList) {
        DefaultTableModel model = (DefaultTableModel) statisticalTable.getModel();
        model.setRowCount(0);
        int totalBill = 0;
        for (Object[] row : billDataList) {
            model.addRow(row);
            totalBill += Integer.parseInt(row[4].toString());
        }
        totalBillTextLabel.setText(Integer.toString(totalBill));
        quantityBillTextJLabel.setText(Integer.toString(billDataList.size()));
    }

}
