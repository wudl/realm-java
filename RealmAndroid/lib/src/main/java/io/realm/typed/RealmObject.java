package io.realm.typed;

import io.realm.Row;

public abstract class RealmObject {

    private Row row;
    long realmAddedAtRowIndex = -1;

    protected Row realmGetRow() {
        return row;
    }

    protected void realmSetRow(Row row) {
        this.row = row;
    }

    public abstract String[] getTableNames();

    public abstract int[] getTableTypes();

    public abstract String getTableName();

}
