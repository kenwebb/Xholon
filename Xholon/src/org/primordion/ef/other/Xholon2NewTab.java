/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.ef.other;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
//import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
//import com.google.gwt.user.client.ui.Widget;

import java.util.Date;
//import java.util.List;

import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.PortInformation;
import org.primordion.ef.AbstractXholon2ExternalFormat;
//import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Create a new tab.
 * The tab is typically used to write notes,
 * but could be used to write any type of text.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on December 24, 2013)
 */
@SuppressWarnings("serial")
public class Xholon2NewTab extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  interface NewTabUiBinder extends UiBinder<DialogBox, Xholon2NewTab> {}
  private static NewTabUiBinder uiBinder = GWT.create(NewTabUiBinder.class);
  
  // DialogBox
  @UiField
  DialogBox dialog;
  
  @UiField
  TextBox tabName;
  
  @UiField
  Button cancelButton;
  
  @UiField
  Button newButton;
  
  private String outFileName;
  private String outPath = "./ef/notes/";
  private String outFileExt = ".txt";
  private String modelName;
  private IXholon root;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  public Xholon2NewTab() {}
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    //consoleLog("Xholon2NewTab initialize() start");
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    /*if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + outFileExt;
    }
    else {
      this.outFileName = outFileName;
    }*/
    this.modelName = modelName;
    this.root = root;
    //consoleLog("Xholon2NewTab initialize() end");
    return true;
  }

  @Override
  public void writeAll() {
    //consoleLog("Xholon2NewTab writeAll() start");
    final Xholon2NewTab xh2nt = this;
    //consoleLog("Xholon2NewTab writeAll() 1");
    dialog = uiBinder.createAndBindUi(this);
    //consoleLog(dialog);
    cancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        //consoleLog("cancelButton clicked");
        xh2nt.hide();
      }
    });
    //consoleLog("Xholon2NewTab writeAll() 2");
    newButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        //consoleLog("newButton clicked");
        xh2nt.makeTab();
        xh2nt.hide();
      }
    });
    //consoleLog("Xholon2NewTab writeAll() 3");
    show();
    //consoleLog("Xholon2NewTab writeAll() end");
  }
  
  /**
   * Make the new tab.
   */
  public void makeTab() {
    //consoleLog("making new tab");
    outFileName = outPath + tabName.getText() + "_" + root.getXhcName() + "_" + root.getId()
        + "_" + timeStamp + outFileExt;
    writeToTarget("", outFileName, tabName.getText(), root);
  }
  
  public void hide() {
    //consoleLog("hide()");
    dialog.hide();
  }

  public void show() {
    //consoleLog("show()");
    dialog.center();
  }
  
}
