package io.realm.test;

import java.util.Date;


@io.realm.base.RealmClass
public class User  {
	
	@io.realm.base.Ignore private boolean state;
	private String userName;
	private float  measurement;
	private Float  measurementF;
	private int theNumber;
	private Integer theNumberI;
	private long theBigNumber;
	private Long theBigNumberL;
	private double theDouble;
	private Double theDoubleL;
	private Date theDate;
	
	public User()
	{
	}
	 
}

