#coding:utf-8
from __future__ import print_function
import socketserver
from threading import Thread
import time


def LogPrint(address, data, is_send=0):
    if(is_send == 0):
        print("\rfrom " + address + " message : " + str(data))
        print("\rsend message " + address + " : ", end="")
    else:
        print("\rsend message " + address + " : ", end="")

def ListenMessage(conn, td):
    while True:
        time.sleep(1)
        data = conn.recv(1024)
        data = str(data).replace('\n', '')
        if data != '':
            LogPrint(str(td.client_address[0]), str(data))

def SendMessage(conn, td):
    while True:
        time.sleep(1)
        message = raw_input()
        if message != '':
            conn.sendall(message + '\n')
            LogPrint(str(td.client_address[0]), str(message), 1)

def Console(conn, self, todo):
    if todo == 'listen':
        ListenMessage(conn, self)
    else:
        if todo == 'send':
            SendMessage(conn, self)


class MyServer(socketserver.BaseRequestHandler):
    def handle(self):
        conn = self.request
        conn.sendall('hello,start talking \n')
        threads = []
        threads.append(Thread(target=Console, args=(conn, self, 'listen')))
        threads.append(Thread(target=Console, args=(conn, self, 'send')))
        for i in range(len(threads)):
            threads[i].start()
        for i in range(len(threads)):
            threads[i].join()



if __name__ == '__main__':
    print("starting socket server......")
    server = socketserver.ThreadingTCPServer(('0.0.0.0', 8888), MyServer)
    server.serve_forever()