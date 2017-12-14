package applications.brightmood;
import java.io.Serializable;
/**
 * Created by florali on 2/12/17.
 */

public class Speech implements Serializable{
    private String word;
    private String domain;
    private String area;
    private String preset;
    private String ctrl;

    public Speech(String word, String domain, String area, String preset, String ctrl) {
        this.word = word;
        this.domain = domain;
        this.area = area;
        this.preset = preset;
        this.ctrl = ctrl;
    }
    public String getWord(){
        return word;
    }
    public void setWord(String word){
        this.word = word;
    }

    public String getArea(){
        return area;
    }
    public String getDomain(){
        return domain;
    }
    public void setDomain(String area){
        this.domain = area;
    }
    public void setArea(String area){
        this.area = area;
    }

    public String getPreset(){
        return preset;
    }
    public void setPreset(String pre){
        this.preset = pre;
    }

    public String getCtrl() {
        return ctrl;
    }
}
