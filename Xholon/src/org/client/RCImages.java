package org.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.ImageResource;

public interface RCImages extends ClientBundleWithLookup {
  public static final RCImages INSTANCE = GWT.create(RCImages.class);

  @Source("org/public/images/PrimordionLogo_tinyicon.png")
  ImageResource PrimordionLogo_tinyicon();

  @Source("org/public/images/Cellontro/bilayer.png")
  ImageResource Cellontro_bilayer();

  @Source("org/public/images/Cellontro/container.png")
  ImageResource Cellontro_container();

  @Source("org/public/images/Cellontro/enzyme.png")
  ImageResource Cellontro_enzyme();

  @Source("org/public/images/Cellontro/substrate.png")
  ImageResource Cellontro_substrate();

  @Source("org/public/images/Control/application_side_tree.png")
  ImageResource Control_application_side_tree();

  @Source("org/public/images/Control/bricks.png")
  ImageResource Control_bricks();

  @Source("org/public/images/Control/bullet_blue.png")
  ImageResource Control_bullet_blue();

  @Source("org/public/images/Control/control_fastforward_blue.png")
  ImageResource Control_control_fastforward_blue();

  @Source("org/public/images/Control/controller.png")
  ImageResource Control_controller();

  @Source("org/public/images/Control/control_pause_blue.png")
  ImageResource Control_control_pause_blue();

  @Source("org/public/images/Control/control_play_blue.png")
  ImageResource Control_control_play_blue();

  @Source("org/public/images/Control/control_repeat_blue.png")
  ImageResource Control_control_repeat_blue();

  @Source("org/public/images/Control/control_rewind_blue.png")
  ImageResource Control_control_rewind_blue();

  @Source("org/public/images/Control/control_stop_blue.png")
  ImageResource Control_control_stop_blue();

  @Source("org/public/images/Control/layout_content.png")
  ImageResource Control_layout_content();

  @Source("org/public/images/Control/rainbow.png")
  ImageResource Control_rainbow();

  @Source("org/public/images/OrNode/OrNode.png")
  ImageResource OrNode_OrNode();

  @Source("org/public/images/PetriNetEntity/arc.png")
  ImageResource PetriNetEntity_arc();

  @Source("org/public/images/PetriNetEntity/place.png")
  ImageResource PetriNetEntity_place();

  @Source("org/public/images/PetriNetEntity/transition.png")
  ImageResource PetriNetEntity_transition();

  @Source("org/public/images/StateMachineEntity/action.png")
  ImageResource StateMachineEntity_action();

  @Source("org/public/images/StateMachineEntity/choice.png")
  ImageResource StateMachineEntity_choice();

  @Source("org/public/images/StateMachineEntity/deepHistory.png")
  ImageResource StateMachineEntity_deepHistory();

  @Source("org/public/images/StateMachineEntity/entryPoint.png")
  ImageResource StateMachineEntity_entryPoint();

  @Source("org/public/images/StateMachineEntity/exitPoint.png")
  ImageResource StateMachineEntity_exitPoint();

  @Source("org/public/images/StateMachineEntity/finalStateActive.png")
  ImageResource StateMachineEntity_finalStateActive();

  @Source("org/public/images/StateMachineEntity/finalState.png")
  ImageResource StateMachineEntity_finalState();

  @Source("org/public/images/StateMachineEntity/forkJoin.png")
  ImageResource StateMachineEntity_forkJoin();

  @Source("org/public/images/StateMachineEntity/guard.png")
  ImageResource StateMachineEntity_guard();

  @Source("org/public/images/StateMachineEntity/initialState.png")
  ImageResource StateMachineEntity_initialState();

  @Source("org/public/images/StateMachineEntity/junction.png")
  ImageResource StateMachineEntity_junction();

  @Source("org/public/images/StateMachineEntity/region.png")
  ImageResource StateMachineEntity_region();

  @Source("org/public/images/StateMachineEntity/shallowHistory.png")
  ImageResource StateMachineEntity_shallowHistory();

  @Source("org/public/images/StateMachineEntity/stateActive.png")
  ImageResource StateMachineEntity_stateActive();

  @Source("org/public/images/StateMachineEntity/stateMachine.png")
  ImageResource StateMachineEntity_stateMachine();

  @Source("org/public/images/StateMachineEntity/state.png")
  ImageResource StateMachineEntity_state();

  @Source("org/public/images/StateMachineEntity/terminateActive.png")
  ImageResource StateMachineEntity_terminateActive();

  @Source("org/public/images/StateMachineEntity/terminate.png")
  ImageResource StateMachineEntity_terminate();

  @Source("org/public/images/StateMachineEntity/transition.png")
  ImageResource StateMachineEntity_transition();

  @Source("org/public/images/StateMachineEntity/trigger.png")
  ImageResource StateMachineEntity_trigger();
  
  @Source("org/public/images/scrollLeft.png")
  ImageResource scrollLeft();
  
  @Source("org/public/images/scrollRight.png")
  ImageResource scrollRight();

}
