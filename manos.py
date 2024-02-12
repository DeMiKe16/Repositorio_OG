#!/usr/bin/env python

import mediapipe as mp
import cv2 as cv

from umucv.stream import autoStream
from umucv.util import putText

import numpy as np

mp_hands = mp.solutions.hands

hands = mp_hands.Hands()

for key,frame in autoStream():
 
    H,W,_ = frame.shape
    imagecam = cv.flip(frame,1)


    image = cv.cvtColor(imagecam, cv.COLOR_BGR2RGB)
    results = hands.process(image)

    points= []
    if results.multi_hand_landmarks:
        for hand_landmarks in results.multi_hand_landmarks:
            for k in range(21):
                x = hand_landmarks.landmark[k].x
                y = hand_landmarks.landmark[k].y
                points.append([int(x*W), int(y*H)])
            break
        points = np.array(points)

        cv.line(imagecam, points[0], points[1], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[1], points[2], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[2], points[3], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[3], points[4], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[0], points[5], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[5], points[6], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[6], points[7], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[7], points[8], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[5], points[9], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[9], points[10], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[10], points[11], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[11], points[12], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[9], points[13], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[13], points[14], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[14], points[15], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[15], points[16], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[13], points[17], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[17], points[18], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[18], points[19], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[19], points[20], color=(0,155,0), thickness = 2)
        cv.line(imagecam, points[17], points[0], color=(0,155,0), thickness = 2)
        

        cv.circle(imagecam, points[0], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[1], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[2], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[3], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[4], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[5], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[6], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[7], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[8], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[9], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[10], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[11], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[12], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[13], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[14], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[15], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[16], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[17], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[18], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[19], 1, color=(0,0,155), thickness = 3)
        cv.circle(imagecam, points[20], 1, color=(0,0,155), thickness = 3)

        center = np.mean([points[5], points[17], points[0]], axis = 0)
        cv.circle(imagecam, center.astype(int), 50, color=(255,0,0), thickness = 3)




    cv.imshow("hands", imagecam)

