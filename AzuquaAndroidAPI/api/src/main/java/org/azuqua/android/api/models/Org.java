package org.azuqua.android.api.models;


/**
 * Created by sasidhar on 14-Oct-15.
 */

public class Org {
    private int id;
    private String name;
    private String display_name;
    private String namespace;
    private String trial_start;
    private String trial_end;
    private boolean trial_used;
    private boolean active;
    private boolean enabled;
    private int isSelected;

   public Org(){
       //empty constructor
   }

    public Org(int id, String name, String display_name, String namespace, String trial_start, String trial_end, boolean trial_used, boolean active, boolean enabled, int isSelected) {
        this.id = id;
        this.name = name;
        this.display_name = display_name;
        this.namespace = namespace;
        this.trial_start = trial_start;
        this.trial_end = trial_end;
        this.trial_used = trial_used;
        this.active = active;
        this.enabled = enabled;
        this.isSelected = isSelected;
    }

    public Org(int id, String name, String display_name, String namespace, String trial_start, String trial_end, boolean trial_used, boolean active, boolean enabled) {
        this.id = id;
        this.name = name;
        this.display_name = display_name;
        this.namespace = namespace;
        this.trial_start = trial_start;
        this.trial_end = trial_end;
        this.trial_used = trial_used;
        this.active = active;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getTrial_start() {
        return trial_start;
    }

    public void setTrial_start(String trial_start) {
        this.trial_start = trial_start;
    }

    public String getTrial_end() {
        return trial_end;
    }

    public void setTrial_end(String trial_end) {
        this.trial_end = trial_end;
    }

    public boolean isTrial_used() {
        return trial_used;
    }

    public void setTrial_used(boolean trial_used) {
        this.trial_used = trial_used;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
