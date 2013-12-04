//Nick Scalzi
//Saturday, October 05, 2013

package com.example.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	double op_1 = 0; //left operand
	double op_2 = 0; //right operand
	String operator = "none"; //holds the current operator
	TextView text;
	
	boolean empty = true; //true if operand text can be overwritten, false if user are still inputting it	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text=  (TextView) findViewById(R.id.text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//onClick function for number buttons
	public void digitHandler(View v) {
		Button b = (Button) v;
		String str;
		
		if (empty) { //if we are entering a new operand
			if (operator == "=") { // if we've displayed a previous result, and the user hasn't given a new operator, treat this as a new operation
				op_1 = 0;
				op_2 = 0;
				operator = "none";
			}
			
			str = b.getText().toString();
			text.setText(str);			
			empty = false;
		} 
		else { //if we are still entering an operand
			str = text.getText().toString() + b.getText().toString();
			text.setText(str);
		}
	}

	//onClick function for operator buttons
	public void operatorHandler(View v) {
		TextView text = (TextView) findViewById(R.id.text);

		if (operator == "none") { //if this is the first operator in the equation
			operator = operatorById(v); //get the operator of the button pressed
			op_1 = Double.parseDouble(text.getText().toString()); //save the current operand
			empty = true; //prepare for the next operand
		}

		else {
			if (!empty) { //if a new operand has been entered
				op_2 = Double.parseDouble(text.getText().toString()); //save it as the right operand

				if (operator == "+") {
					op_1 = add(op_1, op_2);
				} else if (operator == "-") {
					op_1 = subtract(op_1, op_2);
				} else if (operator == "*") {
					op_1 = multiply(op_1, op_2);
				} else if (operator == "/") {
					op_1 = divide(op_1, op_2);
				}
			}
			
			text.setText(String.valueOf(op_1)); //output the result
			
			op_2 = 0; //reset the right operand
			operator = operatorById(v); //get the next operator
			empty = true; //prepare for the next operand
		}

		if (operator == "=") {
			text.setText(String.valueOf(op_1)); //output the result of the operation
		}
	}

	//onClick function for 'clear' button
	public void clearHandler(View v) {		
		op_1 = 0;
		op_2 = 0;
		operator = "none";		
		empty = true;
		
		text.setText("0");
	}

	double add(double o1, double o2) {
		return o1 + o2;
	}
	double subtract(double o1, double o2) {
		return o1 - o2;
	}
	double multiply(double o1, double o2) {
		return o1 * o2;
	}
	double divide(double o1, double o2) {
		if(o2==0)
		{
		Toast.makeText(MainActivity.this, "Can't Divide By Zero", Toast.LENGTH_SHORT).show();
		return o1;
		}			
		else
		return o1 / o2;
	}
	
	//sets the current operator according to the ID of the input button
	public String operatorById(View v) {
		String operator = "none";
		
		if (v.getId() == R.id.add) {
			operator = "+";
		} else if (v.getId() == R.id.subtract) {
			operator = "-";
		} else if (v.getId() == R.id.multiply) {
			operator = "*";
		} else if (v.getId() == R.id.divide) {
			operator = "/";
		} else if (v.getId() == R.id.equals) {
			operator = "=";
		}
		
		return operator;
	}
}
