Xholon GWT Risk
README.txt
Jan 29, 2014
Ken Webb

The GWT version doesn't work yet, although it all compiles with GWT library.
- GWT doesn't have any way of finding synthetic fields at runtime, as required by Xholon Reflection
- I can't spend a lot of time redoing the app and the third-party SwingStates library

This is the partial structure of the original Xholon CSH at runtime:
me:humanPlayer_2 class org.primordion.user.app.Risk.HumanPlayer
  ownedCards_3 class org.primordion.user.app.Risk.Cards
  stateMachine_136 class org.primordion.user.app.Risk.HumanPlayer$1
    settingUpGame(137) class org.primordion.user.app.Risk.HumanPlayer$1$1
      showCards:transition_138 class org.primordion.user.app.Risk.HumanPlayer$RiskActionState$1
      showTerritories:transition_139 class org.primordion.user.app.Risk.HumanPlayer$RiskActionState$2
      ...
    waiting(142) class org.primordion.user.app.Risk.HumanPlayer$1$2
      showCards:transition_143 class class org.primordion.user.app.Risk.HumanPlayer$RiskActionState$1
      showTerritories:transition_144 class org.primordion.user.app.Risk.HumanPlayer$RiskActionState$2
      ...
