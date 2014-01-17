package org.client;

/**
 * This class makes available various GWT and browser "environment variables",
 * in a way that insulates core Xholon classes from any knowledge of GWT.
 * These are obtained by calling static methods on:
 * com.google.gwt.core.client.GWT
 * com.google.gwt.user.client.Window.Navigator
 */
public class GwtEnvironment {
  
  // GWT
  
  public static String gwtHostPageBaseURL = null;
  
  public static String gwtModuleBaseForStaticFiles = null;
  
  public static String gwtModuleBaseURL = null;
  
  public static String gwtModuleName = null;
  
  public static String gwtVersion = null;
  
  // Navigator
  
  public static String navAppCodeName = null;
  
  public static String navAppName = null;
  
  public static String navAppVersion = null;
  
  public static String navPlatform = null;
  
  public static String navUserAgent = null;
  
}
