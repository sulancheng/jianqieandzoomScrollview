package com.example.administrator.myjianqieqi.bean;

/**
 * Created by Administrator
 * on 2017/9/14.
 */

public class ProgressInfo {
    public ProgressInfo(String name,String type) {
        this.name = name;this.type = type;
    }
    String type;
    String name;
    int progress;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "ProgressInfo{" +
                "name='" + name + '\'' +
                ", progress=" + progress +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgressInfo that = (ProgressInfo) o;

        if (progress != that.progress) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + progress;
        return result;
    }
}
