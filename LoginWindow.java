package Login;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Manager.ManagerRootWindow;
import Waiter.GUI.WaiterGUI;
import ADT.Gradients.GradientPanel;

import java.awt.Font;

public class LoginWindow extends JFrame{

	private JFrame userInterface;				//GUI for a specific actor after login.
	private JTextField userLogin, passLogin;	//Text field for user name and password information
	private GradientPanel rootPanel, overlayPanel;	//JPanels for organizing, each has a border and layout designed.
	private JLabel userLabel, passLabel;
	private boolean confirmed = false;
	private Map employees;
	String[] users = {"customer", "kitchen", "busboy", "manager", "waiter", "host"};
	String[] passes = {"customer", "kitchen", "busboy", "manager", "waiter", "host"};
	String user,pass;
	private JButton loginButton;
	private JButton exitButton;
	private JTextField userLoginBox;
	private JPasswordField userPassBox;
	private LoginWindow login = this;
	
	/*
	 *	TopWindow constructor. Name can be changed at a later time.
	 * 	Arguments - NULL
	 * 	@RETURN - NULL
	 * 
	 */
	public LoginWindow() {
		super();
		init();
	}
	
	/*
	 *	Initializing function. For isolation purposes, begins sketch of JFrame.
	 * 	Arguments - NULL
	 * 	@RETURN - NULL
	 * 
	 */
	private void init() {
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
	
	/*
	 * 	All functions to draw the current frame. These functions can get a bit messy, so be sure to seperate them from the actual
	 * 	interaction code.
	 * 
	 * 	Arguments - NULL
	 * 	@Return - NULL
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
			public void actionPerformed(ActionEvent arg0) {
				boolean loginflag = false;
				for(int i = 0;i < users.length;i++){
					if(users[i].equals(userLoginBox.getText()) && passes[i].equals(userPassBox.getText())){
						if(users[i]=="customer"){
							loginflag = true;
							
							dispose();
						}
						if(users[i]=="kitchen"){
							loginflag = true;
							
							dispose();
						}
						if(users[i]=="busboy"){
							loginflag = true;
							
							dispose();
						}
						if(users[i]=="manager"){
							loginflag = true;
							new ManagerRootWindow();
							dispose();
						}
						if(users[i]=="waiter"){
							loginflag = true;
							new WaiterGUI();
							dispose();
						}
						if(users[i]=="host"){
							loginflag = true;
							
							dispose();
						}
						setVisible(false);
						userLoginBox.setText("");
						userPassBox.setText("");
					}
				}
				
				if(loginflag == false){
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Invalid Username or Password","Error", JOptionPane.ERROR_MESSAGE);
				}
				
				loginflag = false;
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
				// TODO Auto-generated method stub
				if(e.getKeyCode()==10){
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
				// TODO Auto-generated method stub
				if(e.getKeyCode()==10){
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
