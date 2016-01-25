package org.azuqua.android.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.azuqua.android.api.Azuqua;
import org.azuqua.android.api.callbacks.AsyncRequest;

/**
 * Created by sasidhar on 14-Oct-15.
 */
public class Flo implements Parcelable {

    private int id;
    private int user_id;
    private int org_id;
    private String alias;
    private String version;
    private String name;
    private String module;
    private boolean active;
    private boolean published;
    private String security_level;
    private String description;
    private long execution_count;
    private String last_run;
    private String updated;
    private String created;
    private String last_run_success;
    private String last_run_fail;
    private User user;
    private Blob[] blob;
    private static Gson gson = new Gson();

    public Flo() {
        //empty constructor
    }

    protected Flo(Parcel in) {
        id = in.readInt();
        user_id = in.readInt();
        org_id = in.readInt();
        alias = in.readString();
        version = in.readString();
        name = in.readString();
        module = in.readString();
        active = in.readByte() != 0;
        published = in.readByte() != 0;
        security_level = in.readString();
        description = in.readString();
        execution_count = in.readLong();
        last_run = in.readString();
        updated = in.readString();
        created = in.readString();
        last_run_success = in.readString();
        last_run_fail = in.readString();
        user = gson.fromJson(in.readString(), User.class);
        blob = gson.fromJson(in.readString(), Blob[].class);
    }

    public static final Creator<Flo> CREATOR = new Creator<Flo>() {
        @Override
        public Flo createFromParcel(Parcel in) {
            return new Flo(in);
        }

        @Override
        public Flo[] newArray(int size) {
            return new Flo[size];
        }
    };

    public Flo(int id, int user_id, int org_id, String alias, String version, String name, String module, boolean active, boolean published, String security_level, String description, long execution_count, String last_run, String updated, String created, String last_run_success, String last_run_fail, User user, Blob[] blob) {
        this.id = id;
        this.user_id = user_id;
        this.org_id = org_id;
        this.alias = alias;
        this.version = version;
        this.name = name;
        this.module = module;
        this.active = active;
        this.published = published;
        this.security_level = security_level;
        this.description = description;
        this.execution_count = execution_count;
        this.last_run = last_run;
        this.updated = updated;
        this.created = created;
        this.last_run_success = last_run_success;
        this.last_run_fail = last_run_fail;
        this.user = user;
        this.blob = blob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getExecution_count() {
        return execution_count;
    }

    public void setExecution_count(long execution_count) {
        this.execution_count = execution_count;
    }

    public String getLast_run() {
        return last_run;
    }

    public void setLast_run(String last_run) {
        this.last_run = last_run;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLast_run_success() {
        return last_run_success;
    }

    public void setLast_run_success(String last_run_success) {
        this.last_run_success = last_run_success;
    }

    public String getLast_run_fail() {
        return last_run_fail;
    }

    public void setLast_run_fail(String last_run_fail) {
        this.last_run_fail = last_run_fail;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setBlob(Blob[] blob) {
        this.blob = blob;
    }

    public Blob[] getBlob() {
        return blob;
    }

    public void getInputs(Azuqua azuqua, String accessKey, String accessSecret, AsyncRequest asyncRequest) {
        azuqua.getFloInputs(this.alias, accessKey, accessSecret, asyncRequest);
    }

    public void invoke(Azuqua azuqua, String data, String accessKey, String accessSecret, AsyncRequest asyncRequest) {
        Boolean isMonitor = this.module.equals("azuquamobile") ? true : false;
        azuqua.invokeFlo(isMonitor, this.alias, data, accessKey, accessSecret, asyncRequest);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user_id);
        dest.writeInt(org_id);
        dest.writeString(alias);
        dest.writeString(version);
        dest.writeString(name);
        dest.writeString(module);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (published ? 1 : 0));
        dest.writeString(security_level);
        dest.writeString(description);
        dest.writeLong(execution_count);
        dest.writeString(last_run);
        dest.writeString(updated);
        dest.writeString(created);
        dest.writeString(last_run_success);
        dest.writeString(last_run_fail);
        dest.writeString(gson.toJson(user).toString());
        dest.writeString(gson.toJson(blob).toString());
    }
}
