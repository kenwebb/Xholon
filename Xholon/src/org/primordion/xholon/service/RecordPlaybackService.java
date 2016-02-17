/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.xholon.service;

import com.google.gwt.storage.client.Storage;

//import java.io.IOException;
//import java.io.Writer;
import java.util.Date;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
//import org.primordion.xholon.util.MiscIo;
import org.primordion.xholon.service.xholonhelper.ClipboardFactory;
import org.primordion.xholon.service.xholonhelper.IClipboard;

/**
 * Record user actions (cut paste), and play them back later.
 * Recorded actions are appended to a "permanent" log/history file.
 * <p>TODO option to write it out in HTML format so each captured text is in its own textarea.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on November 5, 2011)
 */
public class RecordPlaybackService extends AbstractXholonService {
  private static final long serialVersionUID = 2132381384108700426L;

  /**
   * A request to record something.
   */
  public static final int SIG_RECORD_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101;
  
  /**
   * A response to a request to record something.
   */
  public static final int SIG_RECORD_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201;
  
  /**
   * String that should be prefixed to the key of every record saved to storage.
   * "rps" indicates that RecordPlaybackService is doing the storage
   */
  protected static final String KEY_PREFIX = "rps";
  
  //protected static final int STATE_INACTIVE = 0;
  //protected static final int STATE_RECORDING = 1;
  //protected static final int STATE_PLAYINGBACK = 2;
  
  //protected int state = STATE_INACTIVE;
  
  // Java SE
  //protected String recordFileName = "./log/record.xml";
  //protected transient Writer recordWriter = null;
  
  /**
   * A Xholon Workbook starts with the following String.
   */
  protected static final String XHOLONWORKBOOK_START_STR = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!--Xholon Workbook";
  
  /**
   * A Xholon Workbook ends with the following String.
   */
  protected static final String XHOLONWORKBOOK_END_STR = "</XholonWorkbook>";
  
  // GWT
  protected Storage storage = null;
  
  protected boolean shouldRecord = false;
  protected boolean shouldPlayback = false;
  
  @Override
  public IXholon getService(String serviceName)
  {
    if (shouldRecord && serviceName.equals(getXhcName())) {
      return this;
    }
    return null;
  }
  
  @Override
  public void processReceivedMessage(IMessage msg)
  {
    switch (msg.getSignal()) {
    default:
      super.processReceivedMessage(msg);
    }
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg)
  {
    switch (msg.getSignal()) {
    case SIG_RECORD_REQ:
      if (shouldRecord) {
        if (this.getApp().isUseGwt()) {
          recordGwt((String)msg.getData());
        }
        else {
          recordJavaSe((String)msg.getData());
        }
      }
      return new Message(SIG_RECORD_RESP, null, this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    }
    
  }
  
  /**
   * Record to a Java Standard Edition (SE) file.
   * @param data The data to be stored.
   */
  protected void recordJavaSe(String data) {
    /* GWT
    if (recordWriter == null) {
      recordWriter = MiscIo.appendOutputFile(recordFileName);
      //TODO write legal XML at start of a new file ?
    }
    if (recordWriter != null) {
      try {
        recordWriter.write("\n");
        recordWriter.write(data);
        recordWriter.flush();
        //System.out.println("-->" + str);
      } catch (IOException e) {
        logger.error("", e);
      }
    }*/
  }
  
  /**
   * Record to GWT HTML5 local storage.
   * @param data The data to be stored.
   */
  protected void recordGwt(String data) {
    if (storage == null) {
      storage = Storage.getLocalStorageIfSupported();
    }
    if (storage != null) {
      String key = KEY_PREFIX + new Date().getTime();
      storage.setItem(key, data);
    }
  }
  
  String toggleRecord = "Toggle Record";
  String showStorageNames = "Show Storage Names";
  String showStorageValues = "Show Storage Values";
  String showStatus = "Show Status";
  String clear = "Remove all items from Storage";
  String restore = "Restore items to Storage";
  String actionList[] = {toggleRecord, showStorageNames, showStorageValues, showStatus, clear, restore};
  
  @Override
  public String[] getActionList() {
    return actionList;
  }
  
  @Override
  public void doAction(String action) {
    if (storage == null) {
      storage = Storage.getLocalStorageIfSupported();
    }
    if (toggleRecord.equals(action)) {
      setShouldRecord(!isShouldRecord());
    }
    else if (showStorageNames.equals(action)) {
      if (storage != null) {
        for (int i = 0; i < storage.getLength(); i++) {
          println(storage.key(i));
        }
      }
    }
    else if (showStorageValues.equals(action)) {
      if (storage != null) {
        for (int i = 0; i < storage.getLength(); i++) {
          println("\n" + storage.getItem(storage.key(i)));
        }
      }
    }
    else if (showStatus.equals(action)) {
      println("The app " + (isShouldRecord() ? "is " : "is not ")
        + "currently recording.");
      println("Local storage " + (Storage.isLocalStorageSupported() ? "is " : "is not ")
        + "supported on this computer.");
      if (storage != null) {
        println("There are currently " + storage.getLength() + " items in storage.");
      }
    
    }
    else if (clear.equals(action)) {
      if (storage != null) {
        storage.clear();
      }
    }
    else if (restore.equals(action)) {
      // restore one or more workbooks from Xholon clipboard
      if (storage != null) {
        String str = readStringFromClipboard();
        if (str == null) {return;}
        str = str.trim();
        restoreWorkbooks(str);
      }
    }
  }

  /**
   * @return the shouldRecord
   */
  public boolean isShouldRecord() {
    return shouldRecord;
  }

  /**
   * @param shouldRecord the shouldRecord to set
   */
  public void setShouldRecord(boolean shouldRecord) {
    this.shouldRecord = shouldRecord;
  }

  /**
   * @return the shouldPlayback
   */
  public boolean isShouldPlayback() {
    return shouldPlayback;
  }

  /**
   * @param shouldPlayback the shouldPlayback to set
   */
  public void setShouldPlayback(boolean shouldPlayback) {
    this.shouldPlayback = shouldPlayback;
  }
  
  /**
   * Restore one or more workbooks to localstorage.
   * @param wbs one or more workbooks; the String is trimmed at both ends
   */
  protected void restoreWorkbooks(String wbs) {
    int start = wbs.indexOf(XHOLONWORKBOOK_START_STR, 0);
    int endLen = XHOLONWORKBOOK_END_STR.length();
    while (start != -1) {
      int end = wbs.indexOf(XHOLONWORKBOOK_END_STR, start);
      if (end == -1) {break;}
      end = end + endLen;
      String wb = wbs.substring(start, end).trim();
      restoreWorkbook(wb);
      start = wbs.indexOf(XHOLONWORKBOOK_START_STR, end);
    }
  }
  
  /**
   * Restore one workbook to localstorage.
   * @param wb one workbook; the String is trimmed at both ends
   */
  protected void restoreWorkbook(String wb) {
    // find the wb "Title: "
    int titleIndex = wb.indexOf("Title: ");
    if (titleIndex != -1) {
      int beginIndex = titleIndex + 7;
      String ls = "\n"; //getPropertyLineSeparator();
      int endIndex = wb.indexOf(ls, beginIndex);
      if (endIndex != -1) {
        String title = wb.substring(beginIndex, endIndex);
        // title is the localstorage key, wb is the data
        if (storage.getItem(title) == null) {
          storage.setItem(title, wb);
          consoleLog(title + "  restored to localstorage");
        }
        else {
          consoleLog(title + "  already exists");
        }
      }
    }
  }
  
  /**
   * Read a String from the clipboard, such as one or more XholonWorkbooks.
   * @return A String, or null.
   */
  protected String readStringFromClipboard()
  {
    String str = null;
    IClipboard xholonClipboard = ClipboardFactory.instance();
    if (xholonClipboard != null) {
      str = xholonClipboard.readStringFromClipboard();
    }
    return str;
  }
  
}
