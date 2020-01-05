import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.util.*;
import java.awt.Font;


class NotEnoughUnitsException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9111930546562233056L;
	String msg;
	
	NotEnoughUnitsException(String msg){
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
}

class ItemNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9111930546562233056L;
	String msg;
	
	ItemNotFoundException(String msg){
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
}
//error msg
class ErrorMsg {

	JFrame frame;

	public ErrorMsg(String str) {
		initialize(str);
	}

	private void initialize(String str) {
		frame = new JFrame();
		frame.setBounds(100, 100, 327, 156);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("errorNew.png"));
		lblNewLabel.setBounds(26, 33, 69, 57);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNotEnoughUnits = new JLabel(str);
		lblNotEnoughUnits.setBounds(108, 52, 154, 15);
		frame.getContentPane().add(lblNotEnoughUnits);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		btnOk.setBounds(221, 82, 69, 25);
		frame.getContentPane().add(btnOk);
	}
}

//error msg end

interface PaymentStrategy {
	void pay(float amount);
}

class Paytm implements PaymentStrategy {
	long phoneNumber;
	int OTP;

	Paytm(long phoneNumber, int OTP) {
		this.phoneNumber = phoneNumber;
		this.OTP = OTP;
	}

	public String toString() {
		return phoneNumber + ":" + OTP;
	}

	public void pay(float amount) {
		System.out.println(amount + " Paid via Paytm");
	}
}

class Card implements PaymentStrategy {
	String name;
	String cardNumber;
	int cvv;
	int dateOfExpiry;

	Card(String name, String cardNumber, int cvv, int dateOfExpiry) {
		this.name = name;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.dateOfExpiry = dateOfExpiry;
	}

	public String toString() {
		return name + ":" + cardNumber + ":" + cvv + ":" + dateOfExpiry;
	}

	public void pay(float amount) {
		System.out.println(amount + " Paid via Card");
	}
}

class Cash implements PaymentStrategy {
	String customerName;

	Cash(String customerName) {
		this.customerName = customerName;
	}

	public String toString() {
		return customerName;
	}

	public void pay(float amount) {
		System.out.println(amount + " Paid via Cash");
	}
}

class Items {
	private String name;
	private float price;
	private int units;

	Items() {

	}

	Items(String name, float price, int units) {
		this.name = name;
		this.price = price;
		this.units = units;
	}

	void addUnits(int units) {
		this.units += units;
	}

	void removeUnits(int units) {
		this.units -= units;
	}

	void setUnits(int units) {
		this.units = units;
	}

	int getUnits() {
		return units;
	}

	String getName() {
		return name;
	}

	float getPrice() {
		return price;
	}

	public String toString() {
		return "Name :" + name + "\tPrice: " + price + "\tUnits: " + units;
	}
}

class Stationary extends Items {
	String brand;

	Stationary(String name, float price, int units, String brand) {
		super(name, price, units);
		this.brand = brand;
	}

	public String getBrand(){
		return brand;
	}
	public String toString() {
		return super.toString() + "\tBrand: " + brand + "\n";
	}


}

class Book extends Items {
	String isbn;

	Book(String name, float price, int units, String isbn) {
		super(name, price, units);
		this.isbn = isbn;
	}

	public String getIsbn(){
		return isbn;
	}

	public String toString() {
		return super.toString() + "\tISBN: " + isbn + "\n";
	}
}

class NewsPaper extends Items {
	String publisher;

	NewsPaper(String name, float price, int units, String publisher) {
		super(name, price, units);
		this.publisher = publisher;
	}
	public String getPublisher(){
		return publisher;
	}

	public String toString() {
		return super.toString() + "\tPublisher: " + publisher + "\n";
	}
}

class Cart {
	private int total;
	private ArrayList<Items> items = new ArrayList<Items>();

	void addItem(Items item, int units) {
		// item.setUnits(units);
		// System.out.println("----------------"+item+"-------------------");
		// if(item instanceof Stationary){
		// //	Stationary newItem = new Stationary(item.getName(),item.getPrice(),item.getUnits(),item.)
		// }
		// else if(item instanceof Book){
		// //	Book newItem = item;
		// }
		// else if(item instanceof NewsPaper){
		// 	//NewsPaper newItem = item;
		// }
		Items newItem=item;
		newItem.setUnits(units);

		boolean flag = false;
		Iterator<Items> iter = items.iterator();
		while (iter.hasNext()) {
			Items obj = iter.next();

			if (obj.getName().equalsIgnoreCase(item.getName())) {
				flag = true;
				obj.addUnits(newItem.getUnits());
			}
		}
		if (flag == false) {
			items.add(newItem);
		}



		//newItem.setUnits(units);
		//items.add(newItem);
	}

	void removeItems(ArrayList<Items> shopItems,String name, String type,int units) throws NotEnoughUnitsException,ItemNotFoundException{
		for (int i = 0; i < items.size(); i++) {
			Items obj = items.get(i);
			if (type=="Stationary") {
				if (obj instanceof Stationary) {
					if (obj.getName().equalsIgnoreCase(name) ) {
						if(obj.getUnits()==units){
							for(int j=0;j<shopItems.size();j++){
								Items obj1 = shopItems.get(j);
								if(obj1 instanceof Stationary){
									if(obj1.getName().equalsIgnoreCase(name)){
										obj1.addUnits(units);
								} 
							}
						}
							items.remove(obj);
						}
						else if (obj.getUnits()>units){
							for(int j=0;j<shopItems.size();j++){
								Items obj1 = shopItems.get(j);
								if(obj1 instanceof Stationary){
									if(obj1.getName().equalsIgnoreCase(name)){
										obj1.addUnits(units);
								} 
							}
						}
							obj.setUnits(obj.getUnits()-units);
						}
						else throw new NotEnoughUnitsException("The units does not exits in Cart");
					}
					else throw new ItemNotFoundException("Item not found in Cart");

				}
			} else if (type == "Book" ) {
				if (obj instanceof Book) {
					if (obj.getName().equalsIgnoreCase(name)) {
						if(obj.getUnits()==units){
							for(int j=0;j<shopItems.size();j++){
								Items obj1 = shopItems.get(j);
								if(obj1 instanceof Book){
									if(obj1.getName().equalsIgnoreCase(name)){
										obj1.addUnits(units);
								} 
							}
						}
							items.remove(obj);
						}
						else if (obj.getUnits()>units){
							for(int j=0;j<shopItems.size();j++){
								Items obj1 = shopItems.get(j);
								if(obj1 instanceof Book){
									if(obj1.getName().equalsIgnoreCase(name)){
										obj1.addUnits(units);
								} 
							}
						}
							obj.setUnits(obj.getUnits()-units);
						}
						else throw new NotEnoughUnitsException("The units does not exits in Cart");

					}
					else throw new ItemNotFoundException("Item not found in Cart");

				}
			} else if (type=="NewsPaper") {
				if (obj instanceof NewsPaper) {
					if (obj.getName().equalsIgnoreCase(name)) {
						if(obj.getUnits()==units){
							for(int j=0;j<shopItems.size();j++){
								Items obj1 = shopItems.get(j);
								if(obj1 instanceof NewsPaper){
									if(obj1.getName().equalsIgnoreCase(name)){
										obj1.addUnits(units);
								} 
							}
						}
							items.remove(obj);
						}
						else if (obj.getUnits()>units){
							for(int j=0;j<shopItems.size();j++){
								Items obj1 = shopItems.get(j);
								if(obj1 instanceof NewsPaper){
									if(obj1.getName().equalsIgnoreCase(name)){
										obj1.addUnits(units);
								} 
							}
						}
							obj.setUnits(obj.getUnits()-units);
						}
						else throw new NotEnoughUnitsException("The units does not exits in Cart");
					}
					else throw new ItemNotFoundException("Item not found in Cart");

				}
			}
		}
	}

	ArrayList<Items> getItems() {
		return items;
	}

	float calculateTotal() {
		Iterator<Items> iter = items.iterator();
		total = 0;
		while (iter.hasNext()) {
			Items obj = iter.next();
			total += obj.getUnits() * obj.getPrice();
		}
		return total;
	}

	void pay(PaymentStrategy ps, float amount) {
		ps.pay(amount);

	}

	void display() {
		Iterator<Items> iter = items.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}

	String displayString() {
		Iterator<Items> iter = items.iterator();
		String temp = "";
		while (iter.hasNext()) {
			temp = temp + iter.next();
		}
		return temp;
	}
}

class Customer {
	private String name;
	private Cart cart = new Cart();

	Customer(String name) {
		this.name = name;
	}

	void addItemToTheCart(Items item, int units) {
		cart.addItem(item, units);
	}

	float calculateTotal() {
		return cart.calculateTotal();
	}

	void calculateTotalandpay(PaymentStrategy ps) {
		float amount = cart.calculateTotal();

		cart.pay(ps, amount);
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public Cart getCart() {
		return cart;
	}

	void displaycart() {
		cart.display();
	}

	String displayCartString() {
		String temp = cart.displayString();
		return temp;
	}

	void removeItemsFromCart(ArrayList<Items> itemsarray,String name,String type,int units) throws NotEnoughUnitsException,ItemNotFoundException{
		cart.removeItems(itemsarray,name,type,units);
	}
}

class Shop {

	private String shopName;
	private String shopaddress;
	private String ownerName;
	private int shopNo;
	private static Shop instance = null;
	// private Customer customer;
	private ArrayList<Items> items = new ArrayList<Items>();

	private Shop(String shopName, String shopAddress, String ownerName, int shopNo, ArrayList<Items> items) {
		this.shopName = shopName;
		this.shopaddress = shopAddress;
		this.ownerName = ownerName;
		this.shopNo = shopNo;
		this.items = items;
	}

	static Shop getinstance(String shopName, String shopAddress, String ownerName, int shopNo, ArrayList<Items> items) {
		if (instance == null) {
			instance = new Shop(shopName, shopAddress, ownerName, shopNo, items);
		}
		return instance;
	}

	ArrayList<Items> getItems() {
		return items;
	}

	void printItems() {
		System.out.println("*******Items in Bookstore*******");
		Iterator<Items> iter = items.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println("********************************");
	}

	String printItemsString() {
		Iterator<Items> iter = items.iterator();
		String temp = "";
		while (iter.hasNext()) {
			temp = temp + "<br>" + iter.next();
		}
		return temp;
	}

	String printItemsString1() {
		Iterator<Items> iter = items.iterator();
		String temp = "";
		while (iter.hasNext()) {
			temp = temp + iter.next();
		}
		return temp;
	}

	void addItems(Items item) {
		boolean flag = false;
		Iterator<Items> iter = items.iterator();
		while (iter.hasNext()) {
			Items obj = iter.next();

			if (obj.getName().equalsIgnoreCase(item.getName())) {
				flag = true;
				obj.addUnits(item.getUnits());
			}
		}
		if (flag == false) {
			items.add(item);
		}
	}

	void removeItems(String name, String type) {
		for (int i = 0; i < items.size(); i++) {
			Items obj = items.get(i);
			if (type == "Stationary") {
				if (obj instanceof Stationary) {
					if (obj.getName() == name) {
						items.remove(obj);
						System.out.println("Removed Item" + obj.getName());
					}
				}
			} else if (type == "Book") {
				if (obj instanceof Book) {
					if (obj.getName() == name) {
						items.remove(obj);
						System.out.println("Removed Item" + obj.getName());
					}
				}
			} else if (type == "NewsPaper") {
				if (obj instanceof NewsPaper) {
					if (obj.getName() == name) {
						items.remove(obj);
						System.out.println("Removed Item" + obj.getName());
					}
				}
			}
		}
	}

	void RemoveFromCart(Customer c,String name,String type,int units) throws NotEnoughUnitsException,ItemNotFoundException{
		c.removeItemsFromCart(items,name, type,units);

	}
	
	//error handler here
	void transaction(Customer c, String name, String type, int units) throws NotEnoughUnitsException,ItemNotFoundException{
		System.out.println("\nAdding Item To Cart\n");
		boolean success = false;
		for (int i = 0; i < items.size(); i++) {
			Items obj = items.get(i);
			if (type == "Stationary") {
				if (obj instanceof Stationary) {
					if (obj.getName().equalsIgnoreCase(name)) {
						if(obj.getUnits() < units) {
							throw new NotEnoughUnitsException("Low on units");
						}
						if(obj.getUnits()==units)
						{
							items.remove(obj);
						}
						else
						obj.removeUnits(units);
						Items newObj = new Stationary(name, obj.getPrice(), units,((Stationary)obj).getBrand());
						c.addItemToTheCart(newObj, units);
						System.out.println(obj.getName() + " Successfully added to the cart");
						success = true;
					}
					else
						throw new ItemNotFoundException("Item Not Found");
				}
			} else if (type == "Book") {
				if (obj instanceof Book) {
					if (obj.getName().equalsIgnoreCase(name)) {
						if(obj.getUnits() < units) {
							throw new NotEnoughUnitsException("Low on units");
						}
						if(obj.getUnits()==units)
						{
							items.remove(obj);
						}
						else
						obj.removeUnits(units);
						Items newObj = new Book(name, obj.getPrice(), units,((Book)obj).getIsbn());
						c.addItemToTheCart(newObj, units);
						System.out.println(obj.getName() + " Successfully added to the cart");
						success = true;
					}
					else
						throw new ItemNotFoundException("Item Not Found");
				}
			} else if (type == "NewsPaper") {
				if (obj instanceof NewsPaper) {
					if (obj.getName().equalsIgnoreCase(name)) {
						if(obj.getUnits() < units) {
							throw new NotEnoughUnitsException("Low on units");
						}						
						if(obj.getUnits()==units)
						{
							items.remove(obj);
						}
						else
						obj.removeUnits(units);
						Items newObj = new NewsPaper(name, obj.getPrice(), units,((NewsPaper)obj).getPublisher());
						c.addItemToTheCart(newObj, units);
						System.out.println(obj.getName() + " Successfully added to the cart");
						success = true;
					}
					else
						throw new ItemNotFoundException("Item Not Found");
				}
			}
		}
		if (success) {
			// System.out.println("\nTransaction Completed Successfully\n");
		} else {
			System.out.println("\nItem not Found\n");
		}

		System.out.println("********************************");
	}

	float total(Customer c) {
		return c.calculateTotal();
	}

	void totalAndPayment(Customer c, PaymentStrategy ps) {
		System.out.println("\nTransaction by customer:\n");
		c.displaycart();
		c.calculateTotalandpay(ps);
		System.out.println("\nTransaction Successful\n");
		System.out.println("********************************");
	}

	public String toString() {
		return this.shopName + ":" + this.shopaddress + ":" + this.ownerName + ":" + this.shopNo;
	}
}

class Owner {
	JFrame frame;
	private JTextField productNameOwner;
	private JTextField priceOwner;
	private JTextField extraCatOwner;
	private JTextField unitsOwner;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	static ArrayList<Items> items = new ArrayList<Items>();

	private static Shop s1 = Shop.getinstance("Ravindra Cloth Centre", "Bijapur", "Nitinkumar Jain", 10, items);

	public static Shop getshop() {
		return s1;
	}

	Owner() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 578, 668);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// OWNER CODE
		{
			productNameOwner = new JTextField();
			productNameOwner.setBounds(28, 110, 212, 25);
			frame.getContentPane().add(productNameOwner);
			productNameOwner.setColumns(10);

			JButton btnAddItem = new JButton("Add Item");
			btnAddItem.setBounds(420, 228, 105, 25);
			frame.getContentPane().add(btnAddItem);

			JLabel lblProductName = new JLabel("Product Name");
			lblProductName.setBounds(28, 92, 94, 15);
			frame.getContentPane().add(lblProductName);

			JLabel lblProducType = new JLabel("Produc Type");
			lblProducType.setBounds(298, 92, 118, 15);
			frame.getContentPane().add(lblProducType);

			JRadioButton rdbtnBookOwner = new JRadioButton("Book");
			buttonGroup.add(rdbtnBookOwner);
			rdbtnBookOwner.setBounds(268, 111, 60, 23);
			frame.getContentPane().add(rdbtnBookOwner);

			JRadioButton rdbtnNewsPaperOwner = new JRadioButton("News Paper");
			buttonGroup.add(rdbtnNewsPaperOwner);
			rdbtnNewsPaperOwner.setBounds(332, 111, 105, 23);
			frame.getContentPane().add(rdbtnNewsPaperOwner);

			JRadioButton rdbtnStationaryOwner = new JRadioButton("Stationary");
			buttonGroup.add(rdbtnStationaryOwner);
			rdbtnStationaryOwner.setBounds(437, 111, 130, 23);
			frame.getContentPane().add(rdbtnStationaryOwner);

			JLabel lblUnits = new JLabel("Units");
			lblUnits.setBounds(28, 210, 94, 15);
			frame.getContentPane().add(lblUnits);

			priceOwner = new JTextField();
			priceOwner.setColumns(10);
			priceOwner.setBounds(28, 173, 212, 25);
			frame.getContentPane().add(priceOwner);

			JLabel lblIsbnbrand = new JLabel("ISBN/Publisher/Brand");
			lblIsbnbrand.setBounds(276, 155, 249, 15);
			frame.getContentPane().add(lblIsbnbrand);

			extraCatOwner = new JTextField();
			extraCatOwner.setColumns(10);
			extraCatOwner.setBounds(276, 173, 249, 25);
			frame.getContentPane().add(extraCatOwner);

			unitsOwner = new JTextField();
			unitsOwner.setColumns(10);
			unitsOwner.setBounds(28, 228, 212, 25);
			frame.getContentPane().add(unitsOwner);

			JLabel lblPrice = new JLabel("Price");
			lblPrice.setBounds(28, 157, 94, 15);
			frame.getContentPane().add(lblPrice);

			JTextArea textPane = new JTextArea("");
			textPane.setBounds(42, 380, 497, 208);
			frame.getContentPane().add(textPane);

			JButton btnShowitem = new JButton("Show Items");

			btnShowitem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					s1.printItems();
					String temp = s1.printItemsString1();
					textPane.setText(temp);
				}
			});
			btnShowitem.setBounds(42, 334, 105, 25);
			frame.getContentPane().add(btnShowitem);

			btnAddItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String productName = productNameOwner.getText();
					int productPrice = Integer.parseInt(priceOwner.getText());
					String productExtra = extraCatOwner.getText();
					int productUnits = Integer.parseInt(unitsOwner.getText());

					Items i = new Items();
					if (rdbtnBookOwner.isSelected()) {
						i = new Book(productName, productPrice, productUnits, productExtra);
					} else if (rdbtnNewsPaperOwner.isSelected()) {
						i = new NewsPaper(productName, productPrice, productUnits, productExtra);
					} else {
						i = new Stationary(productName, productPrice, productUnits, productExtra);
					}
					s1.addItems(i);
				}
			});
		}

	}
}

class CheckOut {
	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public CheckOut() {
		initialize();
	}

	private void initialize() {
		Shop s1 = Owner.getshop();
		Customer client = Client.getClient();

		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 13));
		frame.setBounds(100, 100, 714, 577);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblWelcomeToPayment = new JLabel("Welcome to Payment Page");
		lblWelcomeToPayment.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		lblWelcomeToPayment.setBounds(229, 22, 272, 54);
		frame.getContentPane().add(lblWelcomeToPayment);

		JLabel lblAmountToBe = new JLabel("Amount to be paid is " + client.calculateTotal());
		lblAmountToBe.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblAmountToBe.setBounds(58, 94, 255, 30);
		frame.getContentPane().add(lblAmountToBe);

		JLabel lblPleaseSelectThe = new JLabel("Please select the mode of Payment : ");
		lblPleaseSelectThe.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		lblPleaseSelectThe.setBounds(58, 147, 297, 30);
		frame.getContentPane().add(lblPleaseSelectThe);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Paytm");
		rdbtnNewRadioButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(58, 208, 105, 21);
		frame.getContentPane().add(rdbtnNewRadioButton);

		textField = new JTextField();
		textField.setBounds(261, 235, 111, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(261, 261, 111, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblPhoneNumber = new JLabel("Phone Number : ");
		lblPhoneNumber.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblPhoneNumber.setBounds(121, 235, 95, 19);
		frame.getContentPane().add(lblPhoneNumber);

		JLabel lblNewLabel = new JLabel("OTP                    : ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNewLabel.setBounds(121, 264, 95, 13);
		frame.getContentPane().add(lblNewLabel);

		JRadioButton rdbtnPaytm = new JRadioButton("Card");
		rdbtnPaytm.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		buttonGroup.add(rdbtnPaytm);
		rdbtnPaytm.setBounds(58, 295, 105, 21);
		frame.getContentPane().add(rdbtnPaytm);

		JLabel label = new JLabel("Name           : ");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		label.setBounds(121, 322, 95, 19);
		frame.getContentPane().add(label);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(261, 322, 111, 19);
		frame.getContentPane().add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(261, 351, 111, 19);
		frame.getContentPane().add(textField_3);

		JLabel label_1 = new JLabel("Card_Number   : ");
		label_1.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		label_1.setBounds(121, 351, 95, 13);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("CVV           : ");
		label_2.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		label_2.setBounds(121, 374, 95, 19);
		frame.getContentPane().add(label_2);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(261, 380, 111, 19);
		frame.getContentPane().add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(261, 409, 111, 19);
		frame.getContentPane().add(textField_5);

		JLabel label_3 = new JLabel("Year Of Expiry : ");
		label_3.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		label_3.setBounds(121, 403, 95, 13);
		frame.getContentPane().add(label_3);

		JRadioButton rdbtnCash = new JRadioButton("Cash");
		rdbtnCash.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		buttonGroup.add(rdbtnCash);
		rdbtnCash.setBounds(58, 434, 105, 21);
		frame.getContentPane().add(rdbtnCash);

		JLabel label_4 = new JLabel("Name              : ");
		label_4.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		label_4.setBounds(121, 461, 95, 19);
		frame.getContentPane().add(label_4);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(261, 461, 111, 19);
		frame.getContentPane().add(textField_6);

		JButton btnNewButton = new JButton("Pay");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewButton.setBounds(505, 455, 117, 30);
		frame.getContentPane().add(btnNewButton);

		JLabel label_5 = new JLabel("");
		label_5.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		label_5.setBounds(282, 517, 195, 13);
		frame.getContentPane().add(label_5);
		//
		// JLabel lblCart = new JLabel("Total amount to be paid is
		// "+client.calculateTotal());
		// lblCart.setBounds(31, 100, 300, 15);
		// frame.getContentPane().add(lblCart);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PaymentStrategy ps = null;
				long no=0;
				int otp=0;
				String name="";
				String cardno="";
				int cvv=0;
				int yearofexpire=0;

				if (rdbtnNewRadioButton.isSelected()) {
					try{
					no = Long.parseLong(textField.getText());
					otp = Integer.parseInt(textField_1.getText());
					ps = new Paytm(no, otp);
					s1.totalAndPayment(client, ps);
					label_5.setText("Payment Successful !!! Paid via Paytm");
					ByeMessage windowBye = new ByeMessage();
					windowBye.frame.setVisible(true);
				
					}
					catch(NumberFormatException nfe){
						System.out.println(nfe);
						ErrorMsg erFrame = new ErrorMsg("Incorrect Format");
						erFrame.frame.setVisible(true);
					}
					
				} else if (rdbtnPaytm.isSelected()) {
					try{
					name = (textField_2.getText());
					cardno = (textField_3.getText());
					cvv = Integer.parseInt(textField_4.getText());
					yearofexpire =Integer.parseInt(textField_5.getText());
					ps = new Card(name, cardno, cvv, yearofexpire);
					s1.totalAndPayment(client, ps);
					label_5.setText("Payment Successful !!! Paid via Card");
					ByeMessage windowBye = new ByeMessage();
					windowBye.frame.setVisible(true);
				}
					catch(NumberFormatException nfe){
						System.out.println(nfe);
						ErrorMsg erFrame = new ErrorMsg("Incorrect Format");
						erFrame.frame.setVisible(true);
					}
					
				} else if (rdbtnCash.isSelected()) {
					try{
					name = textField_6.getText();
					ps = new Cash(name);
					s1.totalAndPayment(client, ps);
					label_5.setText("Payment Successful !!! Paid via Cash");
					ByeMessage windowBye = new ByeMessage();
					windowBye.frame.setVisible(true);
					}
					catch(NumberFormatException nfe){
						System.out.println(nfe);
						ErrorMsg erFrame = new ErrorMsg("Incorrect Format");
						erFrame.frame.setVisible(true);
					}
				
				}

			}
		});

	}
}

class Client {
	JFrame frame;
	private JTextField itemNameText;
	private JTextField unitsTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static Customer client = new Customer("XYZ");


	public Client() {
		initialize();
	}

	public static Customer getClient() {
		return client;
	}

	private void initialize() {
		Shop s1 = Owner.getshop();
		frame = new JFrame();
		frame.setBounds(100, 100, 584, 659);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblCart = new JLabel("Items in your cart");
		lblCart.setBounds(31, 408, 114, 15);
		frame.getContentPane().add(lblCart);

		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.setBounds(457, 567, 105, 25);
		frame.getContentPane().add(btnCheckout);

		JLabel lblItemName = new JLabel("Item Name:");
		lblItemName.setBounds(31, 34, 115, 15);
		frame.getContentPane().add(lblItemName);

		JLabel lblUnits = new JLabel("Units");
		lblUnits.setBounds(31, 105, 115, 15);
		frame.getContentPane().add(lblUnits);

		itemNameText = new JTextField();
		itemNameText.setBounds(29, 61, 259, 25);
		frame.getContentPane().add(itemNameText);
		itemNameText.setColumns(10);

		unitsTextField = new JTextField();
		unitsTextField.setColumns(10);
		unitsTextField.setBounds(31, 132, 259, 25);
		frame.getContentPane().add(unitsTextField);

		JLabel lblItemTpe = new JLabel("Item Type");
		lblItemTpe.setBounds(335, 34, 115, 15);
		frame.getContentPane().add(lblItemTpe);

		JRadioButton rdbtnBook = new JRadioButton("Book");
		buttonGroup.add(rdbtnBook);
		rdbtnBook.setBounds(345, 61, 130, 23);
		frame.getContentPane().add(rdbtnBook);

		JRadioButton rdbtnStationary = new JRadioButton("Stationary");
		buttonGroup.add(rdbtnStationary);
		rdbtnStationary.setBounds(345, 88, 130, 23);
		frame.getContentPane().add(rdbtnStationary);

		JRadioButton rdbtnNewspaper = new JRadioButton("Newspaper");
		buttonGroup.add(rdbtnNewspaper);
		rdbtnNewspaper.setBounds(345, 120, 130, 23);
		frame.getContentPane().add(rdbtnNewspaper);

		JButton btnAddToCart = new JButton("Add To Cart");
		btnAddToCart.setBounds(350, 180, 200, 25);
		frame.getContentPane().add(btnAddToCart);

		JButton btnRemoveFromCart = new JButton("Remove From Cart");
		btnRemoveFromCart.setBounds(50, 180, 200, 25);
		frame.getContentPane().add(btnRemoveFromCart);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(34, 262, 528, 120);
		frame.getContentPane().add(textArea_1);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(34, 437, 528, 105);
		frame.getContentPane().add(textArea);

		JLabel lblItemsInShop = new JLabel("Items in shop");
		lblItemsInShop.setBounds(31, 233, 114, 15);
		frame.getContentPane().add(lblItemsInShop);

		textArea_1.setText(s1.printItemsString1());

		textArea.setText(client.displayCartString());

		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemName="";
				int units=0;
				try{
				itemName = itemNameText.getText();
				units = Integer.parseInt(unitsTextField.getText());
				
				if (rdbtnBook.isSelected()) {
					try {
						s1.transaction(client, itemName, "Book",units);						
					}catch(NotEnoughUnitsException neue){
						System.out.println(neue);
						ErrorMsg erFrame = new ErrorMsg("Not Enough Units");
						erFrame.frame.setVisible(true);
					}
					catch(ItemNotFoundException infe){
						System.out.println(infe);
						ErrorMsg erFrame = new ErrorMsg("Item Not Found");
						erFrame.frame.setVisible(true);
					}
					
				} else if (rdbtnNewspaper.isSelected()) {
					try {
						s1.transaction(client, itemName, "NewsPaper", units);
					}catch(NotEnoughUnitsException neue){
						System.out.println(neue);
						ErrorMsg erFrame = new ErrorMsg("Not Enough Units");
						erFrame.frame.setVisible(true);
					}
					catch(ItemNotFoundException infe){
						System.out.println(infe);
						ErrorMsg erFrame = new ErrorMsg("Item Not Found");
						erFrame.frame.setVisible(true);
					}
				} else if (rdbtnStationary.isSelected()) {
					String type = "Stationary";
					try {
						s1.transaction(client, itemName, type, units);
					}catch(NotEnoughUnitsException neue){
						System.out.println(neue);
						ErrorMsg erFrame = new ErrorMsg("Not Enough Units");
						erFrame.frame.setVisible(true);
					}
					catch(ItemNotFoundException infe){
						System.out.println(infe);
						ErrorMsg erFrame = new ErrorMsg("Item Not Found");
						erFrame.frame.setVisible(true);
					}
				}

				
			}
			catch(NumberFormatException infe){
				System.out.println(infe);
				ErrorMsg erFrame = new ErrorMsg("Incorrect Format");
				erFrame.frame.setVisible(true);
			}
			client.displaycart();
				textArea.setText(client.displayCartString());
				// client.addItemToTheCart();
				s1.printItems();
				textArea_1.setText(s1.printItemsString1());
			}
		});
		
		btnRemoveFromCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemName="";
				int units=0;
				try{
				itemName = itemNameText.getText();
				units = Integer.parseInt(unitsTextField.getText());
				
				if (rdbtnBook.isSelected()) {
					try {
						s1.RemoveFromCart(client, itemName, "Book", units);						
					}catch(NotEnoughUnitsException neue){
						System.out.println(neue);
						ErrorMsg erFrame = new ErrorMsg("Not Enough Units");
						erFrame.frame.setVisible(true);
					}
					catch(ItemNotFoundException infe){
						System.out.println(infe);
						ErrorMsg erFrame = new ErrorMsg("Item Not Found");
						erFrame.frame.setVisible(true);
					}
					
				} else if (rdbtnNewspaper.isSelected()) {
					try {
						s1.RemoveFromCart(client, itemName, "NewsPaper", units);
					}catch(NotEnoughUnitsException neue){
						System.out.println(neue);
						ErrorMsg erFrame = new ErrorMsg("Not Enough Units");
						erFrame.frame.setVisible(true);
					}
					catch(ItemNotFoundException infe){
						System.out.println(infe);
						ErrorMsg erFrame = new ErrorMsg("Item Not Found");
						erFrame.frame.setVisible(true);
					}
				} else if (rdbtnStationary.isSelected()) {
					try {
						s1.RemoveFromCart(client, itemName, "Stationary", units);
					}catch(NotEnoughUnitsException neue){
						System.out.println(neue);
						ErrorMsg erFrame = new ErrorMsg("Not Enough Units");
						erFrame.frame.setVisible(true);
					}
					catch(ItemNotFoundException infe){
						System.out.println(infe);
						ErrorMsg erFrame = new ErrorMsg("Item Not Found");
						erFrame.frame.setVisible(true);
					}
				}	
			}
			catch(NumberFormatException infe){
				System.out.println(infe);
				ErrorMsg erFrame = new ErrorMsg("Incorrect Format");
				erFrame.frame.setVisible(true);
			}
			client.displaycart();
				textArea.setText(client.displayCartString());
				// client.addItemToTheCart();
				s1.printItems();
				textArea_1.setText(s1.printItemsString1());
			}
		});


		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CheckOut window2 = new CheckOut();
				window2.frame.setVisible(true);
			}
		});
	}
}

class ByeMessage {

    JFrame frame;

	public ByeMessage() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 328, 179);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblThankYouVisit = new JLabel("Thank You! Visit Again");
		lblThankYouVisit.setHorizontalAlignment(SwingConstants.CENTER);
		lblThankYouVisit.setBounds(12, 40, 304, 44);
		frame.getContentPane().add(lblThankYouVisit);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(112, 96, 105, 25);
		frame.getContentPane().add(btnExit);
	}
}



class Auth {

	JFrame frame;
	private JPasswordField passwdField;
	private String adminPasswd = "AdminPasswd";

	public Auth() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 472, 267);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Enter the password");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 52, 438, 47);
		frame.getContentPane().add(label);
		
		passwdField = new JPasswordField();
		passwdField.setBounds(45, 105, 383, 39);
		frame.getContentPane().add(passwdField);
		

		JLabel errorMessage = new JLabel("");
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setBounds(12, 156, 448, 15);
		frame.getContentPane().add(errorMessage);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(adminPasswd.equals(passwdField.getText())) {
					Owner window1 = new Owner();
					errorMessage.setText("");
					window1.frame.setVisible(true);		
					frame.setVisible(false);
				}else {
					errorMessage.setText("Invalid Password");
				}
			}
		});
		btnSubmit.setBounds(323, 176, 105, 25);
		frame.getContentPane().add(btnSubmit);
	}
}

public class BookStore {

	private JFrame frame;
	private JTextField productNameOwner;
	private JTextField priceOwner;
	private JTextField extraCatOwner;
	private JTextField unitsOwner;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		BookStore b = new BookStore();

		ArrayList<Items> items = new ArrayList<Items>();
		items.add(new NewsPaper("N1", 10, 10, "Times of India"));
		items.add(new Book("B1", 200, 5, "B1234"));
		items.add(new Stationary("S1", 5, 100, "Cello"));

		Customer c = new Customer("Sourabh");
		Shop s1 = Shop.getinstance("Ravindra Cloth Centre", "Bijapur", "Nitinkumar Jain", 10, items);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookStore window = new BookStore();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookStore() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 578, 668);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnClient = new JButton("Client");
		btnClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client window1 = new Client();
				window1.frame.setVisible(true);
			}
		});
		btnClient.setBounds(199, 303, 152, 25);
		frame.getContentPane().add(btnClient);

		JButton btnOwner = new JButton("Owner");
		btnOwner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Auth window1 = new Auth();
				window1.frame.setVisible(true);
			}
		});
		btnOwner.setBounds(199, 242, 152, 25);
		frame.getContentPane().add(btnOwner);

		JLabel lblWelcomeToOur = new JLabel("Welcome to the Book Store");
		lblWelcomeToOur.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 20));
		lblWelcomeToOur.setBounds(117, 78, 360, 53);
		frame.getContentPane().add(lblWelcomeToOur);

		JLabel lblPleaseSelectAny = new JLabel("Please select any one : ");
		lblPleaseSelectAny.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblPleaseSelectAny.setBounds(187, 178, 190, 16);
		frame.getContentPane().add(lblPleaseSelectAny);


	}
}
