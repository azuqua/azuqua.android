package org.azuqua.android.api.models;

/**
 * Created by sasidhar on 08/09/16.
 */

public class Preview {
    private String module;
    private String name;
    private boolean kernel;

    public Preview() {
    }

    public Preview(String module, String name, boolean kernel) {
        this.module = module;
        this.name = name;
        this.kernel = kernel;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKernel() {
        return kernel;
    }

    public void setKernel(boolean kernel) {
        this.kernel = kernel;
    }
}
