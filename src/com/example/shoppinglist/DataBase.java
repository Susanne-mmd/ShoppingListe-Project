package com.example.shoppinglist;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DataBase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "productDB.db";
	private static final String TABLE_PRODUCTS = "products";

	public static final String COLUMN_PRODUCTNAME = "productname";
	public static final String COLUMN_QUANTITY = "quantity";
	public static final String COLUMN_PRICE = "price";
	
	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
	             TABLE_PRODUCTS + "(" + COLUMN_PRODUCTNAME + " TEXT," + COLUMN_QUANTITY + " INTEGER," + COLUMN_PRICE + " INTEGER" + ")";
	      db.execSQL(CREATE_PRODUCTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		onCreate(db);
	}
	
	public ArrayList<Products> getAllItems(){
		ArrayList<Products> products = new ArrayList<Products>();
		String query = "Select * FROM " + TABLE_PRODUCTS;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
			while (cursor.moveToNext()){
				Products product = new Products();
				product.setName(cursor.getString(0));
				product.setQuantity(cursor.getInt(1));
				product.setPrice(cursor.getInt(2));
			}
			cursor.close();
			return products;
	}
	
	public ArrayList<Double> getAllPrices(){
		ArrayList<Double> prices = new ArrayList<Double>();
		String query = "Select * FROM " + TABLE_PRODUCTS;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
			while (cursor.moveToNext()){
				int price = cursor.getInt(1) * cursor.getInt(2);
				prices.add((double) price);
			}
			cursor.close();
			db.close();
			return prices;
	}
	

	public void addProduct(Products product) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCTNAME, product.getName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_PRICE, product.getPrice());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
	}
	
	public ArrayList<Products> sortProduct(int method){
		 ArrayList<Products>products = new ArrayList<Products>();
		
		String query = null;
		if(method == 0){
			query = "Select * FROM " + TABLE_PRODUCTS + " ORDER BY " + COLUMN_PRODUCTNAME + " ASC";
		}
		
		else if(method == 1){
			query = "Select * FROM " + TABLE_PRODUCTS + " ORDER BY " + COLUMN_QUANTITY + " ASC";
		}
		
		else if(method == 2){
			query = "Select * FROM " + TABLE_PRODUCTS + " ORDER BY " + COLUMN_PRICE + " ASC";
		}
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);		
			
		while(cursor.moveToNext()){	
			Products product = new Products();	
			product.setName(cursor.getString(0));
			product.setQuantity(cursor.getInt(1));
			product.setPrice(cursor.getInt(2));	
			products.add(product);
		}
		cursor.close();		
		db.close();	
		return products;
	}


	public Products findProduct(String name) {
		String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + name + "\"";	
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);		
		Products product = new Products();

		if (cursor.moveToFirst()) {
			product.setName(cursor.getString(0));
			product.setQuantity(cursor.getInt(1));
			product.setPrice(cursor.getInt(2));
			cursor.close();
		} else {
			product = null;
		}
		db.close();
		return product;
	}
	
	public boolean deleteProduct(String name) {
		boolean result = false;		
		String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + name + "\"";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		Products product = new Products();
		if (cursor.moveToFirst()) {
			product.setName(name);
			db.delete(TABLE_PRODUCTS, COLUMN_PRODUCTNAME + " = ?", new String[] { String.valueOf(name) });
			cursor.close();
			result = true;
		}
		db.close();
		return result;
	}
	
	public void deleteAll() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		onCreate(db);
	}

}
