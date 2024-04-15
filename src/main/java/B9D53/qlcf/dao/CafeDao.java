package B9D53.qlcf.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import B9D53.qlcf.entity.Cafe;
import B9D53.qlcf.entity.CafeXML;
import B9D53.qlcf.utils.FileUtils;

/**
 * StudentDao class
 *
 * @author viettuts.vn
 */
public class CafeDao {

    private static final String CAFE_FILE_NAME = "cafe.xml";
    private List<Cafe> listCafes;
    private int stt;

    public CafeDao() {
        this.listCafes = readListCafes();
        if (listCafes == null) {
            listCafes = new ArrayList<Cafe>();
        }
    }

    /**
     * Lưu các đối tượng cafe vào file cafe.xml
     *
     * @param cafes
     *
     */
    public void writeListCafes(List<Cafe> cafes) {
        CafeXML cafeXML = new CafeXML();
        cafeXML.setCafe(cafes);
        FileUtils.writeXMLtoFile(CAFE_FILE_NAME, cafeXML);
    }

    /**
     * Đọc các đối tượng cafe từ file cafe.xml
     *
     * @return list cafe
     */
    public List<Cafe> readListCafes() {
        List<Cafe> list = new ArrayList<Cafe>();
        CafeXML cafeXML = (CafeXML) FileUtils.readXMLFile(CAFE_FILE_NAME, CafeXML.class);
        if (cafeXML != null) {
            list = cafeXML.getCafe();
        }
        return list;
    }

    /**
     * thêm cafe vào listCafes và lưu listCafes vào file
     *
     * @param cafe
     */
    public void add(Cafe cafe) {
        stt = 1;
        if (listCafes != null && !listCafes.isEmpty()) {
            stt = listCafes.size() + 1;
        }
        cafe.setSTT(stt);
        listCafes.add(cafe);
        writeListCafes(listCafes);
    }

    /**
     * cập nhật cafe vào listCafes và lưu listCafes vào file
     *
     * @param cafe
     */
    public void edit(Cafe cafe) {
        int size = listCafes.size();
        for (int i = 0; i < size; i++) {
            if (listCafes.get(i).getSTT() == cafe.getSTT()) {
                listCafes.get(i).setName(cafe.getName());
                listCafes.get(i).setPrice(cafe.getPrice());
                writeListCafes(listCafes);
                break;
            }
        }
    }

    /**
     * xóa cafe từ listCafes và lưu listCafes vào file
     *
     * @param cafe
     * @return
     */
    public boolean delete(Cafe cafe) {
        boolean isFound = false;
        int size = listCafes.size();
        for (int i = 0; i < size; i++) {
            if (listCafes.get(i).getSTT() == cafe.getSTT()) {
                cafe = listCafes.get(i);
                isFound = true;
                listCafes.remove(cafe);
                break;
            }
        }
        if (isFound) {
            updateSTTsAfterDelete();
            writeListCafes(listCafes);
            return true;
        }
        return false;
    }

    private void updateSTTsAfterDelete() {
        int size = listCafes.size();
        for (int i = 0; i < size; i++) {
            listCafes.get(i).setSTT(i + 1);
        }
    }

    public void sortCafeByName() {
        Collections.sort(listCafes, new Comparator<Cafe>() {
            @Override
            public int compare(Cafe cafe1, Cafe cafe2) {
                return cafe1.getName().compareTo(cafe2.getName());
            }
        });
    }

    public void sortCafeBySTT() {
        Collections.sort(listCafes, new Comparator<Cafe>() {
            @Override
            public int compare(Cafe cafe1, Cafe cafe2) {
                if (cafe1.getSTT() > cafe2.getSTT()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public void sortCafeByPrice() {
        Collections.sort(listCafes, new Comparator<Cafe>() {
            @Override
            public int compare(Cafe cafe1, Cafe cafe2) {
                if (cafe1.getPrice() > cafe2.getPrice()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public List<Cafe> getListCafes() {
        return listCafes;
    }

    public void setListCafes(List<Cafe> listCafes) {
        this.listCafes = listCafes;
    }

    public List<String> getAllCafeNames() {
        List<String> names = new ArrayList<>();
        for (Cafe cafe : listCafes) {
            names.add(cafe.getName());
        }
        return names;
    }

    public List<Integer> getAllCafePrices() {
        List<Integer> prices = new ArrayList<>();
        for (Cafe cafe : listCafes) {
            prices.add(cafe.getPrice());
        }
        return prices;
    }

    public List<Cafe> searchCafeByName(String name) {
        List<Cafe> result = new ArrayList<>();
        for (Cafe cafe : listCafes) {
            if (cafe.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(cafe);
            }
        }
        return result;
    }

    public List<Cafe> searchCafeByPrice(int price) {
        List<Cafe> matchingCafes = new ArrayList<>();
        for (Cafe cafe : listCafes) {
            if (cafe.getPrice() == price) {
                matchingCafes.add(cafe);
            }
        }
        return matchingCafes;
    }
}
