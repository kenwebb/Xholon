package org.primordion.user.app.English2French;

/**
 * Used to pass a result from WordRule back to WordPass.
 * @author Ken Webb
 *
 */
public class WordRuleResult {
	
	// meta information codes
	/** There is no meta information. */
	public static final int MI_NULL = 0;
	
	/** This verb is in the past tense. */
	public static final int MI_VERB_PASTTENSE = 1;
	
	/** English "that" (rather than "this"). */
	public static final int MI_THAT = 2;
	
	// QUESTION codes
	/** am/is/are */
	public static final int MI_Q_AMAREIS = 21;
	/** can */
	public static final int MI_Q_CAN = 22;
	/** did */
	public static final int MI_Q_DID = 23;
	/** do/does */
	public static final int MI_Q_DODOES = 24;
	/** has/have */
	public static final int MI_Q_HASHAVE = 25;
	/** may */
	public static final int MI_Q_MAY = 26;
	/** must */
	public static final int MI_Q_MUST = 27;
	/** was/were */
	public static final int MI_Q_WASWERE = 28;
	/** will */
	public static final int MI_Q_WILL = 29;
	
	/**
	 * The name of a reference to a French transform node.
	 * (ex: "avoir")
	 */
	private String transformRef;
	
	/**
	 * A meta information code.
	 * (ex: MI_VERB_PASTTENSE)
	 */
	private int metaInfo;
	
	/**
	 * constructor
	 * @param transformRef
	 * @param metaInfo
	 */
	public WordRuleResult(String transformRef, int metaInfo)
	{
		setTransformRef(transformRef);
		setMetaInfo(metaInfo);
	}
	
	public String getTransformRef() {
		return transformRef;
	}
	public void setTransformRef(String transformRef) {
		this.transformRef = transformRef;
	}
	public int getMetaInfo() {
		return metaInfo;
	}
	public void setMetaInfo(int metaInfo) {
		this.metaInfo = metaInfo;
	}
	
}
