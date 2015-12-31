/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

package org.primordion.ef.program;

import java.util.Date;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in Scratch 2 format.
 * 
 * To prepare a project that can be uploaded to the Scratch editor:
 * - create a new local project folder, for example "myHelloWorld"
 * - save the exported content to the project folder, in a file called "project.json"
 * - include properly named .png .svg .wav files in the project folder
 *  - the IDs in project.json, and the file names, need to line up with each other
 *  - the md5 values also need to line up
 * - create a .zip file from the project folder
 * - change the file extension from ".zip" to ".sb2"
 * - using the online Scratch editor, create a new project by selecting "Upload from your computer" in the File menu
 * 
 * Notes:
 * - if no sprites are specified in project.json, Scratch will insert the default sprite
 * - it is not necessary to fill in "info" section in project.json
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://scratch.mit.edu/">Scratch website</a>
 * @since 0.9.1 (Created on December 27, 2015)
 */
@SuppressWarnings("serial")
public class Xholon2Scratch extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/sb2/"; // JSON inside sb2 zip file
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Template to use when writing out node names. */
  //protected String nameTemplate = "r:C^^^";
  protected String nameTemplate = "^^C^^^"; // don't include role name
  
  // IDs used in the .room file; these values need to be incremented each time they are used
  protected int nextBaseLayerID = 1;
  protected int nextPenLayerID = 0;
  protected int nextSoundID = 0;
  
  // default stage sounds values
  protected String ssSoundNameDefault = "pop";
  protected String ssMd5Default = "83a9787d4cb6f3b7632b4ddfebf74367.wav";
  protected int ssSampleCountDefault = 258;
  protected int ssRateDefault = 11025;
  
  // default stage costumes values
  protected String scCostumeNameDefault = "backdrop1";
  protected String scBaseLayerMD5Default = "";
  protected int scBitmapResolutionDefault = 1;
  protected int scRotationCenterXDefault = 240;
  protected int scRotationCenterYDefault = 180;
  
  /**
   * Constructor.
   */
  public Xholon2Scratch() {}
  
  @Override
  public String getVal_String() {
    return sb.toString();
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String mmFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (mmFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".json";
    }
    else {
      this.outFileName = mmFileName;
    }
    this.modelName = modelName;
    this.root = root;
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    sb = new StringBuilder();
    nextBaseLayerID = 1;
    nextPenLayerID = 0;
    nextSoundID = 0;
    makeDefaults();
    writeStage(sb);
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Make Scratch default values.
   */
  protected void makeDefaults() {
    String[] stageSoundsDefaultsArr = getStageSoundsDefaults().split(",");
    switch (stageSoundsDefaultsArr.length) {
      // the case statements fall through (no break;)
      case 4: ssRateDefault = Integer.parseInt(stageSoundsDefaultsArr[3]);
      case 3: ssSampleCountDefault = Integer.parseInt(stageSoundsDefaultsArr[2]);
      case 2: ssMd5Default = stageSoundsDefaultsArr[1];
      case 1: ssSoundNameDefault = stageSoundsDefaultsArr[0];
      default: break;
    }
    String[] stageCostumesDefaultsArr = getStageCostumesDefaults().split(",");
    switch (stageCostumesDefaultsArr.length) {
      // the case statements fall through (no break;)
      case 5: scRotationCenterYDefault = Integer.parseInt(stageCostumesDefaultsArr[4]);
      case 4: scRotationCenterXDefault = Integer.parseInt(stageCostumesDefaultsArr[3]);
      case 3: scBitmapResolutionDefault = Integer.parseInt(stageCostumesDefaultsArr[2]);
      case 2: scBaseLayerMD5Default = stageCostumesDefaultsArr[1];
      case 1: scCostumeNameDefault = stageCostumesDefaultsArr[0];
      default: break;
    }
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeNode(IXholon node, int level) {
    // never show state machine nodes
    if (node.getXhcId() == CeStateMachineEntity.StateMachineCE) {
      return;
    }
    writeSprite(node);
    if (node.hasChildNodes()) {
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, level+1);
        childNode = childNode.getNextSibling();
        if (childNode != null) {
          
        }
      }
      
    }
  }
  
  protected void writeStage(StringBuilder sbLocal) {
    sbLocal
    .append("{\n")
    .append("  \"objName\": \"Stage\",\n")
    .append(writeStageSounds(new StringBuilder()))
    .append(writeStageCostumes(new StringBuilder()))
    .append("  \"currentCostumeIndex\": 0,\n")
    .append("  \"penLayerMD5\": \"5c81a336fab8be57adc039a8a2b33ca9.png\",\n")
    .append("  \"penLayerID\": 0,\n")
    .append("  \"tempoBPM\": 60,\n")
    .append("  \"videoAlpha\": 0.5,\n")
    .append("  \"children\": [")
    //sprites
    ;
    writeNode(root, 0); // root is level 0
    sbLocal
    .append("],\n")
    //info
    .append("}")
    ;
  }
  
  protected void writeSprite(IXholon node) {
    node.println(node.getName());
  }
  
  protected String writeStageSounds(StringBuilder sbLocal) {
    if (!this.isShouldShowSounds()) {return "";}
    sbLocal
    .append("  \"sounds\": [");
    writeSound(ssSoundNameDefault, ssMd5Default, ssSampleCountDefault, ssRateDefault, sbLocal);
    sbLocal
    .append("],\n")
    ;
    return sbLocal.toString();
  }
  
  protected void writeSound(String soundName, String md5, int sampleCount, int rate, StringBuilder sbLocal) {
    sbLocal
    .append("{\n")
    .append("    \"soundName\": \"").append(soundName).append("\",\n")
    .append("    \"soundID\": ").append(nextSoundID++).append(",\n")
    .append("    \"md5\": \"").append(md5).append("\",\n")
    .append("    \"sampleCount\":").append(sampleCount).append(",\n")
    .append("    \"rate\":").append(rate).append(",\n")
    .append("    \"format\": \"\"\n")
    .append("  }")
    ;
  }
  
  protected String writeStageCostumes(StringBuilder sbLocal) {
    sbLocal
    .append("  \"costumes\": [");
    writeCostume(scCostumeNameDefault, scBaseLayerMD5Default, scBitmapResolutionDefault, scRotationCenterXDefault, scRotationCenterYDefault, sbLocal);
    sbLocal
    .append("],\n")
    ;
    return sbLocal.toString();
  }
  
  protected void writeCostume(String costumeName, String baseLayerMD5, int bitmapResolution, int rotationCenterX, int rotationCenterY, StringBuilder sbLocal) {
    sbLocal
    .append("{\n")
    .append("    \"costumeName\": \"").append(costumeName).append("\",\n")
    .append("    \"baseLayerID\": ").append(nextBaseLayerID++).append(",\n")
    .append("    \"baseLayerMD5\": \"").append(baseLayerMD5).append("\",\n")
    .append("    \"bitmapResolution\":").append(bitmapResolution).append(",\n")
    .append("    \"rotationCenterX\":").append(rotationCenterX).append(",\n")
    .append("    \"rotationCenterY\":").append(rotationCenterY).append(",\n")
    .append("    \"format\": \"\"\n")
    .append("  }")
    ;
  }
  
  public String getOutFileName() {
    return outFileName;
  }

  public void setOutFileName(String outFileName) {
    this.outFileName = outFileName;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public IXholon getRoot() {
    return root;
  }

  public void setRoot(IXholon root) {
    this.root = root;
  }

  public String getNameTemplate() {
    return nameTemplate;
  }

  public void setNameTemplate(String nameTemplate) {
    this.nameTemplate = nameTemplate;
  }

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }

  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.stageSoundsDefaults = "pop,83a9787d4cb6f3b7632b4ddfebf74367.wav,258,11025";
    p.stageCostumesDefaults = "backdrop1,739b5e2a2435f6e1ec2993791b423146.png,1,240,180";
    p.spriteDefaults = "";
    p.shouldShowSounds = true;
    this.efParams = p;
  }-*/;

  public native String getStageSoundsDefaults() /*-{return this.efParams.stageSoundsDefaults;}-*/;
  //public native void setStageSoundsDefaults(String stageSoundsDefaults) /*-{this.efParams.stageSoundsDefaults = stageSoundsDefaults;}-*/;

  public native String getStageCostumesDefaults() /*-{return this.efParams.stageCostumesDefaults;}-*/;
  //public native void setStageCostumessDefaults(String stageCostumesDefaults) /*-{this.efParams.stageCostumesDefaults = stageCostumesDefaults;}-*/;

  /** Whether or not to show Stage and Sprite sounds. */
  public native boolean isShouldShowSounds() /*-{return this.efParams.shouldShowSounds;}-*/;
  //public native void setShouldShowSounds(boolean shouldShowSounds) /*-{this.efParams.shouldShowSounds = shouldShowSounds;}-*/;
  
}
