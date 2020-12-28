package models;

import java.util.Objects;

abstract public class Model {
    // Уникальный ключ объекта
    protected final Integer id;

    public Model(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Model model = (Model) o;
        return getId().equals(model.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
