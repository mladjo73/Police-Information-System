package model;

import java.util.Vector;

/**
 * Apstraktna klasa koja predstavlja element stabla baze podataka sa podrškom za hijerarhijske strukture.
 * @author grupa4
 * 
 *
 */

public abstract class TreeElement {

	protected String code = null;
	public String name = null;
	
	private Vector<TreeElement> treeElements = new Vector<TreeElement>();
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	public int getIndexOfElement(TreeElement element) {
		return treeElements.indexOf(element);
	}
	
	public void addElement(TreeElement element) {
		treeElements.add(element);
	}
	
	public Vector<TreeElement> getAllElements() {
		return treeElements;
	}
	
	public TreeElement getElementAt(int index) {
		return treeElements.get(index);
	}
	
	public String getName() {
		return name;
	}
	
public static class Package extends TreeElement{
		
		public Package() {
			// TODO Auto-generated constructor stub
		}
		
		public Package(String name) {
			// TODO Auto-generated constructor stub
			this.name = name;
		}
	}
	
	public static class TableHelper extends TreeElement{
		
		private Vector<TableHelper> refrences = new Vector<TreeElement.TableHelper>();
		
		private String createSProc = null;
		private String retrieveSProc = null;
		private String updateSProc = null;
		private String deleteSProc = null;
		public String getCreateSProc() {
			return createSProc;
		}
		public void setCreateSProc(String createSProc) {
			this.createSProc = createSProc;
		}
		public String getRetrieveSProc() {
			return retrieveSProc;
		}
		public void setRetrieveSProc(String retrieveSProc) {
			this.retrieveSProc = retrieveSProc;
		}
		public String getUpdateSProc() {
			return updateSProc;
		}
		public void setUpdateSProc(String updateSProc) {
			this.updateSProc = updateSProc;
		}
		public String getDeleteSProc() {
			return deleteSProc;
		}
		public void setDeleteSProc(String deleteSProc) {
			this.deleteSProc = deleteSProc;
		}
				
		public void addReference(TableHelper reference) {
			refrences.add(reference);
		}
		
		public Vector<TableHelper> getAllRefernces(){
			return refrences;
		}	
			
			
		}
	public static class Column extends TreeElement{
		
		private Boolean primary = null;
		private Boolean nullable = null;
		private String refColumn;
		private String refTable;
		private String type;
		private int size;
		private int scale;
		
		public Column() {}
		public Column(String name) {
			this.name = name;
		}
		
		public String getRefColumn() {
			return refColumn;
		}
		public void setRefColumn(String refColumn) {
			this.refColumn = refColumn;
		}
		public String getRefTable() {
			return refTable;
		}
		public void setRefTable(String refTable) {
			this.refTable = refTable;
		}
		public Boolean isPrimary() {
			return primary;
		}
		public void setPrimary(Boolean primary) {
			this.primary = primary;
		}
		public Boolean isNullable() {
			return nullable;
		}
		public void setNullable(Boolean nullable) {
			this.nullable = nullable;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}
		public int getScale() {
			return scale;
		}
		public void setScale(int scale) {
			this.scale = scale;
		}
	}
	
}
