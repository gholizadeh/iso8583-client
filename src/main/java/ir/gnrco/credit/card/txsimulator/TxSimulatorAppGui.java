package ir.gnrco.credit.card.txsimulator;

import ir.gnrco.credit.card.parser.dto.pooya.AdditionalTxData;
import ir.gnrco.credit.card.parser.dto.pooya.GeneralMessage;
import ir.gnrco.credit.card.parser.dto.pooya.OriginalTxData;
import ir.gnrco.credit.card.parser.enums.FunctionCodeType;
import ir.gnrco.credit.card.parser.enums.MessageType;
import ir.gnrco.credit.card.parser.enums.TxProcessCodeType;
import ir.gnrco.credit.card.parser.packer.ISO93APooyaPackager;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.ASCIIChannel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TxSimulatorAppGui {
    private Connection con;

	private String s1[] = { "1200", "1100"};
	private String s2[] = { "Œ—Ìœ", "Å—œ«Œ  ﬁ»÷", "„«‰œÂ êÌ—Ì" };

	private JFrame frame;
	private JList<Transaction> list;
	private JTextField nameFld;
	private JTextField cardNoFld;
	private JTextField amountFld;
	private JTextField dateFld;
	private JTextField ipFld;
	private JTextField portFld;
	private JComboBox<String> messageTypeFld = new JComboBox<>(s1);
	private JComboBox<String> processTypeFld = new JComboBox<>(s2);
	private JButton newButton;
	private JButton deleteButton;
	private JButton saveButton;
	private JButton cancelButton;
	private JButton sendButton;
	private JButton soroushRollbackButton;
	private JButton shetabRollbackButton;
	private JLabel messageLabel;
	private boolean updateFlag;

	enum ResultMessage {INFO, WARN, NONE};
	enum ActionType {LOAD, CREATE, UPDATE, DELETE, SEND, SHETAB_ROLLBACK, SHAPARAK_ROLLBACK};

	private static final Font FONT_FOR_WIDGETS =
			new Font("SansSerif", Font.PLAIN, 16);
	private static final Font FONT_FOR_EDITOR =
			new Font("Comic Sans MS", Font.PLAIN, 16);

//    private void setUpDataBase(){
//        try {
//            Class.forName("org.h2.Driver");
//            String url = "jdbc:h2:mem:InMemoryTest";
//            con = DriverManager.getConnection(url);
//
//            createTransactionListTable();
//        } catch (ClassNotFoundException | SQLException exp) {
//            exp.printStackTrace();
//        }
//    }

	public TxSimulatorAppGui() {
	    //setUpDataBase();

		frame = new JFrame("Transactions App");
		addWidgetsToFrame(frame);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(350, 50); // x, y
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

		initDataAndUpdateUI();
	}

	private void addWidgetsToFrame(JFrame frame) {
		Container pane = frame.getContentPane();
		JPanel panel = new JPanel(new GridBagLayout());
		pane.add(panel);

		JLabel label = new JLabel("Transactions client simulator app!");

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 4, 4); // t, l, b, r

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(label, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(getListInScrollPane(), gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(getToolBarWithButtons(), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		panel.add(createDetailsPanel(), gbc);
	}

	private JPanel createDetailsPanel() {

		JPanel panel = new JPanel();

		JLabel ipLabel = new JLabel("server ip");
		JLabel portLabel = new JLabel("server port");
		JLabel TxNameLabel = new JLabel("Transaction Name");
		JLabel TxAmountLabel = new JLabel("Amount");
		JLabel dateFieldLabel = new JLabel("Date");
		JLabel cardNoLabel = new JLabel("Card No");
		JLabel messageTypeLabel = new JLabel("Message Type");
		JLabel processCodeLabel = new JLabel("Process Code");

		ipFld = getTextField("", "destination server ex: 10.12.47.102, localhost, ...");
		portFld = getTextField("37400", "Just number");
		nameFld = getTextField("", "must be unique and 5 to 25 characters");
		amountFld = getTextField("", "In rials");
		dateFld = getTextField("", "yyyy/mm/dd hh:mm:ss");
		cardNoFld = getTextField("", "16 digit card number or account no");
		messageTypeFld.setEnabled(false);
		processTypeFld.setEnabled(false);

		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 4, 4); // t, l, b, r

		int i=0;
		gbc.gridx = 1;
		gbc.gridy = i;
		panel.add(getMessageLabel(),  gbc);

        i++;
        gbc.gridx = 0;
        gbc.gridy = i;
        panel.add(ipLabel,  gbc);
        gbc.gridx = 1;
        gbc.gridy = i;
        panel.add(ipFld,  gbc);

        i++;
        gbc.gridx = 0;
        gbc.gridy = i;
        panel.add(portLabel,  gbc);
        gbc.gridx = 1;
        gbc.gridy = i;
        panel.add(portFld,  gbc);

		i++;
		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(TxNameLabel,  gbc);
		gbc.gridx = 1;
		gbc.gridy = i;
		panel.add(nameFld,  gbc);

		i++;
		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(TxAmountLabel,  gbc);
		gbc.gridx = 1;
		gbc.gridy = i;
		panel.add(amountFld,  gbc);

		i++;
		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(dateFieldLabel,  gbc);
		gbc.gridx = 1;
		gbc.gridy = i;
		panel.add(dateFld,  gbc);

		i++;
		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(cardNoLabel,  gbc);
		gbc.gridx = 1;
		gbc.gridy = i;
		panel.add(cardNoFld,  gbc);

		i++;
		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(processCodeLabel,  gbc);
		gbc.gridx = 1;
		gbc.gridy = i;
		panel.add(processTypeFld,  gbc);

		i++;
		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(messageTypeLabel,  gbc);
		gbc.gridx = 1;
		gbc.gridy = i;
		panel.add(messageTypeFld,  gbc);

		return panel;
	}

	private void initDataAndUpdateUI() {

		list.setModel(new TransactionListModel(new ArrayList<>()));
		deleteButton.setEnabled(false);
		displayMessage("Welcome!", ResultMessage.INFO);
	}

	private JScrollPane getListInScrollPane() {

		list = new JList<Transaction>();
		list.setToolTipText("Transactions list: double-click a Transaction to edit");
		list.setBorder(new EmptyBorder(5, 5, 5, 5));
		list.setFixedCellHeight(26);
		list.setFont(FONT_FOR_WIDGETS);
		list.addMouseListener(new ListMouseListener());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectListener());
		JScrollPane scroller = new JScrollPane(list);
		scroller.setPreferredSize(new Dimension(250, 350)); // wxh
		scroller.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		return scroller;
	}

	private JTextField getTextField(String defaltValue, String hint) {
		if(defaltValue == null)
			defaltValue = "";

		JTextField textFld = new JTextField(defaltValue, 25);
		textFld.setToolTipText(hint);
		textFld.setFont(FONT_FOR_WIDGETS);
		textFld.setEditable(false);
		textFld.setMargin(new Insets(5, 5, 5, 5)); // top, left, bottom, rt
		return textFld;
	}

	private JToolBar getToolBarWithButtons() {

		newButton = getButton("New");
		saveButton = getButton("Save");
		deleteButton = getButton("Delete");
		cancelButton = getButton("Cancel");
		sendButton = getButton("Send");
		soroushRollbackButton = getButton("Soroush Rollback");
		shetabRollbackButton = getButton("Shetab Rollback");

		JToolBar toolBar = getToolBarForButtons();
		toolBar.add(newButton);
		toolBar.addSeparator(new Dimension(2, 0));
		toolBar.add(saveButton);
		toolBar.addSeparator(new Dimension(2, 0));
		toolBar.add(deleteButton);
		toolBar.addSeparator(new Dimension(2, 0));
		toolBar.add(cancelButton);
		toolBar.addSeparator(new Dimension(10, 0));
		toolBar.add(sendButton);
		toolBar.addSeparator(new Dimension(2, 0));
		toolBar.add(soroushRollbackButton);
		toolBar.addSeparator(new Dimension(2, 0));
		toolBar.add(shetabRollbackButton);

		return toolBar;
	}

	private JToolBar getToolBarForButtons() {

		JToolBar toolBar = new JToolBar();
		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		return toolBar;
	}

	private JButton getButton(String label__) {

		JButton button = new JButton();
		button.setBorderPainted(false);

		switch (label__) {
			case "New":
				button.setIcon(getIconForButton("add.png"));
				button.addActionListener(new NewActionListener());
				break;
			case "Delete":
				button.setIcon(getIconForButton("delete.png"));
				button.addActionListener(new DeleteActionListener());
				break;
			case "Save":
				button.setEnabled(false);
				button.setIcon(getIconForButton("save.png"));
				button.addActionListener(new SaveActionListener());
				break;
			case "Cancel":
				button.setEnabled(false);
				button.setIcon(getIconForButton("cancel.png"));
				button.addActionListener(new CancelActionListener());
				break;
			case "Send":
			    button.setEnabled(false);
				button.setIcon(getIconForButton("send.png"));
				button.addActionListener(new SendActionListener(0));
				break;
			case "Soroush Rollback":
			    button.setEnabled(false);
				button.setIcon(getIconForButton("soroush-return.png"));
				button.addActionListener(new SendActionListener(1));
				break;
			case "Shetab Rollback":
			    button.setEnabled(false);
				button.setIcon(getIconForButton("shetab-return.png"));
				button.addActionListener(new SendActionListener(2));
				break;
			default:
				throw new IllegalArgumentException("*** Invalid button label ***");
		}

		button.setToolTipText(label__);
		return button;
	}

	private ImageIcon getIconForButton(String iconName) {

		String urlString = "/images/" + iconName;
		URL url = this.getClass().getResource(urlString);
		return new ImageIcon(url);
	}

	private JLabel getMessageLabel() {

		messageLabel = new JLabel("");
		messageLabel.setFont(FONT_FOR_WIDGETS);
		return messageLabel;
	}

	private void displayMessage(String msg, ResultMessage type) {

		if (type == ResultMessage.WARN) {

			messageLabel.setText("<html><font color=red>" + msg + "</font></html>");
		}
		else if (type == ResultMessage.INFO) {

			messageLabel.setText("<html><font color=blue>" + msg + "</font></html>");
		}
		else {
			messageLabel.setText("");
		}
	}

	private void emptyForm(){
        ipFld.setText("");
        portFld.setText("37400");
        nameFld.setText("");
        amountFld.setText("");
        cardNoFld.setText("");
        dateFld.setText(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));
    }

    private void fillForm(Transaction t){
        ipFld.setText(t.getIp());
        portFld.setText(t.getPort().toString());
        nameFld.setText(t.getName());
        amountFld.setText(t.getAmount().toString());
        cardNoFld.setText(t.getCardNo().toString());
        dateFld.setText(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(t.getDate()));
        switch (t.getTxProcessCode()){
            case PURCHASE: processTypeFld.setSelectedIndex(0); break;
            case PAYMENT: processTypeFld.setSelectedIndex(1); break;
            default: processTypeFld.setSelectedIndex(2); break;
        }
        switch (t.getMessageType()){
            case MTI_1200: messageTypeFld.setSelectedIndex(0); break;
            case MTI_1100: messageTypeFld.setSelectedIndex(1); break;
        }
    }

    private void enableFrom(){
        ipFld.setEditable(true);
        portFld.setEditable(true);
        nameFld.setEditable(true);
        amountFld.setEditable(true);
        dateFld.setEditable(true);
        cardNoFld.setEditable(true);
        messageTypeFld.setEnabled(true);
        processTypeFld.setEnabled(true);

        newButton.setEnabled(false);
        saveButton.setEnabled(true);
        deleteButton.setEnabled(false);
        cancelButton.setEnabled(true);
        sendButton.setEnabled(false);
        shetabRollbackButton.setEnabled(false);
        soroushRollbackButton.setEnabled(false);
    }

    private void disableForm(){
        ipFld.setEditable(false);
        portFld.setEditable(false);
        nameFld.setEditable(false);
        amountFld.setEditable(false);
        dateFld.setEditable(false);
        cardNoFld.setEditable(false);
        messageTypeFld.setEnabled(false);
        processTypeFld.setEnabled(false);

        newButton.setEnabled(true);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);

        if (getTransactionListModel().isEmpty()) {
            deleteButton.setEnabled(false);
            sendButton.setEnabled(false);
            shetabRollbackButton.setEnabled(false);
            soroushRollbackButton.setEnabled(false);
        }
        else {
            deleteButton.setEnabled(true);
            sendButton.setEnabled(true);
            shetabRollbackButton.setEnabled(true);
            soroushRollbackButton.setEnabled(true);
        }
    }

	private class ListSelectListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {

			if (e.getValueIsAdjusting()) {
				return;
			}

            if (getTransactionListModel().isEmpty()) {
                emptyForm();
            }else {
                Transaction t = list.getSelectedValue();
                fillForm(t);
            }

            disableForm();

			updateFlag = false;

            displayMessage("", ResultMessage.NONE);
		}
	}

	private TransactionListModel getTransactionListModel() {

		return (TransactionListModel) list.getModel();
	}

	private class NewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
            enableFrom();
            emptyForm();
            nameFld.requestFocusInWindow();

            displayMessage("Enter new Transaction", ResultMessage.INFO);
		}
	}

	private class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		    String ip = ipFld.getText();
		    Integer port = Integer.valueOf(portFld.getText());
			String name = nameFld.getText();
			Long cardNo = new Long(cardNoFld.getText());
			BigDecimal amount = new BigDecimal(amountFld.getText());
			Date date = null;
			MessageType messageType = MessageType.findByCode(Integer.parseInt(messageTypeFld.getSelectedItem().toString()));
			Long selectedProcess = null;
			switch (processTypeFld.getSelectedIndex()){
				case 0: selectedProcess = 000000L; break;
				case 1: selectedProcess = 500000L; break;
				default: selectedProcess = 310000L; break;
			}
			TxProcessCodeType processCode = TxProcessCodeType.findByCode(selectedProcess);
			try {
				date =new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(dateFld.getText());
			} catch (ParseException e1) {
				date = new Date();
			}

			name = (name == null) ? "" : name.trim();

			Transaction newTx = new Transaction(name, amount, date, cardNo, processCode, messageType, ip, port, null);
			Transaction originalN = list.getSelectedValue();

			if (updateFlag) {
				getTransactionListModel().update(originalN, newTx);
			}
			else {
				getTransactionListModel().add(newTx);
			}

			list.updateUI();

			int ix = getTransactionListModel().indexOf(newTx);
			list.setSelectedIndex(ix);
			list.ensureIndexIsVisible(ix);

			disableForm();

			updateFlag = false;

			displayMessage("Transaction is saved", ResultMessage.INFO);
		}
	}

	private class DeleteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			int ix = list.getSelectedIndex();
			if (ix < 0) {
				return;
			}

			Transaction n = list.getSelectedValue();

			int optionSelected = JOptionPane.showConfirmDialog(frame,
					"Delete the selected Transaction ?", "Delete",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (optionSelected != JOptionPane.YES_OPTION) {
				return;
			}

			getTransactionListModel().delete(n);
			list.updateUI();

			if (getTransactionListModel().isEmpty()) {
                emptyForm();
				disableForm();
				displayMessage(n.getName() + " is deleted. No Transactions.", ResultMessage.INFO);
				return;
			}

			if (ix > 0) {
				--ix;
			}

			list.setSelectedIndex(ix);
			displayMessage(n.getName() + " is deleted", ResultMessage.INFO);

			if (ix == 0) {
				Transaction t = list.getSelectedValue();
				fillForm(t);
			}
		}
	}

	private class CancelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (updateFlag) {
				displayMessage("Transaction edit is cancelled", ResultMessage.INFO);
			}
			else {
				displayMessage("New Transaction is cancelled", ResultMessage.INFO);
			}

			if (getTransactionListModel().isEmpty()) {
                emptyForm();
			}
			else {
				Transaction n = list.getSelectedValue();
				fillForm(n);
			}

			disableForm();
			updateFlag = false;
		}
	}

    private class SendActionListener implements ActionListener {
        private int requestType = 0;

        public SendActionListener(int requestType){
            this.requestType = requestType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int ix = list.getSelectedIndex();
            if (ix < 0) {
                return;
            }

            Transaction t = list.getSelectedValue();

            try {
                GeneralMessage message = new GeneralMessage();
                if(t.getMessageType().equals(MessageType.MTI_1200)) {
                    message.setTXAmountAcquire(t.getAmount());
                    message.setTrack2Data(t.getCardNo() + "=" + "00115061498917900001");
                    message.setFunctionCode(FunctionCodeType.FINANCIAL_REQUEST);
                }else{
                    message.setFunctionCode(FunctionCodeType.REMAIN_REQUEST);
                }

                if(requestType == 0) {
                    message.setMTI(t.getMessageType());
                    message.setTXProcessCode(t.getTxProcessCode());
                }else{
                    if(t.getMessageType().equals(MessageType.MTI_1100)){
                        displayMessage("Main message is 1100", ResultMessage.INFO);
                        return;
                    }

                    if (requestType == 1) {
                        message.setMTI(MessageType.MTI_1220);
                        message.setTXProcessCode(TxProcessCodeType.FIX_MODIFY_RECONCILIATION);
                        message.setFunctionCode(FunctionCodeType.FIN_FIXING_DEPOSIT_TO_CARD);
                    }else if (requestType == 2){
                        message.setMTI(MessageType.MTI_1420);
                        message.setTXProcessCode(t.getTxProcessCode());
                    }

                    OriginalTxData originalTxData = new OriginalTxData();
                    originalTxData.setRawOriginalTxData("1200"+t.getTxId()+(new SimpleDateFormat("yyMMddhhmmss").format(t.getDate()))+"603799");
                    message.setOriginalTxData(originalTxData);
                }

                message.setAccountNo(t.getCardNo());

                message.setSentDataToShetabDt(t.getDate());
                message.setTXId(t.getTxId());
                message.setTXDateTime(t.getDate());
                message.setDateExpiration("0011");
                message.setTXReceiveDateTime(t.getDate());
                message.setMerchantType(Short.valueOf("5313"));
                message.setPosInfo("210101213144");
                message.setAcquireInstituteCode(Long.valueOf(603799));
                message.setSenderInstituteCode(Long.valueOf(628023));
                message.setTXRefRecoverNo("000000182869");
                message.setTXAcquirePosNumber("67777777");
                message.setCardAcquireNumber("   777777777600");
                message.setCardAcquireNameLocation("                              Â—«‰  Â—«‰");
                AdditionalTxData additionalData = new AdditionalTxData();
                additionalData.setRawAdditionalTxData("P13006"+t.getTxId()+"D01014"+new SimpleDateFormat("yyyyMMddhhmmss").format(t.getDate()));
                message.setAdditionalData(additionalData);
                message.setTXCurrency("364");
                message.setAccountIdentification("310011857615");

                message.packMessageInto();

                ISOMsg isoMsg = message.getIsoMsg();
                ISO93APooyaPackager packager = new ISO93APooyaPackager();

                isoMsg.setPackager(packager);
                isoMsg.pack();

                ASCIIChannel channel = new ASCIIChannel(t.getIp(),t.getPort(), packager);
                channel.setHeader("ISO51000000");
                channel.connect();
                channel.send(isoMsg);
                ISOMsg response = channel.receive();
                response.dump(System.out, "response:");

                if(response.hasField(39))
                    displayMessage("Response Code: " + response.getString(39), ResultMessage.INFO);
                else
                    displayMessage("Bit 39 not exist", ResultMessage.INFO);

            }catch (Exception ex){
                System.out.println(ex);
                displayMessage("Transaction is failed", ResultMessage.WARN);
            }
        }
    }

	private class HelpActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {


		}
	}

	private class ListMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			if ((e.getButton() == MouseEvent.BUTTON1) &&
					(e.getClickCount() == 2)) { // double click on left button

				Rectangle r = list.getCellBounds(0,
						list.getLastVisibleIndex());

				if ((r == null) || (! r.contains(e.getPoint()))) {

					// Double clicked on empty space
					list.requestFocusInWindow();
					return;
				}

				doubleClickActionRoutine();
			}
		}
	}

	private void doubleClickActionRoutine() {
		displayMessage("Transaction is being edited", ResultMessage.INFO);
        enableFrom();
        updateFlag = true;
        nameFld.setCaretPosition(0);
        nameFld.requestFocusInWindow();
    }

    protected void createTransactionListTable() throws SQLException {
        String query = "create table transactions (" +
                "id bigint identity, " +
                "tx_id NUMBER(6), " +
                "tx_ip varchar(255), " +
                "tx_port NUMBER(6), " +
                "tx_name varchar(255), " +
                "tx_amount NUMBER(24,4), " +
                "tx_date_time DATE, " +
                "tx_card_no NUMBER(19), " +
                "tx_message_type NUMBER(4), " +
                "tx_process_code NUMBER(6) )";
        try (Statement stmt = con.createStatement()) {
            stmt.execute(query);
        }
    }

    protected void insertIntoTransactionTable(Transaction tx) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("insert into transatcions " +
                "(tx_id, tx_ip, tx_port, tx_name, tx_amount, tx_date_time, tx_card_no, tx_message_type, tx_process_code) " +
                "values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            ps.setLong(1, tx.getTxId());
            ps.setString(2, tx.getIp());
            ps.setInt(3, tx.getPort());
            ps.setString(4, tx.getName());
            ps.setBigDecimal(5, tx.getAmount());
            ps.setDate(6, new java.sql.Date(tx.getDate().getTime()));
            ps.setLong(7, tx.getCardNo());
            ps.setInt(8, tx.getMessageType().getCode());
            ps.setLong(9, tx.getTxProcessCode().getCode());
            //foreach{
            //set
            //ps.addBatch();
            //ps.executeBatch();
            //}
            ps.executeQuery();
        }
    }

    protected void updateTransactionTable(int id, Transaction tx) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("update transatcions set" +
                "(tx_id, tx_ip, tx_port, tx_name, tx_amount, tx_date_time, tx_card_no, tx_message_type, tx_process_code) " +
                "values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?) where id = ?")) {

            ps.setLong(1, tx.getTxId());
            ps.setString(2, tx.getIp());
            ps.setInt(3, tx.getPort());
            ps.setString(4, tx.getName());
            ps.setBigDecimal(5, tx.getAmount());
            ps.setDate(6, new java.sql.Date(tx.getDate().getTime()));
            ps.setLong(7, tx.getCardNo());
            ps.setInt(8, tx.getMessageType().getCode());
            ps.setLong(9, tx.getTxProcessCode().getCode());
            ps.setInt(10, id);
            ps.executeQuery();
        }
    }

    public void remove(int id) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("delete from transactions where id = ?")) {
            ps.setInt(1, id);
            ps.executeQuery();
        }
    }

    public void refresh() throws SQLException {
        //java.util.List<String> values = new ArrayList<>(25);
        try (PreparedStatement ps = con.prepareStatement("select * from transactions")) {
            try (ResultSet rs = ps.executeQuery()) {
//                ResultSetMetaData md = rs.getMetaData();
//                for (int col = 0; col < md.getColumnCount(); col++) {
//                    values.add(md.getColumnName(col + 1));
//                }
                while (rs.next()) {
                    MessageType msgType = MessageType.findByCode(rs.getInt(8));
                    TxProcessCodeType txCode = TxProcessCodeType.findByCode(rs.getLong(9));
                    Transaction tx = new Transaction(rs.getString(4), rs.getBigDecimal(5), rs.getDate(6), rs.getLong(7), txCode, msgType, rs.getString(2), rs.getInt(3), rs.getLong(1));
                    getTransactionListModel().add(tx);
                }
            }
        }
    }
}