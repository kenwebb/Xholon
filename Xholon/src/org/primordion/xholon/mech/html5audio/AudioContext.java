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
import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.Xholon;

/**
 * This class is part of the Xholon HTML5 Audio Service.
 * It encapsulates an instance of AudioContext as defined in the Web Audio API for web browsers.
 * <p>"This interface represents a set of AudioNode objects and their connections. It allows for arbitrary routing of signals to the AudioDestinationNode (what the user ultimately hears). Nodes are created from the context and are then connected together. In most use cases, only a single AudioContext is used per document."</p>
 * <p>"This specification describes a high-level JavaScript API for processing and synthesizing audio in web applications. The primary paradigm is of an audio routing graph, where a number of AudioNode objects are connected together to define the overall audio rendering." see Web Audio API</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://webaudio.github.io/web-audio-api/">Web Audio API</a>
 * @since 0.9.1 (Created on August 18, 2014)
 */
public class AudioContext extends Xholon {
  
  /**
   * roleName with a default value
   */
  protected String roleName = "context";
  
  protected IXPath xPath = null;
  
  @Override
  public Object getVal_Object() {return getContext();}
  @Override
  public void setVal_Object(Object obj) {setContext((JavaScriptObject)obj);}
  
  @Override
  public void setRoleName(String roleName) {this.roleName = roleName;}
  @Override
  public String getRoleName() {return roleName;}
  
  public IXPath xPath() {
    return xPath;
  }
  
  /**
   * Set instance of the HTML5 AudioContext class.
   */
  public native void setContext(JavaScriptObject cntxt) /*-{
    this.context = cntxt;
  }-*/;
  
  /**
   * Get instance of the HTML5 AudioContext class.
   */
  public native JavaScriptObject getContext() /*-{
    return this.context;
  }-*/;
  
  @Override
  public void postConfigure() {
    this.consoleLog("AudioContext postConfigure() starting ...");
    xPath = this.getXPath();
    this.consoleLog("AudioContext postConfigure() 1");
    setContext(newContext());
    this.consoleLog("AudioContext postConfigure() 2");
    super.postConfigure();
  }
  
  protected native JavaScriptObject newContext() /*-{
    try {
      $wnd.AudioContext = $wnd.AudioContext || $wnd.webkitAudioContext;
    }
    catch(e) {
      $wnd.alert('Web Audio API is not supported in this browser');
    }
    return new $wnd.AudioContext();
  }-*/;
  
  //readonly attribute AudioDestinationNode destination;
  public native JavaScriptObject getDestination() /*-{
    return this.context.destination;
  }-*/;
  
  //readonly attribute float sampleRate;
  public native float getSampleRate() /*-{
    return this.context.sampleRate;
  }-*/;
  
  //readonly attribute double currentTime;
  public native double getCurrentTime() /*-{
    return this.context.currentTime;
  }-*/;
  
  //readonly attribute AudioListener listener;
  public native JavaScriptObject getListener() /*-{
    return this.context.listener;
  }-*/;
  
  //AudioBuffer createBuffer (unsigned long numberOfChannels, unsigned long length, float sampleRate);
  
  //Promise<AudioBuffer> decodeAudioData (ArrayBuffer audioData, optional DecodeSuccessCallback successCallback, optional DecodeErrorCallback errorCallback);
  
  //AudioBufferSourceNode createBufferSource ();
  public native JavaScriptObject createBufferSource() /*-{
    $wnd.console.log("createBufferSource() start");
    $wnd.console.log(this.context);
    return this.context.createBufferSource();
  }-*/;
  
  //MediaElementAudioSourceNode createMediaElementSource (HTMLMediaElement mediaElement);
  
  //MediaStreamAudioSourceNode createMediaStreamSource (MediaStream mediaStream);
  
  //MediaStreamAudioDestinationNode createMediaStreamDestination ();
  public native JavaScriptObject createMediaStreamDestination() /*-{
    return this.context.createMediaStreamDestination();
  }-*/;
  
  //ScriptProcessorNode createScriptProcessor (optional unsigned long bufferSize = 0 , optional unsigned long numberOfInputChannels = 2 , optional unsigned long numberOfOutputChannels = 2 );
  public native JavaScriptObject createScriptProcessor() /*-{
    return this.context.createScriptProcessor();
  }-*/;
  
  //AnalyserNode createAnalyser ();
  public native JavaScriptObject createAnalyser() /*-{
    return this.context.createAnalyser();
  }-*/;
  
  //GainNode createGain ();
  public native JavaScriptObject createGain() /*-{
    return this.context.createGain();
  }-*/;
  
  //DelayNode createDelay (optional double maxDelayTime = 1.0 );
  public native JavaScriptObject createDelay() /*-{
    return this.context.createDelay();
  }-*/;
  
  //BiquadFilterNode createBiquadFilter ();
  public native JavaScriptObject createBiquadFilter() /*-{
    return this.context.createBiquadFilter();
  }-*/;
  
  //WaveShaperNode createWaveShaper ();
  public native JavaScriptObject createWaveShaper() /*-{
    return this.context.createWaveShaper();
  }-*/;
  
  //PannerNode createPanner ();
  public native JavaScriptObject createPanner() /*-{
    return this.context.createPanner();
  }-*/;
  
  //ConvolverNode createConvolver ();
  public native JavaScriptObject createConvolver() /*-{
    return this.context.createConvolver();
  }-*/;
  
  //ChannelSplitterNode createChannelSplitter (optional unsigned long numberOfOutputs = 6 );
  public native JavaScriptObject createChannelSplitter() /*-{
    return this.context.createChannelSplitter();
  }-*/;
  
  //ChannelMergerNode createChannelMerger (optional unsigned long numberOfInputs = 6 );
  public native JavaScriptObject createChannelMerger() /*-{
    return this.context.createChannelMerger();
  }-*/;
  
  //DynamicsCompressorNode createDynamicsCompressor ();
  public native JavaScriptObject createDynamicsCompressor() /*-{
    return this.context.createDynamicsCompressor();
  }-*/;
  
  //OscillatorNode createOscillator ();
  public native JavaScriptObject createOscillator() /*-{
    return this.context.createOscillator();
  }-*/;
  
  //PeriodicWave createPeriodicWave (Float32Array real, Float32Array imag);
  
}

