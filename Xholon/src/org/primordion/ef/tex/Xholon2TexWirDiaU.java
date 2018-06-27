/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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

package org.primordion.ef.tex;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;

public class Xholon2TexWirDiaU extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
  // TODO
  
  public Xholon2TexWirDiaU() {}
  
  @Override
  public String getVal_String() {
    return null; // sb.toString();
  }
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    return true;
  }
  
  @Override
  public void writeAll() {}
  
  @Override
  public void writeNode(IXholon node) {}

  @Override
  public void writeEdges(IXholon node) {}

  @Override
  public void writeNodeAttributes(IXholon node) {
    // nothing to do for now
  }

}
