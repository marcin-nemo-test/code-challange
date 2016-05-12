package tk.zielony.codechallange.api;

/**
 * Created by Marcin on 2016-05-12.
 */
public abstract class DataEvent<Type> {
    Type data;

    public DataEvent() {
    }

    public DataEvent(Type data) {
        this.data = data;
    }

    public void setData(Type data) {
        this.data = data;
    }

    public Type getData() {
        return data;
    }

    public abstract Class<Type> getDataClass();
}