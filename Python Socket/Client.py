#coding:utf-8
from __future__ import print_function
from threading import Thread
from socket import *
import time

ip = '127.0.0.1'
port = 8888

def LogPrint(address, data, is_send=0):
    if(is_send == 0):
        print("\rfrom " + address + " message : " + str(data))
        print("\rsend message " + address + " : ", end="")
    else:
        print("\rsend message " + address + " : ", end="")


def ListenMessage(conn):
    while True:
        time.sleep(1)
        data = conn.recv(1024)
        data = str(data).replace('\n', '')
        if data != '':
            LogPrint(ip, str(data))

def SendMessage(conn):
    while True:
        time.sleep(1)
        message = raw_input()
        if message != '':
            conn.send(message + '\n')
            LogPrint(ip, str(message), 1)

def Console(conn, todo):
    if todo == 'listen':
        ListenMessage(conn)
    else:
        if todo == 'send':
            SendMessage(conn)

def StartClient():
    client = socket(AF_INET, SOCK_STREAM)
    client.connect((ip, port))
    threads = []
    threads.append(Thread(target=Console, args=(client, 'listen')))
    threads.append(Thread(target=Console, args=(client, 'send')))
    for i in range(len(threads)):
        threads[i].start()
    for i in range(len(threads)):
        threads[i].join()


if __name__ == '__main__':
    print("starting socket client......")
    StartClient()