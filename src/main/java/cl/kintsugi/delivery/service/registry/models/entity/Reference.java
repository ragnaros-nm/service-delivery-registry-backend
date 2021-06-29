package cl.kintsugi.delivery.service.registry.models.entity;

public class Reference {

    private String type;
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Reference(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

