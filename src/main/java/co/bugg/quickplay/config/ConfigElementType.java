package co.bugg.quickplay.config;

public enum ConfigElementType {
    BOOLEAN("Boolean"),
    INTEGER("Integer"),
    FLOAT("Float"),
    STRING("String"),
    KEYBIND("Keybind");

    public String name;

    ConfigElementType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
