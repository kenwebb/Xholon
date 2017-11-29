/* Operadics DSL Web Interface
 * Copyright (C) 2017 Ken Webb
 * BSD License
 */

package org.primordion.xholon.io.ngui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

public class OpDslHelpDialogBox {
  
  interface OpDslHelpUiBinder extends UiBinder<DialogBox, OpDslHelpDialogBox> {}
  private static OpDslHelpUiBinder uiBinder = GWT.create(OpDslHelpUiBinder.class);
  
  @UiField DialogBox helpDialog;
  @UiField Button helpDialogOkButton;
  
  public OpDslHelpDialogBox() {
    
  }
  
  /**
   * Read the Help dialog from a UIBinder XML file, and display it.
   */
  public void startHelpDialog() {
    final OpDslHelpDialogBox ahdb = this;
    helpDialog = uiBinder.createAndBindUi(this);
    helpDialogOkButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        ahdb.hide();
      }
    });
    this.show();
  }
  
  public void hide() {
    helpDialog.hide();
  }
  
  public void show() {
    helpDialog.center();
  }

}
