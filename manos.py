#!/usr/bin/env python

import mediapipe as mp
import cv2 as cv

from umucv.stream import autoStream
from umucv.util import putText

import numpy as np

def sacar_orientacion(inicio, final):
    angulo_radianes = np.arctan2(inicio[1] - final[1], inicio[0] - final[0])
    return np.degrees(angulo_radianes)

def sacar_distancia(altura_mano, longitud_focal, altura_mano_imagen):
    # Calcula la distancia real del objeto a la cámara
    distancia = (altura_mano * longitud_focal) / altura_mano_imagen
    return distancia

def calcular_distancia(x, y):
    return np.sqrt((y[0] - x[0])**2 + (y[1] - x[1])**2)

def dedos_levantados(hand_landmarks):
    indices_importantes = [5, 9, 13, 17]
    # Lista para almacenar los estados de los dedos
    estado = []

    # Calcular las distancias entre la punta de cada dedo y el nudillo correspondiente
    distancias = np.array([calcular_distancia(hand_landmarks[i], hand_landmarks[i + 3]) for i in indices_importantes])

    distancia_pulgar = calcular_distancia(hand_landmarks[0], hand_landmarks[4])

    # Establecer umbrales de distancia (puedes ajustar estos valores según sea necesario)
    threshold = 70

    estado_pulgar = distancia_pulgar > 120

    estado = distancias > threshold

    estado = np.append(estado, estado_pulgar)

    return np.count_nonzero(estado)


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
        cv.circle(imagecam, center.astype(int), 1, color=(255,0,0), thickness = 3)

        orientacion = sacar_orientacion(points[0], points[13])

        font = cv.FONT_HERSHEY_DUPLEX

        texto_orientacion = f"Orientacion: {orientacion:.0f}"
        cv.putText(imagecam, texto_orientacion, (20, 50), fontFace=font, fontScale=0.7, color=(255, 255, 255), thickness=2)

        distancia = sacar_distancia(5, 60, calcular_distancia(points[5], points[0]))

        texto_distancia = f"Distancia: {distancia:.0f}"
        
        cv.putText(imagecam, texto_distancia, (240, 50), fontFace=font, fontScale=0.7, color=(255, 255, 255), thickness=2)

        dedos = dedos_levantados(points)

        texto_dedos = f"Dedos: {dedos}"

        cv.putText(imagecam, texto_dedos, (460, 50), fontFace=font, fontScale=0.7, color=(255, 255, 255), thickness=2)


    cv.imshow("hands", imagecam)


