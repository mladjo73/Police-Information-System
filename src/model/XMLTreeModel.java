package model;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.TreeElement.Column;

/**
 * Model stabla za prikaz XML strukture baze podataka koristeći TreeElement kao čvorove stabla.
 * @author grupa4
 * 
 *
 */

public class XMLTreeModel extends DefaultTreeModel {
	private static final long serialVersionUID = 1L;

	Document document = null;
	
	XPathExpression xPathExpression = null;
	
	XPath xPath = null;
	
	TreeElement rootPackage;
	
	public XMLTreeModel(TreeNode root,String xmlPath) {
		super(root);
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			
			document = builder.parse(xmlPath);
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			xPath = xPathFactory.newXPath();
			
			xPathExpression = xPath.compile("database");
			Node database = (Node)xPathExpression.evaluate(document, XPathConstants.NODE);
			
			rootPackage = new TreeElement.Package();
			rootPackage.code = null;
			rootPackage.name = database.getAttributes().getNamedItem("name").getNodeValue();
			
			xPathExpression = xPath.compile("package");
			NodeList packages = (NodeList) xPathExpression.evaluate(database, XPathConstants.NODESET);
			
			subPacks(packages, rootPackage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void subPacks(NodeList packages, TreeElement rootPackage) throws XPathExpressionException {
	    for (int i = 0; i < packages.getLength(); i++) {
	        TreeElement tempPackage = new TreeElement.Package();
	        tempPackage.code = null;
	        tempPackage.name = packages.item(i).getAttributes().getNamedItem("name").getNodeValue();
	        rootPackage.addElement(tempPackage);

	        xPathExpression = xPath.compile("package");
	        NodeList subpackages = (NodeList) xPathExpression.evaluate(packages.item(i), XPathConstants.NODESET);

	        subPacks(subpackages, tempPackage);

	        xPathExpression = xPath.compile("table");
	        NodeList tables = (NodeList) xPathExpression.evaluate(packages.item(i), XPathConstants.NODESET);

	        for (int j = 0; j < tables.getLength(); j++) {
	            TreeElement.TableHelper tempTable = new TreeElement.TableHelper();
	            tempPackage.addElement(tempTable);
	            tempTable.code = tables.item(j).getAttributes().getNamedItem("code").getNodeValue();
	            tempTable.name = tables.item(j).getAttributes().getNamedItem("name").getNodeValue();

	            xPathExpression = xPath.compile("column");
	            NodeList columns = (NodeList) xPathExpression.evaluate(tables.item(j), XPathConstants.NODESET);

	            for (int k = 0; k < columns.getLength(); k++) {
	            	TreeElement.Column tempColumn = new Column();
					
					tempColumn.code = columns.item(k).getAttributes().getNamedItem("code").getNodeValue();
					tempColumn.name = columns.item(k).getAttributes().getNamedItem("name").getNodeValue();
					
					boolean pomBool = false;
					
					if (columns.item(k).getAttributes().getNamedItem("nullable").getNodeValue().equals("true"))
					{
						pomBool = true;
					}
					
					tempColumn.setNullable(pomBool);
					
					pomBool = false;
					
					if (columns.item(k).getAttributes().getNamedItem("primary").getNodeValue().equals("true"))
					{
						pomBool = true;
					}
					
					tempColumn.setPrimary(pomBool);
					
					tempTable.addElement(tempColumn);
					
					//references
					xPathExpression = xPath.compile("refcolumn");
					Node refColumn = (Node) xPathExpression.evaluate(columns.item(k), XPathConstants.NODE);
					
					if(!(refColumn == null)) {
						tempColumn.setRefColumn(refColumn.getTextContent());
						//System.out.println("Ovo je ref kolumn: "+refColumn.getTextContent());
						
						xPathExpression = xPath.compile("reftable");
						Node refTable = (Node) xPathExpression.evaluate(columns.item(k), XPathConstants.NODE);
						tempColumn.setRefTable(refTable.getTextContent());
						//System.out.println("Ovo je ref tbl: "+refTable.getTextContent());
					}
	            }

	            xPathExpression = xPath.compile("references");
	            NodeList references = (NodeList) xPathExpression.evaluate(tables.item(j), XPathConstants.NODESET);

	            for (int k = 0; k < references.getLength(); k++) {
	                TreeElement.TableHelper refTable = new TreeElement.TableHelper();
	                refTable.code = references.item(k).getAttributes().getNamedItem("refTable").getNodeValue();

	                tempTable.addReference(refTable);

	                xPathExpression = xPath.compile("refColumn");
	                NodeList refColumns = (NodeList) xPathExpression.evaluate(references.item(k), XPathConstants.NODESET);
	                for (int l = 0; l < refColumns.getLength(); l++) {
	                    TreeElement.Column refColumn = new TreeElement.Column();
	                    refColumn.code = refColumns.item(l).getTextContent();
	                    refTable.addElement(refColumn);
	                }
	            }

	            xPathExpression = xPath.compile("crud/create");
	            Node create = (Node) xPathExpression.evaluate(tables.item(j), XPathConstants.NODE);
	            if (create != null) {
	                tempTable.setCreateSProc(create.getTextContent());
	            }

	            xPathExpression = xPath.compile("crud/retrieve");
	            Node retrieve = (Node) xPathExpression.evaluate(tables.item(j), XPathConstants.NODE);
	            if (retrieve != null) {
	                tempTable.setRetrieveSProc(retrieve.getTextContent());
	            }

	            xPathExpression = xPath.compile("crud/update");
	            Node update = (Node) xPathExpression.evaluate(tables.item(j), XPathConstants.NODE);
	            if (update != null) {
	                tempTable.setUpdateSProc(update.getTextContent());
	            }

	            xPathExpression = xPath.compile("crud/delete");
	            Node delete = (Node) xPathExpression.evaluate(tables.item(j), XPathConstants.NODE);
	            if (delete != null) {
	                tempTable.setDeleteSProc(delete.getTextContent());
	            }
	        }
	    }
	}


	@Override
	public Object getChild(Object parent, int index) {
		if(parent instanceof TreeElement.Package) {
			return ((TreeElement.Package) parent).getElementAt(index);			
		}
		else if (parent instanceof TreeElement.TableHelper) {
			return null;
		}
		return null;
	}
	
	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof TreeElement.Package) {
			return ((TreeElement.Package) parent).getAllElements().size();
		} else if (parent instanceof TreeElement.TableHelper) {
			return 0;
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof TreeElement.Package) {
			return ((TreeElement.Package) parent).getIndexOfElement((TreeElement) child);
		} else if (parent instanceof TreeElement.TableHelper) {
			return -1;
		}
		return -1;
	}

	@Override
	public Object getRoot() {
		return this.rootPackage;
	}

	@Override
	public boolean isLeaf(Object node) {
		if (node instanceof TreeElement.TableHelper) {
			return true;
		}
		return false;
	}
}
