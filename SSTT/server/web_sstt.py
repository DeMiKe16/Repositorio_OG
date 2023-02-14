# coding=utf-8
#!/usr/bin/env python3

import mimetypes
import socket
import selectors    #https://docs.python.org/3/library/selectors.html
import select
import types        # Para definir el tipo de datos data
import argparse     # Leer parametros de ejecución
import os           # Obtener ruta y extension
from datetime import datetime, timedelta # Fechas de los mensajes HTTP
import time         # Timeout conexión
import sys          # sys.exit
import re           # Analizador sintáctico
import logging      # Para imprimir logs



BUFSIZE = 8192 # Tamaño máximo del buffer que se puede utilizar
TIMEOUT_CONNECTION = 20 # Timout para la conexión persistente
MAX_ACCESOS = 10

# Extensiones admitidas (extension, name in HTTP)
filetypes = {"gif":"image/gif", "jpg":"image/jpg", "jpeg":"image/jpeg", "png":"image/png", "htm":"text/htm", 
             "html":"text/html", "css":"text/css", "js":"text/js"}

# Configuración de logging
logging.basicConfig(level=logging.INFO,
                    format='[%(asctime)s.%(msecs)03d] [%(levelname)-7s] %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')
logger = logging.getLogger()

def construir_resp(date, server, connection, cookie_counter, tam, content_type, estado_codigo, estado_mensaje):
    respuesta = "HTTP/1.1 " + estado_codigo + " " + estado_mensaje + "\r\n"
    respuesta += "Date: " + date + "\r\n" 
    respuesta += "Server: " + server + "\r\n" 
    respuesta += "Connection: " + connection + "\r\n" 
    respuesta += "Set-Cookie:cookie_counter_3581=" + str(cookie_counter) + "\r\n" 
    respuesta += "Content-Length: " + str(tam) + "\r\n"
    respuesta += "Content-Type:" + content_type +" \r\n\r\n"
    return respuesta

def parsear(dato):
    lines = dato.split("\r\n")
    request_line = lines[0]
    headers = {}
    for i in range(len(lines)-3):
        header_name, header_value = lines[i+1].split(": ", 1)
        headers[header_name.strip()] = header_value.strip()
    return request_line, headers

def parsear_linea(linea):
    parts = linea.split()
    method = parts[0]
    url = parts[1]
    http_version = parts[2]
    return method, url, http_version

def enviar_mensaje(cs, data):
    try:
        return cs.send(data)
    except Exception as e:
        logging.error("Error en enviar_mensaje:".format(e))
        return -1


def recibir_mensaje(cs):
    try:
        dtread = cs.recv(BUFSIZE)
        decode = dtread.decode()
        return decode
    except Exception as e:
        logging.error("Error en recibir_mensaje:".format(e))
        return None


def cerrar_conexion(cs):
    try:
        cs.close()
    except Exception as e:
        logging.error("Error en recibir_mensaje:".format(e))


def process_cookies(headers,  cs, cookie_counter):
    cookie = None
    for header in headers:
        if header.startswith("Cookie:"):
            cookie = header.split("Cookie:")[1].strip()
            break
    if not cookie:
        return 1

    if cookie_counter >= MAX_ACCESOS:
        return MAX_ACCESOS
    else:
        cookie_counter += 1
    return cookie_counter


def process_web_request(cs, webroot):
    cookie_counter = 0
    while True:
        list = select.select([cs],[],[], TIMEOUT_CONNECTION)
        if not list[0]:
            cerrar_conexion(cs)
            print('se cierra')
            break
        dato = recibir_mensaje(cs)
        if not dato:
            cerrar_conexion(cs)
            break
        linea, cabeceras = parsear(dato)
        metodo, url, version = parsear_linea(linea)
        if version != 'HTTP/1.1':
            respuesta = "HTTP/1.1 505 Version Not Supported\r\n"
            respuesta += "Date: " + datetime.utcnow().strftime('%a, %d %b %Y %H:%M:%S GMT') + "\r\n" 
            respuesta += "Server: web.hardwaves3581.org\r\n" 
            respuesta += "Connection: close\r\n" 
            respuesta += "Content-Length: 66\r\n"
            respuesta += "Content-Type: text/html\r\n\r\n"
            respuesta += "<html><body><h1>Error 505 Version Not Supported</h1></body></html>"
            respuesta = respuesta.encode()
            enviar_mensaje(cs, respuesta)
            cerrar_conexion(cs)
        else: 
            if metodo == 'GET':
                if url == "/":
                    url = "/index.html"
                print(webroot)
                path = webroot + url
                if not os.path.isfile(path):
                    respuesta = "HTTP/1.1 404 Not Found\r\n"
                    respuesta += "Date: " + datetime.utcnow().strftime('%a, %d %b %Y %H:%M:%S GMT') + "\r\n" 
                    respuesta += "Server: web.hardwaves3581.org\r\n" 
                    respuesta += "Connection: close\r\n" 
                    respuesta += "Content-Length: 55\r\n"
                    respuesta += "Content-Type: text/html\r\n\r\n"
                    respuesta += "<html><body><h1>Error 404: Not Found</h1></body></html>"
                    respuesta = respuesta.encode()
                    enviar_mensaje(cs, respuesta)
                    cerrar_conexion(cs)
                    break
                else:   
                    cookie_counter = process_cookies(cabeceras, cs, cookie_counter)
                    if cookie_counter >= MAX_ACCESOS:
                        respuesta = "HTTP/1.1 403 Forbidden\r\n"
                        respuesta += "Date: " + datetime.utcnow().strftime('%a, %d %b %Y %H:%M:%S GMT') + "\r\n" 
                        respuesta += "Server: web.hardwaves3581.org\r\n" 
                        respuesta += "Connection: close\r\n" 
                        respuesta += "Content-Length: 54\r\n"
                        respuesta += "Content-Type: text/html\r\n\r\n"
                        respuesta += "<html><body><h1>Error 403 Forbidden</h1></body></html>"
                        respuesta = respuesta.encode()
                        enviar_mensaje(cs, respuesta)
                        cerrar_conexion(cs)
                    tamfile = os.stat(path).st_size
                    contenido, encode = mimetypes.guess_type(path)
                    if path == webroot + "/index.html" :
                        respuesta = "HTTP/1.1 OK 200\r\n"
                        respuesta += "Date: " + datetime.utcnow().strftime('%a, %d %b %Y %H:%M:%S GMT') + "\r\n" 
                        respuesta += "Server: web.hardwaves3581.org\r\n" 
                        respuesta += "Connection: Keep Alive\r\n" 
                        respuesta += "Keep-Alive: 27\r\n"
                        respuesta += "Set-Cookie: cookie_counter_3581=" + str(cookie_counter) + "\r\n" 
                        respuesta += "Content-Length: " + str(tamfile) + "\r\n"
                        respuesta += "Content-Type: " + contenido +" \r\n\r\n"
                    else :
                        respuesta = "HTTP/1.1 OK 200\r\n"
                        respuesta += "Date: " + datetime.utcnow().strftime('%a, %d %b %Y %H:%M:%S GMT') + "\r\n" 
                        respuesta += "Server: web.hardwaves3581.org\r\n" 
                        respuesta += "Connection: Keep Alive\r\n"
                        respuesta += "Keep-Alive: 27\r\n"
                        respuesta += "Content-Length: " + str(tamfile) + "\r\n"
                        respuesta += "Content-Type: " + contenido +"\r\n\r\n"
                    print(respuesta)
                    respuesta = respuesta.encode()
                    enviar_mensaje(cs, respuesta)
                    with open(path, "rb") as f:
                        while True:
                            contenido = f.read(BUFSIZE)
                            if not contenido:
                                break
                            enviar_mensaje(cs, contenido)
            elif metodo == 'POST':
                with open(url, "rb") as f:
                    contenido = f.read(BUFSIZE)
                    enviar_mensaje(cs, contenido)                            
            else:
                respuesta = "HTTP/1.1 405 Method Not Allowed\r\n"
                respuesta += "Date: " + datetime.utcnow().strftime('%a, %d %b %Y %H:%M:%S GMT') + "\r\n" 
                respuesta += "Server: web.hardwaves3581.org\r\n" 
                respuesta += "Connection: close\r\n" 
                respuesta += "Content-Length: 64\r\n"
                respuesta += "Content-Type: text/html\r\n\r\n"
                respuesta += "<html><body><h1>Error 405: Method Not Allowed</h1></body></html>"
                respuesta = respuesta.encode()
                enviar_mensaje(cs, respuesta)
                cerrar_conexion(cs)
        




def main():
    """ Función principal del servidor
    """

    try:

        # Argument parser para obtener la ip y puerto de los parámetros de ejecución del programa. IP por defecto 0.0.0.0
        parser = argparse.ArgumentParser()
        parser.add_argument("-p", "--port", help="Puerto del servidor", type=int, required=True)
        parser.add_argument("-ip", "--host", help="Dirección IP del servidor o localhost", required=True)
        parser.add_argument("-wb", "--webroot", help="Directorio base desde donde se sirven los ficheros (p.ej. /home/user/mi_web)")
        parser.add_argument('--verbose', '-v', action='store_true', help='Incluir mensajes de depuración en la salida')
        args = parser.parse_args()


        if args.verbose:
            logger.setLevel(logging.DEBUG)

        logger.info('Enabling server in address {} and port {}.'.format(args.host, args.port))

        logger.info("Serving files from {}".format(args.webroot))


        s = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM, proto=0)

        s.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR, 1)

        s.bind((args.host, args.port))

        while True:
            s.listen()    
            c, addr = s.accept()
            pid = os.fork()
            if pid == 0 :
                s.close()   
                process_web_request(c, args.webroot)
                return
            c.close()
    except KeyboardInterrupt:
        True

if __name__== "__main__":
    main()

""" 
    http://ip:9000 , cosas que hacer:
    Poder abrir el servidor y que al entrar con el firefox, se pueda quedar escuchando.    
"""

""" Funcionalidad a realizar
        * Crea un socket TCP (SOCK_STREAM)
        * Permite reusar la misma dirección previamente vinculada a otro proceso. Debe ir antes de sock.bind
        * Vinculamos el socket a una IP y puerto elegidos

        * Escucha conexiones entrantes

        * Bucle infinito para mantener el servidor activo indefinidamente
            - Aceptamos la conexión

            - Creamos un proceso hijo

            - Si es el proceso hijo se cierra el socket del padre y procesar la petición con process_web_request()

            - Si es el proceso padre cerrar el socket que gestiona el hijo.
"""

""" Procesamiento principal de los mensajes recibidos.
        Típicamente se seguirá un procedimiento similar al siguiente (aunque el alumno puede modificarlo si lo desea)

        * Bucle para esperar hasta que lleguen datos en la red a través del socket cs con select()

            * Se comprueba si hay que cerrar la conexión por exceder TIMEOUT_CONNECTION segundos
              sin recibir ningún mensaje o hay datos. Se utiliza select.select

            * Si no es por timeout y hay datos en el socket cs.
                * Leer los datos con recv.
                * Analizar que la línea de solicitud y comprobar está bien formateada según HTTP 1.1
                    * Devuelve una lista con los atributos de las cabeceras.
                    * Comprobar si la versión de HTTP es 1.1
                    * Comprobar si es un método GET. Si no devolver un error Error 405 "Method Not Allowed".
                    * Leer URL y eliminar parámetros si los hubiera
                    * Comprobar si el recurso solicitado es /, En ese caso el recurso es index.html
                    * Construir la ruta absoluta del recurso (webroot + recurso solicitado)
                    * Comprobar que el recurso (fichero) existe, si no devolver Error 404 "Not found"
                    * Analizar las cabeceras. Imprimir cada cabecera y su valor. Si la cabecera es Cookie comprobar
                      el valor de cookie_counter para ver si ha llegado a MAX_ACCESOS.
                      Si se ha llegado a MAX_ACCESOS devolver un Error "403 Forbidden"
                    * Obtener el tamaño del recurso en bytes.
                    * Extraer extensión para obtener el tipo de archivo. Necesario para la cabecera Content-Type
                    * Preparar respuesta con código 200. Construir una respuesta que incluya: la línea de respuesta y
                      las cabeceras Date, Server, Connection, Set-Cookie (para la cookie cookie_counter),
                      Content-Length y Content-Type.
                    * Leer y enviar el contenido del fichero a retornar en el cuerpo de la respuesta.
                    * Se abre el fichero en modo lectura y modo binario
                        * Se lee el fichero en bloques de BUFSIZE bytes (8KB)
                        * Cuando ya no hay más información para leer, se corta el bucle

            * Si es por timeout, se cierra el socket tras el período de persistencia.
                * NOTA: Si hay algún error, enviar una respuesta de error con una pequeña página HTML que informe del error.
"""
""" Esta función recibe datos a través del socket cs
        Leemos la información que nos llega. recv() devuelve un string con los datos.
    """
""" Esta función envía datos (data) a través del socket cs
        Devuelve el número de bytes enviados.
    """
""" Esta función cierra una conexión activa.
    """
""" Esta función procesa la cookie cookie_counter
        1. Se analizan las cabeceras en headers para buscar la cabecera Cookie
        2. Una vez encontrada una cabecera Cookie se comprueba si el valor es cookie_counter
        3. Si no se encuentra cookie_counter , se devuelve 1
        4. Si se encuentra y tiene el valor MAX_ACCESSOS se devuelve MAX_ACCESOS
        5. Si se encuentra y tiene un valor 1 <= x < MAX_ACCESOS se incrementa en 1 y se devuelve el valor
    """