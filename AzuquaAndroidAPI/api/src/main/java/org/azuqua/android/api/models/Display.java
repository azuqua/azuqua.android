package org.azuqua.android.api.models;

import java.util.List;

/**
 * Created by sasidhar on 08/09/16.
 */

public class Display {
    private List<Preview> preview;

    public Display() {
    }

    public Display(List<Preview> preview) {
        this.preview = preview;
    }

    public List<Preview> getPreview() {
        return preview;
    }

    public void setPreview(List<Preview> preview) {
        this.preview = preview;
    }
}
