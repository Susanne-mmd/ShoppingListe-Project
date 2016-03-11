package com.example.shoppinglist;

public class Products {		
		//just an ID number, to distinguish products with the same name from
		//each other.
		private String name;
		private int quantity;
		private int price;

		public Products() {
			
		}
		
		public Products(int id, String name, int quantity, int price) {
			this.name = name;
			this.quantity = quantity;
			this.price = price;
		}
		

		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
		public int getQuantity() {
			return this.quantity;
		}
		
		public void setPrice(int price){
			this.price = price;
		}
		
		public int getPrice(){
			return this.price;
		}
	}


