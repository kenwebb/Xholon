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

package org.primordion.xholon.service.nosql;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;

/**
 * This class provides access to a Neo4j database for a Xholon app,
 * using the Neo4j REST API.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.neo4j.org/">Neo4j</a>
 * @since 0.9.1 (Created on April 21, 2014)
 */
@SuppressWarnings("serial")
public class Neo4jRestApi extends Xholon implements INoSql {
  
  IXholon contextNode = null;
  
  /*
	 * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
	 */
	public void processReceivedMessage(IMessage msg) {
	  switch (msg.getSignal()) {
		case SIG_TEST_REQ:
			test();
			break;
		default:
			super.processReceivedMessage(msg);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg) {
	  contextNode = msg.getSender();
		switch (msg.getSignal()) {
		case SIG_ADD_TOGRAPH_REQ:
			return null;
		case SIG_ADDALL_TOGRAPH_REQ:
		  //test();
		  postCreate((String)msg.getData());
			return null;
		case SIG_SET_ATTRIBUTE_VALS_REQ:
			return null;
		case SIG_SHOW_GRAPH_REQ:
			return null;
		case SIG_TEST_REQ:
			test();
			getServiceRoot();
			return null;
		default:
			return super.processReceivedSyncMessage(msg);
		}
	}
	
	protected void test() {
	  consoleLog("Neo4jRestApi test()");
	}
	
	/**
	 * Get Neo4j service root, as a test.
	 * GET http://localhost:7474/db/data/
	 */
	protected void getServiceRoot() {
	  String url = "http://localhost:7474/db/data/";
	  try {
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
	    builder.setHeader("Accept", "application/json"); // " ;charset=UTF-8"
	    builder.sendRequest("", new RequestCallback() {
	      
        @Override
        public void onResponseReceived(Request req, Response resp) {
          if (resp.getStatusCode() == resp.SC_OK) {
            String jsonStr = resp.getText();
            // display the raw returned JSON String
            contextNode.println(jsonStr);
            
            // parse the returned JSON String, and get and display a single value
            JSONValue jsonValue = ((JSONObject)JSONParser.parseStrict(jsonStr)).get("neo4j_version");
            contextNode.println("neo4j_version = " + jsonValue);
          }
          else {
            contextNode.println("status code:" + resp.getStatusCode());
            contextNode.println("status text:" + resp.getStatusText());
            contextNode.println("text:\n" + resp.getText());
          }
        }

        @Override
        public void onError(Request req, Throwable e) {
          contextNode.println("onError:" + e.getMessage());
        }
        
      });
    } catch(RequestException e) {
      contextNode.println("RequestException:" + e.getMessage());
    }
	}
	
	/**
	 * Post a Cypher CREATE statement to the Neo4j database.
	 * @param cypherStatement A Cypher statement that begins with "CREATE".
	 */
	protected void postCreate(String cypherStatement) {
	  String url = "http://localhost:7474/db/data/transaction/commit";
	  
	  String jsonReqStr = "{\"statements\":[{\"statement\":"
	  // escapes double-quote to \\" and LF to \\n, and puts double-quote at start and end
	  + JsonUtils.escapeValue(cypherStatement)
	  + ",\"resultDataContents\":[\"row\",\"graph\"],\"includeStats\":true}]}";
	  
	  try {
	    RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
	    builder.setHeader("Accept", "application/json");
	    builder.setHeader("Content-Type", "application/json;charset=utf-8");
	    builder.sendRequest(jsonReqStr, new RequestCallback() {
	      
        @Override
        public void onResponseReceived(Request req, Response resp) {
          if (resp.getStatusCode() == resp.SC_OK) {
            String jsonRespStr = resp.getText();
            // display the raw returned JSON String
            contextNode.println(jsonRespStr);
          }
          else {
            contextNode.println("status code:" + resp.getStatusCode());
            contextNode.println("status text:" + resp.getStatusText());
            contextNode.println("text:\n" + resp.getText());
          }
        }

        @Override
        public void onError(Request req, Throwable e) {
          contextNode.println("onError:" + e.getMessage());
        }
        
      });
    } catch(RequestException e) {
      contextNode.println("RequestException:" + e.getMessage());
    }
	}
	
}
