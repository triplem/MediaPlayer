package org.rpi.songcast.core;

import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import org.apache.log4j.Logger;
import org.rpi.songcast.ohm.OHMEventAudio;

/**
 * SongcastPlayer that uses JavaSound and tries to implement the latency
 * attribute that is send by the Songcast Sender
 */

public class SongcastPlayerJSLatency implements ISongcastPlayer, Runnable {

	private Vector<OHMEventAudio> mWorkQueue = new Vector<OHMEventAudio>();
	private boolean run = true;

	private Logger log = Logger.getLogger(this.getClass());

	private AudioFormat audioFormat = null;// new AudioFormat(44100, 16, 2,
											// true, true);
	private DataLine.Info info = null;// new DataLine.Info(SourceDataLine.class,
										// audioFormat, 16000);

	private SourceDataLine soundLine = null;

	private boolean bWrite = false;


	// public static SongcastPlayerJavaSound getInstance() {
	// if (instance == null) {
	// instance = new SongcastPlayerJavaSound();
	// }
	// return instance;
	// }

	public SongcastPlayerJSLatency() {
		// createSoundLine();

	}

	public void createSoundLine(AudioInformation audioInf) {
		try {
			bWrite = false;// Stop trying to write to the SoundLine
			if (soundLine != null) {
				close();
			}

			log.debug("Creating Audio Format: " + audioInf.toString());
			audioFormat = new AudioFormat(audioInf.getSampleRate(), audioInf.getBitDepth(), audioInf.getChannels(), audioInf.isSigned(), audioInf.isBigEndian());
			int frameSize = audioFormat.getFrameSize();
			log.info("FrameSize: " + frameSize);
			try {
				info = new DataLine.Info(SourceDataLine.class, audioFormat);
			} catch (Exception eline) {
				log.error("Error Creating DataLine: ", eline);
			}
			log.debug("MinBuffer Size: " + info.getMinBufferSize() + " MaxBuffer Size" + info.getMaxBufferSize());
			for (AudioFormat lineFormat : info.getFormats()) {
				log.info(lineFormat.toString());
			}
			if (soundLine == null) {
				soundLine = (SourceDataLine) AudioSystem.getLine(info);
				int bufferSize = (int) (audioFormat.getFrameSize() * audioFormat.getFrameRate() / 2.0f);
				log.debug("Opening SoundLine, BufferSize: " + bufferSize);
				soundLine.open(audioFormat, bufferSize);
				soundLine.start();
				bWrite = true;
			}
		} catch (Exception e) {
			log.error("Error opening Sound:", e);
		}
	}

	@Override
	public void play() {

	}

	private void close() {
		try {
			if (soundLine != null) {
				bWrite = false;
				soundLine.close();
				soundLine = null;				
			}
		} catch (Exception e) {
			log.error("Error Closing Stream", e);
		}

	}

	private void addData(byte[] data) {

		if (!bWrite)
			return;
		try {
			if (soundLine != null) {
				soundLine.write(data, 0, data.length);
			}
		} catch (Exception e) {
			int length = -99;
			if (data != null) {
				length = data.length;
			}
			log.error("Error Writing Data, Data Length: " + length, e);
		}
	}

	public synchronized boolean isEmpty() {
		return mWorkQueue.isEmpty();
	}

	public synchronized void put(OHMEventAudio event) {
		// log.debug("Put Object in WorkQueue " + object.toString());
		try {
			mWorkQueue.addElement(event);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Get the first object out of the queue. Return null if the queue is empty.
	 */
	public synchronized OHMEventAudio get() {
		OHMEventAudio object = peek();
		if (object != null)
			mWorkQueue.removeElementAt(0);
		return object;
	}

	/**
	 * Peek to see if something is available.
	 */
	public OHMEventAudio peek() {
		if (isEmpty())
			return null;
		return mWorkQueue.elementAt(0);
	}

	private void sleep(int value) {
		try {
			Thread.sleep(value);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

	private synchronized void clear() {
		try {
			log.info("Clearing Work Queue. Number of Items: " + mWorkQueue.size());
			mWorkQueue.clear();
			log.info("WorkQueue Cleared");
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

	@Override
	public void run() {
		while (run) {
			if (!isEmpty()) {
				try {
					OHMEventAudio audio = get();
					while (audio.getTimeToPlay() > System.currentTimeMillis()) {
						if (audio.expired()) {
							break;
						}
						sleep(1);
						audio.incAttempts();
					}
					addData(audio.getSound());
				} catch (Exception e) {
					log.error("Error in Run Method");
				}
			} else {
				sleep(10);
			}
		}
	}

	@Override
	public void stop() {
		run = false;
		clear();
		close();
	}

}
