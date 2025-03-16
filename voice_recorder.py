from tkinter import *
import sounddevice as sd
from scipy.io.wavfile import write
from playsound import playsound 
import os
def record():
    freq = 44100
    duration = 10
    try:
        recording = sd.rec(int(duration * freq), samplerate=freq, channels=2)
        sd.wait()
        file_path = os.path.join(os.getcwd(), "recording1.wav")
        write(file_path, freq, recording)
    except Exception as e:
        print(f"Error during recording: {e}")
def play():
    try:
        file_path = os.path.join(os.getcwd(), "recording1.wav")
        playsound(file_path)
    except Exception as e:
        print(f"Error during playback: {e}")
app = Tk()
app.title('GUI Voice Recorder')

button1=Button(app,text=' Record ',font=('Ink Free',50,"bold"),command=record)
button1.config(bg='red',fg='white',activebackground='white',activeforeground='red',compound='top')
button1.pack()
button2=Button(app,text='   play   ',font=('Ink Free',50,"bold"),command=play)
button2.config(bg='red',fg='white',activebackground='white',activeforeground='red')
button2.pack()
app.mainloop()