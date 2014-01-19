package org.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.ImageResource;

public interface RCImages extends ClientBundleWithLookup {
  public static final RCImages INSTANCE = GWT.create(RCImages.class);

  @Source("org/client/images/PrimordionLogo_tinyicon.png")
  ImageResource PrimordionLogo_tinyicon();

  @Source("org/client/images/Cellontro/bilayer.png")
  ImageResource Cellontro_bilayer();

  @Source("org/client/images/Cellontro/container.png")
  ImageResource Cellontro_container();

  @Source("org/client/images/Cellontro/enzyme.png")
  ImageResource Cellontro_enzyme();

  @Source("org/client/images/Cellontro/substrate.png")
  ImageResource Cellontro_substrate();

  @Source("org/client/images/Control/application_side_tree.png")
  ImageResource Control_application_side_tree();

  @Source("org/client/images/Control/bricks.png")
  ImageResource Control_bricks();

  @Source("org/client/images/Control/bullet_blue.png")
  ImageResource Control_bullet_blue();

  @Source("org/client/images/Control/control_fastforward_blue.png")
  ImageResource Control_control_fastforward_blue();

  @Source("org/client/images/Control/controller.png")
  ImageResource Control_controller();

  @Source("org/client/images/Control/control_pause_blue.png")
  ImageResource Control_control_pause_blue();

  @Source("org/client/images/Control/control_play_blue.png")
  ImageResource Control_control_play_blue();

  @Source("org/client/images/Control/control_repeat_blue.png")
  ImageResource Control_control_repeat_blue();

  @Source("org/client/images/Control/control_rewind_blue.png")
  ImageResource Control_control_rewind_blue();

  @Source("org/client/images/Control/control_stop_blue.png")
  ImageResource Control_control_stop_blue();

  @Source("org/client/images/Control/layout_content.png")
  ImageResource Control_layout_content();

  @Source("org/client/images/Control/rainbow.png")
  ImageResource Control_rainbow();

  @Source("org/client/images/OrNode/OrNode.png")
  ImageResource OrNode_OrNode();

  @Source("org/client/images/PetriNetEntity/arc.png")
  ImageResource PetriNetEntity_arc();

  @Source("org/client/images/PetriNetEntity/place.png")
  ImageResource PetriNetEntity_place();

  @Source("org/client/images/PetriNetEntity/transition.png")
  ImageResource PetriNetEntity_transition();

  @Source("org/client/images/StateMachineEntity/action.png")
  ImageResource StateMachineEntity_action();

  @Source("org/client/images/StateMachineEntity/choice.png")
  ImageResource StateMachineEntity_choice();

  @Source("org/client/images/StateMachineEntity/deepHistory.png")
  ImageResource StateMachineEntity_deepHistory();

  @Source("org/client/images/StateMachineEntity/entryPoint.png")
  ImageResource StateMachineEntity_entryPoint();

  @Source("org/client/images/StateMachineEntity/exitPoint.png")
  ImageResource StateMachineEntity_exitPoint();

  @Source("org/client/images/StateMachineEntity/finalStateActive.png")
  ImageResource StateMachineEntity_finalStateActive();

  @Source("org/client/images/StateMachineEntity/finalState.png")
  ImageResource StateMachineEntity_finalState();

  @Source("org/client/images/StateMachineEntity/forkJoin.png")
  ImageResource StateMachineEntity_forkJoin();

  @Source("org/client/images/StateMachineEntity/guard.png")
  ImageResource StateMachineEntity_guard();

  @Source("org/client/images/StateMachineEntity/initialState.png")
  ImageResource StateMachineEntity_initialState();

  @Source("org/client/images/StateMachineEntity/junction.png")
  ImageResource StateMachineEntity_junction();

  @Source("org/client/images/StateMachineEntity/region.png")
  ImageResource StateMachineEntity_region();

  @Source("org/client/images/StateMachineEntity/shallowHistory.png")
  ImageResource StateMachineEntity_shallowHistory();

  @Source("org/client/images/StateMachineEntity/stateActive.png")
  ImageResource StateMachineEntity_stateActive();

  @Source("org/client/images/StateMachineEntity/stateMachine.png")
  ImageResource StateMachineEntity_stateMachine();

  @Source("org/client/images/StateMachineEntity/state.png")
  ImageResource StateMachineEntity_state();

  @Source("org/client/images/StateMachineEntity/terminateActive.png")
  ImageResource StateMachineEntity_terminateActive();

  @Source("org/client/images/StateMachineEntity/terminate.png")
  ImageResource StateMachineEntity_terminate();

  @Source("org/client/images/StateMachineEntity/transition.png")
  ImageResource StateMachineEntity_transition();

  @Source("org/client/images/StateMachineEntity/trigger.png")
  ImageResource StateMachineEntity_trigger();
  
  @Source("org/client/images/scrollLeft.png")
  ImageResource scrollLeft();
  
  @Source("org/client/images/scrollRight.png")
  ImageResource scrollRight();

}
