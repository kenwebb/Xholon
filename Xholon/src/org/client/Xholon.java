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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.IXholonGui;
import org.primordion.xholon.io.XholonGuiClassic;
import org.primordion.xholon.io.XholonGuiD3CirclePack;
import org.primordion.xholon.io.XholonWorkbookBundle;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * The appName (URL "app") for a Java-based Xholon app,
 * must be exactly the same as the Java Application class excluding the initial "App".
 * For example: class AppHelloWorld converts to "HelloWorld" as the appName.
 * But note: class App09SpatialGames converts to "_09SpatialGames" as the appName.
 * The method names used in this class are also exactly the same as the appName.
 * This is consistent with the class name returned by calling Application.getJavaClassName(),
 * that's specified in the _xhn.xml file.
 * This guarantees a consistency that allows apps to store themselves as named items in localSorage.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 15, 2013)
 */
public class Xholon implements EntryPoint {
  
  // GUI options
  private static final String GUI_CLASSIC      = "clsc";
  private static final String GUI_D3CIRCLEPACK = "d3cp";
  private static final String GUI_NONE         = "none";
  private static final String GUI_SAVE         = "save"; // save to localStorage
  private static final String GUI_EDIT         = "edit"; // edit with XholonWorkbook editor
  
  // URL parameters
  private String appName = null; // "app"
  private String src = null; // "src"
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    
    initGwtEnvironment();
    
    // Xholon stuff
    TextArea resultField = new TextArea(); // temporary TextArea
    resultField.setSize("500px","15px"); // ("474px","15px"); // ("170px","100px");
    resultField.setStylePrimaryName("xh-TextArea");
    // TODO resultField.setSpellcheck(false); // spellcheck="false"   use JSNI ?
    resultField.setText("loading ...");
    RootPanel.get("xhconsole").add(resultField);
    
    testXholon(resultField);
  }
  
  /**
   * Generate reflection info for system and mechanism classes that may be used as nodes,
   * or may be used as node superclasses, at run-time.
   * This should only be used when system or mechanism classes have changed.
   */
  //private void genSystemMechanismClassInfo() {
  //  GWT.create(org.primordion.xholon.app.AppApp.class);
  //  GWT.create(org.primordion.xholon.base.AppBase.class);
  //  GWT.create(org.primordion.xholon.mech.petrinet.AppPetriNet.class);
  //}
  
  /**
   * Initialize the "environment variables" in GwtEnvironment.
   */
  private void initGwtEnvironment() {
    GwtEnvironment.gwtHostPageBaseURL = GWT.getHostPageBaseURL();
		GwtEnvironment.gwtModuleBaseForStaticFiles = GWT.getModuleBaseForStaticFiles();
		GwtEnvironment.gwtModuleBaseURL = GWT.getModuleBaseURL();
		GwtEnvironment.gwtModuleName = GWT.getModuleName();
		GwtEnvironment.gwtVersion = GWT.getVersion();
		
		GwtEnvironment.navAppCodeName = Navigator.getAppCodeName();
		GwtEnvironment.navAppName = Navigator.getAppName();
		GwtEnvironment.navAppVersion = Navigator.getAppVersion();
		GwtEnvironment.navPlatform = Navigator.getPlatform();
		GwtEnvironment.navUserAgent = Navigator.getUserAgent();
  }
  
  /**
   * Test Xholon.
   */
  private void testXholon(TextArea resultField) {
    
    // get HTTP parameters
    appName = Location.getParameter("app");
    if (appName != null) {
      // convert "+" back to " ", etc.
      appName = URL.decodeQueryString(appName);
    }
    src = Location.getParameter("src");
    String withGuiParam = Location.getParameter("gui");
    System.out.println(appName);
    if (appName == null) {
      appName = "HelloWorld";
    }
    else if (src != null) {
      // ex: "gist" + "1234567"
      // ex: "lstr" + "1234567"
      appName = src + appName;
    }
    //boolean withGui = true; // default
    String withGui = GUI_CLASSIC;
    if (withGuiParam != null) {
      //withGui = Boolean.parseBoolean(withGuiParam);
      withGui = withGuiParam;
    }
    
    // run Xholon app
    if ("Chameleon".equals(appName)) {Chameleon(withGui);}
    else if ("Furcifer".equals(appName)) {Furcifer(withGui);}
    else if ("Bestiary".equals(appName)) {Bestiary(withGui);}
    else if ("HelloWorld".equals(appName)) {HelloWorld(withGui);}
    else if ("PingPong".equals(appName)) {PingPong(withGui);}
    else if ("Cell".equals(appName)) {Cell(withGui);}
    else if ("Life".equals(appName)) {Life(withGui);}
    else if ("Life_3d".equals(appName)) {Life_3d(withGui);}
    else if ("CellAutop".equals(appName)) {CellAutop(withGui);}
    else if ("GameOfLife".equals(appName)) {GameOfLife(withGui);}
    else if ("GameOfLife_Big".equals(appName)) {GameOfLife_Big(withGui);}
    else if ("GameOfLife_Huge".equals(appName)) {GameOfLife_Huge(withGui);}
    else if ("HelloWorld_TestTime".equals(appName)) {HelloWorld_TestTime(withGui);}
    else if ("_1dSimple".equals(appName)) {_1dSimple(withGui);}
    else if ("Generic".equals(appName)) {Generic(withGui);}
    else if ("model04".equals(appName)) {model04(withGui);}
    else if ("carboncycle03".equals(appName)) {carboncycle03(withGui);}
    else if ("SpringIdol".equals(appName)) {SpringIdol(withGui);}
    else if ("TurtleExample1".equals(appName)) {TurtleExample1(withGui);}
    else if ("WolfSheepGrass".equals(appName)) {WolfSheepGrass(withGui);}
    else if ("mdcs_m2_1mp".equals(appName)) {mdcs_m2_1mp(withGui);}
    else if ("mdcs_m2_2mp".equals(appName)) {mdcs_m2_2mp(withGui);}
    else if ("igm".equals(appName)) {igm(withGui);}
    else if ("AdapSysLab".equals(appName)) {AdapSysLab(withGui);}
    else if ("MeTTTa".equals(appName)) {MeTTTa(withGui);}
    else if ("solarsystemtest".equals(appName)) {solarsystemtest(withGui);}
    else if ("Collisions".equals(appName)) {Collisions(withGui);}
    else if ("Hex".equals(appName)) {Hex(withGui);}
    else if ("_09SpatialGames".equals(appName)) {_09SpatialGames(withGui);}
    
    // RCS manually-created
    else if ("Rcs1".equals(appName)) {Rcs1(withGui);}
    else if ("Rcs2".equals(appName)) {Rcs2(withGui);}
    // RCS from UML MagicDraw
    else if ("Rcs_GP_FSM".equals(appName)) {Rcs_GP_FSM(withGui);}
    else if ("Rcs_GP_FSM_Grid".equals(appName)) {Rcs_GP_FSM_Grid(withGui);}
    else if ("Rcs_GP_MM".equals(appName)) {Rcs_GP_MM(withGui);}
    else if ("Rcs_GP_MM_NoSymbols".equals(appName)) {Rcs_GP_MM_NoSymbols(withGui);}
    
    else if ("roomsghosts".equals(appName)) {roomsghosts(withGui);}
    //else if ("Risk".equals(appName)) {Risk(withGui);}
    else if ("English2French".equals(appName)) {English2French(withGui);}
    else if ("XBar_ex1".equals(appName)) {XBar_ex1(withGui);}
    else if ("RavaszHnm".equals(appName)) {RavaszHnm(withGui);}
    else if ("Red".equals(appName)) {Red(withGui);}
    else if ("TweenTrees".equals(appName)) {TweenTrees(withGui);}
    else if ("WaterLogic".equals(appName)) {WaterLogic(withGui);}
    else if ("TestFsmKW".equals(appName)) {TestFsmKW(withGui);}
    else if ("Turnstile".equals(appName)) {Turnstile(withGui);}
    else if ("testNodePorts".equals(appName)) {testNodePorts(withGui);}
    else if ("MathmlTest1".equals(appName)) {MathmlTest1(withGui);}
    else if ("OrNodeSample".equals(appName)) {OrNodeSample(withGui);}
    
    // Ealontro (non-Genetic Programming versions)
    else if ("AntForaging".equals(appName)) {AntForaging(withGui);}
    else if ("CartCentering".equals(appName)) {CartCentering(withGui);}
    else if ("AntTrail".equals(appName)) {AntTrail(withGui);}
    else if ("Tutorial4".equals(appName)) {Tutorial4(withGui);}
    
    // Agent Base Modeling (ABM) - Stupid Models
    else if ("StupidModel1".equals(appName)) {StupidModel1(withGui);}
    else if ("StupidModel2".equals(appName)) {StupidModel2(withGui);}
    else if ("StupidModel3".equals(appName)) {StupidModel3(withGui);}
    else if ("StupidModel4".equals(appName)) {StupidModel4(withGui);}
    else if ("StupidModel5".equals(appName)) {StupidModel5(withGui);}
    else if ("StupidModel5tg".equals(appName)) {StupidModel5tg(withGui);}
    else if ("StupidModel6".equals(appName)) {StupidModel6(withGui);}
    else if ("StupidModel7".equals(appName)) {StupidModel7(withGui);}
    else if ("StupidModel8".equals(appName)) {StupidModel8(withGui);}
    else if ("StupidModel9".equals(appName)) {StupidModel9(withGui);}
    else if ("StupidModel10".equals(appName)) {StupidModel10(withGui);}
    else if ("StupidModel11".equals(appName)) {StupidModel11(withGui);}
    else if ("StupidModel12".equals(appName)) {StupidModel12(withGui);}
    else if ("StupidModel13".equals(appName)) {StupidModel13(withGui);}
    else if ("StupidModel14".equals(appName)) {StupidModel14(withGui);}
    else if ("StupidModel15".equals(appName)) {StupidModel15(withGui);}
    else if ("StupidModel16".equals(appName)) {StupidModel16(withGui);}
    else if ("StupidModel16nl".equals(appName)) {StupidModel16nl(withGui);}
    
    // FSM UML MagicDraw
    else if ("TestFsm".equals(appName)) {TestFsm(withGui);}
    else if ("TestFsmForkJoin".equals(appName)) {TestFsmForkJoin(withGui);}
    else if ("TestFsmHistory".equals(appName)) {TestFsmHistory(withGui);}
    else if ("TestFsmJunction".equals(appName)) {TestFsmJunction(withGui);}
    else if ("TestFsmOrthogonal".equals(appName)) {TestFsmOrthogonal(withGui);}
    else if ("Elevator".equals(appName)) {Elevator(withGui);}
    else if ("Elevator_ShowStates".equals(appName)) {Elevator_ShowStates(withGui);}
    else if ("Beard2005_UML_Xholon_Step4_v1".equals(appName)) {Beard2005_UML_Xholon_Step4_v1(withGui);}
    else if ("Fsm06ex1_FsmXmi".equals(appName)) {Fsm06ex1_FsmXmi(withGui);}
    else if ("HelloWorldTutorial".equals(appName)) {HelloWorldTutorial(withGui);}
    else if ("HelloWorldTutorial_multiWorld".equals(appName)) {HelloWorldTutorial_multiWorld(withGui);}
    else if ("HelloWorldTutorial_plus".equals(appName)) {HelloWorldTutorial_plus(withGui);}
    else if ("HelloWorldTutorial_universe".equals(appName)) {HelloWorldTutorial_universe(withGui);}
    else if ("ProvidedRequiredTest".equals(appName)) {ProvidedRequiredTest(withGui);}
    else if ("StopWatch".equals(appName)) {StopWatch(withGui);}
    else if ("StopWatch_Xhym".equals(appName)) {StopWatch_Xhym(withGui);}
    
    // other UML/SysML (ArgoUML Poseidon Topcased)
    else if ("HelloWorldTutorialArgoUML".equals(appName)) {HelloWorldTutorialArgoUML(withGui);}
    else if ("Watch".equals(appName)) {Watch(withGui);}
    else if ("HelloWorldTutorialSysML".equals(appName)) {HelloWorldTutorialSysML(withGui);}
    else if ("HelloWorldTutorialTc".equals(appName)) {HelloWorldTutorialTc(withGui);}
    else if ("StateMachineOnly".equals(appName)) {StateMachineOnly(withGui);}
    
    // Petri Net
    else if ("feinberg1".equals(appName)) {feinberg1(withGui);}
    else if ("pn".equals(appName)) {pn(withGui);}
    
    // dynsys
    else if ("Fibonacci".equals(appName)) {Fibonacci(withGui);}
    else if ("Gravity1".equals(appName)) {Gravity1(withGui);}
    else if ("Interest".equals(appName)) {Interest(withGui);}
    else if ("ScheffranNActor".equals(appName)) {ScheffranNActor(withGui);}
    else if ("ScheffranTwoActor".equals(appName)) {ScheffranTwoActor(withGui);}
    else if ("Train".equals(appName)) {Train(withGui);}
    else if ("leakybucket".equals(appName)) {leakybucket(withGui);}
    else if ("stability".equals(appName)) {stability(withGui);}
    
    // Membrane Computing (PSystems)
    else if ("BraneCalc1".equals(appName)) {BraneCalc1(withGui);}
    else if ("CoopPSys00ex0".equals(appName)) {CoopPSys00ex0(withGui);}
    else if ("CoopSys02ex32_1".equals(appName)) {CoopSys02ex32_1(withGui);}
    else if ("CoopSys02ex343_1".equals(appName)) {CoopSys02ex343_1(withGui);}
    else if ("CoopSys02ex343_2".equals(appName)) {CoopSys02ex343_2(withGui);}
    else if ("Fsm06ex1".equals(appName)) {Fsm06ex1(withGui);}
    else if ("Fsm06ex1_Fsm".equals(appName)) {Fsm06ex1_Fsm(withGui);}
    else if ("SymAnti02ex41".equals(appName)) {SymAnti02ex41(withGui);}
    
    // gist (ex: "gist3377945")
    else if ((appName.startsWith("gist")) && (appName.length() > 4)) {gist(withGui, appName);}
    
    // lstr (ex: "lstr3377945")
    else if ((appName.startsWith("lstr")) && (appName.length() > 4)) {lstr(withGui, appName);}
    
    else {
      resultField.setText("unable to load " + appName);
    }
    
  }
  
  /**
   * Chameleon
   */
  private void Chameleon(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.xholon.app.Chameleon.AppChameleon.class),
      null);
  }
  
  /**
   * Furcifer (for QUnit JavaScript unit testing)
   */
  private void Furcifer(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.xholon.app.Furcifer.AppFurcifer.class),
      null);
  }
  
  /**
   * HelloWorld
   */
  private void HelloWorld(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.helloworldjnlp.AppHelloWorld.class),
      "config/helloworldjnlp/HelloWorld_xhn.xml");
  }
  
  /**
   * PingPong
   */
  private void PingPong(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.PingPong.AppPingPong.class),
      "config/PingPong/PingPong_xhn.xml");
  }
  
  /**
   * Cell
   */
  private void Cell(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.cellontro.app.AppCell.class),
      "config/cellontro/Cell/Cell_BioSystems_Jul03_xhn.xml");
  }
  
  /**
   * Life
   */
  private void Life(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.cellontro.app.AppLife.class),
      "config/cellontro/Life/Life_xhn.xml");
  }
  
  /**
   * Life_3d
   */
  private void Life_3d(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.cellontro.app.AppLife.class),
      "config/cellontro/Life3d/Life3d_SingleCells_xhn.xml");
  }
  
  /**
   * CellAutop
   */
  private void CellAutop(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.cellontro.app.AppCellAutop.class),
      "config/cellontro/CellAutop/Autop_xhn.xml");
  }
  
  /**
   * GameOfLife
   */
  private void GameOfLife(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.GameOfLife.AppGameOfLife.class),
      "config/GameOfLife/GameOfLife_xhn.xml");
  }
  
  /**
   * GameOfLife_Big
   */
  private void GameOfLife_Big(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.GameOfLife.AppGameOfLife.class),
      "config/GameOfLife/GameOfLife_Big_xhn.xml");
  }
  
  /**
   * GameOfLife_Huge
   */
  private void GameOfLife_Huge(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.GameOfLife.AppGameOfLife.class),
      "config/GameOfLife/GameOfLife_Huge_xhn.xml");
  }
  
  /**
   * StupidModels
   */
  private void StupidModel1(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm1.AppStupidModel1.class),
      "config/StupidModel/StupidModel1/StupidModel1_xhn.xml");
  }
  private void StupidModel2(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm2.AppStupidModel2.class),
      "config/StupidModel/StupidModel2/StupidModel2_xhn.xml");
  }
  private void StupidModel3(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm3.AppStupidModel3.class),
      "config/StupidModel/StupidModel3/StupidModel3_xhn.xml");
  }
  private void StupidModel4(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm4.AppStupidModel4.class),
      "config/StupidModel/StupidModel4/StupidModel4_xhn.xml");
  }
  private void StupidModel5(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm5.AppStupidModel5.class),
      "config/StupidModel/StupidModel5/StupidModel5_xhn.xml");
  }
  private void StupidModel5tg(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm5tg.AppStupidModel5tg.class),
      "config/StupidModel/StupidModel5tg/StupidModel5tg_xhn.xml");
  }
  private void StupidModel6(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm6.AppStupidModel6.class),
      "config/StupidModel/StupidModel6/StupidModel6_xhn.xml");
  }
  private void StupidModel7(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm7.AppStupidModel7.class),
      "config/StupidModel/StupidModel7/StupidModel7_xhn.xml");
  }
  private void StupidModel8(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm8.AppStupidModel8.class),
      "config/StupidModel/StupidModel8/StupidModel8_xhn.xml");
  }
  private void StupidModel9(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm9.AppStupidModel9.class),
      "config/StupidModel/StupidModel9/StupidModel9_xhn.xml");
  }
  private void StupidModel10(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm10.AppStupidModel10.class),
      "config/StupidModel/StupidModel10/StupidModel10_xhn.xml");
  }
  private void StupidModel11(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm11.AppStupidModel11.class),
      "config/StupidModel/StupidModel11/StupidModel11_xhn.xml");
  }
  private void StupidModel12(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm12.AppStupidModel12.class),
      "config/StupidModel/StupidModel12/StupidModel12_xhn.xml");
  }
  private void StupidModel13(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm13.AppStupidModel13.class),
      "config/StupidModel/StupidModel13/StupidModel13_xhn.xml");
  }
  private void StupidModel14(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm14.AppStupidModel14.class),
      "config/StupidModel/StupidModel14/StupidModel14_xhn.xml");
  }
  private void StupidModel15(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm15.AppStupidModel15.class),
      "config/StupidModel/StupidModel15/StupidModel15_xhn.xml");
  }
  private void StupidModel16(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm16.AppStupidModel16.class),
      "config/StupidModel/StupidModel16/StupidModel16_xhn.xml");
  }
  private void StupidModel16nl(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm16nl.AppStupidModel16nl.class),
      "config/StupidModel/StupidModel16nl/StupidModel16nl_xhn.xml");
  }
  
  /**
   * HelloWorld_TestTime
   */
  private void HelloWorld_TestTime(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.HelloWorld_TestTime.AppHelloWorld_TestTime.class),
      "config/HelloWorld/HelloWorld_TestTime_xhn.xml");
  }
  
  /**
   * _1dSimple
   */
  private void _1dSimple(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.Simple1d.App1dSimple.class),
      "config/ca/1dSimple/1dSimple_xhn.xml");
  }
  
  /**
   * Generic
   */
  private void Generic(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.Generic.AppGeneric.class),
      "config/Generic/Generic_xhn.xml");
  }
  
  /**
   * model04
   */
  private void model04(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.climatechange.model04.Appmodel04.class),
      "config/climatechange/model04/_xhn.xml");
  }
  
  /**
   * carboncycle03
   */
  private void carboncycle03(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.climatechange.carboncycle03.Appcarboncycle03.class),
      "config/climatechange/carboncycle03/_xhn.xml");
  }
  
  /**
   * SpringIdol
   */
  private void SpringIdol(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.SpringIdol.AppSpringIdol.class),
      "config/SpringIdol/SpringIdol_xhn.xml");
  }
  
  /**
   * TestFsm
   */
  private void TestFsm(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsm.AppTestFsm.class),
      "config/xmiapps/TestFsm/TestFsm_xhn.xml");
  }
  
  /**
   * TestFsmForkJoin
   */
  private void TestFsmForkJoin(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsmForkJoin.AppTestFsmForkJoin.class),
      "config/xmiapps/TestFsmForkJoin/TestFsmForkJoin_xhn.xml");
  }
  
  /**
   * TestFsmHistory
   */
  private void TestFsmHistory(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsmHistory.AppTestFsmHistory.class),
      "config/xmiapps/TestFsmHistory/TestFsmHistory_xhn.xml");
  }
  
  /**
   * TestFsmJunction
   */
  private void TestFsmJunction(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsmJunction.AppTestFsmJunction.class),
      "config/xmiapps/TestFsmJunction/TestFsmJunction_xhn.xml");
  }
  
  /**
   * TestFsmOrthogonal
   */
  private void TestFsmOrthogonal(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsmOrthogonal.AppTestFsmOrthogonal.class),
      "config/xmiapps/TestFsmOrthogonal/TestFsmOrthogonal_xhn.xml");
  }
  
  /**
   * Elevator
   */
  private void Elevator(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.Elevator.AppElevator.class),
      "config/xmiapps/Elevator/Elevator_xhn.xml");
  }
  
  /**
   * Elevator_ShowStates
   */
  private void Elevator_ShowStates(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.Elevator.AppElevator_ShowStates.class),
      "config/xmiapps/Elevator/Elevator_ShowStates_xhn.xml");
  }
  
  /**
   * Fsm06ex1_FsmXmi
   */
  private void Fsm06ex1_FsmXmi(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.Fsm06ex1_Fsm.AppFsm06ex1_FsmXmi.class),
      "config/xmiapps/Fsm06ex1_Fsm/Fsm06ex1_Fsm_xhn.xml");
  }
  
  /**
   * HelloWorldTutorial
   */
  private void HelloWorldTutorial(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.HelloWorldTutorial.AppHelloWorldTutorial.class),
      "config/xmiapps/HelloWorldTutorial/HelloWorldTutorial_xhn.xml");
  }
  
  /**
   * HelloWorldTutorial_multiWorld
   */
  private void HelloWorldTutorial_multiWorld(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.HelloWorldTutorial_multiWorld.AppHelloWorldTutorial_multiWorld.class),
      "config/xmiapps/HelloWorldTutorial_multiWorld/HelloWorldTutorial_multiWorld_xhn.xml");
  }
  
  /**
   * HelloWorldTutorial_plus
   */
  private void HelloWorldTutorial_plus(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.HelloWorldTutorial_plus.AppHelloWorldTutorial_plus.class),
      "config/xmiapps/HelloWorldTutorial_plus/HelloWorldTutorial_plus_xhn.xml");
  }
  
  /**
   * HelloWorldTutorial_universe
   */
  private void HelloWorldTutorial_universe(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.HelloWorldTutorial_universe.AppHelloWorldTutorial_universe.class),
      "config/xmiapps/HelloWorldTutorial_universe/HelloWorldTutorial_universe_xhn.xml");
  }
  
  /**
   * ProvidedRequiredTest
   */
  private void ProvidedRequiredTest(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.ProvidedRequiredTest.AppProvidedRequiredTest.class),
      "config/xmiapps/ProvidedRequiredTest/ProvidedRequiredTest_xhn.xml");
  }
  
  /**
   * StopWatch
   */
  private void StopWatch(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.StopWatch.AppStopWatch.class),
      "config/xmiapps/StopWatch/StopWatch_xhn.xml");
  }
  
  /**
   * StopWatch_Xhym
   */
  private void StopWatch_Xhym(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.StopWatch_Xhym.AppStopWatch_Xhym.class),
      "config/xmiapps/StopWatch/StopWatch_Xhym_xhn.xml");
  }
  
  /**
   * Watch (Poseidon)
   */
  private void Watch(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.Watch.AppWatch.class),
      "config/xmiapps/Watch/Watch_xhn.xml");
  }
  
  /**
   * HelloWorldTutorialArgoUML
   */
  private void HelloWorldTutorialArgoUML(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiappsArgoUML.AppHelloWorldTutorialArgoUML.class),
      "config/xmiappsArgoUML/HelloWorldTutorial/HelloWorldTutorial_xhn.xml");
  }
  
  /**
   * HelloWorldTutorialSysML
   */
  private void HelloWorldTutorialSysML(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiappsTcSysML.AppHelloWorldTutorialSysML.class),
      "config/xmiappsTcSysML/HelloWorldTutorialSysML/HelloWorldTutorialSysML_xhn.xml");
  }
  
  /**
   * HelloWorldTutorialTc
   */
  private void HelloWorldTutorialTc(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiappsTc.HelloWorldTutorial.AppHelloWorldTutorialTc.class),
      "config/xmiappsTc/HelloWorldTutorial/HelloWorldTutorial_xhn.xml");
  }
  
  /**
   * StateMachineOnly
   */
  private void StateMachineOnly(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiappsTc.StateMachineOnly.AppStateMachineOnly.class),
      "config/xmiappsTc/StateMachineOnly/StateMachineOnly_xhn.xml");
  }
  
  /**
   * feinberg1
   */
  private void feinberg1(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.petrinet.feinberg1.Appfeinberg1.class),
      null);
  }
  
  /**
   * Bestiary
   */
  private void Bestiary(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.Bestiary.AppBestiary.class),
      null);
  }
  
  /**
   * TurtleExample1
   */
  private void TurtleExample1(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.TurtleExample1.AppTurtleExample1.class),
      "config/user/TurtleExample1/TurtleExample1_xhn.xml");
  }
  
  /**
   * WolfSheepGrass
   */
  private void WolfSheepGrass(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.WolfSheepGrass.AppWolfSheepGrass.class),
      "config/user/WolfSheepGrass/WolfSheepGrass_xhn.xml");
  }
  
  // dynsys
  
  /**
   * Fibonacci
   */
  private void Fibonacci(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppFibonacci.class),
      "config/dynsys/Fibonacci/Fibonacci_xhn.xml");
  }

  /**
   * Gravity1
   */
  private void Gravity1(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppGravity1.class),
      "config/dynsys/Gravity1/Gravity1_xhn.xml");
  }

  /**
   * Interest
   */
  private void Interest(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppInterest.class),
      "config/dynsys/Interest/Interest_xhn.xml");
  }

  /**
   * ScheffranNActor
   */
  private void ScheffranNActor(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppScheffranNActor.class),
      "config/dynsys/ScheffranTwoActor/ScheffranNActor_xhn.xml");
  }

  /**
   * ScheffranTwoActor
   */
  private void ScheffranTwoActor(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppScheffranTwoActor.class),
      "config/dynsys/ScheffranTwoActor/ScheffranTwoActor_xhn.xml");
  }

  /**
   * Train
   */
  private void Train(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppTrain.class),
      "config/dynsys/Train/Train_xhn.xml");
  }
  
  /**
   * leakybucket
   */
  private void leakybucket(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.leakybucket.Appleakybucket.class),
      null);
  }
  
  /**
   * stability
   */
  private void stability(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.stability.Appstability.class),
      null);
  }

  /**
   * mdcs_m2_1mp
   */
  private void mdcs_m2_1mp(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.climatechange.mdcs.m2_1mp.Appmdcs.class),
      null);
  }
  
  /**
   * mdcs_m2_2mp
   */
  private void mdcs_m2_2mp(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.climatechange.mdcs.m2_2mp.Appmdcs.class),
      null);
  }
  
  /**
   * igm
   */
  private void igm(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.climatechange.igm.Appigm.class),
      null);
  }
  
  /**
   * pn
   */
  private void pn(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.azimuth.pn01.Apppn.class),
      null);
  }
  
  /**
   * AdapSysLab
   */
  private void AdapSysLab(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.ctrnn.AdapSysLab.AppAdapSysLab.class),
      null);
  }
  
  /**
   * MeTTTa
   */
  private void MeTTTa(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.MeTTTa.AppMeTTTa.class),
      "config/user/MeTTTa/MeTTTa_xhn.xml");
  }
  
  /**
   * solarsystemtest
   */
  private void solarsystemtest(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.climatechange.solarsystemtest.Appsolarsystemtest.class),
      null);
  }
  
  /**
   * Collisions
   */
  private void Collisions(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.Collisions.AppCollisions.class),
      "config/Collisions/Collisions_xhn.xml");
  }
  
  /**
   * Hex
   */
  private void Hex(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.Hex.AppHex.class),
      "config/Hex/Hex_xhn.xml");
  }
  
  /**
   * _09SpatialGames
   */
  private void _09SpatialGames(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.SpatialGames.App09SpatialGames.class),
      "config/SpatialGames/SpatialGames1_xhn.xml");
  }
  
  /**
   * Rcs1
   */
  private void Rcs1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.rcs.Rcs1.AppRcs1.class),
      "config/Rcs/Rcs1_xhn.xml");
  }
  
  /**
   * Rcs2
   */
  private void Rcs2(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.rcs.Rcs2.AppRcs2.class),
      "config/Rcs/Rcs2_xhn.xml");
  }
  
  /**
   * Rcs_GP_FSM
   */
  private void Rcs_GP_FSM(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.xmiapps.Rcs_GP_FSM.AppRcs_GP_FSM.class),
      "config/xmiapps/Rcs_GP_FSM/Rcs_GP_FSM_xhn.xml");
  }
  
  /**
   * Rcs_GP_FSM_Grid
   */
  private void Rcs_GP_FSM_Grid(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.xmiapps.Rcs_GP_FSM_Grid.AppRcs_GP_FSM_Grid.class),
      "config/xmiapps/Rcs_GP_FSM_Grid/Rcs_GP_FSM_Grid_xhn.xml");
  }
  
  /**
   * Rcs_GP_MM
   */
  private void Rcs_GP_MM(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.xmiapps.Rcs_GP_MM.AppRcs_GP_MM.class),
      "config/xmiapps/Rcs_GP_MM/Rcs_GP_MM_xhn.xml");
  }
  
  /**
   * Rcs_GP_MM_NoSymbols
   */
  private void Rcs_GP_MM_NoSymbols(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.xmiapps.Rcs_GP_MM_NoSymbols.AppRcs_GP_MM_NoSymbols.class),
      "config/xmiapps/Rcs_GP_MM_NoSymbols/Rcs_GP_MM_NoSymbols_xhn.xml");
  }
  
  /**
   * roomsghosts
   */
  private void roomsghosts(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.bigraph.roomsghosts.Approomsghosts.class),
      null);
  }
  
  /**
   * Beard2005_UML_Xholon_Step4_v1
   */
  private void Beard2005_UML_Xholon_Step4_v1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.xmiapps.beard41.AppBeard2005_UML_Xholon_Step4_v1.class),
      "config/xmiapps/Beard2005_UML_Xholon_Step4_v1/Beard2005_UML_Xholon_Step4_v1_xhn.xml");
  }
  
  /**
   * Risk
   */
  /*private void Risk(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.Risk.AppRisk.class),
      "config/user/Risk/Risk_xhn.xml");
  }*/
  
  /**
   * English2French
   */
  private void English2French(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.English2French.AppEnglish2French.class),
      "config/user/English2French/English2French_xhn.xml");
  }
  
  /**
   * XBar_ex1
   */
  private void XBar_ex1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.XBar.AppXBar_ex1.class),
      "config/user/XBar/XBar_xhn.xml");
  }
  
  /**
   * TestFsmKW
   */
  private void TestFsmKW(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.TestFsm.AppTestFsmKW.class),
      "config/TestFsm/TestFsm_KW_xhn.xml");
  }
  
  /**
   * Turnstile
   */
  private void Turnstile(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.Turnstile.AppTurnstile.class),
      null);
  }
  
  /**
   * testNodePorts
   */
  private void testNodePorts(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.testNodePorts.ApptestNodePorts.class),
      null);
  }
  
  /**
   * MathmlTest1
   */
  private void MathmlTest1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.mathmltest1.AppMathmlTest1.class),
      "config/user/mathmltest1/MathML_Test1_xhn.xml");
  }
  
  /**
   * OrNodeSample
   */
  private void OrNodeSample(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.OrNode.AppOrNodeSample.class),
      "config/OrNodeSample/OrNodeSample_xhn.xml");
  }
  
  // Ealontro
  
  /**
   * AntForaging
   */
  private void AntForaging(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.ealontro.app.AntForaging.AppAntForaging.class),
      "config/ealontro/AntForaging/AntForaging_xhn.xml");
  }
  
  /**
   * CartCentering
   */
  private void CartCentering(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.ealontro.app.CartCentering.AppCartCentering.class),
      "config/ealontro/CartCentering/CartCentering_xhn.xml");
  }
  
  /**
   * AntTrail
   */
  private void AntTrail(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.ealontro.app.EcjAntTrail.AppAntTrail.class),
      "config/ealontro/EcjAntTrail/AntTrail_1_xhn.xml");
  }
  
  /**
   * Tutorial4
   */
  private void Tutorial4(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.ealontro.app.EcjTutorial4.AppTutorial4.class),
      "config/ealontro/EcjTutorial4/Tutorial4_1_xhn.xml");
  }
  
  /**
   * RavaszHnm
   */
  private void RavaszHnm(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.RavaszHnm.AppRavaszHnm.class),
      "config/user/RavaszHnm/RavaszHnm_xhn.xml");
  }
  
  /**
   * Red
   */
  private void Red(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.Red.AppRed.class),
      "config/user/Red/xhn.xml");
  }
  
  /**
   * TweenTrees
   */
  private void TweenTrees(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.TweenTrees.AppTweenTrees.class),
      "config/user/TweenTrees/TweenTrees_xhn.xml");
  }
  
  /**
   * WaterLogic
   */
  private void WaterLogic(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.WaterLogic.AppWaterLogic.class),
      "config/user/WaterLogic/WaterLogic_xhn.xml");
  }
  
  // Membrane Computing (PSystems)
  
  /**
   * BraneCalc1
   */
  private void BraneCalc1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.memcomp.app.BraneCalc1.AppBraneCalc1.class),
      "config/memcomp/BraneCalc1/BraneCalc1_xhn.xml");
  }
  
  /**
   * CoopPSys00ex0
   */
  private void CoopPSys00ex0(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.memcomp.app.CoopPSys00ex0.AppCoopPSys00ex0.class),
      "config/memcomp/CoopPSys00ex0/CoopPSys00ex0_xhn.xml");
  }
  
  /**
   * CoopSys02ex32_1
   */
  private void CoopSys02ex32_1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.memcomp.app.CoopSys02ex32_1.AppCoopSys02ex32_1.class),
      "config/memcomp/CoopSys02ex32_1/CoopSys02ex32_1_xhn.xml");
  }
  
  /**
   * CoopSys02ex343_1
   */
  private void CoopSys02ex343_1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.memcomp.app.CoopSys02ex343_1.AppCoopSys02ex343_1.class),
      "config/memcomp/CoopSys02ex343_1/CoopSys02ex343_1_xhn.xml");
  }
  
  /**
   * CoopSys02ex343_2
   */
  private void CoopSys02ex343_2(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.memcomp.app.CoopSys02ex343_2.AppCoopSys02ex343_2.class),
      "config/memcomp/CoopSys02ex343_2/CoopSys02ex343_2_xhn.xml");
  }
  
  /**
   * Fsm06ex1
   */
  private void Fsm06ex1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.memcomp.app.Fsm06ex1.AppFsm06ex1.class),
      "config/memcomp/Fsm06ex1/Fsm06ex1_xhn.xml");
  }
  
  /**
   * Fsm06ex1_Fsm
   */
  private void Fsm06ex1_Fsm(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.memcomp.app.Fsm06ex1_Fsm.AppFsm06ex1_Fsm.class),
      "config/memcomp/Fsm06ex1/Fsm06ex1_Fsm_xhn.xml");
  }
  
  /**
   * SymAnti02ex41
   */
  private void SymAnti02ex41(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.memcomp.app.SymAnti02ex41.AppSymAnti02ex41.class),
      "config/memcomp/SymAnti02ex41/SymAnti02ex41_xhn.xml");
  }
  
  /**
   * gist
   * @param wbId XholonWorkbook ID (ex: "gist3377945"  "gist3457105,crn_1987_6_7_csh.xml")
   */
  private void gist(String withGui, String wbId) {
    IApplication app = (IApplication)GWT.create(org.primordion.xholon.app.Chameleon.AppChameleon.class);
    String[] wbStr = wbId.split(",");
    app.setWorkbookId(wbStr[0]);
    if (wbStr.length > 1) {
      app.setWorkbookFileName(wbStr[1]);
    }
    xhn(withGui, app, null);
  }
  
  /**
   * lstr
   * @param wbId XholonWorkbook ID (ex: "lstr3377945")
   */
  private void lstr(String withGui, String wbId) {
    IApplication app = (IApplication)GWT.create(org.primordion.xholon.app.Chameleon.AppChameleon.class);
    app.setWorkbookId(wbId);
    xhn(withGui, app, null);
  }
  
  /**
   * xhn
   */
  private void xhn(String withGui, IApplication app, String defaultConfigFileName) {
    GWT.log("\nrunning " + app.getClass().getName() + " ...");
    String hostPageBaseURL = GWT.getHostPageBaseURL();
    app.setHostPageBaseURL(hostPageBaseURL);
    app.setXincludePath(hostPageBaseURL + "config/_common/");
    
    String configFileName = defaultConfigFileName == null ? null : hostPageBaseURL + defaultConfigFileName;
    app.setConfigFileName(configFileName);
    
    if (GUI_SAVE.equals(withGui)) {
      JavaApp2Workbook app2Wb = new JavaApp2Workbook();
      app2Wb.save(configFileName, app);
      return;
    }
    else if (GUI_EDIT.equals(withGui)) {
      JavaApp2Workbook app2Wb = new JavaApp2Workbook();
      app2Wb.edit(configFileName, app);
      return;
    }
    
    XholonJsApi.exportTopLevelApi(app);
    XholonJsApi.exportIXholonApi((IXholon)app);
    
    // use localStorage version of Java-based app, if such a version is available
    if (src == null) {
      // this is a Java-based Xholon app
      XholonWorkbookBundle workbookBundle = new XholonWorkbookBundle(appName);
      if (workbookBundle.exists()) {
        // save XholonWorkbookBundle to Application
        app.setWorkbookBundle(workbookBundle);
      }
    }
    
    if (GUI_NONE.equals(withGui)) {
      app.runApp();
    }
    else if (GUI_D3CIRCLEPACK.equals(withGui)) {
      IXholonGui xholonGui = new XholonGuiD3CirclePack();
      xholonGui.execModel(configFileName, app);
    }
    else { // GUI_CLASSIC
      IXholonGui xholonGui = new XholonGuiClassic();
      xholonGui.execModel(configFileName, app);
    }
  }
	
}
