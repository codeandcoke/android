package org.sfsoft.android_multimedia.media;

import android.content.Context;
import android.media.MediaPlayer;

public class Musica {
	
	private static MediaPlayer mp = null;
	
	public static void play(Context contexto, int idRecurso) {
		
		mp = MediaPlayer.create(contexto, idRecurso);
		mp.start();
	}
	
	public static void stop() {
		
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
	}

}
