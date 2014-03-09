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

package org.primordion.xholon.script;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Capture interactions between nodes, which can then be used to create a sequence diagram.
 * This is a sample Xholon script, written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * It can therefore be pasted into a running Xholon app simply as:
 * <p><pre>&lt;InteractionsViewer/></pre></p>
 * <p><pre>&lt;InteractionsViewer useInteractions="true"/></pre></p>
 * <p><pre>&lt;InteractionsViewer useInteractions="false"/></pre></p>
 * <p><pre>&lt;InteractionsViewer interactionParams="32,true,localhost,60001"/></pre></p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on March 4, 2014)
 */
public class InteractionsViewer extends XholonScript {
  
  private static final boolean DEFAULT_USE_INTERACTIONS = true; // true or false
  private static final String DEFAULT_MODE = "ifNotAlready";
  
  /**
   * Default interactionParams.
   */
  private static final String DEFAULT_INTERACTIONPARAMS = "32,true,localhost,60001";
  
  /** Whether or not to capture interaction data in the app */
  private boolean useInteractions = DEFAULT_USE_INTERACTIONS;
  
  /** "OutputFormat,ShowStates,SocketHost,SocketPort" */
  private String interactionParams = null;
  
  /** ifNotAlready (0-->1), replaceExisting (1-->1), new (n-->n+1), null */
  private String mode = DEFAULT_MODE;
  
  /*
   * @see org.primordion.xholon.base.Xholon#postConfigure()
   */
  public void postConfigure()
  {
    IApplication app = this.getApp();
    if (app != null) {
      boolean shouldCreate = false;
      if ("new".equals(mode)) {
        shouldCreate = true;
        this.makeInteractionParams(app);
      }
      else if (!app.getUseInteractions() && "ifNotAlready".equals(mode)) {
        shouldCreate = true;
        this.makeInteractionParams(app);
      }
      app.setUseInteractions(useInteractions);
      if (interactionParams != null) {
        app.setInteractionParams(interactionParams);
      }
      if (shouldCreate) {
        app.createInteractions();
      }
    }
    this.removeChild();
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
   */
  public int setAttributeVal(String attrName, String attrVal) {
    if ("useInteractions".equals(attrName)) {
      this.useInteractions = Boolean.parseBoolean(attrVal);
    }
    else if ("interactionParams".equals(attrName)) {
      this.interactionParams = attrVal;
    }
    else if ("mode".equals(attrName)) {
      this.mode = attrVal;
    }
    return 0;
  }
  
  /**
   * Make a set of interaction params, if they don't already exist.
   * @param app
   */
  protected void makeInteractionParams(IApplication app) {
    if (interactionParams == null) {
      interactionParams = DEFAULT_INTERACTIONPARAMS;
    }
  }
  
  /**
   * Write a Interactions script to an XML writer.
   * This has to be a static method,
   * because Interactions nodes typically remove themselves from the tree,
   * and are therefore not available later to write themselves out.
   * @param xmlWriter
   * @param app
   */
  public static void writeXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter, IApplication app) {
    xmlWriter.writeStartElement("InteractionsViewer");
    xmlWriter.writeAttribute("mode", DEFAULT_MODE);
    xmlWriter.writeAttribute("useInteractions", Boolean.toString(app.getUseInteractions()));
    xmlWriter.writeAttribute("interactionParams", app.getInteractionParams());
    xholon2xml.writeSpecial(app);
    xmlWriter.writeEndElement("InteractionsViewer");
  }
  
}
