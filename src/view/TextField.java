package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.TreeElement.Column;
/**
 * Ova klasa predstavlja komponentu za unos teksta sa formatiranim poljem koje prilagođava
 * svoju veličinu u skladu sa zadatom kolonom, omogućuje promenu vrednosti i referenciranje dugmeta,
 * prikazujući odgovarajuću etiketu.
 * @author grupa4
 */

public class TextField extends JPanel implements InputField
{
	
	private static final long serialVersionUID = 1L;
	private Column column;
	private JFormattedTextField formattedField;
	private Component referenceBtn;
	private String name;
	
	public TextField(String label, JFormattedTextField formattedTextField, Column column) {
		this.formattedField = formattedTextField;
		this.column = column;
		this.formattedField.setEnabled(!column.isPrimary());
		this.name = label;    //this.name = column.getName();
		if(column.getSize()<60) {
			this.formattedField.setPreferredSize(new Dimension(160, 20));
		}
		else if (column.getSize() > 120) {
			this.formattedField.setPreferredSize(new Dimension((int)(column.getSize() * 1.1) + 40, 20));
		} 
		else {
			this.formattedField.setPreferredSize(new Dimension((int)(column.getSize() * 0.8) + 40, 20));
		}
		
		if(column.getRefTable() != null) {
			setEnabled(false);
		}
		
		this.formattedField.getDocument().addDocumentListener(new ChangeListener());
		
		JLabel labela = new JLabel(label);
		
		labela.setLabelFor(this.formattedField);
		labela.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		formattedTextField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		setBackground(Color.white);
		setBorder(new EmptyBorder(7, 15, 7, 15));
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(labela, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(this.formattedField, gbc);
		
		setVisible(true);
	}
	
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return formattedField.getValue();
	}

	@Override
	public void setValue(Object object) {
		// TODO Auto-generated method stub
		formattedField.setValue(object);	
	}

	@Override
	public void setReferenceBtn(Component btn) {
		// TODO Auto-generated method stub
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 4, 0, 0);
		
		btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
		this.referenceBtn = btn;
		//dodala naknadno
		  btn.setEnabled(true);
		add(btn, gbc);
		
	}
	@Override
	public void setEnabled(boolean enabled) {
		if(referenceBtn == null) {
			formattedField.setEnabled(enabled);
		}
		else {
//			referenceBtn.setEnabled(enabled);
//			referenceBtn.setVisible(enabled);
		}
	}

	@Override
	public boolean isPrimary() {
		// TODO Auto-generated method stub
		return column.isPrimary();
	}

	@Override
	public boolean isNullable() {
		// TODO Auto-generated method stub
		return column.isNullable();
	}
	@Override
	public String getName() {
		return this.name;
	}
	
	private class ChangeListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkIfValid();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkIfValid();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkIfValid();
		}
		private void checkIfValid() {
			boolean isValid = formattedField.isEditValid();
			if(isValid) {
				formattedField.setBorder(null);
			}
			else {
			    formattedField.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
		}
		
	}
}
