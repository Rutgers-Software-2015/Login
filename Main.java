package Login;

import KitchenStaff.KitchenStaffHandler;
import Shared.ADT.ExampleOrders;

public class Main {

	public static void main(String[] args)
	{
		//Used for testing till database is fully functioning.
		ExampleOrders test=new ExampleOrders();
		KitchenStaffHandler.addTableOrder(test.table2);
		KitchenStaffHandler.addTableOrder(test.table3);
		KitchenStaffHandler.addTableOrder(test.table5);
		KitchenStaffHandler.addTableOrder(test.table4);
		KitchenStaffHandler.addTableOrder(test.table1);
		
		
		
		new LoginWindow();	

	}

}
