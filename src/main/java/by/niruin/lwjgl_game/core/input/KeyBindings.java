package by.niruin.lwjgl_game.core.input;

import java.util.HashMap;
import java.util.Map;

public class KeyBindings {
    private final Map<Action, Integer> bindings = new HashMap<>();

    public int getBind(Action action) {
        return bindings.get(action);
    }

    public void putBind(Action action, int key) {
        bindings.put(action, key);
    }
}
