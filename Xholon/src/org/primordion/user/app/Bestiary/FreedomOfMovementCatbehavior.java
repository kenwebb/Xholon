package org.primordion.user.app.Bestiary;

//import java.applet.Applet;
//import java.applet.AudioClip;
//import java.net.MalformedURLException;
//import java.net.URL;

import org.primordion.xholon.base.IXholon;

/**
 * A cat wants its freedom of movement.
 * It wants to come in if it's out, and go out if it's in.
 * If it's frustrated in its efforts, it's going to meow.
 * <p>
 * &lt;FreedomOfMovementCatbehavior/>
 * </p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://catcatcat.com/news_sound.html">Sound files</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class FreedomOfMovementCatbehavior extends BeastBehavior {
	
	/**
	 * Whether or not meow and other sounds are enabled.
	 */
	private static boolean soundsEnabled = false;
	
	public static boolean isSoundsEnabled() {
		return soundsEnabled;
	}

	public static void setSoundsEnabled(boolean soundsEnabled) {
		FreedomOfMovementCatbehavior.soundsEnabled = soundsEnabled;
	}
	
	public static void toggleSoundsEnabled() {
		if (soundsEnabled) {soundsEnabled = false;}
		else {soundsEnabled = true;}
	}

	/*
	 * @see org.primordion.user.app.Bestiary.XhBestiary#act()
	 */
	public void act() {
		IXholon artifact = getBeast().getParentNode().getFirstChild();
		if (artifact != null) {
			switch (artifact.getXhcId()) {
			case DoorCE:
				println("miew");
				meow("kitten");
				break;
			case PorchCE:
				println("meow (I want to come in)");
				meow("meow01");
				break;
			case EntranceCE:
				println("meow (I want to go out)");
				meow("meow02");
				break;
			default:
				break;
			}
		}
		super.act();
	}
	
	/**
	 * Play a meow sound.
	 * source of .wav files: http://catcatcat.com/news_sound.html
	 * These files are "for personal use on your web site only".
	 */
	protected void meow(String soundName) {
		if (!soundsEnabled) {return;}
		// the following file name is correct; an AudioClip object is created
		// BUT it doesn't always play
		/*String sound = "file:./src/org/primordion/user/app/Bestiary/" + soundName + ".wav";
		try {
			URL url = new URL(sound);
			AudioClip audioClip = Applet.newAudioClip(url);
			audioClip.play();
		} catch (MalformedURLException e) {
			getLogger().error("Unable to play " + sound + ". ", e);
		}*/
	}
	
	/**
	 * testing
	 * @param args
	 */
	public static void main(String[] args) {
		FreedomOfMovementCatbehavior b = new FreedomOfMovementCatbehavior();
		b.meow("meow02");
	}
	
}
