/* AQL Web Interface
 * Copyright (C) 2017 Ken Webb
 * BSD License
 */

package org.primordion.xholon.io.ngui;

//import org.primordion.xholon.app.IApplication;
//import org.primordion.xholon.base.IMessage;
//import org.primordion.xholon.base.IXholon;

/**
 * A Java interface that any NamedGui class must implement.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 27, 2017)
 */
public interface INamedGui {
  
  public static final String NGUI_AQL_WEB_INTERFACE_NAME = "AqlWebInterface";
  public static final String NGUI_AQL_WEB_INTERFACE_IDSTR = "Categorical Data IDE";
  public static final String NGUI_AQL_WEB_INTERFACE_TOOLTIP = "Categorical Data IDE";
  
  //public abstract String getRoleName();

  //public abstract void setRoleName(String roleName);

  //public abstract IXholon getContext();

  //public abstract void setContext(IXholon context);

  //public abstract IXholon getCommandPane();

  //public abstract void setCommandPane(IXholon commandPane);
  
  public abstract void setResult(String result, boolean replace);

  public abstract void log(Object obj);
  
  public abstract Object getWidget();
  
  /**
   * Set the name and title of the tab header.
   */
  public abstract void setTabHeader();
  
  /*
   * @see org.primordion.xholon.base.Xholon#getApp()
   */
  //public abstract IApplication getApp();

  /*
   * @see org.primordion.xholon.base.Xholon#setApp(org.primordion.xholon.app.IApplication)
   */
  //public abstract void setApp(IApplication app);

  public abstract void postConfigure();

  /*
   * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.Message)
   */
  //public abstract void processReceivedMessage(IMessage msg);
  
}
