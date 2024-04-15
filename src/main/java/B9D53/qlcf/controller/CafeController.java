package B9D53.qlcf.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import B9D53.qlcf.dao.CafeDao;
import B9D53.qlcf.entity.Cafe;
import B9D53.qlcf.view.CafeView;
import B9D53.qlcf.view.StatisticalView;

public class CafeController {

    private CafeDao cafeDao;
    private CafeView cafeView;

    public CafeController(CafeView view) {
        this.cafeView = view;
        cafeDao = new CafeDao();

        view.addAddCafeListener(new AddCafeListener());
        view.addEdiCafeListener(new EditCafeListener());
        view.addDeleteCafeListener(new DeleteCafeListener());
        view.addClearListener(new ClearCafeListener());

        view.addChooseSellCafeListener(new ChooseSellCafeListener());
        view.addRemoveSellCafeListener(new RemoveSellCafeTable());
        view.addShowBillCafe(new ShowBillCafe());
        view.addResetListener(new ResetSellCafeListener());
        view.addStatisticalListener(new Statistical());
        view.addPrintListener(new PrintBill());
        view.addSortCafeListener(new SortCafeListener());

        view.addSearchFieldListener(new SearchFieldListener());
        view.addSearchCafeListener(new SearchCafeListener());
        view.addSuggestionListListener(new SuggestionListListener());
        view.addCancelListener(new CancelCafeListener());

        view.addListCafeSelectionListener(new ListCafeSelectionListener());

        view.addEnterKeyListener(new CafeController.EnterKeyListener());
    }

    public void showCafeView() {
        List<Cafe> cafeList = cafeDao.getListCafes();
        cafeView.setVisible(true);
        cafeView.showListCafes(cafeList);
    }

    /**
     * Lớp ListCafeSelectionListener chứa cài đặt cho sự kiện chọn cafe trong
     * bảng cafe Thông tin cafe sẽ được hiện thị trong trường dữ liệu
     */
    class ListCafeSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            cafeView.fillCafeFromSelectedRow();
        }
    }

    /**
     * Lớp AddCafeListener chứa cài đặt cho sự kiện click button "Add"
     */
    class AddCafeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Cafe cafe = cafeView.getCafeInfo();
            if (cafe != null) {
                cafeDao.add(cafe);
                cafeView.showListCafes(cafeDao.getListCafes());
                cafeView.showMessage("Add Successful");
            }
        }
    }

    /**
     * Lớp EditCafeListener chứa cài đặt cho sự kiện click button "Edit"
     */
    class EditCafeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Cafe cafe = cafeView.getCafeInfo();
            if (cafe != null) {
                cafeDao.edit(cafe);
                cafeView.showListCafes(cafeDao.getListCafes());
                cafeView.showMessage("Edit Successful");

            }
        }
    }

    /**
     * Lớp DeleteCafeListener chứa cài đặt cho sự kiện click button "Delete"
     *
     */
    class DeleteCafeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Cafe cafe = cafeView.getCafeInfo();
            if (cafe != null) {
                cafeDao.delete(cafe);
                cafeView.clearCafeInfo();
                cafeView.showListCafes(cafeDao.getListCafes());
                cafeView.showMessage("Delete Successful");
            }
        }
    }

    /**
     * Lớp ClearCafeListener chứa cài đặt cho sự kiện click button "Clear"
     */
    class ClearCafeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cafeView.clearCafeInfo();
        }
    }

    //Phần dành cho bán Cafe
    /**
     * Lớp ChooseSellCafeListener chứa cài đặt cho sự kiện click button "Choose"
     * Chọn cafe từ bảng menu vào bảng sell
     */
    class ChooseSellCafeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cafeView.addCafeToSellTable();
        }
    }

    /**
     * Lớp RemoveSellCafeTable chứa cài đặt cho sự kiện click button "Remove"
     * Chọn hàng trong bảng sell và remove khỏi bảng sell
     */
    class RemoveSellCafeTable implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cafeView.removeSellCafeTable();
        }
    }

    /**
     * Lớp RemoveSellCafeTable chứa cài đặt cho sự kiện click button "Reset"
     * reset bill, sell table và quantityField
     */
    class ResetSellCafeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cafeView.reset();
        }
    }

    //Hóa đơn
    /**
     * Hiện thị hóa đơn mua hàng sau khi click vào button "Purchase"
     */
    class ShowBillCafe implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cafeView.getRowCountSellTable() == 0) {
                cafeView.showMessage("Please choose product");
                return;
            }
            cafeView.showBillCafe();
        }
    }

    //Sắp xếp  
    class SortCafeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedSort = cafeView.getSelectedSort();
            switch (selectedSort) {
                case "STT":
                    cafeDao.sortCafeBySTT();
                    cafeView.showListCafes(cafeDao.getListCafes());
                    break;
                case "Name":
                    cafeDao.sortCafeByName();
                    cafeView.showListCafes(cafeDao.getListCafes());
                    break;
                case "Price":
                    cafeDao.sortCafeByPrice();
                    cafeView.showListCafes(cafeDao.getListCafes());
                    break;
                default:
            }
        }
    }

    //Phần thống kê
    /**
     * Lớp Statistical cài đặt cho sự kiện click button "Statistics"
     */
    class Statistical implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object[]> billDataList = cafeView.getBillDataList();
            StatisticalView statisticalView = new StatisticalView();
            statisticalView.performStatistics(billDataList);
        }
    }

    /**
     * Cài đặt cho sự kiện click button Print
     */
    class PrintBill implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cafeView.printBill();
        }
    }

    //Phần tìm kiếm
    /**
     * Lớp SearchCafeListener cài đặt cho sự kiện click button "Search"
     */
    class SearchCafeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = cafeView.getSearchField();
            if (searchText.isEmpty()) {
                cafeView.showMessage("Fill in the search box");
                return;
            }
            cafeView.updateSearchList(searchText);
            cafeView.hideSuggestionScrollPane();
        }
    }

    /**
     * tạo danh sách chứa tên cafe
     * @return 
     */
    public static List<String> GetAllCafeNames() {
        CafeDao cafeDao = new CafeDao();
        return cafeDao.getAllCafeNames();
    }

    /**
     * Tạo danh sách chưa price của cafe
     * @return 
     */
    public static List<Integer> GetAllCafePrices() {
        CafeDao cafeDao = new CafeDao();
        return cafeDao.getAllCafePrices();
    }

    /**
     *Trả về đề xuất tìm kiếm theo tên
     * @param searchText
     * @return
     */
    public static List<Cafe> SearchCafeByName(String searchText) {
        CafeDao cafeDao = new CafeDao();
        return cafeDao.searchCafeByName(searchText);
    }

    /**
     *Trả về đề xuất tìm kiếm theo giá
     * @param price
     * @return
     */
    public static List<Cafe> SearchCafeByPrice(int price) {
        CafeDao cafeDao = new CafeDao();
        return cafeDao.searchCafeByPrice(price);
    }

    // Lắng nghe sự kiện cho danh sách gợi ý
    class SuggestionListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            String selectedCafe = cafeView.getSelectedSuggestion();
            if (selectedCafe != null) {
                cafeView.setSearchFieldText(selectedCafe); //điền gợi ý vào trong ô tìm kiếm
                cafeView.updateSearchList(selectedCafe); // trả về kết quả tìm kiếm trên menu
            }
        }
    }

    // Lắng nghe sự kiện cho ô tìm kiếm
    class SearchFieldListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            cafeView.updateSuggestionList();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            cafeView.updateSuggestionList();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            cafeView.updateSuggestionList();
        }
    }

    // Cài đặt sự kiện cho nút hủy tìm kiếm
    class CancelCafeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Cafe> cafeList = cafeDao.getListCafes();
            cafeView.showListCafes(cafeList);
            cafeView.clearSearchField();
        }
    }

    //Sự kiện ấn enter cho tìm kiếm
    class EnterKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            String searchText = cafeView.getSearchField();
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (searchText.isEmpty()) {
                    cafeView.showMessage("Fill in the search box");
                } else {
                    cafeView.updateSearchList(searchText);
                    cafeView.hideSuggestionScrollPane();

                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
}
