package data;

/**
 * Created by Rinik on 13/10/17.
 */

public class History {
    public String name,path,time,phone;
    public boolean isHaving=false,isSaved;
    public int type;
    public History(String name,String phone,String path, String time, boolean isHaving) {
        this.name = name;
        this.path = path;
        this.time = time;
        this.phone=phone;
        this.isHaving= isHaving;
    }

    public History() {

    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public void setPhone(String phone) {
        this.phone = phone;

    }

    public void setHaving(boolean having) {
        isHaving = having;
    }
}
