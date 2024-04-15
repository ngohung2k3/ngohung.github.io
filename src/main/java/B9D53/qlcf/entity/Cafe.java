package B9D53.qlcf.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cafe")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cafe implements Serializable {
    private static final long serialVersionUID = 1L;
    private int STT;
    private String name;
    private int price;

    public Cafe() {
    }

    public Cafe(int STT, String name, int price) {
        super();
        this.STT = STT;
        this.name = name;        
        this.price = price;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    
}
