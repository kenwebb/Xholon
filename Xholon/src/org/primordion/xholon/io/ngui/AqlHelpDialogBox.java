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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

public class AqlHelpDialogBox {
  
  interface AqlHelpUiBinder extends UiBinder<DialogBox, AqlHelpDialogBox> {}
  private static AqlHelpUiBinder uiBinder = GWT.create(AqlHelpUiBinder.class);
  
  @UiField DialogBox helpDialog;
  @UiField Button helpDialogOkButton;
  
  public AqlHelpDialogBox() {
    //this.startHelpDialog();
  }
  
  /**
   * Read the Help dialog from a UIBinder XML file, and display it.
   */
  public void startHelpDialog() {
    final AqlHelpDialogBox ahdb = this;
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
