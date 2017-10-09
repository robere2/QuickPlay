package co.bugg.quickplay.config;

/**
 * BOOLEAN and KEYBIND are always
 * buttons.
 * TODO: Add support for text boxes.
 */
public enum ConfigElementType {
    BOOLEAN("Boolean"),
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
