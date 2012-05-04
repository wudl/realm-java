package com.tightdb.lib;

import com.tightdb.TableBase;
import com.tightdb.TableQuery;

public class BinaryRowsetColumn<Cursor, Query> extends BinaryQueryColumn<Cursor, Query> implements RowsetColumn<byte[]> {

	public BinaryRowsetColumn(EntityTypes<?, ?, Cursor, Query> types, TableBase table, TableQuery query, int index, String name) {
		super(types, table, query, index, name);
	}

	@Override
	public byte[][] getAll() {
		int count = table.getCount();
		byte[][] values = new byte[count][];
		for (int i = 0; i < count; i++) {
			values[i] = table.getBinaryData(columnIndex, i);
		}
		return values;
	}

	@Override
	public void setAll(byte[] value) {
		int count = table.getCount();
		for (int i = 0; i < count; i++) {
			table.setBinaryData(columnIndex, i, value);
		}
	}

}
