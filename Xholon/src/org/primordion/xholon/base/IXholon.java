/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.xholon.base;

//import java.io.Writer;
import java.util.List;
import java.util.Vector;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IAttribute;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXml2Xholon;
import org.primordion.xholon.io.xml.IXmlWriter;


/*
Example of a tree: (^ and v represent up and down arrow heads)

       one
        ^
       / \
      /   \
     v     \
    two-->three
     ^
    /
   /
  v
four

In this example:
- one has no parent and no nextSibling, but does have a firstChild (= two).
- two has a parent (one), a firstChild (four), and a nextSibling (three).
- three has a parent (one).
- four has a parent (two).
- one is the root node.
- one has treeSize() of 4.
- one has height() of 2.
 */

/**
 * A Xholon is a node within a hierarchical tree structure,
 * and builds on the capabilities of tree nodes.
 * A tree node is a node in a tree. It has one parent (none if it's the root),
 * and may have one or more children, and one or more siblings.
 * A Xholon is both a whole, and a part of some other Xholon, at the same time.
 * A Xholon is an instance of a IXholonClass.
 * A Xholon can be an active object, a passive object, a container, or any combination of these three,
 * as defined by its IXholonClass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jul 8, 2005)
 */
public interface IXholon extends XholonMarker {

// IXholon constants

/** The default maximum size of the application message Q. */
public static final int DEFAULT_SIZE_MSG_Q = 0;

/** The default maximum size of the system message Q. */
public static final int DEFAULT_SIZE_SYSTEM_MSG_Q = 10;

/** The default initial value of the Xholon ID before it's explicitly set. */
public static final int XHOLON_ID_DEFAULT = 0;

/** The ID of a termporary node, such as an &lt;attribute&gt; node. */
public static final int XHOLON_ID_NULL = -1;

// getName(String template) templates
public static final String GETNAME_DEFAULT      = "r:c_i^";
public static final String GETNAME_NOROLENAME   = "^^c_i^";
public static final String GETNAME_ROLENAMESEP_ = "r_c_i^";
public static final String GETNAME_LOCALPART_ID = "^^l_i^";
public static final String GETNAME_ROLENAME_OR_CLASSNAME = "R^^^^^"; // but not both
public static final int GETNAME_SIZE_TEMPLATE = 6; // number of characters in a template string

/** Default authority of the base Uniform Resource Identifier (URI), for use with getUri() setUri(String). */
//public static final String URI_BASE_AUTH_DEFAULT = "http://primordion.org/"; //"http://dbpedia.org/";

/** Default path of the Uniform Resource Identifier (URI), for use with getUri() setUri(String). */
//public static final String URI_BASE_PATH_DEFAULT = "resource/";

/** Default base Uniform Resource Identifier (URI), for use with getUri() setUri(String). */
//public static final String URI_BASE_DEFAULT = URI_BASE_AUTH_DEFAULT + URI_BASE_PATH_DEFAULT;

//*******************************************************************************************
// ITreeNode - the following are methods that were declared in the now deprecated ITreeNode interface
/**
 * Remove this node from the model, by recursively removing all children and next siblings,
 * and by then returning it to the factory.
 */
public abstract void remove();

/**
 * Get the instance of Application that owns this Xholon instance and the model of which it is a part.
 * @return An instance of Application.
 */
public abstract IApplication getApp();

/**
 * Set the instance of Application that owns this Xholon instance and the model of which it is a part.
 * @param app An instance of Application.
 */
public abstract void setApp(IApplication app);

//							get and set functions

/**
 * Get parent of this node.
 * @return This node's current parent.
 */
public abstract IXholon getParentNode();
/**
 * Set parent of this node.
 * @param treeNode This node's new parent.
 */
public abstract void setParentNode( IXholon treeNode );
/**
 * Get first (leftmost) child of this node.
 * @return First child, or null.
 */
public abstract IXholon getFirstChild();
/**
 * Set first (leftmost) child of this node.
 * @param treeNode This node's new first child.
 */
public abstract void setFirstChild( IXholon treeNode );
/**
 * Get next (right) sibling of this node.
 * @return Next sibling, or null.
 */
public abstract IXholon getNextSibling();
/**
 * Set next (right) sibling of this node.
 * @param treeNode This node's new next sibling.
 */
public abstract void setNextSibling( IXholon treeNode );
/**
 * Get previous (left) sibling of this node. (may be expensive in processing time)
 * @return Previous Sibling, or null.
 */
public abstract IXholon getPreviousSibling();
/**
 * Get root of the tree or subtree that this node is a part of.
 * @return Root. If this node is the root, it returns itself.
 */
public abstract IXholon getRootNode();

/**
 * Find the first child that matches the specified XholonClass id.
 * @param childXhClass A XholonClass id.
 * @return A matching child xholon, or null.
 */
public abstract IXholon findFirstChildWithXhClass(int childXhClassId);

/**
 * Find the first child that matches the specified XholonClass name.
 * @param childXhClass A XholonClass name.
 * @return A matching child xholon, or null.
 */
public abstract IXholon findFirstChildWithXhClass(String childXhClassName);

// The following are two convenience methods to set related links.

/**
 * Set parent child links. Call this function on the firstChild of a parent.
 * equivalent to:
 *   node->setParent( parent );
 *   parent->setFirstChild( node );
 * @param parent This node's new parent.
 */
public abstract void setParentChildLinks( IXholon parent );

/**
 * Set parent sibling links. Call this function on the node that is pointed to by the previousSibling.
 * equivalent to:
 *   node->setParent( previousSibling->getParent() );
 *   previousSibling->setNextSibling( node );
 *   @param previousSibling This node's new previous sibling.
 */
public abstract void setParentSiblingLinks( IXholon previousSibling );

/**
 * Set the value of an attribute, given it's name.
 * The default implementation of this should handle any variable with the name "val".
 * Concrete subclasses may handle any other variables, which would be required if using JavaMicro reflection.
 * @param attrName The name of an attribute.
 * @param attrVal The intended value of the attribute.
 * @return The Java class type of the input val.
 * Returns JAVACLASS_UNKNOWN if the attribute cannot be set.
 */
public abstract int setAttributeVal(String attrName, String attrVal);

/**
 * Detach this node from its parent and from any siblings.
 * Any siblings are reattached to each other.
 *   If there was a previous sibling and a next sibling, these will be attached to each other.
 *   If there was only a previous sibling, it's next sibling will now be null.
 * If this node was the parent's firstChild, the parent's firstChild link will be appropriately adjusted.
 * Any children are left intact. The entire subtree is detached.
 * The node is NOT deleted or returned to the factory.
 */
public abstract void removeChild();

/**
 * Insert this node after its new previous sibling node.
 * @param newPreviousSibling New previous sibling of this node.
 */
public abstract void insertAfter(IXholon newPreviousSibling);

/**
 * Create a new child, and insert it after this node.
 * @param xhClassName Name of an existing IXholonClass.
 * @param roleName Optional role name.
 * @return The newly created node. It's optional if the calling method wants to use this in any way.
 */
public abstract IXholon insertAfter(String xhClassName, String roleName);

/**
 * Create a new child, and insert it after this node.
 * Make it an instance of a designated Java class.
 * @param xhClassName Name of an existing IXholonClass.
 * @param roleName Optional role name.
 * @param implName Full-package name of a Java class.
 * @return The newly created node. It's optional if the calling method wants to use this in any way.
 */
public abstract IXholon insertAfter(String xhClassName, String roleName, String implName);

/**
 * Insert this node before its new next sibling node.
 * @param newNextSibling New next sibling of this node.
 */
public abstract void insertBefore(IXholon newNextSibling);

/**
 * Create a new child, and insert it before this node.
 * @param xhClassName Name of an existing IXholonClass.
 * @param roleName Optional role name.
 * @return The newly created node. It's optional if the calling method wants to use this in any way.
 */
public abstract IXholon insertBefore(String xhClassName, String roleName);

/**
 * Create a new child, and insert it before this node.
 * Make it an instance of a designated Java class.
 * @param xhClassName Name of an existing IXholonClass.
 * @param roleName Optional role name.
 * @param implName Full-package name of a Java class.
 * @return The newly created node. It's optional if the calling method wants to use this in any way.
 */
public abstract IXholon insertBefore(String xhClassName, String roleName, String implName);

/**
 * Insert this node as the first child of its new parent node.
 * @param newParentNode New parent of this node.
 */
public abstract void insertFirstChild(IXholon newParentNode);

/**
 * Append this node as the last child of its new parent node.
 * @param newParentNode New parent of this node.
 */
public abstract void appendChild(IXholon newParentNode);

/**
 * Create a new child, and append it as the last child of this node.
 * @param xhClassName Name of an existing IXholonClass.
 * @param roleName Optional role name.
 * @return The newly created node. It's optional if the calling method wants to use this in any way.
 */
public abstract IXholon appendChild(String xhClassName, String roleName);

/**
 * Create a new child, and append it as the last child of this node.
 * Make it an instance of a designated Java class.
 * @param xhClassName Name of an existing IXholonClass.
 * @param roleName Optional role name.
 * @param implName Full-package name of a Java class.
 * @return The newly created node. It's optional if the calling method wants to use this in any way.
 */
public abstract IXholon appendChild(String xhClassName, String roleName, String implName);

/**
 * Are instances of this class responsible for appending their own children?
 * This is useful for container classes that need to control the type of nodes
 * that they contain.
 * @return true or false
 */
public abstract boolean appendsOwnChildren();

/**
 * Replace a node in the tree with a replacement node.
 * The replacement node will retain the links to other nodes that the current node has.
 * It will get its parent node, sibling nodes (if any), and child nodes (if any) from the current node.
 * But it will NOT get the ports, or Java attributes (id, XholonClass, etc.).
 * The current node will be removed and will no longer be available.
 * @param replacementNode A free-floating node, one that is not currently in the Xholon tree.
 * It will replace the current node at the same location in the tree.
 */
public abstract void replaceNode(IXholon replacementNode);

/**
 * Swap this node with otherNode.
 * @param otherNode The node to swap position with.
 */
public abstract void swapNode(IXholon otherNode);

//					standard tree utility functions

/**
 * Get the number of ancestors of this node, excluding this node itself.
 * The depth of the root node in the tree is 0.
 */
public abstract int depth();

/**
 * Get number of levels in hierarchical tree.
 * The height is computed by traversing both the firstChild and nextSibling nodes.
 * @return Height of the tree starting from this node.
 */
public abstract int height();

/**
 * Get the number of levels in the tree starting from the current node.
 * This is the same as the height() method, except that it doesn't count sibling nodes,
 * although it does traverse sibling nodes.
 * @return Number of levels in the tree or subtree starting from this node.
 */
public abstract int getNumLevels();

/**
 * Get number of nodes in tree.
 * @return Total number of nodes in the tree, starting from and including this node.
 */
public abstract int treeSize();

/**
 * Is this the root node of the tree or subtree?
 * @return true or false
 */
public abstract boolean isRootNode();

/**
 * Is this an external node (no child or siblings).
 * @return true or false
 */
public abstract boolean isExternal();

/**
 * Is this an internal node (has child and/or siblings).
 * @return true or false
 */
public abstract boolean isInternal();

/**
 * Is this a leaf node (has no child, but may have siblings).
 * @return true or false
 */
public abstract boolean isLeaf();

/**
 * Does this IXholon node handle its own Attribute children?
 * Typically, a IXholon node should return false,
 * which will tell the framework to handle Attribute children in the default way.
 * If a IXholon node returns true, then it is completely responsible for handling
 * its own Attribute children, which it may want to do in postConfigure().
 * A proxy or wrapper is one example of a IXholon class that would typically
 * choose to handle its own Attribute children, because these are quite possibly
 * attributes of the object that it wraps or proxies for. An Attribute is any IXholon
 * node whose name starts with "Attribute_" and/or that implements IAttribute.
 * @return true or false
 */
public abstract boolean isAttributeHandler();

/**
 * Get all children of this node.
 * In general it's better to use the visit method rather than this getChildNodes method.
 * @param deep If true then return entire nested subtree, if false return only immediate children.
 * @return Vector containining all the children.
 */
public abstract Vector getChildNodes(boolean deep);

/**
 * Get the nth child of this node, where 0 <= n < number of children.
 * @param n The index of the child being requested.
 * @param deep If true then return entire nested subtree, if false return only immediate children.
 * @return The nth child, or null.
 */
public abstract IXholon getNthChild(int n, boolean deep);

/**
 * Does this node have a parent.
 * @return true or false
 */
public abstract boolean hasParentNode();

/**
 * Does this node have a first (left) child.
 * @return true or false
 */
public abstract boolean hasChildNodes();

/**
 * Does this node have a next (right) sibling.
 * @return true or false
 */
public abstract boolean hasNextSibling();

/**
 * Does this node have either a first child or a next sibling or both.
 * @return true or false
 */
public abstract boolean hasChildOrSiblingNodes();

/**
 * Does this node have at least one sibling node, either a next sibling or a previous sibling?
 * @return true or false
 */
public abstract boolean hasSiblingNodes();

/**
 * Get number of children that this node has.
 * @param deep If true then return entire nested subtree, if false return only immediate children.
 * @return Number of children.
 */
public abstract int getNumChildren(boolean deep);

/**
 * Get last (rightmost) child.
 * @return Last child, or null.
 */
public abstract IXholon getLastChild();

/**
 * Get last (rightmost) sibling.
 * @return Last sibling, or null.
 */
public abstract IXholon getLastSibling();

/**
 * Get first (leftmost) sibling.
 * @return First sibling, or null.
 */
public abstract IXholon getFirstSibling();

/**
 * Get self and siblings.
 * @param includeSameClassOnly Whether or not to only return nodes that are the same XholonClass as this node.
 * @return Vector containing self and all siblings.
 */
public abstract Vector getSelfAndSiblings(boolean includeSameClassOnly);

/**
 * Get siblings.
 * @return Vector containing all siblings.
 */
public abstract Vector getSiblings();

/**
 * Get nth sibling.
 * @param n The index of the sibling being requested.
 * @return The nth sibling, or null.
 */
public abstract IXholon getNthSibling(int n);

/**
 * Get number of siblings.
 * @return The number of siblings.
 */
public abstract int getNumSiblings();

/**
 * Get the position of this node among its siblings, as an index.
 * @param includeSameClassOnly Whether or not to only include nodes that are the same XholonClass as this node.
 * @return A positive index >= 0, or -1 if the operation fails.
 */
public abstract int getSelfAndSiblingsIndex(boolean includeSameClassOnly);

/**
 * <p>Is this node unique among its siblings?
 * It is unique if no sibling has the same XholonClass,
 * or if the node has no siblings.
 * Otherwise it is not unique.</p>
 * @return true (unique) or false (not unique)
 */
public abstract boolean isUniqueSibling();

/**
 * <p>Is this node unique among its siblings?
 * It is unique if no sibling has the same XholonClass and the same roleName,
 * or if the node has no siblings.
 * Otherwise it is not unique.</p>
 * <p>TODO If the roleName is null or the empty String, then it is considered not unique.</p>
 * @return true (unique) or false (not unique)
 */
public abstract boolean isUniqueSiblingRoleName();

// whether to include (P)arent, (S)iblings, (C)hildren when do getNeighbors()
//                               PSC
public static final int NINCLUDE_xxx = 0;
public static final int NINCLUDE_xxC = 1;
public static final int NINCLUDE_xSx = 2;
public static final int NINCLUDE_xSC = 3;
public static final int NINCLUDE_Pxx = 4;
public static final int NINCLUDE_PxC = 5;
public static final int NINCLUDE_PSx = 6;
public static final int NINCLUDE_PSC = 7;

/**
 * Get neighbors.
 * @param distance How far within the tree to search for neighbors.
 * @param include Whether to include (P)arent and/or (S)iblings and/or (C)hildren.
 * @param excludeBecName Name of a XholonClass, used to limit the scope of the returned Vector.
 *        TODO this uses an IXholon method
 * @return Vector containing all neighbors (instances of TreeNode).
 */
public abstract Vector getNeighbors(int distance, int include, String excludeBecName);

// tree traversal print functions that MAY be overridden in concrete subclasses

/**
 * Pre-order print.
 * Print current node; then traverse children and siblings.
 * @param level Level in the tree, where the root node is level 0.
 */
public abstract void preOrderPrint( int level );

/**
 * In-order print.
 * Traverse children; then print current node; then traverse siblings.
 * @param level Level in the tree, where the root node is level 0.
 */
public abstract void inOrderPrint( int level );

/**
 * Post-order print.
 * Traverse children and siblings; then print current node.
 * @param level Level in the tree, where the root node is level 0.
 */
public abstract void postOrderPrint( int level );

/**
 * Visit another IXholon, and perform some operation.
 * This can be used for a wide range of purposes.
 * A client that wants to visit all nodes in a IXholon tree,
 * can call this method on the root of the tree,
 * passing itself or some other delegated object in as the visitor node.
 * This method allows implementation of the Visitor Pattern described
 * in Gamma, et al. (1995) Design Patterns, p. 331,
 * although the Xholon implementation is somewhat different.
 * This pattern is a callaboration between visitor
 * and visitee objects.
 * <p>If a visitor and visitee object are the same,
 * then the default implementation of the visit method
 * will not call its visitor.</p>
 * @param node Some other IXholon node.
 * This can either be the visitor or the visitee.
 * @return true or false, used to determine whether to continue.
 */
public abstract boolean visit(IXholon node);

/**
 * Write self to a file as XML.
 * Note: This method should be used sparingly.
 * It has been replaced for general use by toXml(IXholon2Xml xholon2xml, XmlWriter xmlWriter) .
 * @param level Level in the tree, where the root node is level 0.
 * @param fw The file to write to.
 */
//public abstract void writeXml( int level, Writer fw );

/**
 * Initialize the tree node.
 * Typically this is only used to re-initialize a node,
 * such as when it is reused through the TreeNodeFactoryStatic.
 */
public abstract void initialize();

/**
 * Initialize any static varibles that can't be statically initialized.
 * ex: Rcs2 needs to initialize some static variables after XholonClass instances have been read in. 
 */
public abstract void initStatics();

/**
 * Perform some action, typically once at start up; MAY be overridden.
 * Recursive; application should call this only for root.
 */
public abstract void preConfigure();

/**
 * Perform some action, typically once at start up; MAY be overridden.
 * Recursive; application should call this only for root.
 */
public abstract void configure();

/**
 * Configure something based on a configure instruction.
 * @param instructions A String that contains a configure instruction.
 * @param instructIx An index into the instructions String.
 * @return An updated index into the instructions String.
 */
public abstract int configure(String instructions, int instructIx);

/**
 * Perform some action, typically once at start up; MAY be overridden.
 * Recursive; application should call this only for root.
 */
public abstract void postConfigure();

/**
 * Pre-Reconfigure, such as at the start of a new GP generation.
 */
public abstract void preReconfigure();

/**
 * Reconfigure, such as at the start of a new GP generation.
 */
public abstract void reconfigure();

/**
 * Post-reconfigure.
 */
public abstract void postReconfigure();

/**
 * Do any setup required before doing the main action during a time step.
 */
public abstract void preAct();

/**
 * Do some action during this time step.
 * Typically all nodes in the tree will do their pre-actions (optional), then all will do their actions,
 * and then all will do their post-actions (optional).
 */
public abstract void act();

/**
 * Do some action during this time step.
 * This is the non-recursive version of act().
 * Implementations of actNr() should typically NOT call actNr() on their children or siblings,
 * and should NOT call super.actNr() .
 * Typically this method is used when a parent or other node wants
 * to request that one or more dependent nodes perform some action during a time step,
 * and wants to control when or if it happens.
 */
public abstract void actNr();

/**
 * Do any required work after doing the main action during a time step.
 */
public abstract void postAct();

/**
 * Do any required cleanup work once the application has stopped executing.
 * This could be used for example to delete or cancel any timers that have been set,
 * which would be especially important if another application is subsequently opened.
 */
public abstract void cleanup();

/**
 * Get ID of this TreeNode instance.
 * @return An integer ID, unique within this application, that is assigned when the TreeNode is created.
 */
public abstract int getId();

/**
 * Set the ID of this TreeNode instance.
 * @param id The ID of this TreeNode.
 */
public abstract void setId( int id );

/**
 * Write an object to System.out or to a PrintWriter.
 * @param obj The object to write.
 */
public abstract void print(Object obj);

/**
 * Write an object to System.out or to a PrintWriter, terminated with an end-of-line.
 * @param obj The object to write.
 */
public abstract void println(Object obj);


// *******************************************************************************************
// IXholon

/**
 * Get name, unique within this application, of this Xholon instance.
 * The name is a concatenation of the IXholonClass name and the Xholon unique ID.
 * The first letter is converted to lowercase, and a "_" is used to separate the name and ID.
 * ex: "helloWorld_123"
 * If the Xholon instance has a roleName, it is included in front of the other two elements,
 * and separated from the IXholonClass name by a ":".
 * ex: "helloRole:helloWorld_123"
 * @return The unique name of this Xholon instance.
 */
public abstract String getName();

/**
 * Get the name of this Xholon instance, formatted according to a template.
 * This method is intended to be used only for some special purpose.
 * To get the standard name of a Xholon instance, you should call getName() with no input parameter.
 * @param template A fixed length template that specifies what elements are included as part of the name.
 * ex: "r:c_i^" is roleName + ":" + className with first letter lowercase + "_" + id
 * The template fields are in fixed positions, and may have the following values:
 * <p>(1) r include roleName, ^ don't include role name</p>
 * <p>(2) : ^</p>
 * <p>(3) c className lowercase, C classname lowercase, l local part, L local part</p>
 * <p>(4) _ ^</p>
 * <p>(5) i include id</p>
 * <p>(6) ^ </p>
 * <p>If an invalid template is input, then the returned name will be in the default format,
 * as defined by the GETNAME_DEFAULT constant. The default format is exactly the same as
 * what would be returned by calling getName() without specifying a template.</p>
 * @return The formatted name of this Xholon instance.
 */
public abstract String getName(String template);

/**
 * Hibernate requires a setter and getter for each property that it persists.
 * Name should never be explicitly set, but there needs to be a setName(String name) method
 * that Hibernate can call.
 * The implementation should do nothing.
 * @param name
 */
public abstract void setName(String name);

/**
 * Get the IXholonClass to which this Xholon instance is a member.
 * @return The IXholonClass.
 */
public abstract IXholonClass getXhc();

/**
 * Get the ID of the IXholonClass to which this Xholon instance is a member.
 * @return The ID of the IXholonClass.
 */
public abstract int getXhcId();

/**
 * Get the name of the IXholonClass to which this Xholon instance is a member.
 * @return The name of the IXholonClass.
 */
public abstract String getXhcName();

/**
 * Set the IXholonClass to which this Xholon instance belongs.
 * @param xhcNode The IXholonClass.
 */
public abstract void setXhc( IXholonClass xhcNode );
 
/**
 * Get the Xholon type, as known to this Xholon's IXholonClass.
 * Typical values include XhtypePureActiveObject, XhtypePurePassiveObject, XhtypePureContainer.
 * @return The Xholon type.
 */
public abstract int getXhType();

/**
 * Is this xholon or xholon class a Container?
 * @return true or false
 */
public abstract boolean isContainer();

/**
 * Is this xholon or xholon class an Active Object?
 * @return true or false
 */
public abstract boolean isActiveObject();

/**
 * Is this xholon or xholon class a Passive Object?
 * @return true or false
 */
public abstract boolean isPassiveObject();

/**
 * Does this instance of Xholon have the specified node as one of its
 * ancestors (parent, grandparent, etc.), within its containment tree.
 * @param tnName the searched-for ancestor
 * @return true or false
 */
public abstract boolean hasAncestor(String tnName);

/**
 * Recursively create this Xholon and its internal part structure,
 * as nodes within the overall composite structure tree.
 * @param containmentData Vector of containment data (composite structure) read in from a file.
 * @param myIx Current index into the containment data vector.
 * @param factory Where Xholon instances are made.
 * @param inherHier Inheritance hierarchy tree containing instances of IXholonClass.
 * @return Updated index into the containment data vector.
 * deprecated
 */
/*public abstract int createStructure(
		Vector containmentData,
		int myIx,
		ITreeNodeFactory factory,
		IInheritanceHierarchy inherHier);*/
 
/**
* Set ports, by creating an array of ports. A port is a reference to a Xholon somewhere else in the tree.
*/
public abstract void setPorts();

public abstract void bindPorts();

public abstract void consoleLog(Object obj);

/**
 * Set the contents of the default port array, typically the port attribute in XholonWithPorts.
 * @param port An array with 0 or more items. The items must be of type IXholon or null.
 */
public abstract void setPort(IXholon[] port);

/**
 * Get the contents of the default port array, typically the port attribute in XholonWithPorts.
 * @return An array with 0 or more items, or null if the default port array does not exist.
 * The items must be of type IXholon or null.
 */
public abstract IXholon[] getPort();

/**
 * Set the IXholon that this port references.
 * @param portNum The index of the port within this Xholon's port array.
 * @param portRef The IXholon instance that can be accessed through this port.
 */
public abstract void setPort(int portNum, IXholon portRef);

/**
 * Get the port that corresponds to this index (0 indexed).
 * @param portNum The index of the port within this Xholon's port array.
 * @return The port.
 */
public abstract IXholon getPort(int portNum);

/**
 * Get a list of all ports exiting from this xholon.
 * @return A list of the IXholon instances referenced by ports.
 * The list will contain duplicate entries if more than one port references the same IXholon instance.
 * The IXholons firstChild, nextSibling, and parentNode nodes are NOT included in the list.
 * If there are no ports, then an empty List is returned.
 */
public abstract List getAllPorts();

/**
 * Is this port bound to anything?
 * If a port is bound, then it will be possible for the local IXholon to send a message to,
 * or get/set a val on, a remote IXholon.
 * <p>If the port is intended to be a direct reference to another IXholon,
 * then isBound() will return true if the port actually references a concrete IXholon,
 * and will return false if the port value is null.</p>
 * <p>If the port is intended to be an IPort,
 * then isBound() will return true if there is an end-to-end connection to a remote IXholon,
 * and will return false if the port value is null or if it does not connect all the way through
 * to a remote IXholon.</p>
 * @return true or false
 */
public abstract boolean isBound(IXholon port);

/**
 * Get a named service.
 * @param serviceName The name of a service.
 * This is the name used in XholonService.xml, which is the IXholonClass name.
 * @return A service, or null.
 */
public abstract IXholon getService(String serviceName);

/**
 * Set name of the role played by this Xholon within a specific context.
 * @param roleName An optional name that identifies the role.
 */
public abstract void setRoleName(String roleName);

/**
 * Get name of the role played by this Xholon within a specific context.
 * @return A name that identifies the role, or null.
 */
public abstract String getRoleName();

/**
 * Set globally unique ID.
 * @param uid A globally unique ID, typically assigned by some external tool such as a UML modeling tool.
 */
public abstract void setUid(String uid);

/**
 * Get globally unique ID.
 * @return A globally unique ID, typically assigned by some external tool such as a UML modeling tool.
 */
public abstract String getUid();

/**
 * Set the Uniform Resource Identifier (URI) that identifies this Xholon node.
 * This is typically used to handle Resource Description Framework (RDF) resources.
 * @param uri A Uniform Resource Identifier (ex: "http://dbpedia.org/page/Mercury_(planet)").
 */
public abstract void setUri(String uri);

/**
 * Get the Uniform Resource Identifier (URI) that identifies this Xholon node.
 * This is typically used to handle Resource Description Framework (RDF) resources.
 * @return A Uniform Resource Identifier (ex: "http://dbpedia.org/page/Mercury_(planet)").
 */
public abstract String getUri();

/**
 * Set the contents of an annotation for this Xholon.
 * @param annotation Some text.
 */
public abstract void setAnnotation(String annotation);

/**
 * Get the contents of the annotation for this Xholon.
 * @return Some text.
 */
public abstract String getAnnotation();

/**
 * Does this Xholon node have an annotation?
 * @return true or false
 */
public abstract boolean hasAnnotation();

/**
 * Show the annotation in some appropriate way.
 */
public abstract void showAnnotation();

/**
 * Get the value of a "double" maintained by this xholon instance.
 * If a class that implements this interface does not maintain such a value, it should return 0.0
 * This method can be used in place of getVal_double()
 * @return A double value.
 */
public abstract double getVal();

/**
 * Set the value of a "double" maintained by this xholon instance.
 * @param val The new value.
 */
public abstract void setVal(double val);

/**
 * Increment an internal double value by a specified amount.
 * This is a convenience method.
 * @param incAmount Increment amount.
 */
public abstract void incVal(double incAmount);
/**
 * Increment an internal int value by a specified amount.
 * This is a convenience method.
 * @param incAmount Increment amount.
 */
public abstract void incVal(int incAmount);

/**
 * Decrement an internal double value by a specified amount.
 * This is a convenience method.
 * @param decAmount Decrement amount.
 */
public abstract void decVal(double decAmount);
/**
 * Decrement an internal int value by a specified amount.
 * This is a convenience method.
 * @param decAmount Decrement amount.
 */
public abstract void decVal(int decAmount);

/*
These setters and getters are intended to be used to set the single value maintained by a pure xholon instance.
The single value may be called "val" or anything else.
It can be of any type including a complex type.
*/

// boolean
/** Get the value of a "boolean" maintained by this xholon instance. */
public abstract boolean getVal_boolean();
/** Set the value of a "boolean" maintained by this xholon instance. */
public abstract void setVal(boolean val);
/** Set the value of a "boolean" maintained by this xholon instance. */
public abstract void setVal_boolean(boolean val);

// byte
/** Get the value of a "byte" maintained by this xholon instance. */
public abstract byte getVal_byte();
/** Set the value of a "byte" maintained by this xholon instance. */
public abstract void setVal(byte val);
/** Set the value of a "byte" maintained by this xholon instance. */
public abstract void setVal_byte(byte val);

// char
/** Get the value of a "char" maintained by this xholon instance. */
public abstract char getVal_char();
/** Set the value of a "char" maintained by this xholon instance. */
public abstract void setVal(char val);
/** Set the value of a "char" maintained by this xholon instance. */
public abstract void setVal_char(char val);

// double
/** Get the value of a "double" maintained by this xholon instance. */
public abstract double getVal_double();
/** Set the value of a "double" maintained by this xholon instance. */
public abstract void setVal_double(double val);

// float
/** Get the value of a "float" maintained by this xholon instance. */
public abstract float getVal_float();
/** Set the value of a "float" maintained by this xholon instance. */
public abstract void setVal(float val);
/** Set the value of a "float" maintained by this xholon instance. */
public abstract void setVal_float(float val);

// int
/** Get the value of a "int" maintained by this xholon instance. */
public abstract int getVal_int();
/** Set the value of a "int" maintained by this xholon instance. */
public abstract void setVal(int val);
/** Set the value of a "int" maintained by this xholon instance. */
public abstract void setVal_int(int val);

// long
/** Get the value of a "long" maintained by this xholon instance. */
public abstract long getVal_long();
/** Set the value of a "long" maintained by this xholon instance. */
public abstract void setVal(long val);
/** Set the value of a "long" maintained by this xholon instance. */
public abstract void setVal_long(long val);

// short
/** Get the value of a "short" maintained by this xholon instance. */
public abstract short getVal_short();
/** Set the value of a "short" maintained by this xholon instance. */
public abstract void setVal(short val);
/** Set the value of a "short" maintained by this xholon instance. */
public abstract void setVal_short(short val);

// String
/** Get the value of a "String" maintained by this xholon instance. */
public abstract String getVal_String();
/** Set the value of a "String" maintained by this xholon instance. */
public abstract void setVal(String val);
/** Set the value of a "String" maintained by this xholon instance. */
public abstract void setVal_String(String val);

// Object
/** Get the value of a "Object" maintained by this xholon instance. */
public abstract Object getVal_Object();
/** Set the value of a "Object" maintained by this xholon instance. */
public abstract void setVal(Object val);
/** Set the value of a "Object" maintained by this xholon instance. */
public abstract void setVal_Object(Object val);

/**
 * Get an instance of XholonClass, given its name.
 * @param cName Name of the XholonClass (ex: "HelloWorld").
 * @return An instance of XholonClass, or null.
 */
public abstract IXholonClass getClassNode( String cName );

/**
 * Get an instance of XholonClass, given its numeric id.
 * @param cName Numeric id of the XholonClass (ex: HelloWorldCE).
 * @return An instance of XholonClass, or null.
 */
public abstract IXholonClass getClassNode( int id );

/**
 * Set the value of a variable using reflection.
 * @param instructions A string containing the variable.
 * @param instructIx An index into the string.
 * @return Updated index into the string, past the end of the variable.
 * deprecated
 */
 //public abstract int setVariableValue(String instructions, int instructIx);
 
/**
 * Handle selection of a tree node by a user,
 * as when a person clicks on a JTree node in the default viewer.
 * Any action performed here should be minimal.
 * Ideally there should be no side effects.
 * WARNING: There may be thread issues.
 * @return An Object with information about that node, that could for example be displayed.
 */
public abstract Object handleNodeSelection();

/**
 * Handle selection of some other node, such as the selection of a child node.
 * @param otherNode The actual node selected, such as a child node.
 * @return An Object with information about that node, that could for example be displayed.
 */
public abstract Object handleNodeSelection(IXholon otherNode);

//                                   Asynchronous Messaging - Application messages

/**
 * Send a message to a receiving Xholon instance.
 * @param signal A distinguishing identifier for this message.
 * @param data Any data that needs to be sent (optional).
 * @param sender The sender of the message.
 */
public abstract void sendMessage(int signal, Object data, IXholon sender);

/**
 * Send a message to a receiving Xholon instance.
 * @param signal A distinguishing identifier for this message.
 * @param data Any data that needs to be sent (optional).
 * @param sender The sender of the message.
 * @param index The index of a replicated port.
 */
public abstract void sendMessage(int signal, Object data, IXholon sender, int index);

/**
 * Send a message to a receiving Xholon instance.
 * @param msg An already existing message.
 */
public abstract void sendMessage(IMessage msg);

/**
 * Process a received message taken from the message queue.
 * All sent messages are placed in queues before they are processed.
 * @param msg The message that was received.
 */
public abstract void processReceivedMessage(IMessage msg);

/**
 * Register a message forwardee.
 * If a node receives a message with a signal that it can't handle,
 * it will try to forward the message to a registered forwardee, if any.
 * @param forwardee A node that wants to receive otherwise unhandled messages.
 * @param signal An array of one or more signals that the forwardee is able to handle,
 * or null if it's able to handle any message. An empty array is ignored.
 */
public abstract void registerMessageForwardee(IXholon forwardee, int[] signal);

/**
 * Forward a message to a registered forwardee, if any.
 * @param msg The message that should be forwarded.
 */
public abstract void forwardMessage(IMessage msg);

/**
 * Process messages that are on the application message queue.
 * Typically, this will involve calling processReceivedMessage() for each message.
 */
public abstract void processMessageQ();

//                                  Asynchronous Messaging - System messages

/**
 * Send a system message to a receiving Xholon instance.
 * @param signal A distinguishing identifier for this message.
 * @param data Any data that needs to be sent (optional).
 * @param sender The sender of the message.
 */
public abstract void sendSystemMessage(int signal, Object data, IXholon sender);

/**
 * Send a system message to a receiving Xholon instance.
 * @param msg An already existing message.
 */
public abstract void sendSystemMessage(IMessage msg);

/**
 * Process messages that are on the system message queue.
 * Typically, this will involve calling processReceivedMessage() for each message.
 */
public abstract void processSystemMessageQ();

//                                   Synchronous Messaging
/**
 * Send a Synchronous message to a receiving Xholon instance.
 * @param signal A distinguishing identifier for this message.
 * @param data Any data that needs to be sent (optional).
 * @param sender The sender of the message.
 * @return A response message.
 */
public abstract IMessage sendSyncMessage(int signal, Object data, IXholon sender);
/**
 * Send a Synchronous message to a receiving Xholon instance.
 * @param signal A distinguishing identifier for this message.
 * @param data Any data that needs to be sent (optional).
 * @param sender The sender of the message.
 * @param index The index of a replicated port.
 * @return A response message.
 */
public abstract IMessage sendSyncMessage(int signal, Object data, IXholon sender, int index);
/**
 * Send a Synchronous message to a receiving Xholon instance.
 * @param msg An already existing message.
 * @return A response message.
 */
public abstract IMessage sendSyncMessage(IMessage msg);
/**
 * Process a received Synchronous message.
 * @param msg The message that was received.
 * @return A response message.
 */
public abstract IMessage processReceivedSyncMessage(IMessage msg);

/**
 * Forward a message to a registered forwardee, if any.
 * @param msg The message that should be forwarded.
 * @return A response message.
 */
public abstract IMessage forwardSyncMessage(IMessage msg);

/**
 * Terminate a UML2 state machine, and optionally terminate the xholon that owns the state machine.
 * The UML 2.1.1 specification states (section 15.3.8):
 * "Entering a terminate pseudostate implies that the execution of this state
 * machine by means of its context object is terminated. The state machine
 * does not exit any states nor does it perform any exit actions other than
 * those associated with the transition leading to the terminate pseudostate.
 * Entering a terminate pseudostate is equivalent to invoking a DestroyObjectAction."
 * The same action is performed if a state machine reaches its top level FinalState.
 */
public abstract void terminate();

/**
 * Search for instances of Xholon with ports that reference this instance. (expensive in time)
 * @return A List with instances of referencing nodes.
 */
public abstract List<IXholon> searchForReferencingNodes();

/**
 * Do the UML2 Activity identified by the activityId.
 * This is code called during a state Transition, or while entering, exiting, or within a State.
 * @param activityId An ID that uniquely identifies an activity to invoke.
 * @param msg The message that triggered this Transition.
 */
public abstract void performActivity(int activityId, IMessage msg);

/**
 * Do the UML2 Activity identified by the activityId.
 * This is code called during an Activity.
 * @param activityId An ID that uniquely identifies an activity to invoke.
 * @return true or false
 */
public abstract boolean performBooleanActivity(int activityId);

/**
 * Do the UML2 Activity identified by the activityId.
 * This is code called before a Transition from a State or Choice, to check the Guard conditions.
 * @param activityId An ID that uniquely identifies an activity to invoke.
 * @return true or false
 */
public abstract boolean performGuard(int activityId, IMessage msg);

// Turtle

/**
 * Do the activity identified by the activityId.
 * This is code called on certain Turtle functions that include a set of commands.
 * ex: ask turtles [set color yellow]
 * This method can also be used in other situations as a general call-back mechanism.
 * @param activityId An ID that uniquely identifies an activity to invoke.
 */
public abstract void performActivity(int activityId);

//       GP, and other evolutionary computation, ECJ, Behavior encoded as an Activity subtree

/**
 * Perform an activity including the entire activity subtree.
 * This is intended to be used in Behavior encoded as an Activity subtree,
 * and in GP and other evolutionary computation such as ECJ integration with Xholon.
 * This will typically be a side effect, instead of computing and returning a value.
 * @param activity IXholon representation of the activity.
 */
public abstract void performVoidActivity(IXholon activity);

/**
 * Perform an activity including the entire activity subtree.
 * This is intended to be used in Behavior encoded as an Activity subtree,
 * and in GP and other evolutionary computation such as ECJ integration with Xholon.
 * @param activity IXholon representation of the activity.
 * @return The result (a double) of performing this activity.
 */
public abstract double performDoubleActivity(IXholon activity);

/**
 * Perform an activity including the entire activity subtree.
 * This is intended to be used in Behavior encoded as an Activity subtree,
 * and in GP and other evolutionary computation such as ECJ integration with Xholon.
 * @param activity IXholon representation of the activity.
 * @return The result (a boolean) of performing this activity.
 */
public abstract boolean performBooleanActivity(IXholon activity);

/**
 * Get a list of actions that this xholon can do.
 * These actions can be presented to users in a GUI, or used for other purposes.
 * @return A list of actions, or null.
 */
public abstract String[] getActionList();

/**
 * Set the list of actions that this xholon can do.
 * These actions can subsequently be presented to users in a GUI, or used for other purposes.
 * @param actionList A list of actions, or null.
 */
public abstract void setActionList(String[] actionList);

/**
 * Do a specific action that this xholon knows how to do.
 * @param action The name of a specific action.
 */
public abstract void doAction(String action);

/**
 * Get a configured instance of Xml2Xholon.
 * @return
 */
public abstract IXml2Xholon getXml2Xholon();

/**
 * Get a new instance of Xholon2Xml.
 * @return
 */
public IXholon2Xml getXholon2Xml();

/**
 * Write self as XML.
 * @param xholon2xml 
 * @param xmlWriter The XML writer.
 */
public abstract void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter);

/**
 * Write a set of attributes formatted as XML.
 * @param xholon2xml 
 * @param xmlWriter The XML writer.
 */
public abstract void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter);

/**
 * Write one attribute formatted as XML.
 * @param xholon2xml 
 * @param xmlWriter The XML writer.
 * @param name Name of the attribute.
 * @param value Value of the attribute.
 * @param clazz Type of the attribute.
 */
public abstract void toXmlAttribute(IXholon2Xml xholon2xml, IXmlWriter xmlWriter, String name, String value, Class type);

/**
 * Write text formatted as XML.
 * @param xholon2xml 
 * @param xmlWriter The XML writer.
 */
public abstract void toXmlText(IXholon2Xml xholon2xml, IXmlWriter xmlWriter);

// the following are based on methods in the DOM Element class
// designed primarily to handle dynamic/untyped attributes
public abstract Object getAttributeXh(String name);
public abstract void setAttributeXh(String name, Object value);
public abstract void removeAttributeXh(String name);
public abstract boolean hasAttributeXh(String name);
public abstract IAttribute getAttributeNodeXh(String name);
public abstract void setAttributeNodeXh(IAttribute newAttr);
public abstract IAttribute removeAttributeNodeXh(IAttribute oldAttr);

}
