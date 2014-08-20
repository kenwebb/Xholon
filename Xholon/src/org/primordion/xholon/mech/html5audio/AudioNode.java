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

package org.primordion.xholon.mech.html5audio;

import com.google.gwt.core.client.JavaScriptObject;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * This class is part of the Xholon HTML5 Audio Service.
 * It encapsulates an instance of an AudioNode subclass as defined in the Web Audio API for web browsers.
 * All AudioNode instances are direct children of an AudioContext node.
 * <p>"AudioNodes are the building blocks of an AudioContext. This interface represents audio sources, the audio destination, and intermediate processing modules. These modules can be connected together to form processing graphs for rendering audio to the audio hardware. Each node can have inputs and/or outputs. A source node has no inputs and a single output. An AudioDestinationNode has one input and no outputs and represents the final destination to the audio hardware. Most processing nodes such as filters will have one input and one output. Each type of AudioNode differs in the details of how it processes or synthesizes audio. But, in general, AudioNodes will process its inputs (if it has any), and generate audio for its outputs (if it has any)."</p>
 * <p>"This specification describes a high-level JavaScript API for processing and synthesizing audio in web applications. The primary paradigm is of an audio routing graph, where a number of AudioNode objects are connected together to define the overall audio rendering." see Web Audio API</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://webaudio.github.io/web-audio-api/">Web Audio API</a>
 * @since 0.9.1 (Created on August 18, 2014)
 */
public class AudioNode extends XholonWithPorts {
  
  /**
   * Max number of AudioNode connections and Xholon ports.
   * This is a security precaution.
   */
  protected final static int MAX_CONNECT = 20;
  
  /**
   * Max number of AudioNode and Xholon attributes.
   * This is a security precaution.
   */
  protected final static int MAX_ATTRS = 20;
  
  /**
   * roleName
   */
  private String roleName = null;
  
  /**
   * Instance of a HTML5 AudioNode subclass.
   */
  //private JavaScriptObject audioNode = null;
  
  /**
   * A comma-separated list of roleNames of other AudioNodes that this AudioNode should connect to.
   * Examples as XML attribute:
   *   connect="dry2,wet2"
   *   connect="reverb"
   */
  private String connect = null;
  
  /**
   * A comma-separated list of attributes of the underlying AudioNode.
   * examples as XML attribute:
   *   attrs="buffer:manTalkingBuffer,start(0)"
   *   attrs="frequency.value:440,start(0)"
   */
  private String[] attrs = null;
  
  @Override
  public Object getVal_Object() {return getAudioNode();}
  
  @Override
  public void setVal_Object(Object obj) {setAudioNode((JavaScriptObject)obj);}
  
  @Override
  public void setRoleName(String roleName) {this.roleName = roleName;}
  
  @Override
  public String getRoleName() {return roleName;}
  
  public native void setAudioNode(JavaScriptObject audioNode) /*-{
    this.audioNode = audioNode;
  }-*/;
  
  public native JavaScriptObject getAudioNode() /*-{
    return this.audioNode;
  }-*/;
  
  @Override
  public void postConfigure() {
    this.consoleLog("AudioNode postConfigure() starting ...");
    postConfigure(getXhcName());
    super.postConfigure();
  }
  
  /**
   * Do postConfigure details.
   * @param xhcName Name of the XholonClass.
   */
  protected void postConfigure(String xhcName) {
    this.consoleLog("AudioNode " + xhcName);
    AudioContext pnode = (AudioContext)parentNode;
    this.consoleLog(this.attrs);
    connect();
    switch(xhcName) {
    case "AudioBufferSourceNode":
      this.consoleLog("case AudioBufferSourceNode start");
      this.consoleLog(parentNode);
      this.consoleLog(parentNode.getName());
      setAudioNode(pnode.createBufferSource());
      this.consoleLog(getAudioNode());
      this.consoleLog("case AudioBufferSourceNode end");
      break;
    case "MediaElementAudioSourceNode":
      //setAudioNode(pnode.create());
      break;
    case "MediaStreamAudioSourceNode":
      //setAudioNode(pnode.create());
      break;
    case "MediaStreamAudioDestinationNode":
      setAudioNode(pnode.createMediaStreamDestination());
      break;
    case "ScriptProcessorNode":
      setAudioNode(pnode.createScriptProcessor());
      break;
    case "GainNode":
      setAudioNode(pnode.createGain());
      break;
    case "BiquadFilterNode":
      setAudioNode(pnode.createBiquadFilter());
      break;
    case "DelayNode":
      setAudioNode(pnode.createDelay());
      break;
    case "PannerNode":
      setAudioNode(pnode.createPanner());
      break;
    case "ConvolverNode":
      setAudioNode(pnode.createConvolver());
      break;
    case "AnalyserNode":
      setAudioNode(pnode.createAnalyser());
      break;
    case "ChannelSplitterNode":
      setAudioNode(pnode.createChannelSplitter());
      break;
    case "ChannelMergerNode":
      setAudioNode(pnode.createChannelMerger());
      break;
    case "DynamicsCompressorNode":
      setAudioNode(pnode.createDynamicsCompressor());
      break;
    case "WaveShaperNode":
      setAudioNode(pnode.createWaveShaper());
      break;
    case "OscillatorNode":
      setAudioNode(pnode.createOscillator());
      break;
    case "AudioDestinationNode":
      // this node represents context.destination
      setAudioNode(pnode.getDestination());
      break;
    default:
      break;
    }
  }
  
  /**
   * Connect ports.
   */
  protected void connect() {
    this.consoleLog("connect() start ...");
    this.consoleLog(this.connect);
    if (this.connect != null) {
      String[] connectArr = this.connect.split(",", MAX_CONNECT);
      this.consoleLog(connectArr);
      for (int i = 0; i < connectArr.length; i++) {
        this.consoleLog(connectArr[i]);
        this.consoleLog(this.parentNode);
        this.consoleLog(this.parentNode.getClass().getName());
        //if ("destination".equals(connectArr[i])) {
          // TODO pnode.getDestination() ?
        //}
        //else {
          this.setPort(i, ((AudioContext)this.parentNode).xPath()
            .evaluate("*[@roleName='" + connectArr[i] + "']", this.parentNode));
        //}
      }
    }
    this.consoleLog("connect() end");
  }
  
  /**
   * Handle attributes.
   */
  //protected void attrs() {
  //  consoleLog(this.attrs);
  //}
  
  /**
   * Push Xholon attributes and ports down to the Web Audio API nodes.
   */
  protected void initAudioNode() {
    int i;
    for (i = 0; i < attrs.length; i++) {
      setAudioNodeAttr(attrs[i]);
    }
    for (i = 0; i < port.length; i++) {
			if (port[i] != null) {
				connectToAudioNode((JavaScriptObject)port[i].getVal_Object());
			}
		}
  }
  
  protected void setAudioNodeAttr(String attr) {
    if (attr.indexOf("(") == -1) {
      // this is a Web Audi API attribute (ex: "frequency.value:440" or "type:square" )
      String token[] = attr.split(":");
      setAudioNodeAttr(token[0], token[1]);
    }
    else {
      // this is a Web Audi API method (ex: "start(0)" )
      // I'm using inline script instead
    }
  }
  
  protected native void setAudioNodeAttr(String attrName, String attrVal) /*-{
    var attrNameArr = attrName.split(".");
    if (attrNameArr.length == 1) {
      this.audioNode[attrName] = attrVal;
    }
    else if (attrNameArr.length == 2) {
      this.audioNode[attrNameArr[0]][attrNameArr[1]] = attrVal;
    }
  }-*/;
  
  /**
   * Connect to another Web Audio API node.
   * The source of the connection is this node.
   * @param dest The destination of the connection.
   */
  protected native void connectToAudioNode(JavaScriptObject dest) /*-{
    this.audioNode.connect(dest);
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("connect".equals(attrName)) {
      this.connect = attrVal;
    }
    else if ("attrs".equals(attrName) && (attrVal.length() > 0)) {
      this.attrs = attrVal.split(",", MAX_ATTRS);
    }
    return 0;
  }
  
  @Override
	public String toString() {
	  String outStr = getName();
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		return outStr;
	}
	
}

