package org.primordion.xholon.base;

import java.util.List;
import java.util.Vector;

import org.primordion.xholon.util.Misc;

/**
 * Parent Child Sibling path language.
 * TODO this class is incomplete and does not yet work correctly
 * This is an alternative to using XPath.
 * The following command characters may be used.
 * <ul>
 * <li>/ root</li>
 * <li>p parent</li>
 * <li>c child</li>
 * <li>s sibling</li>
 * <li>(0|1|2|3|4|5|6|7|8|9)* repetition of previous</li>
 * </ul>
 * examples: ppcsccs /ccss cs13
 * <p>TODO IXpath should be renamed IPath, to encompass XPath, PcsPath, and any other path languages.</p>
 * <p>TODO possibly use + for next sibling, and - for previous sibling</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 17, 2010)
 */
public class PcsPath extends Xholon implements IXPath {
	private static final long serialVersionUID = -9075656950988101968L;

	/*
	 * @see org.primordion.xholon.base.IXPath#evaluate(java.lang.String, java.lang.Object, int)
	 */
	public Object evaluate(String expression, Object item, int returnType) {
		return (String)evaluate(expression, item, XPathConstants.STRING);
	}

	/*
	 * @see org.primordion.xholon.base.IXPath#evaluate(java.lang.String, java.lang.Object)
	 */
	public String evaluate(String expression, Object item) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see org.primordion.xholon.base.IXPath#evaluate(java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public IXholon evaluate(String expression, IXholon item) {
		return evaluate(expression, item, null);
	}

	/*
	 * @see org.primordion.xholon.base.IXPath#evaluate(java.lang.String, org.primordion.xholon.base.IXholon, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public IXholon evaluate(String expression, IXholon item, List pathList) {
		if ((expression == null) || (expression.length() == 0)) {
			getLogger().debug("PcsPath expression is null or has zero length for context: " + item);
			return null;
		}
		if (item == null) {
			getLogger().debug("PcsPath item is null");
			return null;
		}
		int index = 0;
		IXholon contextNode = null;
		if (expression.charAt(index) == '/') {
			contextNode = item.getRootNode(); // this is an absolute location path
			index++;
		}
		else {
			contextNode = item; // this is a relative location path
		}
		while (index < expression.length()) {
			switch (expression.charAt(index)) {
			case 'p': // parent
				contextNode = contextNode.getParentNode();
				index++;
				break;
			case 'c': // child
				contextNode = contextNode.getFirstChild();
				index++;
				break;
			case 's': // sibling
				contextNode = contextNode.getNextSibling();
				index++;
				break;
			case '0': // sibling index
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				int numRepeats = Misc.atoi(expression, index);
				for (int i = 0; i < numRepeats; i++) {
					contextNode = contextNode.getNextSibling();
					if (contextNode == null) {
						getLogger().debug("PcsPath expression has navigated to a null at index " + index
								+ " in expression: " + expression + " context: " + item);
						return null;
					}
				}
				index += Integer.toString(numRepeats).length();
				break;
			default:
				getLogger().debug("PcsPath expression has invalid character [" + expression.charAt(index)
						+ "] in expression: " + expression + " context: " + item);
				return null;
			}
			if (contextNode == null) {
				getLogger().debug("PcsPath expression has navigated to a null at index " + index
						+ " in expression: " + expression + " context: " + item);
				return null;
			}
			if (pathList != null) {
				pathList.add(contextNode);
			}
		}
		if (contextNode == null) {
			getLogger().debug("PcsPath expression is returning null: "
					+ expression + " context: " + item);
		}
		return contextNode;
	}

	/**
	 * @see org.primordion.xholon.base.IXPath#getExpression(org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholon, boolean)
	 * @param shouldRepeat When there is more than one (s)ibling, write multiple 's' (true),
	 * or write an index (false). examples: sss s3
	 */
	public String getExpression(IXholon descendant, IXholon ancestor, boolean shouldRepeat) {
		String expression = "";
		IXholon node = descendant;
		while (node != null) {
			int index = node.getSelfAndSiblingsIndex(false);
			if (index == -1) {
				// this may happen with the root node
				expression = "/" + expression;
			}
			else if (index == 0) {
				// this is the first child
				expression = "c" + expression;
			}
			else if (index == 1) {
				// this is the first sibling of a first child
				expression = "cs" + expression;
			}
			else {
				if (shouldRepeat) { // ex: sss
					StringBuilder siblingStr = new StringBuilder(index);
					while (index > 0) {
						siblingStr.append('s');
						index--;
					}
					expression = "c" + siblingStr + expression;
				}
				else { // ex: s3
					expression = "cs" + index + expression;
				}
			}
			if (node == ancestor) {
				break;
			}
			node = node.getParentNode();
		}
		if (node == null) {
			return ""; // ancestor not found
		}
		return expression;
	}

	/*
	 * @see org.primordion.xholon.base.IXPath#getExpression(org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholon)
	 * TODO not yet complete
	 */
	public String getExpression(IXholon sourceNode, IXholon reffedNode) {
		if (sourceNode == null) {return null;}
		if (reffedNode == null) {return null;}
		String sourceExpr = getExpression(sourceNode, sourceNode.getRootNode(), true);
		String reffedExpr = getExpression(reffedNode, reffedNode.getRootNode(), true);
		println("source:" + sourceExpr);
		println("reffed:" + reffedExpr);
		if (sourceExpr == null) {return null;}
		if (reffedExpr == null) {return null;}
		String pathExpr = null;
		if (reffedExpr.startsWith(sourceExpr)) {
			// reffed is a child/descendant of source, or reffed is a next sibling of source
			pathExpr = reffedExpr.substring(sourceExpr.length());
		}
		else if (sourceExpr.startsWith(reffedExpr)) {
			// reffed is a parent/ancestor of source; convert all (c)hild to (p)arent
			// or reffed is a previous sibling
			String expr = sourceExpr.substring(reffedExpr.length());
			pathExpr = "";
			for (int i = 0; i < expr.length(); i++) {
				if (expr.charAt(i) == 'c') {
					pathExpr += 'p';
				}
			}
			if (expr.charAt(0) == 's') {
				pathExpr = "p" + pathExpr + getExpression(reffedNode.getParentNode(), reffedNode);
			}
		}
		println(evaluate(pathExpr, sourceNode));
		return pathExpr;
	}

	/*
	 * @see org.primordion.xholon.base.IXPath#searchForClosestNeighbors(int, int, java.lang.String, java.lang.String, int, boolean, org.primordion.xholon.base.IXholon)
	 */
	@SuppressWarnings("unchecked")
	public Vector searchForClosestNeighbors(int distance, int include,
			String excludeXhcName, String xhcName, int maxQuantity,
			boolean matchSuperClasses, IXholon xhNode) {
		// TODO Auto-generated method stub
		return null;
	}

}
