import numpy as np
import streamlit as st
import cv2
import pandas as pd
import csv
from collections import Counter
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, Flatten, Conv2D, MaxPooling2D

# Load music dataset
df = pd.read_csv(
    "C:\\Users\\Likhit\\PycharmProjects\\Openhouse\\muse_v3.csv",
    quoting=csv.QUOTE_NONE,
    on_bad_lines='skip'
)
df['link'] = df['lastfm_url']
df['name'] = df['track']
df['emotional'] = df['number_of_emotion_tags']
df = df[['name', 'emotional', 'link', 'artist', 'seeds']]
df = df.sort_values(by=["emotional"]).reset_index(drop=True)

# Create emotion-specific datasets (case-insensitive matching)
emotion_dfs = {
    'Sad': df[df['seeds'].str.contains('Sad', case=False, na=False)].reset_index(drop=True),
    'Fear': df[df['seeds'].str.contains('Fear', case=False, na=False)].reset_index(drop=True),
    'Angry': df[df['seeds'].str.contains('Angry', case=False, na=False)].reset_index(drop=True),
    'Neutral': df[df['seeds'].str.contains('Neutral', case=False, na=False)].reset_index(drop=True),
    'Happy': df[df['seeds'].str.contains('Happy', case=False, na=False)].reset_index(drop=True)
}

# Debug: Print number of songs per emotion
for emo, emo_df in emotion_dfs.items():
    print(f"{emo}: {len(emo_df)} songs")

# Emotion mapping
emotion_dict = {0: "Angry", 1: "Disgusted", 2: "Fearful", 3: "Happy", 4: "Neutral", 5: "Sad", 6: "Surprised"}
emotion_map = {
    'Angry': 'Angry',
    'Disgusted': 'Angry',   # You can change this if needed
    'Fearful': 'Fear',
    'Happy': 'Happy',
    'Neutral': 'Neutral',
    'Sad': 'Sad',
    'Surprised': 'Happy'    # You can change this if needed
}

def get_primary_emotion(emotion_list):
    mapped = [emotion_map.get(e, 'Neutral') for e in emotion_list]
    if not mapped:
        return None
    most_common = Counter(mapped).most_common(1)[0][0]
    return most_common

def recommend_songs(primary_emotion):
    if primary_emotion in emotion_dfs:
        emotion_data = emotion_dfs[primary_emotion]
        return emotion_data.sample(n=min(5, len(emotion_data)), replace=False).drop_duplicates(subset=["name", "artist"])
    else:
        return pd.DataFrame()

# Load emotion detection model
model = Sequential()
model.add(Conv2D(32, (3, 3), activation='relu', input_shape=(48, 48, 1)))
model.add(Conv2D(64, (3, 3), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Conv2D(128, (3, 3), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Conv2D(128, (3, 3), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Dropout(0.25))
model.add(Flatten())
model.add(Dense(1024, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(7, activation='softmax'))
model.load_weights("C:\\Users\\Likhit\PycharmProjects\Openhouse\model (1).h5")

# Load OpenCV face detector
face = cv2.CascadeClassifier("C:\\Users\\Likhit\PycharmProjects\Openhouse\haarcascade_frontalface_default.xml")

# Streamlit UI
st.markdown("<h2 style='text-align: center; color: white'><b>Emotion-based Music Recommendation</b></h2>", unsafe_allow_html=True)
st.markdown("<h5 style='text-align: center; color: grey;'>Click 'SCAN EMOTION' to detect your emotion and get personalized song recommendations.</h5>", unsafe_allow_html=True)

emotion_list = []

if st.button("SCAN EMOTION"):
    cap = cv2.VideoCapture(0)
    count = 0
    emotion_list.clear()
    st.info("Scanning your emotion. Please look at the camera...")

    while True:
        ret, frame = cap.read()
        if not ret:
            break
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        faces = face.detectMultiScale(gray, scaleFactor=1.3, minNeighbors=5)
        count += 1
        for (x, y, w, h) in faces:
            roi_gray = gray[y:y+h, x:x+w]
            cropped_img = np.expand_dims(np.expand_dims(cv2.resize(roi_gray, (48, 48)), -1), 0)
            prediction = model.predict(cropped_img, verbose=0)
            max_index = int(np.argmax(prediction))
            label = emotion_dict[max_index]
            print("Predicted label:", label)  # Debug print
            emotion_list.append(label)
            mapped = emotion_map.get(label, 'Neutral')
            cv2.rectangle(frame, (x, y), (x+w, y+h), (255, 0, 0), 2)
            cv2.putText(frame, mapped, (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (255, 255, 255), 2)
        cv2.imshow('Detecting Emotion (Press s to stop)', cv2.resize(frame, (800, 600)))
        if cv2.waitKey(1) & 0xFF == ord('s'):
            break
        if count >= 30:
            break
    cap.release()
    cv2.destroyAllWindows()

    # Show emotion distribution for debugging
    st.write("Detected emotions (raw):", dict(Counter(emotion_list)))

    primary_emotion = get_primary_emotion(emotion_list)
    if primary_emotion:
        st.success(f"Detected emotion: {primary_emotion}")
        songs = recommend_songs(primary_emotion)

        if not songs.empty:
            st.markdown(f"<h4 style='text-align: center; color: grey;'>Recommended Songs for: {primary_emotion}</h4>", unsafe_allow_html=True)
            st.write("--------------------------------------------------------------------------")
            for idx, row in songs.iterrows():
                st.markdown(f"<h4 style='text-align: center;'><a href='{row['link']}'>{row['name']}</a></h4>", unsafe_allow_html=True)
                st.markdown(f"<h5 style='text-align: center; color: grey;'><i>{row['artist']}</i></h5>", unsafe_allow_html=True)
                st.write("--------------------------------------------------------------------------")
        else:
            st.warning("No songs found for the detected emotion.")
    else:
        st.warning("Could not detect a dominant emotion.")