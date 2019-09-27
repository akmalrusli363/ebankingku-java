package ebanking;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import database.*;

public class EBankingMainMenu extends JFrame implements ActionListener {

	private Customer loginCustomer = null;
	private JFrame launcher = null;
	private JButton topupButton = new JButton("Topup saldo");
	private JButton paymentButton = new JButton("Lakukan pembayaran");
	private JButton historyButton = new JButton("Cek riwayat transaksi");
	private JButton logoutButton = new JButton("Logout");
	private JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 5, 8));
	private JPanel informationalPanel = new JPanel();
	private JPanel propertiesPanel = new JPanel();
	private JPanel userinfoPanel = new JPanel(new BorderLayout(20, 10));
	private ArrayList<JLabel> infoLabels = new ArrayList<>();
	private ArrayList<JLabel> propertiesLabels = new ArrayList<>();
	
	private void addProperty(String info, String defaultValue) {
		addProperty(new JLabel(info), new JLabel(defaultValue, SwingConstants.RIGHT));
	}
	
	private void addButton(JButton button, char mnemonic) {
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setMnemonic(mnemonic);
		button.setSize(300, 45);
		buttonsPanel.add(button);
	}
	
	private void addProperty(JLabel info, JLabel property) {
		property.setAlignmentX(1.0f);
		if (!infoLabels.isEmpty()) {
			informationalPanel.add(Box.createRigidArea(new Dimension(0, 5)));
			propertiesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		}
		informationalPanel.add(info);
		propertiesPanel.add(property);
		infoLabels.add(info);
		propertiesLabels.add(property);
	}
	
	private void setValueProperty(int index, String value) {
		propertiesLabels.get(index).setText(value);
	}
	
	private void initialize() {
		informationalPanel.setLayout(new BoxLayout(informationalPanel, BoxLayout.PAGE_AXIS));
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.PAGE_AXIS));
		
		userinfoPanel.setBorder(new CompoundBorder(new TitledBorder("User information:"), new EmptyBorder(2, 3, 5, 3)));
		userinfoPanel.add(informationalPanel, BorderLayout.CENTER);
		userinfoPanel.add(propertiesPanel, BorderLayout.EAST);
		addProperty("Nama Lengkap:", "-------------------------");
		addProperty("Saldo:", "Rp0");
		
		buttonsPanel.setBorder(new EmptyBorder(10,5,10,5));
		addButton(topupButton,'t');
		addButton(paymentButton,'p');
		addButton(historyButton,'r');
		addButton(logoutButton,'o');
		
		topupButton.addActionListener(this);
		paymentButton.addActionListener(this);
		historyButton.addActionListener(this);
		logoutButton.addActionListener(this);

		JLabel readyLabel = new JLabel("Ready");
		readyLabel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED), new EmptyBorder(1,3,2,3)));
		
		this.setLayout(new BorderLayout());
		this.add(userinfoPanel, BorderLayout.NORTH);
		this.add(buttonsPanel, BorderLayout.CENTER);
		this.add(readyLabel, BorderLayout.SOUTH);
		this.setTitle("EBankingku ~ Hello username!");
		this.pack();
		this.setSize(400, getHeight());
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == topupButton) {
			performUserTopup();
		} else if (e.getSource() == paymentButton) {
			performUserTransaction();
		} else if (e.getSource() == historyButton) {
			showTransactionHistory();
		} else if (e.getSource() == logoutButton) {
			performLogoutAction(launcher);
		}
	}
	
	public void performLogoutAction(JFrame launcher) {
		logout();
		if (launcher != null) {
			launcher.setVisible(true);			
		}
	}
	
	private void logout() {
		dispose();
		if (getDefaultCloseOperation() == EXIT_ON_CLOSE) System.exit(0);
	}
	
	private void performUserTopup() {
		TransferPanel tp = new TransferPanel(loginCustomer);
		tp.performUserTopup();
		refreshUserInformation();
	}

	private void performUserTransaction() {
		TransferPanel tp = new TransferPanel(loginCustomer);
		tp.performUserPayment();
		refreshUserInformation();
	}
	
	private void showTransactionHistory() {
		TransactionHistoryDialog history = new TransactionHistoryDialog(loginCustomer);
		JOptionPane.showMessageDialog(this, history, "Riwayat Transaksi", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void setUserTitle(String username) {
		this.setTitle(String.format("EBankingku ~ Hello %s!", username));
	}
	
	public void refreshUserInformation() {
		setUserTitle(loginCustomer.getUsername());
		setValueProperty(0, loginCustomer.getNamaLengkap());
		setValueProperty(1, CurrencyParser.parseCurrency(loginCustomer.getSaldo()));
	}
	
	public EBankingMainMenu() {
		initialize();
		setUserTitle("Ariana12");
		setValueProperty(0, "ARIANA");
		setValueProperty(1, "Rp123,345,4556");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public EBankingMainMenu(Customer customer, JFrame launcher) {
		initialize();
		loginCustomer = customer;
		refreshUserInformation();
		this.launcher = launcher;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		new EBankingMainMenu();
	}

}
