package io.realm.test;


@io.realm.base.RealmClass

public class User {
	public User()
	{
		_state=false;
	}
	
	@io.realm.base.Ignore private boolean _state;
	
}

