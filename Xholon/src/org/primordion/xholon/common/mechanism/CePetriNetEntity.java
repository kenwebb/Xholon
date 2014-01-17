package org.primordion.xholon.common.mechanism;

public interface CePetriNetEntity {

	public static final int PetriNetEntityCE = 1013000;
	public static final int PetriNetCE = 1013001;
	public static final int PlacePNCE = 1013002;
	public static final int PlaceBooleanCE = 1013003;
	public static final int PlaceIntegerCE = 1013004;
	public static final int PlaceStructuredCE = 1013005;
	public static final int TransitionPNCE = 1013006;
	public static final int QueueTransitionsCE = 1013007;
	public static final int ArcPNCE = 1013008;
	public static final int InputArcCE = 1013009;
	public static final int OutputArcCE = 1013010;
	public static final int TransitionsCE = 1013011;
	public static final int PlacesCE = 1013012;
	public static final int InputArcsCE = 1013013;
	public static final int OutputArcsCE = 1013014;

	public static final int AnalysisPNCE = 1013015;
	public static final int AnalysisPetriNetCE = 1013016;
	public static final int AnalysisCRNCE = 1013017;

	public static final int PneBehaviorCE = 1013018;
	public static final int PlaceBehaviorCE = 1013019;
	public static final int MovingPlaceBehaviorCE = 1013020;
	public static final int TransitionBehaviorCE = 1013021;
	public static final int MovingTransitionBehaviorCE = 1013022;
	public static final int FiringTransitionBehaviorCE = 1013023;
	public static final int QueueNodesInGridCE = 1013024;
	public static final int GridOwnerCE = 1013025;
}