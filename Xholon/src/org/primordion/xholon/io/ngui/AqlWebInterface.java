/* AQL Web Interface
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
//import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

//import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon; //WithPorts;
//import org.primordion.xholon.io.XholonGwtTabPanelHelper;

/**
 * Provide a AQL Web Interface.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 27, 2017)
 */
//public class AqlWebInterface extends Xholon implements INamedGui {
public class AqlWebInterface implements INamedGui {
  
  interface AqlWebUiBinder extends UiBinder<Widget, AqlWebInterface> {}
  private static AqlWebUiBinder uiBinder = GWT.create(AqlWebUiBinder.class);
  
  // File menu
  @UiField MenuItem fileneweasik;
  @UiField MenuItem filenewaql;
  @UiField MenuItem fileopen;
  @UiField MenuItem filesave;
  @UiField MenuItem filesaveas;
  @UiField MenuItem filesaveall;
  @UiField MenuItem fileclose;
  @UiField MenuItem filequit;
  
  // Edit menu
  @UiField MenuItem editfind;
  @UiField MenuItem editcopyasrtf;
  @UiField MenuItem editfoldall;
  @UiField MenuItem editunfoldall;
  
  // Tools menu
  @UiField MenuItem toolsrun;
  @UiField MenuItem toolsabort;
  @UiField MenuItem toolsoptions;
  @UiField MenuItem toolslegacyoptions;
  @UiField MenuItem toolsedchaser;
  @UiField MenuItem toolssqlloader;
  @UiField MenuItem toolssqlmapper;
  @UiField MenuItem toolssqlchecker;
  @UiField MenuItem toolsnrshredder;
  @UiField MenuItem toolseasik;
  
  // AQL menu
  @UiField MenuItem aqloutline;
  @UiField MenuItem aqlinfermapping;
  @UiField MenuItem aqlinferquery;
  @UiField MenuItem aqlinfertransform;
  @UiField MenuItem aqlinferinstance;
  @UiField MenuItem aqlemithtml;
  
  // Xholon menu (optional)
  @UiField MenuItem xhstart;
  @UiField MenuItem xhgvschema;
  @UiField MenuItem xhgvinstances;
  @UiField MenuItem xhgvboth;
  @UiField MenuItem xhchapschema;
  @UiField MenuItem xhsql;
  @UiField MenuItem xhxml;
  @UiField MenuItem xhyaml;
  
  // Help menu
  @UiField MenuItem helpabout;
  
  // buttons
  @UiField Button brun;
  @UiField Button babort;
  @UiField Button bnewaql;
  @UiField Button bopen;
  @UiField Button bsave;
  @UiField Button boptions;
  @UiField Button bhelp;
  
  // label and listBox
  @UiField ListBox lbexamples;
  
  @UiField HTML commandPane;
  @UiField TextAreaElement commandPaneTAE;
  
  /** The Xholon node that the GUI node is associated with. */
  //IXholon context = null;
  
  /** The name of the context node. */
  //private String roleName = null;
  
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
  public AqlWebInterface() {}
  
  /*
   * @see org.primordion.xholon.base.Xholon#getRoleName()
   */
  //public String getRoleName() {
  //  return roleName;
  //}

  /*
   * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
   */
  //public void setRoleName(String roleName) {
  //  this.roleName = roleName;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#getContext()
   */
  //public IXholon getContext() {
  //  return context;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#setContext(org.primordion.xholon.base.IXholon)
   */
  //public void setContext(IXholon context) {
  //  this.context = context;
  //}

  //public IXholon getCommandPane() {
  //  return null; //commandPane;
  //}

  //public void setCommandPane(IXholon commandPane) {
  //  //this.commandPane = commandPane;
  //}
  
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
    
    /*clearcommand.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        clearCommand();
      }
    });
    */
    
    // Xholon menu
    xhgvschema.setEnabled(false);
    xhgvinstances.setEnabled(false);
    xhgvboth.setEnabled(false);
    xhchapschema.setEnabled(false);
    xhsql.setEnabled(false);
    xhxml.setEnabled(false);
    xhyaml.setEnabled(false);
       
    xhstart.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        if (xhstart()) {
          cattSystem = findXhCattSystem();
          xhgvschema.setEnabled(true);
          xhgvinstances.setEnabled(true);
          xhgvboth.setEnabled(true);
          xhchapschema.setEnabled(true);
          xhsql.setEnabled(true);
          xhxml.setEnabled(true);
          xhyaml.setEnabled(true);
        }
        else {
          Window.alert("Unable to start Xholon.");
        }
        xhstart.setEnabled(false);
      }
    });
    
    xhgvschema.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhgvschema(cattSystem);
      }
    });
    
    xhgvinstances.setScheduledCommand(new Command() {
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
    });
    
    xhchapschema.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhchapschema(cattSystem);
      }
    });
    
    xhsql.setScheduledCommand(new Command() {
      @Override
      public void execute() {
        xhsql(cattSystem);
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
    
    bnewaql.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        newAql();
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
        case "Denormalize": loadDenormalize(); break;
        case "Employees": loadEmployees(); break;
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
    //Window.alert("Run not yet implemented.");
    String requestData = getCommand();
    //consoleLog(requestData);
    //this.tryCgi("code=" + requestData);
    //String encodedRequestDataJS = this.encodeURI(requestData);
    //consoleLog(encodedRequestDataJS);
    //this.tryCgi("code=" + encodedRequestDataJS);
    String encodedRequestData = URL.encodeQueryString(requestData);
    consoleLog(encodedRequestData);
    this.tryCgi("code=" + encodedRequestData);
  }
  
  protected void abort() {
    Window.alert("Abort not yet implemented.");
  }
  
  /**
   * Create a New AQL file.
   * For now, just clear the contents of the textarea.
   */
  protected void newAql() {
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
    return $wnd.xh.root().first().next().next();
  }-*/;
  
  /**
   * Start Xholon.
   */
  protected native boolean xhstart() /*-{
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
  
  protected native void xhgvschema(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    var schemaNode = cattSystem.first();
    $wnd.xh.xport("Graphviz", schemaNode, '{"gvFileExt":".gv","gvGraph":"digraph","layout":"dot","edgeOp":"->","gvCluster":"","shouldShowStateMachineEntities":false,"filter":"--Behavior,Script","nameTemplateNodeId":"^^^^i^","nameTemplateNodeLabel":"R^^^^^","shouldQuoteLabels":true,"shouldShowLinks":true,"shouldShowLinkLabels":true,"shouldSpecifyLayout":false,"maxLabelLen":-1,"shouldColor":true,"defaultColor":"#f0f8ff","shouldSpecifyShape":true,"shape":"ellipse","shouldSpecifySize":false,"size":"6","shouldSpecifyFontname":false,"shouldSpecifyArrowhead":true,"arrowhead":"vee","shouldSpecifyStylesheet":true,"stylesheet":"Xholon.css","shouldSpecifyRankdir":false,"rankdir":"LR","shouldDisplayGraph":true,"outputFormat":"svg"}');
  }-*/;
  
  protected native void xhgvinstances(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    var instanceNode = cattSystem.first().next();
    $wnd.xh.xport("Graphviz", instanceNode, '{"gvFileExt":".gv","gvGraph":"digraph","layout":"dot","edgeOp":"->","gvCluster":"","shouldShowStateMachineEntities":false,"filter":"--Behavior,Script","nameTemplateNodeId":"^^^^i^","nameTemplateNodeLabel":"r C^^^","shouldQuoteLabels":true,"shouldShowLinks":true,"shouldShowLinkLabels":true,"shouldSpecifyLayout":false,"maxLabelLen":-1,"shouldColor":true,"defaultColor":"#f0f8ff","shouldSpecifyShape":true,"shape":"ellipse","shouldSpecifySize":false,"size":"6","shouldSpecifyFontname":false,"shouldSpecifyArrowhead":true,"arrowhead":"vee","shouldSpecifyStylesheet":true,"stylesheet":"Xholon.css","shouldSpecifyRankdir":true,"rankdir":"LR","shouldDisplayGraph":true,"outputFormat":"svg"}');
  }-*/;
  
  protected native void xhchapschema(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    var schemaNode = cattSystem.first();
    $wnd.xh.xport("_other,ChapNetwork", schemaNode, '{"showNetwork":true,"showTree":false,"maxTreeLevels":1,"width":"800px","height":"800px","nameTemplate":"R^^^^^","maxChars":-1,"linksLength":100,"showPortName":true,"nodesStyle":"dot","stabilize":"false","jsLibName":"network-min"}');
  }-*/;
  
  protected native void xhgvboth(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    $wnd.xh.xport("Graphviz", cattSystem, '{"shouldShowLinkLabels":true,"shouldSpecifyShape":true,"shouldSpecifyArrowhead":true,"shouldDisplayGraph":true}');
  }-*/;
  
  protected native void xhsql(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    var instanceNode = cattSystem.first().next();
    $wnd.xh.xport("_nosql,Sql", instanceNode, '{"parent":false,"idRoleXhcNames":"ID,,xhcID","idRoleXhcFormats":"^^^^i^,,^^^^i^","parentForeignKeys":false,"mechanismIhNodes":false}');
  }-*/;
  
  protected native void xhxml(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    $wnd.xh.xport("Xml", cattSystem, '{"writeStartDocument":false,"writePorts":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}');
  }-*/;
  
  protected native void xhyaml(Object cattSystem) /*-{
    if (cattSystem == null) {return;}
    $wnd.xh.xport("Yaml", cattSystem,'{"writeStartDocument":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}');
  }-*/;
  
  /**
   * Provide the user with some brief help information.
   */
  protected void help() {
    AqlHelpDialogBox helpdb = new AqlHelpDialogBox();
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
    var aqlex = $wnd.AQLEXAMPLES;
    if (aqlex) {
      str = aqlex.getExample(item);
    }
    return str;
  }-*/;
  
  protected void loadDenormalize() {
    String str = new StringBuilder()
    .append("typeside Ty = literal {\n")
    .append("  java_types\n")
    .append("    String = \"java.lang.String\"\n")
    .append("  java_constants\n")
    .append("    String = \"return input[0]\"\n")
    .append("}\n")
    .append("\n")
    .append("schema NormalizedSchema = literal : Ty {\n")
    .append("  entities\n")
    .append("    Male \n")
    .append("    Female\n")
    .append("  foreign_keys\n")
    .append("    mother : Male -> Female\n")
    .append("  attributes\n")
    .append("    female_name : Female -> String\n")
    .append("    male_name : Male -> String \n")
    .append("}\n")
    .append("\n")
    .append("instance NormalizedData = literal : NormalizedSchema {\n")
    .append("  generators\n")
    .append("    Al Bob Charlie : Male\n")
    .append("    Ellie Fran : Female\n")
    .append("  equations\n")
    .append("    Al.male_name = Albert \n")
    .append("    Al.mother = Ellie\n")
    .append("    \n")
    .append("    Bob.male_name = George\n")
    .append("    Bob.mother = Ellie\n")
    .append("    \n")
    .append("    Charlie.male_name = Charles\n")
    .append("    Charlie.mother = Fran\n")
    .append("\n")
    .append("    Ellie.female_name = Elaine\n")
    .append("    Fran.female_name = Francine\n")
    .append("}\n")
    .append("\n")
    .append("schema DeNormalizedSchema = literal : Ty {\n")
    .append("  imports\n")
    .append("    NormalizedSchema\n")
    .append("  attributes\n")
    .append("    mother_name : Male -> String\n")
    .append("  observation_equations\n")
    .append("    forall m:Male. mother_name(m) = female_name(mother(m))\n")
    .append("}\n")
    .append("\n")
    .append("instance DeNormalizedData = literal : DeNormalizedSchema {\n")
    .append("  imports\n")
    .append("    NormalizedData\n")
    .append("}\n")
    .toString();
    setCommand(str, true);
  }
  
  protected void loadEmployees() {
    String str = new StringBuilder()
    .append("typeside Ty = literal {\n")
    .append("  types\n")
    .append("    string \n")
    .append("    nat\n")
    .append("  constants\n")
    .append("    Al Akin Bob Bo Carl Cork Dan Dunn Math CS : string\n")
    .append("    zero : nat\n")
    .append("  functions\n")
    .append("    succ    : nat -> nat\n")
    .append("    plus    : nat, nat -> nat\n")
    .append("  equations  \n")
    .append("    forall x. plus(zero, x) = x\n")
    .append("    forall x, y. plus(succ(x),y) = succ(plus(x,y))\n")
    .append("  options\n")
    .append("    prover = completion\n")
    .append("}\n")
    .append("\n")
    .append("schema S = literal : Ty {\n")
    .append("  entities\n")
    .append("    Employee \n")
    .append("    Department\n")
    .append("  foreign_keys\n")
    .append("    manager   : Employee -> Employee\n")
    .append("    worksIn   : Employee -> Department\n")
    .append("    secretary : Department -> Employee\n")
    .append("  path_equations \n")
    .append("    manager.worksIn = worksIn\n")
    .append("    secretary.worksIn = Department\n")
    .append("  attributes\n")
    .append("    first last  : Employee -> string\n")
    .append("    age      : Employee -> nat\n")
    .append("    cummulative_age: Employee -> nat\n")
    .append("    name     : Department -> string\n")
    .append("  observation_equations\n")
    .append("    forall e. cummulative_age(e) = plus(age(e), age(manager(e)))\n")
    .append("  options\n")
    .append("    prover = completion\n")
    .append("}\n")
    .append("\n")
    .append("instance I = literal : S {\n")
    .append("  generators \n")
    .append("    a b c : Employee\n")
    .append("    m s : Department\n")
    .append("  equations \n")
    .append("    first(a) = Al\n")
    .append("    first(b) = Bob  last(b) = Bo\n")
    .append("    first(c) = Carl \n")
    .append("    name(m)  = Math name(s) = CS\n")
    .append("    age(a) = age(c) \n")
    .append("    manager(a) = b manager(b) = b manager(c) = c\n")
    .append("    worksIn(a) = m worksIn(b) = m worksIn(c) = s\n")
    .append("    secretary(s) = c secretary(m) = b \n")
    .append("    secretary(worksIn(a)) = manager(a)\n")
    .append("    worksIn(a) = worksIn(manager(a))\n")
    .append("    age(a) = zero.succ.succ \n")
    .append("    age(manager(a)) = zero.succ\n")
    .append("  options\n")
    .append("    prover = completion\n")
    .append("    completion_precedence = \"zero a b c m s Al Akin Bob Bo Carl Cork Dan Dunn Math CS first last name age manager worksIn secretary succ plus\"\n")
    .append("} \n")
    .append("\n")
    .append("/////////////////////////////////////////////////////////////////\n")
    .append("\n")
    .append("typeside TyJava = literal { \n")
    .append("  java_types\n")
    .append("    string = \"java.lang.String\"\n")
    .append("    nat = \"java.lang.Integer\"\n")
    .append("  java_constants\n")
    .append("    string = \"return input[0]\"\n")
    .append("    nat = \"return java.lang.Integer.parseInt(input[0])\"\n")
    .append("  java_functions\n")
    .append("    plus : nat,nat -> nat = \"return (input[0] + input[1]).intValue()\"\n")
    .append("}\n")
    .append("\n")
    .append("schema SJava = literal : TyJava {\n")
    .append("  entities\n")
    .append("    Employee \n")
    .append("    Department\n")
    .append("  foreign_keys\n")
    .append("    manager   : Employee -> Employee\n")
    .append("    worksIn   : Employee -> Department\n")
    .append("    secretary : Department -> Employee\n")
    .append("  path_equations \n")
    .append("    manager.worksIn = worksIn\n")
    .append("    secretary.worksIn = Department\n")
    .append("    manager.manager = manager\n")
    .append("  attributes\n")
    .append("    first last  : Employee -> string\n")
    .append("    age      : Employee -> nat\n")
    .append("    cummulative_age: Employee -> nat\n")
    .append("    name     : Department -> string\n")
    .append("  observation_equations\n")
    .append("    forall e. cummulative_age(e) = plus(age(e), age(manager(e)))\n")
    .append("}\n")
    .append("\n")
    .append("instance IJava = literal : SJava {\n")
    .append("  generators \n")
    .append("    a b c : Employee\n")
    .append("    m s : Department\n")
    .append("  equations \n")
    .append("    first(a) = Al\n")
    .append("    first(b) = Bob  last(b) = Bo\n")
    .append("    first(c) = Carl \n")
    .append("    name(m)  = Math name(s) = CS\n")
    .append("    age(a) = age(c) \n")
    .append("    manager(a) = b manager(b) = b manager(c) = c\n")
    .append("    worksIn(a) = m worksIn(b) = m worksIn(c) = s\n")
    .append("    secretary(s) = c secretary(m) = b \n")
    .append("    secretary(worksIn(a)) = manager(a)\n")
    .append("    worksIn(a) = worksIn(manager(a))\n")
    .append("    age(a) = \"2\" \n")
    .append("    age(manager(a)) = \"1\"\n")
    .append("}\n")
    .append("\n")
    .append("instance IRandom = random : SJava {\n")
    .append("  generators\n")
    .append("    Employee -> 10\n")
    .append("    Department -> 2\n")
    .append("  //options\n")
    .append("  //  random_seed = 2\n")
    .append("}\n")
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
  protected void tryCgi(String requestData) {
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
  }
  
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
