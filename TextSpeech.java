package speaktext;

/*
 * Java code to convert text to speech
 * Adapted from  
 */


import javax.speech.*;
import java.util.*;
import javax.speech.synthesis.*;

public class TextSpeech {
	public static String speaktext;
	public static String fromReception = "From reception, walk to your ";
	
	/*
	 * Here is a list of the rooms supported and direcions.  You can add more rooms and directions here
	 * 
	 */
	public static String[] rooms = {
			"E2004",
			"E0028",
			"A0004",
			"B1024"};
	public static String[] roomDirection = {
			"your left into the Engineering building.  Climb the two flights of stairs and the room is on your right at the end of the corridor.",
			"your left into the Engineering building.  Stay on the ground floor and follow the corridor round the lift.  It is the first door on your left in the main corridor.",
			"your right past reception desk.  Stay on this level.  It is the large lecture room in front of you.",
			"directions to B1024"
			};

	/*
	 * This method speaks the passed string of text using the voice name.
	 */
	public void dospeak(String speak, String voicename) {	

		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
	    
		speaktext = speak;				// the text to speak
		String voiceName = voicename;	// this is fixed here
		try {
			SynthesizerModeDesc desc = new SynthesizerModeDesc(null, "general", Locale.US, null, null);
			Synthesizer synthesizer = Central.createSynthesizer(desc);
			synthesizer.allocate();
			synthesizer.resume();
			desc = (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
			
			// find the voice
			Voice[] voices = desc.getVoices();
			Voice voice = null;
			for (int i = 0; i < voices.length; i++) {
				if (voices[i].getName().equals(voiceName)) {
					voice = voices[i];
					break;
				}
			}
			
			synthesizer.getSynthesizerProperties().setVoice(voice);
			System.out.print("Speaking : " + speaktext);
			synthesizer.speakPlainText(speaktext, null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
			synthesizer.deallocate();
		} catch (Exception e) {
			String message = " missing speech.properties in " + System.getProperty("user.home") + "\n";
			System.out.println("" + e);
			System.out.println(message);
		}
	}

	/*
	 * This is the start point for the program
	 */
	public static void main(String[] args) {
		TextSpeech myVoice = new TextSpeech();
		String chosenRoom = "E2004";			// for this test, assume E2004	
		String roomDir = null;
		String myroom = null;
		String roomInfo = null;
		Boolean foundMatch = false;
		
		for (int i = 0; i < rooms.length; i++) {
			if (rooms[i].equals(chosenRoom)) {
				myroom  = rooms[i];
				roomDir = roomDirection[i];
				foundMatch = true;
				break;
			}
		}	
		if (foundMatch == false) {
			roomInfo = "I'm sorry.  I could not find this room in the building.";
		} else {
			roomInfo = "To get to, " + chosenRoom + "," + fromReception + roomDir;	// give directions
		}
		
		// speak the direction
		myVoice.dospeak(roomInfo, "kevin16");
	}
}
