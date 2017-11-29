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
  
  // File menu
  //@UiField MenuItem fileneweasik;
  @UiField MenuItem filenewopdsl;
  @UiField MenuItem fileopen;
  @UiField MenuItem filesave;
  @UiField MenuItem filesaveas;
  @UiField MenuItem filesaveall;
  @UiField MenuItem fileclose;
  @UiField MenuItem filequit;
  
  // Edit menu
  //@UiField MenuItem editfind;
  //@UiField MenuItem editcopyasrtf;
  //@UiField MenuItem editfoldall;
  //@UiField MenuItem editunfoldall;
  
  // Tools menu
  //@UiField MenuItem toolsrun;
  //@UiField MenuItem toolsabort;
  //@UiField MenuItem toolsoptions;
  //@UiField MenuItem toolslegacyoptions;
  //@UiField MenuItem toolsedchaser;
  //@UiField MenuItem toolssqlloader;
  //@UiField MenuItem toolssqlmapper;
  //@UiField MenuItem toolssqlchecker;
  //@UiField MenuItem toolsnrshredder;
  //@UiField MenuItem toolseasik;
  
  // AQL menu
  //@UiField MenuItem aqloutline;
  //@UiField MenuItem aqlinfermapping;
  //@UiField MenuItem aqlinferquery;
  //@UiField MenuItem aqlinfertransform;
  //@UiField MenuItem aqlinferinstance;
  //@UiField MenuItem aqlemithtml;
  
  // Xholon menu (optional)
  @UiField MenuItem xhstart;
  @UiField MenuItem xhtex;
  @UiField MenuItem xhgv;
  @UiField MenuItem xhchap;
  //@UiField MenuItem xhgvschema;
  //@UiField MenuItem xhgvinstances;
  //@UiField MenuItem xhgvboth;
  //@UiField MenuItem xhchapschema;
  //@UiField MenuItem xhsql;
  @UiField MenuItem xhxml;
  @UiField MenuItem xhyaml;
  
  // Help menu
  @UiField MenuItem helpabout;
  
  // buttons
  @UiField Button brun;
  @UiField Button babort;
  @UiField Button bnewopdsl;
  @UiField Button bopen;
  @UiField Button bsave;
  @UiField Button boptions;
  @UiField Button bhelp;
  
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
    
    // File menu
    fileopen.setEnabled(false);
    filesave.setEnabled(false);
    filesaveas.setEnabled(false);
    
    // Edit menu
    
    // Xholon menu
    //xhgvschema.setEnabled(false);
    //xhgvinstances.setEnabled(false);
    //xhgvboth.setEnabled(false);
    //xhchapschema.setEnabled(false);
    //xhsql.setEnabled(false);
    xhtex.setEnabled(false);
    xhgv.setEnabled(false);
    xhchap.setEnabled(false);
    xhxml.setEnabled(false);
    xhyaml.setEnabled(false);
       
    xhstart.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhstart();
      }
    });
    
    xhgv.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhgv(cattSystem);
      }
    });
    
    /*xhgvinstances.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhgvinstances(cattSystem);
      }
    });
    
    xhgvboth.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhgvboth(cattSystem);
      }
    });*/
    
    xhchap.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhchap(cattSystem);
      }
    });
    
    /*xhsql.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhsql(cattSystem);
      }
    });*/
    
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
    
    babort.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        abort();
      }
    });
    
    bnewopdsl.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        newOpDsl();
      }
    });
    
    bopen.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        open();
      }
    });
    
    bsave.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        save();
      }
    });
    
    boptions.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        options();
      }
    });
    
    bhelp.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        help();
      }
    });
    
    // ListBox
    lbexamples.addChangeHandler(new ChangeHandler() {
      @Override
      public void onChange(ChangeEvent event) {
        String item = lbexamples.getSelectedItemText();
        switch (item) {
        case "Example 1": loadExample1(); break;
        //case "Example 2": loadExample2(); break;
        default:
          //Window.alert("Can't find " + item + ".");
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
  
  protected void abort() {
    Window.alert("Abort not yet implemented.");
  }
  
  /**
   * Create a New Operad DSL file.
   * For now, just clear the contents of the textarea.
   */
  protected void newOpDsl() {
    setCommand("", true);
  }
  
  protected void open() {
    boolean hasfileapi = this.hasFileAPIs();
    Window.alert("Open not yet implemented. Has File APIs: " + hasfileapi);
  }
  
  protected void save() {
    boolean hasfileapi = this.hasFileAPIs();
    Window.alert("Save not yet implemented. Has File APIs: " + hasfileapi);
  }
  
  protected void options() {
    Window.alert("Options not yet implemented.");
  }
  
  // Xholon menu
  
  protected native Object findXhCattSystem() /*-{
    //return $wnd.xh.root().first().next().next(); // old
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
      xhstart.setEnabled(false);
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
  
  //protected native void xhgvinstances(Object cattSystem) /*-{
  //  if (cattSystem == null) {return;}
  //  var instanceNode = cattSystem.first().next();
  //  $wnd.xh.xport("Graphviz", instanceNode, '{"gvFileExt":".gv","gvGraph":"digraph","layout":"dot","edgeOp":"->","gvCluster":"","shouldShowStateMachineEntities":false,"filter":"--Behavior,Script","nameTemplateNodeId":"^^^^i^","nameTemplateNodeLabel":"r C^^^","shouldQuoteLabels":true,"shouldShowLinks":true,"shouldShowLinkLabels":true,"shouldSpecifyLayout":false,"maxLabelLen":-1,"shouldColor":true,"defaultColor":"#f0f8ff","shouldSpecifyShape":true,"shape":"ellipse","shouldSpecifySize":false,"size":"6","shouldSpecifyFontname":false,"shouldSpecifyArrowhead":true,"arrowhead":"vee","shouldSpecifyStylesheet":true,"stylesheet":"Xholon.css","shouldSpecifyRankdir":true,"rankdir":"LR","shouldDisplayGraph":true,"outputFormat":"svg"}');
  //}-*/;
  
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
  
  /**
   * Does this web browser support the JavaScript File APIs.
   * search for: JavaScript File
   * see: https://www.html5rocks.com/en/tutorials/file/dndfiles/
   */
  protected native boolean hasFileAPIs() /*-{
    if ($wnd.File && $wnd.FileReader && $wnd.FileList && $wnd.Blob) {
      //console.log("The File APIs are available.");
      return true;
    } else {
      //console.log("The File APIs are not fully supported in this browser.");
      return false;
    }
  }-*/;
  
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
  
  /**
   * Get the contents of the command.
   * @return The String typed in by the user.
   */
  protected String getCommand() {
    Element ele = commandPane.getElement().getFirstChildElement();
    TextAreaElement ta = TextAreaElement.as(ele);
    String contents = ta.getValue();
    return contents;
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
  
  /**
   * Clear the contents of the result pane.
   */
  protected void clearResults()
  {
    
  }
  
  /**
   * Clear the contents of the command pane.
   */
  protected void clearCommand()
  {
    /*Element ele = commandPane.getElement().getFirstChildElement();
    TextAreaElement ta = TextAreaElement.as(ele);
    ta.setValue("");*/
  }
  
  @Override
  public void setTabHeader() {
    //String idStr = "Categorical Data IDE";
    //String tooltip = "Categorical Data IDE";
    //XholonGwtTabPanelHelper.updateTabHeader(idStr, tooltip, -1);
  }
  
  /**
   * Close the GUI.
   */
  protected void closeGui()
  {
   // Window.alert("closegui selected");
  }
  
  /**
   * Save the contents of the Command panel in an already specified file.
   */
  protected void saveCommandFile()
  {
    
  }
  
  /**
   * Save the contents of the Command panel in a file.
   */
  protected void saveCommandFileAs()
  {
    
  }
  
  /**
   * Read the GUI from a UIBinder XML file.
   */
  protected void readGuiFromXml() {
    widget = uiBinder.createAndBindUi(this);
  }
      
  /**
   * Limit the length of a string.
   */
  protected String limitLength(String str, int maxLen) {
    return str.length() > 80 ? str.substring(0, 80) : str;
  }
  
  /**
   * Test the existing AQL request.
   * http://208.113.133.193/cgi-bin/try.cgi?code=typeside+Ty+%3D+literal ...
   * call a hard-coded php program
   */
  /*protected void tryCgi(String requestData) {
    consoleLog(requestData);
		try {
	    final String uri = "aqlProxy.php";
	    RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, uri); // .GET .POST
	    rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
	    rb.sendRequest(requestData, new RequestCallback() {
        @Override
        public void onResponseReceived(Request req, Response resp) {
          if (resp.getStatusCode() == resp.SC_OK) {
            xholonPrintln(resp.getText());
            showInHtml(resp.getText());
          }
          else {
            xholonPrintln("status code:" + resp.getStatusCode());
            xholonPrintln("status text:" + resp.getStatusText());
            xholonPrintln("text:\n" + resp.getText());
          }
        }

        @Override
        public void onError(Request req, Throwable e) {
          xholonPrintln("onError:" + e.getMessage());
        }
      });
    } catch(RequestException e) {
      xholonPrintln("RequestException:" + e.getMessage());
    }
  }*/
  
  protected native void consoleLog(Object obj) /*-{
    if ($wnd.console && $wnd.console.log) {
      $wnd.console.log(obj);
    }
  }-*/;
  
  protected native void xholonPrintln(Object obj) /*-{
    if ($wnd.xh && $wnd.xh.root) {
      $wnd.xh.root().println(obj);
    }
  }-*/;
  
  protected native void showInHtml(Object obj) /*-{
    //var ele = $doc.querySelector("#xhtreemap");
    //ele.append(obj);
    var ele = $doc.querySelector("#xhgraph");
    ele.insertAdjacentHTML('afterend', obj);
  }-*/;
  
  /**
   * This is probably not needed.
   */
  protected native String encodeURI(String str) /*-{
    return $wnd.encodeURI(str);
  }-*/;
  
}
