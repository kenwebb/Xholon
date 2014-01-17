package org.primordion.xholon.io;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;

public interface IXholonGui {
  
  public abstract void execModel(String mName, IApplication app);
  
  public abstract void showTree(IXholon node);
  
  public abstract void makeContextMenu(Object guiItem, int posX, int posY);
  
  public abstract void handleDrop(String nodeName, Object data);
  
  public abstract Object getGuiRoot();
  
  public abstract void handleNodeSelection(String nodeName, Object guiItem, boolean isCtrlPressed);
}
