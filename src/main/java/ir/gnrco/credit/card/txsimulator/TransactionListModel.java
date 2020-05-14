package ir.gnrco.credit.card.txsimulator;

import javax.swing.AbstractListModel;
import java.util.List;


public class TransactionListModel extends AbstractListModel<Transaction>{

	private List<Transaction> data;

	public TransactionListModel(List<Transaction> input) {
		data = input;
		data.sort(null);
	}
	
	public int getSize() {
		return data.size();
	}
	
	public Transaction getElementAt(int index) {
		if (data.isEmpty()) {
			return null;
		}
		
		return data.get(index);
	}
	
	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	public int indexOf(Transaction n) {
		return data.indexOf(n);
	}
	
	public boolean containsName(Transaction input) {
		if (data.contains(input)) {
			return true;
		}
		
		return false;
	}
	
	public void add(Transaction n) {
		data.add(n);
		data.sort(null);
	}
	
	public void delete(Transaction n) {
		data.remove(n);
	}
 
	public void update(Transaction originalN, Transaction newN) {
		int index = data.indexOf(originalN);	
		data.set(index, newN);
		data.sort(null);
	}
}