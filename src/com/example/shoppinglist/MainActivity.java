package com.example.shoppinglist;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
		
	ArrayList<String> itemlist = new ArrayList<String>();
	ArrayList<Double> pricelist = new ArrayList<Double>();
	
	EditText name;
	EditText quantity;
	EditText price;
	EditText total;
	public int selected;
	double sum = 0;
	
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		name = (EditText)findViewById(R.id.name);
		quantity = (EditText)findViewById(R.id.quantity);
		price = (EditText)findViewById(R.id.price);
		total = (EditText)findViewById(R.id.total);
			
		DataBase dbHandler = new DataBase(this);
				
		ArrayList<Products> productList = new ArrayList<Products>(); 		
		productList = dbHandler.getAllItems();
		pricelist = dbHandler.getAllPrices();
		totalSum();
		for(int i = 0;i < productList.size();i++){
		itemlist.add("produkt: " + productList.get(i).getName() + " , antal: " +  productList.get(i).getQuantity() + " , pris: " +  productList.get(i).getPrice() + " kr.");			
		}
		
		getActionBar().setHomeButtonEnabled(true);		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, itemlist);
			
		ListView listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		setListAdapter(adapter);					
		
		SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
		String name = prefs.getString("name", "");
		toast("Welcome back " + name + "!");
		
	}	
	
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.action_delete:
			
			DataBase dbHandler = new DataBase(this);
			
		    dbHandler.deleteAll();
			
			DeleteAll dialog = new DeleteAll() {
				@Override
				protected void delete() {
					itemlist.clear();
					sum=0;
					total.setText("0");
					adapter.notifyDataSetChanged();
					toast("All item has been deleted");
				}
				
				@Override
				protected void cancel() {
					toast("Cancel");
				}
			};
			
			dialog.show(getFragmentManager(), "MyFragment");		
			return true;
			
		case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, 1);
			return true;
			
		case R.id.action_sort:
			 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			 builder.setTitle("sort list by")
			 .setItems(R.array.Items, new DialogInterface.OnClickListener() {
			 
				 public void onClick(DialogInterface dialog, int which) {        	   
					 DataBase dbHandler = new DataBase(getActivity());   	  
					 
					 if(which == 0){			            		 
						 ArrayList<Products> productList = new ArrayList<Products>(); 		
						 productList = dbHandler.sortProduct(0);
						 itemlist.clear();
						 
						 for(int i = 0;i < productList.size();i++){
							 itemlist.add("produkt: " + productList.get(i).getName() + " , antal: " +  productList.get(i).getQuantity() + " , pris: " +  productList.get(i).getPrice() + " kr.");
						 }
			            			
						 getActionBar().setHomeButtonEnabled(true);		
						 adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, itemlist);
			            				
						 ListView listView = getListView();
						 listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
						 
						 setListAdapter(adapter);
			            		  
						 toast("Your list is sorted by name");
					 }
			            	   
					 if(which == 1){
						 ArrayList<Products> productList = new ArrayList<Products>(); 		
						 productList = dbHandler.sortProduct(1);
						 itemlist.clear();
						 for(int i = 0;i < productList.size();i++){
							 itemlist.add("produkt: " + productList.get(i).getName() + " , antal: " +  productList.get(i).getQuantity() + " , pris: " +  productList.get(i).getPrice() + " kr.");			
						 }
			            			
						 getActionBar().setHomeButtonEnabled(true);		
						 adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, itemlist);
			            				
						 ListView listView = getListView();
						 listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

						 setListAdapter(adapter);
						 toast("Your list is sorted by Quantity");
					 }
			            	   
					 if(which == 2){
						 ArrayList<Products> productList = new ArrayList<Products>(); 		
						 productList = dbHandler.sortProduct(2);
						 itemlist.clear();
						 for(int i = 0;i < productList.size();i++){
							 itemlist.add("produkt: " + productList.get(i).getName() + " , antal: " +  productList.get(i).getQuantity() + " , pris: " +  productList.get(i).getPrice() + " kr.");			
						 }
			            			
						 getActionBar().setHomeButtonEnabled(true);		
						 adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, itemlist);
			            				
						 ListView listView = getListView();
						 listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

						 setListAdapter(adapter);
						 toast("Your list is sorted by price");
					 }
				 }
			               
			 });
			   AlertDialog dialog1 = builder.create();
			   dialog1.show();
			
			return true;
		}
		return false;
	}
			
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode==1) {
				Collections.sort(itemlist);
				for (int i=0; i<itemlist.size(); i++)
				adapter.notifyDataSetChanged();
			}
			super.onActivityResult(requestCode, resultCode, data);
		}


	private Context getActivity() {
		// TODO Auto-generated method stub
		return this;
	}



	protected void totalSum(){
		String sumStr = "";
		sum = 0;

		for(double p : pricelist){
			sum += p;
		}
		
		sumStr = String.valueOf(sum);
		total.setText(sumStr);
	}
	
	public void addItem(View view){	
		DataBase dbHandler = new DataBase(this);
		
		if(name.getText().toString().length() <= 0){
			toast("You need to enter name!");
		}
		if(quantity.getText().toString().equals(""))
		{
			toast("You need to enter guantity!");
		}
		
		if(price.getText().toString().equals(""))
		{
			toast("You need to enter price!");
		}		
		else{
		
		Products product = new Products();	
		
		itemlist.add("produkt: " + name.getText().toString() + " , antal: " + quantity.getText().toString() + " , pris: " + price.getText().toString() + " kr.");	
		String quantityStr = quantity.getText().toString().trim();
		double quantitys = Double.parseDouble(quantityStr);
		String priceStr = price.getText().toString().trim();
		double prices = Double.parseDouble(priceStr);
		double totalItemPrice = quantitys * prices;
		pricelist.add(totalItemPrice);	
		totalSum();
		
		product.setName(name.getText().toString());
		product.setQuantity(Integer.parseInt(quantity.getText().toString()));
		product.setPrice(Integer.parseInt(price.getText().toString()));
		
		adapter.notifyDataSetChanged();
		
		dbHandler.addProduct(product);
		name.setText("");
		quantity.setText("");
		price.setText("");
		
		SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
		boolean sort = prefs.getBoolean("sort", false);
		if (sort == true){
			Collections.sort(itemlist);
			for (int i=0; i<itemlist.size(); i++)
			System.out.println(itemlist.get(i));
			adapter.notifyDataSetChanged();
			}
		}
	}
	
	
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		new DataBase(this);
		selected = position;
		super.onListItemClick(l ,v, position, id);
	}
	
	
	public void clearItem(View view){	
		itemlist.remove(selected);		
		pricelist.remove(selected);		
		totalSum();
		adapter.notifyDataSetChanged();		
		toast("Item has ben deleted!");
	}
	
	
	public void search (View view) {
		   DataBase dbHandler = new DataBase(this);
		
		   Products product = dbHandler.findProduct(name.getText().toString());

		   if (product != null) {
			   name.setText(String.valueOf(product.getName()));
			   
			   quantity.setText(String.valueOf(product.getQuantity()));
			   
			   price.setText(String.valueOf(product.getPrice()));
	       } else {
		         name.setText("No Match");
	       }        	
	   }
	
	
	
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		name = (EditText)findViewById(R.id.name);
		quantity = (EditText)findViewById(R.id.quantity);
		price = (EditText)findViewById(R.id.price);
		outState.putString("name", name.getText().toString());
		outState.putString("quantity", quantity.getText().toString());	
		outState.putString("price", price.getText().toString());
		outState.putString("total", total.getText().toString());
		outState.putStringArrayList("list", itemlist);
	}
	
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		
		name = (EditText)findViewById(R.id.name);
		quantity = (EditText)findViewById(R.id.quantity);
		String text = savedState.getString("name");
		String text1 = savedState.getString("quantity");
		String text2 = savedState.getString("price");
		String text3 = savedState.getString("total");
		
		itemlist = savedState.getStringArrayList("list");
		name.setText(text);
		quantity.setText(text1);
		price.setText(text2);
		total.setText(text3);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, itemlist);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
		
	}

	
	private void toast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
