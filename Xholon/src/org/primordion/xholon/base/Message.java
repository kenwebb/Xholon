/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.primordion.xholon.app.IApplication;

//import org.primordion.xholon.base.transferobject.MessageSenderReceiver;

/**
 * <p>ROOM/UML2 message.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Aug 17, 2005)
 */
public class Message extends Xholon implements IMessage, Serializable {

	private static final long serialVersionUID = -837212903905211049L;

	// constants
	/**
	 * Default index of a replicated receiver port.
	 */
	protected static final int DEFAULT_INDEX = 0;
	
	/** An identifier for this message. */
	protected int signal;
	
	/** Any data that may be transmitted with the message. */
	protected Object data;
	
	/** Which Xholon instance sent the message. */
	protected transient IXholon sender;
	
	/** Which Xholon instance should receive the message. */
	protected transient IXholon receiver;
	
	/** Which index of a replicated port the msg is received at; zero-indexed. */
	protected transient int index;
	
	/**
	 * Constructor.
	 * @param signal An identifier for this message.
	 * @param data Any data that may be transmitted with the message.
	 * @param sender Which Xholon instance sent the message.
	 * @param receiver Which Xholon instance should receive the message.
	 */
	public Message(int signal, Object data, IXholon sender, IXholon receiver) {
		super();
		this.signal   = signal;
		this.data     = data;
		this.sender   = sender;
		this.receiver = receiver;
		this.index    = DEFAULT_INDEX;
	}
	
	/**
	 * Constructor.
	 * @param signal An identifier for this message.
	 * @param data Any data that may be transmitted with the message.
	 * @param sender Which Xholon instance sent the message.
	 * @param receiver Which Xholon instance should receive the message.
	 * @param index The index of the replicated receiver port.
	 */
	public Message(int signal, Object data, IXholon sender, IXholon receiver, int index) {
		super();
		this.signal   = signal;
		this.data     = data;
		this.sender   = sender;
		this.receiver = receiver;
		this.index    = index;
	}
	
	/**
	 * Constructor.
	 * @param msg An existing message.
	 */
	public Message(IMessage msg) {
		super();
		this.signal   = msg.getSignal();
		this.data     = msg.getData();
		this.sender   = msg.getSender();
		this.receiver = msg.getReceiver();
		this.index    = msg.getIndex();
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#getData()
	 */
	public Object getData() {
		return data;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#setData(java.lang.Object)
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#getReceiver()
	 */
	public IXholon getReceiver() {
		return receiver;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#setReceiver(org.primordion.xholon.base.IXholon)
	 */
	public void setReceiver(IXholon receiver) {
		this.receiver = receiver;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#getSender()
	 */
	public IXholon getSender() {
		return sender;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#setSender(org.primordion.xholon.base.IXholon)
	 */
	public void setSender(IXholon sender) {
		this.sender = sender;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#getSignal()
	 */
	public int getSignal() {
		return signal;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#setSignal(int)
	 */
	public void setSignal(int signal) {
		this.signal = signal;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#getIndex()
	 */
	public int getIndex() {
		return index;
	}
	
	/* (non-Javadoc)
	 * @see org.primordion.xholon.base.IMessage#setIndex(int)
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * This is for use by the XholonJsApi.
	 * A JavaScript receiver of a IMessage can call this method to get a JavaScript object with 5 properties.
	 */
	public Object getVal_Object() {
	  if (this.getXhc() == null) {
	    IXholonClass xhctemp = this.findXhc(); //this.getClassNode("Message");
	    this.setXhc(xhctemp);
	    this.setId(this.getAppl().getNextId());
	  }
	  return msgAsJso(signal, data, sender, receiver, index, this);
	}
	
	protected native IXholonClass findXhc() /*-{
	  return $wnd.xh.root().xhc().parent().xpath("descendant::Message");
	}-*/;
	
	protected native IApplication getAppl() /*-{
	  return $wnd.xh.app();
	}-*/;
	
	protected native Object msgAsJso(int signal, Object data, IXholon sender, IXholon receiver, int index, IMessage originalMessage) /*-{
	  var msg = new Object();
	  msg.signal   = signal;
	  msg.data     = data;
	  msg.sender   = sender;
	  msg.receiver = receiver;
	  msg.index    = index;
	  msg.originalMessage = originalMessage;
	  return msg;
	}-*/;
	
	/**
	 * This method is called when the message is being serialized.
	 * @param oos
	 * @throws IOException
	 */
	 // GWT ant build
	/*private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		
		// sender
		if (sender == null) {
			oos.writeObject(null);
		}
		else if (sender instanceof MessageSenderReceiver) {
			oos.writeObject(sender);
		}
		else {
			oos.writeObject(new MessageSenderReceiver(sender));
		}
		
		// receiver
		if (receiver == null) {
			oos.writeObject(null);
		}
		else if (receiver instanceof MessageSenderReceiver) {
			oos.writeObject(receiver);
		}
		else {
			oos.writeObject(new MessageSenderReceiver(receiver));
		}
	}*/
	
	/**
	 * This method is called when the message is being deserialized.
	 * @param ois
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	 // GWT ant build
	/*private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		sender = (MessageSenderReceiver)ois.readObject();
		receiver = (MessageSenderReceiver)ois.readObject();
	}*/
	
	@Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("signal".equals(attrName)) {
      this.signal = Integer.parseInt(attrVal);
    }
    else if ("data".equals(attrName)) {
      this.data = attrVal;
    }
    else if ("sender".equals(attrName)) {
      this.sender = this.getXPath().evaluate(attrVal, this);
    }
    else if ("receiver".equals(attrName)) {
      this.receiver = this.getXPath().evaluate(attrVal, this);
    }
    else if ("index".equals(attrName)) {
      this.index = Integer.parseInt(attrVal);
    }
    return 0;
  }
  
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = "";
		outStr += "signal:" + getSignal();
		Object obj = getData();
		outStr += " data:" + obj;
		if (getSender() == null) {
			outStr += " sender: unknown";
		}
		else {
			outStr += " sender:" + getSender().getName();
		}
		if (getReceiver() == null) {
			outStr += " receiver: unknown";
		}
		else {
			outStr += " receiver:" + getReceiver().getName();
		}
		outStr += " index:" + getIndex();
		return outStr;
	}
}
