package net.justacoder.shadowclient.main.ui;

import net.justacoder.shadowclient.main.ui.text.TextField;
import java.util.ArrayList;
import java.util.List;

public interface ShadowClientScreen {

    boolean capturesKeypress(int key);

    List<TextField> allTextFields = new ArrayList<>();

    default void addTextField(TextField field) {
        allTextFields.add(field);
    }

    default List<TextField> getAllTextFields() {
        return allTextFields;
    }

}
