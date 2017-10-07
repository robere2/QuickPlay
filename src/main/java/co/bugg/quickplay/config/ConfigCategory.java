package co.bugg.quickplay.config;

public enum ConfigCategory {
    GENERAL("General"),
    KEYBINDS("Keybinds");

    public String name;

    ConfigCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
