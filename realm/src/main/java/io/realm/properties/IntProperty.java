/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.realm.properties;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import io.realm.internal.TableOrView;
import io.realm.internal.TableQuery;
import io.realm.*;

import android.content.Context;

public class IntProperty implements RealmProperty {

    private RealmTableOrViewList realmList;
    private Realm realm;
    private TableQuery query;
    private Map<String, Integer> columns = new HashMap<String, Integer>();
    //private Class clazz;

    int columnIndex = -1;

    public IntProperty(Context context, String columnName) {
        try {
            this.realm = new Realm(context.getFilesDir());
        } catch(IOException e) {

        }
        //this.clazz = clazz;

        TableOrView dataStore = getTable();
        this.query = dataStore.where();

        for(int i = 0; i < dataStore.getColumnCount(); i++) {
            this.columns.put(dataStore.getColumnName(i), i);
        }

        int columnIndex = columns.get(columnName);
        //TODO:  Throw exception/Error check for proper column name??
    }

    public IntProperty(RealmTableOrViewList realmList, Class clazz, String columnName) {
        this.realmList = realmList;

        this.realm = realmList.getRealm();
        //this.clazz = clazz;

        TableOrView dataStore = getTable();
        this.query = dataStore.where();

        for(int i = 0; i < dataStore.getColumnCount(); i++) {
            this.columns.put(dataStore.getColumnName(i), i);
        }

        int columnIndex = columns.get(columnName);
        //TODO:  Throw exception/Error check for proper column name??
    }

    public TableOrView getTable() {
        if(realmList != null) {
            return realmList.getTable();
        } else {
            //TODO: fix...
            return null;
            //return realm.getTable(clazz);
        }
    }

    // Equal

    public IntProperty eq(String value) {
        this.query.equalTo(columnIndex, value);
        return this;
    }

    public IntProperty eq(int value) {
        this.query.equalTo(columnIndex, value);
        return this;
    }

    public IntProperty eq(long value) {
        this.query.equalTo(columnIndex, value);
        return this;
    }

    public IntProperty eq(double value) {
        this.query.equalTo(columnIndex, value);
        return this;
    }

    public IntProperty eq(float value) {
        this.query.equalTo(columnIndex, value);
        return this;
    }

    public IntProperty eq(boolean value) {
        this.query.equalTo(columnIndex, value);
        return this;
    }

    public IntProperty eq(Date value) {
        this.query.equalTo(columnIndex, value);
        return this;
    }

    // Not Equal

    public IntProperty neq(String value) {
        this.query.notEqualTo(columnIndex, value);
        return this;
    }

    public IntProperty neq(int value) {
        this.query.notEqualTo(columnIndex, value);
        return this;
    }

    public IntProperty neq(long value) {
        this.query.notEqualTo(columnIndex, value);
        return this;
    }

    public IntProperty neq(double value) {
        this.query.notEqualTo(columnIndex, value);
        return this;
    }

    public IntProperty neq(float value) {
        this.query.notEqualTo(columnIndex, value);
        return this;
    }

    //TODO:  No method in Table View
//    public IntProperty neq(boolean value) {
//        this.query.notEqualTo(columnIndex, !value);
//        return this;
//    }

    public IntProperty neq(Date value) {
        this.query.notEqualTo(columnIndex, value);
        return this;
    }

    // Greater Than

    public IntProperty gt(int value) {
        this.query.greaterThan(columnIndex, value);
        return this;
    }

    public IntProperty gt(long value) {
        this.query.greaterThan(columnIndex, value);
        return this;
    }

    public IntProperty gt(double value) {
        this.query.greaterThan(columnIndex, value);
        return this;
    }

    public IntProperty gt(float value) {
        this.query.greaterThan(columnIndex, value);
        return this;
    }

    public IntProperty gt(Date value) {
        this.query.greaterThan(columnIndex, value);
        return this;
    }

    public IntProperty gte(int value) {
        this.query.greaterThanOrEqual(columnIndex, value);
        return this;
    }

    public IntProperty gte(long value) {
        this.query.greaterThanOrEqual(columnIndex, value);
        return this;
    }

    public IntProperty gte(double value) {
        this.query.greaterThanOrEqual(columnIndex, value);
        return this;
    }

    public IntProperty gte(float value) {
        this.query.greaterThanOrEqual(columnIndex, value);
        return this;
    }

    public IntProperty gte(Date value) {
        this.query.greaterThanOrEqual(columnIndex, value);
        return this;
    }

    // Less Than

    public IntProperty lt(int value) {
        this.query.lessThan(columnIndex, value);
        return this;
    }

    public IntProperty lt(long value) {
        this.query.lessThan(columnIndex, value);
        return this;
    }

    public IntProperty lt(double value) {
        this.query.lessThan(columnIndex, value);
        return this;
    }

    public IntProperty lt(float value) {
        this.query.lessThan(columnIndex, value);
        return this;
    }

    public IntProperty lt(Date value) {
        this.query.lessThan(columnIndex, value);
        return this;
    }

    public IntProperty lte(int value) {
        this.query.lessThanOrEqual(columnIndex, value);
        return this;
    }

    public IntProperty lte(long value) {
        this.query.lessThanOrEqual(columnIndex, value);
        return this;
    }

    public IntProperty lte(double value) {
        this.query.lessThanOrEqual(columnIndex, value);
        return this;
    }

    public IntProperty lte(float value) {
        this.query.lessThanOrEqual(columnIndex, value);
        return this;
    }

    public IntProperty lte(Date value) {
        this.query.lessThanOrEqual(columnIndex, value);
        return this;
    }

    // Between

    public IntProperty between(int from, int to) {
        this.query.between(columnIndex, from, to);
        return this;
    }

    public IntProperty between(long from, long to) {
        this.query.between(columnIndex, from, to);
        return this;
    }

    public IntProperty between(double from, double to) {
        this.query.between(columnIndex, from, to);
        return this;
    }

    public IntProperty between(float from, float to) {
        this.query.between(columnIndex, from, to);
        return this;
    }

    public IntProperty between(Date from, Date to) {
        this.query.between(columnIndex, from, to);
        return this;
    }


    // Contains

//Does not apply for Int property
//    public IntProperty contains(String value) {
//        this.query.contains(columnIndex, value);
//        return this;
//    }
//
//    public IntProperty contains(String value, boolean caseSensitive) {
//        this.query.contains(columnIndex, value, caseSensitive);
//        return this;
//    }
//
//    public IntProperty beginsWith(String value) {
//        this.query.beginsWith(columnIndex, value);
//        return this;
//    }
//
//    public IntProperty beginsWith(String value, boolean caseSensitive) {
//        this.query.beginsWith(columnIndex, value, caseSensitive);
//        return this;
//    }
//
//    public IntProperty endsWith(String value) {
//        this.query.endsWith(columnIndex, value);
//        return this;
//    }
//
//    public IntProperty endsWith(String value, boolean caseSensitive) {
//        this.query.endsWith(columnIndex, value, caseSensitive);
//        return this;
//    }

    // Grouping

    public IntProperty beginGroup() {
        this.query.group();
        return this;
    }

    public IntProperty endGroup() {
        this.query.endGroup();
        return this;
    }

    public IntProperty or() {
        this.query.or();
        return this;
    }


    // Aggregates

    // Sum

    public long sum() {
        return this.query.sumInt(columnIndex);
    }

//    public double sum() {
//        return this.query.sumDouble(columnIndex);
//    }
//
//    public double sum() {
//        return this.query.sumFloat(columnIndex);
//    }

    // Average

    public double average() {
        return this.query.averageInt(columnIndex);
    }

//    public double average() {
//        return this.query.averageDouble(columnIndex);
//    }
//
//    public double average() {
//        return this.query.averageFloat(columnIndex);
//    }

    // Min

    public long min() {
        return this.query.minimumInt(columnIndex);
    }

//    public double min() {
//        return this.query.minimumDouble(columnIndex);
//    }
//
//    public float min() {
//        return this.query.minimumFloat(columnIndex);
//    }
//
//    public Date min() {
//        return this.query.minimumDate(columnIndex);
//    }

    // Max

    public long max() {
        return this.query.maximumInt(columnIndex);
    }

    // Execute

//The methods move to RealmObject, but may eventually be interesting in the future
//    public RealmTableOrViewList<E> findAll() {
//        return new RealmTableOrViewList<E>(realm, query.findAll(), clazz);
//    }
//
//    public Integer findFirst() {
//        RealmList<E> result = findAll();
//        if(result.size() > 0) {
//            return findAll().get(0);
//        } else {
//            return null;
//        }
//    }

}
