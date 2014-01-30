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

import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.TextResource;

import org.client.RCConfig;
import org.primordion.user.app.helloworldjnlp.Resources;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.IXholonGui;
import org.primordion.xholon.io.XholonGuiClassic;
import org.primordion.xholon.io.XholonGuiD3CirclePack;
//import org.primordion.xholon.util.StringHelper;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 15, 2013)
 */
public class Xholon implements EntryPoint {
  
  // GUI options
  private static final String GUI_CLASSIC      = "clsc";
  private static final String GUI_D3CIRCLEPACK = "d3cp";
  private static final String GUI_NONE         = "none";
  
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
    
    // test StringHelper
    /*
    double d = 12345.6789;
    StringHelper.format("%.0f", d);
    StringHelper.format("%.1f", d);
    StringHelper.format("%.2f", d);
    StringHelper.format("%.3f", d);
    StringHelper.format("%.9f", d);
    StringHelper.format("junk", d);
    
    float f = 12345.6789f;
    StringHelper.format("%.0f", f);
    StringHelper.format("%.1f", f);
    StringHelper.format("%.2f", f);
    StringHelper.format("%.3f", f);
    StringHelper.format("%.9f", f);
    StringHelper.format("junk", f);
    
    StringHelper.format("%s", "This is a String.");
    StringHelper.format("%d", 123);
    StringHelper.format("%c", 'x');
    StringHelper.format("%b", false);
    //StringHelper.format("%s", new StringTokenizer("junk"));
    
    StringHelper.format("%s|%s|%s", "One", "Two", "Three");
    StringHelper.format("%s %s %s", "One", "Two", "Three");
    */
    
    //genSystemMechanismClassInfo();
    
    testXholon(resultField);
    
    //testResources();
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
    String appName = Location.getParameter("app");
    if (appName != null) {
      // convert "+" back to " ", etc.
      appName = URL.decodeQueryString(appName);
    }
    String src = Location.getParameter("src");
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
    if ("Chameleon".equals(appName)) {chameleon(withGui);}
    else if ("Furcifer".equals(appName)) {furcifer(withGui);}
    else if ("Bestiary".equals(appName)) {bestiary(withGui);}
    else if ("HelloWorld".equals(appName)) {testAppHelloWorld(withGui);}
    else if ("PingPong".equals(appName)) {testAppPingPong(withGui);}
    else if ("Cell".equals(appName)) {testCell(withGui);}
    else if ("Life".equals(appName)) {life(withGui);}
    else if ("Life3d".equals(appName)) {life3d(withGui);}
    else if ("Autop".equals(appName)) {autop(withGui);}
    else if ("GameOfLife".equals(appName)) {testAppGameOfLife(withGui);}
    else if ("GameOfLife_Big".equals(appName)) {testAppGameOfLife_Big(withGui);}
    else if ("GameOfLife_Huge".equals(appName)) {testAppGameOfLife_Huge(withGui);}
    else if ("HelloWorld_TestTime".equals(appName)) {testHelloWorld_TestTime(withGui);}
    else if ("ca_1dSimple".equals(appName)) {testca_1dSimple(withGui);}
    else if ("Generic".equals(appName)) {testGeneric(withGui);}
    else if ("climatechange_model04".equals(appName)) {testclimatechange_model04(withGui);}
    else if ("climatechange_carboncycle03".equals(appName)) {testclimatechange_carboncycle03(withGui);}
    else if ("SpringIdol".equals(appName)) {testSpringIdol(withGui);}
    else if ("TurtleExample1".equals(appName)) {turtleExample1(withGui);}
    else if ("WolfSheepGrass".equals(appName)) {wolfSheepGrass(withGui);}
    else if ("mdcs_m2_1mp".equals(appName)) {mdcs_m2_1mp(withGui);}
    else if ("mdcs_m2_2mp".equals(appName)) {mdcs_m2_2mp(withGui);}
    else if ("igm".equals(appName)) {igm(withGui);}
    else if ("ctrnn_AdapSysLab".equals(appName)) {ctrnn_AdapSysLab(withGui);}
    else if ("MeTTTa".equals(appName)) {MeTTTa(withGui);}
    else if ("solarsystemtest".equals(appName)) {solarsystemtest(withGui);}
    else if ("Collisions".equals(appName)) {Collisions(withGui);}
    else if ("Hex".equals(appName)) {Hex(withGui);}
    else if ("SpatialGames".equals(appName)) {SpatialGames(withGui);}
    else if ("Rcs1".equals(appName)) {Rcs1(withGui);}
    else if ("Rcs2".equals(appName)) {Rcs2(withGui);}
    else if ("bigraphRG".equals(appName)) {bigraphRG(withGui);}
    else if ("beard41".equals(appName)) {beard41(withGui);}
    //else if ("risk".equals(appName)) {risk(withGui);}
    else if ("English2French".equals(appName)) {English2French(withGui);}
    else if ("XBar".equals(appName)) {XBar(withGui);}
    else if ("RavaszHnm".equals(appName)) {RavaszHnm(withGui);}
    else if ("Red".equals(appName)) {Red(withGui);}
    else if ("TweenTrees".equals(appName)) {TweenTrees(withGui);}
    else if ("WaterLogic".equals(appName)) {WaterLogic(withGui);}
    
    // Ealontro (non-Genetic Programming versions)
    else if ("AntForaging".equals(appName)) {AntForaging(withGui);}
    else if ("CartCentering".equals(appName)) {CartCentering(withGui);}
    else if ("EcjAntTrail".equals(appName)) {EcjAntTrail(withGui);}
    else if ("EcjTutorial4".equals(appName)) {EcjTutorial4(withGui);}
    
    // Agent Base Modeling (ABM) - Stupid Models
    else if ("StupidModel1".equals(appName)) {stupidModel1(withGui);}
    else if ("StupidModel2".equals(appName)) {stupidModel2(withGui);}
    else if ("StupidModel3".equals(appName)) {stupidModel3(withGui);}
    else if ("StupidModel4".equals(appName)) {stupidModel4(withGui);}
    else if ("StupidModel5".equals(appName)) {stupidModel5(withGui);}
    else if ("StupidModel5tg".equals(appName)) {stupidModel5tg(withGui);}
    else if ("StupidModel6".equals(appName)) {stupidModel6(withGui);}
    else if ("StupidModel7".equals(appName)) {stupidModel7(withGui);}
    else if ("StupidModel8".equals(appName)) {stupidModel8(withGui);}
    else if ("StupidModel9".equals(appName)) {stupidModel9(withGui);}
    else if ("StupidModel10".equals(appName)) {stupidModel10(withGui);}
    else if ("StupidModel11".equals(appName)) {stupidModel11(withGui);}
    else if ("StupidModel12".equals(appName)) {stupidModel12(withGui);}
    else if ("StupidModel13".equals(appName)) {stupidModel13(withGui);}
    else if ("StupidModel14".equals(appName)) {stupidModel14(withGui);}
    else if ("StupidModel15".equals(appName)) {stupidModel15(withGui);}
    else if ("StupidModel16".equals(appName)) {stupidModel16(withGui);}
    else if ("StupidModel16nl".equals(appName)) {stupidModel16nl(withGui);}
    
    // FSM UML MagicDraw
    else if ("TestFsm".equals(appName)) {testTestFsm(withGui);}
    else if ("TestFsmForkJoin".equals(appName)) {testTestFsmForkJoin(withGui);}
    else if ("TestFsmHistory".equals(appName)) {testTestFsmHistory(withGui);}
    else if ("TestFsmJunction".equals(appName)) {testTestFsmJunction(withGui);}
    else if ("TestFsmOrthogonal".equals(appName)) {testTestFsmOrthogonal(withGui);}
    else if ("Elevator".equals(appName)) {testElevator(withGui);}
    else if ("Elevator_ShowStates".equals(appName)) {testElevator_ShowStates(withGui);}
    
    // Petri Net
    else if ("Feinberg1".equals(appName)) {testFeinberg1(withGui);}
    else if ("azimuth_pn01".equals(appName)) {azimuth_pn01(withGui);}
    
    // dynsys
    else if ("Fibonacci".equals(appName)) {fibonacci(withGui);}
    else if ("Gravity1".equals(appName)) {gravity1(withGui);}
    else if ("Interest".equals(appName)) {interest(withGui);}
    else if ("ScheffranNActor".equals(appName)) {scheffranNActor(withGui);}
    else if ("ScheffranTwoActor".equals(appName)) {scheffranTwoActor(withGui);}
    else if ("Train".equals(appName)) {train(withGui);}
    else if ("leakybucket".equals(appName)) {leakybucket(withGui);}
    else if ("stability".equals(appName)) {stability(withGui);}
    
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
  private void chameleon(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.xholon.app.Chameleon.AppChameleon.class),
      null);
  }
  
  /**
   * Furcifer (for QUnit JavaScript unit testing)
   */
  private void furcifer(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.xholon.app.Furcifer.AppFurcifer.class),
      null);
  }
  
  /**
   * AppHelloWorld
   */
  private void testAppHelloWorld(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.helloworldjnlp.AppHelloWorld.class),
      "config/helloworldjnlp/HelloWorld_xhn.xml");
  }
  
  private void testResources() {
    Resources res = Resources.INSTANCE;
    // Using a TextResource
    //System.out.println(Resources.INSTANCE.csh().getText());
    // Using an ExternalTextResource
    try {
      res.csh().getText(new ResourceCallback<TextResource>() {
        public void onError(ResourceException e) {
          System.out.println(e);
        }
        public void onSuccess(TextResource r) {
          System.out.println(r.getText());
        }
      });
    } catch (ResourceException e) {
      System.out.println(e);
    }
    
    try {
      ((ExternalTextResource)res.getResource("csh")).getText(new ResourceCallback<TextResource>() {
        public void onError(ResourceException e) {
          System.out.println(e);
        }
        public void onSuccess(TextResource r) {
          System.out.println(r.getText());
        }
      });
    } catch (ResourceException e) {
      System.out.println(e);
    }
    
    // Using an ExternalTextResource
    // causes exception
    try {
      res.default_xhn().getText(new ResourceCallback<TextResource>() {
        public void onError(ResourceException e) {
          System.out.println(e);
        }
        public void onSuccess(TextResource r) {
          System.out.println(r.getText());
        }
      });
    } catch (ResourceException e) {
      System.out.println(e);
    }
    
    // Using an ExternalTextResource
    try {
      res.ih().getText(new ResourceCallback<TextResource>() {
        public void onError(ResourceException e) {
          System.out.println(e);
        }
        public void onSuccess(TextResource r) {
          System.out.println(r.getText());
        }
      });
    } catch (ResourceException e) {
      System.out.println(e);
    }
    //System.out.println(Resources.INSTANCE.ih().getText());
    
    //System.out.println(RCConfig.INSTANCE.XhMechanisms().getText());
    //System.out.println(((TextResource)RCConfig.INSTANCE.getResource("Control_CompositeHierarchy")).getText());
  }
  
  /**
   * AppPingPong
   */
  private void testAppPingPong(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.PingPong.AppPingPong.class),
      "config/PingPong/PingPong_xhn.xml");
  }
  
  /**
   * Cell
   */
  private void testCell(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.cellontro.app.AppCell.class),
      "config/cellontro/Cell/Cell_BioSystems_Jul03_xhn.xml");
  }
  
  /**
   * Life
   */
  private void life(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.cellontro.app.AppLife.class),
      "config/cellontro/Life/Life_xhn.xml");
  }
  
  /**
   * Life3d
   */
  private void life3d(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.cellontro.app.AppLife.class),
      "config/cellontro/Life3d/Life3d_SingleCells_xhn.xml");
  }
  
  /**
   * Autop
   */
  private void autop(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.cellontro.app.AppCellAutop.class),
      "config/cellontro/CellAutop/Autop_xhn.xml");
  }
  
  /**
   * GameOfLife
   */
  private void testAppGameOfLife(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.GameOfLife.AppGameOfLife.class),
      "config/GameOfLife/GameOfLife_xhn.xml");
  }
  
  /**
   * GameOfLife_Big
   */
  private void testAppGameOfLife_Big(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.GameOfLife.AppGameOfLife.class),
      "config/GameOfLife/GameOfLife_Big_xhn.xml");
  }
  
  /**
   * GameOfLife_Huge
   */
  private void testAppGameOfLife_Huge(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.GameOfLife.AppGameOfLife.class),
      "config/GameOfLife/GameOfLife_Huge_xhn.xml");
  }
  
  /**
   * StupidModels
   */
  private void stupidModel1(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm1.AppStupidModel1.class),
      "config/StupidModel/StupidModel1/StupidModel1_xhn.xml");
  }
  private void stupidModel2(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm2.AppStupidModel2.class),
      "config/StupidModel/StupidModel2/StupidModel2_xhn.xml");
  }
  private void stupidModel3(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm3.AppStupidModel3.class),
      "config/StupidModel/StupidModel3/StupidModel3_xhn.xml");
  }
  private void stupidModel4(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm4.AppStupidModel4.class),
      "config/StupidModel/StupidModel4/StupidModel4_xhn.xml");
  }
  private void stupidModel5(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm5.AppStupidModel5.class),
      "config/StupidModel/StupidModel5/StupidModel5_xhn.xml");
  }
  private void stupidModel5tg(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm5tg.AppStupidModel5tg.class),
      "config/StupidModel/StupidModel5tg/StupidModel5tg_xhn.xml");
  }
  private void stupidModel6(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm6.AppStupidModel6.class),
      "config/StupidModel/StupidModel6/StupidModel6_xhn.xml");
  }
  private void stupidModel7(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm7.AppStupidModel7.class),
      "config/StupidModel/StupidModel7/StupidModel7_xhn.xml");
  }
  private void stupidModel8(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm8.AppStupidModel8.class),
      "config/StupidModel/StupidModel8/StupidModel8_xhn.xml");
  }
  private void stupidModel9(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm9.AppStupidModel9.class),
      "config/StupidModel/StupidModel9/StupidModel9_xhn.xml");
  }
  private void stupidModel10(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm10.AppStupidModel10.class),
      "config/StupidModel/StupidModel10/StupidModel10_xhn.xml");
  }
  private void stupidModel11(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm11.AppStupidModel11.class),
      "config/StupidModel/StupidModel11/StupidModel11_xhn.xml");
  }
  private void stupidModel12(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm12.AppStupidModel12.class),
      "config/StupidModel/StupidModel12/StupidModel12_xhn.xml");
  }
  private void stupidModel13(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm13.AppStupidModel13.class),
      "config/StupidModel/StupidModel13/StupidModel13_xhn.xml");
  }
  private void stupidModel14(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm14.AppStupidModel14.class),
      "config/StupidModel/StupidModel14/StupidModel14_xhn.xml");
  }
  private void stupidModel15(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm15.AppStupidModel15.class),
      "config/StupidModel/StupidModel15/StupidModel15_xhn.xml");
  }
  private void stupidModel16(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm16.AppStupidModel16.class),
      "config/StupidModel/StupidModel16/StupidModel16_xhn.xml");
  }
  private void stupidModel16nl(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.StupidModel.sm16nl.AppStupidModel16nl.class),
      "config/StupidModel/StupidModel16nl/StupidModel16nl_xhn.xml");
  }
  
  /**
   * HelloWorld_TestTime
   */
  private void testHelloWorld_TestTime(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.HelloWorld_TestTime.AppHelloWorld_TestTime.class),
      "config/HelloWorld/HelloWorld_TestTime_xhn.xml");
  }
  
  /**
   * ca 1dSimple
   */
  private void testca_1dSimple(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.Simple1d.App1dSimple.class),
      "config/ca/1dSimple/1dSimple_xhn.xml");
  }
  
  /**
   * Generic
   */
  private void testGeneric(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.Generic.AppGeneric.class),
      "config/Generic/Generic_xhn.xml");
  }
  
  /**
   * testclimatechange_model04
   */
  private void testclimatechange_model04(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.climatechange.model04.Appmodel04.class),
      "config/climatechange/model04/_xhn.xml");
  }
  
  /**
   * testclimatechange_carboncycle03
   */
  private void testclimatechange_carboncycle03(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.climatechange.carboncycle03.Appcarboncycle03.class),
      "config/climatechange/carboncycle03/_xhn.xml");
  }
  
  /**
   * SpringIdol
   */
  private void testSpringIdol(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.SpringIdol.AppSpringIdol.class),
      "config/SpringIdol/SpringIdol_xhn.xml");
  }
  
  /**
   * TestFsm
   */
  private void testTestFsm(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsm.AppTestFsm.class),
      "config/xmiapps/TestFsm/TestFsm_xhn.xml");
  }
  
  /**
   * TestFsmForkJoin
   */
  private void testTestFsmForkJoin(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsmForkJoin.AppTestFsmForkJoin.class),
      "config/xmiapps/TestFsmForkJoin/TestFsmForkJoin_xhn.xml");
  }
  
  /**
   * TestFsmHistory
   */
  private void testTestFsmHistory(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsmHistory.AppTestFsmHistory.class),
      "config/xmiapps/TestFsmHistory/TestFsmHistory_xhn.xml");
  }
  
  /**
   * TestFsmJunction
   */
  private void testTestFsmJunction(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsmJunction.AppTestFsmJunction.class),
      "config/xmiapps/TestFsmJunction/TestFsmJunction_xhn.xml");
  }
  
  /**
   * TestFsmOrthogonal
   */
  private void testTestFsmOrthogonal(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.TestFsmOrthogonal.AppTestFsmOrthogonal.class),
      "config/xmiapps/TestFsmOrthogonal/TestFsmOrthogonal_xhn.xml");
  }
  
  /**
   * Elevator
   */
  private void testElevator(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.Elevator.AppElevator.class),
      "config/xmiapps/Elevator/Elevator_xhn.xml");
  }
  
  /**
   * Elevator_ShowStates
   */
  private void testElevator_ShowStates(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.xmiapps.Elevator.AppElevator_ShowStates.class),
      "config/xmiapps/Elevator/Elevator_ShowStates_xhn.xml");
  }
  
  /**
   * Feinberg1
   */
  private void testFeinberg1(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.petrinet.feinberg1.Appfeinberg1.class),
      null);
  }
  
  /**
   * Bestiary
   */
  private void bestiary(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.Bestiary.AppBestiary.class),
      null);
  }
  
  /**
   * TurtleExample1
   */
  private void turtleExample1(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.TurtleExample1.AppTurtleExample1.class),
      "config/user/TurtleExample1/TurtleExample1_xhn.xml");
  }
  
  /**
   * WolfSheepGrass
   */
  private void wolfSheepGrass(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.WolfSheepGrass.AppWolfSheepGrass.class),
      "config/user/WolfSheepGrass/WolfSheepGrass_xhn.xml");
  }
  
  // dynsys
  
  /**
   * Fibonacci
   */
  private void fibonacci(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppFibonacci.class),
      "config/dynsys/Fibonacci/Fibonacci_xhn.xml");
  }

  /**
   * Gravity1
   */
  private void gravity1(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppGravity1.class),
      "config/dynsys/Gravity1/Gravity1_xhn.xml");
  }

  /**
   * Interest
   */
  private void interest(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppInterest.class),
      "config/dynsys/Interest/Interest_xhn.xml");
  }

  /**
   * ScheffranNActor
   */
  private void scheffranNActor(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppScheffranNActor.class),
      "config/dynsys/ScheffranTwoActor/ScheffranNActor_xhn.xml");
  }

  /**
   * ScheffranTwoActor
   */
  private void scheffranTwoActor(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.dynsys.app.AppScheffranTwoActor.class),
      "config/dynsys/ScheffranTwoActor/ScheffranTwoActor_xhn.xml");
  }

  /**
   * Train
   */
  private void train(String withGui) {
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
   * azimuth_pn01
   */
  private void azimuth_pn01(String withGui) {
    xhn(withGui, (IApplication)GWT.create(org.primordion.user.app.azimuth.pn01.Apppn.class),
      null);
  }
  
  /**
   * ctrnn_AdapSysLab
   */
  private void ctrnn_AdapSysLab(String withGui) {
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
   * SpatialGames
   */
  private void SpatialGames(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.SpatialGames.App09SpatialGames.class),
      "config/SpatialGames/SpatialGames1_xhn.xml");
  }
  
  /**
   * Rcs 1
   */
  private void Rcs1(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.rcs.Rcs1.AppRcs1.class),
      "config/Rcs/Rcs1_xhn.xml");
  }
  
  /**
   * Rcs 2
   */
  private void Rcs2(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.rcs.Rcs2.AppRcs2.class),
      "config/Rcs/Rcs2_xhn.xml");
  }
  
  /**
   * bigraphRG
   */
  private void bigraphRG(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.bigraph.roomsghosts.Approomsghosts.class),
      null);
  }
  
  /**
   * beard41
   */
  private void beard41(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.xmiapps.beard41.AppBeard2005_UML_Xholon_Step4_v1.class),
      "config/xmiapps/Beard2005_UML_Xholon_Step4_v1/Beard2005_UML_Xholon_Step4_v1_xhn.xml");
  }
  
  /**
   * risk
   */
  /*private void risk(String withGui) {
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
   * XBar
   */
  private void XBar(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.user.app.XBar.AppXBar_ex1.class),
      "config/user/XBar/XBar_xhn.xml");
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
   * EcjAntTrail
   */
  private void EcjAntTrail(String withGui) {
    xhn(withGui,
      (IApplication)GWT.create(org.primordion.ealontro.app.EcjAntTrail.AppAntTrail.class),
      "config/ealontro/EcjAntTrail/AntTrail_1_xhn.xml");
  }
  
  /**
   * EcjTutorial4
   */
  private void EcjTutorial4(String withGui) {
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
    
    XholonJsApi.exportTopLevelApi(app);
    XholonJsApi.exportIXholonApi((IXholon)app);
    
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
