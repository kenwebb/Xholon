/* AQL Web Interface
 * Copyright (C) 2017 Ken Webb
 * BSD License
 */

package org.primordion.xholon.io.ngui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AqlHelpDialogBox {
  
  interface AqlHelpUiBinder extends UiBinder<Widget, AqlHelpDialogBox> {}
  private static AqlHelpUiBinder uiBinder = GWT.create(AqlHelpUiBinder.class);
  
  @UiField DialogBox helpDialog;
  @UiField Button helpDialogOkButton;
  
  protected Widget widget = null;
  
  public AqlHelpDialogBox() {
    //this.startHelpDialog();
  }
  
  
  /**
   * Read the XholonConsole GUI from a UIBinder XML file.
   */
  public Widget startHelpDialog() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);
      helpDialogOkButton.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          //Window.alert("Ok clicked");
          helpDialog.hide(true);
        }
      });
      return widget;
    }
    else {
      // for now, don't return a second copy of the same widget
      return null;
    }
  }

}
