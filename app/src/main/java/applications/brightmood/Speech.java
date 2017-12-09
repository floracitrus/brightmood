package applications.brightmood;
import java.io.Serializable;
/**
 * Created by florali on 2/12/17.
 */

public class Speech implements Serializable{
    private String word;
    private String area;
    private String preset;

    public Speech(String word, String area, String preset) {
        this.word = word;
        this.area = area;
        this.preset = preset;
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
    public void setArea(String area){
        this.area = area;
    }

    public String getPreset(){
        return preset;
    }
    public void setPreset(String pre){
        this.preset = pre;
    }

}
