package tk.zielony.codechallange.api;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Marcin on 2016-05-12.
 */
public class DataEvent<Type> {
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

    public Class<Type> getDataClass() {
        return ((Class<Type>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getClass());
    }
}