package com.axxes.traineeship.testing.integration;

import java.util.Objects;

public class CatalogItem {

    private String groupName;
    private String name;

    public CatalogItem() {
        // pojo constructor
    }

    public CatalogItem(String groupName, String name) {
        this.groupName = groupName;
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogItem that = (CatalogItem) o;
        return Objects.equals(groupName, that.groupName) &&
               name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, name);
    }

}
