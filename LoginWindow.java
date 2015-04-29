package Login;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Host.HostGUI;
import KitchenStaff.KitchenStaffGUI;
import Manager.ManagerGUIs.ManagerRootWindow;
import Waiter.WaiterGUI;
import Shared.Gradients.GradientPanel;
import Busboy.BusboyGUI;
import Customer.CustomerGUI;
import Debug.DebugGUI;

import java.awt.Font;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame{

	/**
	 * Class created for logging in to the Restaurant System
	 * Uses DB and offline authentication as needed
	 * 
	 * @author Samuel Baysting
	 * @tester Samuel Baysting
	 * @debugger Samuel Baysting
	 * 
	 */
	
	private GradientPanel rootPanel, overlayPanel;
	public JButton loginButton;
	public JButton exitButton;
	public JTextField userLoginBox;
	public JPasswordField userPassBox;
	public String actor = null;
	public JOptionPane error = null;
	
	/**
	 * Initialize a new instance of Login Window and run super constructor for JFrame
	 * 
	 * @return none
	 * 
	 */
	
	public LoginWindow() {
		super();
		init();
	}
	
	/**
	 *	Initializing function. For isolation purposes, begins sketch of JFrame.
	 * 
	 * 	@return void
	 * 
	 */
	
	private void init() {
		
		addWindowListener(new WindowAdapter() // To open main window again if you hit the corner "X"
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                dispose();
                System.exit(0);
            }
        });
		
		this.setTitle("Restaurant Login");
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setSize(900,600);
		setLocationRelativeTo(null);
		this.frameManipulation();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(rootPanel);
		
		JLabel lblRestaurantSystemLogin = new JLabel("Restaurant System Login");
		lblRestaurantSystemLogin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblRestaurantSystemLogin.setBounds(303, 63, 265, 58);
		rootPanel.add(lblRestaurantSystemLogin);
		this.setVisible(true);
	}
	
	/**
	 * All functions to create and organize the Login Window
	 * This function also has the login authentication system and window launcher
	 * Includes key listener to "Enter" for login
	 * 
	 * @return void
	 * 
	 */
	
	private void frameManipulation() {
		
		rootPanel = new GradientPanel(Color.white,Color.gray);
		rootPanel.setLayout(null);
		rootPanel.setBounds(0,0,900,600);
		overlayPanel = new GradientPanel(Color.white,Color.gray.brighter());
		overlayPanel.setLayout(null);
		overlayPanel.setBounds(250,169,380,210);
		overlayPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		rootPanel.add(overlayPanel);
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			

			@SuppressWarnings({ "deprecation", "static-access" })
			public void actionPerformed(ActionEvent arg0) {
				LoginAuthenticator auth = new LoginAuthenticator(userLoginBox.getText(),userPassBox.getText());
				if(auth.getAuth()==0){
					
					actor = auth.getActorClass();
					switch (actor) {
					
					case "Waiter": 			
								   			new WaiterGUI();
								   			dispose();
								   			break;
								   			
					case "Manager": 		
					   			   			new ManagerRootWindow();
					   			   			dispose();
					   			   			break;
					   			   			
					case "Host": 			
		   			   			   			new HostGUI();
		   			   			   			dispose();
		   			   			   			break;
		   			   			   			
					case "Busboy": 			
		   			   			   			new BusboyGUI();
		   			   			   			dispose();
		   			   			   			break;
		   			   			   			
					case "Customer": 		
		   			   			   			new CustomerGUI();
		   			   			   			dispose();
		   			   			   			break;
		   			   			   			
					case "Chef": 			
		   			   			   			new KitchenStaffGUI();
		   			   			   			dispose();
		   			   			   			break;
		   			   			   			
					case "Debug": 			
  			   								new DebugGUI();
  			   								dispose();
  			   								break;
  			   								
					default: 				Toolkit.getDefaultToolkit().beep();
											JOptionPane.showMessageDialog(null, "Invalid Username or Password","Error", JOptionPane.ERROR_MESSAGE);
											break;
					}
					
				}
				else{
					
					Toolkit.getDefaultToolkit().beep();
					error = new JOptionPane();
					error.showMessageDialog(null, "Invalid Username or Password","Error", JOptionPane.ERROR_MESSAGE);
						
				}
			}
		});
		loginButton.setBounds(34, 176, 133, 23);
		overlayPanel.add(loginButton);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds(211, 176, 133, 23);
		overlayPanel.add(exitButton);
		
		userLoginBox = new JTextField();
		userLoginBox.setBounds(34, 54, 310, 20);
		overlayPanel.add(userLoginBox);
		userLoginBox.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(34, 29, 88, 14);
		overlayPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(34, 91, 88, 14);
		overlayPanel.add(lblPassword);
		
		userPassBox = new JPasswordField();
		userPassBox.setBounds(34, 116, 310, 20);
		overlayPanel.add(userPassBox);
		userPassBox.setColumns(10);
		userPassBox.addKeyListener(new KeyListener(){
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10){ //Enter button
					loginButton.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}	
		});
		userLoginBox.addKeyListener(new KeyListener(){
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10){ //Enter button
					loginButton.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}	
		});
	}

}
