package B9D53.qlcf.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cafes")
@XmlAccessorType(XmlAccessType.FIELD)
public class CafeXML {
    
    private List<Cafe> cafe;

    public List<Cafe> getCafe() {
        return cafe;
    }

    public void setCafe(List<Cafe> cafe) {
        this.cafe = cafe;
    }
}
