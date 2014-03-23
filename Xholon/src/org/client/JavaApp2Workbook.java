/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.client;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

import java.util.Date;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * Convert an existing Xholon Java-based app into a Xholon Workbook,
 * and optionally load the Workbook into the HTML5 workbook editor.
 * It saves the workbook to the browser's localStorage.
 * It attempts to convert everything except the Java (.java) code.
 * This class is invoked by org.client.Xholon when "gui=edit".
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on March 18, 2014)
 */
public class JavaApp2Workbook extends Xholon {
  
  /**
   * For each type of thing that might be in a Xholon app,
   * there's a String to capture that thing's content,
   * and there's a boolean to record whether that thing's existence has been resolved.
   * Once all resolvable things have been resolved,
   * then the workbook can be saved and otherwise processed.
   */
  
  protected String _xhnStr = null;
  protected boolean _xhnResolved = false;
  
  protected String ihStr = null;
  protected boolean ihResolved = false;
  
  protected String cdStr = null;
  protected boolean cdResolved = false;
  
  protected String cshStr = null;
  protected boolean cshResolved = false;
  
  protected String infoStr = null;
  protected boolean infoResolved = false;
  
  protected String svgStr = null;
  protected boolean svgResolved = false;
  
  protected int numResolvable = 6;
  protected int numResolved = 0;
  
  protected IApplication app = null;
  
  /**
   * "save" or "edit"
   */
  protected String mode = "save";
  
  protected String appName = null;
  
  /**
   * Save the app to the browser's localStorage.
   * @param configFileName The name and path of a _xhn.xml file, or null.
   * @param app 
   */
  public void save(String configFileName, IApplication app) {
    //this.println("JavaApp2Workbook.save() starting ...");
    this.app = app;
    // have app parse the xhn config file
    try {
      app.readParameters(configFileName);
    } catch (XholonConfigurationException e) {
      //logger.error(e.getMessage(), e.getCause());
    }
    resolve(configFileName, "_xhn");
    resolve(app.getInheritanceHierarchyFile(), "ih");
    resolve(app.getClassDetailsFile(), "cd");
    resolve(app.getCompositeStructureHierarchyFile(), "csh");
    resolve(app.getInformationFile(), "info");
    resolve(getImageFile(configFileName), "svg");
  }
  
  /**
   * Get the name of the SVG image file.
   * @param configFileName The name and path of a _xhn.xml file, or null.
   * @return The name, or null.
   */
  protected String getImageFile(String configFileName) {
    if (configFileName == null) {return null;}
    String imageFile = app.getImageFile();
    if ("default.svg".equals(imageFile)) {
      imageFile = configFileName.substring(0, configFileName.lastIndexOf("/")) + "/" + imageFile;
    }
    return imageFile;
  }
  
  /**
   * Optionally save the app to the browser's localStorage, and
   * invoke the workbook editor which will read it from localStorage.
   * @param configFileName The name and path of a _xhn.xml file, or null.
   * @param app 
   */
  public void edit(String configFileName, IApplication app) {
    //this.println("JavaApp2Workbook.edit() starting ...");
    mode = "edit";
    if (editItemExists()) {
      openEditWindow();
    }
    else {
      save(configFileName, app);
    }
  }
  
  /**
   * Open a window in the browser for the XholonWorkbook editor.
   */
  protected void openEditWindow() {
    //this.println("JavaApp2Workbook.openEditWindow() starting ...");
    StringBuilder wbUrl = new StringBuilder()
    .append(GwtEnvironment.gwtHostPageBaseURL)
    .append("wb/editwb.html?app=")
    .append(getAppName())
    .append("&src=lstr");
    //this.println(wbUrl.toString());
    // replace the current window with the XholonWorkbook editor
    Window.open(wbUrl.toString(), "_self", ""); // starts with "http" or "https"
  }
  
  /**
   * Resolve one type of content that a Xholon app can have.
   * @param fileName A local file name, or remote uri (http), or null.
   * @param contentType One of "_xhn" "ih" "cd" "csh" etc.
   */
  protected void resolve(String fileName, String contentType) {
    //this.println("trying to resolve " + fileName + " " + contentType);
    if (fileName == null) {
      // app is probably using a ClientBundle
      String txt = app.rcConfig(contentType,
        (ClientBundleWithLookup)app.findGwtClientBundle());
      resolveDone(txt, contentType);
      return;
    }
    final String _contentType = contentType;
    final IXholon _this = this;
    try {
      new RequestBuilder(RequestBuilder.GET, fileName).sendRequest("", new RequestCallback() {
        @Override
        public void onResponseReceived(Request req, Response resp) {
          if (resp.getStatusCode() == resp.SC_OK) {
            resolveDone(resp.getText(), _contentType);
          }
          else {
            _this.println("status code:" + resp.getStatusCode());
            _this.println("status text:" + resp.getStatusText());
            _this.println("text:\n" + resp.getText());
            resolveDone(null, _contentType);
          }
        }

        @Override
        public void onError(Request req, Throwable e) {
          _this.println("onError:" + e.getMessage());
          resolveDone(null, _contentType);
        }
      });
    } catch(RequestException e) {
      _this.println("RequestException:" + e.getMessage());
      resolveDone(null, _contentType);
    }
  }
  
  /**
   * Finish resolving one type of content that a Xholon app can have.
   * @param txt The text content, or null.
   * @param contentType One of "_xhn" "ih" "cd" "csh" etc.
   */
  protected void resolveDone(String txt, String contentType) {
    //this.println("resolveDone " + contentType + "\n" + txt);
    if ("_xhn".equals(contentType)) { // params
      _xhnStr = removeXmlDeclaration(txt);
      _xhnResolved = true;
    }
    else if ("ih".equals(contentType)) {
      // convert <XholonClass> to <_-.XholonClass>, and </XholonClass> to </_-.XholonClass>
      ihStr = removeXmlDeclaration(txt)
      .replaceAll("XholonClass", "_-.XholonClass");
      ihResolved = true;
    }
    else if ("cd".equals(contentType)) {
      cdStr = removeXmlDeclaration(txt);
      cdResolved = true;
    }
    else if ("csh".equals(contentType)) {
      cshStr = removeXmlDeclaration(txt);
      // TODO outer XML node name must end with "System"
      cshResolved = true;
    }
    else if ("info".equals(contentType)) {
      infoStr = removeXmlDeclaration(txt);
      infoResolved = true;
    }
    else if ("svg".equals(contentType)) {
      svgStr = removeXmlDeclaration(txt);
      svgResolved = true;
    }
    numResolved++;
    if (numResolved >= numResolvable) {
      // save the workbook to localStorage
      saveWbToLocalStorage();
      // optionally open edit window
      if ("edit".equals(mode)) {
        openEditWindow();
      }
    }
  }
  
  /**
   * Remove initial XML declaration lines that start with "<?xml" and end with "?>",
   * and trim start and end white space.
   * @param txt An XML string that may start with an XML declaration.
   * @return An XML string with the XML declaration removed.
   */
  protected String removeXmlDeclaration(String txt) {
    if (txt == null) {return null;}
    if (txt.startsWith("<?xml")) {
      int pos = txt.indexOf("?>");
      if ((pos != -1) && (pos < 100)) {
        return txt.substring(pos+2).trim();
      }
      else {
        // badly formatted XML
        return "";
      }
    }
    else {
      return txt.trim();
    }
  }
  
  /**
   * Save XholonWorkbook to localStorage.
   */
  protected void saveWbToLocalStorage() {
    //this.println("saveWbToLocalStorage");
    String wbContent = assembleWb();
    Storage storage = Storage.getLocalStorageIfSupported();
    if (storage != null) {
      String key = getAppName();
      storage.setItem(key, wbContent);
    }
  }
  
  /**
   * Does the edit() item already exist ?
   * @return true or false
   */
  protected boolean editItemExists() {
    //this.println("editItemExists");
    Storage storage = Storage.getLocalStorageIfSupported();
    if (storage != null) {
      String item = storage.getItem(getAppName());
      if (item == null) {
        return false;
      }
      else {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Get full path+name of the Application class,
   * and return the short portion of that.
   * ex: "org.primordion.user.app.helloworldjnlp.AppHelloWorld"
   *     returns "HelloWorld"
   */
  protected String getAppName() {
    if (appName == null) {
      appName = Location.getParameter("app");
      if (appName != null) {
        // convert "+" back to " ", etc.
        appName = URL.decodeQueryString(appName);
      }
    }
    return appName;
  }
  
  /**
   * Assemble a XholonWorkbook from the various parts.
   */
  protected String assembleWb() {
    StringBuilder sb = new StringBuilder()
    .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
    .append("<!--Xholon Workbook http://www.primordion.com/Xholon/gwt/ MIT License, Copyright (C) Ken Webb, ")
    .append(new Date().toString())
    .append("-->\n")
    .append("<XholonWorkbook>\n\n");
    
    // info, Notes
    sb
    .append("<Notes><![CDATA[\n")
    .append("Xholon\n")
    .append("------\n")
    .append("Title: ")
    .append(getAppName())
    .append("\n")
    .append("Description: \n")
    .append("Url: http://www.primordion.com/Xholon/gwt/\n")
    .append("InternalName: ")
    .append(getAppName())
    .append("\n")
    .append("Keywords: \n\n")
    .append("My Notes\n")
    .append("--------\n\n");
    if (infoStr != null) {
      sb.append(infoStr);
    }
    sb.append("]]></Notes>\n\n");
    
    // _xhn params
    sb.append(_xhnStr).append("\n\n");
    
    // ih
    sb.append(ihStr).append("\n\n");
    
    // cd
    sb.append(cdStr).append("\n\n");
    
    // csh
    sb.append(cshStr).append("\n\n");
    
    // svg
    sb.append("<SvgClient><Attribute_String roleName=\"svgUri\"><![CDATA[data:image/svg+xml,\n");
    if (svgStr != null) {
      sb.append(svgStr);
    }
    sb.append("]]></Attribute_String><Attribute_String roleName=\"setup\">${MODELNAME_DEFAULT},${SVGURI_DEFAULT}</Attribute_String></SvgClient>\n\n");
    
    sb.append("</XholonWorkbook>");
    
    return sb.toString();
  }
  
}
