import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * this is the test class for the SoundPlayer class.
 *
 */
public class SoundPlayerTest {

    private SoundPlayer soundPlayer;
    private Clip clipMock;

    @BeforeEach
    public void setUp() {
        soundPlayer = new SoundPlayer();
        clipMock = mock(Clip.class);
    }

    /**
     * test if the play method plays the audio clip with the correct volume.
     *
     * @throws Exception ff any exception occurs during the test
     */
    @Test
    public void testPlay() throws Exception {
        String fileName = "test_audio.wav";
        float volume = -5.0f;

        //set up mocked AudioInputStream and Clip
        AudioInputStream audioInputStreamMock = mock(AudioInputStream.class);
        when(AudioSystem.getAudioInputStream(any(File.class).getAbsoluteFile())).thenReturn(audioInputStreamMock);
        when(AudioSystem.getClip()).thenReturn(clipMock);

        //set up mocked FloatControl and set volume
        FloatControl floatControlMock = mock(FloatControl.class);
        when(clipMock.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(floatControlMock);

        soundPlayer.setVolume(volume);
        soundPlayer.play(fileName);

        //verify that the clip is started and volume is set
        verify(clipMock).open(audioInputStreamMock);
        verify(clipMock).start();
        verify(floatControlMock).setValue(volume);
    }

    /**
     * test if the play method throws MultimediaException when an error occurs.
     *
     * @throws Exception if any exception occurs during the test
     */
    @Test
    public void testPlayWithError() throws Exception {
        String fileName = "invalid_audio.wav";
        assertThrows(MultimediaException.class, () -> soundPlayer.play(fileName));
    }

    /**
     * test if the stop method stops and closes the audio clip.
     *
     * @throws UnsupportedAudioFileException ff the audio file format is not supported
     * @throws IOException                   if an I/O error occurs
     * @throws LineUnavailableException      if a line matching the specified description cannot be opened
     */
    @Test
    public void testStop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        soundPlayer.stop(clipMock);
        verify(clipMock).stop();
        verify(clipMock).close();
    }

    /**
     * test if the setVolume method sets the volume correctly.
     */
    @Test
    public void testSetVolume() {
        float volume = -5.0f;
        soundPlayer.setVolume(volume);
        assertEquals(volume, soundPlayer.volume);
    }
}

