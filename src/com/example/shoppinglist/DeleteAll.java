package com.example.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteAll extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setTitle("Confirmation");
		alert.setMessage("Are you sure?");
	    alert.setPositiveButton("Yes", pListener);
		alert.setNegativeButton("No", nListener);

		return alert.create();
	}

	DialogInterface.OnClickListener pListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {				
			delete();
		}
	};

	DialogInterface.OnClickListener nListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {	
			cancel();
		}
	};

	protected void delete() 
	{
		
	}
	protected void cancel()
	{
		
	}
}