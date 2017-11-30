/* Operadics DSL Web Interface
 * Copyright (C) 2017 Ken Webb
 * BSD License
 */

package org.primordion.xholon.io.ngui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * Provide a Operadics DSL Web Interface.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on November 28, 2017)
 */
public class OpDslWebInterface implements INamedGui {
  
  interface OpDslWebUiBinder extends UiBinder<Widget, OpDslWebInterface> {}
  private static OpDslWebUiBinder uiBinder = GWT.create(OpDslWebUiBinder.class);
  
  // Export menu (optional)
  @UiField MenuItem xhtex;
  @UiField MenuItem xhgv;
  @UiField MenuItem xhchap;
  @UiField MenuItem xhxml;
  @UiField MenuItem xhyaml;
  
  // Help menu
  @UiField MenuItem helpabout;
  
  // buttons
  @UiField Button brun;
  @UiField Button bnewopdsl;
  
  // label and listBox
  @UiField ListBox lbexamples;
  
  @UiField HTML commandPane;
  @UiField TextAreaElement commandPaneTAE;
  
  /**
   * The HTML widget that implements the Web Interface.
   */
  protected Widget widget = null;
  
  /**
   * Root node in the optional Xholon-generated tree.
   */
  protected Object cattSystem = null;
  
  /**
   * constructor
   */
  public OpDslWebInterface() {}
  
  @Override
  public Object getWidget() {
    return widget;
  }

  @Override
  public void postConfigure() {
    readGuiFromXml();
    
    // Export menu
    xhtex.setEnabled(false);
    xhgv.setEnabled(false);
    xhchap.setEnabled(false);
    xhxml.setEnabled(false);
    xhyaml.setEnabled(false);
       
    xhgv.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhgv(cattSystem);
      }
    });
    
    xhchap.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhchap(cattSystem);
      }
    });
    
    xhxml.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhxml(cattSystem);
      }
    });
    
    xhyaml.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhyaml(cattSystem);
      }
    });
    
    xhtex.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhtex(cattSystem);
      }
    });
    
    // Help menu
    
    helpabout.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        help();
      }
    });
    
    // buttons
    
    brun.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        run();
      }
    });
    
    bnewopdsl.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        newOpDsl();
      }
    });
    
    // ListBox
    lbexamples.addChangeHandler(new ChangeHandler() {
      @Override
      public void onChange(ChangeEvent event) {
        String item = lbexamples.getSelectedItemText();
        switch (item) {
        case "Example 1": loadExample1(); break;
        default:
          String str = getExampleText(item);
          setCommand(str, true);
          break;
        }
      }
    });
    
  } // end postConfigure()
  
  protected void run() {
    xhstart();
  }
  
  //protected void abort() {
  //  Window.alert("Abort not yet implemented.");
  //}
  
  /**
   * Create a New Operad DSL file.
   * For now, just clear the contents of the textarea.
   */
  protected void newOpDsl() {
    setCommand("", true);
  }
    
  protected native Object findXhCattSystem() /*-{
    return $wnd.xh.root().last().last().first();
  }-*/;
  
  /**
   * Start Xholon.
   */
  protected boolean xhstart() {
    if (xhstartNative()) {
      cattSystem = findXhCattSystem();
      xhtex.setEnabled(true);
      xhgv.setEnabled(true);
      xhchap.setEnabled(true);
      xhxml.setEnabled(true);
      xhyaml.setEnabled(true);
      //xhstart.setEnabled(false);
      brun.setEnabled(false);
      return true;
    }
    else {
      Window.alert("Unable to start Xholon.");
      return false;
    }
  }
  
  /**
   * Start Xholon native.
   */
  protected native boolean xhstartNative() /*-{
    if (!$wnd.xh) {return false;}
    var subTree = $wnd.xh.stored.csh;
    if (subTree) {
      $wnd.xh.stored.csh = null; // prevent the subtree from being used again
      $wnd.xh.root().append(subTree);
    }
    $wnd.xh.state(3);
    // change the number of rows in each textarea to match the height of the resizePanel
    var taArr = $doc.querySelectorAll("#xhtabs textarea");
    for (var i = 0; i < taArr.length; i++) {
      var ta = taArr[i];
      ta.rows = "64";
    }
    return true;
  }-*/;
  
  protected native void xhgv(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    //var schemaNode = cattSystem.first();
    $wnd.xh.xport("Graphviz", cattSystem, '{"gvFileExt":".gv","gvGraph":"digraph","layout":"dot","edgeOp":"->","gvCluster":"","shouldShowStateMachineEntities":false,"filter":"--Behavior,Script","nameTemplateNodeId":"^^^^i^","nameTemplateNodeLabel":"R^^^^^","shouldQuoteLabels":true,"shouldShowLinks":true,"shouldShowLinkLabels":true,"shouldSpecifyLayout":false,"maxLabelLen":-1,"shouldColor":true,"defaultColor":"#f0f8ff","shouldSpecifyShape":true,"shape":"ellipse","shouldSpecifySize":false,"size":"6","shouldSpecifyFontname":false,"shouldSpecifyArrowhead":true,"arrowhead":"vee","shouldSpecifyStylesheet":true,"stylesheet":"Xholon.css","shouldSpecifyRankdir":false,"rankdir":"LR","shouldDisplayGraph":true,"outputFormat":"svg"}');
  }-*/;
  
  protected native void xhchap(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    //var schemaNode = cattSystem.first();
    $wnd.xh.xport("_other,ChapNetwork", cattSystem, '{"showNetwork":true,"showTree":false,"maxTreeLevels":1,"width":"800px","height":"800px","nameTemplate":"R^^^^^","maxChars":-1,"linksLength":100,"showPortName":true,"nodesStyle":"dot","stabilize":"false","jsLibName":"network-min"}');
  }-*/;
  
  protected native void xhxml(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    $wnd.xh.xport("Xml", cattSystem, '{"writeStartDocument":false,"writePorts":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}');
  }-*/;
  
  protected native void xhyaml(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    $wnd.xh.xport("Yaml", cattSystem,'{"writeStartDocument":true,"shouldWriteVal":false,"shouldWriteAllPorts":false,"shouldWriteLinks":true}');
  }-*/;
  
  protected native void xhtex(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    $wnd.xh.xport("_tex,TexWirDiaO", cattSystem, '{"scale":1,"nameTemplate":"r:c_i","bbNameTemplate":"R^^^^^","maxChars":-1,"showPortName":false,"nodesStyle":"rectangle","linksStyle":"default","siblingsPosition":"right=5 of","diffXhtypePosition":"below=10 of","bipartite":false,"intermedDashing":"loosely dotted","includePreamble":true,"documentclass":"[10pt,oneside,article,landscape]{memoir}","preambleFileName":"texWirDiaOpreamble.tex","includeBeginEndDoc":true,"title":"default","author":"default","includeBeginEndEqu":false}');
  }-*/;
  
  /**
   * Provide the user with some brief help information.
   */
  protected void help() {
    OpDslHelpDialogBox helpdb = new OpDslHelpDialogBox();
    if (helpdb != null) {
      helpdb.startHelpDialog();
    }
  }
  
  protected native String getExampleText(String item) /*-{
    var str = "Can't locate " + item + ".";
    var opdslex = $wnd.OPDSLEXAMPLES;
    if (opdslex) {
      str = opdslex.getExample(item);
    }
    return str;
  }-*/;
  
  protected void loadExample1() {
    String str = new StringBuilder()
    .append("# Example Operad 1\n# \npack P1 = {a1,b1,f1}\npack P2 = {b2,c2,d2,e2}\npack P3 = {e3,f3}\npack Q  = {ddd, ggg}\npack R  = {aR, fR, gR}\n# \nbindings # dummy bindings\nmorphism comp: (P1, P2, P3, Q) -> R := {a1=aR, b1=b2, c2, d2=ddd, e2=e3, f1=f3=fR, ggg=gR}")
    .toString();
    setCommand(str, true);
  }
  
  @Override
  public void log(Object obj) {
    if (obj instanceof String) {
      setResult((String)obj, false);
    }
  }
  
  /**
   * For now, the command and the result are in the same window,
   * so just call the existing setResult() method.
   */
  public void setCommand(String result, boolean replace) {
    setResult(result, replace);
  }
  
  /**
   * Set the result of the query into a text area,
   * and make it visible to the user.
   * For now, the resultPane and commandPane are the same.
   * @param result
   * @param replace whether to replace current text (true), or append to end of current text (false)
   */
  public void setResult(String result, boolean replace) {
    // get the element
    Element ele = commandPane.getElement().getFirstChildElement();
    
    // textarea
    TextAreaElement ta = TextAreaElement.as(ele);
    if (replace) {
      ta.setValue(result);
    }
    else {
      ta.setValue(ta.getValue() + result);
    }
  }
  
  @Override
  public void setTabHeader() {}
  
  /**
   * Read the GUI from a UIBinder XML file.
   */
  protected void readGuiFromXml() {
    widget = uiBinder.createAndBindUi(this);
  }
      
  protected native void consoleLog(Object obj) /*-{
    if ($wnd.console && $wnd.console.log) {
      $wnd.console.log(obj);
    }
  }-*/;
  
}
