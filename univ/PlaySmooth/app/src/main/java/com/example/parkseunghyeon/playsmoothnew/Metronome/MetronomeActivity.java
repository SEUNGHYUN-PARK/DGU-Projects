package com.example.parkseunghyeon.playsmoothnew.Metronome;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.parkseunghyeon.playsmoothnew.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.billthefarmer.mididriver.MidiDriver;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * A Simple single screen metronome & mainActivity
 *
 */
public class MetronomeActivity extends YouTubeBaseActivity implements MidiDriver.OnMidiStartListener
{
    private byte[] event;
    private MidiDriver midiDriver;
    private int[] config;
    private Long index = null;
    private UsbService usbService;
    private MyHandler mHandler;
    private Long starttime;
    private int Deltatime;
    private int timeindex;
    /**
     * VideoView and MediaController
     */

    VideoView videoView;
    MediaController mediaController;
    String uriPath="";

    /**
     * imageview to print sheet
     */
    private ImageView imageView;

    /**
     * Default font size for notes
     */
    public static final float NOTE_TEXT_SIZE = 70f;

    /**
     * The amount of margin above the notes
     */
    public static final int NOTES_MARGIN_TOP = 40;

    /**
     * The available time signatures in the dropdown menu
     */
    public static final List<TimeSignature> TIME_SIGNATURES =
            Arrays.asList(
                    TimeSignature.COMMON_TIME,
                    new TimeSignature(3, 4),
                    new TimeSignature(2, 4),
                    new TimeSignature(6, 8),
                    new TimeSignature(3, 8),
                    new TimeSignature(9, 8)
            );

    /**
     * The current index of the note that is being played in the {@link #notes} list
     */
    private int noteIndex = -1;

    /**
     * A list of notes that will be played and displayed
     */
    private List<TextView> notes;

    /**
     * The width of the displayed notes
     */
    private float notesWidth = 0;

    /**
     * The position of the rightmost coordinate for the displayed ntoes
     */
    private float notesRight = 0;

    /**
     * The number of beats to play per minite
     */
    private int beatsPerMinute = 120;

    /**
     * The time signature to use when playing the beat
     */
    private TimeSignature timeSignature;

    /**
     * The number of subdivisions of notes to display from the main beat type.  Limited by
     * the type of beat note
     */
    private int beatSubdivision = 1;

    /**
     * A timer that is used to play the beat sounds and update the highlighted notes
     */
    private Handler noteTimer;

    /**
     * Pool used to play the metronome sounds
     */
    private SoundPool soundPool;

    /**
     * The sound to play for the first beat of a measure
     */
    private SoundThread.Sound emphasisSound;

    /**
     * The sound to play for beats that are not the first
     */
    private SoundThread.Sound tickSound;

    /**
     * Flag to indicate whether the metronome is currenly playing
     */
    private boolean metronomeOn = false;

    /**
     * The thread used to play sounds so that the UI does not lag
     */
    private SoundThread soundThread;

    /**
     * The color to use for beat notes
     */
    private int noteHighlightColor;

    /**
     * The color to use for subdivision notes
     */
    private int noteSubdivisionHighlightColor;

    /**
     * The color to use for notes that are not active
     */
    private int noteUnhighlightColor;

    /**
     * The animator for the spark indicator
     */
    private ObjectAnimator anim;

    /**
     * Array adapter for the subdivision spinner
     */
    private ArrayAdapter<CharSequence> subdivisionAdapter;

    /**
     * Flag to determine if a request to redraw the notes has already been issued, but not yet laid out
     */
    private boolean requestRedraw = false;

    /**
     * The Runnable that is executed on each tick of the {@link #noteTimer}
     */
    private Runnable noteRunner = new Runnable() {
        @Override
        public void run() {
            if (!metronomeOn) {
                return;
            }

            if (noteIndex != -1) {
                unhighlightNote(notes.get(noteIndex));
            }

            noteIndex++;

            if (noteIndex >= notes.size()) {
                noteIndex = 0;
            }

            if (noteIndex == 0) {
                anim.setCurrentPlayTime(0);
                anim.start();

                playSound(emphasisSound);
            }

            if ((noteIndex) % beatSubdivision == 0) {
                highlightNote(notes.get(noteIndex));
                if (noteIndex != 0) playSound(tickSound);
            } else {
                highlightSubdivisionNote(notes.get(noteIndex));
            }


            scheduleNextNote();
        }
    };

    /**
     * Initialize and start the sound thread
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
        layout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.d("layout", "layoutchanged");
                requestRedraw = false;
            }
        });

        // Colors
        noteHighlightColor = ContextCompat.getColor(getApplicationContext(), R.color.colorNoteHighlight);
        noteSubdivisionHighlightColor = ContextCompat.getColor(getApplicationContext(), R.color.colorNoteSubdivisionHighlight);
        noteUnhighlightColor = ContextCompat.getColor(getApplicationContext(), R.color.colorNoteUnhighlight);

        timeSignature = TimeSignature.COMMON_TIME;
        notes = new ArrayList<>();

        noteTimer = new Handler();

        setupTimeSignatureSpinner();
        setupSubdivisionSpinner();

        // handlers must be created first
        setupBPMSeekBar();

        createOldSoundPool();

        videoView = (VideoView)findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        judge();


        imageView = (ImageView)findViewById(R.id.imageView);
        sheetselect();

        soundThread = new SoundThread(soundPool);
        soundThread.setRunning(true);
        soundThread.start();
        mHandler = new MyHandler(this);
        midiDriver = new MidiDriver();
        starttime = System.currentTimeMillis();
        Deltatime = 0;
    }

    public void judge() {


        Intent intent = new Intent(getIntent());
        int ret = intent.getIntExtra("player",40);
        if(ret==0)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.one;
        }
        else if(ret==1)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.two;
        }
        else if(ret==2)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.three;
        }
        else if(ret==3)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.four;
        }
        else if(ret==4)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.five;
        }
        else if(ret==5)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.six;
        }
        else if(ret==6)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.seven;
        }
        else if(ret==7)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.eight;
        }
        else if(ret==8)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.nine;
        }
        else if(ret==9)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.ten;
        }
        else if(ret==10)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.eleven;
        }
        else if(ret==11)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twelve;
        }
        else if(ret==12)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirteen;
        }
        else if(ret==13)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.fourteen;
        }
        else if(ret==14)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.fifteen;
        }
        else if(ret==15)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.sixteen;
        }
        else if(ret==16)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.seventeen;
        }
        else if(ret==17)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.eightteen;
        }
        else if(ret==18)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.nineteen;
        }
        else if(ret==19)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twenty;
        }
        else if(ret==20)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentyone;
        }
        else if(ret==21)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentytwo;
        }
        else if(ret==22)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentythree;
        }
        else if(ret==23)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentyfour;
        }
        else if(ret==24)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentyfive;
        }
        else if(ret==25)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentysix;
        }
        else if(ret==26)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentyseven;
        }
        else if(ret==27)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentyeight;
        }
        else if(ret==28)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.twentynine;
        }
        else if(ret==29)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirty;
        }
        else if(ret==30)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtyone;
        }
        else if(ret==31)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtytwo;
        }
        else if(ret==32)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtythree;
        }
        else if(ret==33)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtyfour;
        }
        else if(ret==34)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtyfive;
        }
        else if(ret==35)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtysix;
        }
        else if(ret==36)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtyseven;
        }
        else if(ret==37)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtyeight;
        }
        else if(ret==38)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.thirtynine;
        }
        else if(ret==39)
        {
            uriPath = "android.resource://"+getPackageName()+"/"+R.raw.fourty;
        }

        Uri video = Uri.parse(uriPath);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.start();


    }

    /**
     * Loads sounds
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d("resume", "resume");

        int soundId = soundPool.load(this, R.raw.kick, 1);
        tickSound = new SoundThread.Sound(soundId, 0.8f);
        emphasisSound = new SoundThread.Sound(soundId, 1.0f);
        setFilters();
        startService(UsbService.class, usbConnection, null); // Start UsbService(if it was not started before) and Bind it
        midiDriver.start();

        // Get the configuration.
        config = midiDriver.config();

        // Print out the details.
        Log.d(this.getClass().getName(), "maxVoices: " + config[0]);
        Log.d(this.getClass().getName(), "numChannels: " + config[1]);
        Log.d(this.getClass().getName(), "sampleRate: " + config[2]);
        Log.d(this.getClass().getName(), "mi    xBufferSize: " + config[3]);

    }

    /**
     * Stops the metronome and unloads sounds
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d("pause", "pause");

        stopMetronome();

        soundPool.unload(tickSound.getSoundID());
        soundPool.unload(emphasisSound.getSoundID());
    }

    /**
     * Stops the sound thread
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        soundThread.setRunning(false);

        Log.d("destroy", "destroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_metronome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    /**
     * Setup the beats per minute seekbar
     */
    private void setupBPMSeekBar() {
        final TextView textView = (TextView)findViewById(R.id.seekDisplay);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seek1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("" + progress);
                beatsPerMinute = progress;
                setSparkSpeed();

                if (metronomeOn) {
                    stopMetronome();
                    startMetronome();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBar.setProgress(beatsPerMinute);
    }

    /**
     * Sets up the time signature spinner
     */
    private void setupTimeSignatureSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.timeSignatures);
        ArrayAdapter<TimeSignature> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TIME_SIGNATURES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("sig", "position: " + position + " signature: " + TIME_SIGNATURES.get(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                timeSignature = TIME_SIGNATURES.get(position);
                setSubdivisionOptions();
                redrawNotes();

                if (metronomeOn) {
                    stopMetronome();
                    startMetronome();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Sets up the subdivision spinner
     */
    private void setupSubdivisionSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.subdivisionSpinner);
        subdivisionAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        subdivisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(subdivisionAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("subd", "position: " + position + " subdivision: " + subdivisionAdapter.getItem(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                setSubdivision(position);
                redrawNotes();

                if (metronomeOn) {
                    stopMetronome();
                    startMetronome();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setSubdivisionOptions();
    }

    /**
     * Sets the options in the subdivision spinner based on the current time signature
     */
    private void setSubdivisionOptions() {
        subdivisionAdapter.clear();

        if (timeSignature.getBeatType() <= 16) subdivisionAdapter.add("1");
        if (timeSignature.getBeatType() <= 8) subdivisionAdapter.add("2");
        if (timeSignature.getBeatType() <= 4) subdivisionAdapter.add("4");

        Spinner spinner = (Spinner) findViewById(R.id.subdivisionSpinner);

        // find the last selected index
        int pos = spinner.getSelectedItemPosition();
        if (pos >= subdivisionAdapter.getCount()) {
            pos = subdivisionAdapter.getCount() - 1;
            spinner.setSelection(pos);
            setSubdivision(pos);
        }

        Object item = spinner.getSelectedItem();
        Log.d("subd", "item: " + item);
    }

    /**
     * Sets the subdivision value based on the spinner setting
     * @param position
     */
    private void setSubdivision(int position) {
        beatSubdivision = Integer.parseInt(subdivisionAdapter.getItem(position).toString());
        Log.d("subd", "sub position is: " + position + " subdiv is: " + beatSubdivision);
    }

    /**
     * The delay that is used to get the correct beats per minute
     * @return
     */
    private long soundDelayMilliSec() {
        return (long)((1000f * 60) / beatsPerMinute);
    }

    /**
     * Graphics may update more frequently than sound depending on the beatType and beatsPerMeasure
     * @return
     */
    private long graphicDelayMilliSec() {
        float freq = 1f/beatSubdivision;
        return (long)(soundDelayMilliSec() * freq);
    }

    /**
     * Schedules the next callback to the {@link #noteRunner}
     * @param immediate true if no delay or uses {@link #graphicDelayMilliSec()}
     */
    private void scheduleNextNote(boolean immediate) {
        long delay = immediate ? 0 : graphicDelayMilliSec();

        noteTimer.removeCallbacks(noteRunner);
        noteTimer.postDelayed(noteRunner, delay);
    }

    /**
     * Schedules a non-immediate next note
     */
    private void scheduleNextNote() {
        scheduleNextNote(false);
    }

    /**
     * Starts the metronome and resets the note played to the beginning
     */
    private void startMetronome() {
        noteIndex = -1;

        scheduleNextNote(true);

        startSparkAnimation();

        metronomeOn = true;
    }

    /**
     * Stops the metronome and disables animation
     */
    private void stopMetronome() {
        noteTimer.removeCallbacks(noteRunner);

        stopSparkAnimation();

        unhighlightAllNotes();

        metronomeOn = false;
    }

    /**
     * Starts and displays the spark at the beginning
     */
    private void startSparkAnimation() {
        anim.setCurrentPlayTime(0);
        anim.start();

        findViewById(R.id.spark).setVisibility(View.VISIBLE);
    }

    /**
     * Stops and hides the spark
     */
    private void stopSparkAnimation() {
        if (anim != null) anim.cancel();

        findViewById(R.id.spark).setVisibility(View.INVISIBLE);
    }

    /**
     * Creates the notes if a request to do so is not already pending
     */
    private void redrawNotes() {
        if (!requestRedraw) {
            requestRedraw = true;
            createNotes();
        }
    }

    /**
     * Gets the note string to use based on type and subdivision
     * @param type the type of beat
     * @param subdivisions the number of subdivisions of that beat
     * @return
     */
    private String getNoteString(int type, int subdivisions) {
        switch (type) {
            case 4:
                switch (subdivisions) {
                    case 4:
                        return getResources().getString(R.string.sixteenth_note);
                    case 2:
                        return getResources().getString(R.string.eigth_note);
                }
                return getResources().getString(R.string.quarter_note);
            case 8:
                if (subdivisions == 2) return getResources().getString(R.string.sixteenth_note);
                return getResources().getString(R.string.eigth_note);
            case 16:
                return getResources().getString(R.string.sixteenth_note);
        }

        return "";
    }

    /**
     * Creates the TextViews used to display notes and initializes the animations with the correct speed
     */
    private void createNotes() {
        // style for the notes
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(NOTE_TEXT_SIZE);
        paint.setColor(noteUnhighlightColor);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
        int width = layout.getWidth();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/FreeSerif.otf");
        paint.setTypeface(font);

        // for the beat type find the width
        String note = getNoteString(timeSignature.getBeatType(), beatSubdivision);

        // the width of one note
        float singleWidth = paint.measureText(note) * metrics.density;

        int notesCount = timeSignature.getBeatsPerMeasure() * beatSubdivision;
        notesWidth = notesCount * singleWidth;

        // if the default text is too big then resize
        float maxWidth = width - layout.getPaddingLeft() - layout.getPaddingRight();
        if (notesWidth > maxWidth) {
            float ratio = notesWidth/maxWidth;
            paint.setTextSize(NOTE_TEXT_SIZE/ratio);
            singleWidth = paint.measureText(note) * metrics.density;
            notesWidth = notesCount * singleWidth;
        }

        // this is the left boundary of the notes, but the actual draw location will be moved by padding
        int sideWidth = (int) (width - notesWidth) / 2;

        notesRight = sideWidth + notesWidth;

        Log.d("size", "side width is " + sideWidth + ", noteswidth is " + notesWidth + " singleWidth is " + singleWidth + " padding: " + layout.getPaddingLeft() + " layout width " + width + " metrics width " + metrics.widthPixels);

        View parent = findViewById(R.id.toggle_button);

        Log.d("notes", "notes size " + notes.size() + ", beats " + timeSignature.getBeatsPerMeasure());

        // remove extra notes, if any
        for (int i = notes.size() - 1; i >= notesCount; i--) {
            TextView view = notes.get(i);
            layout.removeView(view);
            notes.remove(i);
        }

        // add notes, if needed
        for (int i = notes.size(); i < notesCount; i++) {
            TextView view = new TextView(this);
            layout.addView(view);
            notes.add(view);
        }

        createSparkAnimator();
        setSparkSpeed();

        for (int i = 0; i < notesCount; i++) {
            TextView view = notes.get(i);
            initializeNote(view, parent, (int) (sideWidth + i * singleWidth) - layout.getPaddingLeft(), paint, note);
        }
    }

    /**
     * Creates the animator for the spark based on the calculations in {@link #createNotes()}
     */
    private void createSparkAnimator() {
        TextView spark = (TextView)findViewById(R.id.spark);
        float size = spark.getPaint().measureText(spark.getText(), 0, 1);

        if (anim != null) anim.cancel();

        anim = ObjectAnimator.ofFloat(spark, "x", notesRight - notesWidth - size/2, notesRight - size/2);
        anim.setInterpolator(new LinearInterpolator());
    }

    /**
     * Sets the duration of the animation based on the size taken up the notes and the current beats per minute
     */
    private void setSparkSpeed() {
        float delay = timeSignature.getBeatsPerMeasure() * 60f / beatsPerMinute; // time between notes
        float speed = notesWidth/delay;

        if (anim != null) anim.setDuration((long) (delay * 1000));

        Log.d("speed", "speed " + speed + " delay: " + delay);
    }

    /**
     * Initializes a TextView with a note string and style and positions it on the screen
     * @param view TextView that will display the note
     * @param parent The reference view that the notes appear below
     * @param margin The left margin for the view
     * @param paint The style to use for the view
     * @param string The note string
     * @return
     */
    private TextView initializeNote(TextView view, View parent, int margin, Paint paint, String string) {
        view.setText(string);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, parent.getId());
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.setMargins(margin, NOTES_MARGIN_TOP, 0, 0);
        view.setTextColor(paint.getColor());
        view.setTextSize(paint.getTextSize());
        view.setTypeface(paint.getTypeface());

        view.setLayoutParams(lp);

        return view;
    }

    /**
     * New style of sound pool creation
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(att)
                .build();
    }

    /**
     * Old style of sound pool creation
     */
    private void createOldSoundPool() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

            }
        });
    }

    /**
     * Adds a sound to the queue used by the sound thread
     * @param sound
     */
    public void playSound(SoundThread.Sound sound) {
        soundThread.addSound(sound);
    }

    /**
     * Toggles the state of the metronome and sets the button text
     * @param view
     */
    public void toggleActive(View view) {
        Button toggleButton = (Button)findViewById(R.id.toggle_button);
        if (!metronomeOn) {
            startMetronome();
            toggleButton.setText(getResources().getString(R.string.button_stop));
        } else {
            stopMetronome();
            toggleButton.setText(getResources().getString(R.string.button_start));
        }
    }

    /**
     * Highlights a main beat note
     * @param view
     */
    private void highlightNote(TextView view) {
        view.setTextColor(noteHighlightColor);
    }

    /**
     * Highlights a subdivision (non-main beat note)
     * @param view
     */
    private void highlightSubdivisionNote(TextView view) {
        view.setTextColor(noteSubdivisionHighlightColor);
    }

    /**
     * Removes highlighting from a note
     * @param view
     */
    private void unhighlightNote(TextView view) {
        view.setTextColor(noteUnhighlightColor);
    }

    /**
     * Remove highlighting from all notes
     */
    private void unhighlightAllNotes() {
        for (TextView view : notes) {
            unhighlightNote(view);
        }
    }

    public void sheetselect()
    {
        Intent intent = new Intent(this.getIntent());
        int ret = intent.getIntExtra("player",40);
        if(ret==0)
        {
            imageView.setImageResource(R.drawable.sheetone);
        }
        else if(ret==1)
        {
            imageView.setImageResource(R.drawable.sheettwo);
        }
        else if(ret==2)
        {
            imageView.setImageResource(R.drawable.sheetthree);
        }
        else if(ret==3)
        {
            imageView.setImageResource(R.drawable.sheetfour);
        }
        else if(ret==4)
        {
            imageView.setImageResource(R.drawable.sheetfive);
        }
        else if(ret==5)
        {
            imageView.setImageResource(R.drawable.sheetsix);
        }
        else if(ret==6)
        {
            imageView.setImageResource(R.drawable.sheetseven);
        }
        else if(ret==7)
        {
            imageView.setImageResource(R.drawable.sheeteight);
        }
        else if(ret==8)
        {
            imageView.setImageResource(R.drawable.sheetnine);
        }
        else if(ret==9)
        {
            imageView.setImageResource(R.drawable.sheetten);
        }
        else if(ret==10)
        {
            imageView.setImageResource(R.drawable.sheeteleven);
        }
        else if(ret==11)
        {
            imageView.setImageResource(R.drawable.sheettwelve);
        }
        else if(ret==12)
        {
            imageView.setImageResource(R.drawable.sheetthirteen);
        }
        else if(ret==13)
        {
            imageView.setImageResource(R.drawable.sheetfourteen);
        }
        else if(ret==14)
        {
            imageView.setImageResource(R.drawable.sheetfifteen);
        }
        else if(ret==15)
        {
            imageView.setImageResource(R.drawable.sheetsixteen);
        }
        else if(ret==16)
        {
            imageView.setImageResource(R.drawable.sheetseventeen);
        }
        else if(ret==17)
        {
            imageView.setImageResource(R.drawable.sheeteightteen);
        }
        else if(ret==18)
        {
            imageView.setImageResource(R.drawable.sheetnineteen);
        }
        else if(ret==19)
        {
            imageView.setImageResource(R.drawable.sheettwenty);
        }
        else if(ret==20)
        {
            imageView.setImageResource(R.drawable.sheettwentyone);
        }
        else if(ret==21)
        {
            imageView.setImageResource(R.drawable.sheettwentytwo);
        }
        else if(ret==22)
        {
            imageView.setImageResource(R.drawable.sheettwentythree);
        }
        else if(ret==23)
        {
            imageView.setImageResource(R.drawable.sheettwentyfour);
        }
        else if(ret==24)
        {
            imageView.setImageResource(R.drawable.sheettwentyfive);
        }
        else if(ret==25)
        {
            imageView.setImageResource(R.drawable.sheettwentysix);
        }
        else if(ret==26)
        {
            imageView.setImageResource(R.drawable.sheettwentyseven);
        }
        else if(ret==27)
        {
            imageView.setImageResource(R.drawable.sheettwentyeight);
        }
        else if(ret==28)
        {
            imageView.setImageResource(R.drawable.sheettwentynine);
        }
        else if(ret==29)
        {
            imageView.setImageResource(R.drawable.sheetthirty);
        }
        else if(ret==30)
        {
            imageView.setImageResource(R.drawable.sheetthirtyone);
        }
        else if(ret==31)
        {
            imageView.setImageResource(R.drawable.sheetthirtytwo);
        }
        else if(ret==32)
        {
            imageView.setImageResource(R.drawable.sheetthirtythree);
        }
        else if(ret==33)
        {
            imageView.setImageResource(R.drawable.sheetthirtyfour);
        }
        else if(ret==34)
        {
            imageView.setImageResource(R.drawable.sheetthirtyfive);
        }
        else if(ret==35)
        {
            imageView.setImageResource(R.drawable.sheetthirtysix);
        }
        else if(ret==36)
        {
            imageView.setImageResource(R.drawable.sheetthirtyseven);
        }
        else if(ret==37)
        {
            imageView.setImageResource(R.drawable.sheetthirtyeight);
        }
        else if(ret==38)
        {
            imageView.setImageResource(R.drawable.sheetthirtynine);
        }
        else if(ret==39)
        {
            imageView.setImageResource(R.drawable.sheetfourty);
        }
        else if(ret==40)
        {
            imageView.setImageResource(R.drawable.warning);
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                    Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                    Toast.makeText(context, "No USB connected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    Toast.makeText(context, "USB device not supported", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void playNote(byte b) {

        // 미디 노트 on 메시지생성 후 send, channel 10 이 drum
        event = new byte[3];
        event[0] = (byte) (0x90 + 0x09);  // 0x90 = note On, 0x00 = channel 10
        event[1] = (byte) 0x26;  // 0x26 = snare
        event[2] = b;  // 치는 세기에 따른 소리 크기

        // Send the MIDI event to the synthesizer.
        midiDriver.write(event);
       /* try {
            mReceiver.send(event, 0, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void stopNote() {


        // 미디 노트 off 메시지 생성 후 send
        event = new byte[3];
        event[0] = (byte) (0x80 + 0x09);
        event[1] = (byte) 0x26;
        event[2] = (byte) 0x00;  // 0x00 = the minimum velocity (0)
        // Send the MIDI event to the synthesizer.
        midiDriver.write(event);
    }
    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbService = ((UsbService.UsbBinder) arg1).getService();
            usbService.setHandler(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };
    public void onMidiStart() {
        Log.d(this.getClass().getName(), "onMidiStart()");
    }

    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (!UsbService.SERVICE_CONNECTED) {
            Intent startService = new Intent(this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(this, service);
        bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(UsbService.ACTION_NO_USB);
        filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
        filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(mUsbReceiver, filter);
    }


    private class MyHandler extends Handler {
        private final WeakReference<MetronomeActivity> mActivity;

        public MyHandler(MetronomeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 시리얼 통신으로 받은 메시지
                // msg.what은 메시지의 종류
                case UsbService.MESSAGE_FROM_SERIAL_PORT:
                    String data = msg.obj.toString();
                    int intData = ATOI(data); //시리얼 통신으로 받은 데이터를 UTF-8 String 에서 int로 변환
                    int vel = (new Double((intData) * 127 / 4)).intValue(); // 치는 세기에 따라 소리의 세기를 변화시키기 위한 velocity

                    Long nowTime = System.currentTimeMillis();

                    if (index == null) {
                        index = new Long(nowTime);

                    } else {
                        if (nowTime > index + 60 && intData >= 1)
                        // delay = 60ms , threshold >= 1
                        {
                            if (intData >= 1) {
                                playNote((byte)0x7f);
                                Long checkTime = System.currentTimeMillis();
                                Long temp = (checkTime - starttime);

                            }
                            stopNote();
                            index = new Long(nowTime);
                        }
                    }
                    break;
                case UsbService.CTS_CHANGE:
                    Toast.makeText(mActivity.get(), "CTS_CHANGE", Toast.LENGTH_LONG).show();
                    break;
                case UsbService.DSR_CHANGE:
                    Toast.makeText(mActivity.get(), "DSR_CHANGE", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public static int ATOI(String sTmp) {
        // Integer.parseInt를 사용하는 cast의 오류로 인한 메소드추가
        String tTmp = "0", cTmp = "";

        sTmp = sTmp.trim();
        for (int i = 0; i < sTmp.length(); i++) {
            cTmp = sTmp.substring(i, i + 1);
            if (cTmp.equals("0") ||
                    cTmp.equals("1") ||
                    cTmp.equals("2") ||
                    cTmp.equals("3") ||
                    cTmp.equals("4") ||
                    cTmp.equals("5") ||
                    cTmp.equals("6") ||
                    cTmp.equals("7") ||
                    cTmp.equals("8") ||
                    cTmp.equals("9")) tTmp += cTmp;
            else if (cTmp.equals("-") && i == 0)
                tTmp = "-";
            else
                break;
        }

        return (Integer.parseInt(tTmp));
    }

}
