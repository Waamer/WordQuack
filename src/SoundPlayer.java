import java.io.*;
import javax.sound.sampled.*;

public class SoundPlayer {

	private float volume = 0;

	// constructor to initialize streams and clip 
	public void play(String fileName) throws MultimediaException { 
		// create AudioInputStream object 
		try {
			AudioInputStream audioInputStream = 
				AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile()); 
		
			// create clip reference 
			Clip clip = AudioSystem.getClip(); 
		
			// open audioInputStream to the clip 
			clip.open(audioInputStream); 

			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);
			clip.start(); 

		}
		catch (Exception e) {
			throw new MultimediaException("Error processing input file "+fileName);
		}
	} 


	// Method to stop the audio 
	public void stop(Clip clip) throws UnsupportedAudioFileException, 
	IOException, LineUnavailableException { 
		clip.stop(); 
		clip.close(); 
	}

	public void setVolume(float vol) {
		volume = vol;
	}
} 
