package B9D53.qlcf.view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import B9D53.qlcf.controller.CafeController;

import B9D53.qlcf.entity.Cafe;
import javax.swing.JTable;

public class CafeView extends JFrame implements ActionListener, ListSelectionListener {

    private static final long serialVersionUSTT = 1L;
    private final List<Object[]> billDataList;

    int rowStaNumber = 0;

    private String lastTime;
    private String lastDate;

    private JLabel STTLabel;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JTextField STTField;
    private JTextField nameField;
    private JTextField priceField;

    private JButton addCafeBtn;
    private JButton editCafeBtn;
    private JButton deleteCafeBtn;
    private JButton clearBtn;

    private JLabel SortJLabel;
    private JComboBox<String> SortComboBox;

    private JLabel quantityLabel;
    private JTextField quantityField;
    private JButton chooseBtn;

    private JButton removeBtn;
    private JButton buyBtn;
    private JButton resetBtn;

    private JButton statisticsBtn;
    private JButton printBtn;

    private JScrollPane jScrollPaneCafeTable;
    private JScrollPane jScrollPaneSellCafeTable;
    private JScrollPane jScrollPaneBill;

    private JTable cafeTable; // Bảng menu sản phẩm
    private JTable sellCafeTable; // Bảng thanh toán
    private JTextArea jTextBill; // Hiện hóa đơn     

    private JLabel totalLabel;

    private JLabel findJLabel;
    private JList<String> suggestionList;
    private JTextField searchField;
    private JButton searchBtn;
    private JButton cancelBtn;

    private DefaultListModel<String> suggestionListModel;
    private JScrollPane suggestionScrollPane;

    private JLabel jTimeLabel;
    private JLabel jDateLabel;
    private JLabel JTxtTime;
    private JLabel JTxtDate;

    // định nghĩa các cột của bảng cafe
    private String[] columnNames = new String[]{
        "STT", "Name", "Price"};
    // định nghĩa dữ liệu mặc định của bẳng cafe là rỗng
    private Object data = new Object[][]{};

    // định nghĩa các cột của bảng sellcafe
    private String[] columnSellNames = new String[]{
        "Name", "Quantity", "Price", "Total"};
    // định nghĩa dữ liệu mặc định của bẳng cafe là rỗng
    private Object sellData = new Object[][]{};

    public CafeView() {
        initComponents();
        billDataList = new ArrayList<>();
    }

    private void initComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 13);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTime();

        // khởi tạo các label
        STTLabel = new JLabel("STT");
        STTLabel.setFont(labelFont);
        nameLabel = new JLabel("Name");
        nameLabel.setFont(labelFont);
        priceLabel = new JLabel("Price");
        priceLabel.setFont(labelFont);
        SortJLabel = new JLabel("Sort by");
        SortJLabel.setFont(labelFont);
        quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(labelFont);
        findJLabel = new JLabel("Search");
        findJLabel.setFont(labelFont);

        // khởi tạo các trường nhập dữ liệu cho cafe
        STTField = new JTextField(6);
        STTField.setEditable(false);
        nameField = new JTextField(15);
        quantityField = new JTextField(6);
        priceField = new JTextField(15);
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 25));

        // Trường lựa chọn sắp xếp
        SortComboBox = new JComboBox<>(new String[]{"STT", "Name", "Price"});

        // khởi tạo các phím chức năng
        addCafeBtn = new JButton("Add");
        addCafeBtn.setFont(buttonFont);
        editCafeBtn = new JButton("Edit");
        editCafeBtn.setFont(buttonFont);
        deleteCafeBtn = new JButton("Delete");
        deleteCafeBtn.setFont(buttonFont);
        clearBtn = new JButton("Clear");
        clearBtn.setFont(buttonFont);
        chooseBtn = new JButton("Choose");
        chooseBtn.setFont(buttonFont);
        removeBtn = new JButton("Remove");
        removeBtn.setFont(buttonFont);
        buyBtn = new JButton("Purchase");
        buyBtn.setFont(buttonFont);
        resetBtn = new JButton("Reset");
        resetBtn.setFont(buttonFont);
        searchBtn = new JButton("Search");
        searchBtn.setFont(buttonFont);
        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(buttonFont);
        statisticsBtn = new JButton("Statistics");
        statisticsBtn.setFont(buttonFont);
        printBtn = new JButton("Print");
        printBtn.setFont(buttonFont);

        //Khởi tạo Total
        totalLabel = new JLabel("Total: 0 VND");
        totalLabel.setFont(labelFont);

        // khởi tạo bảng cafe
        jScrollPaneCafeTable = new JScrollPane();
        cafeTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Không cho phép chỉnh sửa bất kỳ ô nào
                return false;
            }
        };
        cafeTable.getTableHeader().setReorderingAllowed(false);
        cafeTable.getTableHeader().setResizingAllowed(false);

        // cài đặt các cột và data cho bảng cafe
        cafeTable.setModel(new DefaultTableModel((Object[][]) data, columnNames));
        jScrollPaneCafeTable.setViewportView(cafeTable);
        jScrollPaneCafeTable.setPreferredSize(new Dimension(480, 300));

        //Khởi tạo bảng sellCafe
        jScrollPaneSellCafeTable = new JScrollPane();
        sellCafeTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Không cho phép chỉnh sửa bất kỳ ô nào
                return false;
            }
        };
        sellCafeTable.getTableHeader().setReorderingAllowed(false);
        sellCafeTable.getTableHeader().setResizingAllowed(false);

        //cài đặt các cột và data cho bảng sell cafe
        sellCafeTable.setModel(new DefaultTableModel((Object[][]) sellData, columnSellNames));
        jScrollPaneSellCafeTable.setViewportView(sellCafeTable);
        jScrollPaneSellCafeTable.setPreferredSize(new Dimension(480, 300));

        //Khởi tạo bảng bill
        jTextBill = new JTextArea();
        jTextBill.setFont(new Font("Courier New", Font.PLAIN, 12));
        jScrollPaneBill = new JScrollPane(jTextBill);
        jScrollPaneBill.setPreferredSize(new Dimension(500, 660));
        jTextBill.setEditable(false);

        // Tạo 1 DefaultListModel để lưu trữ các mục dữ liệu trong danh sách gợi ý
        suggestionListModel = new DefaultListModel<>();
        // Tạo JList và sử dụng suggestionListModel làm dữ liệu hiển thị danh sách gợi ý
        suggestionList = new JList<>(suggestionListModel);

        // Khởi tạo thanh cuộn cho danh sách gợi ý
        suggestionScrollPane = new JScrollPane(suggestionList);
        suggestionScrollPane.setVisible(false);

        //Khởi tạo Time và Date
        JTxtTime = new JLabel();
        JTxtTime.setFont(labelFont);
        jTimeLabel = new JLabel("Time:");
        jTimeLabel.setFont(labelFont);

        JTxtDate = new JLabel();
        JTxtDate.setFont(labelFont);
        jDateLabel = new JLabel("Date:");
        jDateLabel.setFont(labelFont);

        // tạo spring layout
        SpringLayout layout = new SpringLayout();
        // tạo đối tượng panel để chứa các thành phần của màn hình quản lý Cafe
        JPanel panel = new JPanel() {
            // Override phương thức paintComponent để vẽ hình ảnh nền
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load hình ảnh nền
                Image background = new ImageIcon("coffee.jpg").getImage();

                // Vẽ hình ảnh nền từ góc trên bên trái của JPanel
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(layout);

        panel.add(suggestionScrollPane);
        panel.add(jScrollPaneCafeTable);
        panel.add(jScrollPaneSellCafeTable);
        panel.add(jScrollPaneBill);

        panel.add(STTLabel);
        panel.add(nameLabel);
        panel.add(quantityLabel);
        panel.add(priceLabel);

        panel.add(STTField);
        panel.add(nameField);
        panel.add(quantityField);
        panel.add(priceField);

        panel.add(addCafeBtn);
        panel.add(editCafeBtn);
        panel.add(deleteCafeBtn);
        panel.add(clearBtn);
        panel.add(chooseBtn);
        panel.add(removeBtn);
        panel.add(buyBtn);
        panel.add(resetBtn);
        panel.add(statisticsBtn);
        panel.add(printBtn);
        panel.add(searchBtn);
        panel.add(cancelBtn);

        panel.add(SortJLabel);
        panel.add(SortComboBox);

        panel.add(findJLabel);
        panel.add(searchField);

        panel.add(totalLabel);

        panel.add(jTimeLabel);
        panel.add(JTxtTime);
        panel.add(jDateLabel);
        panel.add(JTxtDate);

        // cài đặt vị trí các thành phần trên màn hình login
        layout.putConstraint(SpringLayout.WEST, STTLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, STTLabel, 40, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, nameLabel, 0, SpringLayout.WEST, STTLabel);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 30, SpringLayout.SOUTH, STTLabel);
        layout.putConstraint(SpringLayout.WEST, priceLabel, 0, SpringLayout.WEST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, priceLabel, 30, SpringLayout.SOUTH, nameLabel);

        layout.putConstraint(SpringLayout.WEST, STTField, 43, SpringLayout.EAST, STTLabel);
        layout.putConstraint(SpringLayout.NORTH, STTField, 0, SpringLayout.NORTH, STTLabel);
        layout.putConstraint(SpringLayout.WEST, nameField, 32, SpringLayout.EAST, nameLabel);
        layout.putConstraint(SpringLayout.NORTH, nameField, 0, SpringLayout.NORTH, nameLabel);
        layout.putConstraint(SpringLayout.WEST, priceField, 37, SpringLayout.EAST, priceLabel);
        layout.putConstraint(SpringLayout.NORTH, priceField, 0, SpringLayout.NORTH, priceLabel);

        layout.putConstraint(SpringLayout.WEST, addCafeBtn, 0, SpringLayout.WEST, priceLabel);
        layout.putConstraint(SpringLayout.NORTH, addCafeBtn, 30, SpringLayout.SOUTH, priceLabel);
        layout.putConstraint(SpringLayout.WEST, editCafeBtn, 10, SpringLayout.EAST, addCafeBtn);
        layout.putConstraint(SpringLayout.NORTH, editCafeBtn, 0, SpringLayout.NORTH, addCafeBtn);
        layout.putConstraint(SpringLayout.WEST, deleteCafeBtn, 10, SpringLayout.EAST, editCafeBtn);
        layout.putConstraint(SpringLayout.NORTH, deleteCafeBtn, 0, SpringLayout.NORTH, editCafeBtn);
        layout.putConstraint(SpringLayout.WEST, clearBtn, 10, SpringLayout.EAST, deleteCafeBtn);
        layout.putConstraint(SpringLayout.NORTH, clearBtn, 0, SpringLayout.NORTH, deleteCafeBtn);

        layout.putConstraint(SpringLayout.WEST, SortJLabel, 0, SpringLayout.WEST, addCafeBtn);
        layout.putConstraint(SpringLayout.NORTH, SortJLabel, 30, SpringLayout.SOUTH, addCafeBtn);
        layout.putConstraint(SpringLayout.NORTH, SortComboBox, 0, SpringLayout.NORTH, SortJLabel);
        layout.putConstraint(SpringLayout.WEST, SortComboBox, 20, SpringLayout.EAST, SortJLabel);

        layout.putConstraint(SpringLayout.WEST, quantityLabel, 0, SpringLayout.WEST, SortJLabel);
        layout.putConstraint(SpringLayout.NORTH, quantityLabel, 30, SpringLayout.SOUTH, SortJLabel);
        layout.putConstraint(SpringLayout.WEST, quantityField, 10, SpringLayout.EAST, quantityLabel);
        layout.putConstraint(SpringLayout.NORTH, quantityField, 0, SpringLayout.NORTH, quantityLabel);
        layout.putConstraint(SpringLayout.WEST, chooseBtn, 30, SpringLayout.EAST, quantityField);
        layout.putConstraint(SpringLayout.NORTH, chooseBtn, 0, SpringLayout.NORTH, quantityField);

        layout.putConstraint(SpringLayout.WEST, removeBtn, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, removeBtn, 400, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, buyBtn, 0, SpringLayout.WEST, removeBtn);
        layout.putConstraint(SpringLayout.NORTH, buyBtn, 30, SpringLayout.SOUTH, removeBtn);
        layout.putConstraint(SpringLayout.WEST, resetBtn, 0, SpringLayout.WEST, buyBtn);
        layout.putConstraint(SpringLayout.NORTH, resetBtn, 30, SpringLayout.SOUTH, buyBtn);
        layout.putConstraint(SpringLayout.WEST, statisticsBtn, 0, SpringLayout.WEST, jScrollPaneBill);
        layout.putConstraint(SpringLayout.NORTH, statisticsBtn, 10, SpringLayout.SOUTH, jScrollPaneBill);
        layout.putConstraint(SpringLayout.WEST, printBtn, 30, SpringLayout.EAST, statisticsBtn);
        layout.putConstraint(SpringLayout.NORTH, printBtn, 0, SpringLayout.NORTH, statisticsBtn);

        layout.putConstraint(SpringLayout.WEST, jScrollPaneCafeTable, 300, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollPaneCafeTable, 40, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, jScrollPaneSellCafeTable, 300, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollPaneSellCafeTable, 400, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, totalLabel, 0, SpringLayout.WEST, jScrollPaneSellCafeTable);
        layout.putConstraint(SpringLayout.NORTH, totalLabel, 10, SpringLayout.SOUTH, jScrollPaneSellCafeTable);

        layout.putConstraint(SpringLayout.WEST, jScrollPaneBill, 850, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollPaneBill, 40, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, findJLabel, 300, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, findJLabel, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, searchField, 25, SpringLayout.EAST, findJLabel);
        layout.putConstraint(SpringLayout.NORTH, searchField, 0, SpringLayout.NORTH, findJLabel);
        layout.putConstraint(SpringLayout.NORTH, suggestionScrollPane, 5, SpringLayout.SOUTH, searchField);
        layout.putConstraint(SpringLayout.WEST, suggestionScrollPane, 0, SpringLayout.WEST, searchField);
        layout.putConstraint(SpringLayout.EAST, suggestionScrollPane, 0, SpringLayout.EAST, jScrollPaneCafeTable);
        layout.putConstraint(SpringLayout.WEST, searchBtn, 25, SpringLayout.EAST, searchField);
        layout.putConstraint(SpringLayout.NORTH, searchBtn, 0, SpringLayout.NORTH, searchField);
        layout.putConstraint(SpringLayout.WEST, cancelBtn, 23, SpringLayout.EAST, searchBtn);
        layout.putConstraint(SpringLayout.NORTH, cancelBtn, 0, SpringLayout.NORTH, searchBtn);

        layout.putConstraint(SpringLayout.WEST, jTimeLabel, 0, SpringLayout.WEST, jScrollPaneBill);
        layout.putConstraint(SpringLayout.SOUTH, jTimeLabel, -10, SpringLayout.NORTH, jScrollPaneBill);
        layout.putConstraint(SpringLayout.WEST, JTxtTime, 5, SpringLayout.EAST, jTimeLabel);
        layout.putConstraint(SpringLayout.NORTH, JTxtTime, 0, SpringLayout.NORTH, jTimeLabel);
        layout.putConstraint(SpringLayout.WEST, jDateLabel, 20, SpringLayout.EAST, JTxtTime);
        layout.putConstraint(SpringLayout.NORTH, jDateLabel, 0, SpringLayout.NORTH, JTxtTime);
        layout.putConstraint(SpringLayout.WEST, JTxtDate, 5, SpringLayout.EAST, jDateLabel);
        layout.putConstraint(SpringLayout.NORTH, JTxtDate, 0, SpringLayout.NORTH, jDateLabel);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set toàn màn hình
        this.setLocationRelativeTo(null); // Nếu bạn vẫn muốn cửa sổ ở giữa, khi không ở chế độ toàn màn hình
        this.add(panel);
        this.pack();
        this.setTitle("Cafe Management");
        this.addWindowStateListener((WindowEvent e) -> {
            // Kiểm tra nếu cửa sổ không còn ở chế độ toàn màn hình
            if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) == 0) {
                setSize(1400, 800); // Đặt lại kích thước cửa sổ
                setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
            }
        });
        // disable Edit and Delete buttons
        editCafeBtn.setEnabled(false);
        deleteCafeBtn.setEnabled(false);
        // enable Add button
        addCafeBtn.setEnabled(true);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    //lấy 1 trong 3 cách sắp xếp
    public String getSelectedSort() {
        return (String) SortComboBox.getSelectedItem();
    }

    /**
     * Thiết lập thời gian và ngày
     */
    public void setTime() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); //ngủ chương trình trong 1000 mili giây = 1 giây
                } catch (InterruptedException ex) {
                    Logger.getLogger(CafeView.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date date = new Date();
                SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss aa"); //định dạng thời gian
                SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MM-yyyy"); //định hàng ngày
                String time = tf.format(date);
                JTxtTime.setText(time.split(" ")[0] + " " + time.split(" ")[1]);
                JTxtDate.setText(df.format(date));
            }
        }).start();
    }

    //hiển thị list cafe vào bảng cafeTable
    public void showListCafes(List<Cafe> list) {
        int size = list.size();
        // với bảng cafeTable có 3 cột, 
        // khởi tạo mảng 2 chiều cafes, trong đó:
        // số hàng: là kích thước của list cafe 
        // số cột: là 3
        Object[][] cafes = new Object[size][3];
        for (int i = 0; i < size; i++) {
            cafes[i][0] = list.get(i).getSTT();
            cafes[i][1] = list.get(i).getName();
            cafes[i][2] = list.get(i).getPrice();

        }
        //đặt dữu liệu của bảng là mảng cafes, tên cột columnNames
        cafeTable.setModel(new DefaultTableModel(cafes, columnNames));
    }

    //điền thông tin của hàng được chọn từ bảng cafe vào các trường tương ứng
    public void fillCafeFromSelectedRow() {
        // lấy chỉ số của hàng được chọn 
        int row = cafeTable.getSelectedRow();
        if (row >= 0) {
            STTField.setText(cafeTable.getModel().getValueAt(row, 0).toString());
            nameField.setText(cafeTable.getModel().getValueAt(row, 1).toString());
//            quantityField.setText(cafeTable.getModel().getValueAt(row, 2).toString());
            priceField.setText(cafeTable.getModel().getValueAt(row, 2).toString());

            // enable Edit and Delete buttons
            editCafeBtn.setEnabled(true);
            deleteCafeBtn.setEnabled(true);
            // disable Add button
            addCafeBtn.setEnabled(false);
        }
    }

    /**
     * lấy thông tin cafe từ các trường nhập dữ liệu
     * tạo đối tượng cafe có các thông tin đã lấy
     *
     * @return
     */
    public Cafe getCafeInfo() {
        // validate student
        if (!validateName() || !validatePrice()) {
            return null;
        }
        try {
            Cafe cafe = new Cafe();
            if (STTField.getText() != null && !"".equals(STTField.getText())) {
                cafe.setSTT(Integer.parseInt(STTField.getText()));
            }
            cafe.setName(nameField.getText().trim());
            cafe.setPrice(Integer.parseInt(priceField.getText().trim()));
            return cafe;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    //xóa thông tin cafe
    public void clearCafeInfo() {
        STTField.setText("");
        nameField.setText("");
        priceField.setText("");

        // disable Edit and Delete buttons
        editCafeBtn.setEnabled(false);
        deleteCafeBtn.setEnabled(false);
        // enable Add button
        addCafeBtn.setEnabled(true);
    }

    //Thêm cafe vào bảng sellCafeTable
    public void addCafeToSellTable() {
        int row = cafeTable.getSelectedRow();
        if (row >= 0) {
            String name = cafeTable.getValueAt(row, 1).toString();
            String quantityText = quantityField.getText().trim();
            if (quantityText.isEmpty()) {
                showMessage("Please enter quantity");
                return;
            }
            try {
                int quantity = Integer.parseInt(quantityField.getText().trim());
                
                if (quantity <= 0) {
                    showMessage("Quantity must be greater than 0");
                    return;
                }
//                int price = Integer.parseInt(cafeTable.getValueAt(row, 2).toString());
                int price = (int) cafeTable.getValueAt(row, 2);

                Object[] rowData = {name, quantity, price, quantity * price};
                //Lấy mô hiình của cảng sellCafeTable để thêm dữ liệu vào đó
                DefaultTableModel sellTableModel = (DefaultTableModel) sellCafeTable.getModel();
                //Thêm hàng mới
                sellTableModel.addRow(rowData);
                //Cập nhật lại mô hình của bảng sellCafeTable
                sellCafeTable.setModel(sellTableModel);

                int total = 0;
                for (int i = 0; i < sellCafeTable.getRowCount(); i++) {
                    total += Integer.parseInt(sellCafeTable.getValueAt(i, 3).toString());
                }
                totalLabel.setText("Total: " + total + " VND");
            } catch (NumberFormatException e) {
                showMessage("Quantity is illegal");
            }
        } else {
            showMessage("Please choose product!");
        }
    }

    // Hủy cafe đã chọn trong bảng thanh toán
    public void removeSellCafeTable() {
        int selectedRow = sellCafeTable.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel sellTableModel = (DefaultTableModel) sellCafeTable.getModel();
            sellTableModel.removeRow(selectedRow);
            updateTotal();
        } else {
            showMessage("Please select a row to remove.");
        }
    }

    //Cập nhật tổng giá tiền
    public void updateTotal() {
        int total = 0;
        for (int i = 0; i < sellCafeTable.getRowCount(); i++) {
            total += Integer.parseInt(sellCafeTable.getValueAt(i, 3).toString());
        }
        totalLabel.setText(("Total: " + total + " VND"));
    }

    /**
     * Lấy số lượng hàng hiện có trong bảng sellCafeTable
     *
     * @return
     */
    public int getRowCountSellTable() {
        return sellCafeTable.getRowCount();
    }

    /**
     * Hiện hóa đơn
     */
    public void showBillCafe() {
        //Khởi tạo 1 đối tượng StringBuilder để xây dưunjg nội dung của hóa đơn
        StringBuilder billText = new StringBuilder();
        billText.append("Bill\n");
        lastTime = JTxtTime.getText();
        lastDate = JTxtDate.getText();
        billText.append(lastTime).append("\n");
        billText.append(lastDate).append("\n");

        // Tiêu đề các trường
        String nameHeader = "Name";
        String quantityHeader = "Quantity";
        String priceHeader = "Price";
        String totalHeader = "Total";

        // Độ rộng của mỗi trường
        int nameWidth = 30;
        int quantityWidth = 15;
        int priceWidth = 15;

        // Định dạng, căn chiỉnh tiêu đề và các trường dữ liệu
        String format = "%-" + nameWidth + "s %-" + quantityWidth + "s %-" + priceWidth + "s %s%n";

        billText.append(String.format(format, nameHeader, quantityHeader, priceHeader, totalHeader));
        billText.append("********************************************************************** \n");

        // Lưu trữ key-value (name - tổng quantity)
        Map<String, Integer> productQuantities = new HashMap<>();

        // Danh sách tên đồ uống theo thứ tự order
        List<String> orderedProductNames = new ArrayList<>();

        int rowCount = getRowCountSellTable();
        int total = 0;
        rowStaNumber++;

        for (int i = 0; i < rowCount; i++) {
            String name = sellCafeTable.getValueAt(i, 0).toString();
            int quantity = Integer.parseInt(sellCafeTable.getValueAt(i, 1).toString());
            
            //Cập nhật quantity của sản phẩm được order
            productQuantities.put(name, productQuantities.getOrDefault(name, 0) + quantity);

            if (!orderedProductNames.contains(name)) {
                orderedProductNames.add(name); // Thêm tên đồ uống vào danh sách nếu chưa có
            }
        }

        StringBuilder combinedNames = new StringBuilder();

        for (String productName : orderedProductNames) {
            int quantity = productQuantities.get(productName);
            int price = getPriceFromTable(productName); // Lấy price từ bảng
            int subTotal = quantity * price;
            total += subTotal;
            billText.append(String.format("%-" + nameWidth + "s %-" + quantityWidth + "s %-" + priceWidth + "s %s%n", productName, quantity, price, subTotal));

            combinedNames.append(productName).append(" (").append(quantity).append("), ");
        }
        // Xóa dấu phẩy cuối cùng nếu có
        if (combinedNames.length() > 0) {
            combinedNames.deleteCharAt(combinedNames.length() - 2);
        }

        Object[] billData = {rowStaNumber, lastTime, lastDate, combinedNames.toString(), total};
        
        //Thêm hóa đơn mới vào danh sách các hóa đơn trong ngày
        billDataList.add(billData);

        billText.append("********************************************************************** \n");
        billText.append(String.format("%-" + nameWidth + "s %-" + quantityWidth + "s %-" + priceWidth + "s %s%n", "Total", "", "", total));
        jTextBill.setText(billText.toString());
    }

    // Phương thức để lấy giá từ bảng dựa trên tên sản phẩm
    private int getPriceFromTable(String productName) {
        int rowCount = getRowCountSellTable();
        for (int i = 0; i < rowCount; i++) {
            String name = sellCafeTable.getValueAt(i, 0).toString();
            int price = Integer.parseInt(sellCafeTable.getValueAt(i, 2).toString());
            if (name.equals(productName)) {
                return price;
            }
        }
        return 0; // Trả về giá 0 nếu không tìm thấy
    }
    
    /*
    * lấy danh sách hóa đơn phục vụ thống kê
    *
     */
    public List<Object[]> getBillDataList() {
        return billDataList;
    }

    //In hóa đơn trả khách hàng
    public void printBill() {
        try {
            File file = new File("Bill.txt");
            try (FileWriter write = new FileWriter(file)) {
                write.write(jTextBill.getText());
            }
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);

        } catch (IOException ex) {
            showMessage("Error printing bill");
        }
    }

    /**
     * Làm sạch bảng sellCafe, bill, QuantityField
     */
    public void reset() {
        DefaultTableModel sellTableModel = (DefaultTableModel) sellCafeTable.getModel();
        sellTableModel.setRowCount(0);
        quantityField.setText("");
        jTextBill.setText("");
        updateTotal();
    }

    //Phần tìm kiếm
    public String getSearchField() {
        return searchField.getText();
    }

    // Xóa nội dung của thanh tìm kiếm
    public void clearSearchField() {
        searchField.setText("");
    }

    // Phương thức để lấy mục đã được chọn từ danh sách gợi ý
    public String getSelectedSuggestion() {
        return suggestionList.getSelectedValue();
    }

    // Phương thức để đặt văn bản cho ô tìm kiếm
    public void setSearchFieldText(String text) {
        searchField.setText(text);
        hideSuggestionScrollPane();
    }

    // Trả về kết quả trên menu sau khi tìm kiếm
    public void updateSearchList(String searchText) {
        List<Cafe> matchingCafesByName;
        List<Cafe> matchingCafesByPrice;

        if (isNumeric(searchText)) {
            // searchText là số, thực hiện tìm kiếm theo giá
            matchingCafesByName = new ArrayList<>();
            matchingCafesByPrice = CafeController.SearchCafeByPrice(Integer.parseInt(searchText));
        } else {
            // searchText là chữ, thực hiện tìm kiếm theo tên
            matchingCafesByName = CafeController.SearchCafeByName(searchText);
            matchingCafesByPrice = new ArrayList<>();
        }

        // Kết hợp danh sách kết quả từ cả hai tìm kiếm
        Set<Cafe> combinedResults = new HashSet<>();
        combinedResults.addAll(matchingCafesByName);
        combinedResults.addAll(matchingCafesByPrice);

        // Hiển thị kết quả kết hợp trên giao diện
        showListCafes(new ArrayList<>(combinedResults));
    }

    // Phương thức kiểm tra xem một chuỗi có phải là số hay không
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // Cập nhật, trả về danh sách gợi ý tìm kiếm
    public void updateSuggestionList() {
        String searchText = searchField.getText().toLowerCase().trim();
        List<String> allCafeNames = CafeController.GetAllCafeNames();
        List<Integer> allCafePrices = CafeController.GetAllCafePrices();
        
        // Xóa tất cả các mục từ danh sách gợi ý
        suggestionListModel.clear();

        // Nếu ô tìm kiếm không trống, thêm các mục phù hợp vào danh sách gợi ý
        if (!searchText.isEmpty()) {
            // Nếu là số, thêm giá cả vào danh sách gợi ý
            if (searchText.matches("\\d+")) {
                Set<Integer> uniquePrices = new HashSet<>(allCafePrices);
                for (Integer cafePrice : uniquePrices) {
                    if (String.valueOf(cafePrice).startsWith(searchText)) {
                        suggestionListModel.addElement(String.valueOf(cafePrice));
                    }
                }
            } else {
                for (String cafeName : allCafeNames) {
                    if (cafeName.toLowerCase().contains(searchText)) {
                        suggestionListModel.addElement(cafeName);
                    }
                }
            }
        } else {
            // Nếu ô tìm kiếm trống, ẩn cửa sổ gợi ý
            hideSuggestionScrollPane();
        }
        // Hiển thị hoặc ẩn cửa sổ gợi ý tùy thuộc vào có mục trong danh sách gợi ý hay không
        suggestionScrollPane.setVisible(!suggestionListModel.isEmpty());
    }

    public void hideSuggestionScrollPane() {
        suggestionScrollPane.setVisible(false);
    }

    private boolean validateName() {
        String name = nameField.getText();
        if (name == null || "".equals(name.trim())) {
            nameField.requestFocus();
            showMessage("Name cannot be empty");
            return false;
        }
        return true;
    }

    private boolean validatePrice() {
        try {
            int price = Integer.parseInt(priceField.getText().trim());
            if (price <= 0) {
                priceField.requestFocus();
                showMessage("Price is illegal");
                return false;
            }
        } catch (NumberFormatException e) {
            priceField.requestFocus();
            showMessage("Price is illegal");
            return false;
        }
        return true;
    }

    /**
     * Phương thức xử lý xự kiện
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     *
     * @param e
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    public void addAddCafeListener(ActionListener listener) {
        addCafeBtn.addActionListener(listener);
    }

    public void addEdiCafeListener(ActionListener listener) {
        editCafeBtn.addActionListener(listener);
    }

    public void addDeleteCafeListener(ActionListener listener) {
        deleteCafeBtn.addActionListener(listener);
    }

    public void addClearListener(ActionListener listener) {
        clearBtn.addActionListener(listener);
    }

    public void addChooseSellCafeListener(ActionListener listener) {
        chooseBtn.addActionListener(listener);
    }

    public void addRemoveSellCafeListener(ActionListener listener) {
        removeBtn.addActionListener(listener);
    }

    public void addShowBillCafe(ActionListener listener) {
        buyBtn.addActionListener(listener);
    }

    public void addSortCafeListener(ActionListener listener) {
        SortComboBox.addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
        resetBtn.addActionListener(listener);
    }

    public void addStatisticalListener(ActionListener listener) {
        statisticsBtn.addActionListener(listener);
    }

    public void addPrintListener(ActionListener listener) {
        printBtn.addActionListener(listener);
    }

    public void addListCafeSelectionListener(ListSelectionListener listener) {
        cafeTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void addSearchCafeListener(ActionListener listener) {
        searchBtn.addActionListener(listener);
    }

    public void addSearchFieldListener(DocumentListener listener) {
        searchField.getDocument().addDocumentListener(listener);
    }

    public void addSuggestionListListener(ListSelectionListener listener) {
        suggestionList.addListSelectionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelBtn.addActionListener(listener);
    }

    public void addEnterKeyListener(KeyListener listener) {
        searchField.addKeyListener(listener);
    }
}
